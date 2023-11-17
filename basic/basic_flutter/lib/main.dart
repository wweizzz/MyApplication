import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:provider/provider.dart';

import 'boost/BoostBinding.dart';
import 'common/log.dart';
import 'container/my_align.dart';
import 'container/my_center.dart';
import 'container/my_constrained_box.dart';
import 'container/my_container.dart';
import 'container/my_decorated_box.dart';
import 'container/my_gesture_detector.dart';
import 'container/my_layout_builder.dart';
import 'container/my_padding.dart';
import 'container/my_sized_box.dart';
import 'function/my_dialog.dart';
import 'function/my_future_builder.dart';
import 'function/my_inherited_widget.dart';
import 'function/my_stream_builder.dart';
import 'function/my_value_listenable_builder.dart';
import 'function/my_will_pop_scope.dart';
import 'http/my_dio.dart';
import 'layout/my_column.dart';
import 'layout/my_flex.dart';
import 'layout/my_flow.dart';
import 'layout/my_row.dart';
import 'layout/my_stack.dart';
import 'layout/my_wrap.dart';
import 'other/my_animation.dart';
import 'other/my_isolate.dart';
import 'packages/my_notification.dart';
import 'packages/my_screen_util.dart';
import 'packages/my_shared_preferences.dart';
import 'packages/my_toast.dart';
import 'page/my_counter.dart';
import 'scroll/my_animated_list.dart';
import 'scroll/my_custom_scroll_view.dart';
import 'scroll/my_grid_view.dart';
import 'scroll/my_list_view.dart';
import 'scroll/my_nested_scroll_view.dart';
import 'scroll/my_page_view.dart';
import 'scroll/my_scroll_view.dart';
import 'scroll/my_tab_bar_view.dart';
import 'state/get/my_get.dart';
import 'state/getX/my_get_view.dart';
import 'state/provider/my_provider.dart';

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
    'MyPadding': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyPadding();
          });
    },
    'MyDecoratedBox': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyDecoratedBox();
          });
    },
    'MyContainer': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyContainer();
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
    'MyConstrainedBox': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyConstrainedBox();
          });
    },
    'MySizedBox': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MySizedBox();
          });
    },
    'MyLayoutBuilder': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyLayoutBuilder();
          });
    },
    'MyGestureDetector': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyGestureDetector();
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
    'MyDialog': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyDialog();
          });
    },
  };

  /// 网路请求
  Map<String, FlutterBoostRouteFactory> httpMap = {
    'MyDio': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyDio();
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

  /// 三方框架
  Map<String, FlutterBoostRouteFactory> packagesMap = {
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
    'MySharedPreferences': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MySharedPreferences();
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

  Map<String, FlutterBoostRouteFactory> otherMap = {
    'MyAnimation': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyAnimation();
          });
    },
    'MyIsolate': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyIsolate();
          });
    },
  };

  Map<String, FlutterBoostRouteFactory> routerMap = {
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
  };

  Route<dynamic>? routeFactory(RouteSettings settings, String? uniqueId) {
    Map<String, FlutterBoostRouteFactory> map = {
      ...layoutMap,
      ...containerMap,
      ...scrollMap,
      ...functionMap,
      ...httpMap,
      ...stateMap,
      ...packagesMap,
      ...otherMap,
      ...routerMap,
    };
    FlutterBoostRouteFactory? func = map[settings.name];
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
