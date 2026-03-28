package com.lexis.settings;

public class Setting {
    public String name;
    public boolean focused;
    public double doubleValue, min, max;
    public boolean booleanValue;
    public String modeValue;
    public String[] modes;
    public boolean isTyping = false;

    public boolean isSlider() { return modes == null && (min != 0 || max != 0); }

    public boolean isBoolean() {
        return name.toLowerCase().contains("enabled") ||
                name.toLowerCase().contains("edit position") ||
                (modes == null && min == 0 && max == 0);
    }

    public boolean isMode() { return modes != null; }

    public Setting(String name, double defaultValue, double min, double max) {
        this.name = name;
        this.doubleValue = defaultValue;
        this.min = min;
        this.max = max;
    }

    public Setting(String name, boolean defaultValue) {
        this.name = name;
        this.booleanValue = defaultValue;
    }

    public Setting(String name, String defaultValue, String... modes) {
        this.name = name;
        this.modeValue = defaultValue;
        this.modes = modes;
    }

    // CLICKGUI HATASINI ÇÖZEN METOD
    public void cycle() {
        if (modes == null || modes.length == 0) return;
        int index = 0;
        for (int i = 0; i < modes.length; i++) {
            if (modes[i].equalsIgnoreCase(modeValue)) {
                index = i;
                break;
            }
        }
        index++;
        if (index >= modes.length) index = 0;
        modeValue = modes[index];
    }
}