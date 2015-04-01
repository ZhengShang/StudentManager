package model;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.DbUtil;
import dao.UserDao;

public class RegisterFrame extends JFrame {
	JTextField jtfUserName;
	JPasswordField jpwfUserPassword1, jpwfUserPassword2;
	JButton jbSummit,jbCancel;
	DbUtil dbUtil = new DbUtil();
	UserDao userDao = new UserDao();

	public RegisterFrame() {
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.add(new JLabel("�û�ע��", JLabel.CENTER));
		panel2.add(new JLabel("�û���:"));
		panel2.add(jtfUserName = new JTextField(17));
		panel2.add(new JLabel("����:"));
		panel2.add(jpwfUserPassword1 = new JPasswordField(17));
		panel2.add(new JLabel("ȷ������:"));
		panel2.add(jpwfUserPassword2 = new JPasswordField(17));
		panel3.add(jbSummit = new JButton("�ύ"));
		panel3.add(jbCancel = new JButton("ȡ��"));
		panel2.setLayout(new GridLayout(3, 3));
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);

		jbSummit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					jbRegister();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		
		jbCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new LoginOnFrame();
				dispose();
			}
		});

		this.setTitle("���û�ע��");
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	private void jbRegister() throws Exception {
		String userName = jtfUserName.getText();
		String userPassword1 = new String(jpwfUserPassword1.getPassword());
		String userPassword2 = new String(jpwfUserPassword2.getPassword());

		if (userName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��!");
			return;
		}
		if (userPassword1.isEmpty()) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��!");
			return;
		}

		if (userPassword2.isEmpty()) {
			JOptionPane.showMessageDialog(null, "ȷ�����벻��Ϊ��!");
			return;
		}

		if (!(userPassword1.equals(userPassword2))) {
			JOptionPane.showMessageDialog(null, "�������������ͬ!" + "\n" + "�������������!");
			return;
		}
		User user = new User(userName, userPassword1);
		Connection con = dbUtil.getCon();
		ResultSet result = con.createStatement().executeQuery("select count(uid) from t_userinfo");
		result.next();
		int uid = result.getInt(1) + 1;
		String sql = "insert into t_userinfo values(" + Integer.toString(uid) + ",?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.executeUpdate();

		JOptionPane.showMessageDialog(null, "ע��ɹ�!");
		this.dispose();
		new ManagerFrame(user.getUserName()).setVisible(true);
		con.close();
	}
}
