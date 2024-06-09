import 'package:flutter/material.dart';

/// CustomScrollView
class MyCustomScrollView extends StatelessWidget {
  const MyCustomScrollView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter CustomScrollView demo',
      home: CustomScrollViewRoute(title: 'Flutter CustomScrollView demo'),
    );
  }
}

class CustomScrollViewRoute extends StatelessWidget {
  const CustomScrollViewRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Material(
      child: CustomScrollView(
        slivers: <Widget>[
          getSliverAppBar(),
          getSliverBody(),
          getSliverFixedExtentList(),
        ],
      ),
    );
  }

  Widget getSliverAppBar() {
    return SliverAppBar(
      pinned: true,
      expandedHeight: 200.0,
      flexibleSpace: FlexibleSpaceBar(
        title: Text(title),
        background: Image.asset(
          'images/pic1.jpg',
          fit: BoxFit.cover,
        ),
      ),
    );
  }

  Widget getSliverBody() {
    var delegate = const SliverGridDelegateWithFixedCrossAxisCount(
      crossAxisCount: 2,
      childAspectRatio: 4.0,
    );
    return SliverPadding(
      padding: const EdgeInsets.all(0.0),
      sliver: SliverGrid(
        gridDelegate: delegate,
        delegate: SliverChildBuilderDelegate(
              (BuildContext context, int index) {
            return Container(
              alignment: Alignment.center,
              color: Colors.blue[100 * (index % 9)],
              child: Text('grid item $index'),
            );
          },
          childCount: 20,
        ),
      ),
    );
  }

  Widget getSliverFixedExtentList() {
    return SliverFixedExtentList(
      itemExtent: 50.0,
      delegate: SliverChildBuilderDelegate(
            (BuildContext context, int index) {
          return Container(
            alignment: Alignment.center,
            color: Colors.lightBlue[100 * (index % 9)],
            child: Text('list item $index'),
          );
        },
        childCount: 20,
      ),
    );
  }
}
