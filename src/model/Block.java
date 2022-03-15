package model;

import model.elementList.EnumWord;
import view.Sprite;

/**
 * Interface that give methods for the blocks
 * @author BARBE Romain
 * @author ROBERT Eric
 * @version 1
 */
public sealed interface Block permits AbstractBlock{
	
	/**
	 * Return the element of the block
	 * @return element of the block
	 */
	EnumWord getName();
	
	/**
	 * Return the image attached to the element
	 * @return Sprite of the element
	 */
	public Sprite getImage();
}
