package org.roger600.gestures.client.sampler.draw;

import com.ait.lienzo.client.core.shape.Arrow;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.ArrowType;

public class DrawableSamplers {
    
    public static final DrawableSampler LINE_SAMPLER = new LineSampler();
    public static final DrawableSampler ARROW_SAMPLER = new ArrowSampler();
    
    private static class LineSampler implements DrawableSampler.DrawableDoubleSampler {

        @Override
        public IPrimitive<?> draw( final double x0, 
                                   final double y0, 
                                   final double x1, 
                                   final double y1 ) {
            
            return new Line( x0, y0, x1, y1 );
            
        }
    }

    private static class ArrowSampler implements DrawableSampler.DrawableDoubleSampler {

        @Override
        public IPrimitive<?> draw( final double x0,
                                   final double y0,
                                   final double x1,
                                   final double y1 ) {
            
            return new Arrow( 
                    new Point2D( x0, y0),
                    new Point2D( x1, y1),
                    3,
                    15,
                    45,
                    45,
                    ArrowType.AT_END
            );
            
        }
        
    }
    
}
