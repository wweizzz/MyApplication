import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'cubit/my_bloc_cubit.dart';
import 'page/my_bloc_page.dart';

/// BloC
/// https://pub.dev/packages/flutter_bloc
class MyBloC extends StatelessWidget {
  const MyBloC({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'BloC',
      home: BlocProvider(
        create: (_) => MyBloCCubit(),
        child: const MyBloCPage(title: 'BloC'),
      ),
    );
  }
}
