package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.SequenceProducer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IRealestateNumProducerService extends IService<SequenceProducer> {



    /**
     *  楼盘编号构建行政区编码+2数字
     * @param citycode
     * @return
     */
    String getRealestateNum(String citycode);

    /**
     *  楼盘编号构建行政区编码+2数字
     * @param realestateCode
     * @return
     */
    String getProjectNum(String realestateCode);

    /**
     * 根据生成器名称获取编号
     * @param name
     * @return
     */
    int getNum(String name);

    /**
     * 根据生成器名称更新编号
     * @param name
     * @return
     */
    void updateNum(String name);

}
