import 'package:flutter/material.dart';

/// SizeBox 用于给子元素设置大小
class MySizedBox extends StatelessWidget {
  const MySizedBox({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter SizedBox demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter SizedBox demo'),
        ),
        body: const Center(child: SizedBoxRoute()),
      ),
    );
  }
}

class SizedBoxRoute extends StatelessWidget {
  const SizedBoxRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 100.0,
      height: 100.0,
      child: buildChild(),
    );
  }

  Widget buildChild() {
    return Padding(
      padding: const EdgeInsets.all(8),
      child: Image.asset('images/pic1.jpg', width: 100, height: 100),
    );
  }
}
