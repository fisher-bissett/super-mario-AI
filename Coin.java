import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Coin extends Sprite
{
    static Image coin_image = null;
    double coin_vVel;
    double coin_hVel;
    Random rand = new Random();

    // constructor
    Coin(Model m, int xx, int yy)
    {
        super(m);
        // lazy loading
		if(coin_image == null)
		{
			model = m;
			model.sprites.add(this);

			try
			{
				coin_image = ImageIO.read(new File("coin.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
        x = xx;
        y = yy - 50;
        w = 75;
        h = 75;
        coin_vVel = -15.2;
        coin_hVel = rand.nextDouble() * 16 - 8;
    }

    // copy constructor
    Coin(Coin copy, Model newmodel)
    {
        super(copy, newmodel);
        w = copy.w;
        h = copy.h;
        coin_vVel = copy.coin_vVel;
        coin_hVel = copy.coin_hVel;
    }

    Coin cloneme(Model newmodel)
    {
        return new Coin(this, newmodel);
    }

    boolean amIaBrick()
    {
        return false;
    }

    boolean amIaMario()
    {
        return false;
    }

    void draw(Graphics g)
    {
        g.drawImage(coin_image, x - model.scrollPos, y, null);
    }

    void update()
    {
            // coin falling vertical
            y += coin_vVel;
            coin_vVel += 2.0;

            // coin falling horizontal
            x += coin_hVel;
            coin_hVel -= 0.2;

            if(y > 700)
            {
                model.sprites.remove(this);
            }
    }
}
