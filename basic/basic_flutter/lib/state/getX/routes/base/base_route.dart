import 'package:get/get.dart';

abstract class BaseRoute {
  ///prefix
  String get prefix;

  ///GetPage
  List<GetPage> getRoutePages();
}
