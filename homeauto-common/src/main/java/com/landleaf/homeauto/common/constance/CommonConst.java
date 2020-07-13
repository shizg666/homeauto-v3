package com.landleaf.homeauto.common.constance;

/**
 * 定义通用的常量
 * 
 * @author hebin
 */
public interface CommonConst {


	String CN = "CN";

	/**
	 * get方法的起始
	 */
	String GET_METHOD_START = "get";

	/**
	 * 判断是否为数字
	 */
	String NUMBER_PATTERN = "-?[0-9]+";

	/**
	 * 登出地址
	 */
	public static final String AUTH_LOGOUT = "/logout";
	/**
	 * 白名单
	 */
	public static final String AUTH_EXCLUDED_PATHS = "EXCLUDED_PATHS";
	/**
	 * refresh-token 失效时间
	 */
	public static final String REFRESH_TOKEN_EXPIRE = "refresh_token_expire";

	/**
	 * 客户端调用凭证参数传递名称
	 */
	public static final String TOKEN = "token";

	/**
	 * 客户端调用凭证header名称
	 */
	public static final String AUTHORIZATION = "Authorization";
	/**
	 * 内部服务凭证(feign调用透传token)header名称
	 */
	public static final String AUTHORIZATION_INNER = "Authorization_inner";

	/**
	 * jwt 中额外用户信息key
	 */
	String TOKEN_ADDITION_MSG_KEY = "user_info";
	/**
	 * 远程调用设备相关信息
	 */
	String REMOTE_HOST_DETAIL = "Remote_Host_Detail";

	/**
	 * 读取设备状态
	 */
	String COMMAND_TYPE_READ = "read";
	
	/**
	 * 向设备写入
	 */
	String COMMAND_TYPE_WRITE = "write";

	/**
	 * 当前生效环境
	 */
	 String DEV = "dev";
	/**
	 * 当前生效环境
	 */
	 String WYL = "wyl";
	/**
	 * 通配符等级定义
	 * 
	 * @author hebin
	 */
	interface WildcardConst {
		/*
		 * 不包含统配符
		 */
		int LEVEL_WITHOUT = 0;

		/*
		 * 通配符匹配到其中的某一层，即使用+通配符
		 */
		int LEVEL_WITH_RANK = 1;

		/**
		 * 匹配任何,即使用#通配符
		 */
		int LEVEL_WITH_ANY = 2;
	}

	interface NumberConst {
		int INT_ZERO = 0;

		int INT_ONE = 1;

		int INT_TWO = 2;

		int INT_THREE = 3;

		int INT_FOUR = 4;
		
		/**
		 * int的true的表示
		 */
		int INT_TRUE = 1;
		
		/**
		 * int的false的表示
		 */
		int INT_FALSE = 0;
		
		/**
		 * 不同于成功失败的通用的状态码
		 */
		int INT_RESULT_OTHER = 9;
	}


	/**
	 * 标点符号
	 */
	interface SymbolConst {
		String EMPTY = "";
		String COMMA = ",";
		String SPOT = ".";
		String COLON = ":";
		String SPACE = " ";
		String SLASH = "/";
		String VERTICAL = "|";

		String UNDER_LINE = "_";
		String SHORT_LINE = "-";

		String PER_CENT = "%";
		String AT = "@";

		String LEFT_MIDDLE_BRACKETS = "[";
		String RIGHT_MIDDLE_BRACKETS = "]";

		String LEFT_SMALL_BRACKETS = "(";
		String RIGHT_SMALL_BRACKETS = ")";


		String EQUAL = "=";
	}
	
	/**
	 * httpmethod的int的枚举
	 * 
	 * @author hebin
	 */
	interface HttpMethod {
		/**
		 * method为get
		 */
		int METHOD_GET = 0;
		
		/**
		 * method为post
		 */
		int METHOD_POST = 1;
	}

	interface SpecialStr{
		String NULL = "null";
	}
	
	/**
	 * http状态码
	 * 
	 * @author hebin
	 */
	interface HttpStatusCode {
		/**
		 * http返回成功
		 */
		int SUCCESS = 200;
	}
	
	/**
	 * 请求来源定义
	 * 
	 * @author hebin
	 */
	interface CommandSource {
		/**
		 * app端的请求
		 */
		String SOURCE_APP = "app";
		
		/**
		 * system端的请求
		 */
		String SOURCE_SYSTEM = "system";
	}
}
