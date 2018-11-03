import java.awt.*;

abstract class Sprite
{
    // member variables
    int x;
    int y;
    int w;
    int h;
    Graphics g;
    Model model;

    // constructor
    Sprite(Model m)
    {
        model = m;
    }

    // copy constructor
    Sprite(Sprite copy, Model newmodel)
    {
        x = copy.x;
        y = copy.y;
        w = copy.w;
        h = copy.h;
        model = newmodel;
    }

    // update
    abstract void update();

    // draw
    abstract void draw(Graphics g);

    // check to see if something is a brick
    abstract boolean amIaBrick();

    // check to see if something is a mario
    abstract boolean amIaMario();

    abstract Sprite cloneme(Model newmodel);

    // if 2 things collide
    static boolean doesCollide(Sprite a, Sprite b)
	{
		if(a.x + a.w <= b.x) // left
			return false;
		else if(a.x >= b.x + b.w) // right
			return false;
		else if(a.y + a.h <= b.y) // bottom
			return false;
		else if(a.y >= b.y + b.h) // top
			return false;
		else
			return true;
	}
}
