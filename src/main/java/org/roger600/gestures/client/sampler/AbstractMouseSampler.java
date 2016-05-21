package org.roger600.gestures.client.sampler;

import com.google.gwt.user.client.Timer;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractMouseSampler {

    public interface SamplerCallback {
        
        void onComplete( Collection<SamplerFloatPoint> samples );
        
    }
    
    private static final int SAMPLER_PERIOD = 500;
    
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            takeSample = true;
        }
    };
    
    private final Collection<SamplerFloatPoint> points = new LinkedList<>();
    private boolean takeSample = false;
    private SamplerCallback callback = null;

    protected abstract void doAddHandlers();
    
    public void start( final SamplerCallback callback ) {
        
        this.callback = callback;
        
        points.clear();
        
        startTimer();
        
        doAddHandlers();
    }
    
    protected void takeSample( final double x, 
                             final double y ) {
        
        if ( takeSample ) {

            takeSample = false;
            
            stopTimer();

            addSample( x, y );

            startTimer();
            
        }
        
    }

    protected void onCompleteSample( final double x,
                               final double y ) {
        addSample( x, y );

        if ( null != callback ) {
            callback.onComplete( points );
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
