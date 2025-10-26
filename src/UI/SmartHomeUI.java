package UI;
import devices.*;
import roles.*;
import mediator.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SmartHomeUI extends JFrame {
    private SmartHomeController controller;
    private SmartLight livingRoomLight;
    private SmartThermostat hallThermostat;
    private SmartSpeaker kitchenSpeaker;
    
    private JTextArea logArea;
    private JPanel devicesPanel;
    private JLabel statusLabel;
    
    // Toggle state tracking
    private boolean securityModeActive = false;
    private boolean vacationModeActive = false;
    private boolean energyModeActive = false;
    
    // Toggle buttons
    private JButton securityToggleBtn;
    private JButton vacationToggleBtn;
    private JButton energyToggleBtn;
    
    // Color schemes
    private static final Color ACTIVE_GREEN = new Color(46, 204, 113);
    private static final Color INACTIVE_GRAY = new Color(189, 195, 199);
    private static final Color SECURITY_BLUE = new Color(52, 152, 219);
    private static final Color ALERT_RED = new Color(231, 76, 60);
    private static final Color VACATION_PURPLE = new Color(155, 89, 182);
    private static final Color ENERGY_GREEN = new Color(39, 174, 96);
    private static final Color BUTTON_HOVER = new Color(127, 140, 141);
    
    public SmartHomeUI() {
        setTitle("Smart Home Control System - Design Patterns Demo");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Initialize controller and devices
        initializeSystem();
        
        // Create UI components
        createMenuBar();
        createTopPanel();
        createDevicesPanel();
        createControlPanel();
        createLogPanel();
        createBottomPanel();
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        log("✓ Smart Home System initialized with Singleton Controller");
        log("✓ All devices registered with Mediator");
    }
    
    private void initializeSystem() {
        controller = SmartHomeController.getInstance();
        
        livingRoomLight = new SmartLight("DEV-001", "Living Room Light");
        hallThermostat = new SmartThermostat("DEV-002", "Hall Thermostat");
        kitchenSpeaker = new SmartSpeaker("DEV-003", "Kitchen Speaker");
        
        controller.registerDevice(livingRoomLight);
        controller.registerDevice(hallThermostat);
        controller.registerDevice(kitchenSpeaker);
        
        livingRoomLight.turnOn();
        hallThermostat.turnOn();
        kitchenSpeaker.turnOn();
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
    
    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        topPanel.setBackground(new Color(44, 62, 80));
        
        JLabel titleLabel = new JLabel("🏠 SMART HOME CONTROL SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Design Patterns: Singleton + Mediator + Roles", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
    }
    
    private void createDevicesPanel() {
        devicesPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        devicesPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        devicesPanel.setBackground(new Color(236, 240, 241));
        
        devicesPanel.add(createDevicePanel(livingRoomLight, "💡", new Color(241, 196, 15)));
        devicesPanel.add(createDevicePanel(hallThermostat, "🌡️", new Color(230, 126, 34)));
        devicesPanel.add(createDevicePanel(kitchenSpeaker, "🔊", new Color(52, 152, 219)));
        
        add(devicesPanel, BorderLayout.CENTER);
    }
    
    private JPanel createDevicePanel(ISmartDevice device, String icon, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(accentColor, 3, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        
        // Header with icon
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel iconLabel = new JLabel(icon, JLabel.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        
        JLabel nameLabel = new JLabel(device.getDeviceName(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        headerPanel.add(iconLabel, BorderLayout.NORTH);
        headerPanel.add(nameLabel, BorderLayout.CENTER);
        
        // Status area
        JTextArea statusArea = new JTextArea(9, 25);
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        statusArea.setBackground(new Color(248, 249, 250));
        statusArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        updateDeviceStatus(statusArea, device);
        
        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218)));
        
        // Control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton onBtn = createDeviceButton("TURN ON", ACTIVE_GREEN);
        onBtn.addActionListener(e -> {
            device.turnOn();
            log("✓ " + device.getDeviceName() + " turned ON");
            refreshDeviceDisplay();
        });
        
        JButton offBtn = createDeviceButton("TURN OFF", ALERT_RED);
        offBtn.addActionListener(e -> {
            device.turnOff();
            log("✓ " + device.getDeviceName() + " turned OFF");
            refreshDeviceDisplay();
        });
        
        JButton infoBtn = createDeviceButton("INFO", new Color(52, 152, 219));
        infoBtn.addActionListener(e -> showDeviceInfo(device));
        
        JButton rolesBtn = createDeviceButton("ROLES", new Color(142, 68, 173));
        rolesBtn.addActionListener(e -> showDeviceRoles(device));
        
        buttonPanel.add(onBtn);
        buttonPanel.add(offBtn);
        buttonPanel.add(infoBtn);
        buttonPanel.add(rolesBtn);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createDeviceButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.darker());
                }
            }
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor);
                }
            }
        });
        
        return button;
    }
    
    private void createControlPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setBorder(new EmptyBorder(15, 10, 15, 15));
        rightPanel.setBackground(new Color(236, 240, 241));
        
        JLabel titleLabel = new JLabel("🎛️ Scenario Control", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JLabel subtitleLabel = new JLabel("(Mediator Actions)", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(236, 240, 241));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(236, 240, 241));
        
        // Security Mode Section
        controlPanel.add(createSectionHeader("🔒 Security Mode", SECURITY_BLUE));
        
        securityToggleBtn = createToggleButton("Activate Security Mode", INACTIVE_GRAY, SECURITY_BLUE);
        securityToggleBtn.addActionListener(e -> toggleSecurityMode());
        controlPanel.add(securityToggleBtn);
        controlPanel.add(Box.createVerticalStrut(8));
        
        JButton securityAlertBtn = createActionButton("Trigger Security Alert", ALERT_RED);
        securityAlertBtn.addActionListener(e -> {
            if (!securityModeActive) {
                showWarning("Security mode must be active first!");
                return;
            }
            controller.triggerSecurityAlert();
            log("🚨 SECURITY ALERT TRIGGERED!");
            flashButton(securityAlertBtn);
        });
        controlPanel.add(securityAlertBtn);
        
        controlPanel.add(Box.createVerticalStrut(25));
        
        // Vacation Mode Section
        controlPanel.add(createSectionHeader("✈️ Vacation Mode", VACATION_PURPLE));
        
        vacationToggleBtn = createToggleButton("Activate Vacation Mode", INACTIVE_GRAY, VACATION_PURPLE);
        vacationToggleBtn.addActionListener(e -> toggleVacationMode());
        controlPanel.add(vacationToggleBtn);
        controlPanel.add(Box.createVerticalStrut(8));
        
        JButton simulateBtn = createActionButton("Simulate Presence", VACATION_PURPLE.darker());
        simulateBtn.addActionListener(e -> {
            if (!vacationModeActive) {
                showWarning("Vacation mode must be active first!");
                return;
            }
            controller.simulatePresence();
            log("🏡 Simulating presence...");
        });
        controlPanel.add(simulateBtn);
        
        controlPanel.add(Box.createVerticalStrut(25));
        
        // Energy Management Section
        controlPanel.add(createSectionHeader("⚡ Energy Management", ENERGY_GREEN));
        
        energyToggleBtn = createToggleButton("Activate Energy Mode", INACTIVE_GRAY, ENERGY_GREEN);
        energyToggleBtn.addActionListener(e -> toggleEnergyMode());
        controlPanel.add(energyToggleBtn);
        controlPanel.add(Box.createVerticalStrut(8));
        
        JButton applySavingBtn = createActionButton("Apply Energy Saving", ENERGY_GREEN.darker());
        applySavingBtn.addActionListener(e -> {
            if (!energyModeActive) {
                showWarning("Energy mode must be active first!");
                return;
            }
            controller.applyEnergySaving();
            log("🌿 Energy-saving measures applied");
        });
        controlPanel.add(applySavingBtn);
        
        controlPanel.add(Box.createVerticalStrut(25));
        
        // Notification Section
        controlPanel.add(createSectionHeader("📢 Notifications", new Color(52, 152, 219)));
        
        JButton notifyBtn = createActionButton("Send Notification", new Color(52, 152, 219));
        notifyBtn.addActionListener(e -> {
            String message = JOptionPane.showInputDialog(this, 
                "Enter notification message:", 
                "Send Notification", 
                JOptionPane.PLAIN_MESSAGE);
            if (message != null && !message.trim().isEmpty()) {
                controller.sendNotification(message);
                log("📢 Notification: " + message);
            }
        });
        controlPanel.add(notifyBtn);
        
        controlPanel.add(Box.createVerticalStrut(25));
        
        // Status Indicator
        JPanel statusPanel = createStatusPanel();
        controlPanel.add(statusPanel);
        
        controlPanel.add(Box.createVerticalGlue());
        
        JScrollPane scrollPane = new JScrollPane(controlPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(236, 240, 241));
        
        rightPanel.add(headerPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(rightPanel, BorderLayout.EAST);
    }
    
    private JPanel createSectionHeader(String text, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(new EmptyBorder(8, 12, 8, 12));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        
        panel.add(label, BorderLayout.WEST);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        return panel;
    }
    
    private JButton createToggleButton(String text, Color inactiveColor, Color activeColor) {
        JButton button = new JButton(text);
        button.setBackground(inactiveColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Store colors as client properties
        button.putClientProperty("inactiveColor", inactiveColor);
        button.putClientProperty("activeColor", activeColor);
        button.putClientProperty("isActive", false);
        
        return button;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        Color originalColor = color;
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(127, 140, 141), 2, true),
            new EmptyBorder(12, 12, 12, 12)
        ));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        JLabel titleLabel = new JLabel("📊 Active Modes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        
        JLabel secLabel = new JLabel("🔒 Security: Inactive");
        secLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        secLabel.setForeground(Color.GRAY);
        secLabel.setName("securityLabel");
        
        JLabel vacLabel = new JLabel("✈️ Vacation: Inactive");
        vacLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        vacLabel.setForeground(Color.GRAY);
        vacLabel.setName("vacationLabel");
        
        JLabel engLabel = new JLabel("⚡ Energy: Inactive");
        engLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        engLabel.setForeground(Color.GRAY);
        engLabel.setName("energyLabel");
        
        panel.add(secLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(vacLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(engLabel);
        
        return panel;
    }
    
    private void toggleSecurityMode() {
        securityModeActive = !securityModeActive;
        
        if (securityModeActive) {
            controller.activateSecurityMode();
            updateToggleButton(securityToggleBtn, true, "Deactivate Security Mode");
            log("━━━ 🔒 SECURITY MODE ACTIVATED ━━━");
        } else {
            controller.deactivateSecurityMode();
            updateToggleButton(securityToggleBtn, false, "Activate Security Mode");
            log("━━━ 🔓 SECURITY MODE DEACTIVATED ━━━");
        }
        
        updateStatusPanel();
        refreshDeviceDisplay();
    }
    
    private void toggleVacationMode() {
        vacationModeActive = !vacationModeActive;
        
        if (vacationModeActive) {
            controller.activateVacationMode();
            updateToggleButton(vacationToggleBtn, true, "Deactivate Vacation Mode");
            log("━━━ ✈️ VACATION MODE ACTIVATED ━━━");
        } else {
            controller.deactivateVacationMode();
            updateToggleButton(vacationToggleBtn, false, "Activate Vacation Mode");
            log("━━━ 🏠 VACATION MODE DEACTIVATED ━━━");
        }
        
        updateStatusPanel();
        refreshDeviceDisplay();
    }
    
    private void toggleEnergyMode() {
        energyModeActive = !energyModeActive;
        
        if (energyModeActive) {
            controller.activateEnergyManagementMode();
            updateToggleButton(energyToggleBtn, true, "Deactivate Energy Mode");
            log("━━━ ⚡ ENERGY MODE ACTIVATED ━━━");
        } else {
            controller.deactivateEnergyManagementMode();
            updateToggleButton(energyToggleBtn, false, "Activate Energy Mode");
            log("━━━ 💡 ENERGY MODE DEACTIVATED ━━━");
        }
        
        updateStatusPanel();
        refreshDeviceDisplay();
    }
    
    private void updateToggleButton(JButton button, boolean active, String newText) {
        Color inactiveColor = (Color) button.getClientProperty("inactiveColor");
        Color activeColor = (Color) button.getClientProperty("activeColor");
        
        if (active) {
            button.setBackground(activeColor);
            button.setText(newText);
        } else {
            button.setBackground(inactiveColor);
            button.setText(newText);
        }
        
        button.putClientProperty("isActive", active);
    }
    
    private void updateStatusPanel() {
        Component[] components = ((JPanel)getContentPane().getComponent(3)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) comp;
                JPanel panel = (JPanel) scroll.getViewport().getView();
                updateStatusLabels(panel);
            }
        }
    }
    
    private void updateStatusLabels(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel subPanel = (JPanel) comp;
                for (Component subComp : subPanel.getComponents()) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        String name = label.getName();
                        
                        if ("securityLabel".equals(name)) {
                            if (securityModeActive) {
                                label.setText("🔒 Security: ACTIVE");
                                label.setForeground(SECURITY_BLUE);
                            } else {
                                label.setText("🔒 Security: Inactive");
                                label.setForeground(Color.GRAY);
                            }
                        } else if ("vacationLabel".equals(name)) {
                            if (vacationModeActive) {
                                label.setText("✈️ Vacation: ACTIVE");
                                label.setForeground(VACATION_PURPLE);
                            } else {
                                label.setText("✈️ Vacation: Inactive");
                                label.setForeground(Color.GRAY);
                            }
                        } else if ("energyLabel".equals(name)) {
                            if (energyModeActive) {
                                label.setText("⚡ Energy: ACTIVE");
                                label.setForeground(ENERGY_GREEN);
                            } else {
                                label.setText("⚡ Energy: Inactive");
                                label.setForeground(Color.GRAY);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void flashButton(JButton button) {
        Color originalColor = button.getBackground();
        Timer timer = new Timer(100, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                button.setBackground(Color.WHITE);
                button.setForeground(originalColor);
            } else {
                button.setBackground(originalColor);
                button.setForeground(Color.WHITE);
            }
            count[0]++;
            if (count[0] >= 6) {
                timer.stop();
                button.setBackground(originalColor);
                button.setForeground(Color.WHITE);
            }
        });
        timer.start();
    }
    
    private void createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(127, 140, 141), 2),
            "📜 System Log",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));
        logPanel.setBackground(new Color(236, 240, 241));
        
        logArea = new JTextArea(10, 80);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        logArea.setBackground(new Color(40, 44, 52));
        logArea.setForeground(new Color(171, 178, 191));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JButton clearBtn = new JButton("🗑️ Clear Log");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 12));
        clearBtn.setBackground(new Color(127, 140, 141));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.addActionListener(e -> {
            logArea.setText("");
            log("Log cleared");
        });
        
        logPanel.add(scrollPane, BorderLayout.CENTER);
        logPanel.add(clearBtn, BorderLayout.SOUTH);
        
        add(logPanel, BorderLayout.SOUTH);
    }
    
    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(44, 62, 80));
        bottomPanel.setBorder(new EmptyBorder(8, 10, 8, 10));
        
        statusLabel = new JLabel("✓ System Ready | Devices: 3 | Controller: Singleton Instance");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        bottomPanel.add(statusLabel);
    }
    
    private void updateDeviceStatus(JTextArea area, ISmartDevice device) {
        StringBuilder sb = new StringBuilder();
        sb.append("━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("STATUS\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━\n");
        sb.append(device.isOn() ? "● ONLINE\n" : "○ OFFLINE\n");
        sb.append("ID: ").append(device.getDeviceId()).append("\n\n");
        
        if (device instanceof SmartLight) {
            SmartLight light = (SmartLight) device;
            sb.append("💡 Brightness: ").append(light.getBrightness()).append("%\n");
        } else if (device instanceof SmartThermostat) {
            SmartThermostat thermo = (SmartThermostat) device;
            sb.append("🌡️ Temperature: ").append(String.format("%.1f", thermo.getTargetTemperature())).append("°C\n");
        } else if (device instanceof SmartSpeaker) {
            SmartSpeaker speaker = (SmartSpeaker) device;
            sb.append("🔊 Volume: ").append(speaker.getVolume()).append("%\n");
        }
        
        sb.append("\n━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("ACTIVE ROLES (").append(device.getAllRoles().size()).append(")\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━\n");
        
        if (device.getAllRoles().isEmpty()) {
            sb.append("No roles assigned\n");
        } else {
            for (IDeviceRole role : device.getAllRoles()) {
                sb.append("✓ ").append(role.getRoleName()).append("\n");
            }
        }
        
        area.setText(sb.toString());
    }
    
    private void refreshDeviceDisplay() {
        Component[] components = devicesPanel.getComponents();
        int index = 0;
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel devicePanel = (JPanel) comp;
                Component[] innerComps = devicePanel.getComponents();
                for (Component inner : innerComps) {
                    if (inner instanceof JScrollPane) {
                        JScrollPane scroll = (JScrollPane) inner;
                        JTextArea area = (JTextArea) scroll.getViewport().getView();
                        ISmartDevice device = null;
                        if (index == 0) device = livingRoomLight;
                        else if (index == 1) device = hallThermostat;
                        else if (index == 2) device = kitchenSpeaker;
                        if (device != null) {
                            updateDeviceStatus(area, device);
                        }
                    }
                }
                index++;
            }
        }
        devicesPanel.revalidate();
        devicesPanel.repaint();
    }
    
    private void showDeviceInfo(ISmartDevice device) {
        JOptionPane.showMessageDialog(this,
            device.getDeviceInfo(),
            "Device Information",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showDeviceRoles(ISmartDevice device) {
        StringBuilder msg = new StringBuilder();
        msg.append("Device: ").append(device.getDeviceName()).append("\n\n");
        msg.append("Active Roles (").append(device.getAllRoles().size()).append("):\n\n");
        
        List<IDeviceRole> roles = device.getAllRoles();
        if (roles.isEmpty()) {
            msg.append("No roles currently assigned.");
        } else {
            for (IDeviceRole role : roles) {
                msg.append("✓ ").append(role.getRoleName()).append("\n");
                msg.append("   ").append(role.getDescription()).append("\n\n");
            }
        }
        
        JTextArea textArea = new JTextArea(msg.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        
        JOptionPane.showMessageDialog(this, scrollPane, 
            "Device Roles - " + device.getDeviceName(), 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAboutDialog() {
        String about = "🏠 Smart Home Control System\n\n" +
                      "Design Patterns Demonstrated:\n" +
                      "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                      "• SINGLETON PATTERN\n" +
                      "  SmartHomeController (single instance)\n\n" +
                      "• MEDIATOR PATTERN\n" +
                      "  Centralized device coordination\n\n" +
                      "• ROLE PATTERN\n" +
                      "  Dynamic behavior assignment\n\n" +
                      "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                      "Object-Oriented Design Assignment\n" +
                      "GoF Design Patterns Implementation\n" +
                      "Vilnius Tech University";
        
        JOptionPane.showMessageDialog(this, about, 
            "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, 
            "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void log(String message) {
        String timestamp = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
        logArea.append("[" + timestamp + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new SmartHomeUI());
    }
}