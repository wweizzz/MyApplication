import 'package:flutter/material.dart';

/// Padding
/// Padding本身不是视图，是一种布局的描述
class MyPadding extends StatelessWidget {
  const MyPadding({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Padding demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Padding demo'),
        ),
        body: const Center(child: PaddingRoute()),
      ),
    );
  }
}

class PaddingRoute extends StatelessWidget {
  const PaddingRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return getBody();
  }

  Widget getBody() {
    return Column(
      //显式指定对齐方式为左对齐，排除对齐干扰
      crossAxisAlignment: CrossAxisAlignment.start,
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: <Widget>[
        Padding(
          //上下左右各添加16像素补白
          padding: const EdgeInsets.all(20),
          child: buildChild(),
        ),
        Padding(
          //上边添加8像素补白
          padding: const EdgeInsets.only(left: 20),
          child: buildChild(),
        ),
        Padding(
          //上下各添加8像素补白
          padding: const EdgeInsets.symmetric(horizontal: 20),
          child: buildChild(),
        ),
        Padding(
          // 分别指定四个方向的补白
          padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
          child: buildChild(),
        )
      ],
    );
  }

  Widget buildChild() {
    return Image.asset('images/pic1.jpg', width: 100, height: 100);
  }
}
