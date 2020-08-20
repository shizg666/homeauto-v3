package com.landleaf.homeauto.center.device.enums.log;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 日志业务类型枚举
 */
public enum OperationLogBussnessEnum {
	REALESTATE(1, "楼房");



	public Integer type;
	public String name;

	OperationLogBussnessEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, OperationLogBussnessEnum> getMap() {
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


	private static Map<Integer, OperationLogBussnessEnum> map = Maps.newHashMapWithExpectedSize(OperationLogBussnessEnum.values().length);
	static {
		for(OperationLogBussnessEnum enu : OperationLogBussnessEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static OperationLogBussnessEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		OperationLogBussnessEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
