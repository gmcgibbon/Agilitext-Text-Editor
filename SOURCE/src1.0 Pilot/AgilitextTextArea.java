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

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * AgilitextTextArea: Modified JTextArea that supports 
 * saving, loading, reverting, etc. with a different look
 * 
 * @author Gannon McGibbon 
 * @version 1.0 
 * 
 * Date Created: 06/20/13
 * Last Updated: 06/24/13
 */
public class AgilitextTextArea extends JTextArea
{
	//instance variables
	private String initialText;
	private StringBuilder stringBuilder;
	private JFileChooser fileChooser;
	private File targetFile;
	private FileReader fileIn;
	private FileWriter fileOut;
	private BufferedReader readIn;
	private BufferedWriter writeOut;
	private StringTokenizer tokenizer;
	private static final long serialVersionUID = 1L;
	
	/**
	 * AgilitextTextArea: Constructor
	 */
	public AgilitextTextArea()
	{
		//initialize objects to default values
		initialText="";
		targetFile=null;
		fileChooser=new JFileChooser();
	}
	
	/**
	 * paintComponent: Paints component using a color gradient
	 * @param grphcs: Graphics object
	 */
	protected void paintComponent(Graphics grphcs) {
		//cast Graphics object to a Graphics2D object
        Graphics2D g2d = (Graphics2D) grphcs;
        //set rendering settings for new Graphics2D object
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        //create new GradientPaint object with gradient options
        GradientPaint gp = new GradientPaint(0, 0,
                getBackground().brighter(), 0, getHeight(),
                getBackground().darker().darker());
        //set paint object to GradientPAint object and paint
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        //call super to repaint component
        super.paintComponent(grphcs);
    }
	
	/**
	 * saveFile: Saves current text to target file
	 */
	protected void saveFile()
	{
		//if target file is not null
		if(targetFile!=null)
		{
			//set text to be current text area text
			String text=this.getText();
			
			//if target's parent is writable
			if(new File(targetFile.getParent()).canWrite())
			{
				try
				{
					//check if file exists
					if(targetFile.exists())
					{
						//if so, delete target file
						targetFile.delete();
					}
					//initialize writers
					fileOut=new FileWriter(targetFile);
					writeOut=new BufferedWriter(fileOut);
					//write text to file
					writeOut.write(text);
					//close streams when completed
					writeOut.close();
					fileOut.close();
					//update initialText
					initialText=this.getText();
				}
				catch(IOException e)
				{
					//if an error occurs, display error
					System.err.println(e.getMessage());
				}
			}
			//if target's parent is not writable
			else
			{
				//display a message to save in a different location and call selectSaveFile
				JOptionPane.showMessageDialog(null, "File could not be saved using this location, \nplease try a different location.", "Error", JOptionPane.ERROR_MESSAGE);
				selectSaveFile();
			}
		}
		//if target file is null, call selectSaveFile
		else
		{
			selectSaveFile();
		}
	}
	
	/**
	 * selectSaveFile: Targets a new file location to save to and calls saveFile()
	 */
	protected void selectSaveFile()
	{
		//set confirm to be the result of save dialog
		int confirm=fileChooser.showSaveDialog(this);
		//if a save location has been chosen
		if(confirm==JFileChooser.APPROVE_OPTION)
		{
			//assign target file to be fileChooser's selected file
			targetFile=fileChooser.getSelectedFile();
			//call saveFile
			saveFile();
		}
	}
	
	/**
	 * openFile: Targets a new file to open and calls loadFile()
	 */
	protected void openFile()
	{
		//set confirm to be the result of open dialog
		int confirm=fileChooser.showOpenDialog(this);
		//if a file has been chosen to open
		if(confirm==JFileChooser.APPROVE_OPTION)
		{
			//run check on current text
			textCheck();
			//assign target file to be fileChooser's selected file
			targetFile=fileChooser.getSelectedFile();
			//call loadFile
			loadFile();
		}
	}
	
	/**
	 * loadFile: Loads currently selected target into text area
	 */
	protected void loadFile()
	{
		//if target file is not null
		if(targetFile!=null)
		{
			//if target file is readable
			if(targetFile.canRead())
			{
					try
					{
						//initialize string builder
						stringBuilder=new StringBuilder();
						//initialize readers
						fileIn=new FileReader(targetFile);
						readIn=new BufferedReader(fileIn);
						
						//create temp String to store lines read
						String temp;
						//build String from file using temp and string builder
						while((temp = readIn.readLine()) !=null)
						{
							stringBuilder.append(temp);
						}
						//set text area text to be String built from file
						this.setText(stringBuilder.toString());
						
						//close streams
						readIn.close();
						fileIn.close();
						
						//update initialText 
						initialText=this.getText();
						
					}
					catch(IOException e)
					{
						//if an error occurs, display error
						System.err.println(e.getMessage());
					}
			}
			//if target file cannot be read
			else
			{
				//display a message to open a different file and call openFile
				JOptionPane.showMessageDialog(null, "The selected file could not be read, \nplease verify you have the appropriate permissions.", "Attention", JOptionPane.YES_NO_OPTION);
				openFile();
			}
		}
		//if target is null, all openFile
		else
		{
			openFile();
		}
	}
	
	/**
	 * textCheck: Checks for modification of original text and prompts to save changes 
	 */
	protected void textCheck()
	{
		//if changes have been made to working text
		if(changesMade())
		{
			//show a save dialog if unsaved changes should be saved
			if(JOptionPane.showConfirmDialog(null, "Modifications were made to the currently loaded document, \nwould you like to save these unsaved changes?", "Error", JOptionPane.ERROR_MESSAGE)==JOptionPane.YES_OPTION)
			{
				saveFile();
			}
		}
	}
	
	/**
	 * newText: Calls textCheck() and resets target file and text area text
	 */
	protected void newText()
	{
		//run text check
		textCheck();
		//reset text area
		this.setText("");
		//reset target file
		targetFile=null;
	}
	
	/**
	 * revertText: Reverts current text to initially loaded text
	 */
	protected void revertText()
	{
		//set current text to initial text
		this.setText(initialText);
	}
	
	/**
	 * getStats: Displays a summary of statistics for current text in text area
	 */
	protected void getStats()
	{
		//capture current text in String text
		String text=this.getText();
		//initialize tokenizer using text
    	tokenizer=new StringTokenizer(text,"\n\t ");
    	//display dialog containing document statistics
    	JOptionPane.showMessageDialog(null, "Word Count: "+tokenizer.countTokens()+"\nCharacter Count: "+text.toCharArray().length+"\nCharacter Count Without Spaces: "+text.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "").toCharArray().length, "Document Stats", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * deleteSelected: Removes currently highlighted text from text area
	 */
	protected void deleteSelected()
	{
		try
    	{
    		//attempt to remove currently highlighted text
    		this.setText(this.getText().replace(this.getSelectedText(),""));
    	}
    	catch(NullPointerException ex)
    	{
    		//if there is no highlighted text, do nothing
    	}
	}
	
	/**
	 * changesMade: Checks to see if current text is the same as initially loaded text
	 * @return: Change as true or false
	 */
	private boolean changesMade()
	{
		//set initial change to true
		boolean change=true;
		//if current text area text equals initial text
		if(this.getText().equals(initialText))
		{
			//set change to false
			change=false;
		}
		return change;
	}
}
