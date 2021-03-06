package org.roger600.gestures.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.gestures.shared.ContinuousGestureRecognizer;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

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

            GWT.log(" SAMPLE POINTS (#" + translated.size() + ") => " + translated.toString() );
            
            // SamplerUtils.printCode( samplePoints );

            List<ContinuousGestureRecognizer.Result> results = recognizer.recognize( translated );

            if (results == null) {

                resultText.setText( EMPTY );

            } else {

                int candidate = 0;
                double max = 0;
                
                int x = 0;
                for (ContinuousGestureRecognizer.Result r : results) {
                    if ( r.prob > max ) {
                        candidate = x;
                        max = r.prob;
                    }
                    x++;
                }
                
                x = 0;
                for (ContinuousGestureRecognizer.Result r : results) {

                    final String id = r.template.id;
                    final double prob = r.prob;

                    final boolean isTheCandidate = x == candidate;
                    
                    if ( isTheCandidate ) {
                        resultText.setText( "RESULT => " + id + " [" + prob + "]");
                    }
                    
                    final String prefix = x == candidate ? " **RESULT** " : " RESULT ";
                    GWT.log( prefix + " => " + id + " [" + prob + "]");
                    
                    x++;
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
