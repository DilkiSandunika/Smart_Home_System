# Smart Home Control System

A comprehensive smart home management system demonstrating **Gang of Four (GoF) Design Patterns** including Singleton, Mediator, and Role patterns.

## 🎯 Project Overview

This project implements a smart home control system where devices can dynamically gain and lose different "roles" based on situational contexts. The system demonstrates proper object-oriented design principles and design pattern implementation.

## 🏗️ Design Patterns Implemented

### 1. **Singleton Pattern**
- **Class**: `SmartHomeController`
- **Purpose**: Ensures only one controller instance manages the entire smart home system
- **Benefits**: Centralized control, no conflicts, consistent state management

### 2. **Mediator Pattern**
- **Class**: `SmartHomeController`
- **Purpose**: Coordinates interactions between smart devices
- **Benefits**: Loose coupling, easy to add new devices, centralized coordination logic

### 3. **Role Pattern**
- **Classes**: `IDeviceRole`, `SecurityModeRole`, `VacationModeRole`, etc.
- **Purpose**: Allows devices to dynamically acquire context-specific behaviors
- **Benefits**: Flexible behaviors, runtime modification, no class explosion

## 🏠 Smart Home Devices

- **💡 Smart Light**: Brightness control, color changing, status indicators
- **🌡️ Smart Thermostat**: Temperature control, eco mode, temperature monitoring
- **🔊 Smart Speaker**: Volume control, sound playback, announcements

## 🎭 Available Roles

- **🔒 Security Mode Role**: Activates security protocols (lights flash, alarms sound)
- **✈️ Vacation Mode Role**: Simulates presence when residents are away
- **⚡ Energy Management Role**: Optimizes energy consumption
- **📢 Notification Role**: Alerts users to events and messages

## 🚀 Features

✅ Dynamic role assignment and removal at runtime  
✅ Multiple simultaneous roles per device  
✅ Centralized device coordination via Mediator  
✅ Single controller instance (Singleton)  
✅ Easy extensibility (add new devices/roles without modifying existing code)  
✅ Graphical User Interface (GUI) for easy demonstration  
✅ Real-time device status monitoring  
✅ System activity logging  

## 📋 Requirements Met

- ✅ Common interface usage for all devices
- ✅ Role detection capability (hasRole method)
- ✅ Role-specific interface usage (getRole().execute())
- ✅ Dynamic role gain/loss at runtime
- ✅ Multiple simultaneous roles supported
- ✅ Easy role extensibility

## 🛠️ Technologies Used

- **Language**: Java
- **GUI Framework**: Java Swing
- **Design Patterns**: Singleton, Mediator, Role Pattern
- **IDE**: VS Code / IntelliJ IDEA / Eclipse

## 📁 Project Structure
```
SmartHomeSystem/
├── src/
│   ├── devices/           # Device classes (Light, Thermostat, Speaker)
│   ├── roles/             # Role implementations
│   ├── mediator/          # Mediator (Controller)
│   ├── Main.java          # Console demonstration
│   └── SmartHomeUI.java   # GUI application
├── README.md
└── .gitignore
```

## 🏃 How to Run

### Option 1: Console Application
```bash
# Navigate to project directory
cd SmartHomeSystem

# Compile all Java files
javac -d bin src/devices/*.java src/roles/*.java src/mediator/*.java src/Main.java

# Run the console demo
java -cp bin Main
```

### Option 2: GUI Application
```bash
# Compile
javac -d bin src/devices/*.java src/roles/*.java src/mediator/*.java src/SmartHomeUI.java

# Run GUI
java -cp bin SmartHomeUI
```

## 🎮 Using the GUI

1. **Device Control**: Use ON/OFF buttons to control individual devices
2. **Scenario Activation**: Click scenario buttons to activate modes (Security, Vacation, Energy)
3. **Trigger Actions**: Once a mode is active, trigger specific actions (e.g., Security Alert)
4. **View Roles**: Click "Roles" button on any device to see its active roles
5. **Monitor Log**: Watch the system log at the bottom for all activities

## 📊 UML Diagrams

### Class Diagram
```
[See documentation for detailed UML diagrams]
```

### Sequence Diagrams
```
[See documentation for interaction flows]
```

## 📝 Key Learning Outcomes

- ✅ Understanding when and how to apply design patterns
- ✅ Implementing Singleton for resource management
- ✅ Using Mediator for loose coupling
- ✅ Applying Role pattern for flexible behaviors
- ✅ Following SOLID principles
- ✅ Creating maintainable, extensible code

## 👨‍💻 Author

**Dilki Sandunika Rathnayake**  
Vilnius Tech University  

## 📄 License

This project is created for educational purposes.

## 🙏 Acknowledgments

- Gang of Four Design Patterns book
- Vilnius Tech University faculty
- Course instructor: Saulius Valentinavičius

---

**Note**: This is an academic project demonstrating design pattern implementation. It simulates smart home functionality for educational purposes.