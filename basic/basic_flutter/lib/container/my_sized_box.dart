import 'package:flutter/material.dart';

/// SizeBox 用于给子元素指定固定的宽高
class MySizedBox extends StatelessWidget {
  const MySizedBox({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter SizeBox demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter SizeBox demo'),
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
      width: 80.0,
      height: 80.0,
      child: blueBox(),
    );
  }

  DecoratedBox blueBox() => const DecoratedBox(
        decoration: BoxDecoration(color: Colors.blue),
      );
}
