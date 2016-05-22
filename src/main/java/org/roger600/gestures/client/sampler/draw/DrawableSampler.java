package org.roger600.gestures.client.sampler.draw;

import com.ait.lienzo.client.core.shape.IPrimitive;

public interface DrawableSampler {

    interface DrawableSingleSampler extends DrawableSampler {

        IPrimitive<?> draw(final double x0,
                           final double y0);

    }

    interface DrawableDoubleSampler extends DrawableSampler {

        IPrimitive<?> draw(final double x0,
                           final double y0,
                           final double x1,
                           final double y1);

    }
    
}
