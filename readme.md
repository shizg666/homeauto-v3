# 项目结构说明

* homeauto-center-gateway 网关：分发路由中心
* homeauto-center-oauth 认证中心
* homeauto-center-file 文件上传中心
* homeauto-center-device 设备中心
* homeauto-center-adapter 适配器
* homeauto-contact-gateway 双网口网关连接端
* homeauto-contact-screen 大屏连接端
* homeauto-common 公共


## homeauto-contact-gateway
* 路由转发
* 登录认证  
除白名单以外请求，进行登录认证校验，校验access_token,该token为jwt形式token,由认证中心生成，生成同时在redis中存储相应信息以便后期使用。
* 权限校验  
权限由各服务配置收集，或事先在数据库中配置，该处通过自定义permissionService校验