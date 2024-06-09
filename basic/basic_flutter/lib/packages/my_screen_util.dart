import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import '../common/log.dart';

/// ScreenUtil
/// https://pub.dev/packages/flutter_screenutil
class MyScreenUtil extends StatelessWidget {
  const MyScreenUtil({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const ScreenUtilRoute();
  }
}

class ScreenUtilRoute extends StatelessWidget {
  const ScreenUtilRoute({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // 在 MaterialApp 组件外层包裹一层 ScreenUtilInit 组件
    return ScreenUtilInit(
      //填入设计稿中设备的屏幕尺寸,单位dp
      designSize: const Size(360, 640),
      builder: (_, child) {
        return MaterialApp(
          debugShowCheckedModeBanner: false,
          title: 'Flutter ScreenUtil demo',
          theme: ThemeData(
            primarySwatch: Colors.blue,
            textTheme: TextTheme(
              bodyMedium: TextStyle(fontSize: 30.sp),
            ),
          ),
          home: child,
        );
      },
      child: const ScreenUtilScaffold(title: 'Flutter ScreenUtil demo'),
    );
  }
}

class ScreenUtilScaffold extends StatelessWidget {
  const ScreenUtilScaffold({Key? key, required this.title}) : super(key: key);

  void logScreenInformation() {
    log('设备宽度:${1.sw}dp');
    log('设备高度:${1.sh}dp');
    log('设备的像素密度:${ScreenUtil().pixelRatio}');
    log('底部安全区距离:${ScreenUtil().bottomBarHeight}dp');
    log('状态栏高度:${ScreenUtil().statusBarHeight}dp');
    log('实际宽度和字体(dp)与设计稿(dp)的比例:${ScreenUtil().scaleWidth}');
    log('实际高度(dp)与设计稿(dp)的比例:${ScreenUtil().scaleHeight}');
    log('高度相对于设计稿放大的比例:${ScreenUtil().scaleHeight}');
    log('系统的字体缩放比例:${ScreenUtil().textScaleFactor}');
    log('屏幕宽度的0.5:${0.5.sw}dp');
    log('屏幕高度的0.5:${0.5.sh}dp');
    log('屏幕方向:${ScreenUtil().orientation}');
  }

  final String title;

  @override
  Widget build(BuildContext context) {
    logScreenInformation();

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Row(
              children: <Widget>[
                Container(
                  padding: EdgeInsets.all(ScreenUtil().setWidth(10)),
                  width: 180.w,
                  height: 200.h,
                  color: Colors.red,
                  child: Text(
                    '我的实际宽度:${180.w}dp \n'
                        '我的实际高度:${200.h}dp',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 12.sp,
                    ),
                  ),
                ),
                Container(
                  padding: EdgeInsets.all(ScreenUtil().setWidth(10)),
                  width: ScreenUtil().setWidth(180),
                  height: ScreenUtil().setHeight(200),
                  color: Colors.blue,
                  child: Text(
                    '我的设计稿宽度: 180dp \n'
                        '我的设计稿高度: 200dp',
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: ScreenUtil().setSp(12),
                    ),
                  ),
                ),
              ],
            ),
            Container(
              decoration: BoxDecoration(
                borderRadius: const BorderRadius.all(Radius.circular(16)).w,
                color: Colors.green,
              ),
              constraints:
              const BoxConstraints(maxWidth: 200, maxHeight: 200).r,
              padding: const EdgeInsets.all(10).w,
              alignment: Alignment.center,
              child: Text(
                '我是正方形,边长是200',
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Colors.white,
                  fontSize: ScreenUtil().setSp(12),
                ),
              ),
            ),
            Text('设备宽度:${ScreenUtil().screenWidth}dp'),
            Text('设备高度:${ScreenUtil().screenHeight}dp'),
            Text('设备的像素密度:${ScreenUtil().pixelRatio}'),
            Text('底部安全区距离:${ScreenUtil().bottomBarHeight}dp'),
            Text('状态栏高度:${ScreenUtil().statusBarHeight}dp'),
            Text('实际宽度与设计稿的比例:${ScreenUtil().scaleWidth}'),
            Text('实际高度与设计稿的比例:${ScreenUtil().scaleHeight}'),
            Text('系统的字体缩放比例:${ScreenUtil().textScaleFactor}'),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text(
                  '我的文字大小在设计稿上是16dp，因为设置了`textScaleFactor`,所以不会随着系统的文字缩放比例变化',
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 16.sp,
                  ),
                ),
                Text(
                  '我的文字大小在设计稿上是16dp，会随着系统的文字缩放比例变化',
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 16.sp,
                  ),
                ),
              ],
            )
          ],
        ),
      ),
      // floatingActionButton: FloatingActionButton.extended(
      //   onPressed: () {
      //     SystemChrome.setPreferredOrientations([
      //       MediaQuery.of(context).orientation == Orientation.portrait
      //           ? DeviceOrientation.landscapeRight
      //           : DeviceOrientation.portraitUp,
      //     ]);
      //     //  setState(() {});
      //   },
      //   label: const Text('旋转'),
      // ),
    );
  }
}
