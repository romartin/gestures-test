package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.types.Point2D;
import org.roger600.gestures.shared.PointProvider;

public final class Point2DProvider implements PointProvider<Point2D> {
    
    public static final Point2DProvider INSTANCE = new Point2DProvider();
    
    @Override
    public double getX(final Point2D point) {
        return point.getX();
    }

    @Override
    public double getY(final Point2D point) {
        return point.getY();
    }
}
