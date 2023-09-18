import 'package:flutter/material.dart';

///ValueListenableBuilder
class MyValueListenable extends StatelessWidget {
  const MyValueListenable({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter ValueListenable demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter ValueListenable demo'),
        ),
        body: const Center(child: ValueListenableRoute()),
      ),
    );
  }
}

class ValueListenableRoute extends StatefulWidget {
  const ValueListenableRoute({Key? key}) : super(key: key);

  @override
  State<ValueListenableRoute> createState() => _ValueListenableState();
}

class _ValueListenableState extends State<ValueListenableRoute> {
  final ValueNotifier<int> _counter = ValueNotifier<int>(0);

  _incrementCounter(BuildContext context) {
    _counter.value += 1;
  }

  @override
  Widget build(BuildContext context) {
    // 添加 + 按钮不会触发整个 ValueListenableRoute 组件的 build
    return Scaffold(
      body: Center(
        child: ValueListenableBuilder<int>(
          builder: (BuildContext context, int value, Widget? child) {
            return Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                child!,
                Text('$value'),
              ],
            );
          },
          valueListenable: _counter,
          child: const Text(
            'You have pushed the button this many times:',
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _incrementCounter(context),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
