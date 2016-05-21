package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;
import java.util.LinkedList;

public class LienzoSampleTemplateBuilder {
    
    public static Collection<SamplerFloatPoint> build( final MultiPath multiPath ) {
        
        return buildSamples( multiPath );
        
    }
    
    private static Collection<SamplerFloatPoint> buildSamples( final MultiPath multiPath ) {

        NFastArrayList<PathPartList> paths = multiPath.getPathPartListArray();

        Collection<SamplerFloatPoint> result = new LinkedList<>();
        
        for ( PathPartList path : paths ) {

            Point2DArray points = path.getPoints();

            Collection<Point2D> pointsSet = points.getPoints();

            Collection<SamplerFloatPoint> translated = translate( pointsSet );

            if (null != translated && !translated.isEmpty()) {
                
                result.addAll( translated );
                
            }
            
        }
        
        return result;
        
    }

    private static Collection<SamplerFloatPoint> translate( final Collection<Point2D> points ) {

        double mx = 0;
        double my = 0;

        for ( Point2D point : points ) {

            double x = point.getX();
            double y = point.getY();

            if ( x < 0 && x < mx ) {
                mx = x;
            }

            if ( y < 0 && y < my ) {
                my = y;
            }

        }

        if ( mx < 0 ) {
            mx *= -1;
        }

        if ( my < 0 ) {
            my *= -1;
        }

        Collection<SamplerFloatPoint> result = new LinkedList<>();
        for ( Point2D point : points ) {

            double x = point.getX();
            double y = point.getY();

            result.add( new SamplerFloatPoint( x + mx, y + my ) );

        }

        return result;

    }
    
    
}
