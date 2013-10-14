import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Rikki
 * Date: 22/09/13
 * Time: 01:09
 */
public class Tile {

    private Image base;
    private Image track;
    private Image train;
    private int x;
    private int y;

    public Tile(int x, int y) {
        this(x, y, Trains.balancer(100), Trains.balancer(100), Trains.balancer(100), Trains.balancer(100));
    }


    public Tile(int x, int y, boolean top, boolean right, boolean down, boolean left) {
        this.x = x;
        this.y = y;
        routes.put(Route.TOP, top);
        routes.put(Route.RIGHT, right);
        routes.put(Route.DOWN, down);
        routes.put(Route.LEFT, left);
        try {
            base = new Image("src/Resources/Base.png");
            track = new Image("src/Resources/Track.png");
            train = new Image("src/Resources/Train.png");
            routeDecision = Route.DOWN;
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    enum Route { TOP, RIGHT, DOWN, LEFT }

    private HashMap<Route, Boolean> routes = new HashMap<Route, Boolean>();
    private Route trainFrom;
    private Route routeDecision;
    private int progress = -1; //if progress is negative, the train hasn't arrived yet; otherwise a percentage

    public void setRouteDecision(Route decision) {
        if(progress >= 50) return;
        if((decision != trainFrom)) {
            this.routeDecision = decision;
        }
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public Route getRouteDecision() {return this.routeDecision;}

    public void trainArrives(Route from) {
        this.progress = 0;
        this.trainFrom = from;
    }

    public Route getTrainFrom() {return trainFrom;}

    public HashMap<Route, Boolean> getRoutes() {return routes;}

    public int getProgress() {
        if(progress < 0) return 0;
        else if(progress > 100) return 100;
        else return progress;
    }

    public void setProgress(int newProgress) {
        if(newProgress > this.getProgress() || newProgress == -1)
            this.progress = newProgress;
    }

    private boolean canRotate() { return progress<0; }

    public void rotateRight() {
        if(this.canRotate()) {
            Boolean newTop = routes.get(Route.LEFT);
            routes.put(Route.LEFT, routes.get(Route.DOWN));
            routes.put(Route.DOWN, routes.get(Route.RIGHT));
            routes.put(Route.RIGHT, routes.get(Route.TOP));
            routes.put(Route.TOP, newTop);
        }
    }

    public void rotateLeft() {
        if(this.canRotate()) {
            Boolean newTop = routes.get(Route.RIGHT);
            routes.put(Route.RIGHT, routes.get(Route.DOWN));
            routes.put(Route.DOWN, routes.get(Route.LEFT));
            routes.put(Route.LEFT, routes.get(Route.TOP));
            routes.put(Route.TOP, newTop);
        }
    }

    public void render(GameContainer container, Graphics g, int x, int y) throws SlickException {
        if(base != null) base.draw(x*100,y*100);
        if (routes.get(Route.TOP)) {
            track.setRotation(0);
            track.draw(x*100+45,y*100);
        }
        if (routes.get(Route.RIGHT)) {
            track.setRotation(90);
            track.draw(x*100+72,y*100+27);
        }
        if (routes.get(Route.DOWN)) {
            track.setRotation(0);
            track.draw(x*100+45,y*100+54);
        }
        if (routes.get(Route.LEFT)) {
            track.setRotation(90);
            track.draw(x*100+18,y*100+27);
        }
        if (progress > 0) {
            if (progress < 50) {
                if (trainFrom == Route.TOP) {
                    train.setRotation(180);
                    train.draw(x*100+35,y*100+(progress-30));
                }
                else if (trainFrom == Route.RIGHT) {
                    train.setRotation(270);
                    train.draw(x*100+(100-progress),y*100+35);
                }
                else if (trainFrom == Route.DOWN) {
                    train.setRotation(0);
                    train.draw(x*100+35,y*100+(100-progress));
                }
                else if (trainFrom == Route.LEFT) {
                    train.setRotation(90);
                    train.draw(x*100+(progress-30),y*100+35);
                }
            }
            else if (progress <= 99) {
                if (routeDecision == Route.LEFT) {
                    train.setRotation(270);
                    train.draw(x*100+(100-progress),y*100+35);
                }
                else if (routeDecision == Route.DOWN) {
                    train.setRotation(180);
                    train.draw(x*100+35,y*100+(progress-30));
                }
                else if (routeDecision == Route.RIGHT) {
                    train.setRotation(90);
                    train.draw(x*100+(progress-30),y*100+35);
                }
                else if (routeDecision == Route.TOP) {
                    train.setRotation(0);
                    train.draw(x*100+35,y*100+(100-progress));

                }
            }
            //TODO new train class to stop train rendering under tiles

        }
    }

}
