package ui.bot;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import common.ICompanyController;
import common.IStockCollection;
import company.CompanyClient;
import exception.NotFoundAccountException;

public class CompanyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableComp;
	private AddCompany addForm;
	private CompanyClient _client;
	private class AddCompany extends JFrame{
		/**
		 * 
		 */
		private CompanyFrame cmFrame = null;
		private static final long serialVersionUID = 1L;
		public AddCompany(CompanyFrame jframe) {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			AddCompany thisFrame= this;
			setBounds(100, 100, 500, 310);
			cmFrame = jframe;
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5,5,5,5));
			setContentPane(contentPane);
			SpringLayout sl_contentPane = new SpringLayout();
			contentPane.setLayout(sl_contentPane);
			JLabel lblName = new JLabel("Company name:");
			sl_contentPane.putConstraint(SpringLayout.NORTH, lblName, 20, SpringLayout.NORTH, contentPane);
			sl_contentPane.putConstraint(SpringLayout.WEST, lblName, 20, SpringLayout.WEST, contentPane);
			sl_contentPane.putConstraint(SpringLayout.EAST, lblName, 220, SpringLayout.WEST, contentPane);
			contentPane.add(lblName);
			JLabel lblCode = new JLabel("Company Code:");
			sl_contentPane.putConstraint(SpringLayout.NORTH, lblCode, 20, SpringLayout.SOUTH, lblName);
			sl_contentPane.putConstraint(SpringLayout.WEST, lblCode, 20, SpringLayout.WEST, contentPane);
			sl_contentPane.putConstraint(SpringLayout.EAST, lblCode, 220, SpringLayout.WEST, contentPane);
			contentPane.add(lblCode);
			JTextField txtName = new JTextField();
			sl_contentPane.putConstraint(SpringLayout.NORTH, txtName, 0, SpringLayout.NORTH, lblName);
			sl_contentPane.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.EAST, lblName);
			sl_contentPane.putConstraint(SpringLayout.EAST, txtName, -20, SpringLayout.EAST, contentPane);
			contentPane.add(txtName);
			JTextField txtCode = new JTextField();
			sl_contentPane.putConstraint(SpringLayout.NORTH, txtCode, 0 , SpringLayout.NORTH, lblCode);
			sl_contentPane.putConstraint(SpringLayout.WEST, txtCode, 0, SpringLayout.EAST, lblCode);
			sl_contentPane.putConstraint(SpringLayout.EAST, txtCode, -20, SpringLayout.EAST, contentPane);
			contentPane.add(txtCode);
			JButton btnAdd = new JButton("Add");
			btnAdd.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					thisFrame.setVisible(false);
					cmFrame.createNewCompany(txtName.getText(), txtCode.getText());
				}
			});
			sl_contentPane.putConstraint(SpringLayout.NORTH, btnAdd, 20, SpringLayout.SOUTH, txtCode);
			sl_contentPane.putConstraint(SpringLayout.EAST, btnAdd, -50, SpringLayout.EAST, contentPane);
			contentPane.add(btnAdd);
		}
		
	}
	
	
	public CompanyFrame(CompanyClient client) {
		_client = client;
		CompanyFrame thisFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				_client.addCompany();
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAdd, 23, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAdd, 10, SpringLayout.WEST, contentPane);
		contentPane.add(btnAdd);
		
		JPanel panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, btnAdd);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, -5, SpringLayout.EAST, contentPane);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		tableComp = new JTable();
		tableComp.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name","Stock Code","Price","Quantity"
			}
		));
		scrollPane.setViewportView(tableComp);
		addForm = new AddCompany(this);
	}
	void createNewCompany(String name,String code) {
//		this._client.addCompany(name, code);
	}
	public void updateCompanyTable(IStockCollection stocks) {
		DefaultTableModel model = (DefaultTableModel) tableComp.getModel();
		model.setRowCount(0);
		stocks.toArray().forEach(stock ->{
				model.addRow(new Object[]{
						stock.getCompanyName(),
						stock.getCode(),
						String.valueOf(String.format("%.2f", stock.getPrice())),
						String.valueOf(stocks.getStockQuantity(stock.getCode()))
						});
		});
	}
	public void showMessage(String title,String msg) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.OK_OPTION);
	}
}
