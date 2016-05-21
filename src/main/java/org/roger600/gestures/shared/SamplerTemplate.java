package org.roger600.gestures.shared;

import java.util.Collection;

public class SamplerTemplate {

    private final String id;
    private final String name;
    private final Collection<SamplerFloatPoint> samples;

    public SamplerTemplate( final String id, 
                            final String name, 
                            final Collection<SamplerFloatPoint> samples ) {
        this.id = id;
        this.name = name;
        this.samples = samples;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<SamplerFloatPoint> getSamples() {
        return samples;
    }
}
