package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class CheckCodePanel extends JPanel {

	private ArrayList<String> code = new ArrayList<String>();

	public CheckCodePanel() {
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				repaint();
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setToolTipText("点击切换验证码!");
			}
		});
	}

	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		code.clear();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		Random random = new Random();

		// 添加杂色
		for (int i = 0; i < 80; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			g.fillOval(random.nextInt(getWidth()), random.nextInt(getHeight()), 3, 3);
		}
		// 验证码字符部分
		for (int i = 1; i <= 4; i++) {
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			g.setFont(new Font(getFontName(), random.nextInt(3), random.nextInt(getHeight() - getHeight() / 2) + getHeight() / 2));

			// String c = String.valueOf((char) (random.nextInt(26) + 65));
			String c = getSingleCharactor();
			code.add(c);
			g.drawString(c, (2 * i - 1) * getWidth() / 8, getHeight() / 2 + getHeight() / 4);
		}

	}

	public String getCode() {
		StringBuilder sb = new StringBuilder();
		for (String s : code) {
			sb.append(s);
		}
		return sb.toString();
	}

	// 设置字母的大小写
	private String getSingleCharactor() {
		Random random = new Random();
		int big = random.nextInt(26) + 65;
		int small = random.nextInt(26) + 97;
		int result = random.nextInt(2);
		if (result == 0)
			return String.valueOf((char) big);
		else if (result == 1)
			return String.valueOf((char) small);
		return null;
	}

	// 设置字体
	private String getFontName() {
		Random random = new Random();
		String name = null;
		switch (random.nextInt(4)) {
		case 0:
			name = "Arial";
			break;
		case 1:
			name = "Andalus";
			break;
		case 2:
			name = "Impact";
			break;
		case 3:
			name = "Photoshop Large";
			break;
		default:
			name = "Times New Roman";
			break;
		}
		return name;
	}

}