package org.roger600.gestures.client.sampler;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;

public class RootSampler extends AbstractMouseSampler {

    @Override
    protected void doAddHandlers() {

        final HandlerRegistration[] handlerRegs = new HandlerRegistration[ 2 ];

        handlerRegs[ 0 ] = RootPanel.get().addDomHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove( final MouseMoveEvent mouseMoveEvent ) {

                double x = mouseMoveEvent.getX();
                double y = mouseMoveEvent.getY();

                takeSample( x, y );


            }

        }, MouseMoveEvent.getType() );

        handlerRegs[ 1 ] = RootPanel.get().addDomHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp( final MouseUpEvent mouseUpEvent ) {
                handlerRegs[ 0 ].removeHandler();
                handlerRegs[ 1 ].removeHandler();

                double x = mouseUpEvent.getX();
                double y = mouseUpEvent.getY();

                onCompleteSample( x ,y );

            }

        }, MouseUpEvent.getType() );

    }
    
}
