package visualizer.graphics;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Picture {
	private int[] pixels;
	private BufferedImage image;
	private String filePath;
	
	public Picture(String path) {
		this.filePath = path;
		load();
	}
	
	private void load() {
		try {
			image = ImageIO.read(Picture.class.getResource(filePath));
			int h = image.getHeight();
			int w = image.getWidth();
			
			this.pixels = new int[w * h]; 
			image.getRGB(0, 0, w, h, pixels, 0, w);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int[] getPixels() { 
		return pixels;
	}
	public int getWidth() {
		return image.getWidth();
	}
	public int getHeight() {
		return image.getHeight();
	}
	
}
