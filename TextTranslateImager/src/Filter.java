
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
	
	public static int[][] test(){ return null;}

}
