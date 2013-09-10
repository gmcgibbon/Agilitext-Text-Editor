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

import java.awt.*;
import javax.swing.*;

/**
 * AgilitextClient: Class used to construct the frame to hold the main GUI
 * 
 * @author Gannon McGibbon 
 * @version 1.0 
 * 
 * Date Created: 06/06/13
 * Last Updated: 06/24/13
 */
public class AgilitextClient extends JFrame
{
	private static final long serialVersionUID = 1L;
	private AgilitextGui gui;
	private Container c;
	private Dimension workingDim=GuiTools.getWorkingDims();
	
	/**
	 * AgilitextClient: Constructor
	 */
	public AgilitextClient()
	{
		//assign container
	    c = getContentPane();
	    //initialize GUI
	    gui = new AgilitextGui();
	    //add GUI to container
	    c.add(gui);
	    //add window listener
	    addWindowListener(gui.getHandler());
	    //set menu
	    setJMenuBar(gui.getMenu());
	    
	    //add window dressing
	    setTitle("Agilitext Text Editor");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    //set size limitations
	    setMinimumSize(new Dimension(290, 120));
        setMaximumSize(workingDim);
        setSize(290,(int)(workingDim.getHeight()*0.3));
        
        try
        {
        	//attempt to set frame icon
        	setIconImage(gui.getJarImage("icon.png"));
        }
        catch(NullPointerException e)
        {
        	//if not found, continue using default one
        }
        
        //set frame to visible
	    setVisible(true);
	}
	
	/**
	 * main: Main method used to instantiate a new instance of the client GUI
	 * @param args: String of arguments passed on application launch
	 */
	public static void main(String[] args)
	{
	    new AgilitextClient();
	}
}
