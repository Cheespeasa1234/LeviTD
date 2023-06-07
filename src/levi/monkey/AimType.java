package levi.monkey;

public enum AimType {
    FIRST,
    LAST,
    STRONGEST,
    WEAKEST,
    CLOSE,
    FAR;

    private static final AimType[] vals = values();
    
    public AimType next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
    public AimType prev() {
        return vals[(this.ordinal() - 1 + vals.length) % vals.length];
    }
}
