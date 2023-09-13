import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

///Shared Preferences
class MySharedPreferences extends StatelessWidget {
  const MySharedPreferences({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Shared Preferences demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Shared Preferences demo'),
        ),
        body: const Center(child: SharedPreferencesRoute()),
      ),
    );
  }
}

class SharedPreferencesRoute extends StatefulWidget {
  const SharedPreferencesRoute({super.key});

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
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              "$_counter",
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _incrementCounter(),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
