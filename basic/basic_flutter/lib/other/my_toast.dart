import 'package:basic_flutter/common/toast.dart';
import 'package:flutter/material.dart';

class MyToast extends StatelessWidget {
  const MyToast({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Toast demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Toast Example'),
        ),
        body: const ToastRoute(),
      ),
    );
  }
}

class ToastRoute extends StatelessWidget {
  const ToastRoute({super.key});

  _showToast() {
    showToast("show Toast");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
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
