import 'package:get/get.dart';

class MyGetXController extends GetxController {
  var count = 0.obs;

  void increment() {
    count++;
  }
}
