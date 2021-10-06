package visualizer.sorting;

public class MasterSorter {
	public static boolean sort(Pixel[] pixels, SortingMethod m, int iteration) {
		
		for(int i = 0; i < iteration; i++) {
			if(m == SortingMethod.Bubblesort) {	
			 BubbleSort.sort(pixels);
			}
		}
		
		return isSorted(pixels); 
	}
	
	public static boolean isSorted(Pixel [] pixels) {
		for(int i = 0; i < pixels.length; i++) {
			Pixel pixel = pixels[i];
			if(i != pixel.id) {
				return false;
			}
		}
		
		return true;
	}
	
}
