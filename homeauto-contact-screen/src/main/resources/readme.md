# 项目说明
> 该项目用于通过mqtt协议对接大屏（网关）交互
> 区别标记：topic/namespace 两者都可以作为区分标记，为方便大屏端，使用topic标记，而后端可以使用namespace作为唯一区分及业务逻辑处理标记。
> 
# 云端与大屏(网关)端

## 协议列表

<table>
    <tr>
        <td>分类</td>
        <td>协议名称</td>
        <td>请求地址</td>
        <td>发布端</td>
        <td>消费端</td>
    </tr>
    <tr>
        <td rowspan="1">初始认证</td>
        <td>获取mqtt连接信息</td>
        <td>http://${域名}:10013/homeauto-center-device/screen/mqtt/link/information</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td rowspan="2">上线离线状态</td>
        <td>大屏上下线</td>
        <td>http://${域名}:10013/homeauto-center-device/device/callback/mqtt/web_hook ）</td>
        <td>mqtt集群</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>设备上下线</td>
        <td>/screen/service/online/device/status/{家庭编码}/{设备号}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td rowspan="6">控制消息</td>
        <td>设备写入</td>
        <td>/screen/service/control/property/set/{家庭编码}/{设备号} </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
    <tr>
        <td>设备写入响应</td>
        <td> /screen/service/control/property/set/{家庭编码}/{设备号}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>读取状态</td>
        <td>/screen/service/control/property/status/read/{家庭编码}/{设备号} </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
    <tr>
        <td>读取状态响应</td>
        <td>/screen/service/control/property/status/read/{家庭编码}/{设备号}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
       <tr>
        <td>控制场景</td>
        <td>/screen/service/control/scene/set/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
    <tr>
        <td>控制场景响应</td>
        <td>/screen/service/control/scene/set/reply/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
     <tr>
        <td rowspan="20">通知消息</td>
        <td>设备状态更新</td>
        <td>/screen/service/notice/property/status/update/{家庭编码} </td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>设备状态更新响应</td>
        <td>/screen/service/notice/property/status/update/reply/{家庭编码} </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
    <tr>
        <td>楼层信息更新</td>
        <td>/screen/service/notice/floor/update/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
    <tr>
        <td>楼层信息更新响应</td>
        <td>/screen/service/notice/floor/update/reply/{家庭编码}</td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
       <tr>
        <td>房间信息更新</td>
        <td>/screen/service/notice/room/update/{家庭编码}</td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
    <tr>
        <td>房间信息更新响应</td>
        <td>/screen/service/notice/room/update/reply/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
     <tr>
        <td>房间设备关联信息更新</td>
        <td>/screen/service/notice/room/device/relation/update/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
    <tr>
        <td>房间设备关联信息更响应</td>
        <td>/screen/service/noticee/room/device/relation/update/reply/{家庭编码}</td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
    <tr>
        <td>设备信息更新</td>
        <td>/screen/service/notice/device/Info/update/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
       <tr>
        <td>设备信息更新响应</td>
        <td>/screen/service/notice/device/Info/update/reply/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
    <tr>
        <td>场景更新</td>
        <td>/screen/service/notice/scene/update/{家庭编码} </td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
      <tr>
        <td>场景更新响应</td>
        <td>/screen/service/notice/scene/update/reply/{家庭编码}</td>
        <td>云端/大屏</td>
        <td>大屏/云端</td>
    </tr>
      <tr>
        <td>消息公告更新</td>
        <td>/screen/service/notice/news/update/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
       <tr>
        <td>消息公告更新响应</td>
        <td>/screen/service/notice/news/update/reply/{家庭编码} </td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>大屏日志更新</td>
        <td>/screen/service/notice/screen/logs/update/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
      <tr>
        <td>大屏日志更新响应</td>
        <td>/screen/service/notice/screen/logs/update/reply/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
        <tr>
        <td>产品信息更新</td>
        <td>/screen/service/notice/screen/product/update/reply/{家庭编码} </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
      <tr>
        <td>产品信息更新响应</td>
        <td>/screen/service/notice/product/update/reply/{家庭编码} </td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
        <tr>
        <td>场景定时配置更新</td>
        <td>/screen/service/notice/scene/timing/config/update/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
      <tr>
        <td>场景定时配置更新响应</td>
        <td>/screen/service/notice/scene/timing/config/update/reply/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
     <tr>
        <td rowspan="2">事件消息</td>
        <td>报警信息上报</td>
        <td>/screen/service/event/alarm/upload/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>报警信息上报响应</td>
        <td>/screen/service/event/alarm/upload/reply/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
        <tr>
        <td rowspan="4">查询消息</td>
        <td>查询天气</td>
        <td>/screen/service/event/alarm/upload/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>查询天气响应</td>
        <td>/screen/service/event/request/weather/reply/{家庭编码}  </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
      <tr>
        <td>查询时间</td>
        <td>/screen/service/event/request/time/{家庭编码} </td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
      <tr>
        <td>查询时间响应</td>
        <td>/screen/service/event/request/time/reply/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
