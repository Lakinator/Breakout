package Game;

import com.sun.javafx.geom.Vec2f;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Breakout {
    private static boolean debug = false;
    private Gui gui;
    private static int score;
    private boolean running;
    public static Line2D debugLine;
    public static Line2D[] brickDebugLines;

    public Breakout() {
        gui = new Gui("Breakout", 1280, 720, false);
    }

    public Breakout(int width, int height, boolean fullScreen) {
        gui = new Gui("Breakout", width, height, fullScreen);
    }

    public void start() {

        Ball ball = new Ball((int) gui.getDimensions().x, (int) gui.getDimensions().y);
        ball.setRandomSpeed();
        ball.register(gui);

        Paddle paddle = new Paddle((int) gui.getDimensions().x, (int) gui.getDimensions().y, 1);
        paddle.register(gui);

        Brick[] bricks = setupBricks();

        for (Brick brick : bricks) {
            brick.register(gui);
        }

        if (debug) {
            brickDebugLines = new Line2D.Float[bricks.length];
            for (int i = 0; i < bricks.length; i++) {
                brickDebugLines[i] = new Line2D.Float();
                gui.addDebugLine(brickDebugLines[i]);
            }

            debugLine = new Line2D.Float();
            gui.addDebugLine(debugLine);
        }

        running = true;

        long lastFrame = System.currentTimeMillis();

        while (running) {
            int counter = 0;
            for (Brick b : bricks) {
                if (b != null) counter++;
            }
            if (counter == 0) stop();


            long thisFrame = System.currentTimeMillis();
            float timeSinceLastFrame = thisFrame - lastFrame;
            lastFrame = thisFrame;

            ball.update(timeSinceLastFrame);
            paddle.update(timeSinceLastFrame);
            ball.handleCollision((int) gui.getDimensions().x, (int) gui.getDimensions().y, bricks, gui);
            paddle.handleCollision((int) gui.getDimensions().x, (int) gui.getDimensions().y, ball);
            gui.render();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void stop() {
        running = false;
        gui.reset();
        start();
    }

    public static void setDebug(boolean debug) {
        Breakout.debug = debug;
    }

    public static boolean getDebug() {
        return Breakout.debug;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Breakout.score = score;
    }

    Brick[] setupBricks() {

        //TODO: Brickmuster machen

        int bricksizeWidth = (int) gui.getDimensions().x / 10; //Eine Reihe: 8
        int bricksizeHeight = (int) gui.getDimensions().y / 28; //Eine Spalte: 5

        ArrayList<Brick> bricks = new ArrayList<>();

        int startX = (int) (bricksizeWidth*1.1);
        int startY = (int) (bricksizeHeight*1.1);
        int color_index = 0;

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 7; j++) {
                Color c = Color.BLACK;
                if (color_index == 0) {
                    c = Color.BLACK;
                    color_index++;
                }
                else if (color_index == 1) {
                    c = Color.BLUE;
                    color_index++;
                }
                else if (color_index == 2) {
                    c = Color.GREEN;
                    color_index = 0;
                }
                bricks.add(new Brick((int) gui.getDimensions().x, (int) gui.getDimensions().y, new Vec2f(startX*j, startY*i), c));
                //System.out.println(new Vec2f(startX*j, startY*i).toString());
            }
        }

        Brick[] out = new Brick[bricks.size()];
        for (int i = 0; i < bricks.size(); i++) {
            out[i] = bricks.get(i);
        }


        return out;
    }
}
