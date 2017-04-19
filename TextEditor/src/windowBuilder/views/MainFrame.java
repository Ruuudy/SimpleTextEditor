package windowBuilder.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.ScrollPaneConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnNew;
	private JTextArea textArea;
	public JScrollPane scroller;
	public JFileChooser fileSelect;
	private JButton btnOpen;
	private JButton btnSave;
	private File currentFile;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		fileSelect = new JFileChooser();
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.WHITE);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		btnNew = new JButton("");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz utworzyæ nowy plik?") == 0){
					textArea.setText("");
				}
			}
		});
		btnNew.setBackground(Color.WHITE);
		btnNew.setIcon(new ImageIcon(MainFrame.class.getResource("/windowBuilder/resources/new-file-icon.png")));
		toolBar.add(btnNew);
		
		btnOpen = new JButton("");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileSelect.showOpenDialog(null) == fileSelect.APPROVE_OPTION){
					openFile(fileSelect.getSelectedFile());
				}
			}
		});
		btnOpen.setBackground(Color.WHITE);
		btnOpen.setIcon(new ImageIcon(MainFrame.class.getResource("/windowBuilder/resources/open-file-icon.png")));
		toolBar.add(btnOpen);
		
		btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(currentFile==null){
					if(fileSelect.showSaveDialog(null) == fileSelect.APPROVE_OPTION){
						saveFile(fileSelect.getSelectedFile(), textArea.getText());
						System.out.println(fileSelect.getSelectedFile());
					}
				}
				else {
					String[] buttons = { "Save", "Save as"};    
					int returnValue = JOptionPane.showOptionDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons);
					System.out.println(returnValue);
					if(returnValue == 0){
						saveFile(currentFile, textArea.getText());
					}
					else{
						if(fileSelect.showSaveDialog(null) == fileSelect.APPROVE_OPTION){
							saveFile(fileSelect.getSelectedFile(), textArea.getText());
						}
					}
				}
			}
		});
		btnSave.setBackground(Color.WHITE);
		btnSave.setIcon(new ImageIcon(MainFrame.class.getResource("/windowBuilder/resources/Save-icon.png")));
		toolBar.add(btnSave);
		
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		textArea.setWrapStyleWord(true);
		scroller = new JScrollPane(textArea);
		getContentPane().add(scroller, BorderLayout.CENTER);
	}
	
	public void openFile(File file){
		if(file.canRead()){
			String filePath = file.getPath();
			String fileContents = "";
			if(filePath.endsWith(".txt")){
				try{
					Scanner scan = new Scanner(new FileInputStream(file));
					while(scan.hasNextLine()){
						fileContents += scan.nextLine() + "\r\n";
					}
					scan.close();
				} catch(FileNotFoundException e){
					
				}
				textArea.setText(fileContents);
				currentFile = file;
			}
			else{
				JOptionPane.showMessageDialog(null, "Nie wspierany typ pliku!");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Nie mo¿na otworzyæ pliku");
		}
	}
	
	public void saveFile(File file, String content){
		String filePath = file.getPath();
		if(!file.getPath().endsWith(".txt")){
			filePath += ".txt";
		}
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
			out.write(content);
			out.close();
			currentFile = file;
		} catch(Exception e){
			
		}
	}
}
