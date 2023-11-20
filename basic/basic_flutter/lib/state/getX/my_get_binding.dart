import 'package:get/get.dart';

import 'my_get_logic.dart';

// 用于懒加载对应的Controller
class MyGetXBinding extends Bindings {
  @override
  void dependencies() {
    //
    Get.lazyPut(() => MyGetXLogic());
  }
}
