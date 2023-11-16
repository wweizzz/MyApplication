import '../common/toast.dart';
import 'package:flutter/material.dart';

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
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _showToast(),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
