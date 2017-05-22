package Game;

import com.sun.javafx.geom.Vec2f;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Paddle {
    private Vec2f pos;
    private int speedX;
    private Vec2f size;

    public Paddle(int frameWidth, int frameHeight, int speed) {
        this.pos = new Vec2f();
        this.speedX = speed;
        this.size = new Vec2f();
        resizePaddle(frameWidth, frameHeight);
        centerPaddlePos(frameWidth, frameHeight);
    }

    public void register(Gui gui) {
        gui.addPaddle(this);
    }

    public void destroy(Gui gui) {
        gui.removePaddle(this);
    }



    public void setPos(Vec2f pos) {
        this.pos = pos;
    }

    public Vec2f getPos() {
        return this.pos;
    }

    public void setSpeed(int speed) {
        this.speedX = speed;
    }

    public void setSize(Vec2f size) {
        this.size = size;
    }

    public Vec2f getSize() {
        return this.size;
    }

    public Vec2f getCenterPos() {
        return new Vec2f(this.pos.x + (this.size.x/2), this.pos.y + (this.size.y/2));
    }

    public void centerPaddlePos(int frameWidth, int frameHeight) {
        pos.set( (frameWidth/2) - (size.x/2) , (frameHeight/2) + (frameHeight/3) );
    }

    public void resizePaddle(int frameWidth, int frameHeight) {
        size.set(frameWidth/4, frameHeight/18);
    }

    public void update(float timeSinceLastFrame) {
        if (KeyHandler.isLeftDown()) pos.x -= speedX * timeSinceLastFrame;
        if (KeyHandler.isRightDown()) pos.x += speedX * timeSinceLastFrame;
    }

    public void handleCollision(int frameWidth, int frameHeight, Ball ball) {

        //In Bounds of Frame
        if (pos.x < 0) {
            pos.x = 0;
        }

        if (pos.x + size.x > frameWidth) {
            pos.x = frameWidth - size.x;
        }

        if (pos.y < 0) {
            pos.y = 0;
        }

        if (pos.y + size.y > frameHeight) {
            pos.y = frameHeight - size.y;
        }

        //Ball - Paddle Collision
        int radius = ball.getDurchmesser()/2;

        // Find the closest point to the circle within the rectangle
        float closestX = clamp(ball.getCenterPos().x, this.pos.x, this.pos.x + this.size.x);
        float closestY = clamp(ball.getCenterPos().y, this.pos.y, this.pos.y + this.size.y);

        if (Breakout.getDebug()) Breakout.debugLine.setLine(closestX, closestY, ball.getCenterPos().x, ball.getCenterPos().y);

        // Calculate the distance between the circle's center and this closest point
        float distanceX = Math.abs(ball.getCenterPos().x - closestX);
        float distanceY = Math.abs(ball.getCenterPos().y - closestY);

        // If the distance is less than the circle's radius, an intersection occurs
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        if (distanceSquared < (radius * radius)) {

            //TODO: Verschiedene Abprallwinkel von jedem Segment

            float speedX = 0.5f;

            ball.setPos(new Vec2f(ball.getPos().x, this.pos.y - ball.getDurchmesser() + 2));

            //6 Sektoren
            if (ball.getCenterPos().x > this.pos.x && ball.getCenterPos().x < this.pos.x + (this.size.x*(1.0/6.0))) { //1 Segment Links

                System.out.println("Hit Segment 1");
                ball.setSpeed(new Vec2f(-Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(1.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(2.0/6.0))) { //2 Segment Links

                System.out.println("Hit Segment 2");
                ball.setSpeed(new Vec2f(-Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(2.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(3.0/6.0))) { //3 Segment Links

                System.out.println("Hit Segment 3");
                ball.setSpeed(new Vec2f(-Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(3.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(4.0/6.0))) { //3 Segment Rechts

                System.out.println("Hit Segment 4");
                ball.setSpeed(new Vec2f(Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(4.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(5.0/6.0))) { //2 Segment Rechts

                System.out.println("Hit Segment 5");
                ball.setSpeed(new Vec2f(Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(5.0/6.0)) && ball.getCenterPos().x < this.pos.x + this.size.x) { //1 Segment Rechts

                System.out.println("Hit Segment 6");
                ball.setSpeed(new Vec2f(Math.abs(ball.getSpeed().x), -Math.abs(ball.getSpeed().y)));

            }

        }

    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}



