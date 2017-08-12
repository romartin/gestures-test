package org.roger600.gestures.client.sampler;

import java.util.Collection;
import java.util.LinkedList;

import com.google.gwt.user.client.Timer;
import org.roger600.gestures.shared.SamplerFloatPoint;

public abstract class AbstractMouseSampler {

    public interface SamplerCallback {
        
        void onComplete( Collection<SamplerFloatPoint> samples );
        
    }
    
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            takeSample = true;
        }
    };
    
    private final Collection<SamplerFloatPoint> points = new LinkedList<>();
    private int period = 100;
    private boolean takeSample = false;
    private SamplerCallback callback = null;

    public void setSamplerPeriod(final int period) {
        this.period = period;
    }

    public void start(final SamplerCallback callback ) {
        
        this.callback = callback;
        
        points.clear();
        
        startTimer();
        
    }

    protected void takeSample(final double x,
                              final double y ) {
        
        if ( takeSample ) {

            takeSample = false;
            
            stopTimer();

            addSample( x, y );

            doTakeSample( x, y );
            
            startTimer();
            
        }
        
    }
    
    protected void doTakeSample(final double x,
                              final double y) {
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
        
        timer.schedule( period );
        
    }

    private void stopTimer() {

        if ( timer.isRunning() ) {
            timer.cancel();
        }

    }
    
}
