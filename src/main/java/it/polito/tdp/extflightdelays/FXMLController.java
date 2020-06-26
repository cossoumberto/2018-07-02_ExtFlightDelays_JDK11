package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.AirportDistanza;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	try {
    		Double distanza = Double.parseDouble(distanzaMinima.getText());
    		model.creaGrafo(distanza);
    		cmbBoxAeroportoPartenza.getItems().clear();
    		cmbBoxAeroportoPartenza.getItems().addAll(model.getGrafo().vertexSet());
    		txtResult.setText("Grafo creato con " + model.getGrafo().vertexSet().size() + " vertici e " +
    				model.getGrafo().edgeSet().size() + " archi");
    	} catch (NumberFormatException e){
    		e.printStackTrace();
    		txtResult.setText("Inserita distanza non valida");
    	}
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	if(cmbBoxAeroportoPartenza.getValue()!=null) {
    		txtResult.clear();
    		for(AirportDistanza ad : model.getDistanzeAirport(cmbBoxAeroportoPartenza.getValue()))
    			txtResult.appendText(ad+"\n");
    	} else {
    		txtResult.setText("Seleziona Airport");
    	}
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	if(model.getGrafo()!=null || cmbBoxAeroportoPartenza.getValue()!=null) {
	    	try {
	    		Double disponibili= Double.parseDouble(numeroVoliTxtInput.getText());
	    		txtResult.clear();
	    		for(Airport a : model.viaggio(cmbBoxAeroportoPartenza.getValue(), disponibili))
	    			txtResult.appendText(a+"\n");
	    		txtResult.appendText("Sono state percorse " + model.getMigliaPercorse() + " miglia");
	    	} catch (NumberFormatException e){
	    		e.printStackTrace();
	    		txtResult.setText("Inserimento miglia non valido");
	    	}
   		} else {
   			txtResult.setText("Crea grafo e seleziona un aereoporto");
   		}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
