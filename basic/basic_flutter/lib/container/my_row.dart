import 'package:flutter/material.dart';

/// 线性布局
class MyRow extends StatelessWidget {
  const MyRow({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Row demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Row demo'),
        ),
        body: const Center(child: RowRoute()),
      ),
    );
  }
}

class RowRoute extends StatelessWidget {
  const RowRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        Image.asset('images/pic1.jpg', width: 100, height: 100),
        Image.asset('images/pic2.jpg', width: 100, height: 100),
        Image.asset('images/pic3.jpg', width: 100, height: 100),
      ],
    );
  }
}
