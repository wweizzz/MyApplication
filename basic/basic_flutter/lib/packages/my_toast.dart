import 'package:flutter/material.dart';

import '../common/toast.dart';

/// toast
/// https://pub.dev/packages/fluttertoast
class MyToast extends StatelessWidget {
  const MyToast({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Toast demo',
      home: ToastRoute(title: 'Flutter Toast Example'),
    );
  }
}

class ToastRoute extends StatelessWidget {
  const ToastRoute({super.key, required this.title});

  final String title;

  _showToast() {
    showToast("show Toast");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(),
    );
  }

  Widget getBody() {
    return const Center();
  }

  Widget getFAB() {
    return FloatingActionButton(
      onPressed: () => _showToast(),
      tooltip: 'toast',
      child: const Icon(Icons.add),
    );
  }
}
