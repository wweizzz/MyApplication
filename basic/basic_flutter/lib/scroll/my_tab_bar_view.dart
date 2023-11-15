import 'package:basic_flutter/common/keep_alive.dart';
import 'package:flutter/material.dart';

/// TabBarView
class MyTabBarView extends StatelessWidget {
  const MyTabBarView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter TabView demo',
      home: TabBarViewRoute(title: 'Flutter TabView demo'),
    );
  }
}

class TabBarViewRoute extends StatelessWidget {
  const TabBarViewRoute({super.key, required this.title});

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
