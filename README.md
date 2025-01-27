# Enum

## EnumCheckDaFile

Creare la classe `EnumCheckDaFile`, definire un file di testo `auto-brands.txt` e l'enum `EnumAuto`.
Per questo esercizio i test JUnit **non sono previsti**.

### Output

Segnalare, tra le marche presenti nel file, quali sono **definite** (presenti all’interno dell’enum), **quali non lo sono**, e **quante auto producono**.
_Per ogni marca definita, verificare anche quali sono i modelli prodotti definiti e quali non lo sono._

> **NOTA**: Il nome del costruttore all'interno del file, può non essere a inizio della riga.

#### Risorse

> Enum documentation: <https://dev.java/learn/classes-objects/enums/>

#### Files

**auto-brands.txt**

```txt
BMW
STELLANTIS TIPO PANDA 500
AUDI A4 A5 A6
VW
CITROEN
SKODA FABIA
PORSCHE GTRS3 CAYENNE TAYCAN
```

**EnumAuto.java**

```java
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
```
