package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 家庭用户类型
 */
public enum FamilyUserTypeEnum {
	MADIN(1, "住户"),
//	PROJECTADMIN(2, "工程运维"),
	MEMBER(3, "家人"),
	TEPM(4, "租户");

	public Integer type;
	public String name;

	FamilyUserTypeEnum(Integer type, String name) {
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

	private static Map<Integer, FamilyUserTypeEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static FamilyUserTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(FamilyUserTypeEnum.class){
				if(!isInit){
					map = new HashMap<Integer, FamilyUserTypeEnum>();
					for(FamilyUserTypeEnum enu : FamilyUserTypeEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		FamilyUserTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
