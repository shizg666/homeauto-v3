package com.landleaf.homeauto.common.constance;

/**
 * 定义redis缓存key名称
 * 
 */
public interface RedisCacheConst {

    /**
     * 用户token  user_token:user_type:user_id
     */
    String USER_TOKEN = "user_token:%s:%s";

    /**
     * 用户token存储前缀
     */
    String KEY_PRE_TOKEN = "user_token:";

    /**
     * 权限存储根据类型
     */
    String PERMISSION_BY_TYPE = "permission_by_type:%s";
    /**
     * 权限存储根据类型key前缀
     */
    String PERMISSION_BY_TYPE_PRE = "permission_by_type";
    /**
     * web端用户菜单列表key
     */
    String USER_PERMISSIONS_MENU_PROVIDER_KEY_PRE = "user_permissions_menu";
    /**
     * web端用户菜单列表key:userId:permissionType
     */
    String USER_PERMISSIONS_MENU_PROVIDER_KEY = "user_permissions_menu:%s:%s";


    /**
     * 用户信息缓存集合key
     */
    String KEY_USER_INFO = "key_user_info";

    /**
     * 客户信息缓存集合key
     */
    String KEY_CUSTOMER_INFO = "key_customer_info";

    /**
     * 客户信息缓存集合key
     */
    String KEY_WECHAT_CUSTOMER_INFO = "key_wechat_customer_info";


    /**
     * 行政区key
     */
    String KEY_AREA_INFO = "key_area_info";


    /**
	 * token  令牌发放秘钥
	 */
	String KEY_TOKEN_KEY = "user_token_key";


	/**
	 * 更新token失效时长lock
	 */
	String UPDATE_TOKEN_EXPIRE_LOCK = "update_token_expire_lock";
	/**
	 * apk升级失效时长lock
	 */
	String UPDATE_APK_EXPIRE_LOCK = "update_apk_expire_lock";
	/**
	 * 用户token最大数量
	 */
	int MaxToken = 20;







    /**
     * 用户权限信息缓存集合key
     */
    String KEY_SYS_PERMISSION = "key_sys_permission";
    /**
     * 用户角色信息缓存集合key
     */
    String KEY_SYS_ROLE = "key_sys_role";
    /**
     * 用户角色关联信息缓存集合key
     */
    String KEY_USER_ROLE = "key_user_role";
    /**
     * 用户角色关联权限信息缓存集合key
     */
    String KEY_ROLE_PERMISSION = "key_role_permission";
    /**
     * 用户权限范围缓存集合key
     */
    String KEY_USER_PERMISSION_SCOP = "key_user_permission_scop";

    /**
     * dueros与至慧管家信息缓存集合key
     */
    String KEY_DUEROS_APPLICTION_ACTION = "key_dueros_appliction_action";




    /**
     * 缓存失效时间
     */
    Long COMMON_EXPIRE = 30*60L;
    /**
     * 短缓存失效时间
     */
    Long SHORT_COMMON_EXPIRE = 10L;

    /**
     * 设备关联城市信息
     */
    String TABLE_DEVICE_CITY = "TABLE_DEVICE_CITY";

    /**
     * 天气信息
     */
    String TABLE_CITY_WEATHER = "TABLE_CITY_WEATHER";

    /**
     * 城市信息
     */
    String TABLE_CITY_CODE = "TABLE_CITY_CODE";
    /**
     * 工程天气信息
     */
    String TABLE_PROJECT_WEATHER = "table_project_weather";

    /**
     * 任务的lock的前缀
     */
    String TASK_LOCK_PRE = "task_lock_";
    
    /**
     * 任务操作的lock的前缀
     */
    String TASK_OPERATOR_LOCK_PRE = "task_operator_lock_";
    
    /**
     * 与网关交互时msgid的自增的key值
     */
    String GATEWAY_MESSAGE_ID_INCR = "gateway_message_id";
    
    /**
     * 与大屏交互的消息的redis存储的key的前缀
     */
    String SCREEN_MESSAGE_PREFIX = "screen_message_";
    
    /**
     * 设备状态写操作的锁
     */
    String DEVICE_STATUS_CHANGE_LOCK_PRE = "device_status_change_lock_";
    
    /**
     * 设备单个条目状态写操作的锁
     */
    String DEVICE_ITEM_STATUS_CHANGE_LOCK_PRE = "device_item_status_change_lock_";


    /**
     * 工程锁定后信息缓存锁
     */
    String PROJECT_FRDA_LOCK_PREFIX = "project_frda_lock_";


    /**
     * 工程楼层房间设备属性信息
     */
    String PROJECT_FRDA_INFO = "project_frda_info";
    
    /**
     * 场景操作前缀
     */
    String SCENE_CHOOSE_CACHE_PREFIX = "scene_choose_cache_";
    
    /**
     * 设备写操作前缀
     */
    String DEVICE_WRITE_CACHE_PREFIX = "device_write_cache_";
    
    /**
     * 异步处理组件同步锁前缀
     */
    String ASYN_SYNC_LOCK_PREFIX = "asyn_sync_lock_";
    
    /**
     * 异步处理组件NOTICE_CHANGE同步锁前缀
     */
    String ASYN_SYNC_LOCK_NOTICE_CHANGE_PREFIX = "asyn_sync_lock_6_";

    /**
     * 异步处理组件NOTICE_CHANGE_RESP同步锁前缀
     */
    String ASYN_SYNC_LOCK_NOTICE_CHANGE_RESP_PREFIX = "asyn_sync_lock_7_";
    
