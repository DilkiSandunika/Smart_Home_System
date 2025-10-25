package devices;

import roles.IDeviceRole;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all smart home devices
 * Implements common functionality and role management
 */
public abstract class SmartDevice implements ISmartDevice {
    protected String deviceId;
    protected String deviceName;
    protected boolean isOn;
    protected List<IDeviceRole> roles;
    
    /**
     * Constructor for SmartDevice
     * @param deviceId Unique identifier for the device
     * @param deviceName Human-readable name for the device
     */
    public SmartDevice(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.isOn = false;
        this.roles = new ArrayList<>();
    }
    
    // ===== Basic Device Operations =====
    
    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("  ✓ " + deviceName + " is now ON");
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("  ✓ " + deviceName + " is now OFF");
    }
    
    @Override
    public boolean isOn() {
        return isOn;
    }
    
    @Override
    public String getDeviceId() {
        return deviceId;
    }
    
    @Override
    public String getDeviceName() {
        return deviceName;
    }
    
    // ===== Role Management Operations =====
    
    @Override
    public void addRole(IDeviceRole role) {
        // Check if role already exists
        if (!hasRole(role.getClass())) {
            roles.add(role);
            System.out.println("  ✓ " + deviceName + " gained role: " + role.getRoleName());
        } else {
            System.out.println("  ⚠ " + deviceName + " already has role: " + role.getRoleName());
        }
    }
    
    @Override
    public void removeRole(Class<? extends IDeviceRole> roleType) {
        boolean removed = roles.removeIf(role -> role.getClass().equals(roleType));
        if (removed) {
            System.out.println("  ✓ " + deviceName + " lost role: " + roleType.getSimpleName());
        }
    }
    
    @Override
    public boolean hasRole(Class<? extends IDeviceRole> roleType) {
        return roles.stream().anyMatch(role -> role.getClass().equals(roleType));
    }
    
    @Override
    public IDeviceRole getRole(Class<? extends IDeviceRole> roleType) {
        return roles.stream()
            .filter(role -> role.getClass().equals(roleType))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public List<IDeviceRole> getAllRoles() {
        return new ArrayList<>(roles); // Return copy to prevent external modification
    }
    
    /**
     * Abstract method - each device type must implement its own info string
     */
    @Override
    public abstract String getDeviceInfo();
}