package org.roger600.gestures.client;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.types.PathPartList;
import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.client.core.types.Transform;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.tooling.nativetools.client.collection.NFastArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.Collection;
import java.util.LinkedList;

public class GesturesTests extends FlowPanel {

    private Layer layer;
    private MultiPath rectangle;
    private MultiPath circle;
    private MultiPath polygon;
    
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
    
    private void addButton( ) {

        Rectangle button = new Rectangle(50, 50).setFillColor(ColorName.RED).setX(500).setY(200);
        button.addNodeMouseClickHandler(new NodeMouseClickHandler() {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event) {
                log();
            }
        });
        
        layer.add( button );
    }
    
    private void logPoints( String title, MultiPath multiPath ) {

        NFastArrayList<PathPartList> paths = multiPath.getPathPartListArray();
        
        GWT.log("  ");
        GWT.log("********" + title + "***********");
        for ( PathPartList path : paths ) {
            
            Point2DArray points = path.getPoints();
            
            Collection<Point2D> pointsSet = points.getPoints();
            // log( "******", pointsSet );
            
            Collection<Point2D> translated = translate( pointsSet );
            log( "", translated );
            
        }
        GWT.log("**********************************");
        GWT.log("  ");

    }
   
    private Collection<Point2D> translate( Collection<Point2D> points ) {
        
        double mx = 0;
        double my = 0;
        
        for ( Point2D point : points ) {
            
            double x = point.getX();
            double y = point.getY();
            
            if ( x < 0 && x < mx ) {
                mx = x;
            }
            
            if ( y < 0 && y < my ) {
                my = y;
            }
            
        }
        
        if ( mx >= 0 && my >= 0 ) {
            
            return points;
        }

        mx *= -1;
        my *= -1;

        Collection<Point2D> result = new LinkedList<>();
        for ( Point2D point : points ) {

            double x = point.getX();
            double y = point.getY();

            result.add( new Point2D( x + mx, y + my ) );
            
        }

        return result;
        
    }
    
    private void log() {

        logPoints( "Rectangle", rectangle );
        logPoints( "Circle", circle );
        
    }
    
    private void log( String pref, Collection<Point2D> p ) {

        for ( Point2D point : p ) {
            final double px = point.getX();
            final double py = point.getY();
            GWT.log( pref + "[" + px + ", " + py + "]");
        }
        
    }
}
