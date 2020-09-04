package com.landleaf.homeauto.common.enums.category;


import java.util.HashMap;
import java.util.Map;

/**
 *品类类型
 * @author Jason
 */
public enum CategoryTypeEnum {

	MULTI_PARAM("1","multiParameter_sensor", "多参数传感器"),
	ALL_PARAMETERS("2","all_parameters", "全参传感器"),
	FORMALDEHYDE("3","formaldehyde_sensor", "甲醛传感器"),
	PM("4","PM2.5", "PM2.5"),
	HVAC("5","hvac", "暖通"),
	TEMPERATURE_PANEL("6","temperature_panel", "温控面板"),
	FRESH_AIR("7","fresh_air", "朗绿新风"),
	AIRCONDITIONER("8","airconditioner", "空调"),
	CURTAINS("9","roller_curtains", "窗帘"),
	ELECTRIC_METER("10","electric_meter", "电表"),
	LIGHT("11","light", "灯"),
	SECURITY_MAINFRAME("12","security_mainframe", "安防"),
	FLOOR_HEATER("13","floor_heater", "地暖"),
	BGM("14","bgm", "背景音乐"),
	VALVE("15","valve", "水阀煤气阀"),
	CAT_EYE("16","act_eye", "智能猫眼"),
	SLEEP_MONITORING("17","sleep_monitoring","睡眠监测");


	public String type;
	public String name;
	public String nameEn;

	CategoryTypeEnum(String type , String nameEn, String name) {
		this.type = type;
		this.nameEn = nameEn;
		this.name = name;
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
