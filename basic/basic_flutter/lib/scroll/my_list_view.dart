import 'package:english_words/english_words.dart';
import 'package:flutter/material.dart';

/// ListView
class MyListView extends StatelessWidget {
  const MyListView({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Flutter ListView demo',
      home: ListViewRoute(title: 'Flutter ListView demo'),
    );
  }
}

class ListViewRoute extends StatelessWidget {
  const ListViewRoute({super.key, required this.title});

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
    return const InfiniteListView();
  }
}

/// 默认构造函数
class ListViewRoute1 extends StatelessWidget {
  const ListViewRoute1({super.key});

  @override
  Widget build(BuildContext context) {
    return ListView(
      shrinkWrap: true,
      padding: const EdgeInsets.all(10.0),
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

/// ListView.builder
class ListViewRoute2 extends StatelessWidget {
  const ListViewRoute2({super.key});

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: 100,
      itemExtent: 50.0, // 列表项高度
      prototypeItem: const Text(""), // 列表项原型
      //列表项构造器
      itemBuilder: (context, index) {
        return ListTile(title: Text("$index"));
      },
    );
  }
}

/// ListView.separated
/// 比 ListView.builder 多了一个 separatorBuilder 参数
class InfiniteListView extends StatefulWidget {
  const InfiniteListView({super.key});

  @override
  State<InfiniteListView> createState() => _InfiniteListViewState();
}

class _InfiniteListViewState extends State<InfiniteListView> {
  static const loadingTag = "##loading##";
  final _words = <String>[loadingTag];

  @override
  void initState() {
    super.initState();
    _retrieveData();
  }

  @override
  Widget build(BuildContext context) {
    //下划线widget预定义以供复用。
    Widget divider1 = const Divider(color: Colors.blue);
    Widget divider2 = const Divider(color: Colors.red);
    return ListView.separated(
      itemCount: _words.length,
      //列表项构造器
      itemBuilder: (context, index) {
        //return _itemBuilder(index);
        return _itemBuilderLoading(index);
      },
      //分割器构造器
      separatorBuilder: (BuildContext context, int index) {
        return index % 2 == 0 ? divider1 : divider2;
      },
    );
  }

  void _retrieveData() {
    Future.delayed(const Duration(seconds: 2)).then((e) {
      setState(() {
        //重新构建列表
        _words.insertAll(
          _words.length - 1,
          generateWordPairs().take(20).map((e) => e.asPascalCase).toList(),
        );
      });
    });
  }

  //Widget _itemBuilder(int index) {
  //  //如果显示到最后一个并且Icon总数小于200时继续获取数据
  //  if (_words.length < 200) {
  //    _retrieveData();
  //  }
  //  return ListTile(title: Text(_words[index]));
  //}

  Widget _itemBuilderLoading(int index) {
    //显示loading
    if (_words[index] == loadingTag) {
      //不足100条，继续获取数据
      if (_words.length - 1 < 100) {
        //获取数据
        _retrieveData();
        //加载时显示loading
        return Container(
          padding: const EdgeInsets.all(10.0),
          alignment: Alignment.center,
          child: const SizedBox(
            width: 24.0,
            height: 24.0,
            child: CircularProgressIndicator(strokeWidth: 2.0),
          ),
        );
      } else {
        //已经加载了100条数据，不再获取数据。
        return Container(
          alignment: Alignment.center,
          padding: const EdgeInsets.all(10.0),
          child: const Text("没有更多了"),
        );
      }
    }
    //显示单词列表项
    return ListTile(title: Text(_words[index]));
  }
}
