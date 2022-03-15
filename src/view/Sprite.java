package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import model.elementList.EnumWord;

/**
 * Sprite (image) of the block
 * @author BARBE Romain
 * @author ROBERT Eric
 */
public class Sprite {
	private BufferedImage image;
	
	/**
	 * Sprite constructor
	 * @param word Word of the block
	 * @param type Element(E) or Word(W) type of the block
	 */
	public Sprite(EnumWord word, String type) {
		try {
			this.image = ImageIO.read(Objects.requireNonNull(Sprite.class.getResourceAsStream("/" + type + word + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw a Sprite
	 * @param graphics Used to draw
	 * @param minY Top-left coordinates in y
	 * @param minX Top-left coordinates in x
	 * @param maxY Size y
	 * @param maxX size x
	 */
	public void draw(Graphics2D graphics, int minY, int minX, int maxY, int maxX) {
		graphics.drawImage(this.image, minY, minX, maxY, maxX, null);
	}
}
