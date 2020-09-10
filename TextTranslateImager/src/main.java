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
		
		System.out.println("Debut du logiciel de traduction de texte en image");
		System.out.println("Vérification du fichier texte");
		
		List<String> listeMots = Utils.lireTexteToString();
		String listeMotsConcatene = Utils.remplacerCaractereSpeciaux(listeMots);
		List<Integer> listeRGB = Utils.convertStringToRGB(listeMotsConcatene);
		int imageSize = (int) Math.ceil(Math.sqrt(listeRGB.size()));
		int[][] matriceRGB = Utils.listToArray(listeRGB , imageSize);
		matriceRGB = Filter.repeupler(matriceRGB,1500);
		imageSize = (int) Math.ceil(Math.sqrt(Utils.nbrElement(matriceRGB)));
		BufferedImage image = Utils.createImage(imageSize , imageSize , matriceRGB);
		Utils.saveImage(image);
		
		System.out.println("Fini");
		 

	}
	
	
	
	
	
	
	
	

	
	
	
	

}
