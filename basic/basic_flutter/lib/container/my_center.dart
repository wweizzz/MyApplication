import 'package:flutter/material.dart';

class MyCenter extends StatelessWidget {
  const MyCenter({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Center demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Center demo'),
        ),
        body: const Center(child: CenterRoute()),
      ),
    );
  }
}

class CenterRoute extends StatelessWidget {
  const CenterRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return const Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          CenterRoute1(),
          CenterRoute2(),
        ]);
  }
}

/// Center 继承自 Align，其中 alignment 值为 Alignment.center
class CenterRoute1 extends StatelessWidget {
  const CenterRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: Center(
        widthFactor: 2,
        heightFactor: 2,
        child: Image.asset('images/pic1.jpg', width: 100, height: 100),
      ),
    );
  }
}

/// 当 widthFactor 或 heightFactor 为 null 时组件的宽高将会占用尽可能多的空间
class CenterRoute2 extends StatelessWidget {
  const CenterRoute2({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: Center(
        heightFactor: 2,
        child: Image.asset('images/pic2.jpg', width: 100, height: 100),
      ),
    );
  }
}
