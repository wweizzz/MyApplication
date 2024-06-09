import 'package:basic_flutter/common/log.dart';
import 'package:get/get.dart';

// 编写界面业务逻辑代码，包含生命周期回调函数
class MyGetXLogic extends GetxController {
  var count = 0.obs;

  void increment() {
    count++;
  }

  final count1 = 0.obs;
  final count2 = 0.obs;

  final user = User().obs;

  updateUser() {
    user.update((value) {
      value!.name = 'Jose';
      value.age = 30;
    });
  }

  final list = [0].obs;

  /// Once the controller has entered memory, onInit will be called.
  /// It is preferable to use onInit instead of class constructors or initState method.
  /// Use onInit to trigger initial events like API searches, listeners registration
  /// or Workers registration.
  /// Workers are event handlers, they do not modify the final result,
  /// but it allows you to listen to an event and trigger customized actions.
  /// Here is an outline of how you can use them:

  /// made this if you need cancel you worker
  late Worker _ever;

  @override
  onInit() {
    super.onInit();

    /// Called every time the variable $_ is changed
    _ever = ever(count1, (_) => log("$_ has been changed (ever)"));

    everAll([count1, count2], (_) => log("$_ has been changed (everAll)"));

    /// Called first time the variable $_ is changed
    once(count1, (_) => log("$_ was changed once (once)"));

    /// Anti DDos - Called every time the user stops typing for 1 second, for example.
    debounce(count1, (_) => log("debouce$_ (debounce)"),
        time: const Duration(seconds: 1));

    /// Ignore all changes within 1 second.
    interval(count1, (_) => log("interval $_ (interval)"),
        time: const Duration(seconds: 1));
  }

  int get sum => count1.value + count2.value;

  increment1() => count1.value++;

  increment2() => count2.value++;

  disposeWorker() {
    _ever.dispose();
    // or _ever();
  }

  incrementList() => list.add(list.length);
}

class User {
  User({this.name = 'Name', this.age = 0});

  String name;
  int age;
}
