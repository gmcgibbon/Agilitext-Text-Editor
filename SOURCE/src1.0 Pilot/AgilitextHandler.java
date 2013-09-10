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

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JOptionPane;
/**
 * AgilitextHandler: Handles events triggered by application GUI
 * 
 * @author Gannon McGibbon 
 * @version 1.0 
 * 
 * Date Created: 06/06/13
 * Last Updated: 06/24/13
 */
public class AgilitextHandler extends WindowAdapter implements ActionListener, MenuListener
{
	//GUI object
    private AgilitextGui gui;
    
    /**
     * AgilitextHandler: Constructor
     * @param gui: GUI object used to listen for events
     */
    public AgilitextHandler(AgilitextGui gui)
    {
        //set gui to passed gui
        this.gui=gui;
    }
	
    /**
     * actionPerformed: Called to handle GUI action events
     * @param e: GUI action event 
     */
	public void actionPerformed(ActionEvent e)
    {
		//if event source is saveButton or saveItem
        if(e.getSource()==gui.saveButton||e.getSource()==gui.saveItem)
        {
        	//call text area save file method
            gui.textArea.saveFile();
        }
        //if event source is saveAsButton or saveAsItem
        else if(e.getSource()==gui.saveAsButton||e.getSource()==gui.saveAsItem)
        {
        	//call text area select save file method
        	gui.textArea.selectSaveFile();
        }
        //if event source is openButton or openItem
        else if(e.getSource()==gui.openButton||e.getSource()==gui.openItem)
        {
        	//call text area open file method
        	gui.textArea.openFile();
        }
        //if event source is newItem
        else if(e.getSource()==gui.newItem)
        {
        	//call text area new text method
        	gui.textArea.newText();
        }
        //if event source is cutItem
        else if(e.getSource()==gui.cutItem)
        {
        	//call text area cut method
        	gui.textArea.cut();
        }
        //if event source is copyItem
        else if(e.getSource()==gui.copyItem)
        {
        	//call text area copy method
        	gui.textArea.copy();
        }
        //if event source is pasteItem
        else if(e.getSource()==gui.pasteItem)
        {
        	//call text area paste method
        	gui.textArea.paste();
        }
        //if event source is deleteItem
        else if(e.getSource()==gui.deleteItem)
        {
        	//call text area deleteSelected method
        	gui.textArea.deleteSelected();
        }
        //if event source is selectAllItem
        else if(e.getSource()==gui.selectAllItem)
        {
        	//highlight all text in text area
        	gui.textArea.selectAll();
        }
        //if event source is revertItem
        else if(e.getSource()==gui.revertItem)
        {
        	//call text area revertText method
        	gui.textArea.revertText();
        }
        //if event source is statsItem
        else if(e.getSource()==gui.statsItem)
        {
        	//call text area getStats method
        	gui.textArea.getStats();
        }
        //if event source is aboutItem
        else if(e.getSource()==gui.aboutItem)
        {
        	//display about application information
        	JOptionPane.showMessageDialog(null, "Author: Gannon McGibbon\nVersion: 1.0 \nDate: June 2013", "About", JOptionPane.PLAIN_MESSAGE);
        }
        //if event source is siteItem
        else if(e.getSource()==gui.siteItem)
        {
        	//open site in default browser
        	GuiTools.browseToLink("http://www.jukesfordays.com");
        }
    }
	
	/**
	 * windowOpened: Called to handle GUI window opening events
	 * @param e: GUI window event
	 */
	public void windowOpened(WindowEvent e)
	{
		//unused
	}
	
	/**
	 * windowClosing: Called to handle GUI window closing events
	 * @param e: GUI window event
	 */
	public void windowClosing(WindowEvent e)
	{
		//if event source is an instance of AgilitextClient
		if(e.getSource() instanceof AgilitextClient)
		{
			//run text check on window close
			gui.textArea.textCheck();
		}
	}
	
	/**
	 * menuSelected: Called to handle GUI menu selection events
	 * @param e: GUI menu event
	 */
	public void menuSelected(MenuEvent e)
	{
		//unused
    }
	
	/**
	 * menuDeselected: Called to handle GUI menu deselection events
	 * @param e: GUI menu event
	 */
    public void menuDeselected(MenuEvent e)
    {
    	//unused
    }
    
    /**
	 * menuCancelled: Called to handle GUI menu canceled events
	 * @param e: GUI menu event
	 */
    public void menuCanceled(MenuEvent e)
    {
        //unused
    }
}
