import 'package:flutter/material.dart';

///StreamBuilder
class MyStreamBuilder extends StatelessWidget {
  const MyStreamBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter StreamBuilder demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter StreamBuilder demo'),
        ),
        body: const Center(child: FutureRoute()),
      ),
    );
  }
}

class FutureRoute extends StatelessWidget {
  const FutureRoute({super.key});

  Stream<int> counter() {
    return Stream.periodic(const Duration(seconds: 1), (i) {
      return i;
    });
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder<int>(
      stream: counter(),
      builder: (BuildContext context, AsyncSnapshot<int> snapshot) {
        if (snapshot.hasError) return Text('Error: ${snapshot.error}');
        switch (snapshot.connectionState) {
          case ConnectionState.none:
            return const Text('Stream none');
          case ConnectionState.waiting:
            return const Text('Stream waiting');
          case ConnectionState.active:
            return Text('Stream active: ${snapshot.data}');
          case ConnectionState.done:
            return const Text('Stream done');
        }
      },
    );
  }
}
