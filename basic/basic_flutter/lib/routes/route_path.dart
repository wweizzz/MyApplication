import 'package:flutter/material.dart';

import '../container/my_align.dart';
import '../container/my_center.dart';
import '../container/my_column.dart';
import '../container/my_constrained_box.dart';
import '../container/my_container.dart';
import '../container/my_decorated_box.dart';
import '../container/my_flex.dart';
import '../container/my_flow.dart';
import '../container/my_padding.dart';
import '../container/my_row.dart';
import '../container/my_sized_box.dart';
import '../container/my_stack.dart';
import '../container/my_wrap.dart';
import '../function/my_future_builder.dart';
import '../function/my_gesture_detector.dart';
import '../function/my_inherited_widget.dart';
import '../function/my_layout_builder.dart';
import '../function/my_pop_scope.dart';
import '../function/my_stream_builder.dart';
import '../function/my_value_listenable_builder.dart';
import '../page/my_counter.dart';
import 'route_item.dart';

class Routes {
  static const String counter = '/Counter';

  static const String container = '/Container';
  static const String padding = '/Padding';
  static const String align = '/Align';
  static const String center = '/Center';

  static const String row = '/Row';
  static const String column = '/Column';
  static const String flex = '/Flex';

  static const String stack = '/Stack';
  static const String wrap = '/Wrap';
  static const String flow = '/Flow';

  static const String constrainedBox = '/ConstrainedBox';
  static const String decoratedBox = '/DecoratedBox';
  static const String sizedBox = '/SizedBox';

  static const String layoutBuilder = '/LayoutBuilder';

  static const String gestureDetector = '/GestureDetector';
  static const String popScope = '/PopScope';

  static const String inheritedWidget = '/InheritedWidget';
  static const String valueListenableBuilder = '/ValueListenableBuilder';

  static const String futureBuilder = '/FutureBuilder';
  static const String streamBuilder = '/StreamBuilder';

  static String getInitial() {
    return counter;
  }

  static Map<String, WidgetBuilder> getRoutes() {
    return {
      // 计数器
      counter: (context) => const MyCounter(),
      // 单子组件
      container: (context) => const MyContainer(),
      padding: (context) => const MyPadding(),
      align: (context) => const MyAlign(),
      center: (context) => const MyCenter(),
      // 线性布局组件
      row: (context) => const MyRow(),
      column: (context) => const MyColumn(),
      flex: (context) => const MyFlex(),
      // 非线性布局组件
      stack: (context) => const MyStack(),
      wrap: (context) => const MyWrap(),
      flow: (context) => const MyFlow(),
      // Box
      constrainedBox: (context) => const MyConstrainedBox(),
      decoratedBox: (context) => const MyDecoratedBox(),
      sizedBox: (context) => const MySizedBox(),
      //
      layoutBuilder: (context) => const MyLayoutBuilder(),
      //
      gestureDetector: (context) => const MyGestureDetector(),
      popScope: (context) => const MyPopScope(),
      //
      inheritedWidget: (context) => const MyInheritedWidget(),
      //
      valueListenableBuilder: (context) => const MyValueListenableBuilder(),
      //
      futureBuilder: (context) => const MyFutureBuilder(),
      streamBuilder: (context) => const MyStreamBuilder(),
    };
  }

  static List<RouteItem> getRouteList() {
    return [
      //
      RouteItem(routeName: counter, routePath: counter, routeDescribe: "计时器"),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: container, routePath: container),
      RouteItem(routeName: padding, routePath: padding),
      RouteItem(routeName: "", routePath: ""),
      RouteItem(routeName: align, routePath: align),
      RouteItem(routeName: center, routePath: center),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: row, routePath: row),
      RouteItem(routeName: column, routePath: column),
      RouteItem(routeName: flex, routePath: flex),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: stack, routePath: stack),
      RouteItem(routeName: wrap, routePath: wrap),
      RouteItem(routeName: flow, routePath: flow),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: constrainedBox, routePath: constrainedBox),
      RouteItem(routeName: decoratedBox, routePath: decoratedBox),
      RouteItem(routeName: sizedBox, routePath: sizedBox),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: layoutBuilder, routePath: layoutBuilder),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: gestureDetector, routePath: gestureDetector),
      RouteItem(routeName: popScope, routePath: popScope),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: inheritedWidget, routePath: inheritedWidget),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(
          routeName: valueListenableBuilder, routePath: valueListenableBuilder),
      RouteItem(routeName: "", routePath: ""),
      //
      RouteItem(routeName: futureBuilder, routePath: futureBuilder),
      RouteItem(routeName: streamBuilder, routePath: streamBuilder),
      RouteItem(routeName: "", routePath: ""),
    ];
  }
}
