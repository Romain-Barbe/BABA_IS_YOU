package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.elementList.EnumCategory;
import model.elementList.EnumDirection;
import model.elementList.EnumWord;
import model.input.InputData;

/**
 * The class who represent the game data
 * @author BARBE Romain
 * @author ROBERT Eric
 */
public class Model {
	private Cell[][] grid;
	private HashMap<EnumWord, Set<EnumWord>> rules;

	/**
	 * Model constructor
	 * @param file String (name) of the level
	 * @param cheatRules A map in which some cheat rules are set
	 */
	public Model(String file, HashMap<EnumWord, Set<EnumWord>> cheatRules) {
		try {
			this.grid = InputData.readFile(file);
		} catch (IOException e) {
			throw new IllegalArgumentException("File " + file + " do not exist");
		}
		this.rules = this.generateRules(this.grid, cheatRules);
	}

	/**
	 * Return the amount of line of the game's grid
	 * @return amount of line in the grid
	 */
	public int getNbLine() {
		return grid.length;
	}

	/**
	 * Return the amount of column of the game's grid
	 * @return amount of column in the grid
	 */
	public int getNbColumn() {
		return grid[0].length;
	}

	/**
	 * Return the actual list of rule
	 * @return an HashMap of rule
	 */
	public HashMap<EnumWord, Set<EnumWord>> getRules() {
		return this.rules;
	}

	/**
	 * Update the actual rule of the grid in our ArrayList
	 * @param cheatRules A map in which some cheat rules are set
	 */
	public void refreshRules(HashMap<EnumWord, Set<EnumWord>> cheatRules) {
		this.rules = this.generateRules(this.grid, cheatRules);
	}

	/**
	 * Verified if the block is bind to a rule
	 * @param block Block to check
	 * @return a string use to define the block state
	 */
	private String verifiedBlock(Block block) {
		if (rules.containsKey(block.getName()) && block.getClass() == ElementBlock.class) {
			var rule = rules.get(block.getName());
			if (rule.contains(EnumWord.STOP)) {
				return "STOP";
			} else if (rule.contains(EnumWord.PUSH)) {
				return "PUSH";
			} else if (rule.contains(EnumWord.YOU)) {
				return "YOU";
			} else if (rule.contains(EnumWord.DEFEAT)) {
				return "DEFEAT";
			}
		}
		return "OTHER";
	}

