# Smart Home System - Code Description

## Project Structure

```
SmartHomeSystem/
│
├── src/
│   │
│   ├── devices/                          # Device Package
│   │   ├── ISmartDevice.java            # Common interface for all devices
│   │   ├── SmartDevice.java             # Abstract base class with role management
│   │   ├── SmartLight.java              # Concrete device: Smart Light
│   │   ├── SmartThermostat.java         # Concrete device: Smart Thermostat
│   │   └── SmartSpeaker.java            # Concrete device: Smart Speaker
│   │
│   ├── roles/                            # Roles Package
│   │   ├── IDeviceRole.java             # Common interface for all roles
│   │   ├── SecurityModeRole.java        # Security behavior implementation
│   │   ├── VacationModeRole.java        # Vacation behavior implementation
│   │   ├── NotificationRole.java        # Notification behavior implementation
│   │   └── EnergyManagementRole.java    # Energy-saving behavior implementation
│   │
│   ├── mediator/                         # Mediator Package
│   │   └── SmartHomeController.java     # SINGLETON + MEDIATOR implementation
│   │
│   ├── Main.java                         # Console-based demonstration
│   └── SmartHomeUI.java                 # GUI-based demonstration
│
├── bin/                                  # Compiled class files (auto-generated)
├── README.md                             # Project documentation
└── .gitignore                            # Git ignore file
```

---

## Design Pattern Implementations

### 1. SINGLETON PATTERN

**Location:** `src/mediator/SmartHomeController.java`

**Implementation:**

```java
public class SmartHomeController {
    
    // ===== SINGLETON PATTERN =====
    
    // 1. Private static instance variable
    private static SmartHomeController instance;
    
    // 2. Private constructor (prevents external instantiation)
    private SmartHomeController() {
        System.out.println("✓ SmartHomeController initialized (Singleton)");
    }
    
    // 3. Public static method to get the single instance
    public static synchronized SmartHomeController getInstance() {
        if (instance == null) {
            instance = new SmartHomeController();
        }
        return instance;
    }
    
    // ... other methods ...
}
```

**Key Components:**

1. **Private Static Instance** 
   - Holds the single instance of the controller
   - `static` ensures it belongs to the class, not objects

2. **Private Constructor** 
   - Makes it impossible to create instances using `new SmartHomeController()`
   - Only the class itself can call this constructor

3. **Public getInstance() Method** 
   - Only way to get the controller instance
   - `synchronized` ensures thread-safety
   - Creates instance on first call (lazy initialization)
   - Returns the same instance on subsequent calls

**Why Singleton:**
- Ensures only ONE controller manages the entire smart home
- Prevents conflicts from multiple controllers
- Provides global access point to the controller
- Single source of truth for system state

**Usage Example:**
```java
// In Main.java or SmartHomeUI.java
SmartHomeController controller = SmartHomeController.getInstance();
// Always returns the same instance
```

---

### 2. MEDIATOR PATTERN

**Location:** `src/mediator/SmartHomeController.java`

**Implementation:**

```java
public class SmartHomeController {
    
    // ===== MEDIATOR PATTERN =====
    
    // Device registry - Mediator knows about all devices
    private final List<ISmartDevice> devices = new ArrayList<>();
    
    // Device registration
    public void registerDevice(ISmartDevice device) {
        devices.add(device);
        System.out.println("✓ Registered: " + device.getDeviceName());
    }
    
    // ===== SCENARIO COORDINATION (Mediator coordinates all devices) =====
    
    // Security Mode Coordination
    public void activateSecurityMode() {
        // Mediator loops through ALL devices
        for (ISmartDevice device : devices) {
            device.addRole(new SecurityModeRole());
        }
    }
    
    public void triggerSecurityAlert() {
        // Mediator coordinates execution
        for (ISmartDevice device : devices) {
            if (device.hasRole(SecurityModeRole.class)) {
                IDeviceRole role = device.getRole(SecurityModeRole.class);
                role.execute(device);  // Execute role behavior
            }
        }
    }
    
    // Vacation Mode Coordination
    public void activateVacationMode() {
        for (ISmartDevice device : devices) {
            device.addRole(new VacationModeRole());
        }
    }
    
    public void simulatePresence() {
        for (ISmartDevice device : devices) {
            if (device.hasRole(VacationModeRole.class)) {
                device.getRole(VacationModeRole.class).execute(device);
            }
        }
    }
    
    // Energy Management Coordination
    public void activateEnergyManagementMode() {
        for (ISmartDevice device : devices) {
            device.addRole(new EnergyManagementRole());
        }
    }
    
    public void applyEnergySaving() {
        for (ISmartDevice device : devices) {
            if (device.hasRole(EnergyManagementRole.class)) {
                device.getRole(EnergyManagementRole.class).execute(device);
            }
        }
    }
    
    // Notification Coordination
    public void sendNotification(String message) {
        for (ISmartDevice device : devices) {
            if (device.hasRole(NotificationRole.class)) {
                device.getRole(NotificationRole.class).execute(device);
            }
        }
    }
}
```

