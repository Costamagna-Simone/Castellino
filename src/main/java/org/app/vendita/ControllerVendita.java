package org.app.vendita;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.app.App;
import org.app.Controller;
import org.app.model.DataModel;
import org.app.model.Fattura;
import org.app.model.Vendita;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.app.utilities.Constants.*;

public class ControllerVendita implements Controller {
    private final String PATH_FATTURE = "tmp/fattureVendita.csv";
    private DataModel dataModel;
    @Override
    public void init(DataModel dataModel) {
        this.dataModel = dataModel;
    }


    /********************
     Utility
     ********************/
    //leggi fatture da file
    private void leggiFattureDaFile()  {
        boolean fileCorretto = true;
        String stato = "File vuoto";

        ArrayList<Fattura> fatture = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(PATH_FATTURE));
            String line = reader.readLine();

            for(int i=0; i<INIZIO_FATTURE_VENDITA-2 && line!=null; i++)    {
                line = reader.readLine();
            }

            line = reader.readLine();
            int num = INIZIO_FATTURE_VENDITA;
            while (line != null && fileCorretto) {
              Vendita v = new Vendita();

              stato = v.setCampi(reader, line, num);

              if(stato=="OK")   {
                  fatture.add(v);
              } else {
                  fileCorretto = false;
              }

              num++;
              line = reader.readLine();
            }

            reader.close();

            if(stato.equals("OK"))   {
                //TODO dialog lettura avvenuta correttamente
                dataModel.aggiungiFatture(VENDITA, fatture);
            } else {
                //TODO dialog errore durante la lettur
            }

        } catch (IOException e) {
            //TODO non esiste il file
        }
    }



    /********************
     FXML
     ********************/
    //apri il file chooser per la scelta del file
    public void fxmlImporta()   {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));

        File file = fileChooser.showOpenDialog(dataModel.getStage());

        Workbook workbook = new Workbook();
        workbook.loadFromFile(file.getAbsolutePath());

        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.saveToFile(PATH_FATTURE, "^", StandardCharsets.UTF_8);

        leggiFattureDaFile();
    }

    //Torna alla home
    public void fxmlRitornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}