package org.roger600.gestures.client.template;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;
import org.roger600.gestures.client.sampler.Point2DProvider;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;
import org.roger600.gestures.shared.SamplerUtils;

import java.util.Collection;
import java.util.LinkedList;

public class MultiPathSampleTemplateBuilder {
    
    private static final double SIZE = 300;
    
    public static final SamplerTemplate RECTANGLE = new MultiPathSampleTemplateBuilder( "Rectangle", "Rectangle" )
            .build( new MultiPath().rect( 0, 0, SIZE, SIZE) );

    public static final SamplerTemplate CIRCLE = new MultiPathSampleTemplateBuilder( "Circle", "Circle" )
            .build( new MultiPath().circle( SIZE / 2 ) );


    public static final SamplerTemplate MINUS = new MultiPathSampleTemplateBuilder( "Minus", "Minus" )
            .build( new MultiPath()
                    .M( 0, ( SIZE / 2) )
                    .L( SIZE, ( SIZE / 2) ) );

    public static final SamplerTemplate PLUS = new MultiPathSampleTemplateBuilder( "Plus", "Plus" )
            .build( new MultiPath()
                    .M( 0, ( SIZE / 2) )
                    .L( SIZE, ( SIZE / 2) )
                    .M( ( SIZE / 2 ), 0 )
                    .L( ( SIZE / 2), SIZE ) );

    public static final SamplerTemplate XOR = new MultiPathSampleTemplateBuilder( "XOR", "XOR" )
            .build( new MultiPath()
                    .M( 0, 0 )
                    .L( SIZE, SIZE )
                    .M( SIZE, 0 )
                    .L( 0, SIZE ) );
    
    private final String id;
    private final String name;

    MultiPathSampleTemplateBuilder(final String id,
                                   final String name) {
        this.id = id;
        this.name = name;
    }

    private SamplerTemplate build( final MultiPath multiPath ) {
        
        final Collection<SamplerFloatPoint> samples = doBuild( multiPath );
        
        return new SamplerTemplate( id, name, multiPath, samples );
        
    }
    
    
    static Collection<SamplerFloatPoint> doBuild(final MultiPath multiPath ) {

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
