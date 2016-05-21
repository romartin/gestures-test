package org.roger600.gestures.client;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.Collection;

public class GesturesTests extends FlowPanel {

    private Layer layer;
    private MultiPath multiPath;
    
    public GesturesTests(Layer layer) {
        this.layer = layer;
    }

    public void test() {

        final double x = 50;
        final double y = 50;
        final double w = 100;
        final double h = 100;

        multiPath  = new MultiPath()
                .rect( 0, 0, w, h )
                .setStrokeColor("#000000")
                .setStrokeWidth( 5 )
                .setX( x )
                .setY( y );
        
        layer.add( multiPath );
        
        addButton();
    }
    
    private void addButton() {

        Rectangle button = new Rectangle(50, 50).setFillColor(ColorName.BLACK).setX(200).setY(200);
        button.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event) {
                logPoints();
            }
        });
        
        layer.add( button );
    }
    
    private void logPoints() {

        
        PathPartList path = multiPath.getPathPartList();
        Point2DArray points = path.getPoints();
        Collection<Point2D> pointsSet = points.getPoints();

        for ( Point2D point : pointsSet ) {
            final double px = point.getX();
            final double py = point.getY();
            GWT.log("[" + px + ", " + py + "]");
        }
        
    }
   
}
