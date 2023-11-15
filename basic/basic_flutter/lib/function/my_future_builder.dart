import 'package:flutter/material.dart';

/// FutureBuilder
class MyFutureBuilder extends StatelessWidget {
  const MyFutureBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter FutureBuilder demo',
      home: FutureRoute(title: 'Flutter FutureBuilder demo'),
    );
  }
}

class FutureRoute extends StatelessWidget {
  const FutureRoute({super.key, required this.title});

  final String title;

  Future<String> mockNetworkData() async {
    return Future.delayed(
        const Duration(seconds: 2), () => "Flutter FutureBuilder demo");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
    );
  }

  Widget getBody() {
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
