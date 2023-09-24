import 'package:basic_flutter/common/keep_alive.dart';
import 'package:flutter/material.dart';

///TabView
class MyTabView extends StatelessWidget {
  const MyTabView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter TabView demo',
      home: TabViewRoute(title: 'Flutter TabView demo'),
    );
  }
}

class TabViewRoute extends StatelessWidget {
  const TabViewRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    List tabs = ["新闻", "历史", "图片"];
    return DefaultTabController(
      length: tabs.length,
      child: Scaffold(
        appBar: AppBar(
          title: Text(title),
          bottom: TabBar(
            tabs: tabs.map((e) => Tab(text: e)).toList(),
          ),
        ),
        body: TabBarView(
          children: tabs.map((e) {
            return KeepAliveWrapper(
              child: Container(
                alignment: Alignment.center,
                child: Text(e, textScaleFactor: 1.2),
              ),
            );
          }).toList(),
        ),
      ),
    );
  }
}
