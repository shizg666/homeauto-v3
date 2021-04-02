package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.SepatorConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.SpringContextUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 工程批量导入监听器
 */
// 有个很重要的点 FamilyImportDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Component
@Slf4j
public class FamilyImportDataListener extends AnalysisEventListener<ImportFamilyModel> {
    /**
     * 每隔25条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 25;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int MAXIMUMPOOLSIZE = 8;
    /**
     * 线程池维护线程的最少数量
     */
    private static final int COREPOOLSIZE = 8;

    /**
     * 线程池所使用的缓冲队列
     */
    private static final int WORKQUEUE = 60;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final long KEEPALIVETIME = 60L;
    List<ImportFamilyModel> list = new ArrayList<>();
    List<ImportFamilyModel> errorlist = new ArrayList<>();
    List<Future<List<ImportFamilyModel>>> resultlist = new ArrayList<>();
    private ExecutorService es;
    private int lineCount = 1;
    private String projectId = "";
    private String projectCode = "";
    private String realestateId = "";
    private String familyPathPrx = "";
    private String familyPathNamePrx = "";
    private Map<String,String> templateMap;


    Map<String,Integer> macMap = new HashMap<>();
    //户号
    Map<String,Integer> roomNoMap = new HashMap<>();
//    private HouseTemplateConfig config;

