import 'package:basic_flutter/http/my_dio.dart';
import 'package:basic_flutter/other/my_animation.dart';
import 'package:basic_flutter/other/my_dialog.dart';
import 'package:basic_flutter/other/my_isolate.dart';
import 'package:basic_flutter/packages/my_notification.dart';
import 'package:basic_flutter/packages/my_screen_util.dart';
import 'package:basic_flutter/packages/my_shared_preferences.dart';
import 'package:basic_flutter/packages/my_toast.dart';
import 'package:basic_flutter/state/bloc/my_bloc.dart';
import 'package:basic_flutter/state/get/my_get_app.dart';
import 'package:basic_flutter/state/getX/my_get_app.dart';
import 'package:basic_flutter/state/provider/my_provider.dart';
import 'package:flutter/material.dart';

import '../container/my_align.dart';
import '../container/my_center.dart';
import '../container/my_constrained_box.dart';
import '../container/my_container.dart';
import '../container/my_decorated_box.dart';
import '../container/my_padding.dart';
import '../container/my_sized_box.dart';
import '../function/my_future_builder.dart';
import '../function/my_gesture_detector.dart';
import '../function/my_inherited_widget.dart';
import '../function/my_pop_scope.dart';
import '../function/my_stream_builder.dart';
import '../function/my_value_listenable_builder.dart';
import '../layout/my_column.dart';
import '../layout/my_flex.dart';
import '../layout/my_flow.dart';
import '../function/my_layout_builder.dart';
import '../layout/my_row.dart';
import '../layout/my_stack.dart';
import '../layout/my_wrap.dart';
import '../page/my_counter.dart';
import '../scroll/my_animated_list.dart';
import '../scroll/my_custom_scroll_view.dart';
import '../scroll/my_grid_view.dart';
import '../scroll/my_list_view.dart';
import '../scroll/my_nested_scroll_view.dart';
import '../scroll/my_page_view.dart';
import '../scroll/my_scroll_view.dart';
import '../scroll/my_tab_bar_view.dart';
import 'route_item.dart';

class Routes {
  static const String counter = '/Counter';

  // Layout 布局
  // 线性布局组件
  static const String row = '/Row';
  static const String column = '/Column';
  static const String flex = '/Flex';

  // 非线性布局组件
  static const String wrap = '/Wrap';
  static const String flow = '/Flow';
  static const String stack = '/Stack';

  // Container 容器
  // 单子容器
  static const String container = '/Container';
  static const String padding = '/Padding';
  static const String align = '/Align';
  static const String center = '/Center';

  // Box容器
  static const String constrainedBox = '/ConstrainedBox';
  static const String decoratedBox = '/DecoratedBox';
  static const String sizedBox = '/SizedBox';

  // 可滚动组件
  static const String listView = '/MyListView';
  static const String gridView = '/MyGridView';
  static const String scrollView = '/MyScrollView';

  static const String pageView = '/MyPageView';
  static const String tabBarView = '/MyTabBarView';

  static const String animatedList = '/MyAnimatedList';
  static const String customScrollView = '/MyCustomScrollView';
  static const String nestedScrollView = '/MyNestedScrollView';

  // 功能型组件
  static const String layoutBuilder = '/LayoutBuilder';

  static const String gestureDetector = '/GestureDetector';
  static const String popScope = '/PopScope';

  static const String inheritedWidget = '/InheritedWidget';
  static const String valueListenableBuilder = '/ValueListenableBuilder';

  static const String futureBuilder = '/FutureBuilder';
  static const String streamBuilder = '/StreamBuilder';

  // Other
  static const String animation = '/animation';
  static const String dialog = '/dialog';
  static const String isolate = '/isolate';

  // 网络请求
  static const String dio = '/Dio';

  // 状态管理
  static const String provider = '/provider';
  static const String getX = '/getX';
  static const String getX2 = '/getX2';
  static const String bloC = '/bloC';

  // 三方框架
  static const String toast = '/toast';
  static const String notification = '/notification';
  static const String sharedPreferences = '/sharedPreferences';
  static const String screenUtil = '/screenUtil';

