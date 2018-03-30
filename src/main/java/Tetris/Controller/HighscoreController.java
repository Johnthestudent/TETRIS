package Tetris.Controller;

import java.util.Collections;
import java.util.Comparator;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import Tetris.DataRepository;
import Tetris.Difficulty;
import Tetris.Model.HighscoreElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * A {@code HighscoreController} osztálya, mely az eredmény táblázat megjelenítését vezérli.
 */
public class HighscoreController
{
	/**
	* A létszámkorlát a ponttáblázatban: {@value}.
	*/
	private final int maxHighScoreSize=10;	//max ennyien lehetnek a táblában
	
	@FXML 
	private TableView mytable;	//a tábla
		
	@FXML
	private TableColumn player;		//a játékos neve
	
	@FXML
	private TableColumn score;		//a játékos pontszáma
	
	@FXML
	private TableColumn achieved;	//mikor érte el a játékos a pontszámot
	
	@FXML
	private TableColumn difficulty;	//milyen nehézségen játszott a játékos
	
	@FXML
	private Button newGameButton;	//visszatérés a játékba
	
	@FXML
	private RadioButton easybutton;
	
	@FXML
	private RadioButton normalbutton;
	
	@FXML
	private RadioButton hardbutton;
	
	/**
	 * hány pontot ért el a játékos és mikor
	 * 
	 * @see HighscoreElement
	 */
	private HighscoreElement currentAchievement;
	
	public HighscoreController(HighscoreElement currentAchievement)
	{		
		this.currentAchievement = currentAchievement;
	}
	
public void initialize()
{
	if(this.currentAchievement.getDifficulty()==Difficulty.EASY)
		easybutton.setSelected(true);
	else if(this.currentAchievement.getDifficulty()==Difficulty.NORMAL)
		normalbutton.setSelected(true);
	else
			hardbutton.setSelected(true);
}
	
	/**
	 * Visszaadja a játékos aktuális eredményét, ami tartalmazni fogja a játékos
	 * nevét, pontszámát, és hogy mikor érte el azt a pontszámot.
	 * 
	 * @return az aktuális eredmény
	 * @see HighscoreElement
	 */
	public HighscoreElement getCurrentAchievement() 
	{
		return currentAchievement;
	}

	/**
	 * Beállítja a játékos aktuális eredményét, ami tartalmazni fogja a játékos
	 * nevét, pontszámát, és hogy mikor érte el azt a pontszámot.
	 * 
	 * @param currentAchievement az aktuális eredmény
	 * @see HighscoreElement
	 */
	public void setCurrentAchievement(HighscoreElement currentAchievement) 
	{
		this.currentAchievement = currentAchievement;
	}
	
	/**
	 * 
	 * @param event Javafx esemény
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
					
			//lekérem az fxml-hez tartozó már példányosított kontrollert
			//Tetriscontroller controller = loader.<Tetriscontroller>getController();
			Tetriscontroller controller = new Tetriscontroller(this.currentAchievement.getDifficulty());
		    
			loader.setController(controller);
			
			//fxmlloader példányosítja a kontrollert
			Parent root = (Parent) loader.load();
			controller.setPlayername(currentAchievement.getPlayername());
						
			
			//a régi nehézségi szint megmarad
			controller.setGameDifficulty(currentAchievement.getDifficulty());
			
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
	 * Metódus, melynek paramétere a nehézségi szint, kimenete egy highscore fájl.
	 * Pontosabban az adott nehézségi szinthez tartozó adatokat tartalmazó highscore fájl
	 * a kimenete.
	 */
	private String getHighscoreFile(Difficulty gameDifficulty)
	{
		if(gameDifficulty == Difficulty.EASY)
		{
			return "highscore_easy.xml";
		}
		else if(gameDifficulty == Difficulty.NORMAL)
		{
			return "highscore_normal.xml";
		}
		else
		{
			return "highscore_hard.xml";
		}
	}
	
