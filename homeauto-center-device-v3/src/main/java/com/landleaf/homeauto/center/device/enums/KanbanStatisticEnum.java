package com.landleaf.homeauto.center.device.enums;

/**
 * @description:看板统计
 */
public enum KanbanStatisticEnum {

    /**
     * 请求
     */
    FAMILY("FAMILY", "住户统计", "com.landleaf.homeauto.center.device.service.mybatis.impl.IKanBanServiceImpl", "familyStatistic", new Class[]{}),
    DEVICE_TOTAL("DEVICE_TOTAL", "设备总数", "com.landleaf.homeauto.center.device.service.mybatis.impl.IKanBanServiceImpl","deviceCountStatistic", new Class[]{}),
    DEVICE_ERROR("DEVICE_ERROR","设备故障", "com.landleaf.homeauto.center.device.service.mybatis.impl.IKanBanServiceImpl", "fanCoilStatus", new Class[]{}),
    MAINTENANCE("MAINTENANCE","维保", "com.landleaf.ibsaas.screen.service.LargeScreenService", "achpDetailStatus", new Class[]{}),
    PANEL_TEMP("WEATHER","温控面板", "com.landleaf.ibsaas.screen.service.LargeScreenService", "weatherStatus", new Class[]{}),
    AIRCONDITIONER("AIR_CONDITION","空调", "com.landleaf.ibsaas.screen.service.LargeScreenService", "energyStatus2", new Class[]{}),
    HOST("HOST","主机", "com.landleaf.ibsaas.screen.service.LargeScreenService", "getCarbonDataShow", new Class[]{}),
    FRESH_AIR("MEETING","新风机", "com.landleaf.ibsaas.screen.service.LargeScreenService", "meetingStatus", new Class[]{}),
    ;

    private String type;

    private String description;

    private String executeClassPath;

    private String methodName;

    private Class[] params;

    KanbanStatisticEnum(String type, String description, String executeClassPath, String methodName, Class[] params) {
        this.type = type;
        this.description = description;
        this.executeClassPath = executeClassPath;
        this.params = params;
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecuteClassPath() {
        return executeClassPath;
    }

    public void setExecuteClassPath(String executeClassPath) {
        this.executeClassPath = executeClassPath;
    }

    public Class[] getParams() {
        return params;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
