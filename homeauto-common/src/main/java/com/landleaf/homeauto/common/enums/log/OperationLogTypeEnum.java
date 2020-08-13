package com.landleaf.homeauto.common.enums.log;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 日志类型枚举
 */
public enum OperationLogTypeEnum {
	PROJECT_OPERATION(1, "项目配置日志");



	public Integer type;
	public String name;

	OperationLogTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, OperationLogTypeEnum> getMap() {
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


	private static Map<Integer, OperationLogTypeEnum> map = Maps.newHashMapWithExpectedSize(OperationLogTypeEnum.values().length);
	static {
		for(OperationLogTypeEnum enu : OperationLogTypeEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static OperationLogTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		OperationLogTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
