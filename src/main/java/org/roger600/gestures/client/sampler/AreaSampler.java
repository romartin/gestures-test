package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.event.NodeMouseDownEvent;
import com.ait.lienzo.client.core.event.NodeMouseDownHandler;
import com.ait.lienzo.client.core.event.NodeMouseMoveEvent;
import com.ait.lienzo.client.core.event.NodeMouseMoveHandler;
import com.ait.lienzo.client.core.event.NodeMouseUpEvent;
import com.ait.lienzo.client.core.event.NodeMouseUpHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;

public class AreaSampler extends AbstractMouseSampler {

    private static final int PERIOD = 10;
    
    protected final Layer layer;
    protected final Rectangle area;
    private final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    private SamplerCallback callback;
    private boolean listening;

    public AreaSampler(final Layer layer, 
                       final double x,
                       final double y,
                       final double width, 
                       final double height,
                       final SamplerCallback callback ) {
        
        this.layer = layer;
        this.callback = callback;
        this.listening = false;

        this.area = new Rectangle( width, height )
                        .setX( x )
                        .setY( y )
                        .setStrokeWidth(3)
                        .setStrokeColor(ColorName.RED);

        layer.add( area );

        setSamplerPeriod( PERIOD );
        
        startListening();
    }
    
    public void destroy() {
        
        removeHandlers();
        
        layer.remove( area );
        
    }
    
    private void startListening() {
        
        log( "AreaSampler#startListening" );

        registrationManager.register(
                area.addNodeMouseDownHandler(new NodeMouseDownHandler() {
                    @Override
                    public void onNodeMouseDown(final NodeMouseDownEvent event) {

                        log( "AreaSampler#onNodeMouseDown" );
                        
                        doStartSampling();
                        
                        start( callback );

                    }

                }));

        registrationManager.register(

                area.addNodeMouseMoveHandler( new NodeMouseMoveHandler() {

                    @Override
                    public void onNodeMouseMove( final NodeMouseMoveEvent event ) {

                        if (listening) {
                            final double x = event.getX();
                            final double y = event.getY();

                            log( "AreaSampler#onNodeMouseMove" );

                            takeSample( x, y );

                        }

                    }

                }));

        registrationManager.register(

                area.addNodeMouseUpHandler(new NodeMouseUpHandler() {

                    @Override
                    public void onNodeMouseUp( final NodeMouseUpEvent event ) {

                        if (listening) {

                            final double x = event.getX();
                            final double y = event.getY();

                            log( "AreaSampler#onNodeMouseUp" );

                            onCompleteSample( x ,y );

                        }

                    }

                }));

    }
    
    protected void doStartSampling() {
        listening = true;
    }


    private void removeHandlers() {
        registrationManager.removeHandler();
    } 

    @Override
    protected void onCompleteSample( final double x, 
                                     final double y ) {
        super.onCompleteSample(x, y);
        
        log( "AreaSampler#onCompleteSample" );

        listening = false;

    }
    
    private void log( final String message ) {
        // GWT.log( message );
    }

}
