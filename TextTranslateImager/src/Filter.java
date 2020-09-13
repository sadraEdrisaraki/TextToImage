import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class Filter {
	
	public Filter() {}
	
	
	
	public static int[][] repeupler(int[][] matriceRGB , int nombrePixelEntre){
		int tailleX = (matriceRGB.length-1)*nombrePixelEntre;
		int tailleY = (matriceRGB[0].length-1)*nombrePixelEntre;
		int[][] matriceRGBFinal = new int[tailleX][tailleY];
		System.out.println("Taille de l'image avant peuplement = " + matriceRGB.length);
		System.out.println("Taille de l'image après peuplement = " + matriceRGBFinal.length);
		
		for(int i = 0 ; i < matriceRGBFinal.length ; i++) {
			for(int j = 0 ; j < matriceRGBFinal[i].length ; j++) {
				int moduloX = i/(nombrePixelEntre+1);
				int moduloY = j/(nombrePixelEntre+1);
				matriceRGBFinal[i][j] = matriceRGB[moduloX][moduloY];
			}
		}
		
		return matriceRGBFinal;
		
	}
	
	public static void smoother(BufferedImage bufImage, int weight,int radius, int padding){

		System.out.println("ALGO SMOOTHER : ");
		int[] packageTemp = new int[radius*radius];
		int cursor_x = 0;
		int cursor_y = 0;

		int debordement_x;
		int debordement_y;
		int moyenneDirectTest=0;

		if(weight>100) weight=100;
		else if(weight<0) weight=0;
		
		
		for(cursor_y=0 ; cursor_y < bufImage.getHeight() ; cursor_y+=padding) {
			for(cursor_x=0 ; cursor_x < bufImage.getWidth() ; cursor_x+=padding) {
				
				if(cursor_x+radius >= bufImage.getWidth()) debordement_x= cursor_x+radius - bufImage.getWidth();
				else debordement_x = 0;
				if(cursor_y+radius >= bufImage.getHeight()) debordement_y= cursor_y+radius - bufImage.getHeight();
				else debordement_y = 0;
				

				for(int i=0; i<radius*radius ; i++) {
					packageTemp[i]=0;
				}
				
				bufImage.getRGB(cursor_x, cursor_y, radius-debordement_x, radius-debordement_y, packageTemp, 0, radius);
				
				for(int i=0; i<radius*radius ; i++) {
					moyenneDirectTest += packageTemp[i];
				}
				moyenneDirectTest /= radius*radius;
				
				//Faire la transition de chaque couleur vers la moyenne avec le "weight" en pourcentage
				
				for(int i=0; i<radius*radius ; i++) {
						if(packageTemp[i] < moyenneDirectTest )
							packageTemp[i] += (Math.abs(moyenneDirectTest-packageTemp[i]) / 100)*weight;
						else
							packageTemp[i] -= (Math.abs(moyenneDirectTest-packageTemp[i]) / 100)*weight;
				}
				bufImage.setRGB(cursor_x, cursor_y, radius-debordement_x, radius-debordement_y, packageTemp,0,radius);
				
			}
		}
	}
	
	public static void agglutiner(BufferedImage bufImage,Coordinate staticPixelcoordinate) {
		System.out.println("ALGO AGGLUTINER : ");
		int goodRGB;
		int badRGB;
		Coordinate coordinateTemp;
		List<Coordinate> listCoordinatePixelsPlaced = new ArrayList<Coordinate>();
		
		listCoordinatePixelsPlaced.add(staticPixelcoordinate);
		
		
		for(int y=0 ; y<bufImage.getHeight() ; y++) {
			System.out.println(y);
			for(int x=0 ; x<bufImage.getWidth() ; x++ ) {
				listCoordinatePixelsPlaced.add(new Coordinate(x,y));
				coordinateTemp=searchSimilarPixelCoordinate(bufImage,listCoordinatePixelsPlaced,new Coordinate(x,y));

				goodRGB = bufImage.getRGB(coordinateTemp.x, coordinateTemp.y);
				badRGB = bufImage.getRGB(x,y);
				
				bufImage.setRGB(coordinateTemp.x, coordinateTemp.y,badRGB);
				bufImage.setRGB(x, y,goodRGB);
			}
		}
	}
	
	private static Coordinate searchSimilarPixelCoordinate(BufferedImage bufImage,List<Coordinate> forbiddenPixels, Coordinate pixelTarget) {
		Coordinate finalCoordinate = new Coordinate();
		int RGB = bufImage.getRGB(pixelTarget.x,pixelTarget.y);
	    
	    int RGBb;
	    int Rb;
	    int Gb;
	    int Bb;
	    int score;
	    int bestScore=-1;
	    
	    int RGBTarget= bufImage.getRGB(pixelTarget.x,pixelTarget.y);
	    
	    
		for(int y=0 ; y<bufImage.getHeight() ; y++) {
			for(int x=0 ; x<bufImage.getWidth() ; x++ ) {
				
				if(!forbiddenPixels.contains(new Coordinate(x,y))){
					RGBb = bufImage.getRGB(y,x);
				    Rb = (RGBb >> 16) & 0xff;
				    Gb = (RGBb >> 8) & 0xff;
				    Bb = (RGBb) & 0xff;
				    
				    score = 
				    	Math.abs(Rb-((RGBTarget >> 16) & 0xff))+
				    	Math.abs(Gb-((RGBTarget >> 8) & 0xff))+
				    	Math.abs(Bb-((RGBTarget) & 0xff));
				    
				    if(bestScore==-1){
				    	bestScore=score;
				    }
				    if(bestScore < score){
				    	bestScore=score;
				    	finalCoordinate = new Coordinate(x,y);
				    }
				}
			}
		}
		return finalCoordinate;
	}
}










