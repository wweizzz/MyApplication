import 'package:flutter_bloc/flutter_bloc.dart';

/// {@template counter_bloc}
/// A simple [Cubit] that manages an `int` as its state.
/// {@endtemplate}
class MyBloCCubit extends Cubit<int> {
  MyBloCCubit() : super(0);

  void increment() => emit(state + 1);

  void decrement() => emit(state - 1);
}
