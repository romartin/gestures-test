package org.roger600.gestures.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerTemplate;

import java.util.Collection;

public class GestureRecognizerPresenter implements IsWidget {

    private static final String EMPTY = "N/A";

    private final FlowPanel mainPanel = new FlowPanel();
    private final Collection<SamplerTemplate> templates;
    private HTML resultText = new HTML( EMPTY );

    public GestureRecognizerPresenter(final Collection<SamplerTemplate> templates) {
        this.templates = templates;
        this.mainPanel.add( resultText );
    }

    public void show( final Collection<SamplerFloatPoint> samples ) {
        
        if ( null == samples || samples.isEmpty() ) {

            resultText.setText( EMPTY );

        } else {

            resultText.setText( samples.toString() );

        }
        
    }
    
    @Override
    public Widget asWidget() {
        return mainPanel;
    }
}
