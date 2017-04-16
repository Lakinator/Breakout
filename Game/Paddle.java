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

    public void update() {
        if (KeyHandler.isLeftDown()) pos.x -= speedX;
        if (KeyHandler.isRightDown()) pos.x += speedX;
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

            ball.setPos(new Vec2f(ball.getPos().x, this.pos.y - ball.getDurchmesser() + 2));

            //6 Sektoren
            if (ball.getCenterPos().x > this.pos.x && ball.getCenterPos().x < this.pos.x + (this.size.x*(1.0/6.0))) { //1 Segment Links

                System.out.println("1");
                if (ball.getSpeed().y*0.5 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, (float) -(ball.getSpeed().y*0.5)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, -ball.getSpeed().y));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(1.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(2.0/6.0))) { //2 Segment Links

                System.out.println("2");
                if (ball.getSpeed().y*0.7 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, (float) -(ball.getSpeed().y*0.7)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, -ball.getSpeed().y));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(2.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(3.0/6.0))) { //3 Segment Links

                System.out.println("3");
                if (ball.getSpeed().y*0.9 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, (float) -(ball.getSpeed().y*0.9)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? ball.getSpeed().x : -ball.getSpeed().x, -ball.getSpeed().y));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(3.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(4.0/6.0))) { //3 Segment Rechts

                System.out.println("4");
                if (ball.getSpeed().y*0.9 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, (float) -(ball.getSpeed().y*0.9)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, -ball.getSpeed().y));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(4.0/6.0)) && ball.getCenterPos().x < this.pos.x + (this.size.x*(5.0/6.0))) { //2 Segment Rechts

                System.out.println("5");
                if (ball.getSpeed().y*0.7 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, (float) -(ball.getSpeed().y*0.7)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, -ball.getSpeed().y));

            } else if (ball.getCenterPos().x > this.pos.x + (this.size.x*(5.0/6.0)) && ball.getCenterPos().x < this.pos.x + this.size.x) { //1 Segment Rechts

                System.out.println("6");
                if (ball.getSpeed().y*0.5 >= 2.0) ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, (float) -(ball.getSpeed().y*0.5)));
                else ball.setSpeed(new Vec2f(ball.getSpeed().x < 0 ? -ball.getSpeed().x : ball.getSpeed().x, -ball.getSpeed().y));

            }

        }

    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}



