import 'package:basic_flutter/boost/BoostBinding.dart';
import 'package:basic_flutter/common/log.dart';
import 'package:basic_flutter/container/my_align.dart';
import 'package:basic_flutter/container/my_center.dart';
import 'package:basic_flutter/function/my_future_builder.dart';
import 'package:basic_flutter/function/my_inherited_widget.dart';
import 'package:basic_flutter/function/my_stream_builder.dart';
import 'package:basic_flutter/function/my_value_listenable_builder.dart';
import 'package:basic_flutter/function/my_will_pop_scope.dart';
import 'package:basic_flutter/http/my_dio.dart';
import 'package:basic_flutter/http/my_isolate.dart';
import 'package:basic_flutter/layout/my_column.dart';
import 'package:basic_flutter/layout/my_flex.dart';
import 'package:basic_flutter/layout/my_flow.dart';
import 'package:basic_flutter/layout/my_row.dart';
import 'package:basic_flutter/layout/my_stack.dart';
import 'package:basic_flutter/layout/my_wrap.dart';
import 'package:basic_flutter/other/my_animation.dart';
import 'package:basic_flutter/other/my_constraints.dart';
import 'package:basic_flutter/other/my_counter.dart';
import 'package:basic_flutter/other/my_dialog.dart';
import 'package:basic_flutter/other/my_gesture_detector.dart';
import 'package:basic_flutter/other/my_layout_builder.dart';
import 'package:basic_flutter/other/my_screenutil.dart';
import 'package:basic_flutter/scroll/my_animated_list.dart';
import 'package:basic_flutter/scroll/my_custom_scroll_view.dart';
import 'package:basic_flutter/scroll/my_grid_view.dart';
import 'package:basic_flutter/scroll/my_list_view.dart';
import 'package:basic_flutter/scroll/my_nested_scroll_view.dart';
import 'package:basic_flutter/scroll/my_page_view.dart';
import 'package:basic_flutter/scroll/my_scroll_view.dart';
import 'package:basic_flutter/scroll/my_tab_bar_view.dart';
import 'package:basic_flutter/state/getX/my_get.dart';
import 'package:basic_flutter/state/getX/my_get_view.dart';
import 'package:basic_flutter/state/provider/my_provider.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:provider/provider.dart';

import 'other/my_notification.dart';
import 'other/my_paint.dart';
import 'other/my_shared_preferences.dart';
import 'other/my_toast.dart';

void main() {
  ///添加全局生命周期监听类
  ///addGlobalObserver();

  ///这里的CustomFlutterBinding调用务必不可缺少，用于控制Boost状态的resume和pause
  CustomFlutterBinding();

  //runApp(const MyApp());
  runApp(ChangeNotifierProvider(
    //在生成器中初始化模型。
    //这样，提供者就可以拥有Counter的生命周期，
    //确保在不再需要时调用“dispose”。
    // Initialize the model in the builder.
    // That way, Provider can own Counter's lifecycle,
    // making sure to call `dispose` when not needed anymore.
    create: (context) => Counter(),
    child: const MyApp(),
  ));
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

  /// 布局类组件
  Map<String, FlutterBoostRouteFactory> layoutMap = {
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
  };

  /// 容器类组件
  Map<String, FlutterBoostRouteFactory> containerMap = {
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
  };

  /// 滚动类组件
  Map<String, FlutterBoostRouteFactory> scrollMap = {
    'MyListView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyListView();
          });
    },
    'MyGridView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyGridView();
          });
    },
    'MyScrollView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyScrollView();
          });
    },
    'MyPageView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyPageView();
          });
    },
    'MyTabBarView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyTabBarView();
          });
    },
    'MyAnimatedList': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyAnimatedList();
          });
    },
    'MyCustomScrollView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyCustomScrollView();
          });
    },
    'MyNestedScrollView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyNestedScrollView();
          });
    },
  };

  /// 功能型组件
  Map<String, FlutterBoostRouteFactory> functionMap = {
    'MyWillPopScope': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyWillPopScope();
          });
    },
    'MyInheritedWidget': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyInheritedWidget();
          });
    },
    'MyValueListenableBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyValueListenableBuilder();
          });
    },
    'MyFutureBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyFutureBuilder();
          });
    },
    'MyStreamBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyStreamBuilder();
          });
    },
  };

  /// 状态管理
  Map<String, FlutterBoostRouteFactory> stateMap = {
    'MyProvider': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyProvider();
          });
    },
    'MyGetX': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyGetX();
          });
    },
    'MyGetX2': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyGetX2();
          });
    },
  };

  Map<String, FlutterBoostRouteFactory> otherRouter = {
    'MyCounter': (RouteSettings settings, String? uniqueId) {
      return MaterialPageRoute(
          settings: settings,
          builder: (_) {
            Map<String, dynamic> map =
                settings.arguments as Map<String, dynamic>;
            String? pageName = map['pageName'] as String?;
            log("pageName : $pageName");
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
    'MyLayoutBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyLayoutBuilder();
          });
    },
    'MyAnimation': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyAnimation();
          });
    },
    'MyGestureDetector': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyGestureDetector();
          });
    },
    'MyDialog': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyDialog();
          });
    },
    'MyToast': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyToast();
          });
    },
    'MyNotification': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyNotification();
          });
    },
    'MyPaint': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyPaint();
          });
    },
    'MySharedPreferences': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MySharedPreferences();
          });
    },
    'MyHttp': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyDio();
          });
    },
    'MyIsolate': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyIsolate();
          });
    },
    'MyScreenUtil': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyScreenUtil();
          });
    },
  };

  Route<dynamic>? routeFactory(RouteSettings settings, String? uniqueId) {
    Map<String, FlutterBoostRouteFactory> routerMap = {
      ...layoutMap,
      ...containerMap,
      ...scrollMap,
      ...functionMap,
      ...stateMap,
      ...otherRouter
    };
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
