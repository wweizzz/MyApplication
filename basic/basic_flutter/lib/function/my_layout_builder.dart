import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

/// LayoutBuilder
class MyLayoutBuilder extends StatelessWidget {
  const MyLayoutBuilder({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter LayoutBuilder demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter LayoutBuilder demo'),
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
    return Column(
      children: [buildItem()],
    );
  }

  Widget buildItem() {
    return SizedBox(
      width: 180,
      child: ResponsiveColumn(children: buildChildren()),
    );
  }

  List<Widget> buildChildren() {
    return generateWordPairs()
        .take(12)
        .map((word) =>
        Padding(
          padding: const EdgeInsets.all(10),
          child: Text(word.asPascalCase),
        ))
        .toList();
  }
}

class ResponsiveColumn extends StatelessWidget {
  const ResponsiveColumn({Key? key, required this.children}) : super(key: key);

  final List<Widget> children;

  @override
  Widget build(BuildContext context) {
    // 通过 LayoutBuilder 拿到父组件传递的约束，然后判断 maxWidth 是否小于200
    return getLayoutBuilder();
  }

  Widget getLayoutBuilder() {
    return LayoutBuilder(
      builder: (BuildContext context, BoxConstraints constraints) {
        if (constraints.maxWidth < 200) {
          // 最大宽度小于200，显示单列
          return Column(mainAxisSize: MainAxisSize.min, children: children);
        } else {
          // 最大宽度大于200，显示双列
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
