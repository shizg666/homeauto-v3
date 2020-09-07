package com.landleaf.homeauto.common.enums.category;


import java.util.HashMap;
import java.util.Map;

/**
 * 产品属性错误码类型
 */
public enum AttributeErrorTypeEnum {
	ERROR_CODE(1, "错误码"),
	COMMUNICATE(2, "通信"),
	VAKUE(3, "数值");

	public Integer type;
	public String name;

	AttributeErrorTypeEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
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

	private static Map<Integer, AttributeErrorTypeEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static AttributeErrorTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(AttributeErrorTypeEnum.class){
				if(!isInit){
					map = new HashMap<Integer, AttributeErrorTypeEnum>();
					for(AttributeErrorTypeEnum enu : AttributeErrorTypeEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		AttributeErrorTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
