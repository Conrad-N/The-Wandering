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
        playSong("miceonvenus");
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);
        ArrayList<Structure> structs = null;
        //// THE STRUCTURES NEED TO BE LISTED BELOW ////
        ArrayList<MenuElement> elements = new ArrayList<>();
        Player player = new Player(0, 0);//Initialize a new player
        while (true) {
            int gameStarted = 0;//Keeps track of which menu you're on
            Point2D mousePos = new Point2D.Double();

            //Clear the previous elements and make new ones once before entering the while loop
            if (gameStarted == 0) {
                elements.clear();
                elements.add(new MenuElement(600, 100, 0, 0, null, "Adventure Quest: The Wandering!", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(600, 400, 200, 70, "button1", "New Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(600, 500, 200, 70, "button1", "Load Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
            }
            while (gameStarted == 0) {
                mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                dc.setBackground(Color.GRAY);
                elements.get(0).draw(dc, false);
                for (int i = 1; i < elements.size(); i++) {
                    elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                    if (true) {
                        
                    }
                }

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.fillEllipse(mousePos.getX(), mousePos.getY(), 12, 12);

                dc.redraw();
                dc.pause(20);
                dc.clear();
            }
            while (gameStarted == 1) { //New Game
                dc.setBackground(Color.GRAY);

                if (dc.isKeyPressed(27)) {
                    gameStarted = 0;
                    playSong("button2");
                }

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.BLACK);
                dc.setFont(new Font("Times New Roman", Font.BOLD, 50));
                dc.drawString("New Game", 600, 50);

                dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
                dc.setPaint(Color.CYAN);

                dc.fillRect(171, 320, 171, 160);
                //256, 400 is the middle

                dc.fillRect(513, 320, 171, 160);
                //598, 400

                dc.fillRect(855, 320, 171, 160);
                //940, 400

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.BLACK);
                dc.setFont(new Font("Times New Roman", Font.BOLD, 45));
                dc.drawString("Choose a Save File", 600, 700);

                dc.setPaint(Color.MAGENTA);
                dc.setFont(new Font("Times New Roman", Font.PLAIN, 40));

                dc.drawString("Save 1", 256, 390);
                dc.drawString("Save 2", 598, 390);
                dc.drawString("Save 3", 940, 390);

                dc.redraw();
                dc.pause(20);
                dc.clear();
            }

            while (gameStarted == 2) { //Load Game
                dc.setBackground(Color.GRAY);

                if (dc.isKeyPressed(27)) {
                    gameStarted = 0;
                    playSong("button2");
                }

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.BLACK);
                dc.setFont(new Font("Times New Roman", Font.BOLD, 50));
                dc.drawString("Load Game", 600, 50);

                dc.setOrigin(DConsole.ORIGIN_TOP_LEFT);
                dc.setPaint(Color.CYAN);

                dc.fillRect(171, 320, 171, 160);
                //256, 400 is the middle

                dc.fillRect(513, 320, 171, 160);
                //598, 400

                dc.fillRect(855, 320, 171, 160);
                //940, 400

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.BLACK);
                dc.setFont(new Font("Times New Roman", Font.BOLD, 45));
                dc.drawString("Choose a Save File", 600, 700);

                dc.setPaint(Color.MAGENTA);
                dc.setFont(new Font("Times New Roman", Font.PLAIN, 40));

                dc.drawString("Save 1", 256, 390);
                dc.drawString("Save 2", 598, 390);
                dc.drawString("Save 3", 940, 390);

                Scanner fileInput = null;
                int save = 0;
                String saveName = null;

                if (dc.isMouseButton() == true) { //if click check for if mouse is on any button

                    if (dc.getMouseXPosition() >= 171 && dc.getMouseXPosition() <= 342 //Save 1
                            && dc.getMouseYPosition() >= 320 && dc.getMouseYPosition() <= 480) {

                        try {
                            fileInput = new Scanner(new File("save1.txt")); //read file
                            save = 1;
                            saveName = fileInput.nextLine();

                        } catch (Exception e) { //the catch
                            System.out.println("Something's not good.");
                            System.exit(-1);
                        }
                    }

                    if (dc.getMouseXPosition() >= 513 && dc.getMouseXPosition() <= 684 //Save 2
                            && dc.getMouseYPosition() >= 320 && dc.getMouseYPosition() <= 480) {

                        try {
                            fileInput = new Scanner(new File("save2.txt")); //read file
                            save = 2;
                            saveName = fileInput.nextLine();

                        } catch (Exception e) { //the catch
                            System.out.println("Something's not good.");
                            System.exit(-1);
                        }
                    }

                    if (dc.getMouseXPosition() >= 855 && dc.getMouseXPosition() <= 1026 //Save 3
                            && dc.getMouseYPosition() >= 320 && dc.getMouseYPosition() <= 480) {

                        try {
                            fileInput = new Scanner(new File("save3.txt")); //read file
                            save = 3;
                            saveName = fileInput.nextLine();

                        } catch (Exception e) { //the catch
                            System.out.println("Something's not good.");
                            System.exit(-1);
                        }
                    }

                }

                dc.redraw();
                dc.pause(20);
                dc.clear();
            }

            while (gameStarted == 3) { //Main game loop
                dc.redraw();
                dc.pause(20);
                dc.clear();

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
            }
        }
    }

    public static void playSong(String s) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(s + ".wav")));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
}
