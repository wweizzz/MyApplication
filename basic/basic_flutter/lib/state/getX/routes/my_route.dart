import 'package:basic_flutter/state/getX/error/error_binding.dart';
import 'package:basic_flutter/state/getX/error/error_view.dart';
import 'package:basic_flutter/state/getX/my_get_view.dart';
import 'package:basic_flutter/state/getX/routes/base/base_route.dart';
import 'package:get/get.dart';

class AppRoute extends BaseRoute {
  @override
  String get prefix => "/prefix";

  String get home => "/home";

  String get second => "$prefix/second";

  String get third => "$prefix/third";

  /// 找不到页面
  String get error404 => "$prefix/error/unknown404";

  /// 找不到页面
  GetPage get unknown =>
      GetPage(
        name: error404,
        page: () => const ErrorPage(),
        binding: ErrorBinding(),
      );

  @override
  List<GetPage> getRoutePages() {
    return [
      //Simple GetPage
      GetPage(
        name: home,
        page: () => FirstPage(),
        //binding: MyGetXBinding(),
      ),
      // GetPage with custom transitions and bindings
      GetPage(
        name: second,
        page: () => const SecondPage(),
      ),
      // GetPage with default transitions
      GetPage(
        name: third,
        page: () => const ThirdPage(),
      ),
    ];
  }
}

class Routes {
  static final app = AppRoute();

  //static final module = ModuleRoute();
  //static final component = ComponentRoute();
  static List<GetPage> getPages() {
    return [
      ...app.getRoutePages(),
      //...module.getRoutePages(),
      //...component.getRoutePages(),
    ];
  }
}
