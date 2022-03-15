package model;

import model.elementList.EnumCategory;
import model.elementList.EnumWord;

/**
 * Class that represent a word block
 * @author BARBE Romain
 * @author ROBERT Eric
 * @version 1
 */
public final class WordBlock extends AbstractBlock {
	private EnumCategory category;

	/**
	 * WordBlock constructor
	 * @param name Name of the block
	 * @param category Category of the block
	 */
	public WordBlock(EnumWord name, EnumCategory category) {
		super(name);
		this.category = category;
	}
	
	/**
	 * Send back the category of the word block
	 * @return the category of the word block
	 */
	public EnumCategory getCategory() {
		return this.category;
	}
	
	/**
	 * Return a string representation of the word block
	 * @return a String with the name of the word block
	 */
	@Override
	public String toString() {
		return super.toString() + this.category;
	}
}
