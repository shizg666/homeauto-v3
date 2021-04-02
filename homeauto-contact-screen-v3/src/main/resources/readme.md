# 用于处理所有大屏相关交互
该项目只做中转，不作具体业务处理，具体业务放在adpater进行处理、分发
* 接收大屏http/https请求
> 配置请求，查询请求，事件上报
* 消费大屏发布的消息mqtt
> 控制响应、状态上报、数据更新通知响应
* 消费云端产生的消息rocketmq
> app控制，场景控制，读取状态，更改通知
# 云端与大屏(网关）端

## 协议列表

<table>
    <tr>
        <td>协议类型</td>
        <td>分类</td>
        <td>协议名称</td>
        <td>请求地址</td>
        <td>请求方</td>
        <td>响应方</td>
    </tr>
    <tr>
        <td rowspan="10">Mqtt</td>
        <td rowspan="6">控制消息</td>
        <td>设备写入</td>
        <td>/screen/service/control/property/set/{家庭编码}/{设备号}</td>
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
        <td rowspan="4">通知消息</td>
        <td>数据更新通知</td>
        <td>/screen/service/notice/config/update/{家庭编码}</td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>数据更新通知新响应</td>
        <td>/screen/service/notice/config/update/reply/{家庭编码} </td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
    <tr>
        <td>设备状态更新通知</td>
        <td>/screen/service/notice/property/status/update/{家庭编码} </td>
        <td>大屏</td>
        <td>云端</td>
    </tr>
    <tr>
        <td>设备状态更新通知响应</td>
        <td>/screen/service/notice/property/status/update/reply/{家庭编码}</td>
        <td>云端</td>
        <td>大屏</td>
    </tr>
       <tr>
        <td rowspan="13">Http/Https</td>
        <td rowspan="8">配置信息请求</td>
        <td>楼层信息请求</td>
        <td rowspan="13">http://${域名}:${端口}/homeauto-center-device/callback/screen</td>
        <td rowspan="10">大屏</td>
        <td rowspan="10">云端</td>
    </tr>
     <tr>
        <td>房间信息请求</td>
    </tr>
    <tr>
        <td>设备信息请求</td>
    </tr>
      <tr>
        <td>消息公告更新</td>
    </tr>
    <tr>
        <td>产品信息请求</td>
    </tr>
    <tr>
        <td>场景信息请求</td>
    </tr>
     <tr>
        <td>定时场景信息请求</td>
    </tr>
     <tr>
        <td>智能场景信息请求</td>
    </tr>
        <tr>
        <td rowspan="2">查询请求</td>
        <td>查询天气</td>
    </tr>
      <tr>
        <td>查询时间</td>
    </tr>
     <tr>
        <td rowspan="3">事件消息</td>
        <td>报警信息上报</td>
        <td rowspan="3">大屏</td>
        <td rowspan="3">云端</td>
    </tr>
    <tr>
        <td>大屏日志推送</td>
    </tr>
    <tr>
        <td>报警信息上报</td>
    </tr>

</table>

# 协议说明
* 控制消息
> 控制消息包含控制命令下发、控制场景下发、状态读取及相应响应采用mqtt协议交互
* 通知消息
> 云端更改相应楼层、房间、设备、场景信息后，通过mqtt通知大屏端，大屏收到通知消息后响应
* 查询消息
> 大屏端收到通知消息或其它需请求信息或需主动告知云端消息，通过http/https主动请求云端服务器，获取消息，备注为全量请求。

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

参数 | 类型|说明
---|---|---
namespace |String|	消息类型。  
name	|String	|具体消息类型。
messageId	|String	|消息追溯ID。
payloadVersion	|String	|固定值1。
familyCode	|String	|家庭编码。
time	|Long	|请求时间。

## 协议详情

Mqtt协议
------

## 控制消息

### 设备写入

