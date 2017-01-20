package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MenuElement {

    int x;
    int y;
    int height;
    int width;
    Rectangle2D rect;
    String text;
    String sound;
    Color boxColor;
    Color textColor;
    Font smallFont;
    Font bigFont;

    public MenuElement(int x, int y, int width, int height, String sound, String text, Color boxColor, Color textColor, String font, int fontSize) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.rect = new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
        this.text = text;
        this.boxColor = boxColor;
        this.textColor = textColor;
        this.smallFont = new Font(font, Font.PLAIN, fontSize);
        this.bigFont = new Font(font, Font.BOLD, (int) (fontSize * 1.1));
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

    public void draw(DConsole dc, boolean big) {
        dc.setOrigin(DConsole.ORIGIN_CENTER);
        if (boxColor != null) {
            dc.setPaint(boxColor);
            if (big) {
                dc.fillRect(this.x * 0.9, this.y * 0.9, 1.1 * this.width, 1.1 * this.height);
            } else {
                dc.fillRect(this.x, this.y, this.width, this.height);
            }
        }

        dc.setPaint(textColor);
        if (big) {
            dc.setFont(bigFont);
        } else {
            dc.setFont(smallFont);
        }
        dc.drawString(text, this.x, this.y);
    }
}
