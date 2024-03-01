import 'package:flutter/material.dart';

/// Align
class MyAlign extends StatelessWidget {
  const MyAlign({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Align demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Align demo'),
        ),
        body: const Center(child: AlignRoute()),
      ),
    );
  }
}

class AlignRoute extends StatelessWidget {
  const AlignRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return const Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          AlignRoute1(),
          AlignRoute2(),
          AlignRoute3(),
          AlignRoute4(),
        ]);
  }
}

class AlignRoute1 extends StatelessWidget {
  const AlignRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: SizedBox(
          width: 120,
          height: 120,
          child: Align(
            alignment: Alignment.topLeft,
            child: buildChild(),
          )),
    );
  }
}

class AlignRoute2 extends StatelessWidget {
  const AlignRoute2({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: Align(
        widthFactor: 1.2,
        heightFactor: 1.2,
        alignment: Alignment.topLeft,
        child: buildChild(),
      ),
    );
  }
}

/// Alignment 以矩形的中心点作为坐标原点
class AlignRoute3 extends StatelessWidget {
  const AlignRoute3({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: Align(
        widthFactor: 1.2,
        heightFactor: 1.2,
        alignment: const Alignment(0, 0),
        child: buildChild(),
      ),
    );
  }
}

/// FractionalOffset 以左侧顶点作为坐标原点
class AlignRoute4 extends StatelessWidget {
  const AlignRoute4({super.key});

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: Colors.blue),
      child: Align(
        widthFactor: 1.2,
        heightFactor: 1.2,
        alignment: const FractionalOffset(0, 0),
        child: buildChild(),
      ),
    );
  }
}

Widget buildChild() {
  return Image.asset('images/pic1.jpg', width: 100, height: 100);
}
