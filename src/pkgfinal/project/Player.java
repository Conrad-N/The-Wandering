package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Player {

    private boolean isGrounded;
    private boolean isAlive;
    private boolean scrollingRight;
    private boolean scrollingLeft;
    private double xChange;
    private double yChange;
    private double x;
    private double y;
    private double width;
    private double height;
    private double prevX;
    private double prevY;
    private int jumpHeight;
    private int scroll;

    public Player(double x, double y, double width, double height, int jumpHeight) {
        this.isGrounded = true;
        this.isAlive = true;
        this.xChange = 0;
        this.yChange = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.jumpHeight = jumpHeight;
        this.prevX = x;
        this.prevY = y;
        this.xChange = 0;
        this.scroll = 0;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void isTouchingStructure(ArrayList<Structure> structs) { //Check players current position in both dimentions and compare it
        for (Structure s : structs) {
            if (this.y + 20 > s.getY() && this.y < s.getY() + s.getHeight()) { //to the players previous position and that of the structure
                if (this.x + 20 > s.getX() && this.prevX + 20 <= s.getX()) {
                    this.x = s.getX() - 20;
                    this.xChange = 0;
                } else if (this.x < s.getX() + s.getWidth() && this.prevX >= s.getX() + s.getWidth()) {
                    this.x = s.getX() + s.getWidth();
                    this.xChange = 0;
                }
            }
            if (this.x + 20 > s.getX() && this.x < s.getX() + s.getWidth()) {
                if (this.y + 20 > s.getY() && this.prevY + 20 <= s.getY()) {
                    this.y = s.getY() - 20;
                    this.yChange = 0;
                    this.isGrounded = true;                                     //If the player is standing on something let them move
                } else if (this.y < s.getY() + s.getHeight() && this.prevY >= s.getY() + s.getHeight()) {
                    this.y = s.getY() + s.getHeight();
                    this.yChange = 0;
                }
            }
        }
    }

    public void isTouchingPlayer(ArrayList<Player> players) { //Check players current position in both dimentions and compare it
        for (Player p : players) {
            if (this.y + 20 > p.getY() && this.y < p.getY() + p.getHeight()) { //to the players previous position and that of the structure
                if (this.x + 20 > p.getX() && this.prevX + 20 <= p.getX()) {
                    this.x = p.getX() - 20;
                    this.xChange = 0;
                } else if (this.x < p.getX() + p.getWidth() && this.prevX >= p.getX() + p.getWidth()) {
                    this.x = p.getX() + p.getWidth();
                    this.xChange = 0;
                }
            }
            if (this.x + 20 > p.getX() && this.x < p.getX() + p.getWidth()) {
                if (this.y + 20 > p.getY() && this.prevY + 20 <= p.getY()) {
                    this.y = p.getY() - 20;
                    this.yChange = 0;
                    this.isGrounded = true;                                     //If the player is standing on something let them move
                } else if (this.y < p.getY() + p.getHeight() && this.prevY >= p.getY() + p.getHeight()) {
                    this.y = p.getY() + p.getHeight();
                    this.yChange = 0;
                }
            }
        }
    }

    public double getScroll() {
        return this.scroll;
    }

    public void recordPrevValues() { //Record previous values to compare when considering colision
        this.prevX = this.x;
        this.prevY = this.y;
    }

    public void gravityForce() {
        this.yChange += 0.2;
    }

    public void moveCommands(DConsole dc) { //Let the player move if on the ground or wall jump
        if (this.isGrounded && dc.getKeyPress('w')) {
            yChange = -this.jumpHeight;
        }
        if (dc.isKeyPressed('d')) {
            this.xChange = Math.min(xChange + 1, 3);
        }
        if (dc.isKeyPressed('a')) {
            this.xChange = Math.max(xChange - 1, -3);
        }
    }

    public void move() {
        this.x += this.xChange;
        this.y += this.yChange;
    }

    public void frictionForce() { //Slow the players movement as they go along the floor
        if (this.isGrounded) {
            xChange *= 0.9;
        }
    }

    public void draw(DConsole dc, Player p) { //Set the players color acording to thier charge and draw them
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.setPaint(Color.WHITE);
        dc.fillRect(this.x - p.scroll, this.y, this.width, this.height);
    }

    public void scroll() { //Scroll the screen, this will simply add a position modifier when drawing things.
        if (this.x - this.scroll > 1000) {
            this.scrollingRight = true;
        } else if (this.x - scroll < 200) {
            this.scrollingLeft = true;
        } else if (this.x - this.scroll < 800 && this.x - this.scroll > 400) {
            this.scrollingLeft = false;
            this.scrollingRight = false;
        }

        if (this.scrollingRight) {
            this.scroll += 6;
        } else if (this.scrollingLeft) {
            this.scroll -= 6;
        }
    }

    public void resetGrounded() {
        this.isGrounded = false;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
