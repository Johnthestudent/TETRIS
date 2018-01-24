package Tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Window extends Application
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			//a névkérő ablak megjelenítését leíró fxml beolvasása
			Parent root = FXMLLoader.load(getClass().getResource("/NameTaker.fxml"));
			Scene scene = new Scene(root, 400, 300);	//ablak mérete 
			primaryStage.setScene(scene);	//jelenet beállítása
			primaryStage.setTitle("TETRIS"); 	//ablak címe
			primaryStage.show();	//jelenítse meg
		} 
		catch(Exception e) 
		{
			e.printStackTrace();	//ha valami hiba történik
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);	//alkalmazás elindítása
	}
}
