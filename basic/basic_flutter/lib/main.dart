import 'package:basic_flutter/animation/my_animation.dart';
import 'package:basic_flutter/asyn/my_future_builder.dart';
import 'package:basic_flutter/asyn/my_stream_builder.dart';
import 'package:basic_flutter/boost/BoostBinding.dart';
import 'package:basic_flutter/change_notifier/my_provider.dart';
import 'package:basic_flutter/common/log.dart';
import 'package:basic_flutter/get/my_get.dart';
import 'package:basic_flutter/get/my_get_view.dart';
import 'package:basic_flutter/http/my_dio.dart';
import 'package:basic_flutter/http/my_isolate.dart';
import 'package:basic_flutter/inherited_widget/my_inherited_widget.dart';
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
import 'package:basic_flutter/other/my_dialog.dart';
import 'package:basic_flutter/other/my_gesture_detector.dart';
import 'package:basic_flutter/other/my_will_pop.dart';
import 'package:basic_flutter/page/my_counter.dart';
import 'package:basic_flutter/scroll/my_animated_list.dart';
import 'package:basic_flutter/scroll/my_custom_scroll_view.dart';
import 'package:basic_flutter/scroll/my_grid_view.dart';
import 'package:basic_flutter/scroll/my_list_view.dart';
import 'package:basic_flutter/scroll/my_nested_scroll_view.dart';
import 'package:basic_flutter/scroll/my_page_view.dart';
import 'package:basic_flutter/scroll/my_scroll_view.dart';
import 'package:basic_flutter/scroll/my_tab_bar.dart';
import 'package:basic_flutter/shared_preferences/my_shared_preferences.dart';
import 'package:basic_flutter/value_notifier/my_value_listenable.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:provider/provider.dart';

import 'other/my_notification.dart';
import 'other/my_toast.dart';
import 'paint/my_custom_paint.dart';

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
    'MyScrollView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyScrollView();
          });
    },
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
    'MyAnimatedList': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyAnimatedList();
          });
    },
    'MyPageView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyPageView();
          });
    },
    'MyTabView': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyTabView();
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
    'MyAnim': (RouteSettings settings, String? uniqueId) {
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
    'MyProvider': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyProvider();
          });
    },
    'MyValueListenable': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyValueListenable();
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
    'MyCustomPaint': (RouteSettings settings, String? uniqueId) {
      return CupertinoPageRoute(
          settings: settings,
          builder: (_) {
            return const MyCustomPaint();
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
