package pkgfinal.project;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class FinalProject {

    public static Random randGen = new Random();

    public static void main(String[] args) {

        DConsole dc = new DConsole(1200, 800);
        playSong("miceonvenus");
        dc.setMouseMode(DConsole.CURSOR_HIDDEN);
        while (true) {
            int gamestarted = 0;//Keeps track of which menu you're on
            int playbutton1 = 0;//for menu sounds
            int playbutton2 = 0;

            ArrayList<Structure> structs = null;
            //// THE STRUCTURES NEED TO BE LISTED BELOW ////
            Player player = new Player(0, 0);

            while (gamestarted == 0) {
                dc.setBackground(Color.GRAY);

                dc.setOrigin(DConsole.ORIGIN_CENTER); //Text Display
                dc.setPaint(Color.BLACK);
                dc.setFont(new Font("Times New Roman", Font.BOLD, 50));
                dc.drawString("Adventure Quest: The Wandering!!!!", 600, 100);

                dc.setPaint(Color.CYAN);
                if (dc.getMouseXPosition() >= 490 && dc.getMouseXPosition() 
                        <= 710 && dc.getMouseYPosition() >= 385 && dc.getMouseYPosition() <= 435) {
                    dc.fillRect(600, 410, 240, 60);
                } else {
                    dc.fillRect(600, 410, 220, 50);
                }

                if (dc.getMouseXPosition() >= 490 && dc.getMouseXPosition() 
                        <= 710 && dc.getMouseYPosition() >= 485 && dc.getMouseYPosition() <= 535) {
                    dc.fillRect(600, 510, 240, 60);
                } else {
                    dc.fillRect(600, 510, 220, 50);
                }

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.setPaint(Color.BLACK);

                if (dc.getMouseXPosition() >= 490 && dc.getMouseXPosition() <= 710 && dc.getMouseYPosition() >= 385 && dc.getMouseYPosition() <= 435) {
                    dc.setFont(new Font("Times New Roman", Font.BOLD, 42));
                    dc.drawString("New Game", 600, 400);
                    if (playbutton1==0) {
                        playSong("button1");
                        playbutton1=1;                       
                    } if (dc.isMouseButton(1)) {
                        playSong("button2");
                        gamestarted = 2;//New Game
                    }
                } else {
                    dc.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                    dc.drawString("New Game", 600, 400);
                    playbutton1=0;
                    
                }

                if (dc.getMouseXPosition() >= 490 && dc.getMouseXPosition() <= 710 && dc.getMouseYPosition() >= 485 && dc.getMouseYPosition() <= 535) {
                    dc.setFont(new Font("Times New Roman", Font.BOLD, 42));
                    dc.drawString("Load Game", 600, 500);
                    if (playbutton2==0) {
                        playSong("button1");
                        playbutton2=1;
                    }
                } else {
                    dc.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                    dc.drawString("Load Game", 600, 500);
                    playbutton2=0;
                    if (dc.isMouseButton(1)) {
                        gamestarted = 1; //Load
                        playSong("button2");
                    }
                }

                dc.setOrigin(DConsole.ORIGIN_CENTER);
                dc.fillEllipse(dc.getMouseXPosition(), dc.getMouseYPosition(), 25, 25);
                
                dc.redraw();
                dc.pause(20);
                dc.clear();
            }
            while (gamestarted == 1) { //New Game
                dc.setBackground(Color.GRAY);
                
                if(dc.isKeyPressed(27)) {
                    gamestarted = 0;
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

            while (gamestarted == 2) { //Load Game
                dc.setBackground(Color.GRAY);
                
                if(dc.isKeyPressed(27)) {
                    gamestarted = 0;
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

                dc.redraw();
                dc.pause(20);
                dc.clear();
            }

            while (gamestarted == 3) { //Main game loop
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
