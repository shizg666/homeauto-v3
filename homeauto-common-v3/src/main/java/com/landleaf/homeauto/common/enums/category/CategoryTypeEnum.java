package com.landleaf.homeauto.common.enums.category;


import java.util.HashMap;
import java.util.Map;

/**
 *品类类型
 * @author Jason
 */
public enum CategoryTypeEnum {

	MULTI_PARAM("1","multiParameter_sensor", "多参数传感器","1","传感器",3),
	ALL_PARAMETERS("2","all_parameters", "全参传感器","1","传感器",3),
	FORMALDEHYDE("3","formaldehyde_sensor", "甲醛传感器","1","传感器",3),
	PM("4","PM2.5", "PM2.5","1","传感器",3),
	HVAC("5","hvac", "暖通","4","主机",4),
	TEMPERATURE_PANEL("6","temperature_panel", "温控面板","3","面板",2),
	FRESH_AIR("7","fresh_air", "新风","8","新风机",8),
	AIRCONDITIONER("8","airconditioner", "空调","7","空调",7),
	CURTAINS("9","roller_curtains", "窗帘","10","窗帘",9),
	ELECTRIC_METER("10","electric_meter", "电表","12","其他设备",11),
	LIGHT("11","light", "灯","11","灯",10),
	SECURITY_MAINFRAME("12","security_mainframe", "安防","12","其他设备",11),
	FLOOR_HEATER("13","floor_heater", "地暖","12","其他设备",11),
	BGM("14","bgm", "背景音乐","12","其他设备",11),
	VALVE("15","valve", "水阀煤气阀","12","其他设备",11),
	CAT_EYE("16","act_eye", "智能猫眼","12","其他设备",11),
	SLEEP_MONITORING("17","sleep_monitoring","睡眠监测","12","其他设备",11),
	CALORIMETER("18","calorimeter","热量表","12","其他设备",11),
	PLC_HYDRAULIC_CONTROL("19","plc_hydraulic_control","plc控水力模块","6","水利模块",6),
	GLV_HVAC("20","glv_hvac","glv暖通","4","主机",4),
	GLC_FRESH_AIR("21","glc_fresh_air","glc新风","8","新风机",8),
	DOUBLECOLD_FRESH_AIR("22","doublecold_fresh_air","双冷源新风","8","新风机",8),
	SANHENG_GLV_HVAC("23","sanheng_glv_hvac","户式三恒暖通","12","其他设备",11),
	PLC_CONTROL_MODEL("24","control_model","控制模块","5","PLC",5),
	SCREEN("100","screen","全屋信息屏","2","大屏",1);


	public String type;
	public String name;
	public String nameEn;
	public String parentCode;
	public String parentName;
	public Integer parentSort;

	CategoryTypeEnum(String type , String nameEn, String name,String parentCode,String parentName,Integer parentSort) {
		this.type = type;
		this.nameEn = nameEn;
		this.name = name;
		this.parentCode = parentCode;
		this.parentName = parentName;
		this.parentSort = parentSort;
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
	private static Map<String, String> parentMap = null;
	private static Map<String, Integer> parentSortMap = null;
	private static boolean isInit = false;
	private static boolean isInitParent = false;
	private static boolean isInitParentSort = false;
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

	public static String getParentNameByParentType(String type){
		if(type==null){
			return "-";
		}
		if(!isInitParent){
			synchronized(CategoryTypeEnum.class){
				if(!isInitParent){
					parentMap = new HashMap<String, String>();
					for(CategoryTypeEnum enu : CategoryTypeEnum.values()){
						parentMap.put(enu.getParentCode(), enu.getParentName());
					}
				}
				isInitParent = true;
			}
		}
		return parentMap.get(type);
	}


    public static Integer getParentSortByParentType(String type) {
        if(type==null){
            return 0;
        }
        if(!isInitParentSort){
            synchronized(CategoryTypeEnum.class){
                if(!isInitParentSort){
                    parentSortMap = new HashMap<String, Integer>();
                    for(CategoryTypeEnum enu : CategoryTypeEnum.values()){
                        parentSortMap.put(enu.getParentCode(), enu.getParentSort());
                    }
                }
                isInitParentSort = true;
            }
        }
        return parentSortMap.get(type);
    }

	public String getParentCode() {
		return parentCode;
	}

	public String getParentName() {
		return parentName;
	}

    public Integer getParentSort() {
        return parentSort;
    }
}


