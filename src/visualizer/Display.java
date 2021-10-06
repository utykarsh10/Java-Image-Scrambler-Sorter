package visualizer;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import visualizer.graphics.Picture;
import visualizer.sorting.MasterSorter;
import visualizer.sorting.Pixel;
import visualizer.sorting.SortingMethod;

public class Display extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Thread thread;
	private static String title = "Sorting image";
	private int width = 1000;
	private int height = 600;

	private boolean checkRun = false;
	private boolean sorted = false;

	private Picture picture;
	private BufferedImage image;
	private int[] pixels;
	
	private Pixel[] myPixels;
	
	public Display() {
		
		frame = new JFrame();
		picture = new Picture("/images/Mona.jpg"); 
		
		width = picture.getWidth();
		height = picture.getHeight();
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		myPixels = new Pixel[width * height];
		
		
		Dimension size = new Dimension(400, (int) (400.0/width * height));
		this.setPreferredSize(size);
		
		initPixels();
	}
	
	private void initPixels() {
		for(int i =0; i < pixels.length; i++) {
				myPixels[i] = new Pixel(picture.getPixels()[i], i);
		}	
		
		randomizePixels();
	}
		
	private void randomizePixels() {
		ArrayList<Pixel> arr = new ArrayList<Pixel>();
		
		for(int i =0; i < myPixels.length; i++) {
			arr.add(myPixels[i]);
		}	
		Collections.shuffle(arr);
		
		for(int i =0; i < myPixels.length; i++) {
			myPixels[i] = arr.get(i);
	}	
	}
	
	public static void main(String [] args) {
		Display disp = new Display();
		disp.frame.setTitle(title);
		disp.frame.add(disp);
		disp.frame.pack();
		disp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.frame.setLocationRelativeTo(null);
		disp.frame.setVisible(true);
		
		disp.start();
		
	}

	public synchronized void start() {
		checkRun = true;
		thread = new Thread(this, "Display");
		thread.start();
		
	}
	
	public synchronized void stop() {
		checkRun = false;
		try {
			thread.join();
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/100;
		double delta =0;
		int updates = 0;
		int frames = 0;
		
		
		// TODO Auto-generated method stub
		while(checkRun) {
			long now = System.nanoTime();
			
			
			delta += (now-lastTime)/ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render(); 
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "|" + frames + " fps ");
				frames = 0;
				updates = 0;
			}
		}

	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		for(int i =0; i < pixels.length; i++) {
			pixels[i] = myPixels[i].color;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
		
	}
	
	private void update() {
	if(!sorted) {
		sorted = MasterSorter.sort(myPixels, SortingMethod.Bubblesort, 1000000);
		} 
		
	}

}
