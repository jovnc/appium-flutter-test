# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Appium Flutter E2E testing project that combines a Flutter mobile application with Java-based end-to-end tests. The project tests a simple counter app using Appium Flutter Driver.

## Architecture

### Core Components

- **mobile/**: Flutter application (simple counter app)
  - `lib/main.dart`: Main app with test keys (`counter`, `increment_button`, `instruction_text`)
  - `pubspec.yaml`: Flutter dependencies including `appium_flutter_server: ^0.0.29`
  - `integration_test/`: Flutter integration test setup for Appium connectivity

- **e2e-tests/**: Java E2E test suite using Gradle
  - `src/test/java/`: JUnit 5 test classes using Appium Java Client
  - `build.gradle`: Dependencies (Appium Java Client 9.3.0, Flutter Finder)
  - Uses FlutterFinder for element location with Flutter-specific selectors

- **test/Payload/**: Contains built iOS app bundle for testing
- **run_tests.sh**: Automated test runner script with prerequisites checking

## Common Commands

### Flutter Development
```bash
# Get dependencies
cd mobile && flutter pub get

# Build for iOS simulator testing
cd mobile && flutter build ios --simulator --debug

# Analyze code
cd mobile && flutter analyze

# Run Flutter unit tests
cd mobile && flutter test
```

### E2E Testing
```bash
# Build and run all E2E tests (automated)
./run_tests.sh

# Manual E2E test execution
cd e2e-tests && ./gradlew clean test --info

# Build E2E test project
cd e2e-tests && ./gradlew build

# Run with debug output
cd e2e-tests && ./gradlew test --debug --stacktrace
```

### Prerequisites Setup
```bash
# Install Appium and Flutter driver
npm install -g appium
appium driver install --source=npm appium-flutter-driver

# Start Appium server (required before running tests)
appium

# Check Appium setup
appium-doctor --ios
```

## Testing Architecture

### Flutter App Integration
- Uses explicit `Key()` widgets for test element identification
- Integrates `appium_flutter_server` for Appium connectivity
- Built specifically for iOS simulator testing (ARM64 architecture)

### Java Test Framework
- JUnit 5 platform with Appium Java Client
- FlutterFinder library for Flutter-specific element selection
- Test capabilities configured for iOS simulator (iPhone 16 Pro, iOS 18.6)
- Tests expect Appium server on localhost:4723/4724

### Key Test Patterns
- Use `FlutterFinder` methods like `FlutterBy.key()`, `FlutterBy.text()` for element location
- All test classes extend from base setup with AppiumDriver and FlutterFinder initialization
- Capabilities use `appium:` prefix for W3C compliance

## Dependencies and Versions

### Flutter Dependencies
- Flutter SDK ^3.9.0
- appium_flutter_server: ^0.0.29
- flutter_driver (dev dependency)

### Java Dependencies
- Appium Java Client: 9.3.0
- Flutter Finder: 1.0.5
- JUnit 5 platform
- Selenium Support: 4.15.0

## Important Notes

- The automated test runner (`run_tests.sh`) handles all prerequisite checks and build steps
- iOS testing requires Xcode, iOS Simulator, and ARM64 architecture support
- Appium server must be running before executing tests
- Flutter app must be built before running E2E tests
- Test app path is hardcoded to `/Users/jovan.ng/Documents/projects/appium-flutter-test/test/Payload/Runner.app`