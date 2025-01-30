package esercizi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


// FUNZIONA MA SUDDIVIDI IN METODI PER RIORDINARE
public class EnumCheckDaFile {

	private String fileName = "src/text_files/auto-brands.txt";

	public static void main(String[] args) {
		EnumCheckDaFile ecdf = new EnumCheckDaFile();
		ecdf.run();
		
	}
	
	/*TODO -> MODIFICHE DA FARE:
	 * - Suddividere la logica in diversi metodi
	 * es. Metodo che verifica se il brand è uno di quelli nell'enum
	 *     e nel caso lo salva in definedBrands
	 * es. Metodo che salva il brand in undefinedBrands se brandFound è rimasto false
	 * 
	 * - Aggiungere un metodo che trovi all'interno della riga il brand
	 * - Per riconoscere che sia un brand o fai un array di stringhe o un enum CarBrands
	 * - invece di usare il path assoluto per trovare il file usa quello relativo */
	
	private void run() {
		String undefinedBrands = "";
		String definedBrands = "";
		String brand;
		String[] lineWordsArr;

		
		
		// Apro il file
		FileReader autoBrandsFile = null;
		try {
			autoBrandsFile = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("\nProgramma terminato: exit 1\n");
			System.exit(1); // Se il programma non trova il file, non può continuare, quindi lo termino.
		}
		
		// Preparo il file alla lettura
		BufferedReader bufferedReader = new BufferedReader(autoBrandsFile);
		
		
		
		// Legge le righe, prende il brand dalla riga, cerca se il brand è contenuto nell'enum
		String currentFileLine;
		try {
			
			// Finchè la riga non è vuota (ovvero null) stampo il contenuto
			while ((currentFileLine = bufferedReader.readLine()) != null) {
				
				lineWordsArr = currentFileLine.strip().split(" +");   // .strip() toglie gli spazi davanti e dietro
				
				// Stampo il brand
				// System.out.println(fileLine);
				brand = lineWordsArr[0]; //TODO -> Da correggere, il brand non è sempre al primo posto
				
				// Found Flag
				boolean brandFound = false;
				
				
				//TODO -> CREA UN METODO CHE RITORNI TRUE O FALSE (passi enum e brand)
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
				
				checkModelli(brand, lineWordsArr);
				
			}
			
			// Chiudo il bufferedReader per non sprecare risorse del sistema
			bufferedReader.close();
			
			System.out.println("Brand definiti:\n" + definedBrands); // con "\n" posso andare a capo nel print
			System.out.println("Brand indefiniti:\n" + undefinedBrands);
			
			
		} catch (IOException e) {
			System.err.println("Si è verificato un errore durante la lettura del file: " + e.getMessage());
		}
	}	
	
	/* Questo metodo stampa un report sui modelli contenuti nell'array passato.
	 * In pratica, per ogni modello, dice se è definito oppure non all'interno di EnumAuto. */
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
