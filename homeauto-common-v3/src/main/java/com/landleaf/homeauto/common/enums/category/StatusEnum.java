package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 品类属性类型
 */
public enum StatusEnum {

ENABLE(1,"启用"),
	UN_WNABLE(0,"停用");



	public Integer type;
	public String name;

	StatusEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, StatusEnum> getMap() {
		return map;
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


	private static Map<Integer, StatusEnum> map = Maps.newHashMapWithExpectedSize(StatusEnum.values().length);
	static {
		for(StatusEnum enu : StatusEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static StatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		StatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
