package Tetris.Controller;

import java.awt.Graphics2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.beans.property.*;

import javafx.beans.value.ObservableValue;

import java.util.Timer;
import java.util.TimerTask;

import Tetris.Difficulty;
import Tetris.Game;
import Tetris.GameEventListener;
import Tetris.Matrix;
import Tetris.MatrixHelper;
import Tetris.Model.HighscoreElement;
import Tetris.Shapes.Board;

//ez a code behind-ja az fxml-nek
//fxml leírja a megjelenítést
//a code behind pedig a működést

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.*;

import org.pmw.tinylog.Logger; 


/**
 * A {@code Tetriscontroller} osztálya, mely a játék megjelenítését vezérli.
 */
public class Tetriscontroller implements GameEventListener
{
	/**
	* A pálya szélessége: {@value}.
	*/
	private final int tetrisWidth = 12;
	
	/**
	* A pálya magassága: {@value}.
	*/
	private final int tetrisHeight = 20;
	
	/**
	* A pályán belül egy négyzet mérete pixelben: {@value}.
	*/
	private final int squareSize = 15;
	
	@FXML
    private Canvas tetrisboard;	//játék pályája
    
	@FXML
    private Label pointslabel;	//pontszám
    
    @FXML
    private Label namelabel;	//játékosnév
    
    @FXML
    private Button newgame;		//új játék gomb
    
    /**
     * Az aktuális {@link Tetris.Game}.
     * 
     * @see Game
     */
    protected Game currentGame;
    
    /**
     * Mikor futott le a timer utoljára sikeresen.
     * 
     * @see System.nanoTime()
     */
    private long lastTime = System.nanoTime();
    
    private int speed;	//játék sebesség
    
    /**
     * Időzítő, mely nanoszekundum pontossággal minden egyes képkockára fut. Minden egyes 
     * eltelt másodperc után lejjebb visszi az aktuális alakzatot, és újrarajzolja a pályát.
     * 
     * @see AnimationTimer
     */
    private AnimationTimer timer = new AnimationTimer()
    {
        @Override
        public void handle(long l) 
        {
        	if(l - lastTime > 1000 * 1000 * speed)
        	{        		
        		lastTime = l;
        		
        		if(!currentGame.getPaused())
				{
					timer.stop();
					currentGame.moveShapeDown();
					redrawCanvas();
					timer.start();
				}
        	}
        }

    };
	
    /**
     * A gombra kattintás, mint esemény kezelése: új játék indítása
     * 
     * @param event a gombra kattintás esemény
     */
    @FXML
	private void handleButtonAction(ActionEvent event) 
	{
		timer.stop();
		currentGame.resetGame();
		this.redrawCanvas();
		timer.start();
		Logger.trace("New Game gomb megnyomva!");
    };
    
