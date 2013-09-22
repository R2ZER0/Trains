import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: Rikki
 * Date: 22/09/13
 * Time: 01:06
 */
public class Trains extends BasicGame {

    public static void main(String[] args) {
        // make a new game thing!
        Trains trains = new Trains("Trains"); // Trains!
    }

    Map map = new Map(5);

    public Trains(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
