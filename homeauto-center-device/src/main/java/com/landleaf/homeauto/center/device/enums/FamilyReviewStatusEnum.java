package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 家庭审核状态
 */
public enum FamilyReviewStatusEnum {
	UNREVIEW(0, "未审核"),
	REVIEW(1,"已审核"),
	AUTHORIZATION(2,"已授权");



	public Integer type;
	public String name;

	FamilyReviewStatusEnum(Integer type, String name) {
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

	private static Map<Integer, FamilyReviewStatusEnum> map = null; // type, enum映射
	private static boolean isInit = false;
	public static FamilyReviewStatusEnum getInstByType(Integer type){
		if(type==null){
			return null;
		}
		if(!isInit){
			synchronized(FamilyReviewStatusEnum.class){
				if(!isInit){
					map = new HashMap<Integer, FamilyReviewStatusEnum>();
					for(FamilyReviewStatusEnum enu : FamilyReviewStatusEnum.values()){
						map.put(enu.getType(), enu);
					}
				}
				isInit = true;
			}

		}
		FamilyReviewStatusEnum pojoEnum = map.get(type);
		return pojoEnum;
	}
}