    private IHomeAutoFamilyService iHomeAutoFamilyService;
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    private IHomeAutoProjectService iHomeAutoProjectService;
    private IProjectHouseTemplateService iProjectHouseTemplateService;



    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public FamilyImportDataListener(IHomeAutoFamilyService iHomeAutoFamilyService,IHomeAutoRealestateService iHomeAutoRealestateService,IHomeAutoProjectService iHomeAutoProjectService,IProjectHouseTemplateService iProjectHouseTemplateService){
        this.iHomeAutoFamilyService = iHomeAutoFamilyService;
        this.iHomeAutoRealestateService = iHomeAutoRealestateService;
        this.iHomeAutoProjectService = iHomeAutoProjectService;
        this.iProjectHouseTemplateService = iProjectHouseTemplateService;
        es = new ThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE,
                KEEPALIVETIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue(WORKQUEUE), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        if (headMap == null ){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题为空请勿修改模板");
        }
        if (headMap.size() < 7){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "列错误请勿修改模板");
        }
        String head = headMap.get(0);
        String[] heads = head.split(SepatorConst.HORIZONTAL_LINE);
        if (heads == null || heads.length != 3){
                 throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题错误请勿修改模板");
        }
        realestateId = heads[1];
        projectId = heads[2];
        buildCode();
        getTemplateConfig(projectId);
    }

    private void getTemplateConfig(String projectId) {
        List<TemplateSelectedVO> templateSelectedVOS= iProjectHouseTemplateService.getListSelectByProjectId(projectId);
        if (CollectionUtils.isEmpty(templateSelectedVOS)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "项目模板不存在：{}",projectId);
        }
        templateMap =templateSelectedVOS.stream().collect(Collectors.toMap(TemplateSelectedVO::getName,TemplateSelectedVO::getId));
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(ImportFamilyModel data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        lineCount++;
        if (lineCount == 2){
           return;
        }
        if (!checkData(data)){
            return;
        }
        buildData(data);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    private void buildData(ImportFamilyModel data) {
        data.setId(IdGeneratorUtil.getUUID32());
        data.setTemplateId(templateMap.get(data.getTempalteName()));
        data.setRow(String.valueOf(lineCount));
        data.setRealestateId(realestateId);
        data.setProjectId(projectId);
        data.setEnableStatus(0);
        String bulidCode = data.getBuildingCode().length() == 2 ? data.getBuildingCode() : "0".concat(data.getBuildingCode());
        String unitCode = data.getUnitCode().length() == 2 ? data.getUnitCode() : "0".concat(data.getUnitCode());
        data.setBuildingCode(bulidCode);
        data.setUnitCode(unitCode);
        String roomNo = data.getRoomNo();
        if (data.getRoomNo().length() == 1){
            roomNo = "000".concat(data.getRoomNo());
        }else if (data.getRoomNo().length() == 2){
            roomNo ="00".concat(data.getRoomNo());
        }else if (data.getRoomNo().length() == 3){
            roomNo = "0".concat(data.getRoomNo());
        }
        data.setRoomNo(roomNo);

        data.setCode(new StringBuilder().append(projectCode).append("-").append(bulidCode).append(unitCode).append(data.getRoomNo()).toString());
        data.setPath(familyPathPrx.concat(data.getId()));
        data.setPathName( new StringBuilder().append(familyPathNamePrx).append(data.getBuildingCode()).append("栋").append(data.getUnitCode()).append("单元").append(data.getRoomNo()).toString());
    }

    private boolean checkData(ImportFamilyModel data) {
        ImportFamilyModel error = new ImportFamilyModel();
        if (StringUtils.isEmpty(data.getName())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("家庭名称不能为空!");
            errorlist.add(error);
            return false;
        }

        if (!templateMap.containsKey(data.getTempalteName())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("模板不存在!");
            errorlist.add(error);
            return false;
        }

        if (StringUtils.isEmpty(data.getRoomNo())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("门牌号不能为空!");
            errorlist.add(error);
            return false;
        }
        data.setRoomNo(data.getRoomNo().trim());
        if (data.getRoomNo().length() > 4) {
            error.setRow(String.valueOf(lineCount));
            error.setError("门牌号不大于4位!");
            errorlist.add(error);
            return false;
        }

        if (StringUtils.isEmpty(data.getBuildingCode())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("楼栋号不能为空!");
            errorlist.add(error);
            return false;
        }

        if (data.getBuildingCode().length() > 2) {
            error.setRow(String.valueOf(lineCount));
            error.setError("楼栋号不大于2位!");
            errorlist.add(error);
            return false;
        }

        if (StringUtils.isEmpty(data.getUnitCode())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("单元号不能为空!");
            errorlist.add(error);
            return false;
        }

        if (data.getUnitCode().length() > 2) {
            error.setRow(String.valueOf(lineCount));
            error.setError("单元号不大于2位!");
            errorlist.add(error);
            return false;
        }

        String no = data.getBuildingCode().concat(data.getUnitCode()).concat(data.getRoomNo());
        if (roomNoMap.containsKey(no)){
            error.setRow(String.valueOf(lineCount));
            int line = roomNoMap.get(no);
            error.setError("门牌号与第"+line+"行重复！");
            errorlist.add(error);
            return false;
        }else {
            roomNoMap.put(no,lineCount);
        }

        data.setScreenMac(data.getScreenMac().trim());
        if (macMap.containsKey(data.getScreenMac())){
            error.setRow(String.valueOf(lineCount));
            int line = macMap.get(data.getScreenMac());
            error.setError("大屏编号值与第"+line+"行重复！");
            errorlist.add(error);
            return false;
        }else {
            macMap.put(data.getScreenMac(),lineCount);
        }
        return true;
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        macMap.clear();
        list.clear();
        es.shutdown();
        resultlist.forEach(o->{
            try {
                List<ImportFamilyModel> dataResult = o.get();
                if(!CollectionUtils.isEmpty(dataResult)){
                    errorlist.addAll(dataResult);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * 存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        Future<List<ImportFamilyModel>> submit = es.submit(new ResolveTask(list));
        resultlist.add(submit);
    }

    private class ResolveTask implements Callable<List<ImportFamilyModel>> {

        private List<ImportFamilyModel> dataList = Lists.newArrayList();

        private ResolveTask(List<ImportFamilyModel> dataList) {
            this.dataList.addAll(dataList);
        }

        @Override
        public List<ImportFamilyModel> call() {
            if (CollectionUtils.isEmpty(dataList)) {
                return Collections.EMPTY_LIST;
            }
            List<ImportFamilyModel> result = iHomeAutoFamilyService.importBatchFamily(dataList);
            return result;
        }
    }

    public List<ImportFamilyModel> getErrorlist() {
        return errorlist;
    }


    /**
     * 生产家庭编号-前缀
     *
     * @return
     */
    private void buildCode() {
        PathBO realestate = iHomeAutoRealestateService.getRealestatePathInfoById(realestateId);
        PathBO project = iHomeAutoProjectService.getProjectPathInfoById(projectId);
        familyPathPrx = project.getPath().concat("/");
        StringBuilder pathName = new StringBuilder();
        String familyPathNamePrx = pathName.append(realestate.getPathName()).append("/").append(project.getName()).append("/").toString();
        projectCode = project.getCode();

    }



}