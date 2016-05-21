package org.roger600.gestures.shared;

public class SamplerFloatPoint {
    
    private final double x;
    private final double y;

    public SamplerFloatPoint(final double x,
                             final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return " [" + x + ", " + y + "] " ;
    }
    
}
