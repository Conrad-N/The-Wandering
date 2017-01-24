package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
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

    public static void main(String[] args) {

        
        DConsole dc = new DConsole(1200, 800);
        playSound("miceonvenus");
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);

        ArrayList<Structure> structs = new ArrayList<>();
        structs.add(new Structure(5, 750, 1100, 100));

        ArrayList<MenuElement> elements = new ArrayList<>();
        Player player = new Player(300, 200);//Initialize a new player

        int gameState = 0;//Keeps track of which menu you're on

        while (true) {

            Point2D mousePos = new Point2D.Double();

            //Clear the previous elements and make new ones once before entering the while loop
            if (gameState == 0) {
                elements.clear();
                elements.add(new MenuElement(600, 100, 0, 0, null, "Adventure Quest: The Wandering!", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(600, 400, 200, 70, "button1", "New Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(600, 500, 200, 70, "button1", "Load Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 0) {
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);
                    elements.get(0).draw(dc, false);

                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
 //                       
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            gameState = i;
                            playSound("button1");
                            
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
                elements.add(new MenuElement(600, 50, 0, 0, null, "Pick a new game file.", null, Color.BLACK, "Times New Roman", 50));
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

//
                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    //
                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }
            if (gameState == 2) {
                elements.clear();
                elements.add(new MenuElement(600, 50, 0, 0, null, "Pick a game file to load.", null, Color.BLACK, "Times New Roman", 50));
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

   //
                    Scanner fileInput = null;
                    int save = 0;
                    String saveName = null;
                    //Draws all the elements and goes into file writing things if they're clicked on
                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            try {
                                fileInput = new Scanner(new File("save1.txt"));
                                save = i;
                                saveName = fileInput.nextLine();

                            } catch (Exception e) {
                                System.out.println("Something's not good.");
                                System.exit(-1);
                            }
                        }}
  //                  }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

       //
                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            }

            while (gameState == 3) { //Main game loop
                int damage = 0; //MOVE BEFORE START
                int hearts = 0;
                int playerhealthmax = 50 + hearts;
                int playerhealth = 50 + hearts - damage;

                dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
                dc.drawImage("forest1.jpg", 0 - player.getScroll(), 0);
                dc.setPaint(Color.BLACK);

                dc.fillRect(80, 30, 340, 70);
                dc.setPaint(Color.RED);
                dc.fillRect(100, 50, 300, 30);

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.WHITE);

                dc.setFont(new Font("Arial", Font.BOLD, 20));
                dc.drawString(playerhealth + " / " + playerhealthmax, 250, 60);

                player.gravityForce();
                player.frictionForce();

                player.setGrounded(false);
                for (Structure s : structs) {
                    player.isTouchingStructure(s);

                }

                player.moveCommands(dc);

                for (Structure s : structs) {
                    player.isTouchingStructure(s);

                }

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

    public static void playSound(String s) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(s + ".wav")));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
}
