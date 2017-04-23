package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

class DrawLabel extends JLabel {
    private ArrayList<Paddle> paddles;
    private ArrayList<Ball> baelle;
    private ArrayList<Brick> bricks;
    private ArrayList<Line2D> debugLines;

    public DrawLabel(int width, int height) {
        paddles = new ArrayList<>();
        baelle = new ArrayList<>();
        bricks = new ArrayList<>();
        debugLines = new ArrayList<>();

        this.setBounds(0, 0, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Paddle paddle : paddles) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect((int) paddle.getPos().x, (int) paddle.getPos().y, (int) paddle.getSize().x, (int) paddle.getSize().y);
            if (Breakout.getDebug()) {
                g2d.setColor(Color.BLUE);
                g2d.drawLine((int) paddle.getPos().x, (int) paddle.getPos().y, (int) paddle.getPos().x, (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.drawLine((int) (paddle.getPos().x + (paddle.getSize().x*(1.0/6.0))), (int) paddle.getPos().y, (int) (paddle.getPos().x + (paddle.getSize().x*(1.0/6.0))), (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.drawLine((int) (paddle.getPos().x + (paddle.getSize().x*(2.0/6.0))), (int) paddle.getPos().y, (int) (paddle.getPos().x + (paddle.getSize().x*(2.0/6.0))), (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.setColor(Color.RED);
                g2d.drawLine((int) (paddle.getPos().x + (paddle.getSize().x*(3.0/6.0))), (int) paddle.getPos().y, (int) (paddle.getPos().x + (paddle.getSize().x*(3.0/6.0))), (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.setColor(Color.BLUE);
                g2d.drawLine((int) (paddle.getPos().x + (paddle.getSize().x*(4.0/6.0))), (int) paddle.getPos().y, (int) (paddle.getPos().x + (paddle.getSize().x*(4.0/6.0))), (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.drawLine((int) (paddle.getPos().x + (paddle.getSize().x*(5.0/6.0))), (int) paddle.getPos().y, (int) (paddle.getPos().x + (paddle.getSize().x*(5.0/6.0))), (int) (paddle.getPos().y + paddle.getSize().y));
                g2d.drawLine((int) (paddle.getPos().x + paddle.getSize().x), (int) paddle.getPos().y, (int) (paddle.getPos().x + paddle.getSize().x), (int) (paddle.getPos().y + paddle.getSize().y));
            }
            if (Breakout.getDebug()) g2d.drawString(paddle.getPos().toString(), (int) paddle.getPos().x, (int) paddle.getPos().y);
        }

        for (Ball ball : baelle) {
            g2d.setColor(Color.CYAN);
            g2d.fillOval((int) ball.getPos().x, (int) ball.getPos().y, ball.getDurchmesser(), ball.getDurchmesser());
        }

        for (Brick brick : bricks) {
            g2d.setColor(brick.getColor());
            g2d.fillRect((int) brick.getPos().x, (int) brick.getPos().y, (int) brick.getSize().x, (int) brick.getSize().y);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + Breakout.getScore(), 10, 15);

        if (Breakout.getDebug()) {
            for (Line2D line : debugLines) {
                g2d.setColor(Color.RED);
                g2d.draw(line);
            }
        }

    }

    void update() {
        this.repaint();
    }

    void addPaddle(Paddle paddle) {
        paddles.add(paddle);
    }

    void removePaddle(Paddle paddle) {
        paddles.remove(paddle);
    }

    void addBrick(Brick brick) {
        bricks.add(brick);
    }

    void removeBrick(Brick brick) {
        bricks.remove(brick);
    }

    void addBall(Ball ball) {
        baelle.add(ball);
    }

    void removeBall(Ball ball) {
        baelle.remove(ball);
    }

    void addDebugLine(Line2D line) {
        debugLines.add(line);
    }

    void removeDebugLine(Line2D line) {
        debugLines.remove(line);
    }

    void reset() {
        paddles = new ArrayList<>();
        baelle = new ArrayList<>();
        bricks = new ArrayList<>();
        debugLines = new ArrayList<>();
    }
}
