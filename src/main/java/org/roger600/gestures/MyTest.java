/*
 * Demonstrator of
 * Continuous Recognition and Visualization of Pen Strokes and Touch-Screen Gestures
 * Version: 1.1
 * 
 * If you use this code for your research then please remember to cite our paper:
 * 
 * Kristensson, P.O. and Denby, L.C. 2011. Continuous recognition and visualization
 * of pen strokes and touch-screen gestures. In Procceedings of the 8th Eurographics
 * Symposium on Sketch-Based Interfaces and Modeling (SBIM 2011). ACM Press: 95-102.
 * 
 * Copyright (C) 2011 by Per Ola Kristensson, University of St Andrews, UK.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.roger600.gestures;

import org.roger600.gestures.shared.ContinuousGestureRecognizer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class MyTest extends JFrame {

	private static final String WINDOW_TITLE = "Demonstrator of Continuous Recognition and Visualization of Pen Strokes and Touch-Screen Gestures";
	private static final String STROKE_SET2 = "Eight Directional Straight Line Strokes";
	private static final String PROBABILITY_LABEL = "Probability";
	
	private WindowListener windowListener = new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	};
	
	/**
	 * Constructs the demonstrator.
	 * 
	 * Creates a window and provides the system with two template sets for
	 * the user to play with.
	 */
	public MyTest() {

		addWindowListener(windowListener);
		setTitle(WINDOW_TITLE);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		List<ContinuousGestureRecognizer.Template> templates = generateDirectionalTemplates();
		final UpdatePane updatePane = new UpdatePane(templates);
		updatePane.setTemplates(templates);
		updatePane.setBorder(new TitledBorder(new EtchedBorder(), STROKE_SET2));
		contentPane.add(updatePane, BorderLayout.SOUTH);
		final InputPane inputPane = new InputPane(templates, updatePane);
		inputPane.setPreferredSize(new Dimension(200, 200));
		inputPane.setTemplates(templates);
		contentPane.add(inputPane, BorderLayout.CENTER);

		pack();
		setLocationByPlatform(true);
		setVisible(true);
	}

		
	/**
	 * Creates the demonstrator.
	 * 
	 * @param args command-line arguments; this parameter is ignored
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
		}
		Locale.setDefault(Locale.US);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyTest();
			}
		});
	}

	/**
	 * This class shows recognition updates for all templates registered.
	 * 
	 * It can handle an arbitrary amount of templates. Updates are done by
	 * calling setValue with a template id and the new probability the template
	 * is associated with. 
	 * 
	 * @author Per Ola Kristensson
	 *
	 */
	private static class UpdatePane extends JPanel {
		
		private LinkedHashMap<String, JLabel> labelMap = new LinkedHashMap<String, JLabel>();
		DecimalFormat f = new DecimalFormat("0.00000");
		
		private UpdatePane(List<ContinuousGestureRecognizer.Template> templates) {
			super();
			setTemplates(templates);
		}
		
		private void setTemplates(List<ContinuousGestureRecognizer.Template> templates) {
			labelMap.clear();
			removeAll();
			int cells = templates.size() * 2;
			int rows = cells / 4;
			setLayout(new GridLayout(rows, 8));
			for (ContinuousGestureRecognizer.Template t : templates) {
				JLabel l = new JLabel(createIconForTemplate(t));
				l.setBorder(new TitledBorder(t.id));
				add(l);
				JLabel v = new JLabel(" ");
				v.setBorder(new TitledBorder(PROBABILITY_LABEL));
				add(v);
				labelMap.put(t.id.toLowerCase(), v);
			}
			revalidate();
		}
		
		private Icon createIconForTemplate(ContinuousGestureRecognizer.Template template) {
			BufferedImage bImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bImg.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
			List<ContinuousGestureRecognizer.Pt> pts = ContinuousGestureRecognizer.normalize(template.pts, Math.round(bImg.getWidth() * 0.25f), Math.round(bImg.getHeight() * 0.25f), Math.round(bImg.getWidth() * 0.75f), Math.round(bImg.getHeight() * 0.75f));
			paintTemplate(g2d, Color.BLACK, pts);
			g2d.dispose();
			ImageIcon icon = new ImageIcon(bImg);
			return icon;
		}
		
		private void paintTemplate(Graphics2D g2d, Color color, List<ContinuousGestureRecognizer.Pt> pts) {
			g2d.setColor(color);
			Object oldAAHint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Iterator<ContinuousGestureRecognizer.Pt> i = pts.iterator();
			if (i.hasNext()) {
				ContinuousGestureRecognizer.Pt pt0 = i.next();
				g2d.drawOval(pt0.x - 5, pt0.y - 5, 10, 10);
				while (i.hasNext()) {
					ContinuousGestureRecognizer.Pt pt1 = i.next();
					g2d.drawLine(pt0.x, pt0.y, pt1.x, pt1.y);
					pt0 = pt1;
				}
			}
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
		}
		
		private void resetValues() {
			for (JLabel l : labelMap.values()) {
				l.setText(" ");
				l.setBorder(new TitledBorder(PROBABILITY_LABEL));
			}
		}
		
		private void highlightValue(String id) {
			JLabel h = labelMap.get(id.toLowerCase());
			for (JLabel l : labelMap.values()) {
				if (l == h) {
					l.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 2), PROBABILITY_LABEL));
				}
				else {
					l.setBorder(new TitledBorder(PROBABILITY_LABEL));
				}
			}
		}
		
		private void setValue(String id, double value) {
			JLabel v = labelMap.get(id.toLowerCase());
			if (v != null) {
				v.setText(f.format(value));
			}
		}
		
	}

	/**
	 * This class does the following:
	 * 
	 * 1. Keeps track of pointer events---up, down, drag.
	 * 2. Calls the recognizer when drag or up events have occurred.
	 * 3. Delegates recognition results to the update pane above.
	 * 
	 * @author Per Ola Kristensson
	 *
	 */
	private static class InputPane extends JPanel {
		
		private MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				handlePress(e.getX(), e.getY());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				handleRelease(e.getX(), e.getY());
			}
		};
		private MouseMotionListener mouseMotionListener = new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				handleDrag(e.getX(), e.getY());
			}
		};
		
		ContinuousGestureRecognizer recognizer = null;
		List<ContinuousGestureRecognizer.Pt> input = new ArrayList<ContinuousGestureRecognizer.Pt>();
		private UpdatePane updatePane = null;
		
		private InputPane(List<ContinuousGestureRecognizer.Template> templates, UpdatePane updatePane) {
			super();
			if (templates == null) {
				throw new NullPointerException("templates cannot be null");
			}
			if (updatePane == null) {
				throw new NullPointerException("updatePane cannot be null");
			}
			this.updatePane = updatePane;
			addMouseListener(mouseListener);
			addMouseMotionListener(mouseMotionListener);
			/*
			 * Creates the continuous gesture recognizer for the template set. This
			 * template set can be changed later by calling the recognizer's
			 * setTemplateSet method. The second parameter is the distance between
			 * sampling points in the normalized gesture space (1000 x 1000 units).
			 * A lower value improves precision at the cost of increased memory and
			 * processing time.
			 */
			recognizer = new ContinuousGestureRecognizer(templates, 5);
		}
		
		private void setTemplates(List<ContinuousGestureRecognizer.Template> templates) {
			recognizer.setTemplateSet(templates);
		}
		
		private void handlePress(int x, int y) {
			input.clear();
			input.add(new ContinuousGestureRecognizer.Pt(x, y, false));
			repaint();
		}
		
		private void handleDrag(int x, int y) {
			input.add(new ContinuousGestureRecognizer.Pt(x, y, false));
			recognize();
			repaint();
		}
		
		private void handleRelease(int x, int y) {
			input.add(new ContinuousGestureRecognizer.Pt(x, y, false));
			input.clear();
			recognize();
			updatePane.resetValues();
			repaint();
		}
		
		private void recognize() {
			if (input == null || input.size() < 2) {
				return;
			}
			List<ContinuousGestureRecognizer.Result> results = recognizer.recognize(input);
			if (results == null) {
				return;
			}
			for (ContinuousGestureRecognizer.Result r : results) {
				updatePane.setValue(r.template.id, r.prob);
			}
			if (results.size() > 0) {
				updatePane.highlightValue(results.get(0).template.id);
			}
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			paintBackground(g2d, Color.WHITE);
			paintInput(g2d, Color.BLACK);
		}
		
		private void paintBackground(Graphics2D g2d, Color color) {
			g2d.setColor(color);
			Insets insets = getInsets();
			Dimension d = getSize();
			int w = d.width - (insets.left + insets.right);
			int h = d.height - (insets.top + insets.bottom);
			g2d.fillRect(0, 0, w, h);
			Object oldAAHint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Font font = new Font("SanSerif", Font.BOLD, 48);
			TextLayout layout = new TextLayout("Draw here", font, g2d.getFontRenderContext());
			Rectangle2D bounds = layout.getBounds();
			int x = w / 2 - (int)Math.round((bounds.getWidth()) / 2.0d);
			int y = h / 2 - (int)Math.round((bounds.getHeight()) / 2.0d) + (int)Math.round(bounds.getHeight());
			g2d.setColor(Color.LIGHT_GRAY);
			layout.draw(g2d, x, y);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
		}
		
		private void paintInput(Graphics2D g2d, Color color) {
			g2d.setColor(color);
			Object oldAAHint = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Iterator<ContinuousGestureRecognizer.Pt> i = input.iterator();
			if (i.hasNext()) {
				ContinuousGestureRecognizer.Pt pt0 = i.next();
				g2d.drawOval(pt0.x - 5, pt0.y - 5, 10, 10);
				while (i.hasNext()) {
					ContinuousGestureRecognizer.Pt pt1 = i.next();
					g2d.drawLine(pt0.x, pt0.y, pt1.x, pt1.y);
					pt0 = pt1;
				}
			}
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint);
		}
		
	}
	
	private static List<ContinuousGestureRecognizer.Template> generateDirectionalTemplates() {
		List<ContinuousGestureRecognizer.Template> directionalTemplates = new ArrayList<ContinuousGestureRecognizer.Template>();
		List<ContinuousGestureRecognizer.Pt> nPoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		nPoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		nPoints.add(new ContinuousGestureRecognizer.Pt(0, -1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("North", nPoints));
		List<ContinuousGestureRecognizer.Pt> sPoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		sPoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		sPoints.add(new ContinuousGestureRecognizer.Pt(0, 1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("South", sPoints));
		List<ContinuousGestureRecognizer.Pt> wPoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		wPoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		wPoints.add(new ContinuousGestureRecognizer.Pt(-1, 0));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("West", wPoints));
		List<ContinuousGestureRecognizer.Pt> ePoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		ePoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		ePoints.add(new ContinuousGestureRecognizer.Pt(1, 0));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("East", ePoints));
		List<ContinuousGestureRecognizer.Pt> nwPoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		nwPoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		nwPoints.add(new ContinuousGestureRecognizer.Pt(-1, -1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("NorthWest", nwPoints));
		List<ContinuousGestureRecognizer.Pt> nePoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		nePoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		nePoints.add(new ContinuousGestureRecognizer.Pt(1, -1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("NorthEast", nePoints));
		List<ContinuousGestureRecognizer.Pt> swPoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		swPoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		swPoints.add(new ContinuousGestureRecognizer.Pt(-1, 1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("SouthWest", swPoints));
		List<ContinuousGestureRecognizer.Pt> sePoints = new ArrayList<ContinuousGestureRecognizer.Pt>();
		sePoints.add(new ContinuousGestureRecognizer.Pt(0, 0));
		sePoints.add(new ContinuousGestureRecognizer.Pt(1, 1));
		directionalTemplates.add(new ContinuousGestureRecognizer.Template("SouthEast", sePoints));
		return directionalTemplates;
	}
	
}
