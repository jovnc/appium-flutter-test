# Flutter E2E Testing with Appium - Setup Guide

This guide will help you set up and run E2E tests for your Flutter application using Appium Flutter Driver and Java.

## Prerequisites

### 1. Install Required Tools

#### Appium
```bash
npm install -g appium
npm install -g @appium/doctor
```

#### Appium Flutter Driver
```bash
appium driver install --source=npm appium-flutter-driver
```

#### iOS Development Tools (for iOS testing)
- Xcode (latest version)
- iOS Simulator
- Xcode Command Line Tools: `xcode-select --install`

#### Flutter
- Flutter SDK (latest stable version)
- Ensure `flutter doctor` shows no critical issues

#### Java
- Java 11 or higher
- Gradle (included in the project via Gradle Wrapper)

### 2. Verify Installation

Run Appium Doctor to check your setup:
```bash
appium-doctor --ios
```

## Project Structure

```
appium-flutter-test/
├── mobile/                 # Flutter application
│   ├── lib/main.dart      # Main app with test keys
│   └── integration_test/   # Flutter integration test setup
├── e2e-tests/             # Java E2E tests
│   ├── src/test/java/     # Test files
│   └── build.gradle       # Dependencies and configuration
└── run_tests.sh           # Test runner script
```

## Setup Steps

### 1. Install Dependencies

#### Flutter Dependencies
```bash
cd mobile
flutter pub get
```

#### Java Dependencies
```bash
cd ../e2e-tests
./gradlew build
```

### 2. Build Flutter App
```bash
cd mobile
flutter build ios --simulator --debug
```

### 3. Start Appium Server
```bash
appium
```

The server should start on `http://localhost:4723`

### 4. Start iOS Simulator
Open Xcode → Window → Devices and Simulators → Simulators
Or use command line:
```bash
xcrun simctl boot "iPhone 16 Simulator"
```

## Running Tests

### Option 1: Use the Automated Script
```bash
./run_tests.sh
```

### Option 2: Manual Steps
```bash
# 1. Start Appium server (in a separate terminal)
appium

# 2. Build Flutter app
cd mobile
flutter build ios --simulator --debug

# 3. Run tests
cd ../e2e-tests
./gradlew test
```

## Key Components Explained

### Flutter App Modifications
The Flutter app (`mobile/lib/main.dart`) has been updated with test keys:
- `counter`: The counter text widget
- `increment_button`: The floating action button
- `instruction_text`: The instruction text widget

### Java Test Configuration
- **BaseFlutterTest.java**: Base class with Appium setup and Flutter finder methods
- **FlutterE2ETest.java**: Actual test cases
- **build.gradle**: Dependencies including Appium Java Client and Flutter Finder

### Key Dependencies
- `io.appium:java-client:9.3.0`: Appium Java client
- `io.github.ashwithpoojary98:appium_flutterfinder_java:1.0.10`: Flutter finder for Java
- `appium_flutter_server:^0.0.29`: Flutter integration server

## Troubleshooting

### Common Issues

#### 1. "Could not start a new session" Error
- **Solution**: Ensure all capabilities use the `appium:` prefix for W3C compliance
- **Check**: Appium server is running on localhost:4723
- **Verify**: iOS Simulator is available and matches the deviceName in capabilities

#### 2. Element Not Found Errors
- **Solution**: Ensure Flutter app widgets have proper `key` attributes
- **Check**: Use `FlutterBy.key()`, `FlutterBy.text()`, etc. for element finding
- **Debug**: Use Appium Inspector to verify element selectors

#### 3. Flutter Driver Connection Issues
- **Solution**: Ensure `appium_flutter_server` is added to `pubspec.yaml`
- **Check**: Integration test setup in `integration_test/appium_test.dart`
- **Verify**: Flutter app is built with integration test configuration

#### 4. Build Issues
- **iOS**: Ensure Xcode is properly configured and iOS Simulator is available
- **Flutter**: Run `flutter doctor` to check for issues
- **Java**: Ensure Java 11+ is installed and JAVA_HOME is set

### Debugging Tips

1. **Enable Verbose Logging**:
   ```bash
   ./gradlew test --debug --stacktrace
   ```

2. **Check Appium Logs**:
   Start Appium with verbose logging:
   ```bash
   appium --log-level debug
   ```

3. **Verify App Build**:
   Check that `mobile/build/ios/iphonesimulator/Runner.app` exists after building

4. **Test Individual Components**:
   - Test Flutter app manually in simulator
   - Verify Appium server connectivity
   - Run individual test methods

## Test Structure

### Test Classes
- `BaseFlutterTest`: Setup/teardown and helper methods
- `FlutterE2ETest`: Main test cases for the counter app

### Test Cases
1. **App Launch Test**: Verifies app starts and counter is at 0
2. **Counter Increment Test**: Tests single increment functionality
3. **Multiple Increments Test**: Tests multiple button clicks
4. **UI Accessibility Test**: Verifies all elements are accessible

## Configuration Options

### Device Configuration
Update `BaseFlutterTest.java` to change:
- Device name: `capabilities.setCapability("appium:deviceName", "iPhone 15 Simulator")`
- Platform version: `capabilities.setCapability("appium:platformVersion", "17.0")`
- App path: Modify the `appPath` system property

### Test Configuration
Update `build.gradle` to modify:
- Test timeouts
- System properties
- Parallel execution settings

## Next Steps

1. **Add More Test Cases**: Extend `FlutterE2ETest.java` with additional scenarios
2. **Cross-Platform Testing**: Add Android test configuration
3. **CI/CD Integration**: Set up automated testing in your CI pipeline
4. **Page Object Pattern**: Implement page objects for better test organization

## Support

If you encounter issues:
1. Check the troubleshooting section above
2. Verify all prerequisites are installed correctly
3. Review Appium and test logs for specific error messages
4. Ensure Flutter app builds and runs correctly in the simulator

Happy testing! 🚀
