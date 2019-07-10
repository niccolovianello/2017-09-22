package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model = new Model();
	
	FormulaOneDAO dao = new FormulaOneDAO();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Season> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader --> P

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader --> T

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	txtResult.clear();
    	
    	Season season = boxAnno.getValue();
    	model.creaGrafo(season);
    	
    	for(Adiacenza a : model.getAdiacenzaPesoMax()) {
    		txtResult.appendText(a.getR1().getName() + " - " + a.getR2().getName() + " con peso " + a.getPeso() + "\n");
    	}
    	
    	boxGara.getItems().addAll(dao.getAllRacesBySeason(season));
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	
    	String inputP = textInputK.getText().trim();
    	String inputT = textInputK1.getText().trim();
    	
    	try {
    		model.simula(boxGara.getValue(), boxAnno.getValue(), Double.parseDouble(inputP), Integer.parseInt(inputT));
    	}
    	catch(NumberFormatException nfe) {
    		txtResult.appendText("Immettere in input dei valori validi (per il campo P è richiesto un valore decimale compreso tra 0 e 1, mentre per il campo T è richiesto un valore intero maggiore di 0.)");
    	}
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
    }

	public void setModel(Model model) {
		
		this.model = model;
		boxAnno.getItems().addAll(dao.getAllSeasons());
	}
}
