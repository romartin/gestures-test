package org.roger600.gestures.shared;

import java.util.Collection;
import java.util.LinkedList;

public class SamplerUtils {

    public static <T> Collection<SamplerFloatPoint> translateNegative( final Collection<T> points,
                                                                        final PointProvider<T> provider ) {

        double mx = 0;
        double my = 0;

        for ( T point : points ) {

            double x = provider.getX( point );
            double y = provider.getY( point );

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
        for ( T point : points ) {

            double x = provider.getX( point );
            double y = provider.getY( point );

            result.add( new SamplerFloatPoint( x + mx, y + my ) );

        }

        return result;
    }

    public static <T> Collection<SamplerFloatPoint> translateRelative( final T relative,
                                                                        final Collection<T> points,
                                                                        final PointProvider<T> provider ) {

        double rx = provider.getX( relative );
        double ry = provider.getY( relative );

        Collection<SamplerFloatPoint> result = new LinkedList<>();
        for ( T point : points ) {

            double x = provider.getX( point );
            double y = provider.getY( point );

            result.add( new SamplerFloatPoint( x + rx, y + ry) );

        }

        return result;
    }
    
    
    
}
