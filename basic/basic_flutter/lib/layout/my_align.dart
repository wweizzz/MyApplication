import 'package:flutter/material.dart';

class MyAlign extends StatelessWidget {
  const MyAlign({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: Center(child: buildAlign1()),
      ),
    );
  }

  Widget buildAlign1() => Container(
        height: 160.0,
        width: 160.0,
        color: Colors.blue,
        child: const Align(
          alignment: Alignment.topLeft,
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );

  Widget buildAlign2() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
        child: Align(
          widthFactor: 2,
          heightFactor: 2,
          alignment: Alignment.topLeft,
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );

  /// Alignment 以矩形的中心点作为坐标原点
  Widget buildAlign3() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
        child: Align(
          widthFactor: 2,
          heightFactor: 2,
          alignment: Alignment(-1, -1),
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );

  /// FractionalOffset 以左侧顶点作为坐标原点
  Widget buildAlign4() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
        child: Align(
          widthFactor: 2,
          heightFactor: 2,
          alignment: FractionalOffset(0, 0),
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );

  /// Center 继承自 Align
  Widget buildCenter() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
        child: Center(
          widthFactor: 2,
          heightFactor: 2,
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );

  /// 当widthFactor或heightFactor为null时组件的宽高将会占用尽可能多的空间
  Widget buildCenter2() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
        child: Center(
          child: FlutterLogo(
            size: 80,
          ),
        ),
      );
}
