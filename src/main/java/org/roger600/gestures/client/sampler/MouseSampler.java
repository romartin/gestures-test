package org.roger600.gestures.client.sampler;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;
import java.util.LinkedList;

public class MouseSampler {

    private static final int SAMPLER_PERIOD = 500;
    
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            takeSample = true;
        }
    };
    
    private final Collection<SamplerFloatPoint> points = new LinkedList<>();
    private boolean takeSample = false;

    public Collection<SamplerFloatPoint> getSamples() {
        return points;
    }

    public void start() {
        start( null );
    }
    
    public void start( final Runnable callback ) {
        
        points.clear();
        
        startTimer();
        
        final HandlerRegistration[] handlerRegs = new HandlerRegistration[ 2 ];

        handlerRegs[ 0 ] = RootPanel.get().addDomHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove( final MouseMoveEvent mouseMoveEvent ) {

                double x = mouseMoveEvent.getX();
                double y = mouseMoveEvent.getY();
                
               takeSample( x, y );
                

            }

        }, MouseMoveEvent.getType() );

        handlerRegs[ 1 ] = RootPanel.get().addDomHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp( final MouseUpEvent mouseUpEvent ) {
                handlerRegs[ 0 ].removeHandler();
                handlerRegs[ 1 ].removeHandler();

                double x = mouseUpEvent.getX();
                double y = mouseUpEvent.getY();

                addSample( x, y );
                
                if ( null != callback ) {
                    callback.run();
                }
                
            }

        }, MouseUpEvent.getType() );
    }
    
    private void takeSample( final double x, 
                             final double y ) {
        
        if ( takeSample ) {

            takeSample = false;
            
            stopTimer();

            addSample( x, y );

            startTimer();
            
        }
        
    }
    
    private void addSample( final double x,
                            final double y ) {

        points.add( new SamplerFloatPoint( x, y ) );
        
    }
    
    private void startTimer() {
        
        timer.schedule( SAMPLER_PERIOD );
        
    }

    private void stopTimer() {

        if ( timer.isRunning() ) {
            timer.cancel();
        }

    }
    
}
