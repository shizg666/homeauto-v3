package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolDTO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolQryDTO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;

import java.util.List;

/**
 * <p>
 * 协议表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface IProtocolInfoService extends IService<ProtocolInfo> {

    /**
     * 添加协议
     * @param requestData
     */
    ProtocolInfo addProtocol(ProtocolDTO requestData);

    /**
     * 修改协议
     * @param requestData
     */
    void updateProtocol(ProtocolDTO requestData);


    /**
     * 删除协议
     * @param protocolId
     */
    void deleteProtocolById(String protocolId);

    /**
     * 查询协议
     * @param
     * @return
     */
    BasePageVO<ProtocolVO> getListProtocol(ProtocolQryDTO request);


    /**
     * 获取协议下拉列表
     * @return
     */
    List<SelectedVO> getListSelectProtocol(Integer type);


}
