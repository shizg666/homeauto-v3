package com.landleaf.homeauto.common.enums.realestate;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 楼盘状态枚举
 */
public enum ProjectTypeEnum {

	ZIYOU(1, "自由方舟"),
	HOUSE(2, "户式化");




	public Integer type;
	public String name;

	ProjectTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, ProjectTypeEnum> getMap() {
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


	private static Map<Integer, ProjectTypeEnum> map = Maps.newHashMapWithExpectedSize(ProjectTypeEnum.values().length);
	static {
		for(ProjectTypeEnum enu : ProjectTypeEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static ProjectTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		ProjectTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
