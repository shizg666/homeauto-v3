package com.landleaf.homeauto.center.id.common.model;

import com.landleaf.homeauto.center.id.common.enums.Status;
import lombok.Data;

@Data
public class Result {
    private Long id;
    private Status status;

    public Result() {

    }

    public Result(long id, Status status) {
        this.id = id;
        this.status = status;
    }

}
