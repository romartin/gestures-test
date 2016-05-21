package org.roger600.gestures.shared;

public interface PointProvider<T> {
    
    double getX( T point );
    
    double getY( T point );
    
}
