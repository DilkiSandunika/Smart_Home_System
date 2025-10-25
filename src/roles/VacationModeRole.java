package roles;

import devices.ISmartDevice;
import devices.SmartLight;
import devices.SmartSpeaker;
import devices.SmartThermostat;

/**
 * VacationModeRole - Simulates presence when residents are away
 */
public class VacationModeRole implements IDeviceRole {
    
    @Override
    public void execute(ISmartDevice device) {
        System.out.println("  [Vacation] " + device.getDeviceName() + " simulating presence...");
        
        if (device instanceof SmartLight) {
            SmartLight light = (SmartLight) device;
            // Random on/off to simulate someone is home
            if (Math.random() > 0.5) {
                light.turnOn();
                light.setBrightness((int)(Math.random() * 50 + 50)); // 50-100%
                System.out.println("    → Light: Turned ON at random brightness (simulating presence)");
            } else {
                light.turnOff();
                System.out.println("    → Light: Turned OFF (simulating presence)");
            }
            
        } else if (device instanceof SmartSpeaker) {
            SmartSpeaker speaker = (SmartSpeaker) device;
            if (Math.random() > 0.7) {
                speaker.playSound("TV sounds");
                System.out.println("    → Speaker: Playing ambient sounds (simulating presence)");
            }
            
        } else if (device instanceof SmartThermostat) {
            SmartThermostat thermostat = (SmartThermostat) device;
            thermostat.setTemperature(18.0); // Lower temp while away
            System.out.println("    → Thermostat: Maintaining minimal temperature");
        }
    }
    
    @Override
    public String getRoleName() {
        return "Vacation Mode Role";
    }
    
    @Override
    public String getDescription() {
        return "Simulates home presence when residents are away on vacation to deter burglars";
    }
}