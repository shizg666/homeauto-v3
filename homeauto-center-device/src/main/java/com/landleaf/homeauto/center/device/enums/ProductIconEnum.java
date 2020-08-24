package com.landleaf.homeauto.center.device.enums;


import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 产品图片枚举
 */
public enum ProductIconEnum {
	PROJECT_OPERATION(1, "项目配置日志");



	public Integer type;
	public String name;

	ProductIconEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<Integer, ProductIconEnum> getMap() {
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


	private static Map<Integer, ProductIconEnum> map = Maps.newHashMapWithExpectedSize(ProductIconEnum.values().length);
	static {
		for(ProductIconEnum enu : ProductIconEnum.values()){
			map.put(enu.getType(), enu);
		}
	}
	public static ProductIconEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		ProductIconEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
