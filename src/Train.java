import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: Lewis
 * Date: 24/09/13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class Train {

    private int progress;
    private Tile currentTile;
    private int curx, cury;
    private int speed = 1;

    public void update(GameContainer container, int delta) throws SlickException {
        if (!currentTile.getRoutes().get(currentTile.getTrainFrom())) {
            Trains.getInstance().setAlive(false);
        }
        currentTile.setProgress(currentTile.getProgress() + speed);
        if (currentTile.getProgress() == 50 && !currentTile.getRoutes().get(currentTile.getRouteDecision())) {
            Trains.getInstance().setAlive(false);
        }
        if(currentTile.getProgress() > 99) { // go to next tile
            //reset old tile's progress
            currentTile.setProgress(-1);
            Tile.Route route = currentTile.getRouteDecision();
            switch (route) {
                case DOWN: cury++; break;
                case TOP: cury--;  break;
                case RIGHT: curx++; break;
                case LEFT: curx--; break;
            }

            //make sure it's in bounds
            if(Trains.getInstance().getMap().coordsInBounds(curx, cury)) {
                // when they get to a new tile
                currentTile = Trains.getInstance().getMap().getTile(curx, cury);
                Tile.Route oppRoute = Tile.Route.TOP;
                switch (route) {
                    case DOWN: oppRoute = Tile.Route.TOP; break;
                    case TOP: oppRoute = Tile.Route.DOWN; break;
                    case RIGHT: oppRoute = Tile.Route.LEFT; break;
                    case LEFT: oppRoute = Tile.Route.RIGHT; break;
                }
                currentTile.trainArrives(oppRoute);
                currentTile.setRouteDecision(route);
            } else {
                Trains.getInstance().setAlive(false);
            }
        }
    }
    private Image sprite;

    public Train(Map map) {
            curx = 0;
            cury = 0;
            currentTile = map.getTile(curx, cury);
            currentTile.trainArrives(Tile.Route.TOP);
            currentTile.setRouteDecision(Tile.Route.RIGHT);
            currentTile.setProgress(51);
        try {
            sprite = new Image("src/Resources/Train.png");
        } catch(SlickException e) {
            e.printStackTrace();

        }
        }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (currentTile.getProgress() > 0) {
            if (currentTile.getProgress() < 50) {
                if (currentTile.getTrainFrom() == Tile.Route.TOP) {
                    sprite.setRotation(180);
                    sprite.draw(currentTile.getX()*100+35,currentTile.getY()*100+(progress-30));
                }
                else if (currentTile.getTrainFrom() == Tile.Route.RIGHT) {
                    sprite.setRotation(270);
                    sprite.draw(currentTile.getX()*100+(100-progress),currentTile.getY()*100+35);
                }
                else if (currentTile.getTrainFrom() == Tile.Route.DOWN) {
                    sprite.setRotation(0);
                    sprite.draw(currentTile.getX()*100+35,currentTile.getY()*100+(100-progress));
                }
                else if (currentTile.getTrainFrom() == Tile.Route.LEFT) {
                    sprite.setRotation(90);
                    sprite.draw(currentTile.getX()*100+(progress-30),currentTile.getY()*100+35);
                }
            }
            else if (currentTile.getProgress() <= 99) {
                if (currentTile.getRouteDecision() == Tile.Route.LEFT) {
                    sprite.setRotation(270);
                    sprite.draw(currentTile.getX()*100+(100-progress),currentTile.getY()*100+35);
                }
                else if (currentTile.getRouteDecision() == Tile.Route.DOWN) {
                    sprite.setRotation(180);
                    sprite.draw(currentTile.getX()*100+35,currentTile.getY()*100+(progress-30));
                }
                else if (currentTile.getRouteDecision() == Tile.Route.RIGHT) {
                    sprite.setRotation(90);
                    sprite.draw(currentTile.getX()*100+(progress-30),currentTile.getY()*100+35);
                }
                else if (currentTile.getRouteDecision() == Tile.Route.TOP) {
                    sprite.setRotation(0);
                    sprite.draw(currentTile.getX()*100+35,currentTile.getY()*100+(100-progress));

                }
            }
        }
    }

    public Tile getCurrentTile() {return currentTile;}

}
