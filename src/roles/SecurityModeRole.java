package roles;

import devices.ISmartDevice;
import devices.SmartLight;
import devices.SmartSpeaker;
import devices.SmartThermostat;

/**
 * SecurityModeRole - Enables devices to participate in security alerts
 */
public class SecurityModeRole implements IDeviceRole {
    
    @Override
    public void execute(ISmartDevice device) {
        System.out.println("  [Security] " + device.getDeviceName() + " executing security protocol...");
        
        // Different devices react differently in security mode
        if (device instanceof SmartLight) {
            SmartLight light = (SmartLight) device;
            light.turnOn();
            light.setBrightness(100);
            light.setColor("Red");
            light.flash();
            System.out.println("    → Light: Full brightness, flashing RED alert!");
            
        } else if (device instanceof SmartSpeaker) {
            SmartSpeaker speaker = (SmartSpeaker) device;
            speaker.turnOn();
            speaker.setVolume(100);
            speaker.playAlarm();
            System.out.println("    → Speaker: Playing loud security alarm!");
            
        } else if (device instanceof SmartThermostat) {
            SmartThermostat thermostat = (SmartThermostat) device;
            System.out.println("    → Thermostat: Logging security event with timestamp");
        }
    }
    
    @Override
    public String getRoleName() {
        return "Security Mode Role";
    }
    
    @Override
    public String getDescription() {
        return "Enables device to participate in home security alerts and intrusion detection";
    }
}