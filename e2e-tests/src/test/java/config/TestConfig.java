package config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for test properties
 */
@Slf4j
public class TestConfig {
    
    private static final String CONFIG_FILE = "test-config.properties";
    private static TestConfig instance;
    private final Properties properties;
    
    private TestConfig() {
        properties = new Properties();
        loadProperties();
    }
    
    public static TestConfig getInstance() {
        if (instance == null) {
            synchronized (TestConfig.class) {
                if (instance == null) {
                    instance = new TestConfig();
                }
            }
        }
        return instance;
    }
    
    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("❌ Configuration file '" + CONFIG_FILE + "' not found in classpath");
            }
            properties.load(inputStream);
            log.info("✅ Configuration loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load configuration: " + e.getMessage(), e);
        }
    }
    
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("❌ Property '" + key + "' not found in configuration");
        }
        return value;
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
    
    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }
    
    // Platform-specific getters
    public Platform getPlatform() {
        String platformStr = getProperty("platform").toUpperCase();
        try {
            return Platform.valueOf(platformStr);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("❌ Invalid platform '" + platformStr + "'. Supported: ANDROID, IOS");
        }
    }
    
    public String getAppiumServerUrl() {
        return getProperty("appium.serverUrl");
    }
    
    public int getImplicitWait() {
        return getIntProperty("appium.implicitWait");
    }
    
    // Android specific
    public String getAndroidPlatformVersion() {
        return getProperty("android.platformVersion");
    }
    
    public String getAndroidDeviceName() {
        return getProperty("android.deviceName");
    }
    
    public String getAndroidApp() {
        return getProperty("android.app");
    }
    
    // iOS specific
    public String getIosPlatformVersion() {
        return getProperty("ios.platformVersion");
    }
    
    public String getIosDeviceName() {
        return getProperty("ios.deviceName");
    }
    
    public String getIosBundleId() {
        return getProperty("ios.bundleId");
    }
    
    public String getIosApp() {
        return getProperty("ios.app");
    }
    
    // Test Credentials
    public String getTestUsername() {
        return getProperty("test.username");
    }
    
    public String getTestPassword() {
        return getProperty("test.password");
    }
    
    public enum Platform {
        ANDROID, IOS
    }
}
