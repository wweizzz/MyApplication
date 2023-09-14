import 'package:basic_flutter/common/urls.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class MyDio extends StatelessWidget {
  const MyDio({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Dio demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Dio Example'),
        ),
        body: const DioRoute(),
      ),
    );
  }
}

class DioRoute extends StatefulWidget {
  const DioRoute({super.key});

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
