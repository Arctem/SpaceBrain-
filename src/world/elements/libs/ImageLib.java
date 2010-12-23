package world.elements.libs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLib {

	private static BufferedImage SHIP1;
	private static BufferedImage SHIP2;
	private static BufferedImage BULLET1;
	private static BufferedImage ITEM1;
	private static BufferedImage ITEM2;

	public static BufferedImage getImage(ImageType i) {
		switch (i) {
		case SHIP_1:
			return SHIP1;
		case SHIP_2:
			return SHIP2;
		case BULLET_1:
			return BULLET1;
		case ITEM_1:
			return ITEM1;
		case ITEM_2:
			return ITEM2;
		default:
			return null;
		}
	}

	public static void init() {
		System.out.println("Loading images...");
		try {
			SHIP1 = ImageIO.read(new File("data/sprites/ship.png"));
			SHIP2 = ImageIO.read(new File("data/sprites/ship2.png"));
			BULLET1 = ImageIO.read(new File("data/sprites/bullet02.png"));
			ITEM1 = ImageIO.read(new File("data/sprites/item01.png"));
			ITEM2 = ImageIO.read(new File("data/sprites/item02.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Oh jeepers. Error loading images.");
		}
	}
}
