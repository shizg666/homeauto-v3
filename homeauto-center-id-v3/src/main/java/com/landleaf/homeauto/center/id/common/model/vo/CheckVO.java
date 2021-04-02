package com.landleaf.homeauto.center.id.common.model.vo;

import lombok.Data;

@Data
public class CheckVO {
    private long timestamp;
    private int workID;

    public CheckVO(long timestamp, int workID) {
        this.timestamp = timestamp;
        this.workID = workID;
    }

}
