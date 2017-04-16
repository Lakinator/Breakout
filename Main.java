import Game.Breakout;

/**
 * 14.04.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Main {
    public static void main(String[] args) {
        Breakout.setDebug(true);

        Breakout game = new Breakout();
        game.start();
    }
}
