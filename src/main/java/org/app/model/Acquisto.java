package org.app.model;

import java.util.Date;

import static org.app.utilities.Constants.ACQUISTO;

public class Acquisto extends Fattura {
    private String numeroRif;

    private Date dataRif;

    private String fornitore;
    public Acquisto()   {
        super(ACQUISTO);
    }
}
