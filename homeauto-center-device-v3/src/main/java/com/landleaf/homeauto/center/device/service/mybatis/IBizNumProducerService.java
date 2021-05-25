package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.SequenceProducer;
import lombok.Synchronized;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IBizNumProducerService extends IService<SequenceProducer> {



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
     *  根据户型id生成设备编号
     * @param templateId
     * @return
     */
    String getProjectNum(Long templateId);

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

    void updateNum(String name,int num);


    /**
     *  获取产品code  品类两位+ 3为数字
     * @param categoryCode
     * @return
     */
    String getProductCode(String categoryCode);


}
