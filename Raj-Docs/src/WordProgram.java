import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class WordProgram{

	public static final int windowWidth = 800;
	public static final int windowHeight = 1000;
	public static final int panelMargins = 25;
	public static final int textAreaMargins = 25;
	public static JFileChooser fc;
	public static JTextArea textArea;
	public static String openFile;
	public static String openFilePath;
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame("Raj Docs - New Doc");
		fc = new JFileChooser();
		
		ImageIcon alignLeft = new ImageIcon(WordProgram.class.getResource("img/align_left.png"));
		ImageIcon alignMid = new ImageIcon(WordProgram.class.getResource("img/align_mid.png"));
		ImageIcon alignRight = new ImageIcon(WordProgram.class.getResource("img/align_right.png"));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		// create components
		
		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(panelMargins, panelMargins, panelMargins, panelMargins));
		content.setBackground(Color.GRAY);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem newDoc = new JMenuItem("New");
		JMenuItem save = new JMenuItem("Save");
		save.setEnabled(false);
		JMenuItem saveAs = new JMenuItem("Save As");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem print = new JMenuItem("Print");
		print.setEnabled(false);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		JLabel textFont = new JLabel("Font");
		JComboBox font = new JComboBox();
		font.addItem("Times New Roman");
		font.setMaximumSize(font.getPreferredSize());
		JLabel textFontSize = new JLabel("Font Size");
		JTextField fontSize = new JTextField("12", 3);
		fontSize.setMaximumSize(fontSize.getPreferredSize());
		JButton alignLeftButton = new JButton(alignLeft);
		JButton alignMidButton = new JButton(alignMid);
		JButton alignRightButton = new JButton(alignRight);
		
		textArea = new JTextArea();
		textArea.setColumns(140);
		textArea.setRows(24);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(textAreaMargins, textAreaMargins, textAreaMargins, textAreaMargins));
		
		
		
		//
		
		// add components
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		frame.add(content);
		content.add(textArea, BorderLayout.CENTER);
		
		menuBar.add(menu);
		menu.add(newDoc);
		menu.add(save);
		menu.add(saveAs);
		menu.add(open);
		menu.add(print);
		
		toolBar.add(textFont);
		toolBar.add(font);
		toolBar.add(textFontSize);
		toolBar.add(fontSize);
		toolBar.add(alignLeftButton);
		toolBar.add(alignMidButton);
		toolBar.add(alignRightButton);
		
		//
		
		// action listeners
		
		newDoc.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(textArea.getText().length() >= 1){
					
					int choice = JOptionPane.showConfirmDialog(frame, "Would you like to save your current document?");
					if(choice == 0){ // yes, save
						saveAs(frame);
					}else if(choice == 1){ // no, don't save
						textArea.setText("");
					}
				}
				
				frame.setTitle("Raj Docs - New Doc");
			}
		});
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				save(frame);
			}
		});
		
		saveAs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveAs(frame);
			}
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				
				if(textArea.getText().length() >= 1){
					
					int choice = JOptionPane.showConfirmDialog(frame, "Would you like to save your current document?");
					if(choice == 0){ // yes, save
						saveAs(frame);
					}else if(choice == 1){ // no, don't save
						loadFile(frame);
					}
					
				}else{
					loadFile(frame);
				}
				
				save.setEnabled(true);
			}
		});
		
		alignLeftButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(textArea.getSelectedText().length() > 0){
					
				}
			}
		});
		
		//
		
		frame.setSize(windowWidth, windowHeight);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public static void save(JFrame frame){
		
		File file = new File(openFilePath);
		BufferedWriter outFile = null;
		try{
			outFile = new BufferedWriter(new FileWriter(file));
			textArea.write(outFile);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(outFile != null){
				try{
					outFile.close();
				}catch(IOException e){
					
				}
			}
		}
		
	}
	
	public static void saveAs(JFrame frame){
		
		int returnVal = fc.showSaveDialog(frame);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			
			File file = null;
			
			if(fc.getSelectedFile().getName().endsWith(".txt")){
				file = new File(fc.getSelectedFile() + "");
			}else{
				file = new File(fc.getSelectedFile() + ".txt");
			}
			
			BufferedWriter outFile = null;
			try{
				outFile = new BufferedWriter(new FileWriter(file));
				textArea.write(outFile);
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				if(outFile != null){
					try{
						outFile.close();
					}catch(IOException e){
						
					}
				}
			}
		}
		
	}
	
	public static void loadFile(JFrame frame){
		
		int returnVal = fc.showOpenDialog(frame);

		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			openFile = fc.getSelectedFile().getName();
			openFilePath = fc.getSelectedFile().getAbsolutePath();
			String fileText = file.getAbsolutePath();
			//System.out.println(fileText);
			try {
				textArea.setText(readFile(fileText));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		frame.setTitle("Raj Docs - " + openFile);
	}
	
	@SuppressWarnings("resource")
	public static String readFile(String file) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		StringBuffer sb = new StringBuffer();
		
		String line = null;
		while((line = br.readLine()) != null){
			sb.append(line).append("\n");
		}
		
		String ret = sb.toString();
		
		return ret;
		
	}
}
