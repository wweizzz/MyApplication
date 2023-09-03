import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

///ScrollView
class MyScrollView extends StatelessWidget {
  const MyScrollView({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: const Center(child: SingleChildScrollViewRoute()),
      ),
    );
  }
}

/// SingleChildScrollView
class SingleChildScrollViewRoute extends StatelessWidget {
  const SingleChildScrollViewRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Scrollbar(
      // 显示进度条
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            //动态创建一个List<Widget>
            children: generateWordPairs()
                .take(30)
                .map((e) => Padding(
                    padding: const EdgeInsets.all(10),
                    child: Text(e.asPascalCase, textScaleFactor: 1.2)))
                .toList(),
          ),
        ),
      ),
    );
  }
}
