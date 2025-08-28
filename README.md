# MyApplication

使用了 Kotlin DSL + Gradle Version Catalogs 进行统一版本管理。

整体项目使用组件化开发，使用ARouter进行模块内通信，使用hilt依赖注入，并使用hilt进行多module初始化。

# Libs

## 自定义控件

* 高斯模糊
* BottomSheetDialog
* 无限滚动的ImageView
* 跑马灯控件
* 裸眼3D效果
* 自动对齐TextView
* 自定义PageTransformer
* 验证码控件

## 网络，框架相关

* Volley封装
* OkHttp封装
* Retrofit封装
* Retrofit下载库
* RxWebSocket封装
* 点九图片解析库
* RxBus + LiveDataBus + FlowBus

| 消息总线         | 延迟发送 | 有序接收消息 | Sticky | 生命周期感知 | 跨进程/APP | 线程分发 |
|--------------|------|--------|--------|--------|---------|------|
| EventBus     | ❌    | ✅      | ✅      | ❌      | ❌       | ✅    |
| RxBus        | ❌    | ✅      | ✅      | ❌      | ❌       | ✅    |
| LiveEventBus | ✅    | ✅      | ✅      | ✅      | ✅       | ❌    |
| FlowEventBus | ✅    | ✅      | ✅      | ✅      | ❌       | ✅    | 

# Modules

## modules_arch

常用的开发架构

* MVP
* MVVM
* MVI
* 基于 mavericks 框架的 MVI

### MVVM

* LiveData + ViewModel
* 使用 UseCase 封装可复用的单一业务逻辑

### MVI

* 定义状态 State
* 定义事件 Event
* 处理事件+更新状态（ViewModel）
* 处理状态+发送事件（UI）

## modules_network

关于网络方面开发的demo

* Coil
* Glide
* HttpURL
* Volley
* OkHttp
* Retrofit
* WebSocket网络请求
* 基于OkHttp实现WebSocket
* NanoHttpD的Android端的服务器搭建
* WebServer的Android端的服务器搭建
* 基于Netty实现的socket

## module_opensource

记录了一些用过的开源框架的demo

* PAG
* Lottie
* SVGAPlayer
* Greendao 数据库
* ObjectBox 数据库

## modules_sample

* Hilt
* Room
* Paging
* DataStore
* WorkManager
* Android 上的 Kotlin 协程
* Android 上的 Kotlin 数据流

## modules_compose

* 基础组件
* Navigation
* BackHandler
* remember, rememberSaveable
* draggable, dragGestureDetector

## modules_flutter

* 布局类组件
    * 线性布局（Row, Column）
    * 弹性布局（Flex）
    * 流式布局（Wrap, Flow）
    * 堆叠布局（Stack）
* 容器类组件
    * Container
    * Padding
    * Align
    * Center
    * ConstrainedBox
    * DecoratedBox
    * SizedBox
* 可滚动组件
    * ListView
    * GridView
    * SingleChildScrollView
    * PageView
    * TabBarView
    * AnimatedList
    * CustomScrollView
    * NestedScrollView
* 功能型组件
    * 布局构建（LayoutBuilder）
    * 手势检测（GestureDetector）
    * 导航返回拦截（PopScope）
    * 数据共享组件（InheritedWidget）
    * 数据源监听（ValueListenableBuilder）
    * 异步UI更新（FutureBuilder、StreamBuilder）
* 其他组件
    * Animation
    * Dialog
    * Isolate
* 网络请求
    * [Dio](https://pub.dev/packages/dio)
* 状态管理
    * [Provider](https://pub.dev/packages/provider)
    * [GetX](https://pub.dev/packages/get)
    * [BloC](https://pub.dev/packages/flutter_bloc)
* 三方框架
    * [Toast](https://pub.dev/packages/fluttertoast)
    * [Notification](https://pub.dev/packages/flutter_local_notifications)
    * [SharedPreferences](https://pub.dev/packages/shared_preferences)
    * [ScreenUtil](https://pub.dev/packages/flutter_screenutil)

# Android 知识体系

## java

## kotlin

## Android

* [so库适配简单总结](https://flowus.cn/williamwu/share/6f18d2cd-9df9-4637-bdea-1d4e89919876)

### RecyclerView

* [RecyclerView 绘制流程](https://flowus.cn/williamwu/share/a20dab7f-b70c-4272-9353-b60dd832c7b2)

* [RecyclerView 缓存机制](https://flowus.cn/williamwu/share/36be3fd1-6c8d-4164-bd9a-bd8df49e7557)

## JetPack

* [Lifecycle源码解析](https://flowus.cn/williamwu/share/fab8b772-2374-4687-ac80-746ec914dda8)

* [LiveData源码解析](https://flowus.cn/williamwu/share/6e97c045-679e-4f16-93bf-3f5b0b35d8b4)

* [ViewModel源码解析](https://flowus.cn/williamwu/share/a8af4987-102c-436d-89a9-4a42a483c019)

## 开源框架

* [OkHttp 源码解析](https://flowus.cn/williamwu/share/1fb573d6-a924-4441-a20d-2f7de2f9d195)

* [Retrofit 源码解析](https://flowus.cn/williamwu/share/c8e3e04c-d24b-49af-847d-4a8be6646e2e)

* [Glide 执行流程](https://flowus.cn/williamwu/share/fd31dd11-4bcb-45d9-8f94-772a8628b69e)

* [Glide 缓存机制](https://flowus.cn/williamwu/share/2ce6971d-2566-4fe6-ab5a-d24e98fffe80)

* [ARouter 源码解析](https://flowus.cn/williamwu/share/bd1b9ab3-c881-42aa-a209-07336402660e)

* [EventBus 源码解析](https://flowus.cn/williamwu/share/55d699dd-2728-4e68-b2c8-b3d3db9d89a1)

* [LeakCanary 源码解析](https://flowus.cn/williamwu/share/3dab5ccc-2d12-45f7-83a4-828ce8750f4e)