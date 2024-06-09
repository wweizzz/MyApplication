import 'package:flutter/material.dart';

import '../common/log.dart';

/// GestureDetector 手势检测
class MyGestureDetector extends StatelessWidget {
  const MyGestureDetector({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter GestureDetector demo',
      home: GestureDetectorRoute(title: 'Flutter GestureDetector demo'),
    );
  }
}

class GestureDetectorRoute extends StatelessWidget {
  const GestureDetectorRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: Center(
        child: getBody(),
      ),
    );
  }

  Widget getBody() {
    return GestureDetector(
      onTap: () {
        log('onTap');
      },
      onDoubleTap: () {
        log('onDoubleTap');
      },
      onLongPress: () {
        log('onLongPress');
      },
      child: const FlutterLogo(size: 100),
    );
  }
}
