import 'package:flutter/material.dart';

/// 线性布局
class MyColumn extends StatelessWidget {
  const MyColumn({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Column demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Column demo'),
        ),
        body: const Center(child: ColumnRoute()),
      ),
    );
  }
}

class ColumnRoute extends StatelessWidget {
  const ColumnRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        Image.asset('images/pic1.jpg', width: 100, height: 100),
        Image.asset('images/pic2.jpg', width: 100, height: 100),
        Image.asset('images/pic3.jpg', width: 100, height: 100),
      ],
    );
  }
}
