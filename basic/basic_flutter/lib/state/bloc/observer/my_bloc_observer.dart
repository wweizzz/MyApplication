import 'package:basic_flutter/common/log.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

/// {@template app_bloc_observer}
/// Custom [BlocObserver] that observes all bloc and cubit state changes.
/// {@endtemplate}
class AppBlocObserver extends BlocObserver {
  /// {@macro app_bloc_observer}
  const AppBlocObserver();

  @override
  void onChange(BlocBase<dynamic> bloc, Change<dynamic> change) {
    super.onChange(bloc, change);
    if (bloc is Cubit) {
      log(change);
    }
  }

  @override
  void onTransition(Bloc<dynamic, dynamic> bloc,
      Transition<dynamic, dynamic> transition,) {
    super.onTransition(bloc, transition);
    log(transition);
  }
}
