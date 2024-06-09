import 'package:flutter/material.dart';

/// DecoratedBox 用于给子元素添加装饰
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
      child: buildChild(),
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
      ],
    );
  }

  Widget buildChild() {
    return Padding(
      padding: const EdgeInsets.all(20),
      child: Image.asset('images/pic1.jpg', width: 100, height: 100),
    );
  }
}
