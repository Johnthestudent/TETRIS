package Tetris.Controller;

import java.io.IOException;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import org.pmw.tinylog.Logger;

import Tetris.Difficulty; 

/**
 * A {@code NameController} osztálya, mely ezeket tartalmazza: játék indítása, játékos neve,
 * {@link Tetris.Game} kezdése esemény kezelése, tulajdonságok kötésének beállítása.
 */
public class NameController
{
	@FXML
	private Button startgame;	//játék indítása
	
	@FXML
	private TextField name;		//a játékos neve
	
    @FXML
    private RadioButton easybutton;
    
    @FXML
    private RadioButton normalbutton;
    
    @FXML
    private RadioButton hardbutton;
	
	/**
	 * A {@link Tetris.Game} kezdése, mint esemény kezelése. 
	 * 
	 * @param event a játékbeli esemény
	 * @throws IOException ha nem tudja betölteni az fxml fájlt vagy nem tudja
	 * megjeleníteni a jelenetet
	 */
	@FXML
	private void startingGameAction(ActionEvent event) throws IOException
	{
		try 
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
							  "/Tetris.fxml"));
					
			//fxmlloader példányosítja a kontrollert
			Parent root = (Parent) loader.load();
					
			//lekérem az fxml-hez tartozó már példányosított kontrollert
			Tetriscontroller controller = loader.<Tetriscontroller>getController();
			controller.setPlayername(this.name.getText());
			if(easybutton.isSelected())
			{
				controller.setGameDifficulty(Difficulty.EASY);
			}
			else if(normalbutton.isSelected())
			{
				controller.setGameDifficulty(Difficulty.NORMAL);
			}
			else
			{
				controller.setGameDifficulty(Difficulty.HARD);
			}
			
			Scene scene = new Scene(root, 400, 300);	//ablak mérete 
			Button source = (Button) event.getSource();	//kattintásra lép a másik ablakba
			
			//ez ugyanaz, mint a window-nak a parent stage-e
			Stage stage = (Stage) source.getScene().getWindow();	//betölti a másik ablakot
			
			stage.setScene(scene);
			stage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Inicializálás: A játékot indító gomb csak akkor aktiválódik, hogy ha meg 
	 * van adva játékosnév.
	 * 
	 * @see BooleanBinding
	 */
	public void initialize()
	{
		//egyik tulajdonság függ a másiktól
		BooleanBinding bb = new BooleanBinding() 
		{
		    {
		        super.bind(name.textProperty());
		    }

		    @Override
		    protected boolean computeValue() 
		    {
		        return (name.getText().isEmpty());	//üres-e a játékos név sztring
		    }
		};
		
		startgame.disableProperty().bind(bb);	//komponens adott tulajdonsága "bindolódik"

	}
}
