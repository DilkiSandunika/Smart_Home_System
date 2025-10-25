package roles;

import devices.ISmartDevice;
import devices.SmartLight;
import devices.SmartSpeaker;
import devices.SmartThermostat;

/**
 * EnergyManagementRole - Enables devices to operate in power-saving mode
 */
public class EnergyManagementRole implements IDeviceRole {
    
    @Override
    public void execute(ISmartDevice device) {
        System.out.println("  [Energy] " + device.getDeviceName() + " applying energy-saving measures...");
        
        // Different devices save energy differently
        if (device instanceof SmartLight) {
            SmartLight light = (SmartLight) device;
            light.setBrightness(30); // Reduce to 30%
            System.out.println("    → Light: Reduced brightness to 30% to save energy");
            
        } else if (device instanceof SmartSpeaker) {
            SmartSpeaker speaker = (SmartSpeaker) device;
            speaker.setVolume(20); // Lower volume
            System.out.println("    → Speaker: Reduced volume to 20% for energy efficiency");
            
        } else if (device instanceof SmartThermostat) {
            SmartThermostat thermostat = (SmartThermostat) device;
            thermostat.enableEcoMode();
            System.out.println("    → Thermostat: Enabled ECO mode");
        }
    }
    
    @Override
    public String getRoleName() {
        return "Energy Management Role";
    }
    
    @Override
    public String getDescription() {
        return "Optimizes device operation to reduce power consumption and save energy";
    }
}