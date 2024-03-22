import 'package:flutter/material.dart';

/// ValueListenableBuilder
class MyValueListenableBuilder extends StatelessWidget {
  const MyValueListenableBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter ValueListenable demo',
      home: ValueListenableBuilderRoute(title: 'Flutter ValueListenable demo'),
    );
  }
}

class ValueListenableBuilderRoute extends StatefulWidget {
  const ValueListenableBuilderRoute({Key? key, required this.title})
      : super(key: key);

  final String title;

  @override
  State<ValueListenableBuilderRoute> createState() =>
      _ValueListenableBuilderState();
}

class _ValueListenableBuilderState extends State<ValueListenableBuilderRoute> {
  // 定义一个ValueNotifier，当数字变化时会通知 ValueListenableBuilder
  final ValueNotifier<int> _counter = ValueNotifier<int>(0);

  void _incrementCounter() {
    _counter.value += 1;
  }

  @override
  Widget build(BuildContext context) {
    // 添加 + 按钮不会触发整个 ValueListenableRoute 组件的 build
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(context),
    );
  }

  /// ValueListenableBuilder
  Widget getBody() {
    return Center(
      child: ValueListenableBuilder<int>(
        builder: (BuildContext context, int value, Widget? child) {
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              const Text('You have pushed the button this many times:'),
              Text('$value'),
            ],
          );
        },
        valueListenable: _counter,
      ),
    );
  }

  Widget getFAB(context) {
    return FloatingActionButton(
      // 点击后值 +1，触发 ValueListenableBuilder 重新构建
      onPressed: () => _incrementCounter(),
      tooltip: 'increment',
      child: const Icon(Icons.add),
    );
  }
}
