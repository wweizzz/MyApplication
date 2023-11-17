import 'package:flutter/material.dart';

/// 装饰
class MyDecoratedBox extends StatelessWidget {
  const MyDecoratedBox({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter DecoratedBox demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter DecoratedBox demo'),
        ),
        body: const Center(child: DecoratedBoxRoute()),
      ),
    );
  }
}

class DecoratedBoxRoute extends StatelessWidget {
  const DecoratedBoxRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return getBody();
  }

  Widget getBody() {
    return DecoratedBox(
      decoration: buildBoxDecoration(),
      child: buildPadding(),
    );
  }

  BoxDecoration buildBoxDecoration() {
    return BoxDecoration(
        gradient: LinearGradient(
          colors: [Colors.blue, Colors.blue.shade700],
        ),
        borderRadius: BorderRadius.circular(6.0),
        boxShadow: const [
          BoxShadow(
              color: Colors.grey, offset: Offset(2.0, 2.0), blurRadius: 4.0),
        ]);
  }

  Widget buildPadding() {
    return const Padding(
      padding: EdgeInsets.symmetric(horizontal: 100.0, vertical: 20.0),
      child: Text(
        "Padding",
      ),
    );
  }
}