**描述**：云端通过该Topic对设备下达写入指令,一次控制单个属性控制。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/control/property/set/{家庭编码}/{设备号}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Control",
        "name": "DeviceWrite",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "items":{
             "code":"attributeCode",
             "value":"attributeValue"
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
code	|String	|设备属性 见产品属性表
value   |String	|设备属性值 数据类型及可选值见产品属性表



### 设备写入指令结果

**描述**：大屏通过该Topic异步方式返回云端写入指令结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/control/property/set/reply/{家庭编码}/{设备号}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Control",
        "name": "DeviceWriteReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "code":200,
        "message":"success"
    }
}
```

参数 | 类型|说明
---|---|---
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。


### 读取状态

**描述**：云端通过该Topic读取设备状态信息,一次性读取设备所有状态信息。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/control/property/status/read/{家庭编码}/{设备号}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Control",
        "name": "DeviceStatusRead",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "productCode":"1"
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|产生时间。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。
productCode|	String|	设备所属产品编码。




### 读取状态响应

**描述**：大屏通过该Topic异步方式返回云端设备状态读取指令结果信息。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：screen/service/control/property/status/read/reply/{家庭编码}/{设备号}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Control",
        "name": "DeviceStatusReadReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "code":200,
        "message":"success",
        "data":[{
             "code":"attributeCode",
             "value":"attributeValue"  
        }]
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
data	|数组	|设备返回的结果



### 控制场景

**描述**：云端通过该Topic执行场景。

**数据流向**：云端-> 大屏

**Topic**：/screen/service/control/scene/set/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Control",
        "name": "FamilySceneSet",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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
        "namespace": "HomeAuto.Control",
        "name": "FamilySceneSetReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success",
        "sceneId":"1234567*****"
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


## 通知消息

### 设备状态更新

**描述**：大屏通过该Topic上报的设备属性状态。

**数据流向**：大屏->云端 

**Topic**：/screen/service/notice/property/status/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Notice",
        "name": "DeviceStatusUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "deviceSn":"deviceName1234",
        "productCode":"10",
        "items":[{
             "code":"attributeCode",
             "value":"attributeValue"
            }]
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
items	|数组|	设备属性数据。
code	|String	|属性名称。见产品属性表
value	|Objext	|属性值 见产品属性表。


### 设备状态更新响应

**描述**：云端通过该Topic异步方式返回大屏设备状态更新结果信息。如果过程中出现错误，在data中返回。

**数据流向**：云端->大屏

**Topic**：/screen/service/notice/property/status/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Notice",
        "name": "DeviceStatusUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success",
        "deviceSn":"1*****"
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


### 数据更新通知

**描述**：云端通过该Topic告知大屏端配置信息有更新。

**数据流向**：云端->大屏 

**Topic**：/screen/service/notice/config/update/{家庭编码}

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Notice",
        "name": "FamilyConfigUpdate",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "operateType":"delete",
        "name":"scene"
    }

    }
}
```

参数 | 类型|说明
---|---|---
name |String|	更新的类别 场景、楼层、房间、设备。  
operateType|	String|	更新类型 add/update/delte。


### 数据更新通知响应

**描述**：大屏通过该Topic异步方式返回收到数据更新通知。如果过程中出现错误，在data中返回。

**数据流向**：大屏->云端

**Topic**：/screen/service/notice/config/update/reply/{家庭编码}

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Notice",
        "name": "DeviceStatusUpdateReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success"
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

---
## http/https协议
### 请求定义
* url: http://${域名}:${端口}/homeauto-center-device/callback/screen
* 方式：post

## 配置信息请求

### 楼层信息请求

**描述**：请求楼层全量数据。

