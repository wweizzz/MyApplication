import 'package:flutter/material.dart';

import '../common/keep_alive.dart';

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
        appBar: getAppBar(tabs),
        body: getBody(tabs),
      ),
    );
  }

  AppBar getAppBar(List tabs) {
    return AppBar(
      title: Text(title),
      bottom: TabBar(
        tabs: tabs.map((tab) => Tab(text: tab)).toList(),
      ),
    );
  }

  Widget getBody(List tabs) {
    return TabBarView(
      children: tabs.map((e) {
        return KeepAliveWrapper(
          child: Center(
            child: Text(e),
          ),
        );
      }).toList(),
    );
  }
}