    /**
     * 异步处理组件NOTICE同步锁前缀
     */
    String ASYN_SYNC_LOCK_NOTICE_PREFIX = "asyn_sync_lock_8_";
    
    /**
     * 异步处理组件PROJECT同步锁前缀
     */
    String ASYN_SYNC_LOCK_PROJECT_PREFIX = "asyn_sync_lock_9_";
    
    /**
     * 异步处理组件ROOM_CHANGE同步锁前缀
     */
    String ASYN_SYNC_LOCK_ROOM_CHANGE_PREFIX = "asyn_sync_lock_10_";

    /**
     * 异步处理组件ROOM_CHANGE RESP同步锁前缀
     */
    String ASYN_SYNC_LOCK_ROOM_CHANGE_RESP_PREFIX = "asyn_sync_lock_11_";
    
    /**
     * 异步处理组件ROOM同步锁前缀
     */
    String ASYN_SYNC_LOCK_ROOM_PREFIX = "asyn_sync_lock_12_";
    
    /**
     * 异步处理组件SCENE_CHANGE同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_CHANGE_PREFIX = "asyn_sync_lock_13_";
    
    /**
     * 异步处理组件SCENE_CHANGE_RESP同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_CHANGE_RESP_PREFIX = "asyn_sync_lock_14_";
    
    /**
     * 异步处理组件SCENE_CHOOSE_RESP同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_CHOOSE_RESP_PREFIX = "asyn_sync_lock_16_";
    
    /**
     * 异步处理组件SCENE_SYS_CHOOSE_RESP同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_SYS_CHOOSE_RESP_PREFIX = "asyn_sync_lock_18_";
    
    /**
     * 异步处理组件WEATHER同步锁前缀
     */
    String ASYN_SYNC_LOCK_WEATHER_PREFIX = "asyn_sync_lock_19_";
    
    /**
     * 异步处理组件SCENE同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_PREFIX = "asyn_sync_lock_17_";
    
    /**
     * 异步处理组件SCENE_CHOOSE同步锁前缀
     */
    String ASYN_SYNC_LOCK_SCENE_CHOOSE_PREFIX = "asyn_sync_lock_15_";
    
    /**
     * 异步处理组件GATEWAY STATUS同步锁前缀
     */
    String ASYN_SYNC_LOCK_GATEWAY_STATUS_PREFIX = "asyn_sync_lock_5_";
    
    /**
     * 异步处理组件DEVICE STATUS同步锁前缀
     */
    String ASYN_SYNC_LOCK_DEVICE_STATUS_PREFIX = "asyn_sync_lock_2_";
    
    /**
     * 异步处理组件DEVICE WRITE同步锁前缀
     */
    String ASYN_SYNC_LOCK_DEVICE_WRITE_PREFIX = "asyn_sync_lock_3_";
    
    /**
     * 异步处理组件DEVICE WRITE ACK同步锁前缀
     */
    String ASYN_SYNC_LOCK_DEVICE_WRITE_ACK_PREFIX = "asyn_sync_lock_4_";
    /**
     * 异步处理组件DEVICE WRITE ACK CHAIN同步锁前缀
     */
    String ASYN_SYNC_LOCK_DEVICE_WRITE_ACK_CHAIN_PREFIX = "asyn_sync_lock_20_";

    /**
     * 异步处理组件APK CAL同步锁前缀
     */
    String ASYN_SYNC_LOCK_APK_CALL_PREFIX = "asyn_sync_lock_1_";
    
    /**
     * 异步处理组件上线同步锁前缀
     */
    String ASYN_SYNC_ONLINE_LOCK_PREFIX = "asyn_sync_online_lock_";
    
    /**
     * 异步处理组件下线同步锁前缀
     */
    String ASYN_SYNC_OFFLINE_LOCK_PREFIX = "asyn_sync_offline_lock_";
    
    /**
     * adapter下线操作的锁
     */
    String OFFLINE_ADAPTER_LOCK = "offline_adapter_lock";
    
    /**
     * adapter在线信息
     */
    String OFFLINE_ADAPTER = "online_adapter";
    
    /**
     * adapter关联网关的key值
     */
    String ADAPTER_GATEWAY_RELIANCE = "adapter_gateway_reliance";
    
    /**
     * 网关的信息
     */
    String GATEWAY_RELIANCE = "gateway_reliance";

    /**
     * 当前工程
     */
    String CURRENT_PROJECT = "current_project";

    /**
     * 网关缓存信息
     */
    String GATEWAY_INFO = "gateway_info";


    /**
     * 安防报警下发消警命令标识
     */
    String SECURITY_MAINFRAME_ARMING_STATE_DISARM_KEY = "security_mainframe_arming_state_disarm_key";

    /**
     * 小区配置缓存信息
     */
    String ADDRESS_CONFIG = "address_config";


    /**
     * 根据用户手机号批量操作
     */
    String BATCH_OPERATION_FROM_APP_BY_PHONE_CACHE_PREFIX = "batch_operation_from_app_by_phone_cache:";
    /**
     * 批量操作条数
     */
    String BATCH_OPERATION_FROM_APP_BY_PHONE_CACHE_COUNT = "batch_operation_from_app_by_phone:";

    /**
     * 批量删除通知超时锁
     */
    String BATCH_OPERATION_FROM_APP_BY_PHONE_CACHE_LOCK = "batch_operation_from_app_by_phone_lock";

    /**
     * 一键撤防
     */
    String ONEKEY_DISARM_MSGID_MOBILE_CACHE = "onekey_disarm_msgid_mobile_cache:";
}
