package esercizi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


// FUNZIONA MA SUDDIVIDI IN METODI PER RIORDINARE
public class EnumCheckDaFile {

	private String fileName = "src/text_files/auto-brands.txt";
	private String undefinedBrands = "";
	private String definedBrands = "";

	public static void main(String[] args) {
		EnumCheckDaFile ecdf = new EnumCheckDaFile();
		ecdf.run();
		
	}
	
	/*TODO -> MODIFICHE DA FARE:
	 *  - Aggiungere un metodo che trovi all'interno della riga il brand*/
	
	//TODO -> Modifica il file README.md (Al primo paragrafo vai a capo, e poi modifica il file di testo)
	
	private void run() {
		
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
		
		try {
			checkFile(bufferedReader);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Si è verificato un errore durante la lettura del file: " + e.getMessage());
		}
		
	}

	
	private void checkFile(BufferedReader br) throws IOException {
		// Leggo la prima riga del file
		String currentFileLine = br.readLine();
		String[] currentLineWords;
		String currentLineBrand;
		
		while (currentFileLine != null) {
			currentLineWords = currentFileLine.strip().split(" +");   // .strip() toglie gli spazi davanti e dietro
			currentLineBrand = currentLineWords[0]; //TODO -> Da correggere, il brand non è sempre al primo posto
			
			/* Verifico se il brand è incluso nell'enum, in caso positivo
			 * lo salvo in "definedBrands", altrimenti in "undefinedBrands" */
			if (checkBrandInEnum(currentLineBrand)) {
				saveToDefinedBrands(currentLineBrand);
				
			} else {
				saveToUndefinedBrands(currentLineBrand);
				// Salto alla prossima riga
				currentFileLine = br.readLine();
				continue;
			}
			
			// Stampo il report dei modelli (definiti e non definiti)
			checkModelli(currentLineBrand, currentLineWords);
			
			// Leggo la prossima riga
			currentFileLine = br.readLine();
		}
		
		br.close();
		
		//TODO -> Stampo i brand definiti e indefiniti
		System.out.println("Brand definiti:\n" + definedBrands); // con "\n" posso andare a capo nel print
		System.out.println("Brand indefiniti:\n" + undefinedBrands);
	}
	
	/* Questo metodo controlla che il brand sia incluso nell'enumerazione "EnumAuto" */
	private boolean checkBrandInEnum(String brand) {
		for (EnumAuto enumBrand : EnumAuto.values()) {
			if (enumBrand.name().equals(brand)) {   // Con .name() prendo il nome dell'enumerazione
				return true;
			}
		}
		
		return false;
	}
	
	// Questo metodo concatena il brand alla stringa "definedBrands"
	private void saveToDefinedBrands(String brand) {
		definedBrands += "- " + brand + " (Auto prodotte: " + EnumAuto.autoProduced(brand) + ")\n";
	}
	
	// Questo metodo concatena il brand alla stringa "undefinedBrands"
	private void saveToUndefinedBrands(String brand) {
		undefinedBrands += "- " + brand + "\n";
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
