package org.roger600.gestures.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class GesturesEntryPoint implements EntryPoint {

    private FlowPanel mainPanel = new FlowPanel();
    private LienzoPanel panel = new LienzoPanel(1200, 900);
    private Layer layer = new Layer();
    
    @Override
    public void onModuleLoad() {

        RootPanel.get().add(mainPanel);
        mainPanel.add(panel);
        layer.setTransformable(true);
        panel.add(layer);

        GesturesTests tests = new GesturesTests(layer);
        tests.test();
        layer.draw();
        
    }
    
}
