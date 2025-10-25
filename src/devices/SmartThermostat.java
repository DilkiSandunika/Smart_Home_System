package devices;

/**
 * Smart Thermostat device with temperature control
 */
public class SmartThermostat extends SmartDevice {
    private double targetTemperature; // in Celsius
    private double currentTemperature; // simulated current temperature
    
    /**
     * Constructor for SmartThermostat
     * @param deviceId Unique device identifier
     * @param deviceName Human-readable device name
     */
    public SmartThermostat(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.targetTemperature = 20.0; // Default comfortable temperature
        this.currentTemperature = 20.0;
    }
    
    /**
     * Set the target temperature
     * @param temperature Target temperature in Celsius
     */
    public void setTemperature(double temperature) {
        if (temperature >= 10.0 && temperature <= 35.0) {
            this.targetTemperature = temperature;
            System.out.println("  ✓ " + deviceName + " target temperature set to " + temperature + "°C");
        } else {
            System.out.println("  ⚠ Invalid temperature. Must be between 10°C and 35°C");
        }
    }
    
    /**
     * Get current target temperature
     * @return Target temperature in Celsius
     */
    public double getTargetTemperature() {
        return targetTemperature;
    }
    
    /**
     * Get current room temperature (simulated)
     * @return Current temperature in Celsius
     */
    public double getCurrentTemperature() {
        return currentTemperature;
    }
    
    /**
     * Simulate reading current temperature
     */
    public void readTemperature() {
        // Simulate temperature reading (random variation)
        currentTemperature = targetTemperature + (Math.random() * 4 - 2);
        System.out.println("  🌡️ " + deviceName + " current temperature: " + 
                         String.format("%.1f", currentTemperature) + "°C");
    }
    
    /**
     * Enable energy-saving mode (lower temperature)
     */
    public void enableEcoMode() {
        double ecoTemp = targetTemperature - 2.0;
        setTemperature(ecoTemp);
        System.out.println("  🌿 " + deviceName + " ECO mode enabled");
    }
    
    @Override
    public String getDeviceInfo() {
        return String.format("SmartThermostat [ID=%s, Name='%s', Status=%s, Target=%.1f°C, Current=%.1f°C, Roles=%d]",
            deviceId, 
            deviceName, 
            isOn ? "ON" : "OFF", 
            targetTemperature,
            currentTemperature,
            roles.size());
    }
}