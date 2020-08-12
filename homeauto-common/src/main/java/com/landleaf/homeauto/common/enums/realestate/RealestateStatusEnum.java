package com.landleaf.homeauto.common.enums.realestate;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 楼盘状态枚举
 */
public enum RealestateStatusEnum {
	PLAN(1, "规划"),
	WORKING(2, "施工"),
	MAINTAIN(3, "运维"),
	STOP_MAINTAIN(4, "停止运维");



	public Integer type;
	public String name;

	RealestateStatusEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, RealestateStatusEnum> getMap() {
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


	private static Map<Integer, RealestateStatusEnum> map = Maps.newHashMapWithExpectedSize(RealestateStatusEnum.values().length);
	static {
		for(RealestateStatusEnum enu : RealestateStatusEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static RealestateStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		RealestateStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
