package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 房间类型
 */
public enum RoomTypeEnum {
	WHOLE(1, "全屋","/room/quanwu.png","/room/icon/quanwu.png",1,"/room/applets/quanwu.png",""),
	LIVINGROOM(2, "客厅","/room/keting.png","/room/icon/keting.png",2,"/room/applets/keting.png",""),
	RESTAURANT(3, "餐厅","/room/canting.png","/room/icon/canting.png",3,"/room/applets/canting.png",""),
	BEDROOM(4, "主卧","/room/zhuwo.png","/room/icon/zhuwo.png",4,"/room/applets/zhuwo.png",""),
	BEDROOM_SECOND(5, "次卧","/room/ciwo.png","/room/icon/ciwo.png",5,"/room/applets/ciwo.png",""),
	BOOKROOM(6, "书房","/room/shufang.png","/room/icon/shufang.png",6,"/room/applets/shufang.png",""),
	KITCHEN(7, "厨房","/room/chufang.png","/room/icon/chufang.png",7,"/room/applets/chufang.png",""),
	BATHROOM(8, "卫生间","/room/cesuo.png","/room/icon/cesuo.png",8,"/room/applets/cesuo.png",""),
	GYM(9, "健身房","/room/jianshenfang.png","/room/applets/jianshenfang.png",9,"/room/icon/jianshenfang.png",""),
	VIDEOROOM(10,"影音室","/room/yingyin.png","/room/icon/yingyin.png",10,"/room/applets/yingyin.png",""),
	YANGTAI(11,"阳台","/room/yangtai.png","/room/icon/yangtai.png",11,"/room/applets/yangtai.png",""),
	GUODAO(12,"过道","/room/guodao.png","/room/icon/guodao.png",12,"/room/applets/guodao.png",""),
	BATAI(13,"吧台","/room/batai.png","/room/icon/batai.png",13,"/room/applets/batai.png",""),
	OTHER(14,"其他","/room/other.png","/room/icon/other.png",14,"/room/applets/other.png","")
	;

	public Integer type;
	public Integer order;
	public String name;
	public String icon;
	public String imgIcon;
	public String imgApplets;
	public String imgExpand;

	RoomTypeEnum(Integer type, String name, String icon,String imgIcon, Integer order, String imgApplets,String imgExpand) {
		this.type = type;
		this.name = name;
		this.icon = icon;
		this.imgIcon = imgIcon;
		this.order = order;
		this.imgApplets = imgApplets;
		this.imgExpand = imgExpand;
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

	public String getImgApplets() {
		return imgApplets;
	}

	public void setImgApplets(String imgApplets) {
		this.imgApplets = imgApplets;
	}

	public String getImgExpand() {
		return imgExpand;
	}

	public void setImgExpand(String imgExpand) {
		this.imgExpand = imgExpand;
	}}
