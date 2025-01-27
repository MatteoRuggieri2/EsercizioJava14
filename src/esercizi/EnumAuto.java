package esercizi;

public enum EnumAuto {
	STELLANTIS("TIPO", "PANDA", "500"),
	AUDI("A4"),
	TESLA("T1"),
	VW,
	CITROEN;
	
	private String[] arrModello;
	
	private EnumAuto() {
		arrModello = new String[0]; // Creo un array lungo 0
	}
	
	private EnumAuto(String ... modello) {
		this.arrModello = modello;
	}

	public int getAutoNumber() {
		return this.arrModello.length;
	}
	
	public static int autoProduced(String marca) {
		return EnumAuto.valueOf(marca.toUpperCase()).getAutoNumber();
	}
	
	/* Questo metodo ritorna un valore booleano se trova il modello
	 * passato come parametro all'interno dell'enumerazione corrente.
	 * es. Se passo "TIPO" e l'enum corrente è "STELLANTIS" tornerà "true" */
	public boolean isModelDefined(String model) {
		
		for (String modello : arrModello) {
			if (modello.equals(model)) {
				return true;
			}
		}
		
		return false;
	}

}
