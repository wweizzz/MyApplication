import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

import '../common/log.dart';

void addGlobalObserver() {
  PageVisibilityBinding.instance.addGlobalObserver(AppLifecycleObserver());
}

///全局生命周期监听
class AppLifecycleObserver with GlobalPageVisibilityObserver {
  @override
  void onBackground(Route route) {
    super.onBackground(route);
    log("AppLifecycleObserver - onBackground：${route.settings.name}");
  }

  @override
  void onForeground(Route route) {
    super.onForeground(route);
    log("AppLifecycleObserver - onForeground：${route.settings.name}");
  }

  /// 对标Android onCreate
  @override
  void onPagePush(Route route) {
    super.onPagePush(route);
    log("AppLifecycleObserver - onPagePush：${route.settings.name}");
  }

  /// 对标Android onDestroy
  @override
  void onPagePop(Route route) {
    super.onPagePop(route);
    log("AppLifecycleObserver - onPagePop：${route.settings.name}");
  }

  /// 对标Android onStop
  @override
  void onPageHide(Route route) {
    super.onPageHide(route);
    log("AppLifecycleObserver - onPageHide：${route.settings.name}");
  }

  /// 对标Android onResume
  @override
  void onPageShow(Route route) {
    super.onPageShow(route);
    log("AppLifecycleObserver - onPageShow：${route.settings.name}");
  }
}
