package org.app.model;

import static org.app.utilities.Constants.ACQUISTO;

public class Acquisto extends Fattura {
    private String fornitore;
    public Acquisto()   {
        super(ACQUISTO);
    }
}
