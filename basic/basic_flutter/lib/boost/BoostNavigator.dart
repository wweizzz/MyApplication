import 'package:flutter/cupertino.dart';

void start(var context, String routeName) {
  Navigator.of(context).pushNamed(routeName);
}

void startWithArg(var context, String routeName,
    Map<String, dynamic>? arguments) {
  Navigator.of(context).pushNamed(routeName, arguments: arguments);
}
