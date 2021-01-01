## 通讯接口文档

向后端发送的请求均为POST，返回状态码：

- 200 表示请求成功
- 400 请求格式错误
- 401 密码/token错误/已锁定
- 403 权限不足以访问当前页面
- 500 服务端响应错误



baseService ：http://121.196.103.2:8080/

## 用户

#### user格式 JSON

| 属性名     | 类型   | 备注                 |
| ---------- | ------ | -------------------- |
| `id`       | int    | 用户id（由后端指定） |
| `nickname` | string | 昵称                 |
| `password` | string | 密码                 |
| `email`    | string | 邮箱                 |
| `phoneNum` | string | 电话号码             |
| `credit`   | int    | 信誉积分             |

#### `/user/login` 用户登录

- 请求体：username(String) password(String)
- 返回：无
- 备注：username可以是邮箱或者手机号
- password经过前端的一次hash加密;token表示用户的登入凭证
- token(String)写入cookie

#### `/user/logout` 用户登出

- 请求体：无
- 返回：无

#### `/user/register` 用户注册

- 请求体：nickname(String)  password(String) email(String) phoneNum(String)
- 返回：无

#### `/user/query` 用户查询

- 请求体：id(int)
- 返回：user_JSON

#### `/user/modify` 用户信息更改

- 请求体：nickname(String) password(String) newPassword(String) email(String) phoneNum(String) 
- 返回：无
- 备注：初始信息框中填有原信息 （password和newPassword为空）
- 点击”确认修改“后，前端检查nickname、newPassword、email和phoneNum是否符合规范
- newPassword可以为空，表示不修改密码
- 后端检查password



## 订单

#### order格式 JSON

| 属性名        | 类型   | 备注         |
| ------------- | ------ | ------------ |
| `id`          | int    | 由后端生成   |
| `userID`      | int    | 发布者ID     |
| `toolmanID`   | int    | 接单者ID     |
| `place`       | string | 执行地址     |
| `destination` | string | 送货地址     |
| `endtime`     | String | 截止时间     |
| `description` | string | 订单详细描述 |
| `state`       | int    | 订单状态     |

State: 0 = created; 1 = executing; 2 = finished; 3 = canceled



#### `/order/add` 添加订单

- 请求体：order ，其中不需要`id`、`toolmanID`和`state`
- 返回：无



#### `/order/myorderlist` 我的订单查询

- 请求体：userID(int)
- 返回：[orders]（符合查询条件，`userID`=userID或`toolmanID`=userID的订单列表）



#### `/order/receive` 接收订单

- 请求体： orderID(int) toolmanID(int)
- 返回：无



#### `/order/compete` 完成订单

- 请求体：orderID(int)，userID
- 返回：无
- *备注：权限检查，检查userID是否为发布者



#### `/order/delete` 删除订单

- 请求体：orderID ，userID
- 返回：无
- *备注：权限检查，检查userID是否为该订单的发布者



#### `/order/query` 查看订单

- 请求体：orderID
- 返回：order
- 备注：前端根据user身份，显示不同信息和操作



#### `/order/orderlist` 调取所有发布中的订单

- 请求体：无
- 返回：[orders] (符合条件：state = ”发布中“)



## 聊天信息

#### chatmessage格式 JSON

| 属性名       | 类型   | 备注                   |
| ------------ | ------ | ---------------------- |
| `orderID`    | int    | 由后端生成，与订单对应 |
| `senderID`   | int    | 发布者ID               |
| `receiverID` | int    | 接单者ID               |
| `sendtime`   | String | 信息发送时间           |
| `message`    | string | 聊天记录文本           |

#### `/chat/query` 查询聊天记录

- 请求体：orderID(int)，userID(int)
- 返回：`string`



#### `/chat/check` 查询是否有未读消息

- 请求体：orderID(int)，userID(int)

- 返回：new_message(boolean)

  

#### `/chat/update` 添加聊天记录

- 请求体：orderID(int),senderID(int),receiverID(int),message(string)
- 返回：无



