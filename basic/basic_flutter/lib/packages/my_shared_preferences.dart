import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

/// Shared Preferences
/// https://pub.dev/packages/shared_preferences
class MySharedPreferences extends StatelessWidget {
  const MySharedPreferences({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Shared Preferences demo',
      home: SharedPreferencesRoute(title: 'Flutter Shared Preferences demo'),
    );
  }
}

class SharedPreferencesRoute extends StatefulWidget {
  const SharedPreferencesRoute({super.key, required this.title});

  final String title;

  @override
  State<StatefulWidget> createState() => _SharedPreferencesRouteState();
}

class _SharedPreferencesRouteState extends State<SharedPreferencesRoute> {
  int _counter = 0;

  void _incrementCounter() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int counter = prefs.getInt('counter') ?? 0;
    await prefs.setInt('counter', counter + 1);

    setState(() {
      int counter = prefs.getInt('counter') ?? 0;
      _counter = counter;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(),
    );
  }

  Widget getBody() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text('You have pushed the button this many times:'),
          Text("$_counter"),
        ],
      ),
    );
  }

  Widget getFAB() {
    return FloatingActionButton(
      onPressed: () => _incrementCounter(),
      tooltip: 'Increment',
      child: const Icon(Icons.add),
    );
  }
}
