package agh.ics.oop.GUI.controllers;

public class InputElement {
    public final GetAction getAction;
    public final SetAction setAction;

    public InputElement(GetAction getAction, SetAction setAction) {
        this.getAction = getAction;
        this.setAction = setAction;
    }

    public void set(String value) {
        setAction.set(value);
    }

    public String get() {
        return getAction.get();
    }
}
