import 'package:basic_flutter/boost/BoostBinding.dart';
import 'package:basic_flutter/common/log.dart';
import 'package:basic_flutter/layout/my_align.dart';
import 'package:basic_flutter/layout/my_center.dart';
import 'package:basic_flutter/layout/my_column.dart';
import 'package:basic_flutter/layout/my_constraints.dart';
import 'package:basic_flutter/layout/my_flex.dart';
import 'package:basic_flutter/layout/my_flow.dart';
import 'package:basic_flutter/layout/my_layout_builder.dart';
import 'package:basic_flutter/layout/my_row.dart';
import 'package:basic_flutter/layout/my_stack.dart';
import 'package:basic_flutter/layout/my_wrap.dart';
import 'package:basic_flutter/page/my_counter.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

void main() {
  ///添加全局生命周期监听类
  ///addGlobalObserver();

  ///这里的CustomFlutterBinding调用务必不可缺少，用于控制Boost状态的resume和pause
  CustomFlutterBinding();

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  /// Flutter 提供了两个转场动画，分别为 MaterialPageRoute 和 CupertinoPageRoute，
  /// MaterialPageRoute 根据不同的平台显示不同的效果，Android为从下到上，iOS为从左到右。
  /// CupertinoPageRoute 不分平台，都是从左到右。
  Map<String, FlutterBoostRouteFactory> routerMap = {
    'MyCounter': (RouteSettings settings, String? uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map =
                settings.arguments as Map<String, dynamic>;
            String? data = map['data'] as String?;
            log("data : $data");
            return const MyCounter();
          });
    },
    'MyConstraints': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyConstraints();
          });
    },
    'MyRow': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyRow();
          });
    },
    'MyColumn': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyColumn();
          });
    },
    'MyFlex': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyFlex();
          });
    },
    'MyWrap': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyWrap();
          });
    },
    'MyFlow': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyFlow();
          });
    },
    'MyStack': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyStack();
          });
    },
    'MyAlign': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyAlign();
          });
    },
    'MyCenter': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyCenter();
          });
    },
    'MyLayoutBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyLayoutBuilder();
          });
    },
  };

  Route<dynamic>? routeFactory(RouteSettings settings, String? uniqueId) {
    FlutterBoostRouteFactory? func = routerMap[settings.name];
    if (func == null) {
      return null;
    }
    return func(settings, uniqueId);
  }

  Widget appBuilder(Widget home) {
    return MaterialApp(
      home: home,
      debugShowCheckedModeBanner: true,

      ///必须加上builder参数，否则showDialog等会出问题
      builder: (_, __) {
        return home;
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return FlutterBoostApp(
      routeFactory,
      appBuilder: appBuilder,
    );
  }
}
