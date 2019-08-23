# base-server
多商城模式商城

改系统使用了smm框架，数据库使用mysql

部署完成前台页面是：http://localhost:8080

后台管理页面是：http://localhost:8080/base-server/a/login

使用eclipse导入项目的时候jdk换成1.6，使用jdk的包不要使用jre的

该系统开发的思路是先整体设计，在慢慢的到一个个细小的模块，站在不同的角色角度先建立逻辑模型，再一步步的把逻辑模型展开成物理模型。
电子商城主要包括实现以下基本功能：

(1)用户要求能够可以注册登录，可以浏览商品、购买商品，把商品加载到购物车里和查看购买记录等。

(2)商家可以管理商店里面的信息。

(3)系统管理员能够管理商店。

所以关于这个购物网站，可以分为用户、商家和管理员三大模块。用户界面（个人中心），用户登录后可以进行相关的购物管理。商家界面，商家登录后可以商店管理，如添加和删除商品、显示所有订单等操作。网站管理员界面：增删改查商店信息。本网站系统的页面整体框架如图4.1所示：
![image](https://github.com/sentimentes/base-server/blob/master/image/41.png)
图4.1电子商城网站的页面整体框架
本系统的三大模块具体设计如下：
(1)用户模块
此模块是对于用户用的，该模块分为两种用户，一种是没有注册的游客，另外一种是注册了的会员，游客只有浏览商品的权限，如果想获得其他权限就只能注册。注册的会员有浏览商品并把看中的商品添加到购物车中的权限，也可以把看中的商品直接付款购买的权限。购买之后可以查看该订单的权限，管理收货信息管理的权限。
用户操作流程图如图4.2所示：
![image](https://github.com/sentimentes/base-server/blob/master/image/42.png)
图4.2用户操作流程图
(2)商家模块
此模块是给商家用的，用于管理商店的各种事物。商家可以添加商品分类，与给相应的商品分类添加商品。商家还可以查看用户购买本商店的订单详情，给订单审核看是否可以发货相应的商品。可以为商店设置积分等级与相应的折扣等等。商家流程图如图4.3所示：
![image](https://github.com/sentimentes/base-server/blob/master/image/43.png)
图4.3商家流程图
(3)系统管理员模块
此模块是系统管理员使用，系统管理员有三种操作，一种是添加商店，为客户开商店，第二种是与给相应的商店添加管理员，第三种是为商城添加公告与优惠的信息。
系统管理员流程图如图4.4所示：
![image](https://github.com/sentimentes/base-server/blob/master/image/44.png)
图 4.4 系统管理员流程图
下面的是一些系统的图片
![image](https://github.com/sentimentes/base-server/blob/master/image/51.png)
![image](https://github.com/sentimentes/base-server/blob/master/image/52.png)
![image](https://github.com/sentimentes/base-server/blob/master/image/53.png)
![image](https://github.com/sentimentes/base-server/blob/master/image/54.png)
![image](https://github.com/sentimentes/base-server/blob/master/image/55.png)
![image](https://github.com/sentimentes/base-server/blob/master/image/56.png)
