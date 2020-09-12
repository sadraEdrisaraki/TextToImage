import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class main {

	
	

	public static void main(String[] args) {
		BufferedImage image;
		List<String> listeMots;
		String listeMotsConcatene;
		List<Integer> listeRGB;
		
		
		System.out.println("Debut du logiciel de traduction de texte en image");
		System.out.println("Vérification du fichier texte");
		
		listeMots = Utils.lireTexteToString();
		listeMotsConcatene = Utils.remplacerCaractereSpeciaux(listeMots);
		listeRGB = Utils.convertStringToRGB(listeMotsConcatene);
		int imageSize = (int) Math.ceil(Math.sqrt(listeRGB.size()));
		int[][] matriceRGB = Utils.listToArray(listeRGB , imageSize);
		//matriceRGB = Filter.repeupler(matriceRGB,1500);
		
		imageSize = (int) Math.ceil(Math.sqrt(Utils.nbrElement(matriceRGB)));
		image = Utils.createImage(imageSize , imageSize , matriceRGB);
		Filter.smoother(image, 90, 30 , 13);
		Utils.saveImage(image);
		
		System.out.println("Fini");
		 

	}
	
	
	
	
	
	
	
	

	
	
	
	

}
