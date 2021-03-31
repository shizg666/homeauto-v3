package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 品类属性类型
 */
public enum CategoryAttributeTypeEnum {

FEATURES(1,"功能"),
	BASE(2,"基本");



	public Integer type;
	public String name;

	CategoryAttributeTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, CategoryAttributeTypeEnum> getMap() {
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


	private static Map<Integer, CategoryAttributeTypeEnum> map = Maps.newHashMapWithExpectedSize(CategoryAttributeTypeEnum.values().length);
	static {
		for(CategoryAttributeTypeEnum enu : CategoryAttributeTypeEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static CategoryAttributeTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		CategoryAttributeTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
