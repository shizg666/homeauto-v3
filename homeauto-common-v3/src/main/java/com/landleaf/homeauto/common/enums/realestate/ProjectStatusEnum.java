package com.landleaf.homeauto.common.enums.realestate;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 楼盘状态枚举
 */
public enum ProjectStatusEnum {

	PLAN(1, "规划"),
	WORKING(2, "施工"),
	MAINTAIN(3, "运维"),
	STOP_MAINTAIN(4, "停止运维");

//	PLAN(1, "规划"),
//	WORKING(2, "施工"),
//	MAINTAIN(3, "运维"),
//	STOP_MAINTAIN(4, "停止运维");



	public Integer type;
	public String name;

	ProjectStatusEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, ProjectStatusEnum> getMap() {
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


	private static Map<Integer, ProjectStatusEnum> map = Maps.newHashMapWithExpectedSize(ProjectStatusEnum.values().length);
	static {
		for(ProjectStatusEnum enu : ProjectStatusEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static ProjectStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		ProjectStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
