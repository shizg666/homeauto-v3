package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 家庭启停用状态
 */
public enum FamilyEnableStatusEnum {
	UNDELIVERY(0, "启用"),
	DELIVERY(1,"禁用");



	public Integer type;
	public String name;

	FamilyEnableStatusEnum(Integer type, String name) {
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

	private static Map<Integer, FamilyEnableStatusEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static FamilyEnableStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(FamilyEnableStatusEnum.class){
				if(!isInit){
					map = new HashMap<Integer, FamilyEnableStatusEnum>();
					for(FamilyEnableStatusEnum enu : FamilyEnableStatusEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		FamilyEnableStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
