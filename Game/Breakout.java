package Game;

import com.sun.javafx.geom.Vec2f;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Breakout {
    private static boolean debug = false;
    private Gui gui;
    private Timer timer;
    public static Line2D debugLine;
    public static Line2D[] brickDebugLines;

    public Breakout() {
        gui = new Gui("Breakout", 1280, 720);
    }

    public Breakout(int width, int height) {
        gui = new Gui("Breakout", width, height);
    }

    public void start() {
        timer = new Timer();

        Ball ball = new Ball((int) gui.getDimensions().x, (int) gui.getDimensions().y);
        ball.setRandomSpeed();
        ball.register(gui);

        Paddle paddle = new Paddle((int) gui.getDimensions().x, (int) gui.getDimensions().y, 10);
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




        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                ball.update();
                paddle.update();
                ball.handleCollision((int) gui.getDimensions().x, (int) gui.getDimensions().y, bricks, gui);
                paddle.handleCollision((int) gui.getDimensions().x, (int) gui.getDimensions().y, ball);

                gui.setTitle("Breakout || Speed: " + ball.getSpeed().toString());

                int counter = 0;
                for (Brick b : bricks) {
                    if (b != null) counter++;
                }
                if (counter == 0) stop();

                gui.render();
            }
        }, 0, 15);


    }

    public void stop() {
        timer.cancel();
        gui.reset();
        start();
    }

    public static void setDebug(boolean debug) {
        Breakout.debug = debug;
    }

    public static boolean getDebug() {
        return Breakout.debug;
    }

    Brick[] setupBricks() {

        //TODO: Brickmuster machen

        int bricksizeWidth = (int) gui.getDimensions().x / 10; //Eine Reihe: 8
        int bricksizeHeight = (int) gui.getDimensions().y / 28; //Eine Spalte: 5

        int howManyARow;

        ArrayList<Brick> bricks = new ArrayList<>();

        int startX = (int) (bricksizeWidth*1.1);
        int startY = (int) (bricksizeHeight*1.1);

        for (int i = 1; i <= 8; i++) {
            howManyARow = new Random().nextInt(6);
            System.out.println(howManyARow);

            for (int j = 1; j <= 5; j++) {
                if (j >= howManyARow) {
                    bricks.add(new Brick((int) gui.getDimensions().x, (int) gui.getDimensions().y, new Vec2f(startX*j, startY*i)));
                    System.out.println(new Vec2f(startX*j, startY*i).toString());
                }
            }
        }

        Brick[] out = new Brick[bricks.size()];
        for (int i = 0; i < bricks.size(); i++) {
            out[i] = bricks.get(i);
        }





        /*for (int i = 1; i <= 8; i++) {
            howManyARow = new Random().nextInt(6);
            System.out.println(howManyARow);

            if (howManyARow != 0) {
                for (int j = 1; j <= howManyARow; j++) {
                    bricks.add(new Brick((int) gui.getDimensions().x, (int) gui.getDimensions().y, new Vec2f(startX*j, startY*i)));
                    System.out.println(new Vec2f(startX*j, startY*i).toString());
                }
            }

        }*/


        return out;
    }
}
