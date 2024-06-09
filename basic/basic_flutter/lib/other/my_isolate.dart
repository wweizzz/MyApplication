import 'dart:async';
import 'dart:convert';
import 'dart:isolate';

import 'package:flutter/material.dart';
import 'package:http/http.dart';

import '../common/urls.dart';

///Isolate
class MyIsolate extends StatelessWidget {
  const MyIsolate({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter Isolate demo',
      home: IsolatePage(title: 'Flutter Isolate demo'),
    );
  }
}

class IsolatePage extends StatefulWidget {
  const IsolatePage({super.key, required this.title});

  final String title;

  @override
  State<IsolatePage> createState() => _IsolatePageState();
}

class _IsolatePageState extends State<IsolatePage> {
  List widgets = [];

  @override
  void initState() {
    super.initState();
    loadData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: getBody(),
    );
  }

  Widget getBody() {
    bool showLoadingDialog = widgets.isEmpty;
    if (showLoadingDialog) {
      return getProgressDialog();
    } else {
      return getListView();
    }
  }

  Widget getProgressDialog() {
    return const Center(child: CircularProgressIndicator());
  }

  ListView getListView() {
    return ListView.builder(
      itemCount: widgets.length,
      itemBuilder: (context, position) {
        return Padding(
          padding: const EdgeInsets.all(10),
          child: Text("Row ${widgets[position]["title"]}"),
        );
      },
    );
  }

  Future<void> loadData() async {
    ReceivePort receivePort = ReceivePort();
    await Isolate.spawn(dataLoader, receivePort.sendPort);
    //await Isolate.spawn((sendPort) {}, receivePort.sendPort);

    SendPort sendPort = await receivePort.first;

    ReceivePort port = ReceivePort();
    sendPort.send([port.sendPort, Urls().posts]);

    List msg = await port.first;

    setState(() {
      widgets = msg;
    });
  }

  // 隔离的入口点。
  // The entry point for the isolate.
  static Future<void> dataLoader(SendPort sendPort) async {
    // 打开接收端口以接收传入消息。
    // Open the ReceivePort for incoming messages.
    ReceivePort port = ReceivePort();

    // 通知任何其他隔离此隔离侦听的端口。
    // Notify any other isolates what port this isolate listens to.
    sendPort.send(port.sendPort);

    await for (var msg in port) {
      SendPort replyTo = msg[0];
      String dataURL = msg[1];

      Response response = await get(Uri.parse(dataURL));
      // Lots of JSON to parse
      replyTo.send(jsonDecode(response.body));
    }
  }
}
