import 'package:flutter/material.dart';

class MyLayoutBuilder extends StatelessWidget {
  const MyLayoutBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: const Center(child: LayoutBuilderRoute()),
      ),
    );
  }
}

class LayoutBuilderRoute extends StatelessWidget {
  const LayoutBuilderRoute({super.key});

  @override
  Widget build(BuildContext context) {
    var children = List.filled(12, const Text("A"));
    // Column在本示例中在水平方向的最大宽度为屏幕的宽度
    return Column(
      children: [
        // 限制宽度为190，小于 200
        SizedBox(width: 290, child: ResponsiveColumn(children: children)),
      ],
    );
  }
}

class ResponsiveColumn extends StatelessWidget {
  const ResponsiveColumn({Key? key, required this.children}) : super(key: key);

  final List<Widget> children;

  @override
  Widget build(BuildContext context) {
    // 通过 LayoutBuilder 拿到父组件传递的约束，然后判断 maxWidth 是否小于200
    return LayoutBuilder(
      builder: (BuildContext context, BoxConstraints constraints) {
        if (constraints.maxWidth < 200) {
          // 最大宽度小于200，显示单列
          return Column(mainAxisSize: MainAxisSize.min, children: children);
        } else {
          // 大于200，显示双列
          var c = <Widget>[];
          for (var i = 0; i < children.length; i += 2) {
            if (i + 1 < children.length) {
              c.add(Row(
                mainAxisSize: MainAxisSize.min,
                children: [children[i], children[i + 1]],
              ));
            } else {
              c.add(children[i]);
            }
          }
          return Column(mainAxisSize: MainAxisSize.min, children: c);
        }
      },
    );
  }
}
