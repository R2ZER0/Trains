import org.newdawn.slick.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Rikki
 * Date: 22/09/13
 * Time: 01:06
 */
public class Trains extends BasicGame {

    public static void main(String[] args) {
        // make a new game thing!
        ///Trains trains = ; // Trains!
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new Trains("Simple Slick Game"));
            appgc.setDisplayMode(640, 480, false);
            appgc.start();
        } catch(SlickException e) {
            Logger.getLogger(Trains.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    Map map;
    GUI gui;

    public Trains(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        map = new Map(5);
        gui = new GUI(map);
        curx = 0;
        cury = 0;
        currentTile = map.getTile(curx, cury);
    }

    private Tile currentTile;
    private int curx, cury;
    private int speed = 5;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        currentTile.setProgress(currentTile.getProgress() + speed);
        if(currentTile.getProgress() == 100) { // go to next tile
            Tile.Route route = if()
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        map.render(container, g);
        gui.render(container, g);
    }
}
