package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class UtilityTool {

	private static final Map<String, BufferedImage> imageCache = new HashMap<>();

	// Sprites are shared, already scaled, and must not be modified by entities.
	public static synchronized BufferedImage loadImage(String path, int width, int height) {
		String key = path + ":" + width + "x" + height;
		BufferedImage image = imageCache.get(key);
		if(image != null) {
			return image;
		}
		try(InputStream stream = UtilityTool.class.getResourceAsStream(path)) {
			if(stream == null) {
				throw new IllegalArgumentException("Missing image: " + path);
			}
			image = ImageIO.read(stream);
			if(image == null) {
				throw new IOException("Unsupported image: " + path);
			}
			image = new UtilityTool().scaleImage(image, width, height);
			imageCache.put(key, image);
			return image;
		} catch(IOException e) {
			throw new IllegalStateException("Could not load image: " + path, e);
		}
	}

	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		
		Graphics2D g2 = scaledImage.createGraphics();
		
		g2.drawImage(original, 0, 0, width, height, null);
		
		g2.dispose(); 
		return scaledImage;
	}
}
