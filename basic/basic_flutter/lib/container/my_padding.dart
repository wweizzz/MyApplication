import 'package:flutter/material.dart';

/// Padding 填充
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
    return const Padding(
      //上下左右各添加16像素补白
      padding: EdgeInsets.all(16),
      child: Column(
        //显式指定对齐方式为左对齐，排除对齐干扰
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          Padding(
            //左边添加8像素补白
            padding: EdgeInsets.only(left: 8),
            child: Text("Hello world"),
          ),
          Padding(
            //上下各添加8像素补白
            padding: EdgeInsets.symmetric(vertical: 8),
            child: Text("I am Jack"),
          ),
          Padding(
            // 分别指定四个方向的补白
            padding: EdgeInsets.fromLTRB(20, 0, 20, 20),
            child: Text("Your friend"),
          )
        ],
      ),
    );
  }
}
