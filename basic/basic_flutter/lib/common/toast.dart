import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

void showToast(var msg) {
  Fluttertoast.showToast(
      msg: msg,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      timeInSecForIosWeb: 1,
      backgroundColor: Colors.white,
      textColor: Colors.grey[850],
      fontSize: 16.0);
}
