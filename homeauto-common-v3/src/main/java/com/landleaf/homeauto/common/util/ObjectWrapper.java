package com.landleaf.homeauto.common.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lokiy
 * @date 2019/9/2 15:09
 * @description: 各项包装类,用于lambda传参
 */
@Data
@Accessors(fluent = true)
public class ObjectWrapper {

    private Integer i;

    private String s;

    private Boolean flag;


    public ObjectWrapper(Integer i){
        this.i = i;
    }

    public ObjectWrapper(String s){
        this.s = s;
    }

    public ObjectWrapper(Boolean flag){
        this.flag = flag;
    }

    public ObjectWrapper(){

    }



    public void clear(){
        this.i = 0;
        this.s = null;
        this.flag = true;
    }
}
