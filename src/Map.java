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
    }

    private Tile[] tiles;
    private final int sideSize;

    public Tile getTile(int x, int y) throws Exception {
        return this.tiles[y* sideSize + x];
    }
}
