import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

import '../common/urls.dart';

/// dio
/// https://pub.dev/packages/dio
class MyDio extends StatelessWidget {
  const MyDio({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Dio demo',
      home: DioRoute(title: 'Flutter Dio Example'),
    );
  }
}

class DioRoute extends StatefulWidget {
  const DioRoute({super.key, required this.title});

  final String title;

  @override
  State<DioRoute> createState() => _DioRouteState();
}

class _DioRouteState extends State<DioRoute> {
  String info = "";

  void _loginByDio() async {
    Response response;
    var dio = Dio();
    response = await dio.post(
      Urls().login,
      data: {
        Urls().keyUsername: Urls().valueUsername,
        Urls().keyPassword: Urls().valuePassword,
      },
      options: Options(contentType: Headers.formUrlEncodedContentType),
    );
    var result = await response.data;
    setState(() {
      info = result.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              info,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _loginByDio(),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}
