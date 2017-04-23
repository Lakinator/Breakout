package Game;

import com.sun.javafx.geom.Vec2f;

import javax.swing.*;
import java.awt.geom.Line2D;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Gui {
    private JFrame jf;
    private DrawLabel drawLabel;

    public Gui(String title, int width, int height, boolean fullScreen) {
        jf = new JFrame();
        jf.setTitle(title);
        jf.setSize(width, height);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);

        if (fullScreen) {
            jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jf.setUndecorated(true);
        } else {
            jf.setExtendedState(JFrame.NORMAL);
            jf.setUndecorated(false);
        }

        drawLabel = new DrawLabel(width, height);
        jf.add(drawLabel);
        jf.addKeyListener(new KeyHandler());

        jf.setVisible(true);
    }

    public void addPaddle(Paddle paddle) {
        drawLabel.addPaddle(paddle);
    }

    public void removePaddle(Paddle paddle) {
        drawLabel.removePaddle(paddle);
    }

    public void addBall(Ball ball) {
        drawLabel.addBall(ball);
    }

    public void removeBall(Ball ball) {
        drawLabel.removeBall(ball);
    }

    public void addBrick(Brick brick) {
        drawLabel.addBrick(brick);
    }

    public void removeBrick(Brick brick) {
        drawLabel.removeBrick(brick);
    }

    void addDebugLine(Line2D line) {
        drawLabel.addDebugLine(line);
    }

    void removeDebugLine(Line2D line) {
        drawLabel.removeDebugLine(line);
    }

    void reset() {
        drawLabel.reset();
    }

    public void render() {
        drawLabel.update();
    }

    public void setTitle(String title) {
        jf.setTitle(title);
    }

    public String getTitle() {
        return jf.getTitle();
    }

    public Vec2f getDimensions() {
        return new Vec2f(jf.getWidth(), jf.getHeight());
    }
}