**请求参数**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilyFloorRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```

** 返回值**

```
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilyFloorRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "items":[
            {
                 "id":"1***"，
                 "name":"楼层一"
                 "order":1
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
items	|数组|	更新数据数组。
id	|String	|楼层唯一主键
name	|String	|楼层名称
order	|String	|楼号



### 房间信息请求

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilyRoomRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```



**返回值**

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilyRoomRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "items":[
            {
                 "id":"1***",
                 "floorId":"楼层主键,
                 "name":"楼层一",
                 "type":1,
                 "remark":""
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
items	|数组|	更新数据数组。
id	    |String	|房间主键
floorId	|String	|楼层主键
type	|int|房间类型
remark	|String	|备注
name	|String	|房间名称



### 设备信息请求

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "DeviceInfoRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```

**返回值**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "DeviceInfoRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "items":[
            {
                 "id":"1***",
                 "deviceSn":"楼层主键,
                 "name":"楼层一",
                 "type":1,
                 "remark":""
            }
        ]
    }
}
```

参数 | 类型|说明
---|---|---
items	|数组|	更新数据数组。
attributeCode	|String	|设备号
attributeValue	|String	|房间ID 



### 场景信息请求

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilySceneRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```
**返回值**:

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilySceneRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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


### 消息公告信息请求


**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilyNewsRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```

**返回值**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilyNewsRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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



### 产品信息请求
**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilyProductRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```

**返回值**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilyProductRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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





### 定时场景信息请求

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilySceneTimingConfigRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```
**返回值**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilySceneTimingConfigRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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



### 智能场景信息请求

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request",
        "name": "FamilySceneSmartConfigRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```
**返回值**：

```
{
   "header": {
        "namespace": "HomeAuto.Config.Request.Reply",
        "name": "FamilySceneSmartConfigRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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

## 事件
### 大屏日志推送

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Event.Push",
        "name": "FamilyScreenLogsEvent",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
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

**响应**
```
{
   "header": {
        "namespace": "HomeAuto.Event.Push.Reply",
        "name": "FamilyScreenLogsEventReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success"
    }
}
```

参数 | 类型|说明
---|---|---
time	|Long	|事件产生时间。
familyCode |String|	家庭编码。  
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。




### 报警信息上报

**数据格式**：

```
{
   "header": {
        "namespace": "HomeAuto.Event.Push",
        "name": "FamilyDeviceAlarmEvent",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
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
**返回信息**：
```
{
   "header": {
        "namespace": "HomeAuto.Event.Push.Reply",
        "name": "FamilyDeviceAlarmEventReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success",
        "data":[{
            "uniqueId":"唯一标记",
            "code":200,
            "message":"success"
        }]
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


### 设备上下线状态推送

**请求参数**：

```
{
   "header": {
        "namespace": "HomeAuto.Event.Push",
        "name": "DeviceOnlineStatusEvent",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "status":"online|offline",
        "deviceSn":"deviceName1234"
    }
}
```
**Payload**
参数 | 类型|说明
---|---|---
status	|String	|设备状态。online：上线。offline：离线。
familyCode |String|	家庭编码。  
deviceSn|	String|	设备号。


**返回信息**：
```
{
   "header": {
        "namespace": "HomeAuto.Event.Push.Reply",
        "name": "DeviceOnlineStatusEventReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "code":200,
        "message":"success",
    }
}
```

参数 | 类型|说明
---|---|---
code	|Integer	|结果状态码说明参见下表表 1。
message	|String	|结果状态码信息，说明参见下表表 1。



## 查询消息
### 查询天气

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Other.Request",
        "name": "FamilyWeatherRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }

}
```

**响应**：

```
{
   "header": {
        "namespace": "HomeAuto.Other.Request.Reply",
        "name": "FamilyWeatherRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "items":
           {
               "attributeCode":"attributeValue"
           }
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

**数据格式**：
```
{
   "header": {
        "namespace": "HomeAuto.Other.Request",
        "name": "FamilyTimeRequest",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
    }
}
```


**响应**：

```
{
   "header": {
        "namespace": "HomeAuto.Other.Request.Reply",
        "name": "FamilyTimeRequestReply",
        "messageId": "01ebf625-0b89-4c4d-b3aa-32340e894688",
        "payloadVersion": "1",
        "time":1510292739881,
        "familyCode":"al12345****"
    },
    "payload": {
        "name":"请求天气/时间",
        "items":
           {
               "attributeCode":"attributeValue"
           }
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

