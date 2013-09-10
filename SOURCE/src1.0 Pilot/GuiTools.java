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
import java.net.URI;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;

/**
 * GuiTools: Class used to obtain application environment information
 * 
 * @author Gannon McGibbon 
 * @version 1.0 
 * 
 * Date Created: 06/13/13
 * Last Updated: 06/24/13
 */
public class GuiTools
{
	//initialize d to current desktop object
	protected static Desktop d= Desktop.getDesktop();
	
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
			d.browse(URI.create(link));
		}
		catch(Exception e)
		{
			//if an error occurs, display error
			System.err.println(e.getMessage());
		}
	}
}
