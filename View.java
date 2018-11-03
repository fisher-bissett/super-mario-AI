import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
		Model model;
		Controller controller;
		static Image background_image = null;

		View(Controller c, Model m)
		{
				model = m;
				controller = c;
				try
				{
					background_image = ImageIO.read(new File("backgroundfinal.png"));
				}
		        catch(Exception e)
				{
					e.printStackTrace(System.err);
					System.exit(1);
				}
		}

		public void paintComponent(Graphics g)
		{

			// sets view window and clears
			g.setColor(new Color(128, 255, 255));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			// scrolling background
			int k = 0;
			for(int i = 0; i < 25; i++)
			{
				g.drawImage(background_image, model.scrollPos / -3 - 85 + k, 0, null);
				k += 1507;
			}
			// draws the ground
			g.setColor(new Color(143, 87, 9));
			g.fillRect(0, 550, 1050, 100);
			g.setColor(new Color(200, 87, 9));
			g.fillRect(0, 550, 1050, 3);

			for(int i = 0; i < model.sprites.size(); i++)
			{
				Sprite s = model.sprites.get(i);
				s.draw(g);
			}
		}
}
