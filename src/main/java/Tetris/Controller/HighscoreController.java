package Tetris.Controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import Tetris.DataRepository;
import Tetris.Model.HighscoreElement;
import Tetris.Model.HighscoreModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * A {@code HighscoreController} osztálya, mely ezeket tartalmazza: létszámkorlát, tábla, játékos
 * neve/pontszáma/mikor érte el a pontszámot, játékos eredményének visszaadása/beállítása, tábla
 * frissítése.
 */
public class HighscoreController extends DataRepository
{
	/**
	* A létszámkorlát a ponttáblázatban: {@value}.
	*/
	private final int maxHighScoreSize=30;	//max ennyien lehetnek a táblában
	
	@FXML 
	private TableView mytable;	//a tábla
		
	@FXML
	private TableColumn player;		//a játékos neve
	
	@FXML
	private TableColumn score;		//a játékos pontszáma
	
	@FXML
	private TableColumn achieved;	//mikor érte el a játékos a pontszámot
	
	/**
	 * hány pontot ért el a játékos és mikor
	 * 
	 * @see HighscoreElement
	 */
	private HighscoreElement currentAchievement;
	
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
	 * A játékosok eredményeit tartalmazó tábla frissítése az aktuális elemmel. A játékos
	 * akkor kerülhet fel a táblára, ha nullánál nagyobb pontszámot ért el. Ha a tábla még nincs
	 * tele, és nullánál nagyobb pontot ért el, mindenképpen felkerül, egyébként csak ha nagyobb
	 * vagy egyenlő pontot ért el, mint a táblában lévő legkisebb pont. 
	 */
	public void refreshTable()
	{
		HighscoreModel table = DataRepository.getHighscore();	//ebben még vektor van
		
		if(this.currentAchievement.getPlayerscore()>0)
		{
			// meg kell nézni, hogy currentAchievement-ben lévő pontok alapján 
			// beszúrható-e a highscore-ba
			// akkor kerülhet bele, ha nagyobb vagy egyenlő pontot ért el, mint a listában 
			// valamelyik elem
			boolean canAdd=false;
			for(HighscoreElement e:table.getHighscoreTable())
			{
				if(this.currentAchievement.getPlayerscore()>=e.getPlayerscore())
				{
					canAdd=true;	//hozzáadható a táblához
					break;
				}
					
			}
			
			// Ha még nem telt meg a highsore, belekerülhet
			if(table.getHighscoreTable().size()<maxHighScoreSize)
				canAdd=true;
			
			if(canAdd==true)	//hozzáadja a táblához, mint új sort
			{
				table.getHighscoreTable().add(this.currentAchievement);				
			}
			
			//tábla rendezése
			Collections.sort(table.getHighscoreTable(), 
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
			for(int i = 0; i < table.getHighscoreTable().size() - maxHighScoreSize; i++)
			{
				table.getHighscoreTable().remove(table.getHighscoreTable().size() -1);
			}
			try
			{
				DataRepository.saveHighscore(table); 	//elmentem az állást
			}
			catch (Exception x)
			{}
			
		}		
		
		ObservableList<HighscoreElement> data = FXCollections.observableArrayList(
				table.getHighscoreTable());	//átalakítja a vektort observablelist-té
		player.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,String>("playername")
	        );	//megmondom, hogy a player oszlop a highscorelement objektum sztring típusú
		//playername adattagjára köt
		
		score.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,Integer>("playerscore")
	        );	//a kicsi intet a generikus nem tudja kezelni
		
		achieved.setCellValueFactory(
	            new PropertyValueFactory<HighscoreElement,Date>("achieved")
	        );	//a highscorelement objektum Date típusú achieved adattagjára köt
			//az achieved nevű oszlop
		
		mytable.setItems(data);	//az adatok betöltése
	}
}