package roles;

import devices.ISmartDevice;

/**
 * Interface for device roles
 * A role defines additional behavior that can be dynamically assigned to devices
 */
public interface IDeviceRole {
    /**
     * Execute the role's behavior on the given device
     * @param device The device performing this role
     */
    void execute(ISmartDevice device);
    
    /**
     * Get the name of this role
     * @return Role name as String
     */
    String getRoleName();
    
    /**
     * Get a description of what this role does
     * @return Role description as String
     */
    String getDescription();
}