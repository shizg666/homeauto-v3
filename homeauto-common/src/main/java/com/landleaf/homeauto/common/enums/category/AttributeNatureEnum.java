package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 属性性质类型
 */
public enum AttributeNatureEnum {
	CONTROL(1, "单选"),
	READ(2,"多选");



	public Integer type;
	public String name;

	AttributeNatureEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, AttributeNatureEnum> getMap() {
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


	private static Map<Integer, AttributeNatureEnum> map = Maps.newHashMapWithExpectedSize(AttributeNatureEnum.values().length);
	static {
		for(AttributeNatureEnum enu : AttributeNatureEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static AttributeNatureEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		AttributeNatureEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
