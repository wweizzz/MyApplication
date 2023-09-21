import 'package:flutter/material.dart';
import 'package:get/get.dart';

class MyGetX extends StatelessWidget {
  const MyGetX({super.key});

  @override
  Widget build(BuildContext context) {
    //GetMaterialApp 只是对 MaterialApp 封装了一层，它的子组件是默认的 MaterialApp。
    //
    // 如果你只用 Get 来进行状态管理或依赖管理，就没有必要使用GetMaterialApp。
    // GetMaterialApp 对于路由、snackBar、国际化、bottomSheet、对话框以及与路由相关的高级 apis 和没有上下文（context）的情况下是必要的。
    return GetMaterialApp(home: GetXRoute());
  }
}

// 状态管理器
class Controller extends GetxController {
  // 响应式变量
  var count = 0.obs;

  void increment() {
    count++;
  }
}

class GetXRoute extends StatelessWidget {
  GetXRoute({super.key});

  final controller = Get.put(Controller());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("counter")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            GetX<Controller>(
                builder: (_) => Text(
                      'clicks: ${controller.count}',
                    )),
            ElevatedButton(
              child: const Text('Next Route'),
              onPressed: () {
                Get.to(Second());
              },
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: controller.increment,
        child: const Icon(Icons.add),
      ),
    );
  }
}

class Second extends StatelessWidget {
  Second({super.key});

  final Controller ctrl = Get.find();

  @override
  Widget build(context) {
    return Scaffold(body: Center(child: Text("${ctrl.count}")));
  }
}
