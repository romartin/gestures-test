package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.LinkedList;
import java.util.List;

public class DrawableAreaSampler extends AreaSampler {

    private SamplerFloatPoint lastPoint = null;
    private final List<Line> linePoints = new LinkedList<>();

    public DrawableAreaSampler( final Layer layer, 
                                final double x, 
                                final double y, 
                                final double width, 
                                final double height, 
                                final SamplerCallback callback) {
        super(layer, x, y, width, height, callback);
    }

    @Override
    protected void doTakeSample(final double x, 
                                final double y) {

        super.doTakeSample(x, y);
        
        drawPoint( x, y );
        
    }

    protected void drawPoint(final double x,
                             final double y) {

        if ( null == lastPoint ) {
            
            lastPoint = new SamplerFloatPoint( x, y );
            
        } else {
            
            final Line line = new Line( lastPoint.getX(), lastPoint.getY(), x, y );
            layer.add( line );
            layer.batch();
            
            lastPoint = null;
        }

    }


    @Override
    protected void onCompleteSample(final double x, 
                                    final double y) {
        super.onCompleteSample(x, y);
        
        removeLinePoints();
    }

    private void removeLinePoints() {

        for ( final Line line : linePoints ) {
            line.removeFromParent();
        }

        linePoints.clear();
        lastPoint = null;

    }
}
