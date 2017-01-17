package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MenuElement {

    Rectangle2D rect;
    String text;
    String sound;
    Color boxColor;
    Color textColor;
    Font smallFont;
    Font bigFont;

    public MenuElement(int x, int y, int width, int height, String sound, String text, Color boxColor, Color textColor, String font, int fontSize) {
        this.rect = new Rectangle2D.Double(x, y, width, height);
        this.text = text;
        this.boxColor = boxColor;
        this.textColor = textColor;
        this.smallFont = new Font(font, Font.PLAIN, fontSize);
        this.bigFont = new Font(font, Font.BOLD, fontSize + 5);
        this.sound = sound;
    }

    public boolean isMousedOver(Point2D p) {
        return rect.contains(p);
    }

    public boolean isPressed(Point2D p, boolean isMousePressed) {
        return (isMousePressed && this.isMousedOver(p));
    }
    
    public String getSound() {
        return this.sound;
    }

    public void draw(DConsole dc) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(boxColor);
        dc.fillRect(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setPaint(textColor);
        dc.setFont(smallFont);
        dc.drawString(text, rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
    }

    public void drawBig(DConsole dc) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(boxColor);
        dc.fillRect(rect.getX() - 10, rect.getY() - 10, rect.getX() + rect.getWidth() + 10, rect.getY() + rect.getHeight() + 10);
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        dc.setPaint(textColor);
        dc.setFont(bigFont);
        dc.drawString(text, rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
    }

}
