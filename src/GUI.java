import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Lewis
 * Date: 22/09/13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */


public class GUI {

    private int direction;
    Image[] arrow;
    int size;

    public GUI (Map map) {
        size = map.GetSize();
        try {
            direction = 0;
            arrow = new Image[5];
            arrow[0] = new Image("src/Resources/D0_Centre.png");
            arrow[1] = new Image("src/Resources/D1_Up.png");
            arrow[2] = new Image("src/Resources/D2_Right.png");
            arrow[3] = new Image("src/Resources/D3_Down.png");
            arrow[4] = new Image("src/Resources/D4_Left.png");
        } catch(SlickException e) {
            // do nothing
        }

        startTime = new Date().getTime();
    }

    //set to 0 after changing tile, set when press direction
    public void setDirection(int d){
        direction = d;
    }

    long startTime;

    public void render(GameContainer container, Graphics g) throws SlickException {
        arrow[direction].draw((size)*100, (size-1)*100);

        long timeDiff = (new Date().getTime() - startTime) / 1000;
        g.drawString("" + Long.toString(timeDiff) + "s", 550, 0);
    }

}
