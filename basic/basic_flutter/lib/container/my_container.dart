import 'package:flutter/material.dart';

/// Container
class MyContainer extends StatelessWidget {
  const MyContainer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Container demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Container demo'),
        ),
        body: const ContainerRoute(),
      ),
    );
  }
}

class ContainerRoute extends StatelessWidget {
  const ContainerRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return getBody();
  }

  Widget getBody() {
    return Container(
      padding: const EdgeInsets.all(16),
      margin: const EdgeInsets.only(top: 50.0, left: 120.0),
      constraints: const BoxConstraints.tightFor(width: 200.0, height: 100.0),
      transform: Matrix4.rotationZ(.2),
      decoration: buildBoxDecoration(),
      alignment: Alignment.center,
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
    return const Text("Container");
  }
}