  static String getInitial() {
    return counter;
  }

  static Map<String, WidgetBuilder> getRoutes() {
    return {
      // 计数器
      counter: (context) => const MyCounter(),

      // Layout 布局

      // 线性布局组件
      row: (context) => const MyRow(),
      column: (context) => const MyColumn(),
      flex: (context) => const MyFlex(),

      // 非线性布局组件
      wrap: (context) => const MyWrap(),
      flow: (context) => const MyFlow(),
      stack: (context) => const MyStack(),

      // Container 容器

      // 单子容器
      container: (context) => const MyContainer(),
      padding: (context) => const MyPadding(),
      align: (context) => const MyAlign(),
      center: (context) => const MyCenter(),

      // Box容器
      constrainedBox: (context) => const MyConstrainedBox(),
      decoratedBox: (context) => const MyDecoratedBox(),
      sizedBox: (context) => const MySizedBox(),

      // 可滚动组件
      listView: (context) => const MyListView(),
      gridView: (context) => const MyGridView(),
      scrollView: (context) => const MyScrollView(),

      pageView: (context) => const MyPageView(),
      tabBarView: (context) => const MyTabBarView(),

      animatedList: (context) => const MyAnimatedList(),
      customScrollView: (context) => const MyCustomScrollView(),
      nestedScrollView: (context) => const MyNestedScrollView(),

      // 功能型组件
      layoutBuilder: (context) => const MyLayoutBuilder(),

      gestureDetector: (context) => const MyGestureDetector(),
      popScope: (context) => const MyPopScope(),

      inheritedWidget: (context) => const MyInheritedWidget(),

      valueListenableBuilder: (context) => const MyValueListenableBuilder(),

      futureBuilder: (context) => const MyFutureBuilder(),
      streamBuilder: (context) => const MyStreamBuilder(),

      // Other
      animation: (context) => const MyAnimation(),
      dialog: (context) => const MyDialog(),
      isolate: (context) => const MyIsolate(),

      // 网络请求
      dio: (context) => const MyDio(),

      // 状态管理
      provider: (context) => const MyProvider(),
      getX: (context) => const MyGet(),
      getX2: (context) => const MyGetX2(),
      bloC: (context) => const MyBloC(),

      // 三方框架
      toast: (context) => const MyToast(),
      notification: (context) => const MyNotification(),
      sharedPreferences: (context) => const MySharedPreferences(),
      screenUtil: (context) => const MyScreenUtil(),
    };
  }

