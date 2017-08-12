package org.roger600.gestures.client.sampler.draw;

import java.util.LinkedList;
import java.util.List;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import org.roger600.gestures.client.sampler.AreaSampler;
import org.roger600.gestures.shared.SamplerFloatPoint;

public class DrawableAreaSampler extends AreaSampler {

    private final List<IPrimitive<?>> samplePoints = new LinkedList<>();
    private final DrawableSampler drawableSampler;
    private final boolean isSingleSampleDrawable;
    private SamplerFloatPoint lastPoint = null;

    public DrawableAreaSampler( final Layer layer, 
                                final double x, 
                                final double y, 
                                final double width, 
                                final double height, 
                                final SamplerCallback callback,
                                final DrawableSampler drawableSampler) {
        super(layer, x, y, width, height, callback);
        this.drawableSampler = drawableSampler;
        this.isSingleSampleDrawable = drawableSampler instanceof DrawableSampler.DrawableSingleSampler;
    }

    @Override
    protected void doStartSampling() {

        super.doStartSampling();

        removeLinePoints();

    }

    @Override
    protected void doTakeSample(final double x, 
                                final double y) {

        super.doTakeSample(x, y);
        
        drawPoint( x, y );
        
    }

    protected void drawPoint(final double x,
                             final double y) {

        if ( isSingleSampleDrawable ) {

            final IPrimitive<?> p = ( (DrawableSampler.DrawableSingleSampler) drawableSampler).draw( x, y );
            addSampleDrawable( p );
            
        } else {

            if ( null == lastPoint ) {

                lastPoint = new SamplerFloatPoint( x, y );

            } else {

                final IPrimitive<?> p = ( (DrawableSampler.DrawableDoubleSampler) drawableSampler).draw( lastPoint.getX(), lastPoint.getY(), x, y );
                addSampleDrawable( p );

                lastPoint = null;
            }
            
        }

    }
    
    protected void addSampleDrawable( final IPrimitive<?> sd ) {
        sd.setListening(false);
        samplePoints.add( sd );
        // TODO: Add into top/background layer?
        layer.add( sd );
        layer.batch();
    }
    

    private void removeLinePoints() {

        if ( null != samplePoints && !samplePoints.isEmpty() ) {
            
            for ( final IPrimitive<?> samplePoint : samplePoints) {
                samplePoint.removeFromParent();
            }

            samplePoints.clear();
        }

        
        lastPoint = null;

    }
}
