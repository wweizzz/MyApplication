import 'package:basic_flutter/common/urls.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class MyHttp extends StatelessWidget {
  const MyHttp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Dio Example'),
        ),
        body: const MyHttpPage(),
      ),
    );
  }
}

class MyHttpPage extends StatefulWidget {
  const MyHttpPage({super.key});

  @override
  State<MyHttpPage> createState() => _MyHttpPageState();
}

class _MyHttpPageState extends State<MyHttpPage> {
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
