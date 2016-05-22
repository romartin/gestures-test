package org.roger600.gestures.shared;

import com.ait.lienzo.client.core.shape.MultiPath;

import java.util.Collection;

public class SamplerTemplate {

    private final String id;
    private final String name;
    private final MultiPath path;
    private final Collection<SamplerFloatPoint> samples;

    public SamplerTemplate(final String id,
                           final String name,
                           final MultiPath path, 
                           final Collection<SamplerFloatPoint> samples) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.samples = samples;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MultiPath getPath() {
        return path;
    }

    public Collection<SamplerFloatPoint> getSamples() {
        return samples;
    }
}
