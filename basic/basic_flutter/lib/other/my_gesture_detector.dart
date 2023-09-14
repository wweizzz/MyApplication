import 'package:basic_flutter/common/log.dart';
import 'package:flutter/material.dart';

///GestureDetector
class MyGestureDetector extends StatelessWidget {
  const MyGestureDetector({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter GestureDetector demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter GestureDetector demo'),
        ),
        body: const Center(child: GestureDetectorRoute()),
      ),
    );
  }
}

class GestureDetectorRoute extends StatelessWidget {
  const GestureDetectorRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: GestureDetector(
          onTap: () {
            log('onTap');
          },
          onDoubleTap: () {
            log('onDoubleTap');
          },
          onLongPress: () {
            log('onLongPress');
          },
          child: const FlutterLogo(
            size: 200,
          ),
        ),
      ),
    );
  }
}
