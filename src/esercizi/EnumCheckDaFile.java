package esercizi;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


// FUNZIONA MA SUDDIVIDI IN METODI PER RIORDINARE
public class EnumCheckDaFile {

	public static void main(String[] args) {
		EnumCheckDaFile ecdf = new EnumCheckDaFile();
		ecdf.run();
		
	}
	
	private void run() {
		String fileName = "src/text_files/auto-brands.txt";
		String fileLine;
		String undefinedBrands = "";
		String definedBrands = "";
		String brand;
		String[] arrItems;
		
		try {
			// Apro il file
			FileReader autoBrandsFile = new FileReader(fileName);
			
			// Preparo il file alla lettura
			BufferedReader bufferedReader = new BufferedReader(autoBrandsFile);
			
			// Finchè la riga non è vuota (ovvero null) stampo il contenuto
			while ((fileLine = bufferedReader.readLine()) != null) {
				
				arrItems = fileLine.strip().split(" +");   // .strip() toglie gli spazi davanti e dietro
				
				// Stampo il brand
				// System.out.println(fileLine);
				brand = arrItems[0]; //TODO -> Da correggere, il brand non è sempre al primo posto
				
				// Found Flag
				boolean brandFound = false;
				
				/* Verifico SE il brand è uguale a uno di quelli nell'enum, in caso
				 * lo salvo in "definedBrands" */
				for (EnumAuto enumBrandName : EnumAuto.values()) {
					if (enumBrandName.name().equals(brand)) {   // Con .name() prendo il nome dell'enumerazione
						definedBrands += "- " + brand + " (Auto prodotte: " + EnumAuto.autoProduced(brand) + ")\n";
						brandFound = true;
						break;
					}
				}
				
				/* SE il flag "brandFound" è rimasto a FALSE vuol dire che il brand non
				 * è incluso tra quelli nell'enum, quindi lo aggiungo a "undefinedBrands" */
				if (!brandFound) {
					undefinedBrands += "- " + brand + "\n";
					continue;
				}
				
				checkModelli(brand, arrItems);
				
			}
			
			// Chiudo il bufferedReader per non sprecare risorse del sistema
			bufferedReader.close();
			
			System.out.println("Brand definiti:\n" + definedBrands); // con "\n" posso andare a capo nel print
			System.out.println("Brand indefiniti:\n" + undefinedBrands);
			
			
		} catch (IOException e) {
			System.err.println("Si è verificato un errore durante la lettura del file: " + e.getMessage());
		}
	}

	private void checkModelli(String brand, String[] arrItems) {
		EnumAuto enumBrand = EnumAuto.valueOf(brand);
		String singleModel = "";
		
		for (int i = 1; i < arrItems.length; i++) {
			singleModel = arrItems[i];
			if (enumBrand.isModelDefined(singleModel)) {
				System.out.println("Il modello " + singleModel + " del brand " + brand + " è definito");
			} else {
				System.out.println("Il modello " + singleModel + " del brand " + brand + " non è definito");
			}
		}
	}

}
