package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class KeyHandler implements KeyListener {
    private static boolean isLeft, isRight;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) isLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) isRight = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) isLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) isRight = false;
    }

    public static boolean isLeftDown() {
        return isLeft;
    }

    public static boolean isRightDown() {
        return isRight;
    }
}
