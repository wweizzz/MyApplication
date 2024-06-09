import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../cubit/my_bloc_cubit.dart';

class MyBloCPage extends StatelessWidget {
  const MyBloCPage({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      // body: BlocBuilder<MyBloCCubit, int>(
      //   builder: (context, count) {
      //     return getBody(count);
      //   },
      // ),
      body: getBody(),
      floatingActionButton: getFAB(context),
    );
  }

  Widget getBody() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text('You have pushed the button this many times:'),
          BlocBuilder<MyBloCCubit, int>(builder: (context, count) {
            return Text('$count');
          }),
        ],
      ),
    );
  }

  Widget getFAB(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.end,
      mainAxisAlignment: MainAxisAlignment.end,
      children: <Widget>[
        FloatingActionButton(
          tooltip: "increment",
          onPressed: () => context.read<MyBloCCubit>().increment(),
          child: const Icon(Icons.add),
        ),
        FloatingActionButton(
          tooltip: "decrement",
          onPressed: () => context.read<MyBloCCubit>().decrement(),
          child: const Icon(Icons.remove),
        ),
      ],
    );
  }
}
