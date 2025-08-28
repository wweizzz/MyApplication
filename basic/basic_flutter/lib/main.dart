import 'package:basic_flutter/boost/boost_navigator.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:provider/provider.dart';

import 'routes/route_path.dart';
import 'state/bloc/observer/my_bloc_observer.dart';
import 'state/provider/notifier/my_provider_notifier.dart';

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
    child: const MyApp(),
  ));
}

/// 在 Flutter 3.0 中，
/// 同时使用 MaterialApp 的 title 和 Scaffold 的 appBar 时，
/// Scaffold 的 appBar 会覆盖 MaterialApp 的 title
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: Routes.getRoutes(),
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Flutter Demo"),
        ),
        body: ListView.builder(
          itemCount: Routes
              .getRouteList()
              .length,
          itemBuilder: (context, index) {
            return ListTile(
              title: Text(Routes.getRouteList()[index].routeName),
              subtitle: Text(Routes.getRouteList()[index].routeDescribe),
              onTap: () {
                start(context, Routes.getRouteList()[index].routePath);
              },
            );
          },
        ),
      ),
    );
  }
}
