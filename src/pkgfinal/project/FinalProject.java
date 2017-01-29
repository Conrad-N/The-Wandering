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
        playSound("miceonvenus");//Main background music
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);
        //Initializing the ArrayLists for all the player characters and structures
        //MenuElement will get filled in later when the specsific situation calls for it
        ArrayList<MenuElement> elements = new ArrayList<>();
        ArrayList<Scanner> fileInputs = new ArrayList<>();
        ArrayList<Structure> structs = new ArrayList<>();
        structs.add(new Structure(70, 700, 50, 50));
        structs.add(new Structure(80, 650, 100, 20));
        structs.add(new Structure(5, 750, 1100, 100));
        structs.add(new Structure(1140, 700, 500, 100));
        structs.add(new Structure(1000, 710, 76, 50));
        structs.add(new Structure(-500, 0, 505, 900));
        structs.add(new Structure(0, 0, 1000, 60));
        
        ArrayList<SavePoint> savePoints = new ArrayList<>();
        savePoints.add(new SavePoint(1500, 650, 20, 20, 0));
        ArrayList<Player> players = new ArrayList<>();

        int gameState = 0;
        int currentPlayer = 0;
        int[] playersAtSaves = new int[3];

        while (true) {
            Point2D mousePos = new Point2D.Double();
            //Clear the previous elements and make new ones once before entering the while loop
            //This is done for every single gameState
            if (gameState == 0) {
                elements.clear();
                elements.add(new MenuElement(dc.getWidth() / 2, 100, 0, 0, null, "Adventure Quest: The Wandering!", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 2, 400, 200, 70, "button1", "New Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 2, 500, 200, 70, "button1", "Load Game", Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 0) {
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);
                    elements.get(0).draw(dc, false);
                    //Cheking if things are moused over or pressed and reacting acordingly
                    for (int i = 1; i < elements.size(); i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        elements.get(i).playSoundOnMouseOver(mousePos);
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            gameState = i;
                            if (gameState == 1) {
                                gameState = 3;
                            }
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else if (gameState == 2) {
                elements.clear();
                fileInputs.clear();
                fileInputs.add(new Scanner(new File("Saves/save1.txt")));
                fileInputs.add(new Scanner(new File("Saves/save2.txt")));
                fileInputs.add(new Scanner(new File("Saves/save3.txt")));
                elements.add(new MenuElement(dc.getWidth() / 2, 50, 0, 0, null, "Pick a game file to load.", null, Color.BLACK, "Times New Roman", 50));
                elements.add(new MenuElement(dc.getWidth() / 4 * 1, 320, 150, 150, "button1", fileInputs.get(0).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 2, 320, 150, 150, "button1", fileInputs.get(1).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                elements.add(new MenuElement(dc.getWidth() / 4 * 3, 320, 150, 150, "button1", fileInputs.get(2).nextLine(), Color.CYAN, Color.BLACK, "Times New Roman", 40));
                while (gameState == 2) { //Load Game
                    mousePos.setLocation(dc.getMouseXPosition(), dc.getMouseYPosition());
                    dc.setBackground(Color.GRAY);

                    if (dc.isKeyPressed(27)) {
                        gameState = 0;
                        playSound("button2");
                    }

                    elements.get(0).draw(dc, false);

                    //Draws all the elements and goes into file writing things if they're clicked on
                    for (int i = 1; i < 4; i++) {
                        elements.get(i).draw(dc, elements.get(i).isMousedOver(mousePos));
                        if (elements.get(i).isPressed(mousePos, dc.isMouseButton(1))) {
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 20, 20, 4, Color.RED));
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 10, 40, 7, Color.YELLOW));
                            players.add(new Player(fileInputs.get(i - 1).nextInt(), fileInputs.get(i - 1).nextInt(), 30, 15, 5, Color.BLUE));
                            gameState = 3;
                        }
                    }

                    dc.setOrigin(DConsole.ORIGIN_CENTER);
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(mousePos.getX(), mousePos.getY(), 10, 10);

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else if (gameState == 3) {
                if (players.size() == 0) {
                    players.add(new Player(200, 500, 20, 20, 4, Color.RED));
                    players.add(new Player(300, 500, 10, 40, 7, Color.YELLOW));
                    players.add(new Player(400, 500, 30, 15, 5, Color.BLUE));
                }
                while (gameState == 3) { //Main game loop
                    drawPicture(dc, players.get(currentPlayer), "pixelForest");

                    if (dc.getKeyPress('q')) {
                        currentPlayer--;
                        if (currentPlayer < 0) {
                            currentPlayer = players.size() - 1;
                        }
                        players.get(currentPlayer).setScroll(dc.getWidth() / 2);
                    }
                    if (dc.getKeyPress('e')) {
                        currentPlayer++;
                        if (currentPlayer > players.size() - 1) {
                            currentPlayer = 0;
                        }
                        players.get(currentPlayer).setScroll(dc.getWidth() / 2);
                    }

                    for (Player p : players) {
                        p.gravityForce();
                        p.frictionForce();
                        p.resetGrounded();
                        p.isTouchingStructure(structs);
                        p.isTouchingPlayer(players);
                        playersAtSaves = new int[3];//Resetting all the values to 0
                        int i = p.isTouchingSavePoint(savePoints);
                        if (i != -1) { //Checking to see if all the players are at SavePoints
                            playersAtSaves[i]++;
                        }
                    }
                    //You can only control the current player
                    players.get(currentPlayer).moveCommands(dc);

                    for (Player p : players) {
                        p.recordPrevValues();
                        p.move();
                        p.draw(dc, players.get(currentPlayer));
                    }

                    players.get(currentPlayer).scroll();

                    for (Structure s : structs) {
                        s.draw(dc, players.get(currentPlayer));
                    }
                    //Display which player you're controling
                    players.get(currentPlayer).drawArrow(dc);

                    for (int i = 0; i < playersAtSaves.length; i++) {
                        if (playersAtSaves[i] == 3) { //Let the pick the file they want to save to
                            gameState = 4;
                            elements.clear();
                            for (int j = 0; j < 8; j++) {
                                elements.add(new MenuElement(dc.getWidth() / 15 * (3 + i), 50, 0, 0, null, 'a', null, Color.BLACK, "Times New Roman", 30));
                            }
                            int currentDigit = 0;
                            while (gameState == 4) {
                                dc.clear();
                                dc.setBackground(Color.GRAY);
                                dc.redraw();
                                dc.pause(20);
                            }
                            elements.clear();
                        }
                    }

                    dc.redraw();
                    dc.pause(20);
                    dc.clear();
                }
            } else { //Incase gameState end up with an invalid value for some reason.
                System.out.println("You shouldn't be seeing this.");
                System.exit(-1);
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
