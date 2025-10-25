package devices;

/**
 * Smart Light device with brightness control
 */
public class SmartLight extends SmartDevice {
    private int brightness; // 0-100
    
    /**
     * Constructor for SmartLight
     * @param deviceId Unique device identifier
     * @param deviceName Human-readable device name
     */
    public SmartLight(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.brightness = 100; // Default to full brightness
    }
    
    /**
     * Set the brightness level of the light
     * @param brightness Brightness level (0-100)
     */
    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            System.out.println("  ✓ " + deviceName + " brightness set to " + brightness + "%");
        } else {
            System.out.println("  ⚠ Invalid brightness value. Must be 0-100");
        }
    }
    
    /**
     * Get current brightness level
     * @return Current brightness (0-100)
     */
    public int getBrightness() {
        return brightness;
    }
    
    /**
     * Simulate flashing light (for security alerts, notifications)
     */
    public void flash() {
        System.out.println("  💡 " + deviceName + " is FLASHING!");
    }
    
    /**
     * Change light color (simulated)
     * @param color Color name
     */
    public void setColor(String color) {
        System.out.println("  🎨 " + deviceName + " color changed to " + color);
    }
    
    @Override
    public String getDeviceInfo() {
        return String.format("SmartLight [ID=%s, Name='%s', Status=%s, Brightness=%d%%, Roles=%d]",
            deviceId, 
            deviceName, 
            isOn ? "ON" : "OFF", 
            brightness, 
            roles.size());
    }
}