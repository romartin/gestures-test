package org.roger600.gestures.client.sampler;

import com.ait.lienzo.client.core.event.*;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;

public class AreaSampler extends AbstractMouseSampler {

    private static final int PERIOD = 200;
    
    private final HandlerRegistrationManager registrationManager = new HandlerRegistrationManager();
    private final Layer layer;
    private final Rectangle area;
    private SamplerCallback callback;
    private HandlerRegistration startHandler = null;
    
    public AreaSampler(final Layer layer, 
                       final double x,
                       final double y,
                       final double width, 
                       final double height,
                       final SamplerCallback callback ) {
        
        this.layer = layer;
        this.callback = callback;

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
        
        if ( null != startHandler ) {
            startHandler.removeHandler();
        }

        layer.remove( area );
        
    }
    
    private void startListening() {
        
        log( "AreaSampler#startListening" );
        
        startHandler =
                area.addNodeMouseDownHandler(new NodeMouseDownHandler() {
                    @Override
                    public void onNodeMouseDown(final NodeMouseDownEvent event) {

                        log( "AreaSampler#onNodeMouseDown" );
                        
                        startHandler.removeHandler();
                        
                        start(callback);

                    }

                });

    }

    @Override
    protected void doAddHandlers() {

        registrationManager.register(
                
                area.addNodeMouseMoveHandler( new NodeMouseMoveHandler() {
            
                @Override
                public void onNodeMouseMove( final NodeMouseMoveEvent event ) {

                    final double x = event.getX();
                    final double y = event.getY();

                    log( "AreaSampler#onNodeMouseMove" );
    
                    takeSample( x, y );
                    
                }
                
            }));

        registrationManager.register(
            
            area.addNodeMouseUpHandler(new NodeMouseUpHandler() {
                
                @Override
                public void onNodeMouseUp( final NodeMouseUpEvent event ) {
    
                    final double x = event.getX();
                    final double y = event.getY();

                    log( "AreaSampler#onNodeMouseUp" );
                    
                    onCompleteSample( x ,y );
                }
                
            }));
        
    }
    
    private void removeHandlers() {
        registrationManager.removeHandler();
    } 

    @Override
    protected void onCompleteSample( final double x, 
                                     final double y ) {
        super.onCompleteSample(x, y);
        
        log( "AreaSampler#onCompleteSample" );

        removeHandlers();
        startListening();
    }
    
    private void log( final String message ) {
        GWT.log( message );
    }

}
