package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 属性类型
 */
public enum AttributeTypeEnum {
//	SINGLE_CHOICE(1, "单选"),
//	MULTIPLE_CHOICE_SPECIAL(3,"特殊多选值"),
MULTIPLE_CHOICE(1,"多选"),
	VALUE(2,"值域");



	public Integer type;
	public String name;

	AttributeTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, AttributeTypeEnum> getMap() {
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


	private static Map<Integer, AttributeTypeEnum> map = Maps.newHashMapWithExpectedSize(AttributeTypeEnum.values().length);
	static {
		for(AttributeTypeEnum enu : AttributeTypeEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static AttributeTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		AttributeTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
