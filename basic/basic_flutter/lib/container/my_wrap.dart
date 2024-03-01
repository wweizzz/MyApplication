import 'package:flutter/material.dart';

/// 流式布局，根据子组件大小自动换行的布局
/// 用于将子组件排列成流状，可以通过设置alignment和mainAxisSpacing属性来控制子组件的对齐方式和间距。
class MyWrap extends StatelessWidget {
  const MyWrap({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Wrap demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Wrap demo'),
        ),
        body: const WrapRoute(),
      ),
    );
  }
}

class WrapRoute extends StatelessWidget {
  const WrapRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Wrap(
      children: buildChildren(),
    );
  }

  List<Widget> buildChildren() {
    return [
      Image.asset('images/pic1.jpg', width: 100, height: 100),
      Image.asset('images/pic2.jpg', width: 100, height: 100),
      Image.asset('images/pic3.jpg', width: 100, height: 100),
      Image.asset('images/pic4.jpg', width: 100, height: 100),
      Image.asset('images/pic5.jpg', width: 100, height: 100),
      Image.asset('images/pic6.jpg', width: 100, height: 100),
      Image.asset('images/pic7.jpg', width: 100, height: 100),
      Image.asset('images/pic8.jpg', width: 100, height: 100),
    ];
  }
}
