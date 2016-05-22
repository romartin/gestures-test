package org.roger600.gestures.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.gestures.client.sampler.Point2DProvider;
import org.roger600.gestures.shared.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GestureRecognizerPresenter implements IsWidget {

    private static final String EMPTY = "N/A";

    private final FlowPanel mainPanel = new FlowPanel();
    private ContinuousGestureRecognizer recognizer = null;
    private HTML resultText = new HTML( EMPTY );

    public GestureRecognizerPresenter(final Collection<SamplerTemplate> templates) {
        final List<ContinuousGestureRecognizer.Template> rt = getRecognizerTemplates( templates );
        this.recognizer = new ContinuousGestureRecognizer( rt, 25 );
        this.mainPanel.add( resultText );
    }

    public void show( final Collection<SamplerFloatPoint> samples ) {

        doRecognize( samples );
        
    }
    
    private void doRecognize( final Collection<SamplerFloatPoint> samplePoints ) {

        if ( null == samplePoints || samplePoints.isEmpty() ) {

            resultText.setText( EMPTY );
            
        } else {

            final List<ContinuousGestureRecognizer.Pt> translated = translate( samplePoints );

            GWT.log(" SAMPLE POINTS => " + translated.toString() );
            
            // SamplerUtils.printCode( samplePoints );

            List<ContinuousGestureRecognizer.Result> results = recognizer.recognize( translated );

            if (results == null) {

                resultText.setText( EMPTY );

            } else {

                for (ContinuousGestureRecognizer.Result r : results) {

                    final String id = r.template.id;
                    final double prob = r.prob;

                    resultText.setText( "RESULT => " + id + " [" + prob + "]");

                }

            }
            
        }
        
    }
    
    private List<ContinuousGestureRecognizer.Template> getRecognizerTemplates( final Collection<SamplerTemplate> templates ) {
        
        final List<ContinuousGestureRecognizer.Template> result = new LinkedList<>();
        
        for ( final SamplerTemplate template : templates ) {
            
            final Collection<SamplerFloatPoint> samplePoints = template.getSamples();
            
            final List<ContinuousGestureRecognizer.Pt> translated = translate( samplePoints );
            
            final ContinuousGestureRecognizer.Template t = 
                    new ContinuousGestureRecognizer.Template( template.getId(), translated );
            
            result.add( t );
            
        }
        
        return result;
    } 
    
    private List<ContinuousGestureRecognizer.Pt> translate( final Collection<SamplerFloatPoint> points ) {

        if ( null != points && !points.isEmpty() ) {
            
            final List<ContinuousGestureRecognizer.Pt> result = new LinkedList<>();

            for ( final SamplerFloatPoint point : points ) {

                final ContinuousGestureRecognizer.Pt p =
                        new ContinuousGestureRecognizer.Pt(
                                (int) point.getX(),
                                (int) point.getY() );

                result.add( p );

            }

            return result;
            
        }

        return new ArrayList<>();
    }
    
    @Override
    public Widget asWidget() {
        return mainPanel;
    }
    
}
