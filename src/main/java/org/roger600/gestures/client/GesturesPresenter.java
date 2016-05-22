package org.roger600.gestures.client;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.gestures.client.sampler.AbstractMouseSampler;
import org.roger600.gestures.client.sampler.AreaSampler;
import org.roger600.gestures.client.sampler.draw.DrawableAreaSampler;
import org.roger600.gestures.client.sampler.draw.DrawableSamplers;
import org.roger600.gestures.shared.SamplerFloatPoint;

import java.util.Collection;

public class GesturesPresenter implements IsWidget {

    public interface Callback {
    
        void onComplete( Collection<SamplerFloatPoint> samples );
        
    }
    
    private final LienzoPanel panel;
    private final Layer layer = new Layer();
    
    private AreaSampler areaSampler;
    private final Callback presenterCallback;
    
    public GesturesPresenter(final int width, 
                             final int height,
                             final Callback presenterCallback) {
        
        this.panel = new LienzoPanel( width, height );
        this.presenterCallback = presenterCallback;
        initLayer();

    }
    
    private void initLayer() {

        layer.setTransformable(true);
        panel.add(layer);
        layer.draw();
        
    }
    
    public void show( final double x, 
                      final double y,
                      final double width,
                      final double height ) {
        
        this.areaSampler = new DrawableAreaSampler( layer, x, y, width, height, callback, DrawableSamplers.ARROW_SAMPLER );

    }
    
    private final AbstractMouseSampler.SamplerCallback callback = new AbstractMouseSampler.SamplerCallback() {

        @Override
        public void onComplete(final Collection<SamplerFloatPoint> samples) {

            presenterCallback.onComplete( samples );
            layer.batch();

        }

    };
    
    public void destroy() {
        areaSampler.destroy();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
