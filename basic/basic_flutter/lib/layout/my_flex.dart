import 'package:flutter/material.dart';

/// 弹性布局
class MyFlex extends StatelessWidget {
  const MyFlex({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: Center(child: buildExpandedImages()),
      ),
    );
  }

  Widget buildExpandedImages() => Row(
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

  Widget buildExpandedImagesWithFlex() => Row(
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

  Widget buildExpandedImagesWithSpacer() => Row(
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
