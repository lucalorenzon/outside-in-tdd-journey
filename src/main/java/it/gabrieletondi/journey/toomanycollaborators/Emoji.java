package it.gabrieletondi.journey.toomanycollaborators;

public class Emoji {
    private final String shortcut;
    private final String value;

    public Emoji(String shortcut, String value) {
        this.shortcut = shortcut;
        this.value = value;
    }

    public String getShortcut() {
        return shortcut;
    }

    public String getValue() {
        return value;
    }
}
