package com.landleaf.homeauto.common.enums.category;


import java.util.HashMap;
import java.util.Map;

/**
 *品类类型
 * @author Jason
 */
public enum CategoryTypeEnum {

	//多参数传感器、甲醛传感器、空调、新风、地暖、可视对讲、安防、灯光、窗帘
	MULTI_PARAM("1","multiParameter_sensor", "多参数传感器","http://www.lokosmart.com:9000/images/appicon/device/duocan.png"),
	FORMALDEHYDE("2","formaldehyde_sensor", "甲醛传感器","http://www.lokosmart.com:9000/images/appicon/device/jiaquan.png"),
	AIRCONDITIONER("3","airconditioner", "空调","http://www.lokosmart.com:9000/images/appicon/device/kongtiao.png"),
	ELECTRIC_METER("4","electric_meter", "电表","http://www.lokosmart.com:9000/images/appicon/device/icon_dianbiao.png"),
//	FLOOR_HEATER(5,"floor_heater", "地暖","www.lokosmart.com:9000/images/appicon/device/dinuan@.png"),
//	VIDEO_INTERCOM(6,"video_intercom","可视对讲",""),
	SECURITY_MAINFRAME("7","security_mainframe", "安防","http://www.lokosmart.com:9000/images/appicon/device/anfang.png"),
	LIGHT("8","light", "开关灯","http://www.lokosmart.com:9000/images/appicon/device/dengguang.png"),
	WINDOW_CURTAINS("9","window_curtains", "开合帘","http://www.lokosmart.com:9000/images/appicon/device/chuanglian.png"),
	TEMPERATURE_PANEL("11","temperature_panel", "温控面板","http://www.lokosmart.com:9000/images/appicon/device/mianban.png"),
	HVAC("10","hvac", "暖通","http://www.lokosmart.com:9000/images/appicon/device/nuantong.png"),
	FRESH_AIR("12","fresh_air", "朗绿新风","http://www.lokosmart.com:9000/images/appicon/device/xinfeng.png"),
	PM("13","PM2.5", "PM2.5","http://www.lokosmart.com:9000/images/appicon/device/duocan.png"),
	BGM("15","bgm", "背景音乐","http://www.lokosmart.com:9000/images/appicon/device/bgm.png"),
	CAT_EYE("16","act_eye", "智能猫眼","http://www.lokosmart.com:9000/images/appicon/device/cat_eye.png"),
	SLEEP_MONITORING("17","sleep_monitoring", "睡眠监测","http://www.lokosmart.com:9000/images/appicon/device/sleep.png"),
	VALVE("18","valve", "水阀煤气阀","http://www.lokosmart.com:9000/images/appicon/device/menfa.png"),
	ROLLER_CURTAINS("19","roller_curtains", "卷帘","http://www.lokosmart.com:9000/images/appicon/device/juanlian.png"),
	DIMMING_LIGHT("20","dimming_light", "调光灯","http://www.lokosmart.com:9000/images/appicon/device/dengguang.png"),
	ALL_PARAMETERS("21","all_parameters", "全参传感器","http://www.lokosmart.com:9000/images/appicon/device/duocan.png");


	public String type;
	public String name;
	public String nameEn;
	public String icon;

	CategoryTypeEnum(String type , String nameEn, String name, String icon) {
		this.type = type;
		this.nameEn = nameEn;
		this.name = name;
		this.icon = icon;
	}
	public String getName() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	public String getNameEn() {
		return nameEn;
	}

	public String getIcon() {
		return icon;
	}

	/**
	 * 根据type获取枚举对象
	 * @param type
	 * @return
	 */

	private static Map<String, CategoryTypeEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static CategoryTypeEnum getInstByType(String type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(CategoryTypeEnum.class){
				if(!isInit){
					map = new HashMap<String, CategoryTypeEnum>();
					for(CategoryTypeEnum enu : CategoryTypeEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		CategoryTypeEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
