import java.awt.*;

public class Brick extends Sprite
{
	// constructor
	Brick(Model m, int xx, int yy, int ww, int hh)
	{
		super(m);
		x = xx;
		y = yy;
		w = ww;
		h = hh;
	}

	// copy constructor
	Brick(Brick copy, Model newmodel)
	{
		super(copy, newmodel);
		model = newmodel;
	}

    Brick(Json ob, Model m)
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

	Brick cloneme(Model newmodel)
	{
		return new Brick(this, newmodel);
	}

	boolean amIaBrick()
	{
		return true;
	}

	boolean amIaMario()
	{
		return false;
	}


	void draw(Graphics g)
	{
		// draw bricks
		g.setColor(new Color(191, 61, 63));
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite b = model.sprites.get(i);
			if(amIaBrick() == true)
				g.fillRect(x - model.scrollPos, y, w, h);
		}
	}

	void update()
	{

	}
}