</table>


## 状态码

表 1. 结果状态码
code	 |message	 |说明
---|---|---
200	 |success	 |请求成功。
400	 |request parameter error |	请求参数错误
429	 |too many requests |	请求过于频繁。
500  |request error	 |内部服务错误， 处理时发生内部错误。
...	 |... |	未完待续

## 公共参数
> 适用于topic协议请求 

参数 | 类型|说明
---|---|---
namespace |String|	请求类别。  
name	|对象	|请求名称。
messageId	|String	|消息追溯ID。
payloadVersion	|String	|固定值1。
familyCode	|String	|家庭编码。

## 协议详情


### 初始认证
#### 获取Mqtt连接信息
大屏通过该接口获取mqtt连接信息，凭证为家庭编码及mac地址 

**地址**：http://${域名}:10013/homeauto-center-device/screen/mqtt/link/information 

**请求方式**：POST|GET  

**参数**:

Key|	类型 | 说明
---|---|---
familyCode   | string	|家庭编码
screenMac | string	|大屏mac
**响应**:

Key|	类型 | 说明
---|---|---
url   | string	|连接地址
port   | string	|端口
userName | string	|用户名
password | string	|密码
caCrt | string	|ca证书

### 上线离线状态
#### 大屏上下线通知
通过在mqtt服务中配置钩子回调函数，监听大屏连接与断开事件后回调到云端服务，以此更新大屏上下线状态  

**回调地址**：http://${域名}:10013/homeauto-center-device/device//callback/mqtt/web_hook  
**请求方式**：POST|GET  

**参数**:

Key|	类型 | 说明
---|---|---
action   | string	|事件名称 固定为："client_connected" 或者client_disconnected
client_id | string	|客户端 ClientId
username  | 	string	|客户端 Username，不存在时该值为 "undefined"


### 设备上下线状态

**描述**：大屏通过该Topic上传设备的上下线状态。

**数据流向**：大屏->云端  

