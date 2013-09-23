import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: Rikki
 * Date: 22/09/13
 * Time: 01:47
 */
public class Map {

    public Map() {
        this(5);
    }

    public Map(int sideSize) {
        this.sideSize = sideSize;
        this.tiles = new Tile[sideSize*sideSize];
        for (int i = 0; i < sideSize*sideSize; i++) {
            tiles[i] = new Tile();
        }
        tiles[0] = new Tile(true, true, true, true);
        tiles[1] = new Tile(true, true, true, true);
    }

    public boolean coordsInBounds(int x, int y) {
        return (x >= 0 && x < sideSize) && (y >= 0 && y < sideSize);
    }

    private Tile[] tiles;
    private final int sideSize;

    public int GetSize() {
        return sideSize;
    }

    public Tile getTile(int x, int y) {
        return this.tiles[y* sideSize + x];
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
         for(int y = 0; y < sideSize; ++y) {
             for(int x = 0; x < sideSize; ++x) {
                 this.getTile(x, y).render(container, g, x, y);
             }
         }
    }
}
