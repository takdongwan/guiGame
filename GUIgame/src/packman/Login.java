package packman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/*
public class Login extends JFrame {
	
	public int  isRuning =0;

	public Login() {
		JPanel panel = new JPanel();
		JLabel label =new JLabel("ID");
		JTextField txtID = new JTextField(10);
		JLabel pswrd = new JLabel ("passworld");
		JPasswordField txtPass = new JPasswordField(10);
		JButton logBtn = new JButton ("Log In");
		panel.add(label);
		panel.add(txtID);
		panel.add(pswrd);
		panel.add(txtPass);
		panel.add(logBtn);
		
		//기능추가
		logBtn.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String id = "takdongwan";
				String pass = "1234";
				
				if(id.equals(txtID.getText()) && pass.equals(txtPass.getText())) {
					JOptionPane.showMessageDialog(null," 로그인 성공 ");
					isRuning =1;
				}  else {
					JOptionPane.showMessageDialog(null,"로그인실패");
					isRuning =-1;
				
				}
			}
		});
		
		
		
		add(panel);
		
		setVisible(true);
		setSize (600,400);
		setLocationRelativeTo (null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}*/