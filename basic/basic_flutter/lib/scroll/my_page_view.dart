import 'package:flutter/material.dart';

import '../common/keep_alive.dart';
import '../common/log.dart';

/// PageView
class MyPageView extends StatelessWidget {
  const MyPageView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter PageView demo',
      home: PageViewRoute(title: 'Flutter PageView demo'),
    );
  }
}

class PageViewRoute extends StatelessWidget {
  const PageViewRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    var children = <Widget>[];
    for (int i = 1; i < 7; ++i) {
      //children.add(Page(text: '$i'));
      //实现页面缓存功能，只需要用 KeepAliveWrapper 包装一下即可
      children.add(KeepAliveWrapper(child: Page(text: '$i')));
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(children),
    );
  }

  Widget getBody(children) {
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
    return Center(
      child: Text(widget.text),
    );
  }
}
