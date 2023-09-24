import 'package:basic_flutter/common/log.dart';
import 'package:flutter/material.dart';

///GestureDetector
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
            size: 100,
          ),
        ),
      ),
    );
  }
}
