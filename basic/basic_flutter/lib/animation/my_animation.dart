import 'package:flutter/material.dart';

///Animation
class MyAnimation extends StatelessWidget {
  const MyAnimation({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Anim demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Anim demo'),
        ),
        body: const Center(child: AnimationRoute()),
      ),
    );
  }
}

class AnimationRoute extends StatefulWidget {
  const AnimationRoute({super.key});

  @override
  State<AnimationRoute> createState() => _MyFadeTest();
}

class _MyFadeTest extends State<AnimationRoute> with TickerProviderStateMixin {
  late AnimationController controller;
  late CurvedAnimation curve;

  @override
  void initState() {
    super.initState();
    controller = AnimationController(
      duration: const Duration(milliseconds: 2000),
      vsync: this,
    );
    curve = CurvedAnimation(
      parent: controller,
      curve: Curves.easeIn,
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: FadeTransition(
          opacity: curve,
          child: const FlutterLogo(
            size: 100,
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        tooltip: 'Fade',
        onPressed: () {
          controller.forward();
        },
        child: const Icon(Icons.brush),
      ),
    );
  }
}
