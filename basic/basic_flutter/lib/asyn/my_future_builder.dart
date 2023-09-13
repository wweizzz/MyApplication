import 'package:flutter/material.dart';

///FutureBuilder
class MyFutureBuilder extends StatelessWidget {
  const MyFutureBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter FutureBuilder demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter FutureBuilder demo'),
        ),
        body: const Center(child: FutureRoute()),
      ),
    );
  }
}

class FutureRoute extends StatelessWidget {
  const FutureRoute({super.key});

  Future<String> mockNetworkData() async {
    return Future.delayed(
        const Duration(seconds: 2), () => "Flutter FutureBuilder demo");
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: FutureBuilder<String>(
        future: mockNetworkData(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            if (snapshot.hasError) {
              return Text("Error: ${snapshot.error}");
            } else {
              return Text("Contents: ${snapshot.data}");
            }
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }
}
