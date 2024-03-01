import 'package:flutter/material.dart';

/// 流式布局，根据子组件大小自动换行的布局
/// 用于将子组件排列成流状，可以通过设置alignment和mainAxisSpacing属性来控制子组件的对齐方式和间距。
class MyFlow extends StatelessWidget {
  const MyFlow({super.key});

  static const showWrap = true; // Set to false to show Flow

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Flow demo',
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Flow demo'),
        ),
        body: const FlowRoute(),
      ),
    );
  }
}

/// 优点：
/// 性能好；Flow是一个对子组件尺寸以及位置调整非常高效的控件，Flow用转换矩阵在对子组件进行位置调整的时候进行了优化：在Flow定位过后，如果子组件的尺寸或者位置发生了变化，在FlowDelegate中的paintChildren()方法中调用context.paintChild 进行重绘，而context.paintChild在重绘时使用了转换矩阵，并没有实际调整组件位置。
/// 灵活；由于我们需要自己实现FlowDelegate的paintChildren()方法，所以我们需要自己计算每一个组件的位置，因此，可以自定义布局策略。
/// 缺点：
/// 使用复杂。
/// Flow 不能自适应子组件大小，必须通过指定父容器大小或实现TestFlowDelegate的getSize返回固定大小。

class FlowRoute extends StatelessWidget {
  const FlowRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Flow(
      delegate: MyFlowDelegate(margin: const EdgeInsets.all(0)),
      children: buildChildren(),
    );
  }

  List<Widget> buildChildren() {
    return [
      Image.asset('images/pic1.jpg', width: 100, height: 100),
      Image.asset('images/pic2.jpg', width: 100, height: 100),
      Image.asset('images/pic3.jpg', width: 100, height: 100),
      Image.asset('images/pic4.jpg', width: 100, height: 100),
      Image.asset('images/pic5.jpg', width: 100, height: 100),
      Image.asset('images/pic6.jpg', width: 100, height: 100),
      Image.asset('images/pic7.jpg', width: 100, height: 100),
      Image.asset('images/pic8.jpg', width: 100, height: 100),
    ];
  }
}

class MyFlowDelegate extends FlowDelegate {
  EdgeInsets margin;

  MyFlowDelegate({this.margin = EdgeInsets.zero});

  double width = 0;
  double height = 0;

  @override
  void paintChildren(FlowPaintingContext context) {
    var x = margin.left;
    var y = margin.top;
    //计算每一个子widget的位置
    for (int i = 0; i < context.childCount; i++) {
      var w = context.getChildSize(i)!.width + x + margin.right;
      if (w < context.size.width) {
        context.paintChild(i, transform: Matrix4.translationValues(x, y, 0.0));
        x = w + margin.left;
      } else {
        x = margin.left;
        y += context.getChildSize(i)!.height + margin.top + margin.bottom;
        //绘制子widget(有优化)
        context.paintChild(i, transform: Matrix4.translationValues(x, y, 0.0));
        x += context.getChildSize(i)!.width + margin.left + margin.right;
      }
    }
  }

  @override
  Size getSize(BoxConstraints constraints) {
    // 指定Flow的大小，简单起见我们让宽度尽可能大，但高度指定为500，
    // 实际开发中我们需要根据子元素所占用的具体宽高来设置Flow大小
    return const Size(double.infinity, 500.0);
  }

  @override
  bool shouldRepaint(FlowDelegate oldDelegate) {
    return oldDelegate != this;
  }
}
