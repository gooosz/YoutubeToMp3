import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//Exception handling
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.datatransfer.UnsupportedFlavorException;

//Copy and Paste
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class Window
{
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JTextField linkTextField;
	private JButton downloadButton;
	private JButton clearButton;
	private JButton pasteButton;
	/*
	 * first download file
	 * share file to connected device
	 * using shareKdeConnect.sh script
	 */
	private JButton shareButton;

	private String pwd = null;

	//store previous downloaded file name
	private String prevDownloaded;

	public Window()
	{
		frame = new JFrame("Window Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 200);

		panel = new JPanel(new BorderLayout());
		label = new JLabel("Enter Link");

		//Textfield accepts upto 50 characters, inputLink length
		linkTextField = new JTextField(50);
		linkTextField.setBounds(270, 400, 50, 10);

		//Download after clicking downloadButton
		downloadButton = new JButton("Download");
		downloadButton.setBounds(50, 50, 100, 20);

		//clear the Textfield
		clearButton = new JButton("Clear");

		//paste system Clipboard into TextField
		pasteButton = new JButton("Paste");

		shareButton = new JButton("Share");

		//add actionListener for important buttons
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = linkTextField.getText();

				if (pwd == null)
				{
					//get pwd
					ProcessBuilder pb = new ProcessBuilder("pwd");

					try
					{
						Process process = pb.start();
						BufferedReader br = new BufferedReader(
										new InputStreamReader(
											process.getInputStream()
										));
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null)
						{
							builder.append(line);
						}

						pwd = builder.toString();
						pwd += "/src/download.sh";
					}
					catch (IOException ev)
					{
						ev.printStackTrace();
					}
				}

				//run download script
				ProcessBuilder pb = new ProcessBuilder("sudo", pwd, link);

				try {
					Process process = pb.start();

					//store output in string prevDownloaded
					BufferedReader reader = new BufferedReader(
									new InputStreamReader(
										process.getInputStream()
									));
					String filename = "";
					while ((prevDownloaded = reader.readLine()) != null)
					{
						/*
						 * get only the last line
						 * aka loop through the output
						 * until there is no more output
						 */
						 filename = prevDownloaded;
					}
					prevDownloaded = filename;
					prevDownloaded += ".mp3";
				}
				catch (IOException ev) {
					ev.printStackTrace();
				}
			}
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				linkTextField.setText("");
			}
		});

		pasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable content = clipboard.getContents(null);

				String pastedString = "";

				try
				{
					pastedString = (String) content.getTransferData(DataFlavor.stringFlavor);
					linkTextField.setText(pastedString);
				}
				catch (UnsupportedFlavorException | IOException ev)
				{
					ev.printStackTrace();
				}
			}
		});

		shareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//download the song first
				downloadButton.doClick();

				//get downloaded file name


				//send to connected device using shareKdeConnect.sh

			}
		});

		//Layout
		JPanel titelRow = new JPanel();
		titelRow.add(label);

		JPanel textFieldRow = new JPanel();
		textFieldRow.add(linkTextField, BorderLayout.EAST);
		textFieldRow.add(clearButton, BorderLayout.WEST);
		textFieldRow.add(pasteButton, BorderLayout.SOUTH);

		JPanel southStuff = new JPanel();
		southStuff.add(shareButton, BorderLayout.EAST);
		southStuff.add(downloadButton, BorderLayout.WEST);

		//add everything to JPanel
		panel.add(titelRow, BorderLayout.NORTH);
		panel.add(textFieldRow);
		panel.add(southStuff, BorderLayout.SOUTH);

		panel.setPreferredSize(new Dimension(480, 360));
		panel.setMaximumSize(panel.getPreferredSize());
		panel.setMinimumSize(panel.getPreferredSize());


		//add Components to JFrame
		frame.getContentPane().add(panel);

		frame.setVisible(true);
	}


}
