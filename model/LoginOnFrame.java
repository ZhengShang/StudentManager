package model;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.CheckCodePanel;
import util.DbUtil;
import dao.UserDao;

public class LoginOnFrame extends JFrame {
	private JLabel jlWelcom;
	private JTextField jtfUserName, jtfCheckCode;
	private JPasswordField jpwfPassword;
	private JButton jbLog, jbRegist;
	private DbUtil dbUtil = new DbUtil();
	private UserDao userDao = new UserDao();
	private CheckCodePanel codePanel =new CheckCodePanel();
	
	public LoginOnFrame() {
		JPanel jptop, jpmid, jpbot;
		jptop = new JPanel();
		jpmid = new JPanel();
		jpbot = new JPanel();
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jpCode = new JPanel();
		jptop.add(jlWelcom = new JLabel("学生信息管理系统"));
		jlWelcom.setFont(new Font("黑体", Font.BOLD, 18));
		jp1.add(new JLabel("用户名：", JLabel.CENTER));
		jp1.add(jtfUserName = new JTextField("java", 17));
		jp2.add(new JLabel("密    码：", JLabel.CENTER));
		jp2.add(jpwfPassword = new JPasswordField("123456", 17));
		jpCode.add(new JLabel());
		jpCode.add(new JLabel("请输入验证码:"));
		jpCode.add(jtfCheckCode = new JTextField(5));
		jpCode.add(codePanel);
		jpCode.setLayout(new GridLayout(1, 3, 10, 20));
		jpmid.add(jp1);
		jpmid.add(jp2);
		jpmid.add(jpCode);
		jpmid.setLayout(new GridLayout(3, 1, 10, 20));
		jpbot.add(jbLog = new JButton("登录"));
		jpbot.add(jbRegist = new JButton("注册"));

		jpwfPassword.requestFocusInWindow();
		this.add(jptop, BorderLayout.NORTH);
		this.add(jpmid, BorderLayout.CENTER);
		this.add(jpbot, BorderLayout.SOUTH);
//		this.getRootPane().setDefaultButton(jbLog);

		jtfCheckCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					login();
			}
		});

		jbLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		jbRegist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new RegisterFrame().setVisible(true);

			}
		});

		
		this.setSize(530, 261);
		this.setResizable(false);
		this.setTitle("登录");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	//处理验证码部分
	private boolean check(){
		String inputCode = jtfCheckCode.getText().trim().toUpperCase();
		if(inputCode.isEmpty()){
			JOptionPane.showMessageDialog(null, "请填写验证码!");
			return false;
		}
		
		String code = codePanel.getCode();
		if (code.equals(inputCode)) {
			return true;
		}else{
			JOptionPane.showMessageDialog(null, "验证码不正确!");
			jtfCheckCode.setText("");
			codePanel.repaint();
			return false;
			
		}
	}
	
	private void login() {
		String userName = jtfUserName.getText();
		String password = new String(jpwfPassword.getPassword());
		if (userName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "用户名不能为空!");
			return;
		}
		if (password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "密码不能为空!");
			return;
		}
		
		if(!check())
			return;
		User user = new User(userName, password);
		try {
			User currentUser = userDao.login(dbUtil.getCon(), user);
			if (currentUser != null) {
				JOptionPane.showMessageDialog(null, "登录成功!");
				this.dispose();
				new ManagerFrame(currentUser.getUserName()).setVisible(true);
			} else
				JOptionPane.showMessageDialog(null, "登录失败，帐号或密码错误!");
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "登录失败!");
		}

	}

}
