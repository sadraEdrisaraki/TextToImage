import java.awt.image.BufferedImage;

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
		int maxCursor_x = bufImage.getWidth()-radius;
		int maxCursor_y = bufImage.getHeight()-radius;
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
}










