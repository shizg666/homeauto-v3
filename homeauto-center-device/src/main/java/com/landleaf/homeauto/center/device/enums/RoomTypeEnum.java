package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 工程类型
 */
public enum RoomTypeEnum {
	WHOLE(1, "全屋","http://www.lokosmart.com:9000/images/appicon/room/bedroom.png",1),
	LIVINGROOM(2, "客厅","http://www.lokosmart.com:9000/images/appicon/room/livingroom.png",2),
	RESTAURANT(3, "餐厅","http://www.lokosmart.com:9000/images/appicon/room/restaurant.png",3),
	BEDROOM(4, "主卧","http://www.lokosmart.com:9000/images/appicon/room/bedroom.png",4),
	BEDROOM_SECOND(5, "次卧","http://www.lokosmart.com:9000/images/appicon/room/bedroom.png",5),
	BOOKROOM(6, "书房","http://www.lokosmart.com:9000/images/appicon/room/bookroom.png",6),
	KITCHEN(7, "厨房","http://www.lokosmart.com:9000/images/appicon/room/kitchen.png",7),
	BATHROOM(8, "卫生间","http://www.lokosmart.com:9000/images/appicon/room/bathroom.png",8),
	GYM(9, "健身房","http://www.lokosmart.com:9000/images/appicon/room/gym.png",9),
	VIDEOROOM(10,"影音室","http://www.lokosmart.com:9000/images/appicon/room/videoroom.png",10);

	public Integer type;
	public Integer order;
	public String name;
	public String icon;

	RoomTypeEnum(Integer type, String name, String icon, Integer order) {
		this.type = type;
		this.name = name;
		this.icon = icon;
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	private static Map<Integer, RoomTypeEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static RoomTypeEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(RoomTypeEnum.class){
				if(!isInit){
					map = new HashMap<Integer, RoomTypeEnum>();
					for(RoomTypeEnum enu : RoomTypeEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		RoomTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
