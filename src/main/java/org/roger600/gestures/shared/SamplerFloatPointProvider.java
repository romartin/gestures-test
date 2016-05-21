package org.roger600.gestures.shared;

public final class SamplerFloatPointProvider implements PointProvider<SamplerFloatPoint> {

    public static final SamplerFloatPointProvider INSTANCE = new SamplerFloatPointProvider();
    
    @Override
    public double getX(final SamplerFloatPoint point) {
        return point.getX();
    }

    @Override
    public double getY(final SamplerFloatPoint point) {
        return point.getY();
    }
    
}
