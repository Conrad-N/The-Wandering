package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;

public class Player {

    private boolean isGrounded;
    private boolean isAlive;
    private boolean scrollingRight;
    private boolean scrollingLeft;
    private double xChange;
    private double yChange;
    private double x;
    private double y;
    private double prevX;
    private double prevY;
    private double moveForce;
    private double scroll;

    public Player(double x, double y) {
        this.isGrounded = true;
        this.isAlive = true;
        this.xChange = 0;
        this.yChange = 0;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.moveForce = 0;
        this.scroll = 0;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void isTouchingStructure(Structure s) { //Check players current position in both dimentions and compare it
        if (this.y + 20 > s.getY() && this.y < s.getY() + s.getHeight()) { //to the players previous position and that of the structure
            if (this.x + 20 > s.getX() && this.prevX + 20 <= s.getX()) {
                this.x = s.getX() - 20;
                this.xChange = 0;//If the player is against a wall let them jump off of it
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

    public double getScroll() {
        return this.scroll;
    }

    public void recordPrevValues() { //Record previous values to compare when considering colision
        this.prevX = this.x;
        this.prevY = this.y;
    }

    public void gravityForce() {
        this.yChange += 0.1;
    }

    public void moveCommands(DConsole dc) { //Let the player move if on the ground or wall jump
        if (this.isGrounded && dc.getKeyPress('w')) {
            yChange = -5;
            //this.jumpCharge = 0.1;
        }

        if (this.isGrounded && dc.isKeyPressed('d')) {
            this.moveForce = Math.min(moveForce + 1, 5);
        }

        if (this.isGrounded && dc.isKeyPressed('a')) {
            this.moveForce = Math.max(moveForce - 1, -5);
        }
    }

    public void move() {
        this.x += this.xChange + this.moveForce;
        this.y += this.yChange;
    }

    public void frictionForce() { //Slow the players movement as they go along the floor
        if (this.isGrounded) {
            xChange *= 0.8;
        }
    }

    public void draw(DConsole dc) { //Set the players color acording to thier charge and draw them
        dc.fillRect(this.x - this.scroll, this.y, 20, 20);
    }

    public void scroll() { //Scroll the screen, this will simply add a position modifier when drawing things.
        if (this.x - this.scroll > 700) {
            this.scrollingRight = true;
        } else if (this.x - scroll < 100) {
            this.scrollingLeft = true;
        }

        if (this.scrollingRight) {
            this.scroll += 10;
            if (this.x - scroll < 550) {
                this.scrollingRight = false;
            }
        } else if (this.scrollingLeft) {
            this.scroll -= 10;
            if (this.x - scroll < 250) {
                this.scrollingRight = false;
            }
        }
    }

    public void setGrounded(boolean i) {
        this.isGrounded = i;
    }
}
