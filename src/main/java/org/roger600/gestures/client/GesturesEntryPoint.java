package org.roger600.gestures.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.roger600.gestures.client.template.MultiPathSampleTemplateBuilder;
import org.roger600.gestures.client.template.SampleTemplateBuilder;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

import java.util.ArrayList;
import java.util.Collection;

public class GesturesEntryPoint implements EntryPoint {

    
    private FlowPanel mainPanel = new FlowPanel();
    private GesturesPresenter gesturesPresenter;
    private GestureRecognizerPresenter recognizerPresenter;

    @Override
    public void onModuleLoad() {

        RootPanel.get().add( mainPanel );

        final Collection<SamplerTemplate> templates = getTemplates();
        // logTemplates( templates );
        
        gesturesPresenter = new GesturesPresenter( 500, 500, callback );
        gesturesPresenter.show( 50, 50, 300, 300 );

        recognizerPresenter = new GestureRecognizerPresenter( templates );
        mainPanel.add( gesturesPresenter );
        mainPanel.add( recognizerPresenter );
        
    }
    
    private Collection<SamplerTemplate> getTemplates() {
        
        /*return new ArrayList<SamplerTemplate>() {{
            add(MultiPathSampleTemplateBuilder.RECTANGLE );
            add(MultiPathSampleTemplateBuilder.CIRCLE );
            add(MultiPathSampleTemplateBuilder.PLUS );
            add(MultiPathSampleTemplateBuilder.MINUS );
            add(MultiPathSampleTemplateBuilder.XOR );
        }};*/

        return new ArrayList<SamplerTemplate>() {{
            add(SampleTemplateBuilder.RECTANGLE );
            add(SampleTemplateBuilder.CIRCLE );
            add(SampleTemplateBuilder.MINUS );
        }};
        
        
    }
    
    private void logTemplates( final Collection<SamplerTemplate> templates ) {
        
        for ( final SamplerTemplate template : templates ) {
            
            final String id = template.getId();
            final Collection<SamplerFloatPoint> points = template.getSamples();

            GWT.log(" TEMPLATE [" + id + "] -> { " + points.toString() + "}" );
            
        }
        
    } 
    
    private final GesturesPresenter.Callback callback = new GesturesPresenter.Callback() {
        @Override
        public void onComplete(final Collection<SamplerFloatPoint> samples) {
            
            recognizerPresenter.show( samples );
           
        }
    };
    
}
