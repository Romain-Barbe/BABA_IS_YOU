package model.input;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import model.Block;
import model.Cell;
import model.WordBlock;
import model.ElementBlock;
import model.elementList.EnumCategory;
import model.elementList.EnumWord;

/**
 * Class who create the game grid
 * @author BARBE Romain
 * @author ROBERT Eric
 */
public class InputData {
	
	/**
	 * Read a file and return a 2D array of Cell
	 * @param file File to read
	 * @return 2D array of the grid
	 * @throws IOException if the file is not found
	 */
	public static Cell[][] readFile(String file) throws IOException {
		Path levelPath = Paths.get("classes/" + file); /* ("ressource" + file) si tests en dev */
		System.out.println("TEST" + levelPath.toAbsolutePath());
		Scanner sc = new Scanner(Files.newBufferedReader(levelPath, StandardCharsets.UTF_8));
		int height = Integer.parseInt(sc.next()), width = Integer.parseInt(sc.next());
		sc.nextLine();
		Cell[][] grid = new Cell[height][width];
		int i = 0, j = 0;
		while (sc.hasNextLine()) {
			Scanner sc2 = new Scanner(sc.nextLine());
			j = 0;
			while (sc2.hasNext()) {
				String s = sc2.next();
				grid[i][j] = new Cell();
				Block b = stringToBlock(s);
				if (b != null) {
					grid[i][j].addBlock(b);
				}
				j++;
			}
			sc2.close();
			i++;
		}
		sc.close();
		return grid;
	}

	/**
	 * Call stringToWordBlock or stringToElementBlock depending of the string start
	 * @param s String to convert
	 * @return the new block created
	 */
	private static Block stringToBlock(String s) {
		return switch (s.charAt(0)) {
			case 'W' -> stringToWordBlock(s);
			case 'E' -> stringToElementBlock(s);
		default -> null;
		};
	}
	
	/**
	 * Convert a string to a word block
	 * @param s String to convert
	 * @return the block affiliated with the string
	 */
	private static Block stringToWordBlock(String s) {
		return switch (s) {
			case "W-BABA" -> new WordBlock(EnumWord.BABA, EnumCategory.NOUN);
			case "W-FLAG" -> new WordBlock(EnumWord.FLAG, EnumCategory.NOUN);
			case "W-WALL" -> new WordBlock(EnumWord.WALL, EnumCategory.NOUN);
			case "W-WATER" -> new WordBlock(EnumWord.WATER, EnumCategory.NOUN);
			case "W-SKULL" -> new WordBlock(EnumWord.SKULL, EnumCategory.NOUN);
			case "W-LAVA" -> new WordBlock(EnumWord.LAVA, EnumCategory.NOUN);
			case "W-ROCK" -> new WordBlock(EnumWord.ROCK, EnumCategory.NOUN);
			case "W-IS" -> new WordBlock(EnumWord.IS, EnumCategory.OPERATOR);
			case "W-YOU" -> new WordBlock(EnumWord.YOU, EnumCategory.ATTRIBUTE);
			case "W-WIN" -> new WordBlock(EnumWord.WIN, EnumCategory.ATTRIBUTE);
			case "W-STOP" -> new WordBlock(EnumWord.STOP, EnumCategory.ATTRIBUTE);
			case "W-PUSH" -> new WordBlock(EnumWord.PUSH, EnumCategory.ATTRIBUTE);
			case "W-MELT" -> new WordBlock(EnumWord.MELT, EnumCategory.ATTRIBUTE);
			case "W-HOT" -> new WordBlock(EnumWord.HOT, EnumCategory.ATTRIBUTE);
			case "W-DEFEA" -> new WordBlock(EnumWord.DEFEAT, EnumCategory.ATTRIBUTE);
			case "W-SINK" -> new WordBlock(EnumWord.SINK, EnumCategory.ATTRIBUTE);
		default -> null;
		};
	}
	
	/**
	 * Convert a string to an element block
	 * @param s String to convert
	 * @return the block affiliated with the string
	 */
	private static Block stringToElementBlock(String s) {
		return switch (s) {
			case "E-BABA" -> new ElementBlock(EnumWord.BABA);
			case "E-FLAG" -> new ElementBlock(EnumWord.FLAG);
			case "E-WALL" -> new ElementBlock(EnumWord.WALL);
			case "E-WATER" -> new ElementBlock(EnumWord.WATER);
			case "E-SKULL" -> new ElementBlock(EnumWord.SKULL);
			case "E-LAVA" -> new ElementBlock(EnumWord.LAVA);
			case "E-ROCK" -> new ElementBlock(EnumWord.ROCK);
		default -> null;
		};
	}
}
