import 'package:flutter/material.dart';

/// 弹性布局，通过 Flex 和 Expanded 实现
class MyFlex extends StatelessWidget {
  const MyFlex({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Flex demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Flex demo'),
        ),
        body: const Center(child: FlexRoute()),
      ),
    );
  }
}

class FlexRoute extends StatelessWidget {
  const FlexRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Flex(
      direction: Axis.horizontal,
      children: [
        Expanded(
          flex: 2,
          child: Image.asset('images/pic1.jpg'),
        ),
        const Spacer(
          flex: 1,
        ),
        Expanded(
          flex: 4,
          child: Image.asset('images/pic2.jpg'),
        ),
        const Spacer(
          flex: 1,
        ),
        Expanded(
          flex: 2,
          child: Image.asset('images/pic3.jpg'),
        ),
      ],
    );
  }
}
