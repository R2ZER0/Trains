import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Rikki
 * Date: 22/09/13
 * Time: 01:09
 */
public class Tile {

    public Tile() {
        //TODO generate random valid tile
        this(true, false, true, false);
    }

    public Tile(boolean top, boolean right, boolean down, boolean left) {
        routes.put(Route.TOP, top);
        routes.put(Route.RIGHT, right);
        routes.put(Route.DOWN, down);
        routes.put(Route.LEFT, left);
    }

    enum Route { TOP, RIGHT, DOWN, LEFT }

    private HashMap<Route, Boolean> routes;
    private Route trainFrom;
    private Route routeDecision;
    private int progress = -1; //if progress is negative, the train hasn't arrived yet; otherwise a percentage

    public void trainArrives(Route from) {
        this.progress = 0;
        this.trainFrom = from;
    }

    public int getProgress() {
        if(progress < 0) return 0;
        else if(progress > 100) return 100;
        else return progress;
    }

    public void setProgress(int newProgress) {
        if(newProgress > this.getProgress())
            this.progress = newProgress;
    }

    private boolean canRotate() { return (progress < 0); }

    public void rotate() {
        if(this.canRotate()) {
            Boolean newTop = routes.get(Route.LEFT);
            routes.put(Route.LEFT, routes.get(Route.DOWN));
            routes.put(Route.DOWN, routes.get(Route.RIGHT));
            routes.put(Route.RIGHT, routes.get(Route.TOP));
            routes.put(Route.TOP, newTop);
        }
    }

    // TODO a public render method

}
