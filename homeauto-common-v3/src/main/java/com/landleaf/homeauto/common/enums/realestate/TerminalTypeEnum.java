package com.landleaf.homeauto.common.enums.realestate;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 楼盘状态枚举
 */
public enum TerminalTypeEnum {

	SCREEN(1, "大屏"),
	GATEWAY(2, "网关");




	public Integer type;
	public String name;

	TerminalTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, TerminalTypeEnum> getMap() {
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


	private static Map<Integer, TerminalTypeEnum> map = Maps.newHashMapWithExpectedSize(TerminalTypeEnum.values().length);
	static {
		for(TerminalTypeEnum enu : TerminalTypeEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static TerminalTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		TerminalTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
