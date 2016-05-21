package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerUtils;

import java.util.Collection;
import java.util.LinkedList;

public class SampleTemplateBuilder {
    
    public static Collection<SamplerFloatPoint> build( final MultiPath multiPath ) {
        
        return buildSamples( multiPath );
        
    }
    
    private static Collection<SamplerFloatPoint> buildSamples( final MultiPath multiPath ) {

        NFastArrayList<PathPartList> paths = multiPath.getPathPartListArray();

        Collection<SamplerFloatPoint> result = new LinkedList<>();
        
        for ( PathPartList path : paths ) {

            Point2DArray points = path.getPoints();

            Collection<Point2D> pointsSet = points.getPoints();

            Collection<SamplerFloatPoint> translated = SamplerUtils.translateNegative( pointsSet, Point2DProvider.INSTANCE );

            if (null != translated && !translated.isEmpty()) {
                
                result.addAll( translated );
                
            }
            
        }
        
        return result;
        
    }

}
