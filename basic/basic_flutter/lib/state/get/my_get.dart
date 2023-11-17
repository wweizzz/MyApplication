import 'package:flutter/material.dart';
import 'package:get/get.dart';

// 状态管理器
class Controller extends GetxController {
  // 响应式变量
  var count = 0.obs;

  void increment() {
    count++;
  }
}

/// GetX
/// https://pub.dev/packages/get
class MyGetX extends StatelessWidget {
  const MyGetX({super.key});

  @override
  Widget build(BuildContext context) {
    //GetMaterialApp 只是对 MaterialApp 封装了一层，它的子组件是默认的 MaterialApp。
    //
    // 如果你只用 Get 来进行状态管理或依赖管理，就没有必要使用GetMaterialApp。
    // GetMaterialApp 对于路由、snackBar、国际化、bottomSheet、对话框以及与路由相关的高级 apis 和没有上下文（context）的情况下是必要的。
    return GetMaterialApp(
      title: 'GetX',
      home: GetXPage(title: 'GetX'),
    );
  }
}

class GetXPage extends StatelessWidget {
  GetXPage({super.key, required this.title});

  final String title;

  final controller = Get.put(Controller());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(),
    );
  }

  Widget getBody() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          GetX<Controller>(builder: (_) => Text('clicks: ${controller.count}')),
          const Text('GetX'),
        ],
      ),
    );
  }

  Widget getFAB() {
    return FloatingActionButton(
      onPressed: () => controller.increment,
      tooltip: 'increment',
      child: const Icon(Icons.add),
    );
  }
}
