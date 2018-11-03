import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class Mario extends Sprite
{
	// member variables
	static Image[] mario_images = null; // static makes lazy loading
	int prev_x; //where mario was x
	int prev_y; //where mario was y
	double vVel;
	int frames_since_ground;

	// constructor
	Mario(Model m)
	{
		super(m);
		w = 60; // width of mario
		h = 95; // height of mario
		frames_since_ground = 0;
		prev_x = 0;
		prev_y = 0;
		vVel = 0;

		// lazy loading
		if(mario_images == null)
		{
			mario_images = new Image[5];
			try
			{
				mario_images[0] = ImageIO.read(new File("mario1.png"));
				mario_images[1] = ImageIO.read(new File("mario2.png"));
				mario_images[2] = ImageIO.read(new File("mario3.png"));
				mario_images[3] = ImageIO.read(new File("mario4.png"));
				mario_images[4] = ImageIO.read(new File("mario5.png"));
			}
			catch(Exception e)
			{
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	// copy constructor
	Mario(Mario copy, Model newmodel)
	{
		super(copy, newmodel);
		model = newmodel;
		w = copy.w;
		h = copy.h;
		frames_since_ground = copy.frames_since_ground;
		prev_x = copy.prev_x;
		prev_y = copy.prev_y;
		vVel = copy.vVel;
	}

	Mario cloneme(Model newmodel)
	{
		return new Mario(this, newmodel);
	}

	// remember where mario was
	void marioWas()
	{
		prev_x = x;
		prev_y = y;
	}

	// check to see if something is a brick
	boolean amIaBrick()
	{
		return false;
	}

	boolean amIaMario()
	{
		return true;
	}

	// push mario back out of object
	void getOut(Sprite s)
	{
			if((x + w > s.x) && (prev_x + w <= s.x)) // if i am came in from the left
			{
				x = s.x - w;
			}
			else if((x < s.x + s.w) && (prev_x >= s.x + s.w)) // if i am came in from the right
			{
				x = s.x + s.w;
			}
			else if((y + h >= s.y) && (prev_y + h <= s.y)) // if i am came in from above
			{
				y = s.y - h;
				vVel = 0.0;
				frames_since_ground = 0;
			}
			else if((y < s.y + s.h) && (prev_y >=s.y + s.h)) // if i am came in from below
			{
				y = s.y + s.h;
				vVel = 0.0;
			}
	}

	void draw(Graphics g)
	{
		// draw mario and cycle frames
		int marioFrame = (Math.abs(x) / 20) % 5;
		g.drawImage(mario_images[marioFrame], x - model.scrollPos, y, null);
	}

	void update()
	{
		// moves mario and the scrolling position
		model.scrollPos = x - 250;

		// set vertical velocity
		vVel += 3.14;
		y += vVel;

		// stop mario at ground
		if(y + h > 550)
		{
			y = 550 - h;
			frames_since_ground = 0;
		}

		// am I colliding?
		Iterator<Sprite> it = model.sprites.iterator(); // use of iterator
		while(it.hasNext())
		{
			Sprite s = it.next();

			if(s.amIaBrick())
			{
				if(doesCollide(this, s))
				{
					getOut(s);
				}
			}
		}
		frames_since_ground++;
	}
}
