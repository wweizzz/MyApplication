import 'package:flutter/widgets.dart';
import 'package:flutter_boost/flutter_boost.dart';

import '../common/log.dart';

///单个生命周期示例
class PageLifecycleObserver extends StatefulWidget {
  const PageLifecycleObserver({Key? key}) : super(key: key);

  @override
  State<PageLifecycleObserver> createState() => _PageLifecycleObserverState();
}

class _PageLifecycleObserverState extends State<PageLifecycleObserver>
    with PageVisibilityObserver {
  @override
  void onBackground() {
    super.onBackground();
    log("LifecycleTestPage - onBackground");
  }

  @override
  void onForeground() {
    super.onForeground();
    log("LifecycleTestPage - onForeground");
  }

  @override
  void onPageHide() {
    super.onPageHide();
    log("LifecycleTestPage - onPageHide");
  }

  @override
  void onPageShow() {
    super.onPageShow();
    log("LifecycleTestPage - onPageShow");
  }

  @override
  void initState() {
    super.initState();

    ///请在didChangeDependencies中注册而不是initState中
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();

    ///注册监听器
    PageVisibilityBinding.instance
        .addObserver(this, ModalRoute.of(context) as Route);
  }

  @override
  void dispose() {
    ///移除监听器
    PageVisibilityBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    throw UnimplementedError();
  }
}
