package org.roger600.gestures.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

import java.util.Collection;

public class GesturesEntryPoint implements EntryPoint {

    private static final String EMPTY = "N/A";
    
    private FlowPanel mainPanel = new FlowPanel();
    private HTML resultText = new HTML( EMPTY );
    private GesturesPresenter gesturesPresenter;

    @Override
    public void onModuleLoad() {

        final FlowPanel resultPanel = new FlowPanel();

        RootPanel.get().add( mainPanel );
        RootPanel.get().add( resultPanel );
        
        gesturesPresenter = new GesturesPresenter( 500, 500, getTemplates(), callback );
        gesturesPresenter.show( 50, 50, 300, 300 );
        mainPanel.add( gesturesPresenter );
        
        resultPanel.add( resultText );
        
    }
    
    private Collection<SamplerTemplate> getTemplates() {
        // TODO
        return null;
    }
    
    private final GesturesPresenter.Callback callback = new GesturesPresenter.Callback() {
        @Override
        public void onComplete(final Collection<SamplerFloatPoint> samples) {
            
            if ( null == samples || samples.isEmpty() ) {
                
                resultText.setText( EMPTY );
                
            } else {
            
                resultText.setText( samples.toString() );
                
            }
            
        }
    };
    
}
