import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../notifier/my_provider_notifier.dart';

class MyProviderPage extends StatelessWidget {
  const MyProviderPage({super.key, required this.title});

  final String title;

  void _incrementCounter(BuildContext context) {
    var counter = context.read<MyProviderNotifier>();
    counter.increment();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(context),
    );
  }

  Widget getBody() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text('You have pushed the button this many times:'),
          Consumer<MyProviderNotifier>(builder: (context, counter, child) {
            return Text('${counter.value}');
          }),
        ],
      ),
    );
  }

  Widget getFAB(context) {
    return FloatingActionButton(
      onPressed: () => _incrementCounter(context),
      tooltip: 'increment',
      child: const Icon(Icons.add),
    );
  }
}
