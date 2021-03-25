package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.excel.importfamily.ImportProtocolModel;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrInfoBO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrInfoVO;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrQryInfoDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.DeviceProtocolAttrQry;
import com.landleaf.homeauto.common.domain.vo.file.ProtocolFileVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 协议属性信息 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface IProtocolAttrInfoService extends IService<ProtocolAttrInfo> {

    /**
     * 新增协议属性
     * @param requestData
     */
    void addProtocolAttr(ProtocolAttrInfoDTO requestData);

    /**
     * 修改字段
     * @param requestData
     */
    void updateProtocolAttr(ProtocolAttrInfoDTO requestData);

    /**
     * 删除字段
     * @param attrId
     */
    void deleteProtocolAttrById(String attrId);

    /**
     * 删除字段
     * @param attrIds
     */
    void deleteProtocolAttrByIds(List<String> attrIds);

    /**
     * 根据协议id删除字段
     * @param protocolId
     */
    void deleteProtocolAttrByProtocolId(String protocolId);

    /**
     * 查询协议属性
     * @param requestData
     * @return
     */
    BasePageVO<ProtocolAttrInfoVO> getListProtocolAttr(ProtocolAttrQryInfoDTO requestData);


    /**
     * 协议属性详情
     * @param attrId
     * @return
     */
    ProtocolAttrInfoDTO getDetail(String attrId);

    /**
     * 获取协议导入模板
     */
    void getdownLoadTemplate(HttpServletResponse response);

    /**
     * 导入
     */
    void importTemplate(ProtocolFileVO request, HttpServletResponse response);


    /**
     * 批量导入属性
     * @param dataList
     * @return
     */
    List<ImportProtocolModel> importBatchAttr(List<ImportProtocolModel> dataList);


    /**
     * 查询某个设备编号的协议属性
     * @param attrQry
     * @return
     */
    List<ProtocolAttrInfoBO> getListProtocolAttrByDevice(DeviceProtocolAttrQry attrQry);

    /**
     * 协议属性导出
     * @param protocolId
     * @param response
     */
    void exportProtocolAttrs(String protocolId, HttpServletResponse response);
}
