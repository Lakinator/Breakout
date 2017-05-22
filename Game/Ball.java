package Game;

import com.sun.javafx.geom.Vec2f;

import java.awt.*;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Ball {
    private Vec2f pos;
    private Vec2f speed;
    private int durchmesser;

    public Ball(int frameWidth, int frameHeight) {
        this.pos = new Vec2f();
        this.speed = new Vec2f();
        resizeBall(frameWidth, frameHeight);
        centerBallPos(frameWidth, frameHeight);
    }

    public void resizeBall(int frameWidth, int frameHeight) {
        int temp = frameWidth + frameHeight;
        durchmesser = temp/48;
    }

    public void centerBallPos(int frameWidth, int frameHeight) {
        pos.set( (frameWidth/2) - (durchmesser / 2) , (frameHeight/2) - (durchmesser / 2) );
    }

    public void update(float timeSinceLastFrame) {
        pos.x += speed.x * timeSinceLastFrame;
        pos.y += speed.y * timeSinceLastFrame;
    }

    public void setRandomSpeed() {
        float randX = getRndInteger(-0.3f, 0.3f);
        while (randX < 0.1 && randX > -0.1) {
            randX = getRndInteger(-0.3f, 0.3f);
        }

        float randY = getRndInteger(-0.3f, 0.3f);
        while (randY < 0.1 && randY > -0.1) {
            randY = getRndInteger(-0.3f, 0.3f);
        }

        speed.x = randX;
        speed.y = randY;
    }

    private float getRndInteger(float min, float max) {
        return (float) Math.floor(Math.random() * (max - min + 1) ) + min;
    }

    public void handleCollision(int frameWidth, int frameHeight, Brick[] bricks, Gui gui) {
        if (pos.x < 0) {
            speed.x *= -1;
        }

        if (pos.x + (durchmesser * 1.2) > frameWidth) {
            speed.x *= -1;
        }

        if (pos.y < 0) {
            speed.y *= -1;
        }

        if (pos.y + (durchmesser * 2) > frameHeight - frameHeight/18) {
            //Leben verloren
            Breakout.setScore(Breakout.getScore() > 100 ? Breakout.getScore() - 100 : 0);
            this.centerBallPos(frameWidth, frameHeight);
            this.setRandomSpeed();
        }

        for (int i = 0; i < bricks.length; i++) {
            if (bricks[i] != null) {
                if (handleBrickCollision(bricks[i], i, gui)) {
                    bricks[i] = null;
                }
            }
        }

    }

    private boolean handleBrickCollision(Brick brick, int index, Gui gui) {
        int realRadius = this.durchmesser /2;

        // Find the closest point to the circle within the rectangle
        float closestX = clamp(this.getCenterPos().x, brick.getPos().x, brick.getPos().x + brick.getSize().x);
        float closestY = clamp(this.getCenterPos().y, brick.getPos().y, brick.getPos().y + brick.getSize().y);

        if (Breakout.getDebug()) Breakout.brickDebugLines[index].setLine(closestX, closestY, this.getCenterPos().x, this.getCenterPos().y);

        // Calculate the distance between the circle's center and this closest point
        float distanceX = Math.abs(this.getCenterPos().x - closestX);
        float distanceY = Math.abs(this.getCenterPos().y - closestY);

        // If the distance is less than the circle's durchmesser, an intersection occurs
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        if (distanceSquared < (realRadius * realRadius)) {

            //Hit an den Seiten Links und Rechts oder Oben und Unten
            if (this.getCenterPos().x < brick.getPos().x || this.getCenterPos().x > brick.getPos().x + brick.getSize().x) {
                this.speed.set(new Vec2f(-this.speed.x, this.speed.y));
            } else {
                this.speed.set(new Vec2f(this.speed.x, -this.speed.y));
            }



            if (brick.getColor() == Color.BLACK) {
                brick.setColor(Color.BLUE);
                Breakout.setScore(Breakout.getScore() + 10);
            } else if (brick.getColor() == Color.BLUE) {
                brick.setColor(Color.GREEN);
                Breakout.setScore(Breakout.getScore() + 20);
            } else if (brick.getColor() == Color.GREEN) {
                brick.destroy(gui);
                Breakout.setScore(Breakout.getScore() + 50);
                return true;
            }

        }

        return false;
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public void register(Gui gui) {
        gui.addBall(this);
    }

    public void destroy(Gui gui) {
        gui.removeBall(this);
    }


    public void setPos(Vec2f pos) {
        this.pos = pos;
    }

    public Vec2f getPos() {
        return this.pos;
    }

    public void setSpeed(Vec2f speed) {
        this.speed = speed;
    }

    public Vec2f getSpeed() {
        return this.speed;
    }

    public void setDurchmesser(int durchmesser) {
        this.durchmesser = durchmesser;
    }

    public int getDurchmesser() {
        return this.durchmesser;
    }

    public Vec2f getCenterPos() {
        return new Vec2f(this.pos.x + (this.durchmesser / 2), this.pos.y + (this.durchmesser / 2));
    }

}
