import org.newdawn.slick.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

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
            appgc = new AppGameContainer(new Trains("Trains"));
            appgc.setDisplayMode(600, 500, false);
            appgc.setTargetFrameRate(30);
            appgc.start();
        } catch(SlickException e) {
            Logger.getLogger(Trains.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    Map map;
    GUI gui;
    Boolean alive;

    public Trains(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        map = new Map(5);
        gui = new GUI(map);
        alive = true;
        curx = 0;
        cury = 0;
        currentTile = map.getTile(curx, cury);
        currentTile.trainArrives(Tile.Route.UP);
        currentTile.setRouteDecision(Tile.Route.RIGHT);
        currentTile.setProgress(51);
    }

    private Tile currentTile;
    private int curx, cury;
    private int speed = 1;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (!currentTile.getRoutes().get(currentTile.getTrainFrom())) {
            alive = false;
        }
        currentTile.setProgress(currentTile.getProgress() + speed);
        if (currentTile.getProgress() == 50 && !currentTile.getRoutes().get(currentTile.getRouteDecision())) {
            alive = false;
        }
        if(currentTile.getProgress() > 99) { // go to next tile
            //reset old tile's progress
            currentTile.setProgress(-1);
            Tile.Route route = currentTile.getRouteDecision();
            switch (route) {
                case DOWN: cury++; break;
                case UP: cury--;  break;
                case RIGHT: curx++; break;
                case LEFT: curx--; break;
            }

            //make sure it's in bounds
            if(map.coordsInBounds(curx, cury)) {
                // when they get to a new tile
                currentTile = map.getTile(curx, cury);
                Tile.Route oppRoute = Tile.Route.UP;
                switch (route) {
                    case DOWN: oppRoute = Tile.Route.UP; break;
                    case UP: oppRoute = Tile.Route.DOWN; break;
                    case RIGHT: oppRoute = Tile.Route.LEFT; break;
                    case LEFT: oppRoute = Tile.Route.RIGHT; break;
                }
                currentTile.trainArrives(oppRoute);
                currentTile.setRouteDecision(route);
            } else {
                alive = false;
            }
        }
        Input input = container.getInput();
        if (currentTile.getProgress() < 50) {
            if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
                currentTile.setRouteDecision(Tile.Route.UP);
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
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            //if player is dead (restart button)
            if (!alive && input.getMouseX() > 380 && input.getMouseX() < 580 && input.getMouseY() > 430 && input.getMouseY() < 480) {
                container.reinit();
            }
            //if player is alive (spinning)
            else if (input.getMouseX() < 500 && input.getMouseY() < 500) {
                map.getTile(input.getMouseX()/100, input.getMouseY()/100).rotateLeft();
            }

        }
        else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && input.getMouseX() < 500 && input.getMouseY() < 500) {
            map.getTile(input.getMouseX()/100, input.getMouseY()/100).rotateRight();
        }
    }

    private static Random random = new Random();

    public static boolean balancer(int val) {
        return !(random.nextInt(val) > 60);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if (alive) {
            map.render(container, g);
            gui.render(container, g);
        }
        else {
            g.drawString("What a loser", 200, 240);
            g.setColor(Color.red);
            g.drawRect(380, 430, 200, 50);
            g.drawString("Try Again Pussy", 400, 445);
        }
    }
}
