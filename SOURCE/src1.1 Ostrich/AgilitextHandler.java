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

/**
 * AgilitextHandler: Handles events triggered by application GUI
 * 
 * @author Gannon McGibbon 
 * @version 1.1
 * 
 * Date Created: 06/06/13
 * Last Updated: 08/18/13
 */
public class AgilitextHandler extends WindowAdapter implements FocusListener, ActionListener,  ListSelectionListener
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
        //if event source is undoItem
        else if(e.getSource()==gui.undoItem)
        {
        	//call undo action
        	gui.textArea.doUndo();
        }
        //if event source is undoItem
        else if(e.getSource()==gui.redoItem)
        {
        	//call redo action
        	gui.textArea.doRedo();
        }
        //if event source is findReplaceItem
        else if(e.getSource()==gui.findReplaceItem)
        {
        	//set findMan frame to visible
        	gui.findMan.setVisible(true);
        }
        //if event source is fontItem
        else if(e.getSource()==gui.fontItem)
        {
        	//set fontMan frame to visible
        	gui.fontMan.setVisible(true);
        }
        //if event source is dateItem
        else if(e.getSource()==gui.dateItem)
        {
        	//call date insert on text area
        	gui.textArea.insertDate();
        }
        //if event source is timeItem
        else if(e.getSource()==gui.timeItem)
        {
        	//call time insert on text area
        	gui.textArea.insertTime();
        }
        
        //if event source is welcomeItem
        else if(e.getSource()==gui.welcomeItem)
        {
        	//display welcome message
        	Appsistant.getWelcomeMessage();
        }
        //if event source is aboutItem
        else if(e.getSource()==gui.aboutItem)
        {
        	//display about application information
        	Appsistant.getAppInfo();
        }
        //if event source is siteItem
        else if(e.getSource()==gui.siteItem)
        {
        	//open site in default browser
        	Appsistant.browseToLink("http://jukesfordays.com");
        }
        
        //if event source is findButton
        else if(e.getSource()==gui.findMan.findButton)
        {
        	//
        	gui.findMan.findText();
        }
        //if event source is replaceButton
        else if(e.getSource()==gui.findMan.replaceButton)
        {
        	gui.findMan.replaceText();
        }
        //if event source is replaceAllButton
        else if(e.getSource()==gui.findMan.replaceAllButton)
        {
        	gui.findMan.replaceAll();
        }
        //if event source is countButton
        else if(e.getSource()==gui.findMan.countButton)
        {
        	gui.findMan.count();
        }
        
        //if event source is resetButton
        else if(e.getSource()==gui.fontMan.resetButton)
        {
        	//reset target font to default
        	gui.fontMan.resetTargetFont();
        }
        //if event source is confirmButton
        else if(e.getSource()==gui.fontMan.confirmButton)
        {
        	//set target font to font specified by fontMan components
        	gui.fontMan.setTargetFont();
        }
        //if event source is cancelButton
        else if(e.getSource()==gui.fontMan.cancelButton)
        {
        	//set fontMan to invisible
        	gui.fontMan.setVisible(false);
        }
    }
	
	/**
     * focusGained: Called to handle GUI focus gained events
     * @param e: GUI focus event 
     */
	public void focusGained(FocusEvent e)
	{
		//if even source is an instance of AgilitextTextField
		if(e.getSource() instanceof AgilitextTextField)
		{
			//cast source to AgilitextTextField and select all text inside component
			((AgilitextTextField)(e.getSource())).selectAll();
		}
	}
	
	/**
     * focusLost: Called to handle GUI focus lost events
     * @param e: GUI focus event 
     */
	public void focusLost(FocusEvent e)
	{
		//if event source is sizeField
		if(e.getSource()==gui.fontMan.sizeField)
		{
			//attempt to parse valid number from field
			gui.fontMan.sizeField.getNumber(1, 125);
		}
	}
	
	/**
	 * windowOpened: Called to handle GUI window opening events
	 * @param e: GUI window event
	 */
	public void windowOpened(WindowEvent e)
	{
		//if event source is an instance of AgilitextClient
		if(e.getSource() instanceof AgilitextClient)
		{
			//if run count is not valid
			if(Appsistant.RUN_COUNT==1)
			{
				//display welcome message
				Appsistant.getWelcomeMessage();
			}
			
			//update run count, adding one on each startup
			Appsistant.pref.putInt(Appsistant.RUN_COUNT_KEY, Appsistant.pref.getInt(Appsistant.RUN_COUNT_KEY, 1)+1);
			
			//if last text is valid
			if(Appsistant.LAST_TEXT!=null)
			{
				//set textArea text to last text
				gui.textArea.setText(Appsistant.LAST_TEXT);
			}
			
			//set fontMan to load last used font
			gui.fontMan.setLastFont();
		}
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
			//if textArea contains no valid text
			if(gui.textArea.getText().trim().isEmpty())
			{
				//remove last text key from preferences
				Appsistant.pref.remove(Appsistant.LAST_TEXT_KEY);
			}
			else
			{
				//store textArea text as last text
				Appsistant.pref.put(Appsistant.LAST_TEXT_KEY,gui.textArea.getText());
			}
		}
	}
    
	/**
	 * valueChanged: Called to handle GUI value changing events
	 * @param e: GUI list selection event
	 */
    public void valueChanged(ListSelectionEvent e)
    {
    	//if event source is fontList
    	if(e.getSource()==gui.fontMan.fontList)
    	{
    		//set selected font to display on sample text field
    		gui.fontMan.setSampleFont();
    	}
    }
}
