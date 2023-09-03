import 'package:flutter/material.dart';

///GridView
class MyGridView extends StatelessWidget {
  const MyGridView({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter layout demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter layout demo'),
        ),
        body: const Center(child: GridViewRoute1()),
      ),
    );
  }
}

class GridViewRoute1 extends StatelessWidget {
  const GridViewRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return GridView.extent(
      maxCrossAxisExtent: 100.0, // 子元素在横轴上的最大宽度
      childAspectRatio: 1.0,
      children: <Widget>[
        Image.asset('images/pic1.jpg'),
        Image.asset('images/pic2.jpg'),
        Image.asset('images/pic3.jpg'),
        Image.asset('images/pic4.jpg'),
        Image.asset('images/pic5.jpg'),
        Image.asset('images/pic6.jpg'),
        Image.asset('images/pic7.jpg'),
        Image.asset('images/pic8.jpg'),
        Image.asset('images/pic9.jpg'),
        Image.asset('images/pic10.jpg'),
        Image.asset('images/pic11.jpg'),
        Image.asset('images/pic12.jpg'),
      ],
    );
  }
}

/// GridView.builder
class InfiniteGridView extends StatefulWidget {
  const InfiniteGridView({super.key});

  @override
  State<InfiniteGridView> createState() => _InfiniteGridViewState();
}

class _InfiniteGridViewState extends State<InfiniteGridView> {
  final List<String> _icons = [];

  /// SliverGridDelegateWithFixedCrossAxisCount 横轴为固定数量子元素
  var gridDelegate1 = const SliverGridDelegateWithFixedCrossAxisCount(
    crossAxisCount: 2, //每行三列
    childAspectRatio: 1.0, //宽高比为1
  );

  /// SliverGridDelegateWithMaxCrossAxisExtent 横轴子元素为固定最大长度
  var gridDelegate2 = const SliverGridDelegateWithMaxCrossAxisExtent(
      maxCrossAxisExtent: 100.0, // 子元素在横轴上的最大宽度
      childAspectRatio: 1.0 //宽高比为1
      );

  @override
  void initState() {
    super.initState();
    // 初始化数据
    _retrieveIcons();
  }

  @override
  Widget build(BuildContext context) {
    return GridView.builder(
      gridDelegate: gridDelegate2,
      itemCount: _icons.length,
      itemBuilder: (context, index) {
        //如果显示到最后一个并且Icon总数小于200时继续获取数据
        if (_icons.length < 200) {
          _retrieveIcons();
        }
        return Image.asset(_icons[index]);
      },
    );
  }

  //模拟异步获取数据
  void _retrieveIcons() {
    Future.delayed(const Duration(seconds: 2)).then((e) {
      setState(() {
        _icons.addAll([
          'images/pic1.jpg',
          'images/pic2.jpg',
          'images/pic3.jpg',
          'images/pic4.jpg',
          'images/pic5.jpg',
          'images/pic6.jpg',
          'images/pic7.jpg',
          'images/pic8.jpg',
          'images/pic9.jpg',
          'images/pic10.jpg',
          'images/pic11.jpg',
          'images/pic12.jpg',
        ]);
      });
    });
  }
}
