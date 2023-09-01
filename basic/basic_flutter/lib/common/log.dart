import 'package:logger/logger.dart';

var logger = Logger();

void log(var msg) {
  logger.d(msg);
}
