package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class FinalProject {

    public static Random randGen = new Random();

    public static void main(String[] args) throws FileNotFoundException {

        DConsole dc = new DConsole(1200, 800);
        playSound("miceonvenus");
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);

        ArrayList<Structure> structs = new ArrayList<>();
        structs.add(new Structure(5, 750, 1100, 100));
        structs.add(new Structure(1140, 700, 500, 100));
        structs.add(new Structure(1000, 710, 76, 50));

        ArrayList<MenuElement> elements = new ArrayList<>();
        Player player = new Player(300, 500);//Initialize a new player

        int gameState = 3;//Keeps track of which menu you're on
        int damage = 0;
        int hearts = 0;
        int playerHealthMax = 50 + hearts;
        int playerHealth = 50 + hearts - damage;
        while (true) {
            Point2D mousePos = new Point2D.Double();

            //Clear the previous elements and make new ones once before entering the while loop
            if (gameState == 0) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 100, 0, 0, null, "Adventure Quest: The Wandering!", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 2, 400, 200, 70, "button1", "New Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 2, 500, 200, 70, "button1", "Load Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 0) {
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);
                    elements.get(0).draw(dc, false);

                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            gameState = i;
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }
            if (gameState == 1) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 50, 0, 0, null, "Pick a new game file.", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 4 * 1, 320, 150, 150, "button1", "File 1", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 2, 320, 150, 150, "button1", "File 2", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 3, 320, 150, 150, "button1", "File 3", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 1) { //New Game
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);

                    if (dc.isKeyPressed(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

                    elements.get(0).draw(dc, false);

                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {

                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }
            if (gameState == 2) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 50, 0, 0, null, "Pick a game file to load.", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 4 * 1, 320, 150, 150, "button1", "File 1", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 2, 320, 150, 150, "button1", "File 2", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 3, 320, 150, 150, "button1", "File 3", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 2) { //Load Game
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);

                    if (dc.isKeyPressed(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

                    elements.get(0).draw(dc, false);

                    Scanner fileInput = null;
                    int save = 0;
                    String saveName = null;
                    //Draws all the elements and goes into file writing things if they're clicked on
                    for (int i = 1; i < 4; i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            fileInput = new Scanner(new File("save1.txt"));
                            save = i;
                            saveName = fileInput.nextLine();
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }
            if (gameState == 3) {
                elements.clear();
                elements.add(new MenuElement(150, 35, 270, 40, null, null, Color.BLACK, null, null, 0));
                elements.add(new MenuElement(150, 35, 260, 30, null, "0/0", Color.RED, Color.WHITE, "Times New Roman", 20));

                while (player.isAlive()) { //Main game loop
                    drawPicture(dc, player, "pixelForest");

                    elements.get(1).setText(playerHealth + " / " + playerHealthMax);
                    for (int i = 0; i < elements.size(); i++) {
                        elements.get(i).draw(dc, false);
                    }

                    player.gravityForce();
                    player.frictionForce();

                    player.resetGrounded(structs);
                    player.isTouchingStructure(structs);

                    player.moveCommands(dc);

                    player.recordPrevValues();
                    player.move();
                    player.scroll();
                    player.draw(dc);

                    for (Structure s : structs) {
                        s.draw(dc, player);

                    }

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }
        }
    }

    public static void playSound(String s) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("Sounds/" + s + ".wav")));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void drawPicture(DConsole dc, Player p, String s) {
        dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
        dc.drawImage("Pictures/" + s + ".jpg", 0 - p.getScroll(), 0);
    }
}
