import 'package:flutter/material.dart';

/// Simplest possible model, with just one field.
///
/// [ChangeNotifier] is a class in `flutter:foundation`. [MyProviderNotifier] does
/// _not_ depend on Provider.
class MyProviderNotifier with ChangeNotifier {
  int value = 0;

  void increment() {
    value += 1;
    notifyListeners();
  }
}
