package model;

import model.elementList.EnumWord;
import view.Sprite;

/**
 * Class that represent a element block
 * @author BARBE Romain
 * @author ROBERT Eric
 * @version 1
 */
abstract sealed class AbstractBlock implements Block permits WordBlock, ElementBlock{
	private final EnumWord name;
	private Sprite image;
	
	/**
	 * Block constructor
	 * @param name name of the block
	 */
	public AbstractBlock(EnumWord name) {
		this.name = name;
		
		if (this.getClass() == ElementBlock.class) {
			this.image = new Sprite(this.name, "E-");
		}
		else {
			this.image = new Sprite(this.name, "W-");
		}
	}
	
	/**
	 * Return the name of the block
	 * @return the name of the block
	 */
	public EnumWord getName() {
		return this.name;
	}
	
	/**
	 * Return the image attached to the block
	 * @return Sprite of the block
	 */
	public Sprite getImage() {
		return this.image;
	}
	
	/**
	 * Return a string representation of the block
	 * @return a String with the name of the block
	 */
	@Override
	public String toString() {
		return this.name.toString();
	}
}
