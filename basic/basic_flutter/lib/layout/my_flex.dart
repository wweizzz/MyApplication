import 'package:flutter/material.dart';

class MyFlex extends StatelessWidget {
  const MyFlex({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Flex demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Flex demo'),
        ),
        body: const Center(child: FlexRoute()),
      ),
    );
  }
}

class FlexRoute extends StatelessWidget {
  const FlexRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return const Column(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          FlexRoute1(),
          FlexRoute2(),
          FlexRoute3(),
        ]);
  }
}

class FlexRoute1 extends StatelessWidget {
  const FlexRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Expanded(
          child: Image.asset('images/pic1.jpg'),
        ),
        Expanded(
          child: Image.asset('images/pic2.jpg'),
        ),
        Expanded(
          child: Image.asset('images/pic3.jpg'),
        ),
      ],
    );
  }
}

class FlexRoute2 extends StatelessWidget {
  const FlexRoute2({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Expanded(
          child: Image.asset('images/pic1.jpg'),
        ),
        Expanded(
          flex: 2,
          child: Image.asset('images/pic2.jpg'),
        ),
        Expanded(
          child: Image.asset('images/pic3.jpg'),
        ),
      ],
    );
  }
}

class FlexRoute3 extends StatelessWidget {
  const FlexRoute3({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Expanded(
          child: Image.asset('images/pic1.jpg'),
        ),
        const Spacer(
          flex: 1,
        ),
        Expanded(
          flex: 2,
          child: Image.asset('images/pic2.jpg'),
        ),
        const Spacer(
          flex: 1,
        ),
        Expanded(
          child: Image.asset('images/pic3.jpg'),
        ),
      ],
    );
  }
}
