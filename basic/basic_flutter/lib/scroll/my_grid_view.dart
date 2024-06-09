import 'package:flutter/material.dart';

/// GridView
class MyGridView extends StatelessWidget {
  const MyGridView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter GridView demo',
      home: GridViewRoute(title: 'Flutter GridView demo'),
    );
  }
}

class GridViewRoute extends StatelessWidget {
  const GridViewRoute({super.key, required this.title});

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
    return const InfiniteGridViewRoute();
  }
}

/// GridView.count
/// SliverGridDelegateWithFixedCrossAxisCount 横轴为固定数量子元素
class GridViewRoute1 extends StatelessWidget {
  const GridViewRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      crossAxisCount: 2, // 横轴item的个数
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

/// GridView.extent
/// SliverGridDelegateWithMaxCrossAxisExtent 横轴子元素为固定最大长度
class GridViewRoute2 extends StatelessWidget {
  const GridViewRoute2({super.key});

  @override
  Widget build(BuildContext context) {
    return GridView.extent(
      maxCrossAxisExtent: 100.0, // 横轴item的最大宽度
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
class InfiniteGridViewRoute extends StatefulWidget {
  const InfiniteGridViewRoute({super.key});

  @override
  State<InfiniteGridViewRoute> createState() => _InfiniteGridViewRouteState();
}

class _InfiniteGridViewRouteState extends State<InfiniteGridViewRoute> {
  final List<String> _icons = [];

  /// SliverGridDelegateWithFixedCrossAxisCount 横轴为固定数量子元素
  var gridCountDelegate = const SliverGridDelegateWithFixedCrossAxisCount(
    crossAxisCount: 2, //每行三列
    childAspectRatio: 1.0, //宽高比为1
  );

  /// SliverGridDelegateWithMaxCrossAxisExtent 横轴子元素为固定最大长度
  var gridExtentDelegate = const SliverGridDelegateWithMaxCrossAxisExtent(
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
      itemCount: _icons.length,
      gridDelegate: gridExtentDelegate,
      //列表项构造器
      itemBuilder: (context, index) {
        return _itemBuilder(index);
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

  Widget _itemBuilder(int index) {
    //如果显示到最后一个并且Icon总数小于200时继续获取数据
    if (_icons.length < 200) {
      _retrieveIcons();
    }
    return Image.asset(_icons[index]);
  }
}
