package org.roger600.gestures.client.sampler;

import com.google.gwt.core.client.GWT;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;

public class SamplerUtils {

    public static void logSamples( final Collection<SamplerFloatPoint> points ) {
        logSamples( null, points );
    }

    public static void logSamples( final String title, 
                                   final Collection<SamplerFloatPoint> points ) {

        if ( null != title ) {
            GWT.log("*******" + title + "************");
        }

        for ( SamplerFloatPoint point : points ) {
            final double px = point.getX();
            final double py = point.getY();
            GWT.log("[" + px + ", " + py + "]");
        }
        
    }
    
}
