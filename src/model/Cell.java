package model;

import java.util.ArrayList;

/**
 * A cell of the grid containing multiple element
 * @author BARBE Romain
 * @author ROBERT Eric
 */
public class Cell {
	private final ArrayList<Block> element;
	
	/**
	 * Cell constructor
	 */
	public Cell() {
		this.element = new ArrayList<>();
	}
	
	/**
	 * Add a block to the cell
	 * @param block Block to add
	 */
	public void addBlock(Block block) {
		element.add(block);
	}
	
	/**
	 * Remove a block from the cell
	 * @param index Index of the block to remove
	 * @return true if the block is deleted, false otherwise
	 */	
	public boolean removeBlockAt(int index) {
		if (index < this.element.size()) {
			element.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Verify if a block is inside this cell
	 * @param block Block to look at
	 * @return true if it's in the cell, false otherwise
	 */
	public boolean containBlock(Block block) {
		return element.contains(block);
	}

	/**
	 * Return the amount of element in this cell
	 * @return the amount of element
	 */
    public int size(){
        return element.size();
    }
	
    /**
     * Return a block at the specified position 
     * @param index Position
     * @return the element at the specified position in this cell
     */
	public Block getBlock(int index) {
		return element.get(index);
	}

	/**
	 * Returns a string representation of this cell
	 * @return a string representing of this cell
	 */
    @Override
    public String toString() {
        if (element.isEmpty()) {
            return "[]";
        } else {
            return element.toString();
        }
    }

    /**
     * Look if the cell is empty
     * @return true if the cell is empty, false otherwise
     */
    public boolean isEmpty(){
        return element.isEmpty();
    }
}
