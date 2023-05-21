package chev.game2.util;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import chev.game2.main.Game;

public class StringMover {
	
	private String[] strings;
	private Font[] fonts;
	
	public StringMover(String[] strings, Font[] fonts) {
		this.strings = strings;
		this.fonts = fonts;
	}

	public Point[] getPositions(int separator, Graphics2D g) {
		Point[] points = new Point[strings.length];
		int totalHeight = 0;
		int HautMoy = 0;
		
		for (int i = 0; i < points.length; i++) {
			totalHeight += getHeight(strings[i], g, fonts[i]);
		}
		
		HautMoy = totalHeight / points.length;
		totalHeight += (points.length - 1) * separator;
		
		for (int i = 0; i < points.length; i++) {
			int width = getWidth(strings[i], g, fonts[i]);
			points[i] = new Point((Game.WIDTH / 2 - width / 2), (Game.HEIGHT / 2 - totalHeight / 2 + (i + 1) * HautMoy + i * separator));
		}
		
		return points;
	}
	
	private int getHeight(String s, Graphics2D g, Font font) {
		GlyphVector gv = font.layoutGlyphVector(g.getFontRenderContext(), s.toCharArray(), 0, s.length(), Font.LAYOUT_LEFT_TO_RIGHT);
		Rectangle2D rec = gv.getVisualBounds();
		return (int) rec.getHeight();
	}
	
	private int getWidth(String s, Graphics2D g, Font font) {
		GlyphVector gv = font.layoutGlyphVector(g.getFontRenderContext(), s.toCharArray(), 0, s.length(), Font.LAYOUT_LEFT_TO_RIGHT);
		Rectangle2D rec = gv.getVisualBounds();
		return (int) rec.getWidth();
	}
}
