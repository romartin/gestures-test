package org.roger600.gestures.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.google.gwt.user.client.ui.FlowPanel;

public class GesturesTests extends FlowPanel {

    private Layer layer;
    
    public GesturesTests(Layer layer) {
        this.layer = layer;
    }

    public void test() {

        final double x = 50;
        final double y = 50;
        final double w = 100;
        final double h = 100;

        MultiPath parent  = new MultiPath()
                .rect( 0, 0, w, h )
                .setStrokeColor("#000000")
                .setStrokeWidth( 5 )
                .setX( x )
                .setY( y );

        MultiPath xor  = new MultiPath()
                .M( x, y )
                .L( x + w, y + h )
                .M( x + w, y )
                .L( x, y + h )
                .setStrokeColor("#0000FF")
                .setStrokeWidth( 5 );
        
        layer.add( parent );
        layer.add( xor );
        
    }
   
}
