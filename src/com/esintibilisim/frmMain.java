package com.esintibilisim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;

public class frmMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtConsole;
	private Thread threat;
	private JPanel panel;
	
	private JButton btnStart = new JButton("Start");
	private JButton btnStop = new JButton("Stop");
	private JTextField txtIpAddress;
	private JPanel panel2;
	private JLabel lblThisToolIs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmMain frame = new frmMain();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frmMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmMain.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		setTitle("Ping Test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtConsole = new JTextArea();
		txtConsole.setEditable(false);
		contentPane.add(txtConsole, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(txtConsole);
		contentPane.add(scroll);	
		
		/* Scroll always to bottom */
		DefaultCaret caret = (DefaultCaret)txtConsole.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		/* *********************** */
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(2, 0, 5, 0));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		txtIpAddress = new JTextField();
		txtIpAddress.setText("8.8.8.8");
		txtIpAddress.setColumns(10);
		panel.add(txtIpAddress);
		
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				threat = new Thread(new ping_threat());
				threat.start();
				
				txtIpAddress.setEnabled(false);
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
			}
		});
		
		panel.add(btnStop);
		btnStop.setEnabled(false);
		
		panel2 = new JPanel();
		panel2.setToolTipText("");
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		lblThisToolIs = new JLabel("This Tool is programmed by Erdal ÖZBAN using JAVA");
		lblThisToolIs.setForeground(Color.GRAY);
		panel2.add(lblThisToolIs);
		btnStop.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				threat.stop();
				
				txtIpAddress.setEnabled(true);
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}
		});
		

	}
	
	class ping_threat implements Runnable{

		@Override
		public void run() {
			System.out.println("threat started");
	        String pingCmd = "ping " + txtIpAddress.getText().trim();
	        txtConsole.setText(null);
	        
	        try {
	            Runtime r = Runtime.getRuntime();
	            Process p = r.exec(pingCmd);

	            BufferedReader in = new BufferedReader(new
	            InputStreamReader(p.getInputStream()));
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                System.out.println(inputLine);
	                txtConsole.append(inputLine + "\n");
	            }
	            in.close();
	            p.destroy();

	        } catch (IOException e1) {
	            System.out.println(e1);
	        }
			
	        System.out.println("threat finished");
		}
		
	}
}
