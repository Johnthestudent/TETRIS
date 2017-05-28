package Tetris;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import Tetris.Model.HighscoreElement;
import Tetris.Model.HighscoreModel;

/**
 * A ponttáblázat mentése és betöltése.
 */
public class DataRepository 
{
	/**
	 * Az alkalmazott dátum formátum.
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * Az XML dokumentum fájlneve.
	 */
	private static String filename = "highscore.xml";
	
	/**
	 * Egy játékos adatait tartalmazó XML elem.
	 */
	private static String playerNodeName = "player";
	
	/**
	 * A játékos neve, mint attribútum az XML-ben.
	 */
	private static String nameAttrName = "playername";
	
	/**
	 * A játékos pontszáma, mint attribútum az XML-ben.
	 */
	private static String scoreAttrName = "playerscore";
	
	/**
	 * A dátum, mint attribútum az XML-ben.
	 */
	private static String achieveAttrName = "achieved";
	
	/**
	 * A ponttáblázat beolvasása XML dokumentumból.
	 * 
	 * @return {@link HighscoreModel} a ponttáblázat model
	 * @see HighscoreModel
	 */
	public static HighscoreModel getHighscore()
	{
		HighscoreModel x = new HighscoreModel();  
		try
		{
			//documentbuilderfactory
			DocumentBuilderFactory dbFactory = 
				DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			File input = new File(filename);
			Document doc = dBuilder.parse(input);	//xml tartalom dom object lesz
			
			doc.normalize();
			
			NodeList nodeList = doc.getElementsByTagName(playerNodeName);
			for(int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);	//i. elem
				
				Element element = (Element)  node;
				
				String playername = element.getAttribute(nameAttrName);
				//vissza kell alakítani az elmentett sztringet
				int playerscore = Integer.parseInt(element.getAttribute(scoreAttrName));
				
				Date achieved = df.parse(element.getAttribute(achieveAttrName));
			
				//ezen változókból egy új HighscoreElement objektum készül
				HighscoreElement he = new HighscoreElement(playername, playerscore, achieved);
				
				x.getHighscoreTable().add(he);	//visszatöltés után visszakerül a táblába
			}
		}
		catch(Exception e)
		{
			x = new HighscoreModel();
			System.out.println(e.getMessage());
		}
		return x;
	}

	/**
	 * A ponttáblázat mentése XML dokumentumba. 
	 * 
	 * @param m {@code HighscoreModel} a ponttáblázat amit menteni szeretnék
	 * @throws Exception ha az írás sikertelen
	 * 
	 * @see HighscoreModel
	 * @see <a href="https://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer">
	 * https://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer</a>
	 */
	public static void saveHighscore(HighscoreModel m) throws Exception
	{
		//documentbuilderfactory
		DocumentBuilderFactory dbFactory = 
			DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();	//ez egy üres xml dokumentum
		
		Element gyokerelem = doc.createElement("highscoretable");
		
		doc.appendChild(gyokerelem);	//elem hozzáfűzése a dokumentumhoz
		
		//a ponttáblázat elemeinek száma nem fix, for each kell a bejáráshoz
		for(HighscoreElement e : m.getHighscoreTable())
		{
			Element player = doc.createElement(playerNodeName);
			Attr playername = doc.createAttribute(nameAttrName);
			playername.setValue(e.getPlayername());
			player.setAttributeNode(playername);
			
			Attr playerscore = doc.createAttribute(scoreAttrName);
			playerscore.setValue(Integer.toString(e.getPlayerscore()));
			player.setAttributeNode(playerscore);

			Attr achieved = doc.createAttribute(achieveAttrName);
			achieved.setValue(df.format(e.getAchieved()));
			player.setAttributeNode(achieved);
			
			gyokerelem.appendChild(player);
		}
		
		//xml-be író átalakító
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");	//indentálás engedélyezése
		//két space/mélység
		//xml namespace
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		//objektum forrás
		DOMSource source = new DOMSource(doc);
		
		//az eredmény xml fájl
		StreamResult result = new StreamResult(new File(filename));
		
		//mit mire alakítson át
		transformer.transform(source, result);
	}
}