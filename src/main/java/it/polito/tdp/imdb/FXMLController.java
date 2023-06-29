/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Movie;
import it.polito.tdp.imdb.model.MovieWeight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrandoMax"
    private Button btnGrandoMax; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="txtRank"
    private TextField txtRank; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMovie"
    private ComboBox<Movie> cmbMovie; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    @FXML
    void doCammino(ActionEvent event) {
    	
    	Movie mStart= this.cmbMovie.getValue(); 
    	
    	this.txtResult.appendText("\nRISULTATO RICORSIONE: \n "+  model.getPercorsoMovie(mStart));

    	

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	float f= Float.parseFloat(this.txtRank.getText());
    	model.creaGrafo(f);
    	
    	this.txtResult.appendText("\nGrafo creato con #vertici e #archi: "+model.getNumVerex()+" - "+model.getNumEdges());

    	List<Movie> temp=  model.getVertex(); 
    	Collections.sort(temp); 
    	this.cmbMovie.getItems().addAll(temp); 
    	
    }

    @FXML
    void doGradoMax(ActionEvent event) {
        
    	MovieWeight mw=model.getFilmGradoMax(); 
    	
    	
    	this.txtResult.appendText("\nVertice Grado max: "+mw.getMovieMax());
    	this.txtResult.appendText(" con peso: "+mw.getPesoTot());
  
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGrandoMax != null : "fx:id=\"btnGrandoMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRank != null : "fx:id=\"txtRank\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMovie != null : "fx:id=\"cmbMovie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
