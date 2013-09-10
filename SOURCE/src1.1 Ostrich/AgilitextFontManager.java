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

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

/**
 * AgilitextFindReplace: Class used to construct a find replace String window
 * 
 * @author Gannon McGibbon 
 * @version 1.1
 * 
 * Date Created: 08/09/13
 * Last Updated: 08/18/13
 */
public class AgilitextFontManager extends JDialog
{
	//GUI components
	
	//panels
	protected JPanel fontPanel;
	protected JPanel sizePanel;
	protected JPanel samplePanel;
	protected JPanel buttonPanel;
	protected JPanel mainPanel;
	
	//text fields
	protected AgilitextTextField sampleField;
	protected AgilitextTextField sizeField;
	
	//scroll panes
	protected JScrollPane fontScroll;
	
	//lists
	protected JList<String> fontList;
	
	//buttons
	protected JButton resetButton;
	protected JButton confirmButton;
	protected JButton cancelButton;
	
	//private objects
	
	//container
	private Container c;
	
	//target component
	private JComponent target;
	
	//vectors
	private Vector<String> fonts;
	
	//fonts
	private Font[] systemFonts;
	private Font targetFont;
	private Font defaultFont;
	
	//initialize serialization long
	private static final long serialVersionUID = 1L;
	
	/**
	 * AgilitextFontManager: Constructor
	 * @param parent: The parent JFrame used to set position of window
	 * @param target: The target JComponent to set selected fonts
	 */
	public AgilitextFontManager(JFrame parent, JComponent target)
	{
		//call super constructor using parent JFrame with no modal window priority
		super(parent, false);
		//set dialog to be always on top
		setAlwaysOnTop(true);
		//set location relative to parent JFrame
		setLocationRelativeTo(parent);
		
		//set target to passed target JComponent
		this.target=target;
		//set targetFont to current target font
		targetFont=target.getFont();
		//set defaultFont to current targetFont
		defaultFont=targetFont;
		
		//get and initialize system fonts from Appsistant
		getSystemFont();
		
		//build panels
		buildFontPanel();
		buildButtonPanel();
		buildMainPanel();
		
		//assign container
	    c = getContentPane();
	    //add main panel to container
	    c.add(mainPanel);
	    
	    //set size limitations
	    pack();
        setResizable(false);
        setMinimumSize(new Dimension(getWidth(),(int)(getWidth()*1.3)));
        
        //add window dressing
	    setTitle("Font");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    
	    //set initial visibility to false
	    setVisible(false);
	    
	    //request initial focus on fontScroll
	    fontScroll.requestFocus();
	}
	
	/**
	 * getSystemFonts: Used to obtain system font array and build fonts name Vector
	 */
	private void getSystemFont()
	{
		//set systemFonts to Appsistant's systemFont array
		systemFonts=Appsistant.getSystemFonts();
		
		//initialize fonts name Vector
		fonts=new Vector<String>();
		
		//iterate through systemFonts
		for(int i=0;i<systemFonts.length;i++)
		{
			//add each font name to fonts Vector
			fonts.add(systemFonts[i].getName());
		}
	}
	
	/**
	 * buildFontPanel: Initializes and constructs font panel
	 */
	private void buildFontPanel()
	{
		//initialize font panel and set layout
		fontPanel=new JPanel();
		fontPanel.setLayout(new BorderLayout());
		
		//set font panel border
		fontPanel.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
		
		//initialize fontList
		fontList=new JList<String>(fonts);
		//inital fontScroll and set blank corner button
		fontScroll=new JScrollPane(fontList);
		fontScroll.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER,new JButton()); 
		
		//initialize sample subpanel and set layout
		samplePanel=new JPanel();
		samplePanel.setLayout(new GridLayout(1,1));
		
		//initialize sample field and add to sample panel
		sampleField=new AgilitextTextField("Test");
		samplePanel.add(sampleField);
		
		//initialize size subpanel and set layout
		sizePanel=new JPanel();
		sizePanel.setLayout(new GridLayout(1,1));
		
		//initialize size field and add to size panel
		sizeField = new AgilitextTextField();
		sizePanel.add(sizeField);
		
