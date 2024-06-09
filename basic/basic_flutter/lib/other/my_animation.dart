import 'package:flutter/material.dart';

///Animation
class MyAnimation extends StatelessWidget {
  const MyAnimation({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Animation demo',
      home: AnimationRoute(title: 'Flutter Animation demo'),
    );
  }
}

class AnimationRoute extends StatefulWidget {
  const AnimationRoute({super.key, required this.title});

  final String title;

  @override
  State<AnimationRoute> createState() => _MyFadeState();
}

class _MyFadeState extends State<AnimationRoute> with TickerProviderStateMixin {
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
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: FadeTransition(
          opacity: curve,
          child: const FlutterLogo(size: 100),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        tooltip: 'Fade',
        onPressed: () => controller.forward(),
        child: const Icon(Icons.brush),
      ),
    );
  }
}
