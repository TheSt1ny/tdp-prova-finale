package it.polito.tdp.gestioneFlussiMigratori;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.gestioneFlussiMigratori.model.Country;
import it.polito.tdp.gestioneFlussiMigratori.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConferma1;

    @FXML
    private Button btnConferma2;

    @FXML
    private Button btnCreaMondo;

    @FXML
    private Button btnReset1;

    @FXML
    private Button btnReset2;

    @FXML
    private ComboBox<String> cmbEvento1;

    @FXML
    private ComboBox<String> cmbEvento2;

    @FXML
    private ComboBox<String> cmbEvento3;

    @FXML
    private ComboBox<Integer> cmbGravity1;

    @FXML
    private ComboBox<Integer> cmbGravity2;

    @FXML
    private ComboBox<Integer> cmbGravity3;

    @FXML
    private ComboBox<Country> cmbNazione1;

    @FXML
    private ComboBox<Country> cmbNazione2;

    @FXML
    private ComboBox<Country> cmbNazione3;

    @FXML
    private ComboBox<Country> cmbNazioneOspitante;

    @FXML
    private ImageView imgMondo;

    @FXML
    private ImageView imgNazioneOspitante;

    @FXML
    private Slider sliderApproccio;

    @FXML
    private TableColumn<Country, Integer> tableMorti;

    @FXML
    private TableColumn<Country, String> tableNazione;

    @FXML
    private TableView<Country> tableView;

    @FXML
    private TextArea txt1;

    @FXML
    private TextField txtMigrantiMorti;

    @FXML
    private TextField txtMigrantiNordAfrica1;

    @FXML
    private TextField txtMigrantiNordAfrica2;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaMondo(ActionEvent event) {
    	this.cmbNazione1.getItems().clear();
    	this.cmbNazione2.getItems().clear();
    	this.cmbNazione3.getItems().clear();
    	this.cmbEvento1.getItems().clear();
    	this.cmbEvento2.getItems().clear();
    	this.cmbEvento3.getItems().clear();
    	this.cmbGravity1.getItems().clear();
    	this.cmbGravity2.getItems().clear();
    	this.cmbGravity3.getItems().clear();
    	this.txt1.clear();
    	this.tableView.getItems().clear();
    	this.txtMigrantiMorti.clear();
    	this.txtMigrantiNordAfrica1.clear();
    	this.txtMigrantiNordAfrica2.clear();
    	this.cmbNazioneOspitante.getItems().clear();
    	this.txtResult.clear();
    	
    	this.model.creaGrafo();
    	File file = new File("img/Immagini tesi/mondo.png");
    	Image image = new Image(file.toURI().toString());
    	this.imgMondo.setImage(image);
    	
    	this.txt1.setText("Mondo Creato!\nOra scegli Nazioni ed Eventi!");
    	
    	List<Country> africanCountries = this.model.getAfricanCountries();
    	this.cmbNazione1.getItems().addAll(africanCountries);
    	this.cmbNazione2.getItems().addAll(africanCountries);
    	this.cmbNazione3.getItems().addAll(africanCountries);
    	List<String> eventi = Arrays.asList("Epidemia", "Guerra", "Carestia", "Disastro Ambientale", "Persecuzione", "Povertà");
    	this.cmbEvento1.getItems().addAll(eventi);
    	this.cmbEvento2.getItems().addAll(eventi);
    	this.cmbEvento3.getItems().addAll(eventi);
    	List<Integer> numeri = Arrays.asList(1,2,3);
    	this.cmbGravity1.getItems().addAll(numeri);
    	this.cmbGravity2.getItems().addAll(numeri);
    	this.cmbGravity3.getItems().addAll(numeri);
    }

    @FXML
    void onConferma1(ActionEvent event) {
    	Country c1 = this.cmbNazione1.getValue();
    	Country c2 = this.cmbNazione2.getValue();
    	Country c3 = this.cmbNazione3.getValue();
    	String e1 = this.cmbEvento1.getValue();
    	String e2 = this.cmbEvento2.getValue();
    	String e3 = this.cmbEvento3.getValue();
    	Integer g1 = this.cmbGravity1.getValue();
    	Integer g2 = this.cmbGravity2.getValue();
    	Integer g3 = this.cmbGravity3.getValue();
    	
    	if(c1 == null && c2 == null && c3 == null) {
    		this.txt1.setText("Devi selezionare almeno uno stato da cui far partire un evento ed impostare i parametri!");
    		return;
    	}
    	
    	this.model.Simula(c1, c2, c3, e1, e2, e3, g1, g2, g3);
    	this.txt1.setText("Simulazione avvenuta con successo!");
    	
    	ObservableList<Country> lista = FXCollections.observableArrayList();
    	for(Country c: this.model.getCountries()) {
    		lista.add(c);
    	}
    	this.tableView.setItems(lista);
    	
    	this.txtMigrantiMorti.setText(""+this.model.getNTotMorti());
    	this.txtMigrantiNordAfrica1.setText(""+this.model.getNNordAfrica());
    	this.txtMigrantiNordAfrica2.setText(""+this.model.getNNordAfrica());
    	
    	this.cmbNazioneOspitante.getItems().setAll(this.model.getEuropeanCountries());
    }

    @FXML
    void onConferma2(ActionEvent event) {
    	Double approccio = this.sliderApproccio.getValue();
    	Country c = this.cmbNazioneOspitante.getValue();
    	if(c==null) {
    		this.txtResult.setText("Devi prima scegliere una nazione!");
    		return;
    	}
    	
    	List<Integer> lista = this.model.gestisci(c, approccio, Integer.parseInt(this.txtMigrantiNordAfrica2.getText()));
    	this.txtResult.setText("Dati raccolti e stime: \n");
    	this.txtResult.appendText("Sono morti: " +lista.get(0)+ " migranti\n");
    	this.txtResult.appendText("Sono giunti: " +lista.get(1)+ " migranti\n");
    	this.txtResult.appendText("La spesa stimata ammonta a: " +lista.get(2)+ " euro\n");
    	this.txtResult.appendText("Rapporto tra popolazione e migranti attesi: 1 migrante ogni " +lista.get(3) + " abitanti");
    	
    	String uri = this.model.bandiera(c);
    	uri = "img/Immagini tesi/"+uri;
    	File file = new File(uri);
    	Image image = new Image(file.toURI().toString());
    	this.imgNazioneOspitante.setImage(image);
    }

    @FXML
    void onReset1(ActionEvent event) {
    	this.cmbNazione1.getItems().clear();
    	this.cmbNazione2.getItems().clear();
    	this.cmbNazione3.getItems().clear();
    	this.cmbEvento1.getItems().clear();
    	this.cmbEvento2.getItems().clear();
    	this.cmbEvento3.getItems().clear();
    	this.cmbGravity1.getItems().clear();
    	this.cmbGravity2.getItems().clear();
    	this.cmbGravity3.getItems().clear();
    	this.txt1.clear();
    	this.tableView.getItems().clear();
    	this.txtMigrantiMorti.clear();
    	this.txtMigrantiNordAfrica1.clear();
    	this.txtMigrantiNordAfrica2.clear();
    	this.cmbNazioneOspitante.getItems().clear();
    	this.txtResult.clear();
    	this.txt1.appendText("Benvenuto! Crea il Mondo!");
    	List<String> eventi = Arrays.asList("Epidemia", "Guerra", "Carestia", "Disastro Ambientale", "Persecuzione", "Povertà");
    	this.cmbEvento1.getItems().addAll(eventi);
    	this.cmbEvento2.getItems().addAll(eventi);
    	this.cmbEvento3.getItems().addAll(eventi);
    	List<Integer> numeri = Arrays.asList(1,2,3);
    	this.cmbGravity1.getItems().addAll(numeri);
    	this.cmbGravity2.getItems().addAll(numeri);
    	this.cmbGravity3.getItems().addAll(numeri);
    	List<Country> africanCountries = this.model.getAfricanCountries();
    	this.cmbNazione1.getItems().addAll(africanCountries);
    	this.cmbNazione2.getItems().addAll(africanCountries);
    	this.cmbNazione3.getItems().addAll(africanCountries);
    }

    @FXML
    void onReset2(ActionEvent event) {
    	this.cmbNazioneOspitante.getItems().clear();
    	this.sliderApproccio.setValue(5);
    	this.txtResult.clear();
    }

    @FXML
    void initialize() {
        assert btnConferma1 != null : "fx:id=\"btnConferma1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConferma2 != null : "fx:id=\"btnConferma2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaMondo != null : "fx:id=\"btnCreaMondo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset1 != null : "fx:id=\"btnReset1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset2 != null : "fx:id=\"btnReset2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbEvento1 != null : "fx:id=\"cmbEvento1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbEvento2 != null : "fx:id=\"cmbEvento2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbEvento3 != null : "fx:id=\"cmbEvento3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGravity1 != null : "fx:id=\"cmbGravity1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGravity2 != null : "fx:id=\"cmbGravity2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGravity3 != null : "fx:id=\"cmbGravity3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione1 != null : "fx:id=\"cmbNazione1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione2 != null : "fx:id=\"cmbNazione2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione3 != null : "fx:id=\"cmbNazione3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazioneOspitante != null : "fx:id=\"cmbNazioneOspitante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert imgMondo != null : "fx:id=\"imgMondo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert imgNazioneOspitante != null : "fx:id=\"imgNazioneOspitante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert sliderApproccio != null : "fx:id=\"sliderApproccio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tableMorti != null : "fx:id=\"tableMorti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tableNazione != null : "fx:id=\"tableNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txt1 != null : "fx:id=\"txt1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMigrantiMorti != null : "fx:id=\"txtMigrantiMorti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMigrantiNordAfrica1 != null : "fx:id=\"txtMigrantiNordAfrica1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMigrantiNordAfrica2 != null : "fx:id=\"txtMigrantiNordAfrica2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.tableNazione.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
        this.tableMorti.setCellValueFactory(new PropertyValueFactory<Country, Integer>("morti"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.txt1.appendText("Benvenuto! Crea il Mondo!");
    }

}
