import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Coinbox extends Sprite
{
    static Image coinbox_image = null; // statis makes lazy loading
    static Image coinbox_image_empty = null; // statis makes lazy loading
    int hit_count;

    // constructor
    Coinbox(Model m, int xx, int yy, int ww, int hh)
    {
        super(m);
        x = xx;
        y = yy;
        w = ww;
        h = hh;

        // lazy loading
		if(coinbox_image == null && coinbox_image_empty == null)
		{
			try
			{
				coinbox_image = ImageIO.read(new File("block1.png"));
				coinbox_image_empty = ImageIO.read(new File("block2.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
        hit_count = 0;
    }

    // copy constructor
    Coinbox(Coinbox copy, Model newmodel)
    {
        super(copy, newmodel);
        model = newmodel;
        hit_count = copy.hit_count;
    }

    Coinbox(Json ob, Model m)
    {
        super(m);
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }

    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    Coinbox cloneme(Model newmodel)
    {
        return new Coinbox(this, newmodel);
    }

    boolean amIaBrick()
    {
        return false;
    }

    boolean amIaMario()
	{
		return false;
	}

    void hitit()
    {
        model.mario.getOut(this);

        if((this.y + this.h <= model.mario.y) && hit_count < 5 && model.mario.frames_since_ground < 5)
        {
            hit_count++;
            Coin coin = new Coin(model, x, y); // creates new coin object
            model.sprites.add(coin); // puts coin in the arraylist
            model.numCoins++;
        }
    }

    void draw(Graphics g)
    {
        if(hit_count >= 5)
            g.drawImage(coinbox_image_empty, x - model.scrollPos, y, null);
        else
            g.drawImage(coinbox_image, x - model.scrollPos, y, null);

    }

    void update()
    {
        if(doesCollide(this, model.mario))
        {
            hitit();
        }
    }
}
