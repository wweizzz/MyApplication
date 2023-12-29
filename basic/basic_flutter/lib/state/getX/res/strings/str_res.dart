import 'package:basic_flutter/state/getX/res/strings/str_res_keys_en.dart';
import 'package:basic_flutter/state/getX/res/strings/str_res_keys_zh.dart';
import 'package:get/get.dart';

class MyTranslations extends Translations {
  @override
  Map<String, Map<String, String>> get keys =>
      {
        'zh_CN': zh_CN_res,
        'en_US': en_US_res,
      };
}
