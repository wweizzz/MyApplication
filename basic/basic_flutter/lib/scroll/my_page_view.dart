import 'package:basic_flutter/common/log.dart';
import 'package:basic_flutter/common/keep_alive.dart';
import 'package:flutter/material.dart';

class MyPageView extends StatelessWidget {
  const MyPageView({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: const Center(child: PageViewRoute()),
      ),
    );
  }
}

class PageViewRoute extends StatelessWidget {
  const PageViewRoute({super.key});

  @override
  Widget build(BuildContext context) {
    var children = <Widget>[];
    for (int i = 1; i < 7; ++i) {
      //children.add(Page(text: '$i'));
      //实现页面缓存功能，只需要用 KeepAliveWrapper 包装一下即可
      children.add(KeepAliveWrapper(child: Page(text: '$i')));
    }

    return PageView(
      // scrollDirection: Axis.vertical, // 滑动方向为垂直方向
      children: children,
    );
  }
}

class Page extends StatefulWidget {
  const Page({Key? key, required this.text}) : super(key: key);

  final String text;

  @override
  State<Page> createState() => _PageState();
}

class _PageState extends State<Page> {
  @override
  Widget build(BuildContext context) {
    log("build ${widget.text}");
    return Center(child: Text(widget.text, textScaleFactor: 1.2));
  }
}
