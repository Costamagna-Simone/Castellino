package org.app.acquisto;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.app.App;
import org.app.Controller;
import org.app.caricamentoFile.ControllerCaricamento;
import org.app.filtro.ControllerFiltro;
import org.app.model.Acquisto;
import org.app.model.DataModel;
import org.app.model.Fattura;
import org.app.modificaFattura.ControllerModificaFattura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.app.utilities.Constants.INIZIALE;
import static org.app.utilities.Constants.INIZIO_FATTURE_ACQUISTO;

public class ControllerAcquisto implements Controller {

    private final String PATH_FATTURE = "tmp/fattureAcquisto.csv";

    private DataModel dataModel;

    private Fattura fatturaCorrente;

    private ControllerCaricamento controllerCaricamento;
    private Stage stageCaricamento;

    private ControllerFiltro controllerFiltro;
    private Stage stageFiltro;
    private MultipleSelectionModel<Fattura> fattureSelezionate;

    private ControllerModificaFattura controllerModificaFattura;
    private Stage stageModificaFatture;

    public void init(DataModel dataModel)  {
        this.dataModel = dataModel;
        tabella();
        tabella.refresh();
    }

    /********************
     Initialize
     ********************/
    private void tabella()  {
        tabella.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        dataCaricamento.setCellValueFactory(new PropertyValueFactory<>("dataCaricamento"));
        dataCaricamento.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Fattura, Timestamp> call(TableColumn<Fattura, Timestamp> column) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Timestamp item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            setText(dateFormat.format(item));
                        }
                    }
                };
            }
        });

        tabella.setItems(dataModel.getFatture());

        fattureSelezionate = tabella.getSelectionModel();
    }

    //inizializza il dialog caricamento utente
    private void dialogCaricamento()  {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("dialogCaricamento.fxml"));
            Parent parent = loaderReceived.load();
            controllerCaricamento = loaderReceived.getController();
            controllerCaricamento.init(dataModel);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dialogCaricamento.css")).toExternalForm());

            stageCaricamento = new Stage();
            stageCaricamento.setScene(scene);
            stageCaricamento.setResizable(false);
            stageCaricamento.setTitle("Importa file");

            stageCaricamento.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    //inizializza filtro
    private void filtro()   {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("filtro.fxml"));
            Parent parent = loaderReceived.load();
            controllerFiltro = loaderReceived.getController();
            controllerFiltro.init(dataModel);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/filtro.css")).toExternalForm());

            stageFiltro = new Stage();
            stageFiltro.setScene(scene);
            stageFiltro.setResizable(false);
            stageFiltro.setTitle("Filtro");

            stageFiltro.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //inizializza modifica fatture
    private void modificaFatture()   {
        try {
            FXMLLoader loaderReceived = new FXMLLoader(App.class.getResource("dialogModificaFattura.fxml"));
            Parent parent = loaderReceived.load();
            controllerModificaFattura = loaderReceived.getController();
            controllerModificaFattura.init(dataModel);

            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/modificaFattura.css")).toExternalForm());

            stageModificaFatture = new Stage();
            stageModificaFatture.setScene(scene);
            stageModificaFatture.setResizable(false);
            stageModificaFatture.setTitle("Modifica fattura");

            stageModificaFatture.initModality(Modality.APPLICATION_MODAL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /********************
     Utility
     ********************/
    //aggiorna i dati all'apertura della finestra
    public void aggiorna()  {
        nomeCognome.setText(dataModel.getUtenteCorrente().getNome() + " " +
                dataModel.getUtenteCorrente().getCognome());
    }

    //leggi fatture da file
    private void leggiFattureDaFile()  {
        boolean fileCorretto = true;
        String stato = "File vuoto";
        BufferedReader reader;

        ArrayList<Fattura> fatture = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(PATH_FATTURE));
            String line = reader.readLine();

            for(int i=0; i<INIZIO_FATTURE_ACQUISTO-2 && line!=null; i++)    {
                line = reader.readLine();
            }

            int numItem = 0;
            while (reader.readLine() != null) {
                numItem++;
            }

            reader = new BufferedReader(new FileReader(PATH_FATTURE));
            line = reader.readLine();

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
                    fileCorretto = false;
                }

                num++;
                controllerCaricamento.setProgressSpinner(num, numItem);

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                line = reader.readLine();
            }

            reader.close();

            if(stato.equals("OK"))   {
                controllerCaricamento.setProgressSpinner(num, num);
                controllerCaricamento.setFatture(fatture);
                controllerCaricamento.setDisableImporta(false);
            } else {
                controllerCaricamento.setErrore(stato);
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

            dialogCaricamento();
            controllerCaricamento.setNomeFile(file.getName());
            stageCaricamento.show();

            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.submit(() -> {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                leggiFattureDaFile();
            });
            executor.shutdown();
        }
    }


    /********************
     FXML
     ********************/

    //Torna alla home
    public void ritornaHome(MouseEvent mouseEvent) {
        try {
            App.setRoot(INIZIALE, "iniziale");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //apri filtro
    public void apriFiltro()    {
        if(stageFiltro==null)  {
            filtro();
        }
        stageFiltro.show();
    }

    //elimina fatture
    public void eliminaFatture()    {
        ObservableList<Fattura> selectedFatture = fattureSelezionate.getSelectedItems();
        ArrayList<Fattura> fatture = new ArrayList<>(selectedFatture);

        dataModel.eliminaFatture(fatture);
    }

    //apri modifica fattura
    public void selezionaFattura(MouseEvent mouseEvent) {
        Node source = mouseEvent.getPickResult().getIntersectedNode();

        //spostati attraverso la gerarchia node fino a quando incontri TableRow o la scena radice
        while (source != null && !(source instanceof TableRow)) {
            source = source.getParent();
        }

        if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
            //se cliccato al di fuori della tabella o su una riga vuota, deseleziona il cantiere corrente
            tabella.getSelectionModel().select(null);
            fatturaCorrente = null;
        } else {
            Fattura f = fattureSelezionate.getSelectedItem();
            if(f != null) {
                if(f == fatturaCorrente)    {
                    if(stageModificaFatture==null)  {
                        modificaFatture();
                    }
                    stageModificaFatture.show();
                } else {
                    fatturaCorrente = f;
                }
            }
        }
    }

    @FXML
    private Text nomeCognome;

    @FXML
    private TableColumn<Fattura, String> commessa;

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

    @FXML
    private TableColumn<Fattura, Timestamp> dataCaricamento;
}