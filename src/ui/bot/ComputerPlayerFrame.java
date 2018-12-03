package ui.bot;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import player.ComputerPlayerClient;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class ComputerPlayerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> listMessage;
	private DefaultListModel<String> listModel;

	/**
	 * Create the frame.
	 */
	public ComputerPlayerFrame(ComputerPlayerClient client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.addNewPlayer();
			}
		});
		contentPane.add(btnNewButton, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		this.listModel = new DefaultListModel<>();
		this.listMessage = new JList<>();
		scrollPane.setViewportView(listMessage);
	}
	
	public void addMessage(String message) {
		this.listModel.addElement(message);
		this.listMessage.setModel(this.listModel);
	}

}
