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
import javax.swing.border.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
/**
 * AgilitextGui: Class used to construct the main Agilitext GUI
 * 
 * @author Gannon McGibbon 
 * @version 1.0 
 * 
 * Date Created: 06/06/13
 * Last Updated: 06/24/13
 */
public class AgilitextGui extends JPanel
{
	//GUI components
	
	//panels
	protected JPanel mainPanel;
	protected JPanel subPanel;
	
	//scroll panes
	protected JScrollPane mainScroll;
	
	//text areas
	public AgilitextTextArea textArea;
	
	//buttons
	protected JButton saveButton;
	protected JButton saveAsButton;
	protected JButton openButton;
	
	//menu components
	
	//bar
	protected JMenuBar menuBar;
	
	//menus
	protected JMenu fileMenu;
	protected JMenu editMenu;
	protected JMenu toolsMenu;
	protected JMenu aboutMenu;
	
	//menu items
	protected JMenuItem newItem;
	protected JMenuItem openItem;
	protected JMenuItem saveItem;
	protected JMenuItem saveAsItem;
	
	protected JMenuItem cutItem;
	protected JMenuItem copyItem;
	protected JMenuItem pasteItem;
	protected JMenuItem deleteItem;
	protected JMenuItem selectAllItem;
	protected JMenuItem revertItem;
	
	protected JMenuItem statsItem;
	
	protected JMenuItem aboutItem;
	protected JMenuItem siteItem;
	
	//private objects
	private static final long serialVersionUID = 1L;
	private Font mainFont=getJarFont("UbuntuMono-B.ttf",16);
	private Font buttonFont=getJarFont("UbuntuMono-B.ttf",15);
	private Font menuFont=getJarFont("UbuntuMono-B.ttf",14);
	private AgilitextHandler handler = new AgilitextHandler(this);
	
	/**
	 * AgilitextGui: Constructor
	 */
	public AgilitextGui()
	{
		
		//set layout of gui panel
		setLayout(new BorderLayout());
		
		//build panels
		buildMain();
		buildSub();
		//build menu
		buildMenu();
		//set mnemonics
		setShortcuts();
		//add panels to main panel
		add(mainPanel, BorderLayout.CENTER);
		add(subPanel, BorderLayout.SOUTH);
		
		//add listeners and style main panel components
		configComponents(this.getComponents());
		//add listeners and style menu components
		configComponents(menuBar.getComponents());
		
		//repaint main panel
		repaint();
	}
	
	/**
	 * getJarFont: Loads a font from main jar
	 * @param fileName: Specifies the name of the font file, eg. "font.ttf"
	 * @param size: Specifies a font size to be used
	 * @return A fully constructed Font object or null
	 */
	public Font getJarFont(String fileName, float size)
	{
		//declare Font object
		Font f=null;
		try
		{
			//declare input stream as resource from jar using passed filename
			InputStream stream = this.getClass().getResourceAsStream("/assets/"+fileName);
			//attempt to create a tuetype font using input stream
			f=Font.createFont(Font.TRUETYPE_FONT, stream);
			//set font size to passed size
			f=f.deriveFont(size);
			
		}
		catch(Exception e)
		{
			//if an error occurs, print font error
			System.err.println("Fontface failed to load!");
		}
		return f;
	}
	
	public Image getJarImage(String fileName)
	{
		ImageIcon img = null;
		
		try
		{
			//declare input stream as resource from jar using passed filename
			InputStream stream = this.getClass().getResourceAsStream("/assets/"+fileName);
			//attempt to initialize ImageIcon using Input Stream
			img = new ImageIcon(ImageIO.read(stream)); 
			
		}
		catch(Exception e)
		{
			//if an error occurs, print font error
			System.err.println("Icon failed to load!");
		}
		return img.getImage();
	}
	
	/**
	 * buildMenu: Initializes and constructs menu bar
	 */
	private void buildMenu()
	{
		//initialize bar
		menuBar=new JMenuBar();
		
		//initialize menus
		fileMenu=new JMenu("File");
		editMenu=new JMenu("Edit");
		toolsMenu=new JMenu("Tools");
		aboutMenu=new JMenu("?");
		
		//initialize and add file menu items
		newItem=new JMenuItem("New");
		openItem=new JMenuItem("Open");
		saveItem=new JMenuItem("Save");
		saveAsItem=new JMenuItem("Save As");
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		
		//initialize and add edit menu items
		cutItem=new JMenuItem("Cut");
		copyItem=new JMenuItem("Copy");
		pasteItem=new JMenuItem("Paste");
		deleteItem=new JMenuItem("Delete");
		selectAllItem=new JMenuItem("Select All");
		revertItem=new JMenuItem("Revert");
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
		editMenu.add(selectAllItem);
		editMenu.add(revertItem);
		
		//initialize and add tools menu items
		statsItem=new JMenuItem("Stats");
		toolsMenu.add(statsItem);
		
		//initialize and add about menu item
		aboutItem=new JMenuItem("About");
		siteItem=new JMenuItem("Visit My Website");
		aboutMenu.add(aboutItem);
		aboutMenu.add(siteItem);
		
		//add menus to bar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(aboutMenu);
	}
	
