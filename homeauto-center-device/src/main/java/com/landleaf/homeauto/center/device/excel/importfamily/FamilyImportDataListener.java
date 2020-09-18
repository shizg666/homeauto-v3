package com.landleaf.homeauto.center.device.excel.importfamily;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
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
    private String realestateId = "";
    private String buildingId = "";
    private String unitId = "";
    private String unitName = "";
    private String templateId = "";
    private String templateName = "";
    private String templateArea = "";
    private String familyPath = "";
    private String familyPathName = "";
    private String familyCode = "";
    //项目path
    private String pathName = "";
    private String path = "";
    Map<String,Integer> macMap = new HashMap<>();
    //户号
    Map<String,Integer> roomNoMap = new HashMap<>();
    private HouseTemplateConfig config;

    private IHomeAutoFamilyService iHomeAutoFamilyService;
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    private IHomeAutoProjectService iHomeAutoProjectService;
    private IProjectBuildingService iProjectBuildingService;
    private IProjectBuildingUnitService iProjectBuildingUnitService;
    private IProjectHouseTemplateService iProjectHouseTemplateService;



    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public FamilyImportDataListener(IHomeAutoFamilyService iHomeAutoFamilyService,IHomeAutoRealestateService iHomeAutoRealestateService,IHomeAutoProjectService iHomeAutoProjectService,IProjectBuildingService iProjectBuildingService,IProjectBuildingUnitService iProjectBuildingUnitService,IProjectHouseTemplateService iProjectHouseTemplateService){
        this.iHomeAutoFamilyService = iHomeAutoFamilyService;
        this.iHomeAutoRealestateService = iHomeAutoRealestateService;
        this.iHomeAutoProjectService = iHomeAutoProjectService;
        this.iProjectBuildingService = iProjectBuildingService;
        this.iProjectBuildingUnitService = iProjectBuildingUnitService;
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
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题错误请勿修改模板");
        }
        if (headMap.size() < 3){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题错误请勿修改模板");
        }
        String head = headMap.get(0);
        String[] heads = head.split(SepatorConst.HORIZONTAL_LINE);
        if (heads == null || heads.length != 6){
                 throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题错误请勿修改模板");
        }
        templateName = heads[0];
        realestateId = heads[1];
        projectId = heads[2];
        buildingId = heads[3];
        unitId = heads[4];
        templateId = heads[5];
        buildCode();
        getTemplateConfig(templateId);
    }

    private void getTemplateConfig(String templateId) {
        config = iProjectHouseTemplateService.getImportTempalteConfig(templateId);
        templateArea = iProjectHouseTemplateService.getTemplateArea(templateId);
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
        data.setTemplateName(templateName);
        data.setRow(String.valueOf(lineCount));
        data.setArea(templateArea);
        data.setRealestateId(realestateId);
        data.setProjectId(projectId);
        data.setBuildingId(buildingId);
        data.setUnitId(unitId);
        data.setReviewStatus(0);
        data.setDeliveryStatus(0);
        data.setCode(familyCode.concat(data.getRoomNo()));
        data.setPath(familyPath.concat(data.getId()));
        data.setPathName(familyPathName.concat(data.getRoomNo()));
    }

    private boolean checkData(ImportFamilyModel data) {
        ImportFamilyModel error = new ImportFamilyModel();

        if (StringUtils.isEmpty(data.getName())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("家庭名称不能为空!");
            errorlist.add(error);
            return false;
        }

        if (StringUtils.isEmpty(data.getRoomNo())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("户号不能为空!");
            errorlist.add(error);
            return false;
        }
        data.setRoomNo(data.getRoomNo().trim());

        if (roomNoMap.containsKey(data.getRoomNo())){
            error.setRow(String.valueOf(lineCount));
            int line = roomNoMap.get(data.getRoomNo());
            error.setError("户号与第"+line+"行重复！");
            errorlist.add(error);
            return false;
        }else {
            roomNoMap.put(data.getRoomNo(),lineCount);
        }

        if (StringUtils.isEmpty(data.getMac1())) {
            error.setRow(String.valueOf(lineCount));
            error.setError("主网关Mac不能为空!");
            errorlist.add(error);
            return false;
        }
        data.setMac1(data.getMac1().trim());
        if (macMap.containsKey(data.getMac1())){
            error.setRow(String.valueOf(lineCount));
            int line = macMap.get(data.getMac1());
            error.setError("Mac值与第"+line+"行重复！");
            errorlist.add(error);
            return false;
        }else {
            macMap.put(data.getMac1(),lineCount);
        }
        if(!StringUtil.isEmpty(data.getMac2())){
            data.setMac2(data.getMac2().trim());
            if (macMap.containsKey(data.getMac2())){
                error.setRow(String.valueOf(lineCount));
                int line = macMap.get(data.getMac2());
                error.setError("Mac值与第"+line+"行重复！");
                errorlist.add(error);
                return false;
            }else {
                macMap.put(data.getMac2(),lineCount);
            }
        }


        if(!StringUtil.isEmpty(data.getMac3())){
            data.setMac3(data.getMac3().trim());
            if ( macMap.containsKey(data.getMac3())){
                error.setRow(String.valueOf(lineCount));
                int line = macMap.get(data.getMac3());
                error.setError("Mac值与第"+line+"行重复！");
                errorlist.add(error);
                return false;
            }else {
                macMap.put(data.getMac3(),lineCount);
            }
        }

        if(!StringUtil.isEmpty(data.getMac4())){
            data.setMac4(data.getMac4().trim());
            if (macMap.containsKey(data.getMac4())){
                error.setRow(String.valueOf(lineCount));
                int line = macMap.get(data.getMac4());
                error.setError("Mac值与第"+line+"行重复！");
                errorlist.add(error);
                return false;
            }else {
                macMap.put(data.getMac4(),lineCount);
            }
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
//        nameMap.clear();
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
            List<ImportFamilyModel> result = iHomeAutoFamilyService.importBatchFamily(dataList,config);
            return result;
        }
    }

    public List<ImportFamilyModel> getErrorlist() {
        return errorlist;
    }

    public String getUnitName() {
        return unitName;
    }

    /**
     * 生产家庭编号-前缀
     *
     * @return
     */
    private void buildCode() {
        PathBO realestate = iHomeAutoRealestateService.getRealestatePathInfoById(realestateId);
        PathBO project = iHomeAutoProjectService.getProjectPathInfoById(projectId);
        PathBO building = iProjectBuildingService.getBuildingPathInfoById(buildingId);
        PathBO unit = iProjectBuildingUnitService.getUnitPathInfoById(unitId);
        String path =project.getPath().concat("/").concat(buildingId).concat("/").concat(unitId).concat("/");
        String pathName = realestate.getPathName().concat("/").concat(project.getName()).concat("/").concat(building.getName()).concat(unit.getName());
        familyPath = path;
        familyPathName = pathName;
        unitName = unit.getName();
        familyCode = new StringBuilder().append(realestate.getCode()).append(building.getCode()).append(unit.getCode()).toString();
    }
}