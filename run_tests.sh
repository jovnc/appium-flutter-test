#!/bin/bash

# Flutter E2E Test Runner Script
# This script builds the Flutter app and runs the E2E tests

set -e

echo "🚀 Starting Flutter E2E Test Setup..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if we're in the right directory
if [ ! -d "mobile" ] || [ ! -d "e2e-tests" ]; then
    print_error "Please run this script from the project root directory"
    exit 1
fi

# Check if Appium server is running
print_status "Checking if Appium server is running..."
if ! curl -s http://localhost:4723/status > /dev/null; then
    print_warning "Appium server is not running on localhost:4723"
    print_warning "Please start Appium server with: appium"
    print_warning "Or install and start with: npm install -g appium && appium"
    exit 1
fi

# Check if iOS Simulator is available
print_status "Checking iOS Simulator availability..."
if ! xcrun simctl list devices | grep -q "iPhone 16 Simulator"; then
    print_warning "iPhone 16 Simulator not found. Available simulators:"
    xcrun simctl list devices | grep iPhone
    print_warning "You may need to update the deviceName in BaseFlutterTest.java"
fi

# Navigate to Flutter app directory
cd mobile

# Get Flutter dependencies
print_status "Getting Flutter dependencies..."
flutter pub get

# Build iOS app for testing
print_status "Building Flutter app for iOS simulator..."
flutter build ios --simulator --debug

# Check if the build was successful
if [ ! -d "build/ios/iphonesimulator/Runner.app" ]; then
    print_error "Flutter iOS build failed. Runner.app not found."
    exit 1
fi

print_status "Flutter app built successfully!"

# Navigate back to project root
cd ..

# Navigate to e2e-tests directory
cd e2e-tests

# Run the tests
print_status "Running Flutter E2E tests..."
./gradlew clean test --info

print_status "E2E tests completed!"
