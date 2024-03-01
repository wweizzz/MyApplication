import 'package:flutter/material.dart';

import '../common/log.dart';

/// InheritedWidget 数据共享
class MyInheritedWidget extends StatelessWidget {
  const MyInheritedWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter InheritedWidget demo',
      home: InheritedWidgetRoute(title: 'Flutter InheritedWidget demo'),
    );
  }
}

class InheritedWidgetRoute extends StatefulWidget {
  const InheritedWidgetRoute({super.key, required this.title});

  final String title;

  @override
  State<InheritedWidgetRoute> createState() => _InheritedWidgetRouteState();
}

class _InheritedWidgetRouteState extends State<InheritedWidgetRoute> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: getBody(),
      floatingActionButton: getFAB(),
    );
  }

  /// InheritedWidget
  Widget getBody() {
    return Center(
      child: ShareDataWidget(
        data: _counter,
        child: const Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('You have pushed the button this many times:'),
            ShareDataText(), // 子widget中依赖ShareDataWidget
          ],
        ),
      ),
    );
  }

  Widget getFAB() {
    return FloatingActionButton(
      onPressed: () => _incrementCounter(),
      tooltip: 'increment',
      child: const Icon(Icons.add),
    );
  }
}

/// InheritedWidget 组件
class ShareDataWidget extends InheritedWidget {
  const ShareDataWidget({
    Key? key,
    required this.data,
    required Widget child,
  }) : super(key: key, child: child);

  final int data; //需要在子树中共享的数据，保存点击次数

  //定义一个便捷方法，方便子树中的widget获取共享数据
  static ShareDataWidget? of(BuildContext context) {
    return context.dependOnInheritedWidgetOfExactType<ShareDataWidget>();
  }

  //该回调决定当data发生变化时，是否通知子树中依赖data的Widget重新build
  @override
  bool updateShouldNotify(ShareDataWidget oldWidget) {
    return oldWidget.data != data;
  }
}

/// 包含 InheritedWidget 的组件
class ShareDataText extends StatefulWidget {
  const ShareDataText({super.key});

  @override
  State<ShareDataText> createState() => _ShareDataTextState();
}

class _ShareDataTextState extends State<ShareDataText> {
  @override
  Widget build(BuildContext context) {
    return Text(ShareDataWidget.of(context)!.data.toString());
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    // 当 updateShouldNotify 返回 true 时会调用
    log("Dependencies change");
  }
}
