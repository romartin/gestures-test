package org.roger600.gestures.client;

import java.util.Collection;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.roger600.gestures.client.sampler.AbstractMouseSampler;
import org.roger600.gestures.client.sampler.AreaSampler;
import org.roger600.gestures.client.sampler.draw.DrawableAreaSampler;
import org.roger600.gestures.client.sampler.draw.DrawableSamplers;
import org.roger600.gestures.shared.SamplerFloatPoint;
import org.roger600.gestures.shared.SamplerFloatPointProvider;
import org.roger600.gestures.shared.SamplerUtils;

public class GesturesPresenter implements IsWidget {

    public interface Callback {
    
        void onComplete( Collection<SamplerFloatPoint> samples );
        
    }
    
    private final LienzoPanel panel;
    private final Layer layer = new Layer();
    
    private AreaSampler areaSampler;
    private final Callback presenterCallback;
    private double x;
    private double y;
    
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
        
        this.x = x;
        this.y = y;
        this.areaSampler = new DrawableAreaSampler( layer, x, y, width, height, callback, DrawableSamplers.POINT_SAMPLER );

    }
    
    private final AbstractMouseSampler.SamplerCallback callback = new AbstractMouseSampler.SamplerCallback() {

        @Override
        public void onComplete(final Collection<SamplerFloatPoint> samples) {

            final Collection<SamplerFloatPoint> result = translate( samples );
            presenterCallback.onComplete( result );
            layer.batch();

        }

    };
    
    private Collection<SamplerFloatPoint> translate( final Collection<SamplerFloatPoint> samples ) {

        final SamplerFloatPoint r = new SamplerFloatPoint( -x, -y );
        return SamplerUtils.translateRelative( r, samples, SamplerFloatPointProvider.INSTANCE );

    }
    
    public void destroy() {
        areaSampler.destroy();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
