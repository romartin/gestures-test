package org.roger600.gestures.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Text;
import org.roger600.gestures.client.sampler.AbstractMouseSampler;
import org.roger600.gestures.client.sampler.AreaSampler;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

import java.util.Collection;

public class GesturesPresenter {

    private final static String EMPTY = "N/A";
    
    private final Layer layer;
    private final Collection<SamplerTemplate> templates;
    private final Text resultText = new Text( EMPTY )
            .setFontFamily( "verdana" )
            .setFontSize( 12 );
    
    private AreaSampler areaSampler;
    
    public GesturesPresenter(final Layer layer, 
                             final Collection<SamplerTemplate> templates) {
        this.layer = layer;
        this.templates = templates;
    }
    
    public void show( final double x, 
                      final double y,
                      final double width,
                      final double height ) {
        
        this.areaSampler = new AreaSampler( layer, x, y, width, height, new AbstractMouseSampler.SamplerCallback() {
            
            @Override
            public void onComplete(final Collection<SamplerFloatPoint> samples) {
                
                if ( null != samples ) {

                    resultText.setText( samples.toString() );
                    
                } else {
                    
                    resultText.setText( EMPTY );
                    
                }
                
            }
            
        });

        layer.add( resultText
                .setX( x + ( width / 2 ) )
                .setY( y + width + 50 ) );
    }
    
    public void destroy() {
        areaSampler.destroy();
        layer.remove( resultText );
    }
    
}
