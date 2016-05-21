package org.roger600.gestures.shared;

public class SamplerPoint {
    
    private final int x;
    private final int y;

    public SamplerPoint( final int x, 
                         final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return " [" + x + ", " + y + "] " ;
    }
    
}
