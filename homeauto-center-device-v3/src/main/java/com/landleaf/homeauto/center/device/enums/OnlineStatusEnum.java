package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 在线
 */
public enum OnlineStatusEnum {
	ONLINE(1, "在线"),
	UNONLINE(0, "离线");

	public Integer type;
	public String name;

	OnlineStatusEnum(Integer type, String name) {
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

	private static Map<Integer, OnlineStatusEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static OnlineStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(OnlineStatusEnum.class){
				if(!isInit){
					map = new HashMap<Integer, OnlineStatusEnum>();
					for(OnlineStatusEnum enu : OnlineStatusEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		OnlineStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
