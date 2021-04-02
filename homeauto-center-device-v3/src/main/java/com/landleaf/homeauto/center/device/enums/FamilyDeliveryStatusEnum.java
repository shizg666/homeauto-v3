package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 工程交付状态
 */
public enum FamilyDeliveryStatusEnum {
	UNDELIVERY(0, "未交付"),
	DELIVERY(1,"已交付");



	public Integer type;
	public String name;

	FamilyDeliveryStatusEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	public Integer getType() {
		return type;
	}

	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 */

	private static Map<Integer, FamilyDeliveryStatusEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static FamilyDeliveryStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(FamilyDeliveryStatusEnum.class){
				if(!isInit){
					map = new HashMap<Integer, FamilyDeliveryStatusEnum>();
					for(FamilyDeliveryStatusEnum enu : FamilyDeliveryStatusEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		FamilyDeliveryStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
