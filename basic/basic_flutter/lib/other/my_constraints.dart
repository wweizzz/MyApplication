import 'package:flutter/material.dart';

class MyConstraints extends StatelessWidget {
  const MyConstraints({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter BoxConstraints demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter BoxConstraints demo'),
        ),
        body: const Center(child: ConstrainedBoxRoute()),
      ),
    );
  }
}

/// 父渲染对象传递给子渲染对象的约束信息，包含最大宽高信息，子组件大小需要在约束的范围内
class ConstrainedBoxRoute extends StatelessWidget {
  const ConstrainedBoxRoute({super.key});

  BoxConstraints boxConstraints() => const BoxConstraints(
      minWidth: double.infinity, //宽度尽可能大
      minHeight: 50.0 //最小高度为50像素
      );

  // ConstrainedBox 用于对子组件添加额外的约束。
  Widget buildConstrainedBox() {
    return ConstrainedBox(
      constraints: boxConstraints(),
      child: buildSizedBox(),
    );
  }

  // SizedBox 用于给子元素指定固定的宽高
  Widget buildSizedBox() {
    return const SizedBox(
      height: 5.0,
      child: DecoratedBox(decoration: BoxDecoration(color: Colors.blue)),
    );
  }

  @override
  Widget build(BuildContext context) {
    return buildConstrainedBox();
  }
}
