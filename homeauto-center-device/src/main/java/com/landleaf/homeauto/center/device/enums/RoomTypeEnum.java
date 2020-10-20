package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 工程类型
 */
public enum RoomTypeEnum {
	WHOLE(1, "全屋","/room/quanwu.png","/room/icon/quanwu.png",1),
	LIVINGROOM(2, "客厅","/room/keting.png","/room/icon/keting.png",2),
	RESTAURANT(3, "餐厅","/room/canting.png","/room/icon/canting.png",3),
	BEDROOM(4, "主卧","http:/room/zhuwo.png","/room/icon/zhuwo.png",4),
	BEDROOM_SECOND(5, "次卧","/room/ciwo.png","/room/icon/ciwo.png",5),
	BOOKROOM(6, "书房","/room/shufang.png","/room/icon/shufang.png",6),
	KITCHEN(7, "厨房","/room/chufang.png","/room/icon/chufang.png",7),
	BATHROOM(8, "卫生间","/room/cesuo.png","/room/icon/cesuo.png",8),
	GYM(9, "健身房","/room/jianshenfang.png","/room/icon/jianshenfang.png",9),
	VIDEOROOM(10,"影音室","/room/yingyin.png","/room/icon/yingyin.png",10);

	public Integer type;
	public Integer order;
	public String name;
	public String icon;
	public String imgIcon;

	RoomTypeEnum(Integer type, String name, String icon,String imgIcon, Integer order) {
		this.type = type;
		this.name = name;
		this.icon = icon;
		this.imgIcon = imgIcon;
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

	public String getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
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
