package com.landleaf.homeauto.common.enums.category;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 *协议枚举
 * @author Jason
 */
public enum ProtocolEnum {

	KNX("1", "KNX"),
	RS485("2","RS485"),
	NET("3","干接点"),
	OTHER("4","TCP/IP");



	public String type;
	public String name;

	ProtocolEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<String, ProtocolEnum> getMap() {
		return map;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 */


	private static Map<String, ProtocolEnum> map = Maps.newHashMapWithExpectedSize(ProtocolEnum.values().length);
	static {
		for(ProtocolEnum enu : ProtocolEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static ProtocolEnum getInstByType(String type){
		if(type==null){
			return null;
		}
		ProtocolEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
