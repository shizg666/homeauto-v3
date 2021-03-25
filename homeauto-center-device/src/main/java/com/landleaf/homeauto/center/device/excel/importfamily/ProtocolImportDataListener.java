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
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * 工程批量导入监听器
 */
@Component
@Slf4j
public class ProtocolImportDataListener extends AnalysisEventListener<ImportProtocolModel> {
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
    List<ImportProtocolModel> list = new ArrayList<>();
    List<ImportProtocolModel> errorlist = new ArrayList<>();
    List<Future<List<ImportProtocolModel>>> resultlist = new ArrayList<>();
    private ExecutorService es;
    private int lineCount = 1;
    private String protocolId ="";



    private IProtocolInfoService iProtocolInfoService;
    private IProtocolAttrInfoService iProtocolAttrInfoService;


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public ProtocolImportDataListener(IProtocolInfoService iProtocolInfoService, IProtocolAttrInfoService iProtocolAttrInfoService){
        this.iProtocolInfoService = iProtocolInfoService;
        this.iProtocolAttrInfoService = iProtocolAttrInfoService;
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
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题为空");
        }
        if (headMap.size() < 14){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "标题错误请勿修改模板");
        }
    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(ImportProtocolModel data, AnalysisContext context) {
//        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        lineCount++;
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

    private boolean checkData(ImportProtocolModel data) {

        if (StringUtil.isEmpty(data.getType()) || data.getType().split("-").length !=2){
            ImportProtocolModel error = new ImportProtocolModel();
            error.setRow(String.valueOf(lineCount));
            error.setError("类型格式错误!");
            errorlist.add(error);
            return false;
        }
        if (StringUtil.isEmpty(data.getValType()) || data.getValType().split("-").length !=2){
            ImportProtocolModel error = new ImportProtocolModel();
            error.setRow(String.valueOf(lineCount));
            error.setError("值类型格式错误!");
            errorlist.add(error);
            return false;
        }
        if (StringUtil.isEmpty(data.getAppFlag()) || data.getAppFlag().split("-").length !=2){
            ImportProtocolModel error = new ImportProtocolModel();
            error.setRow(String.valueOf(lineCount));
            error.setError("app是否只读格式错误!");
            errorlist.add(error);
            return false;
        }
        if (StringUtil.isEmpty(data.getCategoryCode()) || data.getCategoryCode().split("-").length !=2){
            ImportProtocolModel error = new ImportProtocolModel();
            error.setRow(String.valueOf(lineCount));
            error.setError("品类格式错误!");
            errorlist.add(error);
            return false;
        }
        return true;
    }

    private void buildData(ImportProtocolModel data) {
        data.setProtocolId(protocolId);
        data.setRow(String.valueOf(lineCount));
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
        list.clear();
        es.shutdown();
        resultlist.forEach(o->{
            try {
                List<ImportProtocolModel> dataResult = o.get();
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
        Future<List<ImportProtocolModel>> submit = es.submit(new ResolveTask(list));
        resultlist.add(submit);
    }

    private class ResolveTask implements Callable<List<ImportProtocolModel>> {

        private List<ImportProtocolModel> dataList = Lists.newArrayList();

        private ResolveTask(List<ImportProtocolModel> dataList) {
            this.dataList.addAll(dataList);
        }

        @Override
        public List<ImportProtocolModel> call() {
            if (CollectionUtils.isEmpty(dataList)) {
                return Collections.EMPTY_LIST;
            }
            List<ImportProtocolModel> result = iProtocolAttrInfoService.importBatchAttr(dataList);
            return result;
        }
    }

    public List<ImportProtocolModel> getErrorlist() {
        return errorlist;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }
}