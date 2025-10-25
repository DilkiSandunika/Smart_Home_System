package devices;

import roles.IDeviceRole;
import java.util.List;

/**
 * Common interface for all smart home devices
 * Defines base functionality and role management operations
 */
public interface ISmartDevice {
    // ===== Basic Device Operations =====
    
    /**
     * Turn the device on
     */
    void turnOn();
    
    /**
     * Turn the device off
     */
    void turnOff();
    
    /**
     * Check if device is currently on
     * @return true if on, false if off
     */
    boolean isOn();
    
    /**
     * Get device information as a formatted string
     * @return Device info string
     */
    String getDeviceInfo();
    
    /**
     * Get the unique device ID
     * @return Device ID
     */
    String getDeviceId();
    
    /**
     * Get the device name
     * @return Device name
     */
    String getDeviceName();
    
    // ===== Role Management Operations =====
    
    /**
     * Add a role to this device
     * @param role The role to add
     */
    void addRole(IDeviceRole role);
    
    /**
     * Remove a role from this device by role type
     * @param roleType The class type of the role to remove
     */
    void removeRole(Class<? extends IDeviceRole> roleType);
    
    /**
     * Check if this device has a specific role
     * @param roleType The class type of the role to check
     * @return true if device has this role, false otherwise
     */
    boolean hasRole(Class<? extends IDeviceRole> roleType);
    
    /**
     * Get a specific role from this device
     * @param roleType The class type of the role to get
     * @return The role instance, or null if not found
     */
    IDeviceRole getRole(Class<? extends IDeviceRole> roleType);
    
    /**
     * Get all roles currently assigned to this device
     * @return List of all roles
     */
    List<IDeviceRole> getAllRoles();
}