		//add components and subpanels to font panel
		fontPanel.add(samplePanel, BorderLayout.NORTH);
		fontPanel.add(fontScroll, BorderLayout.CENTER);
		fontPanel.add(sizePanel, BorderLayout.SOUTH);
		
	}
	
	/**
	 * buildButtonPanel: Initializes and constructs button panel
	 */
	private void buildButtonPanel()
	{
		//initialize button panel and set layout
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		
		//Initialize buttons
		resetButton= new JButton("Reset");
		confirmButton= new JButton("Apply");
		cancelButton= new JButton("Cancel");
		
		//add buttons to panel
		buttonPanel.add(resetButton);
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
		
		//set button panel border
		buttonPanel.setBorder(new LineBorder(Color.darkGray));
	}
	
	/**
	 * buildMainPanel: Initialize and construct main panel
	 */
	private void buildMainPanel()
	{
		//initialize main panel and set layout
		mainPanel=new JPanel();
	    mainPanel.setLayout(new BorderLayout());
	    
	    //add font and button panels to main panel
	    mainPanel.add(fontPanel, BorderLayout.CENTER);
	    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * getTargetFont: Used to display target Font object's attributes within the GUI 
	 */
	public void getTargetFont()
	{
		//if fonts name Vector contains targetFont's name
		if(fonts.contains(targetFont.getName()))
		{
			//set fontList's selected value to targetFont's name
			fontList.setSelectedValue(targetFont.getName(),true);
		}
		else
		{
			//if name is not present in list, set selected value to original dialog font
			fontList.setSelectedValue("Dialog.plain",true);
		}
		
		//make sure selection is visible in scrollable list
		fontList.ensureIndexIsVisible(fontList.getSelectedIndex());
		
		//set text of sizeField to float size of targetFont
		sizeField.setText(""+targetFont.getSize2D());
	}
	
	/**
	 * setTargetFont: Used to set target font to font specified in GUI
	 */
	public void setTargetFont()
	{
		//initialize null Font object
		Font f=null;
		//initialize size float to sizeField's number
		float size=(float)sizeField.getNumber(1,125);
		
		//if size is not -1
		//(if size was returned as a valid number)
		if(size!=-1)
		{
			//set f to fontList's selected index of systemFonts array resized to specified size
			f=systemFonts[fontList.getSelectedIndex()].deriveFont(size);
			//set targetFont to f
			targetFont=f;
			//set target's font to f
			target.setFont(f);
			
			//update font preferences
			Appsistant.pref.put(Appsistant.LAST_FONT_NAME_KEY, f.getName());
			Appsistant.pref.putFloat(Appsistant.LAST_FONT_SIZE_KEY, f.getSize2D());
		}
	}
	
	/**
	 * setSampleFont: Used to set sample text field font to currently selected list font
	 */
	public void setSampleFont()
	{
		//set saampleField font to be fontList's selected index of systemFonts array resized to 16.0
		sampleField.setFont(systemFonts[fontList.getSelectedIndex()].deriveFont(16f));
		//reset text in sample field to default
		sampleField.setText("Test");
	}
	
	/**
	 * setVisible: Used to display or hide window
	 * @param vis: True if set to visible and false if set to invisible
	 */
	public void setVisible(boolean vis)
	{
		//call superclass using passed vis boolean
		super.setVisible(vis);
		
		//if vis is true
		if(vis)
		{
			//call getTargetFont to load target font into GUI
			getTargetFont();
		}
	}
	
	/**
	 * resetTargetFont: Used to reset target font
	 */
	public void resetTargetFont()
	{
		//set targetFont to defaultFont
		targetFont=defaultFont;
		//load target targetFont into GUI and assign it to target text area 
		getTargetFont();
		setTargetFont();
	}
	
	/**
	 * setLastFont: Used to assign last used Font object to target text area
	 */
	public void setLastFont()
	{
		
	    //if last font name and last font size are valid values
	    if(Appsistant.LAST_FONT_NAME!=null&&Appsistant.LAST_FONT_SIZE!=-1)
	    {
	    	try
	    	{
	    		//attempt to set targetFont to be the index of last font name in fonts name Vector in 
	    		//systemFonts array resized using last font size
		    	targetFont=systemFonts[fonts.indexOf(Appsistant.LAST_FONT_NAME)].deriveFont(Appsistant.LAST_FONT_SIZE);
	    	}
	    	catch(ArrayIndexOutOfBoundsException e)
	    	{
	    		//if an error occurs, call resetTargetFont
	    		resetTargetFont();
	    		//set targetFont to itself resized with last font size
	    		targetFont=targetFont.deriveFont(Appsistant.LAST_FONT_SIZE);
	    	}
	    	//load target targetFont into GUI and assign it to target text area 
	    	getTargetFont();
	    	setTargetFont();
	    }
	    
	}
}
