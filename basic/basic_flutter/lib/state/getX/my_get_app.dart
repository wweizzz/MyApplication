import 'package:basic_flutter/state/getX/my_get_binding.dart';
import 'package:basic_flutter/state/getX/res/strings/str_res.dart';
import 'package:basic_flutter/state/getX/routes/my_route.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

/// GetX
/// https://pub.dev/packages/get
class MyGetX2 extends StatelessWidget {
  const MyGetX2({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      // It is not mandatory to use named routes, but dynamic urls are interesting.
      initialRoute: Routes.app.home,
      unknownRoute: Routes.app.unknown,
      defaultTransition: Transition.native,
      translations: MyTranslations(),
      locale: const Locale('zh', 'CN'),
      fallbackLocale: const Locale('en', 'US'),
      getPages: Routes.getPages(),
      initialBinding: MyGetXBinding(),
    );
  }
}