import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'page/my_get_page.dart';

/// GetX
/// https://pub.dev/packages/get
class MyGet extends StatelessWidget {
  const MyGet({super.key});

  @override
  Widget build(BuildContext context) {
    //GetMaterialApp 只是对 MaterialApp 封装了一层，它的子组件是默认的 MaterialApp。
    //
    // 如果你只用 Get 来进行状态管理或依赖管理，就没有必要使用GetMaterialApp。
    // GetMaterialApp 对于路由、snackBar、国际化、bottomSheet、对话框以及与路由相关的高级 apis 和没有上下文（context）的情况下是必要的。
    return GetMaterialApp(
      title: 'Flutter GetX demo',
      home: MyGetXPage(title: 'Flutter GetX demo'),
    );
  }
}
