package org.app.vendita;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.app.App;
import org.app.Controller;
import org.app.model.DataModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.app.utilities.Constants.INIZIALE;

public class ControllerVendita implements Controller {
    private final String PATH_FATTURE = "tmp/fattureVendita.csv";
    private DataModel dataModel;
    @Override
    public void init(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    // --- Utility ---

    private void utilityLeggiFatture()  {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(PATH_FATTURE));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            //TODO non esiste il file
        }
    }



    // --- FXML ---
    public void fxmlImporta()   {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));

        File file = fileChooser.showOpenDialog(dataModel.getStage());

        Workbook workbook = new Workbook();
        workbook.loadFromFile(file.getAbsolutePath());

        Worksheet sheet = workbook.getWorksheets().get(0);
        sheet.saveToFile(PATH_FATTURE, ",", StandardCharsets.UTF_8);

        utilityLeggiFatture();
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