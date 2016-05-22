package org.roger600.gestures.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

import java.util.Collection;

public class GesturesEntryPoint implements EntryPoint {

    
    private FlowPanel mainPanel = new FlowPanel();
    private GesturesPresenter gesturesPresenter;
    private GestureRecognizerPresenter recognizerPresenter;

    @Override
    public void onModuleLoad() {

        RootPanel.get().add( mainPanel );
        RootPanel.get().add( recognizerPresenter );
        
        gesturesPresenter = new GesturesPresenter( 500, 500, callback );
        gesturesPresenter.show( 50, 50, 300, 300 );

        recognizerPresenter = new GestureRecognizerPresenter( getTemplates() );
        mainPanel.add( gesturesPresenter );
        mainPanel.add( recognizerPresenter );
        
    }
    
    private Collection<SamplerTemplate> getTemplates() {
        // TODO
        return null;
    }
    
    private final GesturesPresenter.Callback callback = new GesturesPresenter.Callback() {
        @Override
        public void onComplete(final Collection<SamplerFloatPoint> samples) {
            
            recognizerPresenter.show( samples );
           
        }
    };
    
}
