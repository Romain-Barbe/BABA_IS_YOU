package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import model.Block;
import model.Cell;
import model.ElementBlock;
import model.Model;
import model.elementList.EnumDirection;
import model.elementList.EnumWord;
import view.View;

/**
 * A class which is assembling the game data, the game view and the user's input to run the game
 * @author BARBE Romain
 * @author ROBERT Eric
 * @version 1
 */
public class Controller {
	
	/**
	 * Main method, used to run each level.
	 * @param args The arguments the user give at the execution (for some cheat rules)
	 */
	public static void main(String[] args) {
		HashMap<EnumWord, Set<EnumWord>> cheatRules = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--execute")) {
				Set<EnumWord> set = new HashSet<>();
				set.add(EnumWord.valueOf(args[i + 3].toUpperCase()));
				cheatRules.put(EnumWord.valueOf(args[i + 1].toUpperCase()), set);
			}
		}
		
		Application.run(Color.BLACK, context -> {
			String level0 = "level0.txt";
			String level1 = "level1.txt";
			String level2 = "level2.txt";
			String level3 = "level3.txt";
			String level4 = "level4.txt";
			String level5 = "level5.txt";
			String level6 = "level6.txt";

			System.out.println("Level 0");
			playLevel(context, level0, cheatRules);
			System.out.println("Level 1");
			playLevel(context, level1, cheatRules);
			System.out.println("Level 2");
			playLevel(context, level2, cheatRules);
			System.out.println("Level 3");
			playLevel(context, level3, cheatRules);
			System.out.println("Level 4");
			playLevel(context, level4, cheatRules);
			System.out.println("Level 5");
			playLevel(context, level5, cheatRules);
			System.out.println("Level 6");
			playLevel(context, level6, cheatRules);

			System.out.println("End of the game !");
			context.exit(0);
		});
	}

	/**
	 * Play a level
	 * @param context The ApplicationContext in which the game will be printed
	 * @param level Actual level
	 * @param cheatRules A map in which some cheat rules are set
	 */
	public static void playLevel(ApplicationContext context, String level, HashMap<EnumWord, Set<EnumWord>> cheatRules) {
		Model model = new Model(level, cheatRules);
		Cell[][] grid = model.getGrid();
		HashMap<EnumWord, Set<EnumWord>> rules = model.getRules();

		int sizeGridX = model.getNbLine();
		int sizeGridY = model.getNbColumn();
		System.out.println("Nb lines = " + sizeGridX + " && nb columns = " + sizeGridY);

		ScreenInfo screenInfo = context.getScreenInfo();
		float width = screenInfo.getWidth();
		float height = screenInfo.getHeight();

		System.out.println("size of the screen (" + width + " x " + height + ")");

		for (;;) {
			View.draw(context, sizeGridX, sizeGridY, model.getGrid());
			rules = model.getRules();
			Event event = context.pollOrWaitEvent(100);
			if (event == null) {
				continue;
			}
			Action action = event.getAction();
			if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.Q) {
				System.out.println("Leaving the game !");
				context.exit(0);
				return;
			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.R || model.noMoreXIsYou()) {
				System.out.println("Restarting this level !");
				playLevel(context, level, cheatRules);
				return;
			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.N || model.blockIsYouAndWin()
					|| model.blockYouIsOnBlockWin()) {
				System.out.println("You won this level, leaving this level !");
				return;
			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.UP) {
				ArrayList<Block> blocks = new ArrayList<Block>();
				ArrayList<Integer[]> pos = new ArrayList<>();
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						if (grid[i][j].isEmpty()) {
							continue;
						}
						for (int k = 0; k < grid[i][j].size(); k++) {
							Block block = grid[i][j].getBlock(k);
							if (block != null) {
								if (block.getClass() == ElementBlock.class) {
									Set<EnumWord> set = rules.get(block.getName());
									if (set.contains(EnumWord.YOU)) {
										blocks.add(block);
										Integer[] tmp = { i, j };
										pos.add(tmp);
									}
								}
							}
						}
					}
				}

				for (int i = 0; i < blocks.size(); i++) {
					model.moveBlock(pos.get(i)[0], pos.get(i)[1], blocks.get(i), EnumDirection.TOP);
				}

			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.DOWN) {
				ArrayList<Block> blocks = new ArrayList<Block>();
				ArrayList<Integer[]> pos = new ArrayList<>();
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						if (grid[i][j].isEmpty()) {
							continue;
						}
						for (int k = 0; k < grid[i][j].size(); k++) {
							Block block = grid[i][j].getBlock(k);
							if (block != null) {
								if (block.getClass() == ElementBlock.class) {
									Set<EnumWord> set = rules.get(block.getName());
									if (set.contains(EnumWord.YOU)) {
										blocks.add(block);
										Integer[] tmp = { i, j };
										pos.add(tmp);
									}
								}
							}
						}
					}
				}

				for (int i = blocks.size() - 1; i >= 0; i--) {
					model.moveBlock(pos.get(i)[0], pos.get(i)[1], blocks.get(i), EnumDirection.BOTTOM);
				}

			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.RIGHT) {
				ArrayList<Block> blocks = new ArrayList<Block>();
				ArrayList<Integer[]> pos = new ArrayList<>();
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						if (grid[i][j].isEmpty()) {
							continue;
						}
						for (int k = 0; k < grid[i][j].size(); k++) {
							Block block = grid[i][j].getBlock(k);
							if (block != null) {
								if (block.getClass() == ElementBlock.class) {
									Set<EnumWord> set = rules.get(block.getName());
									if (set.contains(EnumWord.YOU)) {
										blocks.add(block);
										Integer[] tmp = { i, j };
										pos.add(tmp);
									}
								}
							}
						}
					}
				}

				for (int i = blocks.size() - 1; i >= 0; i--) {
					model.moveBlock(pos.get(i)[0], pos.get(i)[1], blocks.get(i), EnumDirection.RIGHT);
				}

			} else if (action == Action.KEY_PRESSED && event.getKey() == KeyboardKey.LEFT) {
				ArrayList<Block> blocks = new ArrayList<Block>();
				ArrayList<Integer[]> pos = new ArrayList<>();
				for (int i = 0; i < grid.length; i++) {
					for (int j = 0; j < grid[0].length; j++) {
						if (grid[i][j].isEmpty()) {
							continue;
						}
						for (int k = 0; k < grid[i][j].size(); k++) {
							Block block = grid[i][j].getBlock(k);
							if (block != null) {
								if (block.getClass() == ElementBlock.class) {
									Set<EnumWord> set = rules.get(block.getName());
									if (set.contains(EnumWord.YOU)) {
										blocks.add(block);
										Integer[] tmp = { i, j };
										pos.add(tmp);
									}
								}
							}
						}
					}
				}

				for (int i = 0; i < blocks.size(); i++) {
					model.moveBlock(pos.get(i)[0], pos.get(i)[1], blocks.get(i), EnumDirection.LEFT);
				}
			}
			
			System.out.println("/******** CHEAT RULES ********/");
			for (var rule : cheatRules.entrySet()) {
				if (!(rule.getValue().isEmpty())) {
					for (var value : rule.getValue()) {
						System.out.println("CHEAT : " + rule.getKey() + " IS " + value);
					}
				}
			}
			System.out.println("/****** FIN CHEAT RULES ******/");

			model.refreshRules(cheatRules);

			System.out.println("/*********** RULES ***********/");
			rules = model.getRules();
			for (var rule : rules.entrySet()) {
				if (!(rule.getValue().isEmpty())) {
					for (var value : rule.getValue()) {
						System.out.println(rule.getKey() + " IS " + value);
					}
				}
			}
			System.out.println("/********* FIN RULES *********/");

			model.destroyBlockXIfIsOnBlockY(EnumWord.YOU, EnumWord.DEFEAT);
			model.destroyBlockXIfIsOnBlockY(EnumWord.MELT, EnumWord.HOT);
			model.destroyBlockXAndBlockSinkIsInTheSameCell();
			model.transformABlockIfARuleSaidSo();
		}
	}
}
