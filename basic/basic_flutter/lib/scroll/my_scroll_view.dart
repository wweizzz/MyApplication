import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

/// SingleChildScrollView
class MyScrollView extends StatelessWidget {
  const MyScrollView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter ScrollView demo',
      home: SingleChildScrollViewRoute(title: 'Flutter ScrollView demo'),
    );
  }
}

/// SingleChildScrollView
class SingleChildScrollViewRoute extends StatelessWidget {
  const SingleChildScrollViewRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: getBody(),
    );
  }

  Widget getBody() {
    return Scrollbar(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            //动态创建一个List<Widget>
            children: buildChildren(),
          ),
        ),
      ),
    );
  }

  List<Widget> buildChildren() {
    return generateWordPairs()
        .take(20)
        .map(
          (word) =>
          Padding(
            padding: const EdgeInsets.all(10),
            child: Text(word.asPascalCase),
          ),
    )
        .toList();
  }
}
