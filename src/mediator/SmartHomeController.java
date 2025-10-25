package mediator;

import devices.ISmartDevice;
import roles.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Mediator Pattern Implementation
 * Coordinates all smart home devices and manages role assignments
 * 
 * Design Patterns Used:
 * 1. SINGLETON: Only one instance of the controller exists
 * 2. MEDIATOR: Coordinates communication and role assignments between devices
 */
public class SmartHomeController {
    
    // ===== SINGLETON PATTERN =====
    private static SmartHomeController instance; // Single instance
    
    // ===== MEDIATOR PATTERN =====
    private final List<ISmartDevice> devices = new ArrayList<>();
    
    /**
     * Private constructor prevents external instantiation (Singleton pattern)
     */
    private SmartHomeController() {
        System.out.println("✓ SmartHomeController initialized (Singleton)\n");
    }
    
    /**
     * Get the single instance of SmartHomeController (Singleton pattern)
     * Thread-safe implementation
     * @return The singleton instance
     */
    public static synchronized SmartHomeController getInstance() {
        if (instance == null) {
            instance = new SmartHomeController();
        }
        return instance;
    }
    
    // ===== DEVICE REGISTRATION (Mediator functionality) =====
    
    /**
     * Register a device with the smart home system
     * @param device Device to register
     */
    public void registerDevice(ISmartDevice device) {
        if (!devices.contains(device)) {
            devices.add(device);
            System.out.println("✓ Controller: Registered '" + device.getDeviceName() + "'");
        } else {
            System.out.println("⚠ Controller: '" + device.getDeviceName() + "' already registered");
        }
    }
    
    /**
     * Unregister a device from the system
     * @param device Device to unregister
     */
    public void unregisterDevice(ISmartDevice device) {
        if (devices.remove(device)) {
            System.out.println("✓ Controller: Unregistered '" + device.getDeviceName() + "'");
        }
    }
    
    // ===== SCENARIO MANAGEMENT (Mediator coordinates role assignments) =====
    
    /**
     * Activate Security Mode - assigns SecurityModeRole to all devices
     * Demonstrates: Mediator coordinating role assignment across multiple devices
     */
    public void activateSecurityMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔒 ACTIVATING SECURITY MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.addRole(new SecurityModeRole());
        }
        System.out.println("✓ Security Mode activated on all devices\n");
    }
    
    /**
     * Deactivate Security Mode - removes SecurityModeRole from all devices
     * Demonstrates: Dynamic role removal through mediator
     */
    public void deactivateSecurityMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🔓 DEACTIVATING SECURITY MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.removeRole(SecurityModeRole.class);
        }
        System.out.println("✓ Security Mode deactivated\n");
    }
    
    /**
     * Trigger security alert - executes SecurityModeRole on all capable devices
     * Demonstrates: Using devices through role interface
     */
    public void triggerSecurityAlert() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🚨 SECURITY ALERT TRIGGERED!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        boolean alertExecuted = false;
        for (ISmartDevice device : devices) {
            if (device.hasRole(SecurityModeRole.class)) {
                IDeviceRole role = device.getRole(SecurityModeRole.class);
                role.execute(device);
                alertExecuted = true;
            }
        }
        
        if (!alertExecuted) {
            System.out.println("⚠ No devices with SecurityModeRole available");
        }
        System.out.println();
    }
    
    /**
     * Activate Vacation Mode - assigns VacationModeRole to all devices
     */
    public void activateVacationMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✈️ ACTIVATING VACATION MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.addRole(new VacationModeRole());
        }
        System.out.println("✓ Vacation Mode activated\n");
    }
    
    /**
     * Deactivate Vacation Mode
     */
    public void deactivateVacationMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏠 DEACTIVATING VACATION MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.removeRole(VacationModeRole.class);
        }
        System.out.println("✓ Vacation Mode deactivated\n");
    }
    
    /**
     * Simulate vacation presence - executes VacationModeRole
     */
    public void simulatePresence() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏡 Simulating presence...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (ISmartDevice device : devices) {
            if (device.hasRole(VacationModeRole.class)) {
                IDeviceRole role = device.getRole(VacationModeRole.class);
                role.execute(device);
            }
        }
        System.out.println();
    }
    
    /**
     * Activate Energy Management Mode
     */
    public void activateEnergyManagementMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("⚡ ACTIVATING ENERGY MANAGEMENT MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.addRole(new EnergyManagementRole());
        }
        System.out.println("✓ Energy Management Mode activated\n");
    }
    
    /**
     * Deactivate Energy Management Mode
     */
    public void deactivateEnergyManagementMode() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💡 DEACTIVATING ENERGY MANAGEMENT MODE");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (ISmartDevice device : devices) {
            device.removeRole(EnergyManagementRole.class);
        }
        System.out.println("✓ Energy Management Mode deactivated\n");
    }
    
    /**
     * Apply energy-saving measures - executes EnergyManagementRole
     */
    public void applyEnergySaving() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🌿 Applying energy-saving measures...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (ISmartDevice device : devices) {
            if (device.hasRole(EnergyManagementRole.class)) {
                IDeviceRole role = device.getRole(EnergyManagementRole.class);
                role.execute(device);
            }
        }
        System.out.println();
    }
    
    /**
     * Send notification through devices with NotificationRole
     * Demonstrates: Checking for role and using specific role interface
     */
    public void sendNotification(String message) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📢 Sending notification: \"" + message + "\"");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        boolean notificationSent = false;
        for (ISmartDevice device : devices) {
            if (device.hasRole(NotificationRole.class)) {
                IDeviceRole role = device.getRole(NotificationRole.class);
                role.execute(device);
                notificationSent = true;
            }
        }
        
        if (!notificationSent) {
            System.out.println("⚠ No devices with NotificationRole available");
        }
        System.out.println();
    }
    
    /**
     * Assign specific role to specific device
     * Demonstrates: Targeted role assignment through mediator
     */
    public void assignRoleToDevice(String deviceName, IDeviceRole role) {
        for (ISmartDevice device : devices) {
            if (device.getDeviceName().equals(deviceName)) {
                device.addRole(role);
                return;
            }
        }
        System.out.println("⚠ Device '" + deviceName + "' not found");
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * List all registered devices and their current roles
     */
    public void listAllDevices() {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║          REGISTERED DEVICES & ROLES                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        if (devices.isEmpty()) {
            System.out.println("  No devices registered.");
        } else {
            for (ISmartDevice device : devices) {
                System.out.println("\n📱 " + device.getDeviceInfo());
                System.out.println("   Active Roles:");
                
                if (device.getAllRoles().isEmpty()) {
                    System.out.println("     • No roles assigned");
                } else {
                    for (IDeviceRole role : device.getAllRoles()) {
                        System.out.println("     • " + role.getRoleName());
                        System.out.println("       ↳ " + role.getDescription());
                    }
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Get count of registered devices
     */
    public int getDeviceCount() {
        return devices.size();
    }
    
    /**
     * Get all devices (returns copy to prevent external modification)
     */
    public List<ISmartDevice> getAllDevices() {
        return new ArrayList<>(devices);
    }
}