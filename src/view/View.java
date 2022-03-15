package view;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen5.ApplicationContext;
import model.Block;
import model.Cell;

/**
 * Class used to render our game
 * @author BARBE Romain
 * @author ROBERT Eric
 */
public class View {
	
	/**
	 * Draw the game's grid
	 * @param context The ApplicationContext in which the game will be printed
	 * @param sizeGridX Number of lines of the grid
	 * @param sizeGridY Number of column of the grid
	 * @param grid Game's grid
	 */
	public static void draw(ApplicationContext context, int sizeGridX, int sizeGridY, Cell[][] grid) {
		float width = context.getScreenInfo().getWidth();
		float height = context.getScreenInfo().getHeight();

		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					int minY = (int) (j * (width / sizeGridY));
					int maxY = (int) (width / sizeGridY);
					int minX = (int) (i * (height / sizeGridX));
					int maxX = (int) (height / sizeGridX);

					Block block;
					if (grid[i][j].isEmpty()) {
						block = null;
					} else {
						for (int k = 0; k < grid[i][j].size(); k++) {
							block = grid[i][j].getBlock(k);
							block.getImage().draw(graphics, minY, minX, maxY, maxX);
						}
					}
				}
			}
		});
	}
}