  static List<RouteItem> getRouteList() {
    return [
      //
      RouteItem(routeName: counter, routePath: counter, routeDescribe: "计时器"),
      //
      RouteItem(
          routeName: "—— Layout 布局 ——"
              "\n会有一个children属性",
          routePath: ""),
      RouteItem(routeName: row, routePath: row, routeDescribe: "水平线性布局"),
      RouteItem(routeName: column, routePath: column, routeDescribe: "垂直线性布局"),
      RouteItem(
          routeName: flex,
          routePath: flex,
          routeDescribe: "弹性布局，按照一定比例来分配父容器空间"),
      RouteItem(
          routeName: wrap,
          routePath: wrap,
          routeDescribe: "流式布局，根据子组件大小自动换行的布局"),
      RouteItem(
          routeName: flow,
          routePath: flow,
          routeDescribe: "流式布局，根据子组件大小自动换行的布局"),
      RouteItem(
          routeName: stack,
          routePath: stack,
          routeDescribe: "堆叠布局，根据距父容器四个角的位置来确定自身的位置"),
      //
      RouteItem(
          routeName: "—— Container 容器 ——"
              "\n会有一个child属性",
          routePath: ""),
      RouteItem(
          routeName: container, routePath: container, routeDescribe: "容器"),
      RouteItem(routeName: padding, routePath: padding, routeDescribe: "填充容器"),
      RouteItem(routeName: align, routePath: align, routeDescribe: "对齐容器"),
      RouteItem(routeName: center, routePath: center, routeDescribe: "居中容器"),
      RouteItem(
          routeName: constrainedBox,
          routePath: constrainedBox,
          routeDescribe: "约束容器"),
      RouteItem(
          routeName: decoratedBox,
          routePath: decoratedBox,
          routeDescribe: "装饰容器"),
      RouteItem(
          routeName: sizedBox, routePath: sizedBox, routeDescribe: "尺寸容器"),
      //
      RouteItem(routeName: "—— 可滚动组件 ——", routePath: ""),
      RouteItem(
          routeName: listView, routePath: listView, routeDescribe: "ListView"),
      RouteItem(
          routeName: gridView, routePath: gridView, routeDescribe: "GridView"),
      RouteItem(
          routeName: scrollView,
          routePath: scrollView,
          routeDescribe: "ScrollView"),
      //
      RouteItem(
          routeName: pageView, routePath: pageView, routeDescribe: "PageView"),
      RouteItem(
          routeName: tabBarView,
          routePath: tabBarView,
          routeDescribe: "TabBarView"),
      //
      RouteItem(
          routeName: animatedList,
          routePath: animatedList,
          routeDescribe: "AnimatedList"),
      RouteItem(
          routeName: customScrollView,
          routePath: customScrollView,
          routeDescribe: "CustomScrollView"),
      RouteItem(
          routeName: nestedScrollView,
          routePath: nestedScrollView,
          routeDescribe: "NestedScrollView"),
      //
      RouteItem(routeName: "—— 功能型组件 ——", routePath: ""),
      RouteItem(
          routeName: layoutBuilder,
          routePath: layoutBuilder,
          routeDescribe: "获取父组件大小并布局容器"),
      RouteItem(
          routeName: gestureDetector,
          routePath: gestureDetector,
          routeDescribe: "手势检测"),
      RouteItem(
          routeName: popScope, routePath: popScope, routeDescribe: "返回拦截"),
      RouteItem(
          routeName: inheritedWidget,
          routePath: inheritedWidget,
          routeDescribe: "数据共享"),
      RouteItem(
          routeName: valueListenableBuilder,
          routePath: valueListenableBuilder,
          routeDescribe: "数据源监听"),
      RouteItem(
          routeName: futureBuilder,
          routePath: futureBuilder,
          routeDescribe: "异步UI更新"),
      RouteItem(
          routeName: streamBuilder,
          routePath: streamBuilder,
          routeDescribe: "异步UI更新"),
      //
      RouteItem(routeName: "—— 其他组件 ——", routePath: ""),
      RouteItem(
          routeName: animation,
          routePath: animation,
          routeDescribe: "Animation"),
      RouteItem(routeName: dialog, routePath: dialog, routeDescribe: "Dialog"),
      RouteItem(
          routeName: isolate, routePath: isolate, routeDescribe: "Isolate"),
      //
      RouteItem(routeName: "—— 网络请求 ——", routePath: ""),
      RouteItem(routeName: dio, routePath: dio, routeDescribe: "Dio"),
      //
      RouteItem(routeName: "—— 状态管理 ——", routePath: ""),
      RouteItem(
          routeName: provider, routePath: provider, routeDescribe: "Provider"),
      RouteItem(routeName: getX, routePath: getX, routeDescribe: "GetX"),
      RouteItem(routeName: getX2, routePath: getX2, routeDescribe: "GetX"),
      RouteItem(routeName: bloC, routePath: bloC, routeDescribe: "BloC"),
      //
      RouteItem(routeName: "—— 三方框架 ——", routePath: ""),
      RouteItem(routeName: toast, routePath: toast, routeDescribe: "Toast"),
      RouteItem(
          routeName: notification,
          routePath: notification,
          routeDescribe: "Notification"),
      RouteItem(
          routeName: sharedPreferences,
          routePath: sharedPreferences,
          routeDescribe: "SharedPreferences"),
      RouteItem(
          routeName: screenUtil,
          routePath: screenUtil,
          routeDescribe: "ScreenUtil"),
    ];
  }
}
