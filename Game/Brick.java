package Game;

import com.sun.javafx.geom.Vec2f;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Brick {
    private Vec2f pos;
    private Vec2f size;

    public Brick(int frameWidth, int frameHeight, Vec2f pos) {
        this.pos = new Vec2f(pos);
        this.size = new Vec2f();
        resizeBrick(frameWidth, frameHeight);
    }

    public void register(Gui gui) {
        gui.addBrick(this);
    }

    public void destroy(Gui gui) {
        gui.removeBrick(this);
    }



    public void setPos(Vec2f pos) {
        this.pos = pos;
    }

    public Vec2f getPos() {
        return this.pos;
    }

    public void setSize(Vec2f size) {
        this.size = size;
    }

    public Vec2f getSize() {
        return this.size;
    }

    public void resizeBrick(int frameWidth, int frameHeight) {
        size.set(frameWidth/10, frameHeight/28);
    }

}
