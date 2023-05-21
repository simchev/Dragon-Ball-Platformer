package chev.game2.handlers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Content {

	// Player
	public static BufferedImage[][] Player;
	private static final int[] playerSprites = { 6, 8, 8, 4, 3, 2, 3 };
	
	public static BufferedImage[][] load(String s, int[] ns, int w, int h) {
		BufferedImage[][] sprites;
		
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			
			sprites = new BufferedImage[playerSprites.length][];
			for (int i = 0; i < playerSprites.length; i++) {
				sprites[i] = new BufferedImage[ns[i]];
			}
			
			for(int i = 0; i < sprites.length; i++) {
				for(int j = 0; j < sprites[i].length; j++) {
					sprites[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			
		}
		catch(Exception e) {
			sprites = null;
			System.out.println("Erreur lors du chargement des images");
			e.printStackTrace();
			System.exit(0);
		}
		
		return sprites;
	}
	
	public static void loadSprites() {
		Player = load("/goku.png", playerSprites, 30, 40);
	}
	
}
