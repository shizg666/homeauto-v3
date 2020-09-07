package com.landleaf.homeauto.center.device.util;

/**
 * @Description 判断故障超过上下限的方法
 * @Author zhanghongbin
 * @Date 2020/9/7 18:06
 */
public class FaultValueUtils {

    /**
     *
     * @param current
     * @param min
     * @param max
     * @return 如果是故障，则返回true
     */
    public static boolean isValueError(String current,String min,String max){
        boolean flag = false;
        try {


            Double currentD = Double.parseDouble(current);
            Double minD = Double.parseDouble(min);
            Double maxD = Double.parseDouble(max);

            if (currentD - maxD >0 || currentD - minD <0){//比最大值大，比最小值小
                flag = true;
            }

        }catch (Exception e){

            e.printStackTrace();

            return flag;
        }



        return flag;

    }
}
