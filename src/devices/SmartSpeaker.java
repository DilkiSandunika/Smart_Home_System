package devices;

/**
 * Smart Speaker device with audio control
 */
public class SmartSpeaker extends SmartDevice {
    private int volume; // 0-100
    private String currentSound;
    
    /**
     * Constructor for SmartSpeaker
     * @param deviceId Unique device identifier
     * @param deviceName Human-readable device name
     */
    public SmartSpeaker(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.volume = 50; // Default medium volume
        this.currentSound = "None";
    }
    
    /**
     * Set the volume level
     * @param volume Volume level (0-100)
     */
    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
            System.out.println("  ✓ " + deviceName + " volume set to " + volume + "%");
        } else {
            System.out.println("  ⚠ Invalid volume. Must be 0-100");
        }
    }
    
    /**
     * Get current volume level
     * @return Current volume (0-100)
     */
    public int getVolume() {
        return volume;
    }
    
    /**
     * Play a sound or message
     * @param sound Sound/message to play
     */
    public void playSound(String sound) {
        if (isOn) {
            this.currentSound = sound;
            System.out.println("  🔊 " + deviceName + " playing: \"" + sound + "\" at volume " + volume + "%");
        } else {
            System.out.println("  ⚠ " + deviceName + " is OFF. Cannot play sound.");
        }
    }
    
    /**
     * Stop playing current sound
     */
    public void stopSound() {
        this.currentSound = "None";
        System.out.println("  ⏹️ " + deviceName + " stopped playing");
    }
    
    /**
     * Play notification sound
     */
    public void playNotification() {
        playSound("🔔 NOTIFICATION ALERT");
    }
    
    /**
     * Play alarm sound
     */
    public void playAlarm() {
        playSound("🚨 ALARM! ALARM! ALARM!");
    }
    
    /**
     * Announce message (Text-to-Speech simulation)
     * @param message Message to announce
     */
    public void announce(String message) {
        System.out.println("  📢 " + deviceName + " announcing: \"" + message + "\"");
    }
    
    @Override
    public String getDeviceInfo() {
        return String.format("SmartSpeaker [ID=%s, Name='%s', Status=%s, Volume=%d%%, Playing='%s', Roles=%d]",
            deviceId, 
            deviceName, 
            isOn ? "ON" : "OFF", 
            volume,
            currentSound,
            roles.size());
    }
}