    /**
     * A billentyűlenyomás, mint esemény kezelése. Az A billentyű jelenti a játékban a balra
     * forgatást, a D a jobbra forgatást. Jobbra a jobb nyíl billentyűvel, balra
     * a bal nyíl billentyűvel, lefele pedig a le nyíl billentyűvel lehet menni.
     * A játékot leállítani a P billentyűvel lehet.
     * 
     * @param event a billentyűlenyomás, mint esemény
     */
    @FXML
    private void keyListener(KeyEvent event)
    {

        switch(event.getCode())	//megkapom az adott billentyűnek a kódját, és azt tudom 
        //konstansokkal hasonlítani
		{
		case A:
			if(!this.currentGame.getPaused())
			{
				this.currentGame.rotateShapeLeft();	//A billentyűvel balra forgatok
				Logger.trace("A billentyű megnyomva!");
			}
			break;
		case D:
			if(!this.currentGame.getPaused())
			{
				this.currentGame.rotateShapeRight(); //D billentyűvel jobbra forgatok
				Logger.trace("D billentyű megnyomva!");
			}
			break;
		case LEFT:
			if(!this.currentGame.getPaused()) //bal nyíl billentyűvel balra mozgatok
			{
				this.currentGame.moveShapeLeft();
				Logger.trace("bal nyíl billentyű megnyomva!");
			}
			break;
		case RIGHT:
			if(!this.currentGame.getPaused())
			{
				this.currentGame.moveShapeRight(); //jobb nyíl billentyűvel jobbra mozgatok
				Logger.trace("jobb nyíl billentyű megnyomva!");
			}
			break;
		case DOWN:
			if(!this.currentGame.getPaused())
			{
				timer.stop();
				this.currentGame.moveShapeDown(); //le nyíl billentyűvel lefele mozgatok
				timer.start();
				Logger.trace("le nyíl billentyű megnyomva!");
			}
			break;
		case P:
			this.currentGame.togglePause();	//P billentyűvel leállítom a játékot
			Logger.trace("P billentyű megnyomva!");
			break;
		case T:
			if(!this.currentGame.getPaused())
			{
				this.currentGame.mirrorShape(); //T billentyűvel tükrözök
				Logger.trace("T billentyű megnyomva!");
			}
			break;
		case L:
			if(!this.currentGame.getPaused())
			{
				timer.stop();
				this.currentGame.dropShape();
				timer.start();
			}
		}
		this.redrawCanvas(); //kényszerítem az ablakot, hogy újrarajzolja magát
		event.consume();	//ne kezelje más eseménykezelő ezt az eseményt,
		//mert már le van kezelve
    };
    
    /**
     * A {@link Tetris.Matrix} kirajzolása az ablakra.
     * 
     * @param matrix a kirajzolandó mátrix
     * @param g2 {@link GraphicsContext}
     * 
     * @see GraphicsContext
     * @see Matrix
     */
  	private void drawShape(Matrix matrix, GraphicsContext g2)
  	{
  		drawShape(matrix, 0, 0, g2);	
  	}
  	
    /**
     * A {@link Tetris.Matrix} kirajzolása az ablakra, eltolással.
     * 
     * @param matrix a kirajzolandó mátrix
     * @param x az x szerinti eltolás mértéke (vízszintes)
     * @param y az y szerinti eltolás mértéke (függőleges)
     * @param g2 {@link GraphicsContext}
     * 
     * @see GraphicsContext
     * @see Matrix
     */
  	private void drawShape(Matrix matrix, int x, int y, GraphicsContext g2)
  	  {  		  
  		  for(int i = 0; i < MatrixHelper.getRowNum(matrix.getMatrix()); i++)
  		  {
  			  	for(int j = 0; j < MatrixHelper.getColNum(matrix.getMatrix()); j++)
  				{
  			  		if(matrix.getMatrix()[i][j] != 0)
  			  		{
  			  			//kirajzolom a mátrix elemeit
  			  			//i és j, hogy hol vagyok a shape mátrixban + eltolás
  			  			g2.setFill(getColor(matrix.getMatrix()[i][j]));	//a mátrix adott helyén lévő 
  			  			//érték alapján szinezek
  			  			g2.fillRect((j + x) * squareSize, (i + y) * squareSize, 
  			  					squareSize, squareSize);
  			  		}	
  				}	
  		  }
  	  }
  	
  	/**
  	 * {@code int} paraméterhez adott színt rendel. 
  	 * 
  	 * @param n a {@code int} amihez színt kell rendelni
  	 * @return az {@link Color} szín
  	 * @see Color
  	 */
  	private Color getColor(int n)	//milyen színű lesz a négyzet
	{
		switch(n)
		{
		case 1:
			return Color.CYAN;	//I alakzat szine
		case 2:
			return Color.YELLOW;	//J alakzat szine
		case 3:
			return Color.GREEN;		//L alakzat szine
		case 4:
			return Color.ORANGE;	//O alakzat szine
		case 5:
			return Color.MAGENTA;	//S alakzat szine
		case 6:
			return Color.BROWN;	//Z alakzat szine
		case 8:
			return Color.BLUE;	//U alakzat szine
		case 9:
			return Color.PURPLE;	//Cross alakzat szine
		default:	
			return Color.RED;	//T alakzat szine
			
		}
	}

