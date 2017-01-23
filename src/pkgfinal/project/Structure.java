package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Structure {

    private double height;
    private double width;
    private double x;
    private double y;
    private Rectangle2D rect;

    public Structure(double x, double y, double width, double height) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.rect = new Rectangle2D.Double(x, y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
    public Rectangle2D getRect() {
        return this.rect;
    }

    public void setX(double i) {
        x = i;
    }

    public void setY(double i) {
        y = i;
    }

    public void setWidth(double i) {
        width = i;
    }

    public void setHeight(double i) {
        height = i;
    }

    public void draw(DConsole dc, Player p) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(Color.BLACK);
        dc.fillRect(this.x - p.getScroll(), this.y, this.width, this.height);
    }
}
