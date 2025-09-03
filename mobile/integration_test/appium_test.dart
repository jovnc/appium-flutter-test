import 'package:appium_flutter_server/appium_flutter_server.dart';
import 'package:mobile/main.dart';
import 'package:flutter_driver/driver_extension.dart';

void main() {
  enableFlutterDriverExtension();
  initializeTest(app: const MyApp());
}