package com.landleaf.homeauto.common.enums;

import java.io.Serializable;

public interface BaseEnum extends Serializable {
    public static final String GET_NAME = "getName";
    public static final String GET_TYPE = "getType";

    public String getName();

    public int getType();
}
