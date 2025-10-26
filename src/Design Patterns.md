### Singleton Implementation

Now let's look at the actual Java code that implements the Singleton pattern. This is from the SmartHomeController class.

### private static SmartHomeController instance; 

we have the private static instance variable. Let me break down why each keyword matters.
'private' means it can only be accessed from within this class. External code can't directly access this variable.
'static' means it belongs to the class itself, not to individual objects. There's only ONE instance variable, shared across everything.
And 'SmartHomeController' is the type - it holds a reference to a SmartHomeController object.
This instance starts as null - it doesn't exist yet. We use lazy initialization.
Next, the devices list. This isn't directly part of the Singleton pattern, but it's the data our single controller manages - the list of registered devices.

### private SmartHomeController() {
        System.out.println("✓ SmartHomeController initialized (Singleton)\n");
    }

The constructor is private. This is absolutely crucial for Singleton. A private constructor means you cannot do 'new SmartHomeController()' from outside this class. You can't create instances externally. The only way to get an instance is through the method I'll show you next.

### getInstance()

The getInstance method. This is the heart of the Singleton pattern. Let me explain it carefully.
It's 'public' - anyone can call it. It's 'static' - you call it on the class itself, not on an object. SmartHomeController.getInstance(), not controller.getInstance(). And 'synchronized' - this is for thread safety. It ensures that even if multiple threads call getInstance simultaneously, only one will execute at a time. This prevents the race condition where two threads might both create instances.
Finally, 'return instance' - return the instance. If it was just created, return the new one. If it already existed, return the existing one. Either way, always return the SAME instance.

### mediator Implementation

 let's see the Mediator pattern in action. Same SmartHomeController class, but now we're looking at its coordination methods.

 First, the activateSecurityMode method. This is pure mediation - coordinating a scenario across all devices.

 It loops through ALL devices in the devices list. For each device, it calls device.addRole(new SecurityModeRole()).

 Notice what's important here: The controller is creating the SecurityModeRole instance and giving it to each device. The devices don't create their own roles. The devices don't talk to each other. Everything flows through the controller.

 This is centralized coordination. One place knows about all devices. One place decides what roles to assign. If you want to change security mode behavior, you change this one method, not dozens of device classes.