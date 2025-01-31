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
	
	private String[] autoBrands = {
			
			"Bmw",
			"Dacia",
			"Mercedes",
			"Audi",
			"Porsche",
			"Volkswagen",
			"Stellantis",
			"Tesla",
			"Citroen",
			"Skoda",
			"Kia",
			"Toyota",
			"Ford"
	};

	
	
	public static void main(String[] args) {
		EnumCheckDaFile ecdf = new EnumCheckDaFile();
		ecdf.run();
		
	}
	
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
	
	// Questo metodo principale controlla il contenuto del file e stampa il report.
	private void checkFile(BufferedReader br) throws IOException {
		// Leggo la prima riga del file
		String currentFileLine = br.readLine();
		String[] currentLineWords;
		String currentLineBrand;
		String[] currentLineBrandModels;
		
		while (currentFileLine != null) {
			currentLineWords = currentFileLine.strip().split(" +");   // .strip() toglie gli spazi davanti e dietro
			currentLineBrand = findBrandInTheLine(currentLineWords);
			
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
			
			// Prendo i modelli nella riga
			currentLineBrandModels = removeBrandFromCurrentLineWords(currentLineBrand, currentLineWords);

			// Stampo il report dei modelli (definiti e non definiti)
			reportModelli(currentLineBrand, currentLineBrandModels);
			
			// Leggo la prossima riga
			currentFileLine = br.readLine();
		}
		
		br.close();
		brandsReport();
	}
	
	/* Questo metodo trova all'interno di un array di stringhe il Brand,
	 * utilizzando come ricerca un array contenente molti brand */
	private String findBrandInTheLine(String[] lineWords) {
		for (String word : lineWords) {
			for (String autoBrand : autoBrands) {
				if (word.equalsIgnoreCase(autoBrand)) {
					return word;
				}
			}
		}
		
		return null;
	}
	
	// Questo metodo rimuove il brand da "currentLineWords" per ottenere un array di soli modelli
	private String[] removeBrandFromCurrentLineWords(String brand, String[] currentLineWords) {
		int arrSize = currentLineWords.length;
		String autoModels[] = new String[arrSize - 1];
		
		for (int i = 0; i < currentLineWords.length; i++) {
			String lineWord = currentLineWords[i];
			
			// Se la parola è diversa dal brand vuoi dire che è un modello, quindi lo salvo
			if (!lineWord.equalsIgnoreCase(brand)) {
				
				// Salvo il modello nel primo posto libero dell'array "autoModels"
				for (int j = 0; j < autoModels.length; j++) {
					String model = autoModels[j];
					if (model == null) {
						autoModels[j] = lineWord;
						break;
					}
					
				}
			}
			
		}
		
		return autoModels;
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
	
	// Questo metodo concatena il brand alla stringa "unDdefinedBrands"
	private void saveToUndefinedBrands(String brand) {
		undefinedBrands += "- " + brand + "\n";
	}
	
	/* Questo metodo stampa un report sui modelli contenuti nell'array passato.
	 * In pratica, per ogni modello, dice se è definito oppure non all'interno di EnumAuto. */
	private void reportModelli(String brand, String[] lineModels) {
		EnumAuto enumBrand = EnumAuto.valueOf(brand);
		String singleModel = "";
		
		for (int i = 0; i < lineModels.length; i++) {
			singleModel = lineModels[i];
			if (enumBrand.isModelDefined(singleModel)) {
				System.out.println("Il modello " + singleModel + " del brand " + brand + " è definito");
			} else {
				System.out.println("Il modello " + singleModel + " del brand " + brand + " non è definito");
			}
		}
	}
	
	/* Questo metodo stampa il report dei brand, quelli definiti e quelli non definiti */
	private void brandsReport() {
		System.out.println("Brand definiti:\n" + definedBrands);
		System.out.println("Brand indefiniti:\n" + undefinedBrands);
	}

}
