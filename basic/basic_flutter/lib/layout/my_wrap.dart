import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

class MyWrap extends StatelessWidget {
  const MyWrap({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: const Center(child: WrapRoute()),
      ),
    );
  }
}

class WrapRoute extends StatelessWidget {
  const WrapRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Wrap(children: <Widget>[
      Image.asset('images/pic1.jpg', width: 100, height: 100),
      Image.asset('images/pic2.jpg', width: 100, height: 100),
      Image.asset('images/pic3.jpg', width: 100, height: 100),
      Image.asset('images/pic4.jpg', width: 100, height: 100),
      Image.asset('images/pic5.jpg', width: 100, height: 100),
      Image.asset('images/pic6.jpg', width: 100, height: 100),
    ]);
  }
}
