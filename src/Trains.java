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
            appgc.setDisplayMode(600, 500, false);
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
            Tile.Route route = currentTile.getRouteDecision();
            switch (route) {
                case DOWN: cury++;
                case TOP: cury--;
                case RIGHT: curx++;
                case LEFT: curx--;
            }

            //make sure it's in bounds
            if(map.coordsInBounds(curx, cury)) {
                // when they get to a new tile
                currentTile = map.getTile(curx, cury);
                Tile.Route oppRoute = Tile.Route.TOP;
                switch (route) {
                    case DOWN: oppRoute = Tile.Route.TOP; break;
                    case TOP: oppRoute = Tile.Route.DOWN; break;
                    case RIGHT: oppRoute = Tile.Route.LEFT; break;
                    case LEFT: oppRoute = Tile.Route.RIGHT; break;
                }
                currentTile.trainArrives(oppRoute);
                currentTile.setRouteDecision(oppRoute);
            } else {
                // TODO - when the train goes off the edge, and they lose (or win maybe... what even is the objective?)
            }
            Input input = container.getInput();
            if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
                currentTile.setRouteDecision(Tile.Route.TOP);
                gui.setDirection(1);
            }
            else if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
                currentTile.setRouteDecision(Tile.Route.RIGHT);
                gui.setDirection(2);
            }
            else if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
                currentTile.setRouteDecision(Tile.Route.DOWN);
                gui.setDirection(3);
            }
            else if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
                currentTile.setRouteDecision(Tile.Route.LEFT);
                gui.setDirection(4);
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        map.render(container, g);
        gui.render(container, g);
    }
}