  	/**
  	 * Az {@code Canvas} újrarajzolása. 
  	 * 
  	 * @see Canvas
  	 */
  	private void redrawCanvas()
  	{
  		GraphicsContext gc = tetrisboard.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0,tetrisboard.getWidth(),tetrisboard.getHeight());
		drawShape(currentGame.getBoard(), gc);
	    drawShape(currentGame.getCurrentshape(), currentGame.getCurrentshape().getPosition().getX(),
	    		currentGame.getCurrentshape().getPosition().getY(), gc);
  	}

  	/**
  	 * A {@link Tetris.Game} inicializálása: Game over eseményre feliratkozás, pálya elkészítése, 
  	 * kötések beállítása, időzítő indítása.
  	 */
	public void initialize() 
	{
		this.currentGame.addListener(this); //feliratkozás az eseményre
		tetrisboard.setWidth(tetrisWidth * squareSize);
		tetrisboard.setHeight(tetrisHeight * squareSize);
		pointslabel.textProperty().bind(currentGame.getPoints().asString());
		namelabel.textProperty().bind(currentGame.getPlayername());
		redrawCanvas();	//hogy rögtön látható legyen a pálya
		this.timer.start();
		newgame.setFocusTraversable(false);
		tetrisboard.setFocusTraversable(true);
		this.setSpeed();	//játék nehézségtől függően állítódik a sebesség át
	}
	
	/**
	 * A játék sebességének beállítása. Könnyű játékmódban egy másodperc/lépés a sebesség.
	 */
	private void setSpeed()
	{
		switch(this.currentGame.getGameDifficulty())
		{
			case EASY:
				this.speed = 1000;	//1 másodperc
				break;
			case NORMAL:
				this.speed = 700;
				break;
			default:
				this.speed = 400;
				break;
		}
	}
	
	/**
	 * Konstruktor: Új játék készítése.
	 */
	public Tetriscontroller(Difficulty difficulty)
	{
		this.currentGame = new Game(tetrisWidth, tetrisHeight, difficulty);	//megcsinálom a játékot
		Logger.info("Új játék készül!");
	}

	/**
	 * A game over, mint játékesemény kezelése, a ponttáblázat megjelenítése.
	 */
	@Override
	public void gameOver(String player, int points, Difficulty difficulty) 
	{
		// a fő szálban (GUI) akarok futtatni valamit
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
        		timer.stop();	//megáll az időzítő
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Score.fxml"));
        		try 
        		{
        			HighscoreController controller = new HighscoreController(new HighscoreElement(player,points, LocalDateTime.now(), difficulty));
        			fxmlLoader.setController(controller);
					Parent root = (Parent) fxmlLoader.load();
				
					controller.refreshTable();
					Scene scene = new Scene(root, 600, 500);
					
					//a vászon ugyanúgy viselkedik, mint bármely más node
					Stage stage = (Stage) tetrisboard.getScene().getWindow();
					
					stage.setScene(scene);
					stage.show();
					Logger.info("Ennyi pontom van: ");
					Logger.info(points);
        		} 
        		catch (IOException e) 
        		{
					e.printStackTrace();
				}
        		
        		
        		}
          });
	}

	/**
	 * Elkéri az aktuális {@code Tetris.Game} játékosának nevét.
	 * 
	 * @return az aktuális játék játékosának neve
	 */
	public SimpleStringProperty getPlayername() 
	{
		return this.currentGame.getPlayername();
	}
	
	/**
	 * Beállítja az aktuális {@code Tetris.Game} játékosának nevét
	 * 
	 * @param playername az aktuális játék játékosának neve
	 */
	public void setPlayername(String playername) 
	{
		this.currentGame.setPlayername(playername);
	}
	
	/**
	 * Beállítja az aktuális {@code Tetris.Game} játék nehézségi szintjét
	 * 
	 * @param gameDifficulty a játék nehézségi szintje
	 */
	public void setGameDifficulty(Difficulty gameDifficulty) 
	{
		this.currentGame.setGameDifficulty(gameDifficulty);
		this.setSpeed();
	}
}