**Key Components:**

1. **Device Registry**
   - `List<ISmartDevice> devices` maintains all registered devices
   - Mediator knows about all devices, but devices don't know about each other

2. **Registration Method** 
   - `registerDevice()` adds devices to the registry
   - Devices register with mediator, not with each other

3. **Coordination Methods** 
   - `activateSecurityMode()` - assigns SecurityModeRole to all devices
   - `triggerSecurityAlert()` - coordinates security response across devices
   - Similar methods for vacation, energy, notification scenarios
   - All coordination logic centralized in the mediator

**How Mediator Works:**

```
WITHOUT MEDIATOR (❌ Bad):
SmartLight ←→ SmartThermostat ←→ SmartSpeaker
(Devices talk to each other directly - complex web of dependencies)

WITH MEDIATOR (✅ Good):
            SmartHomeController (Mediator)
                    ↓
        ┌───────────┼───────────┐
        ↓           ↓           ↓
   SmartLight  SmartThermostat  SmartSpeaker
(Devices only know about mediator - loose coupling)
```

**Why Mediator:**
- **Loose Coupling**: Devices don't reference each other
- **Centralized Control**: All coordination logic in one place
- **Easy Extension**: Add new devices without modifying existing ones
- **Simplified Devices**: Devices focus on their own functionality

**Usage Example:**
```java
// Create devices
SmartLight light = new SmartLight("DEV-001", "Living Room Light");
SmartSpeaker speaker = new SmartSpeaker("DEV-003", "Kitchen Speaker");

// Register with mediator
controller.registerDevice(light);
controller.registerDevice(speaker);

// Mediator coordinates scenario
controller.activateSecurityMode();  // Adds SecurityModeRole to BOTH devices
controller.triggerSecurityAlert();   // Executes SecurityModeRole on BOTH devices
```

---

## Pattern Collaboration

**Both patterns work together in `SmartHomeController.java`:**

- **Singleton Pattern** ensures there's only ONE controller instance
- **Mediator Pattern** defines HOW that controller coordinates devices

```java
// Get the single controller instance (Singleton)
SmartHomeController controller = SmartHomeController.getInstance();

// Use the mediator to coordinate devices (Mediator)
controller.registerDevice(light);
controller.activateSecurityMode();
```

This combination provides:
- ✅ Single point of control (Singleton)
- ✅ No conflicts (Singleton)
- ✅ Centralized coordination (Mediator)
- ✅ Loose coupling between devices (Mediator)


---

## Compilation and Execution

**Compile:**
```bash
javac -d bin src/devices/*.java src/roles/*.java src/mediator/*.java src/Main.java src/SmartHomeUI.java
```

**Run Console Demo:**
```bash
java -cp bin Main
```

**Run GUI Demo:**
```bash
java -cp bin SmartHomeUI
```

---

## Summary

| Pattern | File | Purpose | Key Benefit |
|---------|------|---------|-------------|
| **Singleton** | SmartHomeController.java | Single controller instance | Prevents conflicts, centralized control |
| **Mediator** | SmartHomeController.java | Device coordination | Loose coupling, easy extensibility |
