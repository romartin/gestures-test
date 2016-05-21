package org.roger600.gestures.client;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.ui.FlowPanel;
import org.roger600.gestures.client.sampler.LienzoSampleTemplateBuilder;
import org.roger600.gestures.client.sampler.MouseSampler;
import org.roger600.gestures.client.sampler.SamplerUtils;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;

public class GesturesTests extends FlowPanel {

    private Layer layer;
    private MultiPath rectangle;
    private MultiPath circle;
    private MultiPath polygon;
    
    private final MouseSampler mouseSampler = new MouseSampler();
    
    public GesturesTests(Layer layer) {
        this.layer = layer;
    }

    public void test() {

        final double w = 100;
        final double h = 100;

        rectangle  = new MultiPath()
                .rect( 0, 0, w, h )
                .setStrokeColor("#000000")
                .setStrokeWidth( 1 )
                .setX( 0 )
                .setY( 0 );

        circle  = new MultiPath()
                .circle( 50 )
                .setStrokeColor("#000000")
                .setStrokeWidth( 1 )
                .setX( 150 )
                .setY( 150 );
        
        log();

        layer.add( rectangle );
        layer.add( circle );
        
        addButton();
        
    }
    
    private void samplerTest() {
        
        mouseSampler.start(new Runnable() {
            @Override
            public void run() {
                Collection<SamplerFloatPoint> samples = mouseSampler.getSamples();
                SamplerUtils.logSamples( "Mouse Samples", samples );
            }
        });
        
    }
    
    private void addButton( ) {

        Rectangle button = new Rectangle(50, 50).setFillColor(ColorName.RED).setX(500).setY(200);
        button.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event) {
                samplerTest();
            }
        });
        
        layer.add( button );
    }
   
    
    private void log() {

        Collection<SamplerFloatPoint> rectangleSamples = LienzoSampleTemplateBuilder.build( rectangle );
        SamplerUtils.logSamples( "Rectangle", rectangleSamples );

        Collection<SamplerFloatPoint> circleSamples = LienzoSampleTemplateBuilder.build( circle );
        SamplerUtils.logSamples( "Circle", circleSamples );
        
    }
   
    
}
