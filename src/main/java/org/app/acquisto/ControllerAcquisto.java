package org.app.acquisto;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.app.App;
import org.app.Controller;
import org.app.model.Acquisto;
import org.app.model.DataModel;
import org.app.model.Fattura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;

import static org.app.utilities.Constants.INIZIALE;
import static org.app.utilities.Constants.INIZIO_FATTURE_ACQUISTO;

public class ControllerAcquisto implements Controller {

    private final String PATH_FATTURE = "tmp/fattureAcquisto.csv";

    private DataModel dataModel;

    public void init(DataModel dataModel)  {
        this.dataModel = dataModel;
        tabella();
        tabella.refresh();
    }

    /********************
     Initialize
     ********************/
    private void tabella()  {
        anno.setCellValueFactory(new PropertyValueFactory<>("anno"));
        bollo.setCellValueFactory(new PropertyValueFactory<>("bollo"));
        cassaPrevidenza.setCellValueFactory(new PropertyValueFactory<>("cassaPrevidenza"));
        fornitore.setCellValueFactory(new PropertyValueFactory<>("fornitore"));
        codiceFiscale.setCellValueFactory(new PropertyValueFactory<>("codiceFiscale"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        imponibile.setCellValueFactory(new PropertyValueFactory<>("imponibile"));
        importoArt15.setCellValueFactory(new PropertyValueFactory<>("importoArt15"));
        imposta.setCellValueFactory(new PropertyValueFactory<>("imposta"));
        nettoAPagare.setCellValueFactory(new PropertyValueFactory<>("nettoAPagare"));
        notePiede.setCellValueFactory(new PropertyValueFactory<>("notePiede"));
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        partitaIva.setCellValueFactory(new PropertyValueFactory<>("partitaIva"));
        ritenuta.setCellValueFactory(new PropertyValueFactory<>("ritenuta"));
        stato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        suffisso.setCellValueFactory(new PropertyValueFactory<>("suffisso"));
        tipoCassaPrevidenza.setCellValueFactory(new PropertyValueFactory<>("tipoCassaPrevidenza"));
        tipoDocumento.setCellValueFactory(new PropertyValueFactory<>("tipoDocumento"));
        totale.setCellValueFactory(new PropertyValueFactory<>("totale"));
        dataRif.setCellValueFactory(new PropertyValueFactory<>("dataRif"));
        numeroRif.setCellValueFactory(new PropertyValueFactory<>("numeroRif"));

        tabella.setItems(dataModel.getFatture());

    }

    /********************
     Utility
     ********************/
    //leggi fatture da file
    private void leggiFattureDaFile()  {
        System.out.println("Entrato");
        boolean fileCorretto = true;
        String stato = "File vuoto";

        ArrayList<Fattura> fatture = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(PATH_FATTURE));
            String line = reader.readLine();

            for(int i=0; i<INIZIO_FATTURE_ACQUISTO-2 && line!=null; i++)    {
                line = reader.readLine();
            }

            line = reader.readLine();
            int num = INIZIO_FATTURE_ACQUISTO;
            while (line != null && fileCorretto) {
                Acquisto a = new Acquisto();
                a.setUtente(dataModel.getUtenteCorrente().getId());
                stato = a.setCampi(reader, line, num);

                if(stato.equals("OK"))   {
                    fatture.add(a);
                } else {
                    System.out.println("STATO: " + stato);
                    fileCorretto = false;
                }

                num++;
                line = reader.readLine();
            }

            reader.close();

            if(stato.equals("OK"))   {
                //TODO dialog lettura avvenuta correttamente
                dataModel.aggiungiFatture(fatture);
            } else {
                System.out.println("Errore: " + stato);
                //TODO dialog errore durante la lettur
            }

            tabella.refresh();
        } catch (IOException e) {
            //TODO non esiste il file
        }
    }


    /********************
     FXML
     ********************/
    //apri il file chooser per la scelta del file
    public void importa()   {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));

        File file = fileChooser.showOpenDialog(dataModel.getStage());

        if(file != null)    {
            Workbook workbook = new Workbook();
            workbook.loadFromFile(file.getAbsolutePath());

            Worksheet sheet = workbook.getWorksheets().get(0);
            sheet.saveToFile(PATH_FATTURE, "^", StandardCharsets.UTF_8);

            leggiFattureDaFile();
        }
    }


    /********************
     FXML
     ********************/

    //Torna alla home
    public void fxmlRitornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TableColumn<Fattura, Integer> anno;

    @FXML
    private TableColumn<Fattura, Double> bollo;

    @FXML
    private TableColumn<Fattura, Double> cassaPrevidenza;

    @FXML
    private TableColumn<Fattura, String> codiceFiscale;

    @FXML
    private TableColumn<Fattura, Date> data;

    @FXML
    private TableColumn<Fattura, Double> imponibile;

    @FXML
    private TableColumn<Fattura, Double> importoArt15;

    @FXML
    private TableColumn<Fattura, Double> imposta;

    @FXML
    private TableColumn<Fattura, Double> nettoAPagare;

    @FXML
    private TableColumn<Fattura, String> notePiede;

    @FXML
    private TableColumn<Fattura, Integer> numero;

    @FXML
    private TableColumn<Fattura, String> partitaIva;

    @FXML
    private TableColumn<Fattura, String> ritenuta;

    @FXML
    private TableColumn<Fattura, String> stato;

    @FXML
    private TableColumn<Fattura, String> suffisso;

    @FXML
    private TableView<Fattura> tabella;

    @FXML
    private TableColumn<Fattura, String> tipoCassaPrevidenza;

    @FXML
    private TableColumn<Fattura, String> tipoDocumento;

    @FXML
    private TableColumn<Fattura, String> numeroRif;

    @FXML
    private TableColumn<Fattura, Date> dataRif;

    @FXML
    private TableColumn<Fattura, String> fornitore;

    @FXML
    private TableColumn<Fattura, Double> totale;
}