	/**
	 * Move the block to a direction (if possible)
	 * @param line Line coordinate of the block
	 * @param column Column coordinate of the block
	 * @param block Block to move
	 * @param dir Direction of the movement
	 * @return true if the move is successful, false otherwise
	 */
	public boolean moveBlock(int line, int column, Block block, EnumDirection dir) {
		if (line < 0 || column < 0 || line >= grid.length || column >= grid[0].length) {
			return false;
		} else if (grid[line][column].isEmpty()) {
			return true;
		}
		int lineNext;
		int columnNext;
		switch (dir) {
		case RIGHT: {
			lineNext = line;
			columnNext = column + 1;
			if (moveBlock(lineNext, columnNext, block, dir)) {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP") {
						return false;
					}
				}
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "PUSH" || verifiedBlock(actualBlock) == "YOU"
							|| actualBlock.getClass() == WordBlock.class) {
						grid[lineNext][columnNext].addBlock(actualBlock);
						grid[line][column].removeBlockAt(i);
					}
				}
				return true;
			} else {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP" || verifiedBlock(actualBlock) == "PUSH" ||  verifiedBlock(actualBlock) == "YOU" || actualBlock.getClass() == WordBlock.class) {
						return false;
					}
				}
				return true;
			}
		}
		case LEFT: {
			lineNext = line;
			columnNext = column - 1;
			if (moveBlock(lineNext, columnNext, block, dir)) {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP") {
						return false;
					}
				}
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "PUSH" || verifiedBlock(actualBlock) == "YOU"
							|| actualBlock.getClass() == WordBlock.class) {
						grid[lineNext][columnNext].addBlock(actualBlock);
						grid[line][column].removeBlockAt(i);
					}
				}
				return true;
			} else {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP" || verifiedBlock(actualBlock) == "PUSH" ||  verifiedBlock(actualBlock) == "YOU" || actualBlock.getClass() == WordBlock.class) {
						return false;
					}
				}
				return true;
			}
		}
		case TOP: {
			lineNext = line - 1;
			columnNext = column;
			if (moveBlock(lineNext, columnNext, block, dir)) {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP") {
						return false;
					}
				}
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "PUSH" || verifiedBlock(actualBlock) == "YOU"
							|| actualBlock.getClass() == WordBlock.class) {
						grid[lineNext][columnNext].addBlock(actualBlock);
						grid[line][column].removeBlockAt(i);
					}
				}
				return true;
			} else {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP" || verifiedBlock(actualBlock) == "PUSH" ||  verifiedBlock(actualBlock) == "YOU" || actualBlock.getClass() == WordBlock.class) {
						return false;
					}
				}
				return true;
			}
		}
		case BOTTOM: {
			lineNext = line + 1;
			columnNext = column;
			if (moveBlock(lineNext, columnNext, block, dir)) {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP") {
						return false;
					}
				}
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "PUSH" || verifiedBlock(actualBlock) == "YOU"
							|| actualBlock.getClass() == WordBlock.class) {
						grid[lineNext][columnNext].addBlock(actualBlock);
						grid[line][column].removeBlockAt(i);
					}
				}
				return true;
			} else {
				for (int i = 0; i < grid[line][column].size(); i++) {
					Block actualBlock = grid[line][column].getBlock(i);
					if (verifiedBlock(actualBlock) == "STOP" || verifiedBlock(actualBlock) == "PUSH" ||  verifiedBlock(actualBlock) == "YOU" || actualBlock.getClass() == WordBlock.class) {
						return false;
					}
				}
				return true;
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dir);
		}
	}

	/**
	 * Display the grid on terminal (only use in debug)
	 */
	public void displayGrid() {
		System.out.println("\n****** DISPLAY GRID ******\n");
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				str.append(grid[i][j].toString());
				str.append(" ");

			}
			System.out.println(str);
			str.setLength(0);
			System.out.println("");
		}
		System.out.println("\n**** FIN DISPLAY GRID ****\n");
	}

	/**
	 * Return the game's grid
	 * @return the game grid (2D array)
	 */
	public Cell[][] getGrid() {
		return grid;
	}

	/**
	 * Generate rules from the grid and return those rules
	 * @param grid Grid to check
	 * @param cheatRules A map in which some cheat rules are set
	 * @return a new HashMap of rules
	 */
	public HashMap<EnumWord, Set<EnumWord>> generateRules(Cell[][] grid, HashMap<EnumWord, Set<EnumWord>> cheatRules) {
		HashMap<EnumWord, Set<EnumWord>> rules = new HashMap<>();
		rules = fillHashMap(cheatRules);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].isEmpty()) {
					continue;
				}
				for (int k = 0; k < grid[i][j].size(); k++) {
					Block block = grid[i][j].getBlock(k);
					Block blockX1 = null;
					Block blockX2 = null;
					Block blockY1 = null;
					Block blockY2 = null;

					if (j < grid[0].length - 2) {
						if (!(grid[i][j + 1].isEmpty()) && !(grid[i][j + 2].isEmpty())) {
							blockX1 = grid[i][j + 1].getBlock(0);
							blockX2 = grid[i][j + 2].getBlock(0);
						}
					}
					if (i < grid.length - 2) {
						if (!(grid[i + 1][j].isEmpty()) && !(grid[i + 2][j].isEmpty())) {
							blockY1 = grid[i + 1][j].getBlock(0);
							blockY2 = grid[i + 2][j].getBlock(0);
						}
					}

					if (block != null) {
						if (blockX1 != null && blockX2 != null) {
							if (block.getClass() == WordBlock.class && blockX1.getClass() == WordBlock.class
									&& blockX2.getClass() == WordBlock.class) {
								if (((WordBlock) block).getCategory() == EnumCategory.NOUN
										&& ((WordBlock) blockX1).getCategory() == EnumCategory.OPERATOR
										&& (((WordBlock) blockX2).getCategory() == EnumCategory.NOUN
												|| ((WordBlock) blockX2).getCategory() == EnumCategory.ATTRIBUTE)) {
									Set<EnumWord> set = rules.get(block.getName());
									set.add(blockX2.getName());
									rules.put(block.getName(), set);
								}
							}
						}
						if (blockY1 != null && blockY2 != null) {
							if (block.getClass() == WordBlock.class && blockY1.getClass() == WordBlock.class
									&& blockY2.getClass() == WordBlock.class) {
								if (((WordBlock) block).getCategory() == EnumCategory.NOUN
										&& ((WordBlock) blockY1).getCategory() == EnumCategory.OPERATOR
										&& (((WordBlock) blockY2).getCategory() == EnumCategory.NOUN
												|| ((WordBlock) blockY2).getCategory() == EnumCategory.ATTRIBUTE)) {
									Set<EnumWord> set = rules.get(block.getName());
									set.add(blockY2.getName());
									rules.put(block.getName(), set);
								}
							}
						}
					}
				}
			}
		}
		return rules;
	}

	/**
	 * Fill the rule HashMap of rules with keys (and the content of cheatRules)
	 * @param cheatRules A map in which some cheat rules are set
	 * @return a new HashMap of rules
	 */
	private HashMap<EnumWord, Set<EnumWord>> fillHashMap(HashMap<EnumWord, Set<EnumWord>> cheatRules) {
		HashMap<EnumWord, Set<EnumWord>> rules = new HashMap<>();
		
		cheatRules.keySet().forEach(word -> rules.put(word, new HashSet<>(cheatRules.get(word))));

		rules.putIfAbsent(EnumWord.BABA, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.FLAG, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.WALL, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.WATER, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.SKULL, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.LAVA, new HashSet<EnumWord>());
		rules.putIfAbsent(EnumWord.ROCK, new HashSet<EnumWord>());

		return rules;
	}
	
	/**
	 * Verify if the player have entities to move
	 * @return true if no more entities to move, false if at least one
	 */
	public boolean noMoreXIsYou() {
		Set<EnumWord> setBlockOfYou = new HashSet<EnumWord>();
		for (var rule : this.rules.entrySet()) {
			if (!(rule.getValue().isEmpty())) {
				for (var value : rule.getValue()) {
					if (value == EnumWord.YOU) {
						setBlockOfYou.add(rule.getKey());
					}
				}
			}
		}
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				for (EnumWord word : setBlockOfYou) {
					for (int k = 0; k < this.grid[i][j].size(); k++) {
						Block block = this.grid[i][j].getBlock(k);
						if (block.getName() == word && block.getClass() == ElementBlock.class) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Check on the rule if the block is at the same time you and win
	 * @return true if at least a block is you and win, false otherwise
	 */
	public boolean blockIsYouAndWin() {
		boolean blockIsYou, blockIsWin;
		for (var rule : this.rules.entrySet()) {
			if (!(rule.getValue().isEmpty())) {
				blockIsYou = false;
				blockIsWin = false;
				for (var value : rule.getValue()) {
					if (value == EnumWord.YOU) {
						blockIsYou = true;
					} else if (value == EnumWord.WIN) {
						blockIsWin = true;
					}
				}
				if (blockIsYou && blockIsWin) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verify if a block you is in the same cell as a block win
	 * @return true if yes, false otherwise
	 */
	public boolean blockYouIsOnBlockWin() {
		boolean cellHasYou, cellHasWin;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				cellHasYou = false;
				cellHasWin = false;
				for (int k = 0; k < this.grid[i][j].size(); k++) {
					Block block = this.grid[i][j].getBlock(k);
					if (this.rules.get(block.getName()) != null && block.getClass() == ElementBlock.class) {
						if (this.rules.get(block.getName()).contains(EnumWord.YOU)) {
							cellHasYou = true;
						} else if (this.rules.get(block.getName()).contains(EnumWord.WIN)) {
							cellHasWin = true;
						}
					}
				}
				if (cellHasYou && cellHasWin) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Destroy a block X if it is in the same cell as a block Y
	 * @param enumWordX X property
	 * @param enumWordY Y property
	 */
	public void destroyBlockXIfIsOnBlockY(EnumWord enumWordX, EnumWord enumWordY) {
		boolean cellHasX, cellHasY;
		HashMap<Integer, Block> mapBlockX;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				mapBlockX = new HashMap<>();
				cellHasX = false;
				cellHasY = false;
				for (int k = 0; k < this.grid[i][j].size(); k++) {
					Block block = this.grid[i][j].getBlock(k);
					if (this.rules.get(block.getName()) != null && block.getClass() == ElementBlock.class) {
						if (this.rules.get(block.getName()).contains(enumWordX)) {
							cellHasX = true;
							mapBlockX.put(k, block);
						}
						if (this.rules.get(block.getName()).contains(enumWordY)) {
							cellHasY = true;
						}
					}
				}
				if (cellHasX && cellHasY) {
					Iterator<Entry<Integer, Block>> iterator = mapBlockX.entrySet().iterator();
					while (iterator.hasNext()) {
						var pair = iterator.next();
						this.grid[i][j].removeBlockAt(pair.getKey());
						iterator.remove();
					}
				}
			}
		}
	}

	/**
	 * Destroy a block if it is on a block bind by sink rule and destroy the block sink
	 */
	public void destroyBlockXAndBlockSinkIsInTheSameCell() {
		boolean cellHasOther, cellHasSink;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				cellHasOther = false;
				cellHasSink = false;
				for (int k = 0; k < this.grid[i][j].size(); k++) {
					Block block = this.grid[i][j].getBlock(k);
					if (this.rules.get(block.getName()) != null && block.getClass() == ElementBlock.class) {
						if (this.rules.get(block.getName()).contains(EnumWord.SINK)) {
							cellHasSink = true;
						} else {
							cellHasOther = true;
						}
					}
				}
				if (cellHasOther && cellHasSink) {
					this.grid[i][j].removeBlockAt(1);
					this.grid[i][j].removeBlockAt(0);
				}
			}
		}
	}

	/**
	 * Transform a block according to rules (ex : baba is rock)
	 */
	public void transformABlockIfARuleSaidSo() {
		for (var rule : this.rules.entrySet()) {
			if (!(rule.getValue().isEmpty())) {
				for (var value : rule.getValue()) {
					switch (value.toString()) {
					case "BABA": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.BABA);
						break;
					}
					case "FLAG": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.FLAG);
						break;
					}
					case "WALL": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.WALL);
						break;
					}
					case "WATER": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.WATER);
						break;
					}
					case "SKULL": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.SKULL);
						break;
					}
					case "LAVA": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.LAVA);
						break;
					}
					case "ROCK": {
						tranformAllBlockXToBlockY(rule.getKey(), EnumWord.ROCK);
						break;
					}
					default:
					}
				}
			}
		}
	}

	/**
	 * Transform all X block to Y block
	 * @param enumWordX Block to transform
	 * @param enumWordY Block that will take place of X block
	 */
	public void tranformAllBlockXToBlockY(EnumWord enumWordX, EnumWord enumWordY) {
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[0].length; j++) {
				for (int k = 0; k < this.grid[i][j].size(); k++) {
					Block block = this.grid[i][j].getBlock(k);
					if (block.getName() == enumWordX && block.getClass() == ElementBlock.class) {
						this.grid[i][j].removeBlockAt(k);
						this.grid[i][j].addBlock(new ElementBlock(enumWordY));
					}
				}
			}
		}
	}
}
