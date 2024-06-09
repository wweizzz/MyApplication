import 'package:flutter/material.dart';

/// ConstrainedBox 用于给子元素添加额外的约束
class MyConstrainedBox extends StatelessWidget {
  const MyConstrainedBox({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter ConstrainedBox demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter ConstrainedBox demo'),
        ),
        body: const Center(child: ConstrainedBoxRoute()),
      ),
    );
  }
}

class ConstrainedBoxRoute extends StatelessWidget {
  const ConstrainedBoxRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return ConstrainedBox(
      constraints: boxConstraints(),
      child: buildChild(),
    );
  }

  /// 传递给子元素的约束信息
  BoxConstraints boxConstraints() => const BoxConstraints(
        minWidth: double.infinity, //宽度尽可能大
        minHeight: 50.0, //最小高度为50像素
      );

  Widget buildChild() {
    return Padding(
      padding: const EdgeInsets.all(20),
      child: Image.asset('images/pic1.jpg', width: 100, height: 100),
    );
  }
}