	/**
	 * buildMain: Initializes and constructs main panel
	 */
	private void buildMain()
	{
		//initialize main panel and set layout
		mainPanel=new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		
		//initialize text area
		textArea=new AgilitextTextArea();
        //set line wrap and margins of text area
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(8,8,8,8));
		
		//initialize and add text area to main scroll pane
		mainScroll=new JScrollPane(textArea);
		mainPanel.add(mainScroll);
		
		//set text area to transparent
		textArea.setOpaque(false);
		//add empty border to scroll pane
		mainScroll.setBorder(new EmptyBorder(0,0,0,0));
		
		//set styles for text area
		textArea.setBackground(Color.darkGray);
		textArea.setCaretColor(Color.WHITE);
		textArea.setForeground(Color.WHITE);
	}
	
	/**
	 * buildSub: Initializes and constructs sub panel
	 */
	private void buildSub()
	{
		//initialize and set layout for sub panel
		subPanel=new JPanel();
		subPanel.setLayout(new GridLayout(1,3));
		
		//initialize buttons and add them to sub panel
		saveButton=new JButton("Save");
		saveAsButton=new JButton("Save As");
		openButton=new JButton("Open");
		subPanel.add(saveButton);
		subPanel.add(saveAsButton);
		subPanel.add(openButton);
		
		//set dark gray line border for sub panel
		subPanel.setBorder(new LineBorder(Color.darkGray));
		
		//set styles for sub panel buttons
		saveButton.setForeground(Color.WHITE);
		saveAsButton.setForeground(Color.WHITE);
		openButton.setForeground(Color.WHITE);
		saveButton.setBackground(Color.BLACK);
		saveAsButton.setBackground(Color.BLACK);
		openButton.setBackground(Color.BLACK);
	}
	
	/**
	 * setShortcuts: Sets mnemonics for GUI objects
	 */
	private void setShortcuts()
	{
		//set menu mnemonics
		fileMenu.setMnemonic('F');
		editMenu.setMnemonic('E');
		toolsMenu.setMnemonic('T');
		aboutMenu.setMnemonic('H');
		
		//set file menu item mnemonics
		newItem.setMnemonic('N');
		openItem.setMnemonic('O');
		saveItem.setMnemonic('S');
		saveAsItem.setMnemonic('A');
		
		//set edit menu item mnemonics
		cutItem.setMnemonic('T');
		copyItem.setMnemonic('C');
		pasteItem.setMnemonic('P');
		deleteItem.setMnemonic('D');
		selectAllItem.setMnemonic('A');
		revertItem.setMnemonic('R');
		
		//set tool menu item mnemonics
		statsItem.setMnemonic('S');
		
		//set about menu item mnemonics
		saveButton.setMnemonic('S');
		saveAsButton.setMnemonic('A');
		openButton.setMnemonic('O');
	}
	
	/**
	 * configComponenets: Applies styles and listeners to GUI objects on the main GUI panel
	 * @param items: An array of Component objects to apply Fonts to
	 */
	private void configComponents(Component[] items)
	{
		//iterate though components
		for(int i=0;i<items.length;i++)
		{
			
			//if current component is an instance of JPanel
			if (items[i] instanceof JPanel)
			{
				//rerun method passing current JPanel components
				configComponents(((Container)items[i]).getComponents());
	        }
			//if current component is an instance of JScrollPane
			else if (items[i] instanceof JScrollPane)
			{
				//rerun method passing current JScrollPane components
				configComponents(((Container)items[i]).getComponents());
	        }
			//if current component is an instance of JViewPort
			else if (items[i] instanceof JViewport)
			{
				//create temporary component array of component inside view
				Component[] tmp={((JViewport)items[i]).getView()};
				//rerun method passing temporary array
				configComponents(tmp);
	        }
			//if current component is an instance of JMenu
			else if(items[i] instanceof JMenu)
			{
				//set font for menu
				items[i].setFont(menuFont);
				//add listener to menu
				((JMenu)items[i]).addMenuListener(handler);
				//rerun method passing all menu item components
				configComponents(((JMenu)items[i]).getMenuComponents());
			}
			//if current component is an instance of JMenuItem
			else if(items[i] instanceof JMenuItem)
			{
				//set font for menu item
				items[i].setFont(menuFont);
				//add listener to menu item
				((JMenuItem)items[i]).addActionListener(handler);
			}
			//if current component is an instance of JButton
			else if(items[i] instanceof JButton)
			{
				//set font for button
				items[i].setFont(buttonFont);
				//add listener to button
				((JButton)items[i]).addActionListener(handler);
			}
			//if current component is an instance of JTextArea
			else if(items[i] instanceof JTextArea)
			{
				//set font for text area
				items[i].setFont(mainFont);
			}
		}
	}
	
	/**
	 * getMenu: Used to get main menu bar object
	 * @return: Main menu bar object
	 */
	protected JMenuBar getMenu()
	{
		return menuBar;
	}
	
	/**
	 * getHandler: Used to get GUI component handler
	 * @return: GUI component handler
	 */
	protected AgilitextHandler getHandler()
	{
		return handler;
	}
	
}