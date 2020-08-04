package com.landleaf.homeauto.common.domain.dto.screen.payload.request.config;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenFamilyDeviceInfo;
import lombok.Data;

import java.util.List;

/**
 * 设备信息请求
 *
 * @author wenyilu
 */
@Data
public class ContactScreenDeviceInfoRequestReplyPayload {

    /**
     * 数据集合
     */
    private List<ContactScreenFamilyDeviceInfo> items;



}
