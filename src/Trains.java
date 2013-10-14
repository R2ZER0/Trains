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
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(Trains.getInstance());
            appgc.setDisplayMode(600, 500, false);
            appgc.setTargetFrameRate(30);
            appgc.start();
        } catch(SlickException e) {
            Logger.getLogger(Trains.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Trains(String title) {
        super(title);
    }

    //Variables
    Map map;
    Train train;
    GUI gui;
    Boolean alive;
    private static Trains instance;

    public static Trains getInstance() {
        if (instance == null) instance = new Trains("Trains");
        return instance;
    }

    public Map getMap() {return map;}

    @Override
    public void init(GameContainer container) throws SlickException {
        map = new Map(5);
        train = new Train(map);
        gui = new GUI(map);
        alive = true;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        train.update(container, delta);
        Input input = container.getInput();
        if (train.getCurrentTile().getProgress() < 50) {
            if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
                train.getCurrentTile().setRouteDecision(Tile.Route.TOP);
                gui.setDirection(1);
            }
            else if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
                train.getCurrentTile().setRouteDecision(Tile.Route.RIGHT);
                gui.setDirection(2);
            }
            else if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
                train.getCurrentTile().setRouteDecision(Tile.Route.DOWN);
                gui.setDirection(3);
            }
            else if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
                train.getCurrentTile().setRouteDecision(Tile.Route.LEFT);
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

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if (alive) {
            map.render(container, g);
            train.render(container, g);
            gui.render(container, g);
        }
        else {
            g.drawString("What a loser", 200, 240);
            g.setColor(Color.red);
            g.drawRect(380, 430, 200, 50);
            g.drawString("Try Again Pussy", 400, 445);
        }
    }


    private static Random random = new Random();

    public static boolean balancer(int val) {
        return !(random.nextInt(val) > 60);
    }

    public void setAlive(boolean b) { alive = b;}

}
