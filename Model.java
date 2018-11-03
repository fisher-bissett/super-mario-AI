import java.util.ArrayList;

class Model
{
		// member variables
		int scrollPos;
		ArrayList<Sprite> sprites;
		Mario mario;
		int numCoins;
		int jumpCount;

		// model constructor
		Model()
		{
			// create new objects
			sprites = new ArrayList<Sprite>();
			mario = new Mario(this);
			sprites.add(mario);

			// initialize member variables
			scrollPos = 0;
			numCoins = 0;
			jumpCount = 0;
		}

		// copy constructor
		Model(Model copy)
		{
			scrollPos = copy.scrollPos;
			numCoins = copy.numCoins;
			jumpCount = copy.jumpCount;
			sprites = new ArrayList<Sprite>();
			for(int i = 0; i < copy.sprites.size(); i++)
			{
				Sprite othercopy = copy.sprites.get(i);
				Sprite clone = othercopy.cloneme(this);
				sprites.add(clone);
				if(clone.amIaMario())
					mario = (Mario)clone;
			}
		}

		// unmarshalls json
		void unmarshall(Json ob)
		{
			sprites.clear();
			sprites.add(mario);

			Coinbox c1 = new Coinbox(this, 900, 285, 89, 83);
			Coinbox c2 = new Coinbox(this, 2800, 285, 89, 83);

			sprites.add(c1);
			sprites.add(c2);

			// sprites.add(coinbox);
			Json json_bricks = ob.get("bricks");
			for(int i = 0; i < json_bricks.size(); i++)
			{
				Json j = json_bricks.get(i);
				Brick b = new Brick(j, this);
				sprites.add(b);
			}
		}

		// marshalls json
		Json marshall()
		{
			Json ob = Json.newObject();
			Json json_bricks = Json.newList();
			ob.add("bricks", json_bricks);
			for(int i = 0; i < sprites.size(); i++)
			{
				Sprite s = sprites.get(i);
				if(s.amIaBrick())
				{
					Brick b = new Brick(this, s.x, s.y, s.w, s.h);
					Json j = b.marshall();
					json_bricks.add(j);
				}
			}
			return ob;
		}

		// save map
		void save(String filename)
		{
			Json ob = marshall();
			ob.save(filename);
			System.out.println("saved!");
		}

		// load map
		void load(String filename)
		{
			Json ob = Json.load(filename);
			unmarshall(ob);
			System.out.println("loaded!");
		}

		// adds bricks
		void addBrick(int x, int y, int w, int h)
		{
			Brick b = new Brick(this, x, y, w, h);
			sprites.add(b);
		}

		// adds coinbox
		void addCoinbox(int x, int y, int w, int h)
		{
			Coinbox c = new Coinbox(this, x, y, w, h);
			sprites.add(c);
		}

		void left()
		{
			mario.x += -10;
		}

		void right()
		{
			mario.x += 10;
		}

		void space()
		{
			if(mario.frames_since_ground < 5)
			{
				mario.vVel =  -35.0;
			}
		}

		void do_action(Action i)
		{
			if(i == Action.run)
				this.mario.x += 10;
			else if(i == Action.jump && mario.frames_since_ground < 5)
			{
				this.mario.vVel =  -35.0;
			}
			else if(i == Action.jump_and_run)
			{
				this.mario.vVel =  -35.0;
				this.mario.x += 10;
			}
		}

		double evaluateAction(Action action, int depth)
		{
			int d = 33;
			int k = 10;

			// Evaluate the state
			if(depth >= d)
				return mario.x + 5000 * numCoins - 2 * jumpCount;

			// Simulate the action
			Model copy = new Model(this); // uses the copy constructor
			copy.do_action(action); // like what Controller.update did before
			copy.update(); // advance simulated time

			// Recurse
			if(depth % k != 0)
			   return copy.evaluateAction(action, depth + 1);
			else
			{
			   double best = copy.evaluateAction(Action.run, depth + 1);
			   best = Math.max(best, copy.evaluateAction(Action.jump, depth + 1));
			   best = Math.max(best, copy.evaluateAction(Action.jump_and_run, depth + 1));
			   return best;
			}
		}

		public void update()
		{
			for(int i = 0; i < sprites.size(); i++)
			{
				Sprite s = sprites.get(i);
				s.update();
			}
		}
}
