package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 *协议枚举
 * @author Jason
 */
public enum ProtocolEnum {

	KNX(1, "knx"),
	RS485(2,"RS485"),
	OTHER(3,"其他");



	public Integer type;
	public String name;

	ProtocolEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, ProtocolEnum> getMap() {
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


	private static Map<Integer, ProtocolEnum> map = Maps.newHashMapWithExpectedSize(ProtocolEnum.values().length);
	static {
		for(ProtocolEnum enu : ProtocolEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static ProtocolEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		ProtocolEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
