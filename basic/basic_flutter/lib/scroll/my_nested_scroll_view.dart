import 'package:flutter/material.dart';

/// NestedScrollView
class MyNestedScrollView extends StatelessWidget {
  const MyNestedScrollView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
        title: 'Flutter NestedScrollView demo',
        home: NestedScrollViewRoute(title: 'Flutter NestedScrollView demo'));
  }
}

class NestedScrollViewRoute extends StatelessWidget {
  const NestedScrollViewRoute({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Material(
      child: NestedScrollView(
        headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
          return <Widget>[
            getSliverAppBar(),
            getSliverBody(),
            getSliverFixedExtentList(),
          ];
        },
        body: getBody(),
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
          childCount: 20,
              (BuildContext context, int index) {
            return Container(
              alignment: Alignment.center,
              color: Colors.blue[100 * (index % 9)],
              child: Text('grid item $index'),
            );
          },
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

  Widget getBody() {
    return ListView.builder(
      padding: const EdgeInsets.all(10),
      physics: const ClampingScrollPhysics(), // 注意
      itemCount: 30,
      itemBuilder: (context, index) {
        return SizedBox(
          height: 50,
          child: Center(child: Text('Item $index')),
        );
      },
    );
  }
}