**Topic**：/screen/service/online/device/status/{家庭编码}/{设备号}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Device.Online.Status",
        "name": "DeviceOnlineStatus",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "status":"online|offline",
        "deviceSn":"deviceName1234",
        "time":"2018-08-31 15:32:28.205"
    }
}
```
**Payload**
参数 | 类型|说明
---|---|---
status	|String	|设备状态。online：上线。offline：离线。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
time|	String|	发送通知的时间点。

## 控制消息

### 设备写入

**描述**：云端通过该Topic对设备下达写入指令。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/online/device/status/{家庭编码}/{设备号}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Device.Write",
        "name": "DeviceWrite",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "deviceSn":"deviceName1234",
        "items":{
             "attributeCode":"attributeValue"
            }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
productCode|	String|	设备所属产品编码。
items	|Object	|写入数据
attributeCode	|String	|设备属性 见产品属性表
attributeValue  |Object	|设备属性值 数据类型及可选值见产品属性表



### 设备写入指令结果

**描述**：大屏通过该Topic异步方式返回云端写入指令结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/control/property/set/{家庭编码}/{设备号}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Device.Write.reply",
        "name": "DeviceWriteReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "deviceSn":"deviceName1234",
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


### 读取状态

**描述**：云端通过该Topic读取设备状态信息。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/control/property/status/read/{家庭编码}/{设备号}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Device.Status.Read",
        "name": "DeviceStatusRead",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "deviceSn":"deviceName1234",
        "productCode":"1",
        "items":{
             "attributeCode":"attributeValue"
            }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
productCode|	String|	设备所属产品编码。
items	|Object	|读取的设备状态数据
attributeCode	|String	|设备属性 见产品属性表
attributeValue  |Object	|设备属性值 数据类型及可选值见产品属性表



### 读取状态响应

**描述**：大屏通过该Topic异步方式返回云端设备状态读取指令结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：screen/service/control/property/status/read/{家庭编码}/{设备号}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Device.Status.Read.reply",
        "name": "DeviceStatusReadReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "deviceSn":"deviceName1234",
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果



### 控制场景

**描述**：云端通过该Topic执行场景。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/control/scene/set/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Set",
        "name": "FamilySceneSet",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "sceneId":"1234567*****"
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|产生时间。
familyCode |String|	家庭编码。  
sceneId	|String	|场景ID，云端与大屏端统一



### 控制场景响应

**描述**：大屏通过该Topic异步方式返回云端场景执行结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/control/scene/set/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Set.Reply",
        "name": "FamilySceneSetReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "sceneId":"1234567*****"
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
sceneId|	String|	执行的场景。
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


## 通知消息

### 设备状态更新

**描述**：大屏通过该Topic上报的设备属性状态。

**数据流向**：大屏->云端 

**Topic**：/screen/service/notice/property/status/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Device.Status.Update",
        "name": "DeviceStatusUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "time":1510799670074,
        "productCode":"10",
        "items":{
            "attributeCode":"attributeValue"
        }
    }

    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
productCode|String	|设备所属产品编码。
time	|Long|	数据流转消息产生时间，即上报时间。
items	|Object|	设备属性数据。
attributeCode	|String	|属性名称。见产品属性表
attributeValue	|String	|属性值 见产品属性表。


### 设备状态更新响应

**描述**：云端通过该Topic异步方式返回大屏设备状态更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：云端->大屏

**Topic**：/screen/service/notice/property/status/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Device.Status.Update.Reply",
        "name": "DeviceStatusUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "deviceSn":"1*****"
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果



### 楼层信息更新

**描述**：通过该Topic同步更新楼层数据。

**数据流向**：云端 <-> 大屏

**Topic**：/screen/service/notice/floor/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Floor.Update",
        "name": "FamilyFloorUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue"
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值


### 楼层信息更新响应

**描述**：通过该Topic异步方式返回楼层更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏<->云端

**Topic**：/screen/service/notice/floor/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Floor.Update.Reply",
        "name": "FamilyFloorUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


### 房间信息更新

**描述**：通过该Topic同步更新房间数据。

**数据流向**：云端 <-> 大屏

**Topic**：/screen/service/notice/room/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Room.Update",
        "name": "FamilyRoomUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue"
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值


### 房间信息更新响应

**描述**：通过该Topic异步方式返回房间更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏<->云端

**Topic**：/screen/service/notice/room/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Romm.Update.Reply",
        "name": "FamilyRommUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果



### 房间设备关联信息更新

**描述**：通过该Topic同步更新房间设备关联数据。

**数据流向**：云端 <-> 大屏

**Topic**：/screen/service/notice/room/device/relation/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Room.Device.Relation.Update",
        "name": "FamilyRoomDeviceRelationUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue"
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值


### 房间信息更新响应

**描述**：通过该Topic异步方式返回房间设备关联更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏<->云端

**Topic**：/screen/service/noticee/room/device/relation/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Room.Device.Relation.Update.Reply",
        "name": "FamilyRoomDeviceRelationUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


### 设备信息更新

**描述**：通过该Topic同步更新设备数据。

**数据流向**：云端 <-> 大屏

**Topic**：/screen/service/notice/device/Info/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Device.Info.Update",
        "name": "DeviceInfoUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue"
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|设备号
attributeValue	|String	|房间ID 


### 设备信息更新响应

**描述**：通过该Topic异步方式返回设备信息更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏<->云端

**Topic**：/screen/service/notice/device/Info/update/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Device.Info.Update.Reply",
        "name": "DeviceInfoUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


### 场景信息更新

**描述**：通过该Topic同步更新场景数据。

**数据流向**：云端 <-> 大屏

**Topic**：/screen/service/notice/device/Info/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Update",
        "name": "FamilySceneUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 


### 场景信息更新响应

**描述**：通过该Topic异步方式返回场景信息更新响应结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏<->云端

**Topic**：/screen/service/notice/scene/update/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Update.reply",
        "name": "FamilySceneUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果

### 消息公告信息更新

**描述**：通过该Topic同步更新消息公告数据。

**数据流向**：云端 -> 大屏

**Topic**：screen/service/notice/news/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.News.Update",
        "name": "FamilyNewsUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 


### 消息公告信息更新响应

**描述**：通过该Topic异步方式返回消息公告信息更新响应结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/notice/news/update/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.News.Update.reply",
        "name": "FamilyNewsUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果

### 大屏日志信息更新

**描述**：通过该Topic同步更新消息大屏日志数据。

**数据流向**： 大屏 -> 云端

**Topic**：/screen/service/notice/screen/logs/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Screen.Logs.Update",
        "name": "FamilyScreenLogsUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 


### 大屏日志信息更新响应

**描述**：通过该Topic异步方式返回大屏日志信息更新响应结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/notice/screen/logs/update/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Screen.Logs.Update.Reply",
        "name": "FamilyScreenLogsUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


### 产品信息更新

**描述**：通过该Topic同步更新产品信息（包含设备及属性）数据。

**数据流向**： 大屏 -> 云端

**Topic**：/screen/service/notice/screen/product/update/reply/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Product.Update",
        "name": "FamilyProductUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 


### 产品信息更新响应

**描述**：通过该Topic异步方式返回产品信息更新响应结果信息。如果过程中出现错误，在data中返回。

**数据流向**：云端 -> 大屏

**Topic**：/screen/service/notice/product/update/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Product.Update.Reply",
        "name": "FamilyProductUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果



### 场景定时配置信息更新

**描述**：通过该Topic同步更新场景定时配置信息数据。

**数据流向**： 大屏 <-> 云端

**Topic**：/screen/service/notice/scene/timing/config/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Timing.Config.Update",
        "name": "FamilySceneTimingConfigUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510799670074,
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
time	|Long|	数据流转消息产生时间，即上报时间。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 


### 场景定时配置信息更新响应

**描述**：通过该Topic异步方式返回场景定时配置信息更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：云端 -> 大屏

**Topic**：/screen/service/notice/scene/timing/config/update/{家庭编码} 

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Scene.Timing.Config.Update",
        "name": "FamilySceneTimingConfigUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果


## 事件消息

### 报警信息上报
**描述**：通过该Topic同步上报报警信息数据。

**数据流向**： 大屏 -> 云端

**Topic**：/screen/service/event/alarm/upload/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Alarm.Upload",
        "name": "FamilyDeviceAlarmUpload",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"1号设备报警",
        "deviceSn":"5gJtxDVeGAkaEztpisjX",
        "items":[
            {
                 "attributeCode":"attributeValue",
                 "...":"..."
            }
        ]
        "time":1510799670074
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
items	|数组|	更新数据数组。
attributeCode	|String	|属性名
attributeValue	|Object	|属性值 
time	|Long	|事件产生时间。

### 报警信息上报响应

**描述**：通过该Topic异步方式返回报警信息上报结果信息。如果过程中出现错误，在data中返回。

**数据流向**：云端 -> 大屏

**Topic**：/screen/service/event/alarm/upload/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Alarm.Upload.Reply",
        "name": "FamilyDeviceAlarmUploadReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "time":1510292739881,
        "code":200,
        "message":"success",
        "data":{
    
        }
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。
data	|Object	|设备返回的结果

## 查询消息
### 查询天气

**描述**：通过该Topic请求天气信息。

**数据流向**：大屏 -> 云端

**Topic**：/screen/service/event/alarm/upload/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Request.Weather",
        "name": "FamilyRequestWeather",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"请求天气/时间",
        "items":
           {
               "attributeCode":"attributeValue"
           }
        
        "time":1510799670074
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
items	|对象	|入参。
attributeCode	|String	|请求入参属性。
attributeValue	|Object	|请求入参属性值。
time	|Long	|事件产生时间。


### 查询天气响应
**描述**：通过该Topic返回天气信息。

**数据流向**：云端 -> 大屏

**Topic**：/screen/service/event/request/weather/reply/{家庭编码}
**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Request.Weather",
        "name": "FamilyRequestWeather",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"请求天气/时间",
        "items":
           {
               "attributeCode":"attributeValue"
           }
        
        "time":1510799670074
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
items	|对象	|入参。
attributeCode	|String	|请求入参属性。
attributeValue	|Object	|请求入参属性值。
time	|Long	|事件产生时间。

### 查询时间

**描述**：通过该Topic请求时间信息。

**数据流向**：大屏 -> 云端

**Topic**：/screen/service/event/request/weather/reply/{家庭编码}
**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Family.Request.Time",
        "name": "FamilyRequestTime",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"请求天气/时间",
        "items":
           {
               "attributeCode":"attributeValue"
           }
        
        "time":1510799670074
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
items	|对象	|入参。
attributeCode	|String	|请求入参属性。
attributeValue	|Object	|请求入参属性值。
time	|Long	|事件产生时间。


### 查询时间响应
**描述**：通过该Topic返回时间信息。

**数据流向**：云端 -> 大屏

**Topic**：/screen/service/event/request/time/reply/{家庭编码}
**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Family.Request.Time.Reply",
        "name": "FamilyRequestTimeReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"请求天气/时间",
        "items":
           {
               "attributeCode":"attributeValue"
           }
        
        "time":1510799670074
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
items	|对象	|入参。
attributeCode	|String	|请求入参属性。
attributeValue	|Object	|请求入参属性值。
time	|Long	|事件产生时间。