	/**
	 * A rádiógombok közötti váltásokat ellenőrző metódus. Ha pl. könnyű módban játszott a játékos,
	 * és meg akarja tekinteni a normál játék módban játszott játékosok eredményeit, akkor a
	 * nehézségi szinthez tartozó rádiógombra kattintva csak a normál játék módban játszott játékosok
	 * eredményeit látja.
	 * 
	 * @param event rádiógomb váltás/kattintás, mint esemény
	 */
	@FXML
	private void RadioChanged(ActionEvent event)
	{
		if(event.getSource().equals(easybutton))
			refreshTable(Difficulty.EASY, false);
		else if(event.getSource().equals(normalbutton))
			refreshTable(Difficulty.NORMAL, false);
		else
			refreshTable(Difficulty.HARD, false);
	}
	
	/**
	 * A játékosok eredményeit tartalmazó tábla frissítése az aktuális elemmel. A játékos
	 * akkor kerülhet fel a táblára, ha nullánál nagyobb pontszámot ért el. Ha a tábla még nincs
	 * tele, és nullánál nagyobb pontot ért el, mindenképpen felkerül, egyébként csak ha nagyobb
	 * vagy egyenlő pontot ért el, mint a táblában lévő legkisebb pont. 
	 */
	
	/**
	 * Táblázatmódosító metódus. Ezt a game over esemény fogja hívni. Itt mivel igaz a kezdeti
	 * frissítés érték, ezért ki van kényszerítve az adott nehézségi mód esetén a táblázat frissítése.
	 * 	 
	 */
	public void refreshTable()
	{
		refreshTable(this.currentAchievement.getDifficulty(), true);
	}
	
	/**
	 * Táblázatmódosító metódus. Abban tér a fentebb lévőtől, hogy ezt a rádiógomb megváltozása
	 * fogja hívni.
	 * 
	 * @param d a nehézségi szint
	 * @param initialRefresh ki van-e kényszerítve a táblázat frissítése
	 */
	public void refreshTable(Difficulty d, boolean initialRefresh)
	{
		Vector<HighscoreElement> table = DataRepository.getHighscore(getHighscoreFile(d));	//ebben még vektor van
		
			boolean canAdd=false;
			for(HighscoreElement e:table)
			{
					canAdd=true;	//hozzáadható a táblához
					break;	
			}
			
			// Ha még nem telt meg a highsore, belekerülhet
			if(table.size()<maxHighScoreSize)
				canAdd=true;
			
			if(canAdd==true && initialRefresh)	//hozzáadja a táblához, mint új sort
			{
				table.add(this.currentAchievement);				
			}
			
			//tábla rendezése
			Collections.sort(table, 
					//a rendezésnél a -1-gyel való szorzás megfordítja a sorrendet
	                (o1, o2) ->
	                {
	                	//ha esetleg mindkét játékosnak ugyanannyi pontja van
	                	//akkor dátum alapján
	                	if(o1.getPlayerscore()==o2.getPlayerscore())
	                	{
	                		return -1*(o1.getAchieved().compareTo(o2.getAchieved()));
	                	}
	                	else
	                	{
	                	return -1 * Integer.compare(o1.getPlayerscore(), o2.getPlayerscore());
	                	}
	                });
			// highscoretable sort
			
			// az utolsó méret - kívánt méret elemet kivágom
			for(int i = 0; i < table.size() - maxHighScoreSize; i++)
			{
				table.remove(table.size() -1);
			}
			try
			{
				if(initialRefresh)
					DataRepository.saveHighscore(table, getHighscoreFile(this.currentAchievement.getDifficulty())); 	//elmentem az állást
			}
			catch (Exception x)
			{}	
		
		ObservableList<HighscoreElement> data = FXCollections.observableArrayList(
				table);	//átalakítja a vektort observablelist-té
		player.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,String>("playername")
	        );	//megmondom, hogy a player oszlop a highscorelement objektum sztring típusú
		//playername adattagjára köt
		
		score.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,Integer>("playerscore")
	        );	//a kicsi intet a generikus nem tudja kezelni
		
		achieved.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,LocalDate>("achieved")
	        );	//a highscorelement objektum Date típusú achieved adattagjára köt
			//az achieved nevű oszlop
		
		difficulty.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,Integer>("difficulty")
	        );	//a kicsi intet a generikus nem tudja kezelni
		
		mytable.setItems(data);	//az adatok betöltése
	
	}
}