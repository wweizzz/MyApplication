import 'package:flutter/material.dart';

/// 线性布局
class MyRowColumn extends StatelessWidget {
  const MyRowColumn({super.key});

  static const showRow = true; // Set to false to show Column

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: Center(child: showRow ? buildRow() : buildColumn()),
      ),
    );
  }

  Widget buildRow() => Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Image.asset('images/pic1.jpg', width: 100, height: 100),
          Image.asset('images/pic2.jpg', width: 100, height: 100),
          Image.asset('images/pic3.jpg', width: 100, height: 100),
        ],
      );

  Widget buildColumn() => Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Image.asset('images/pic1.jpg', width: 100, height: 100),
          Image.asset('images/pic2.jpg', width: 100, height: 100),
          Image.asset('images/pic3.jpg', width: 100, height: 100),
        ],
      );
}
