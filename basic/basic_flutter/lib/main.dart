import 'package:basic_flutter/state/provider/notifier/my_provider_notifier.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:provider/provider.dart';

import 'page/my_counter.dart';
import 'state/bloc/observer/my_bloc_observer.dart';

void main() {
  Bloc.observer = const AppBlocObserver();

  //runApp(const MyApp());
  runApp(ChangeNotifierProvider(
    //在生成器中初始化模型。
    //这样，提供者就可以拥有Counter的生命周期，
    //确保在不再需要时调用“dispose”。
    // Initialize the model in the builder.
    // That way, Provider can own Counter's lifecycle,
    // making sure to call `dispose` when not needed anymore.
    create: (context) => MyProviderNotifier(),
    child: const MyCounter(),
  ));
}
