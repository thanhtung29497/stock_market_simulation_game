package ui.server;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class BankServerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	private JList<String> listMessage;
	private DefaultListModel<String> listModel;
	
	public BankServerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.listMessage = new JList<>();
		contentPane.add(listMessage, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(this.listMessage);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		this.listModel = new DefaultListModel<String>();
		
		this.setVisible(true);
	}
	
	public void addMessage(String message) {
		this.listModel.addElement(message);
		this.listMessage.setModel(this.listModel);
	}
	
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error, "Error", ERROR);
	}

}
