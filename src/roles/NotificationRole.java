package roles;

import devices.ISmartDevice;
import devices.SmartLight;
import devices.SmartSpeaker;
import devices.SmartThermostat;

/**
 * NotificationRole - Enables devices to notify users of events
 */
public class NotificationRole implements IDeviceRole {
    
    @Override
    public void execute(ISmartDevice device) {
        System.out.println("  [Notification] " + device.getDeviceName() + " sending notification...");
        
        // Different devices notify in different ways
        if (device instanceof SmartLight) {
            SmartLight light = (SmartLight) device;
            light.turnOn();
            light.setColor("Blue");
            light.flash();
            System.out.println("    → Light: Flashing BLUE for notification");
            
        } else if (device instanceof SmartSpeaker) {
            SmartSpeaker speaker = (SmartSpeaker) device;
            speaker.turnOn();
            speaker.playNotification();
            System.out.println("    → Speaker: Playing notification sound");
            
        } else if (device instanceof SmartThermostat) {
            SmartThermostat thermostat = (SmartThermostat) device;
            System.out.println("    → Thermostat: Displaying notification on screen");
        }
    }
    
    @Override
    public String getRoleName() {
        return "Notification Role";
    }
    
    @Override
    public String getDescription() {
        return "Enables device to alert users about important events and messages";
    }
}