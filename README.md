# PKU-TOOL-MAN
### 项目简介：

​	本项目是一个提供跑腿服务的交流平台，用户可以在平台上发布、接收订单，并通过聊天等行为，来完成跑腿业务。



### 使用指南：

##### 登录、注册、找回密码

​	打开app后即进入`登录`界面，该界面可点击按钮切换至`注册`或`找回密码`界面

​	在这些界面中根据指示填充文本框，再点击按钮即可完成操作。



##### 我的订单

​	在`我的订单`界面可查看 “我发布的订单”和“我接收的订单”两个列表，并提供了筛选功能

​	点击订单条目即可跳转至`订单详情`界面

​	点击右下角`刷新`图标可刷新订单列表



##### 订单广场

​	在`订单广场`界面可查看`已发布`的订单，并提供了筛选功能

​	点击订单条目即可跳转至`订单详情`界面

​	点击右下角`刷新`图标可刷新订单列表

​	点击右上角 **+** 号图标可跳转至`订单创建`界面，填入信息再点击`发布`即可完成订单的发布



##### 我的信息

​	在`我的信息`界面可查看个人信息，点击`修改`图标即可跳转到`信息修改`界面进行操作

​	点击`退出登录`按钮可退回登录界面



##### 订单详情

​	`订单详情`界面会根据查看用户的身份（`发布者`、`接收者`或`其他`）来显示不同的组件和信息

​	该界面提供了`接收订单`，`完成订单`，`打开地图`，`打开聊天板`等操作



### 项目成果：

| **立项目标**                             | **实际情况** | **偏差有无** | **原因分析**                   |
| ---------------------------------------- | ------------ | ------------ | ------------------------------ |
| 用户注册、登录，  查看、维护个人信息     | 达到了       | 无           | 组员尽职尽责，合作开发较为顺利 |
| 查看订单列表                             | 达到了       | 无           |                                |
| 发布、删除、接收、取消订单               | 达到了       | 无           |                                |
| 用户执行订单（包括查看地图、用户对话等） | 达到了       | 无           |                                |
| 用户举报违规行为                         | 未达到       | 有           | 开发时间不足                   |
| 成就系统                                 | 未达到       | 有           | 开发时间不足                   |

#### 项目代码成果（截止12月29日）（后续又添加了测试相关代码）：

![image-20210101154004356](https://github.com/Dcat0/PKU-Tool-Man/blob/main/image/image-20210101154004356.png)





## Git仓库说明

#### 小组成员介绍：

| 成员姓名 | github账号   | 分支名         |
| -------- | ------------ | -------------- |
| 陈官厅   | andcgt       | backEnd-cgt    |
| 董晨     | Dcat0        | dcat           |
| 吴裕铖   | Fb1107       | wyc            |
| 张可鸣   | gosicksky    | zkm--interface |
| 刘韫辉   | liuyunhui123 | backEnd-lyh    |
| 蔡思琪   | pakchoi-i    | csq            |
| 王思翰   | Sihan-Wang   | backEnd-wsh    |
| 王宇鑫   | wyxoi        | wyxoi          |

*下图是本地绑定邮箱前的commit（

<img src="https://github.com/Dcat0/PKU-Tool-Man/blob/main/image/image-20210101155759852.png" alt="image-20210101155759852" style="zoom:45%;" />



### 分支列表：

<img src="https://github.com/Dcat0/PKU-Tool-Man/blob/main/image/image-20210101160158042.png" alt="image-20210101160158042" style="zoom:50%;" />

<img src="https://github.com/Dcat0/PKU-Tool-Man/blob/main/image/image-20210101160720440.png" alt="image-20210101160720440" style="zoom:50%;" />

​	开发过程中，前端分支为 `main` ，后端分支为 `backEnd` 

​	最终前后端合并由 **刘韫辉** 负责完成



### 成员主要工作：

| 成员姓名 | 主要工作                                                     |
| -------- | ------------------------------------------------------------ |
| 陈官厅   | 后端“Chat”相关数据库及接口                         |
| 董晨     | `orderspace`相关class及ui，API文档，杂活担当                 |
| 吴裕铖   | `baseclass` 代码，`myorder`相关class及ui，前端主力副手       |
| 张可鸣   | 登录注册、地图、聊天相关class及ui，前端主力                  |
| 刘韫辉   | 服务器环境搭建、后端“User”相关数据库及接口，后端主力           |
| 蔡思琪   | UI界面设计优化、组件布局设计优化，`orderinfo`相关class及ui，UI担当 |
| 王思翰   | 后端框架搭建、后端“Order”相关数据库及接口                      |
| 王宇鑫   | `myinfo`相关class及ui，杂活副手                              |



#### 前端java文件：

<img src="https://github.com/Dcat0/PKU-Tool-Man/blob/main/image/image-20210101172039596.png" alt="image-20210101172039596" style="zoom:50%;" />
