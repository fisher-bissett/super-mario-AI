import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

enum Action
{
	run,
	jump,
	jump_and_run
}

class Controller implements MouseListener, KeyListener
{
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;

	int mouseDownX;
	int mouseDownY;

	Controller(Model m)
	{
		model = m;
	}

	public void mousePressed(MouseEvent e)
	{
		mouseDownX = e.getX();
		mouseDownY = e.getY();
	}

	public void mouseReleased(MouseEvent e)
	{
		int x1 = mouseDownX;
		int x2 = e.getX();
		int y1 = mouseDownY;
		int y2 = e.getY();
		int left = Math.min(x1, x2);
		int right = Math.max(x1, x2);
		int top = Math.min(y1, y2);
		int bottom = Math.max(y1, y2);
		model.addBrick(left + model.scrollPos, top, right - left, bottom - top);

	}

	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }

	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		int w = 89;
		int h = 83;
		model.addCoinbox(x + model.scrollPos, y, w, h);
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
			case KeyEvent.VK_S: model.save("map.json"); break;
			case KeyEvent.VK_L: model.load("map.json"); break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void do_action(Action i)
	{
		if(i == Action.run)
			model.mario.x += 10;
		else if(i == Action.jump && model.mario.frames_since_ground < 5)
		{
			model.mario.vVel =  -35.0;
			model.jumpCount++;
		}
		else if(i == Action.jump_and_run)
		{
			model.mario.vVel =  -35.0;
			model.mario.x += 10;
		}
	}

	void update()
	{
		model.mario.marioWas();

		if(keyRight)
		{
			 model.right();
		}
		if(keyLeft)
		{
			 model.left();
		}
		if(keySpace)
		{
			model.space();
		}

		// Evaluate each possible action
		double score_run = model.evaluateAction(Action.run, 0);
		double score_jump = model.evaluateAction(Action.jump, 0);
		double score_jump_and_run = model.evaluateAction(Action.jump_and_run, 0);

		// Do the best one
		if(score_jump_and_run > score_jump && score_jump_and_run > score_run)
			do_action(Action.jump_and_run);
		else if(score_jump > score_run)
			do_action(Action.jump);
		else
			do_action(Action.run);
	}
}
