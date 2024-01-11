package agh.ics.oop.model.utils;

public class KeyValue<KeyType, ValueType> {
    private KeyType key;
    private ValueType value;

    public KeyValue(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    public KeyType getKey() {
        return key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }

    public ValueType getValue() {
        return value;
    }

    public void setValue(ValueType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\n"+"Amount: " + value + " " + key;
    }
}
