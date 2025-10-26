import devices.*;
import roles.*;
import mediator.*;

public class Main {
    public static void main(String[] args) {
        printHeader();
        
        // ===== STEP 1: Singleton Pattern Demonstration =====
        printStep(1, "SINGLETON PATTERN - Getting Controller Instance");
        SmartHomeController controller = SmartHomeController.getInstance();
        
        // Verify it's truly a singleton
        SmartHomeController controller2 = SmartHomeController.getInstance();
        System.out.println("First instance:  " + controller);
        System.out.println("Second instance: " + controller2);
        System.out.println("Same instance? " + (controller == controller2) + " ✓ (Singleton verified)\n");
        
        pause();
        
        // ===== STEP 2: Create Smart Devices =====
        printStep(2, "Creating Smart Home Devices");
        SmartLight livingRoomLight = new SmartLight("DEV-001", "Living Room Light");
        SmartThermostat hallThermostat = new SmartThermostat("DEV-002", "Hall Thermostat");
        SmartSpeaker kitchenSpeaker = new SmartSpeaker("DEV-003", "Kitchen Speaker");
        System.out.println();
        
        pause();
        
        // ===== STEP 3: Register Devices with Mediator =====
        printStep(3, "Registering Devices with Mediator (Controller)");
        controller.registerDevice(livingRoomLight);
        controller.registerDevice(hallThermostat);
        controller.registerDevice(kitchenSpeaker);
        System.out.println();
        
        pause();
        
        // ===== STEP 4: Use Devices Through Common Interface =====
        printStep(4, "Using Devices Through Common Interface (ISmartDevice)");
        System.out.println("All devices accessed through ISmartDevice interface:\n");
        
        // Polymorphism - all accessed through common interface
        livingRoomLight.turnOn();
        livingRoomLight.setBrightness(75);
        System.out.println();
        
        hallThermostat.turnOn();
        hallThermostat.setTemperature(22);
        hallThermostat.readTemperature();
        System.out.println();
        
        kitchenSpeaker.turnOn();
        kitchenSpeaker.setVolume(50);
        kitchenSpeaker.playSound("Welcome home!");
        System.out.println();
        
        pause();
        
        // ===== STEP 5: Show Devices Without Roles =====
        printStep(5, "Listing Devices (No Roles Assigned Yet)");
        controller.listAllDevices();
        
        pause();
        
        // ===== STEP 6: Manually Assign Individual Roles =====
        printStep(6, "Manually Assigning Individual Roles to Devices");
        System.out.println("Adding NotificationRole to Living Room Light:");
        livingRoomLight.addRole(new NotificationRole());
        System.out.println();
        
        System.out.println("Adding NotificationRole to Kitchen Speaker:");
        kitchenSpeaker.addRole(new NotificationRole());
        System.out.println();
        
        pause();
        
        // ===== STEP 7: Check if Device Has Role =====
        printStep(7, "Checking if Devices Have Specific Roles (hasRole method)");
        System.out.println("Does Living Room Light have NotificationRole? " + 
                          livingRoomLight.hasRole(NotificationRole.class) + " ✓");
        System.out.println("Does Hall Thermostat have NotificationRole? " + 
                          hallThermostat.hasRole(NotificationRole.class) + " (Expected: false)");
        System.out.println("Does Kitchen Speaker have NotificationRole? " + 
                          kitchenSpeaker.hasRole(NotificationRole.class) + " ✓");
        System.out.println();
        
        pause();
        
        // ===== STEP 8: Use Device Through Role Interface =====
        printStep(8, "Using Device Through Role Interface (getRole().execute())");
        System.out.println("Checking Living Room Light for NotificationRole and executing it:\n");
        
        if (livingRoomLight.hasRole(NotificationRole.class)) {
            IDeviceRole notifRole = livingRoomLight.getRole(NotificationRole.class);
            System.out.println("✓ Role found: " + notifRole.getRoleName());
            System.out.println("Executing role on device:\n");
            notifRole.execute(livingRoomLight);
        }
        System.out.println();
        
        pause();
        
        // ===== STEP 9: Mediator Coordinates Role Assignment =====
        printStep(9, "MEDIATOR PATTERN - Coordinating Security Mode Activation");
        System.out.println("Controller assigns SecurityModeRole to ALL devices:\n");
        controller.activateSecurityMode();
        
        // Show devices now have multiple roles
        controller.listAllDevices();
        
        pause();
        
        // ===== STEP 10: Multiple Simultaneous Roles =====
        printStep(10, "Demonstrating Multiple Simultaneous Roles");
        System.out.println("Living Room Light now has multiple roles:");
        for (IDeviceRole role : livingRoomLight.getAllRoles()) {
            System.out.println("  • " + role.getRoleName());
        }
        System.out.println("\nTotal roles on Living Room Light: " + 
                          livingRoomLight.getAllRoles().size() + " ✓");
        System.out.println("(Device successfully has multiple roles simultaneously)\n");
        
        pause();
        
        // ===== STEP 11: Trigger Event Through Mediator =====
        printStep(11, "Triggering Security Alert via Mediator");
        System.out.println("Mediator finds all devices with SecurityModeRole and executes them:\n");
        controller.triggerSecurityAlert();
        
        pause();
        
        // ===== STEP 12: Dynamic Role Removal =====
        printStep(12, "Dynamically Removing Roles (deactivateSecurityMode)");
        controller.deactivateSecurityMode();
        
        System.out.println("Living Room Light roles after security deactivation:");
        for (IDeviceRole role : livingRoomLight.getAllRoles()) {
            System.out.println("  • " + role.getRoleName());
        }
        System.out.println("✓ SecurityModeRole removed dynamically\n");
        
        pause();
        
        // ===== STEP 13: Vacation Mode Scenario =====
        printStep(13, "Activating Vacation Mode Scenario");
        controller.activateVacationMode();
        
        System.out.println("Simulating presence while on vacation:");
        controller.simulatePresence();
        
        pause();
        
        // ===== STEP 14: Energy Management Scenario =====
        printStep(14, "Activating Energy Management Scenario");
        controller.activateEnergyManagementMode();
        controller.applyEnergySaving();
        
        pause();
        
        // ===== STEP 15: Show All Current Roles =====
        printStep(15, "Current State - All Devices with All Roles");
        controller.listAllDevices();
        
        pause();
        
        // ===== STEP 16: Send Notification =====
        printStep(16, "Sending Notification Through Mediator");
        controller.sendNotification("Package delivered at front door!");
        
        pause();
        
        // ===== STEP 17: Expandability Demonstration =====
        printStep(17, "Demonstrating Easy Role Expandability");
        System.out.println("Adding a new custom role to specific device via mediator:\n");
        controller.assignRoleToDevice("Kitchen Speaker", new EnergyManagementRole());
        
        System.out.println("\nKitchen Speaker now has these roles:");
        for (IDeviceRole role : kitchenSpeaker.getAllRoles()) {
            System.out.println("  • " + role.getRoleName());
        }
        System.out.println("\n✓ New roles can be easily added without modifying existing code\n");
        
        pause();
        
        // ===== FINAL SUMMARY =====
        printSummary();
    }
    
    // ===== HELPER METHODS FOR FORMATTING =====
    
    private static void printHeader() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                ║");
        System.out.println("║          SMART HOME SYSTEM - DESIGN PATTERNS DEMO             ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Patterns Implemented:                                         ║");
        System.out.println("║  • SINGLETON PATTERN (SmartHomeController)                     ║");
        System.out.println("║  • MEDIATOR PATTERN (Device coordination)                      ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }
    
    private static void printStep(int stepNumber, String description) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP " + stepNumber + ": " + description);
        System.out.println("=".repeat(70) + "\n");
    }
    
    private static void printSummary() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DEMONSTRATION SUMMARY                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println("\n✅ DESIGN PATTERNS DEMONSTRATED:");
        System.out.println("   • Singleton Pattern: Single SmartHomeController instance");
        System.out.println("   • Mediator Pattern: Controller coordinates all device interactions");

    }
    
    private static void pause() {
        try {
            Thread.sleep(800); // Pause for readability
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}