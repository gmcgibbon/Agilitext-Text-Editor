/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright Gannon McGibbon 2013
 * 
 */

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.util.prefs.Preferences;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.URI;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Appsistant: Obtains and displays application and environment information, 
 * store and receives preferences, and gets system fonts
 * 
 * @author Gannon McGibbon
 * @version 1.1
 * 
 * Date Created: 06/13/13
 * Last Updated: 08/18/13
 */
public class Appsistant
{
	
	//initialize desktop to current desktop object
	protected static final Desktop desktop= Desktop.getDesktop();
	//initialize pref to user root preferences
	protected static final Preferences pref= Preferences.userRoot();
	//initialize graphics to local graphics environment
	protected static final GraphicsEnvironment graphics=GraphicsEnvironment.getLocalGraphicsEnvironment();
	
	//initialize preference key Strings
	protected static final String RUN_COUNT_KEY="AgilitextRunCount";
	protected static final String LAST_FONT_SIZE_KEY="AgilitextFontSize";
	protected static final String LAST_FONT_NAME_KEY="AgilitextFontName";
	protected static final String LAST_TEXT_KEY="AgilitextLastText";
	
	//initialize loaded preferences
	protected static final int RUN_COUNT=pref.getInt(RUN_COUNT_KEY, 1);
	protected static final float LAST_FONT_SIZE=pref.getFloat(LAST_FONT_SIZE_KEY, -1);
	protected static final String LAST_FONT_NAME=pref.get(LAST_FONT_NAME_KEY, null);
	protected static final String LAST_TEXT=pref.get(LAST_TEXT_KEY, null);
	
	//initialize final application info Strings
	private static final String NAME="Agilitext Text Editor";
	private static final String DATE="August 2013";
	private static final String AUTHOR="Gannon McGibbon";
	private static final String VERSION="1.1";
	private static final String CRYPTONYM="Ostrich";
	private static final String LICENSE="GNU GPLv3";
	
	//initialize RUN_PATH to class path of Appsistant
	private static final String RUN_PATH=Appsistant.class.getResource("Appsistant.class").toString();
	//initialize inArchive to true if RUN_PATH contains an acceptable archive file extension, otherwise set to false
	private static final boolean inArchive=(RUN_PATH.contains(".jar")||RUN_PATH.contains(".exe"));
	//initialize system font array to graphics's fonts
	private static Font[] systemFonts=graphics.getAllFonts();
	//initialize date and time formats
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
	
	/**
	 * getWorkingDims: Used to return a Dimension object or available working space on desktop
	 * @return: Dimension object containing working dimensions
	 */
	public static Dimension getWorkingDims() 
	{
	    return new Dimension((GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width),(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height));
	}
	
	/**
	 * browseToLink: Used to browse to a web address using the default browser
	 * @param link: String containing web address, eg. "http://www.google.ca/"
	 */
	public static void browseToLink(String link)
	{
		try
		{
			//attempt to browse to passed link String
			desktop.browse(URI.create(link));
		}
		catch(Exception e)
		{
			//if an error occurs, print error and stack trace
			System.err.println("Failed to browse to specified URI");
			e.printStackTrace();
		}
	}
	
	/**
	 * getAppInfo: Used to display application information based on specified info Strings
	 */
	public static void getAppInfo()
	{
		//display a JOptionPane with application information
		JOptionPane.showMessageDialog(null, NAME+"\nAuthor: "+AUTHOR+"\nVersion: "+VERSION+" \""+CRYPTONYM+"\"\nDate: "+DATE+"\nLicense: "+LICENSE+"\nRun Count: "+RUN_COUNT, "About", JOptionPane.INFORMATION_MESSAGE); 
	}
	
	/**
	 * getWelcomeMessage: Used to display the application welcome message
	 */
	public static void getWelcomeMessage()
	{
		//display a JOptionPane with welcome message
		JOptionPane.showMessageDialog(null, "<html>Hello and welcome to <i>"+NAME+"!</i> <br>This is a <u>free</u> and <u>open source</u> text editing <br>solution made by "+AUTHOR+". If you <br>enjoy using this application, check out my <br>website for more of my work!</html>", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * getCurrentTime: Used to obtain current date String
	 * @return: Current date String
	 */
	public static String getCurrentDate()
	{
		return dateFormat.format(new Date());
	}
	
	/**
	 * getCurrentTime: Used to obtain current time String
	 * @return: Current time String
	 */
	public static String getCurrentTime()
	{
		return timeFormat.format(new Date());
	}
	
	/**
	 * getSystemFonts: Used to obtain an array of local graphics environment fonts
	 * @return: Font object array of all system fonts
	 */
	public static Font[] getSystemFonts()
	{
		return systemFonts;
	}
	
	/**
	 * registerFont: Used to register a Font object in local graphics environment
	 * @param f: Font object to be registered
	 */
	public static void registerFont(Font f)
	{
		//if passed Font is not null
		if(f!=null)
		{
			//register font object in local graphics environment
			graphics.registerFont(f);
			//update system fonts array
			systemFonts=graphics.getAllFonts();
		}
	}
	
	/**
	 * getJarFont: Loads a font file from main jar
	 * @param fileName: Specifies the name of the font file, eg. "font.ttf"
	 * @param size: Specifies a font size to be used
	 * @return A fully constructed Font object or null
	 */
	public static Font getJarFont(String fileName, float size)
	{
		//declare null Font object
		Font f=null;
		try
		{
			if(inArchive)
			{
				//declare input stream as resource from jar using passed filename
				InputStream stream = Appsistant.class.getResourceAsStream("/assets/"+fileName);
				//attempt to create a tuetype font using input stream
				f=Font.createFont(Font.TRUETYPE_FONT, stream);
				//set font size to passed size
				f=f.deriveFont(size);
			}
		}
		catch(Exception e)
		{
			//if an error occurs, print font error and stack trace
			System.err.println("Fontface failed to load!");
			e.printStackTrace();
		}
		return f;
	}
	
	/**
	 * getJarImage: Loads an image file from main jar
	 * @param fileName: Specifies the name of the image file, eg. "image.png"
	 * @return A fully constructed Font object or null
	 */
	public static Image getJarImage(String fileName)
	{
		//declare null Image and ImageIcon objects
		ImageIcon imageIcon=null;
		Image image=null;
		try
		{
			if(inArchive)
			{
				//declare input stream as resource from jar using passed filename
				InputStream stream = Appsistant.class.getResourceAsStream("/assets/"+fileName);
				//attempt to initialize ImageIcon using Input Stream
				imageIcon=new ImageIcon(ImageIO.read(stream));
				//set image to ImageIcon's image
				image=imageIcon.getImage();
			}
		}
		catch(Exception e)
		{
			//if an error occurs, print image error and stack trace
			System.err.println("Icon failed to load!");
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * isInArchive: Used to determine if application is running from an archive file
	 * @return: true if running from archive, false if not
	 */
	public static boolean isInArchive()
	{
		return inArchive;
	}
	
}
