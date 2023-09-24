import 'package:flutter/material.dart';

class MyStack extends StatelessWidget {
  const MyStack({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Stack demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Stack demo'),
        ),
        body: const Center(child: StackRoute()),
      ),
    );
  }
}

class StackRoute extends StatelessWidget {
  const StackRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return ConstrainedBox(
      constraints: const BoxConstraints.expand(),
      child: Stack(
        alignment: Alignment.center, //指定未定位或部分定位widget的对齐方式
        children: <Widget>[
          Container(
            color: Colors.blue,
            child: const Text("Hello world",
                textScaleFactor: 1.2, style: TextStyle(color: Colors.white)),
          ),
          const Positioned(
            left: 18.0,
            child: Text("I am Jack", textScaleFactor: 1.2),
          ),
          const Positioned(
            top: 18.0,
            child: Text("Your friend", textScaleFactor: 1.2),
          )
        ],
      ),
    );
  }
}
