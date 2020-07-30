package packman;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;



//� ���,� ������ ������ , ��Ģ ���� ���� ���� , ������ ��� �и� �ϱ� ex)ȸ�����Ա�� , git diff �� Ȯ�ΰ��� 
// 
//TODO init  add commit push clone pull checkout-�귱ġ����  ,branch , checkout , diff, status

class PacmanGame extends JFrame implements  KeyListener, Runnable, ComponentListener {
	
	
	int Wall_garo = 20;// ����ü ����
	int Wall_sero =20;// ����ü ����
	int count_heart = 169;// ��Ʈ��
	int blueScoreAmount = 0;
	// ������ ����
	final ImageIcon Icon_wall = new ImageIcon("wall.png");
	final ImageIcon Icon_road = new ImageIcon("road.png");
	final ImageIcon Icon_heart = new ImageIcon("heart.png");
	final ImageIcon Icon_enemy = new ImageIcon("enemy.png");
	final ImageIcon Icon_monsterOrange = new ImageIcon("monsterOrange.png");
	final ImageIcon Icon_monsterPink = new ImageIcon("monsterPink.png");
	final ImageIcon pacmanRight = new ImageIcon("pacmanRight.png");
	final ImageIcon pacmanLeft = new ImageIcon("pacmanLeft.png");
	final ImageIcon pacmanUp = new ImageIcon("pacmanUp.png");
	final ImageIcon pacmanDown = new ImageIcon("pacmanDown.png");
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/*---------------------*/
	
	JFrame PM_frame = new JFrame();
	Container PM_contain = null;
	
	JLabel pacman = new JLabel();// �Ѹǰ�ü����
	JLabel enemy = new JLabel();// ���Ͱ�ü����
	JLabel monsterPink = new JLabel();// ���Ͱ�ü����
	JLabel monsterOrange = new JLabel();// ���Ͱ�ü����
	JLabel blueLabel,blueScore;
	JPanel scorePanel;
	JLabel wall[][] = new JLabel[Wall_garo][Wall_sero];// ������� 2���� �迭 ȭ����ü�� �̹����� ä��
	
	

	public PacmanGame() {	
	PM_contain = PM_frame.getContentPane(); // ���������̶�� ������ �󺧵��̳� ������Ʈ ���� �÷��� �� �ִ�.
	PM_contain.setBackground(Color.black);
	PM_frame.setLayout(null);
	PM_frame.setBounds(0, 0, 1300, 1100);// ����� �������� ȭ��ũ�� �ʺ�� ���� ���� �Ҽ�����. x��ǥ y��ǥ ���� ���� ũ��

	PM_frame.setTitle("PACMAN by TAKDONGWAN");
	PM_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	PM_frame.setVisible(true);
	PM_frame.setResizable(false);

	PM_frame.addKeyListener(this);
	PM_frame.addComponentListener(this);
	
	JFrame.setDefaultLookAndFeelDecorated(true);
	createContentPane();
	init();
	Thread gamestart = new Thread(this);
	gamestart.start();

}

	public JPanel createContentPane() {
		blueLabel = new JLabel("\n\n\n\n\n\n\n\n<score>");
		blueLabel.setLocation(1100, 0);
		blueLabel.setSize(100, 30);
		blueLabel.setHorizontalAlignment(0);
		blueLabel.setForeground(Color.white);
		PM_contain.add(blueLabel);
		
		scorePanel = new JPanel();
		scorePanel.setLayout(null);
		scorePanel.setLocation(1100, 40);
		scorePanel.setSize(100, 30);
		PM_contain.add(scorePanel);
		
		
		blueScore = new JLabel("0");
		blueScore.setLocation(0, 0);
		blueScore.setSize(100, 30);
		blueScore.setHorizontalAlignment(0);
		scorePanel.add(blueScore);
		
		((JComponent) PM_contain).setOpaque(true);
		return (JPanel) PM_contain;
	}
	
	static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(true);

	}
	

	public void init() {
		
		
		// ���� �Ӽ� ���� ��ũ�� , ������ �������� , ������
		monsterPink.setBounds(440, 430, 50, 50);// 200 200 �� ��ġ�� , 50 50 �� monster png ũ��
		monsterPink.setIcon(Icon_monsterPink);
		PM_contain.add(monsterPink);

		monsterOrange.setBounds(600, 340, 55, 55);
		monsterOrange.setIcon(Icon_monsterOrange);
		PM_contain.add(monsterOrange);
		
		enemy.setBounds(400, 300, 50, 50);// (400,300,50,50)  //��ü ��ġ ���� ������ 400 , 350��ġ�� , ��ü ũ�� 50 50 ��
		enemy.setIcon(Icon_enemy);
		PM_contain.add(enemy);

		// �Ѹ� �Ӽ�����
		pacman.setBounds(50, 900, 50, 50);// �⺻��ġ�� ũ��(x,y,xũ��,yũ��)
		pacman.setIcon(pacmanRight);
		PM_contain.add(pacman);

		// ������.�ϴ� ��� ������ ������ ����� ��� ������ ������ �������Ŀ� �� ��ġ���� ������ ��ġ��.
		for (int i = 0; i < Wall_garo; i++) {
			for (int j = 0; j < Wall_sero; j++) {
				wall[i][j] = new JLabel();
				wall[i][j].setBounds(i * 50, j * 50, 50, 50);// ���μ��� ũ�� ,�������� ���μ��� ũ�Ⱚ ,setbound �� ��ġ�� ũ�⸦ �������ִ� �޼ҵ��4����
				PM_contain.add(wall[i][j]);
				wall[i][j].setIcon(Icon_wall);
			}
		}

		for (int i = 1; i < 19; i++) {
			for (int j = 1; j < 19; j++) {
				wall[i][j].setIcon(Icon_heart);
			}
		}
		
		// Coin�������� ��ġ
		
		for(int i=3; i<18;i ++) {
			wall[i][2].setIcon(Icon_wall);
		}
			
		for(int i=3; i <18; i++) {
			wall[3][i].setIcon(Icon_wall);
			System.out.println();
		}
		for(int i=3; i <18; i++) {
			wall[17][i].setIcon(Icon_wall);
			System.out.println();
		}
		for(int i=3; i <15; i++) {
			wall[i][17].setIcon(Icon_wall);
		}
		
	

		
	
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
  
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void run() {// ���Ͱ� �����̱� ���� �Լ�.

		String temp = "";

		temp = JOptionPane.showInputDialog("Press  ENTER");

		// ����ڰ� ��ҹ�ư�� �����ų� -1�� �Է��ϸ� â�� �ݴ´�.
		if (temp == null || temp.equals("-1")) {
			PM_frame.dispose();
		}
		// ���͵��� �̵� ����� �������̳� ���ٸ����� ���ö����� �����ΰ��� �������̳� ���ٸ� ���� ������ �����Լ��� ����Ͽ� ������ �������Ѵ�.
		int location_enemy_x = enemy.getX();// ������ x��ǥ��
		int location_enemy_y = enemy.getY();// ������ y��ǥ��

		int location_monsterOrange_x = monsterOrange.getX();// ������ x��ǥ��
		int location_monsterOrange_y =monsterOrange.getY();// ������ y��ǥ��

		int location_monsterPink_x = monsterPink.getX();
		int location_monsterPink_y = monsterPink.getX();
		
		
		int monster_move = 0;
		int monsterPink_move=0;
		int monsterOrange_move=0;
		int Up = 0, Down = 0, Left = 0, Right = 0;

		// ���Ͱ� �ڵ����� �����̰� �ϱ����� �ڵ�

		while (PM_frame.isFocused()) { // isFocused�� true , false �����Լ�

			try {
				monster_move = (int) (Math.random() * 4);// switch ���� ���ڰ� �������� ������ �ϱ� ���� ����.
				monsterPink_move = (int) (Math.random() * 4);// switch ���� ���ڰ� �������� ������ �ϱ� ���� ����.
				monsterOrange_move = (int) (Math.random() * 4);// switch ���� ���ڰ� �������� ������ �ϱ� ���� ����.
				
				// �������� ���Ḧ ��Ű�ų� enemy�� �����̱����� ��ü����
				Point monster_collision, packman_collision,monsterOrange_collision,monsterPink_collision;

				Rectangle monsterOrange_area =new Rectangle();
				Rectangle monsterPink_area =new Rectangle();
				Rectangle monster_area = new Rectangle();
				Rectangle packman_area = new Rectangle();
				
				Rectangle left_area_of_monster = new Rectangle();
				Rectangle right_area_of_monster = new Rectangle();
				
				Rectangle left_area_of_monsterPink = new Rectangle();
				Rectangle right_area_of_monsterPink = new Rectangle();
				
				Rectangle left_area_of_monsterOrange = new Rectangle();
				Rectangle right_area_of_monsterOrange = new Rectangle();

				//int 10 = 2; // ������ �̵��Ÿ� �⺻�� .
				int delay = 10;
				int speed;
				boolean strait = true; // ������ ���� �ѹ��������� ���� ����(0,1)
				
				switch(monsterOrange_move) 
				{
				case 0:// �����̵�
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterOrange_y -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterOrange.setLocation(location_monsterOrange_x, location_monsterOrange_y);// ��ǥ�� ����.

						monsterOrange_collision = monsterOrange.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.
						monsterOrange_area = new Rectangle(monsterOrange_collision.x, monsterOrange_collision.y,50,50);
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.
						left_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x - 50, monsterOrange_collision.y + 49, 50, 1);
						right_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x + 50, monsterOrange_collision.y + 49, 50, 1);
						
						if (monsterOrange_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monsterOrange_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��


								if ((left_area_of_monsterOrange.intersects(wall_area))|| (right_area_of_monsterOrange.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Up == 0) 
										{
											strait = false;
											Up = 50 / 2;
											continue;
										}
										Up--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�

							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterOrange_y -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;

				case 1:// �Ʒ����̵�
					while (strait == true) {
						Thread.sleep(delay);
						speed = 2;
						location_monsterOrange_y += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterOrange.setLocation(location_monsterOrange_x, location_monsterOrange_y);// ��ǥ�� ����.

						monsterOrange_collision = monsterOrange.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterOrange_area = new Rectangle(monsterOrange_collision.x, monsterOrange_collision.y,50,50);
						
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x - 50, monsterOrange_collision.y + 50, 50, 1);
						right_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x + 50, monsterOrange_collision.y + 50, 50, 1);						
						

						if (monsterOrange_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������


								if (monsterOrange_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��

								// ���� �����ʿ� ���� �ִ��� �˻�
								
								if ((left_area_of_monsterOrange.intersects(wall_area))|| (right_area_of_monsterOrange.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Down == 0) 
										{
											strait = false;
											Down = 50 / 2;
											continue;
										}
										Down--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�

							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} //
						location_monsterOrange_y += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} 

					break;
				case 2: // �����̵�.
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed =2;
						location_monsterOrange_x -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterOrange.setLocation(location_monsterOrange_x, location_monsterOrange_y);// ��ǥ�� ����.

						monsterOrange_collision = monsterOrange.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterOrange_area = new Rectangle(monsterOrange_collision.x, monsterOrange_collision.y,50,50);
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x +49, monsterOrange_collision.y - 50, 1, 50);
						right_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x + 49, monsterOrange_collision.y+ 50, 1, 50);						
						
						if (monster_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������

						if (monsterOrange_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						if (monsterPink_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monsterOrange_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��


								
								if ((left_area_of_monsterOrange.intersects(wall_area))|| (right_area_of_monsterOrange.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Left == 0) 
										{
											strait = false;
											Left = 50 / 2;
											continue;
										}
										Left--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} 

							} 

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterOrange_x -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;

				case 3:// ���������� �̵� 
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterOrange_x += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterOrange.setLocation(location_monsterOrange_x, location_monsterOrange_y);// ��ǥ�� ����.

						monsterOrange_collision = monsterOrange.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterOrange_area = new Rectangle(monsterOrange_collision.x, monsterOrange_collision.y,50,50);
						
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x , monsterOrange_collision.y - 50, 1,50);
						right_area_of_monsterOrange = new Rectangle(monsterOrange_collision.x , monsterOrange_collision.y + 50, 1,50);						
						

						if (monsterOrange_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������
								if (monsterOrange_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��

								if ((left_area_of_monsterOrange.intersects(wall_area))|| (right_area_of_monsterOrange.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Right == 0) 
										{
											strait = false;
											Right = 50 / 2 -1 ;
											continue;
										}
										Right--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterOrange_x += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;
				
				
				}
				strait = true;
				
				switch (monster_move) {

				case 0:// �����̵�

					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_enemy_y -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						enemy.setLocation(location_enemy_x, location_enemy_y);// ��ǥ�� ����.

						monster_collision = enemy.getLocation();// ���� ��ǥ ����
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monster_area = new Rectangle(monster_collision.x, monster_collision.y, 50, 50);// ������ ������ ����
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monster = new Rectangle(monster_collision.x - 50, monster_collision.y + 49, 50, 1);// ���Ϳ������������
						right_area_of_monster = new Rectangle(monster_collision.x + 50, monster_collision.y + 49, 50,1);// ���Ϳ�������������� �߽ɱ��� ������.
						if (monster_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������
								if (monster_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��
								// ���� �����ʿ� ���� �ִ��� �˻�
								if ((left_area_of_monster.intersects(wall_area))|| (right_area_of_monster.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Up == 0) 
										{
											strait = false;
											Up = 50 / 2;
											continue;
										}
										Up--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
							} // for �� j ��
							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} 
						location_enemy_y -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					}

					break;
				case 1:// �Ʒ����̵�
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_enemy_y += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						enemy.setLocation(location_enemy_x, location_enemy_y);// ��ǥ�� ����.

						monster_collision = enemy.getLocation();// ���� ��ǥ ����
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.
						monster_area = new Rectangle(monster_collision.x, monster_collision.y, 50, 50);// ������ ������ ����
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.
						left_area_of_monster = new Rectangle(monster_collision.x - 50, monster_collision.y , 50, 1);// ���Ϳ������������
						right_area_of_monster = new Rectangle(monster_collision.x + 50, monster_collision.y, 50,1);// ���Ϳ�������������� �߽ɱ��� ������				
						
						if (monster_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������
								if (monster_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��
								// ���� �����ʿ� ���� �ִ��� �˻�
								if ((left_area_of_monster.intersects(wall_area))|| (right_area_of_monster.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Down == 0) 
										{
											strait = false;
											Down= 50 /2;
											continue;
										}
										Down--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
								
							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_enemy_y += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;
				case 2: // �����̵�.
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_enemy_x -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						enemy.setLocation(location_enemy_x, location_enemy_y);// ��ǥ�� ����.

						monster_collision = enemy.getLocation();// ���� ��ǥ ����
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monster_area = new Rectangle(monster_collision.x, monster_collision.y, 50, 50);// ������ ������ ����
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monster = new Rectangle(monster_collision.x + 49, monster_collision.y - 50, 1, 50);// ���Ϳ������������
						right_area_of_monster = new Rectangle(monster_collision.x +49, monster_collision.y +50, 1,1);// ���Ϳ�������������� �߽ɱ��� ������				
						
						if (monster_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������

						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monster_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��

								// ���� �����ʿ� ���� �ִ��� �˻�
								if ((left_area_of_monster.intersects(wall_area))|| (right_area_of_monster.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Left == 0) 
										{
											strait = false;
											Left= 50 /2;
											continue;
										}
										Left--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
								
							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_enemy_x -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;

				case 3:// ���������� �̵� 
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_enemy_x += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						enemy.setLocation(location_enemy_x, location_enemy_y);// ��ǥ�� ����.

						monster_collision = enemy.getLocation();// ���� ��ǥ ����
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monster_area = new Rectangle(monster_collision.x, monster_collision.y, 50, 50);// ������ ������ ����
						
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monster = new Rectangle(monster_collision.x, monster_collision.y - 50, 1, 50);// ���Ϳ������������
						right_area_of_monster = new Rectangle(monster_collision.x , monster_collision.y + 50, 1,50);// ���Ϳ�������������� �߽ɱ��� ������				
						
						if (monster_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monster_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��


								// ���� �����ʿ� ���� �ִ��� �˻�
								if ((left_area_of_monster.intersects(wall_area))|| (right_area_of_monster.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Right == 0) 
										{
											strait = false;
											Right= 50 /2-1;
											continue;
										}
										Right--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�

							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_enemy_x += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��
					break;
				
				}
				strait = true;
				
				switch(monsterPink_move){

				case 0:// �����̵�

					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterPink_y-= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterPink.setLocation(location_monsterPink_x, location_monsterPink_y);// ��ǥ�� ����.

						monsterPink_collision = monsterPink.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterPink_area = new Rectangle(monsterPink_collision.x, monsterPink_collision.y,50,50);
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						
						left_area_of_monsterPink = new Rectangle(monsterPink_collision.x - 50, monsterPink_collision.y +49, 50, 1);
						right_area_of_monsterPink = new Rectangle(monsterPink_collision.x + 50, monsterPink_collision.y + 49, 50, 1);
						

						if (monsterPink_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������


								if (monsterPink_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��
								
								if ((left_area_of_monsterPink.intersects(wall_area))|| (right_area_of_monsterPink.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Up == 0) 
										{
											strait = false;
											Up = 50 / 2;
											continue;
										}
										Up--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
								

							} // for �� j ��

							if (speed == -4) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterPink_y -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;

				case 1:// �Ʒ����̵�
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterPink_y += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterPink.setLocation(location_monsterPink_x, location_monsterPink_y);// ��ǥ�� ����.

						monsterPink_collision = monsterPink.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterPink_area = new Rectangle(monsterPink_collision.x, monsterPink_collision.y,50,50);
						
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterPink = new Rectangle(monsterPink_collision.x - 50, monsterPink_collision.y + 50, 50, 1);
						right_area_of_monsterPink = new Rectangle(monsterPink_collision.x + 50, monsterPink_collision.y + 50, 50, 1);
						

						if (monsterPink_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						
						
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monsterPink_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��

								
								if ((left_area_of_monsterPink.intersects(wall_area))|| (right_area_of_monsterPink.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Down == 0) 
										{
											strait = false;
											Down = 50 /2;
											continue;
										}
										Down--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
								

							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterPink_y += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;
					
				case 2: // �����̵�.
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterPink_x -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterPink.setLocation(location_monsterPink_x, location_monsterPink_y);// ��ǥ�� ����.

						monsterPink_collision = monsterPink.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterPink_area = new Rectangle(monsterPink_collision.x, monsterPink_collision.y,50,50);
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterPink = new Rectangle(monsterPink_collision.x +49, monsterPink_collision.y - 50, 1,50);
						right_area_of_monsterPink = new Rectangle(monsterPink_collision.x +49, monsterPink_collision.y + 50,1, 50);
						

						if (monsterPink_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������

								if (monsterPink_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��
								if ((left_area_of_monsterPink.intersects(wall_area))|| (right_area_of_monsterPink.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Left == 0) 
										{
											strait = false;
											Left = 50 / 2;
											continue;
										}
										Left--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
							} // for �� j ��
							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterPink_x -= speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��

					break;

				case 3:// ���������� �̵� 
					while (strait == true) {
						Thread.sleep(delay);// ���� ���� �ϱ�.
						speed = 2;
						location_monsterPink_x += speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �ε����� �� Ȯ���ϴ� �ڵ忡�� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�

						monsterPink.setLocation(location_monsterPink_x, location_monsterPink_y);// ��ǥ�� ����.

						monsterPink_collision = monsterPink.getLocation();
						packman_collision = pacman.getLocation();// �Ѹ���ǥ ���´�.

						monsterPink_area = new Rectangle(monsterPink_collision.x, monsterPink_collision.y,50,50);
						
						packman_area = new Rectangle(packman_collision.x, packman_collision.y, 50, 50);// �Ѹ��� ������ ����.

						left_area_of_monsterPink = new Rectangle(monsterPink_collision.x, monsterPink_collision.y -50, 1,50);
						right_area_of_monsterPink = new Rectangle(monsterPink_collision.x, monsterPink_collision.y + 50, 1, 50);
						

						
						if (monsterPink_area.intersects(packman_area))// �Ѹ��� ������ ������ ������ intersects �ϸ�
						{
							failMassage();
							break;
						} // End �Ѹǰ� ���Ͱ� ������
						// ������ �������
						for (int i = 0; i < Wall_garo; i++) {
							for (int j = 0; j < Wall_sero; j++) {
								Point wall_location = wall[i][j].getLocation();// ������ ��ǥ�� ����
								Rectangle wall_area = new Rectangle(wall_location.x, wall_location.y, 50, 50);// �������������


								if (monsterPink_area.intersects(wall_area))// �ΰ��� ������ ��ġ���� Ȯ���ϴ� if��
								{
									if ((wall[i][j].getIcon()).equals(Icon_wall)) {
										speed = -2;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
										strait = false;// while�� ���߱����ؼ� �ۼ�
										break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
									} else
										speed = 0; // �浹������������ ���ڸ��� ��ġ �ϰ� �Ʒ� if ������ ��.
								} // End �ΰ����� ��ġ�� �˻� ��
								if ((left_area_of_monsterPink.intersects(wall_area))|| (right_area_of_monsterPink.intersects(wall_area))) 
								{
									if (((wall[i][j].getIcon()).equals(Icon_heart)) || ((wall[i][j].getIcon()).equals(Icon_road))) 
									{
										if (Right == 0) 
										{
											strait = false;
											Right= 50/2-1;
											continue;
										}
										Right--;// �������� 0 or 1�̴ϱ� �ٸ� ���� üũ�ϱ����� 1 �ΰ�� �ٽ� 0���� ����.
									}
								} // end ���� �����ʿ� ���� �ִ��� �˻�
								

							} // for �� j ��

							if (speed == -2) // �̵����� �̵��İ� ������� Break �ٽ� �����̰� �ؾ� ��.
							{
								break;
							}
						} // for�� i �� ������ ������� ������ ��ȣ
						location_monsterPink_x += speed; // ���� �ö����� ���ư��� SWITCH ���̴ϱ� �ٽ� �Ʒ��� ���������.
					} // while�� ��
					break;
					
				}
				strait = true;
	
				enemy.setLocation(location_enemy_x, location_enemy_y);// �̵� �� ������ġ
				monsterPink.setLocation(location_monsterPink_x,location_monsterPink_y);
				monsterOrange.setLocation(location_monsterOrange_x,location_monsterOrange_y);
			} catch (Exception e) {
			
			}
			
			
		} // ȭ������

	}//run ����

		

	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		String temp = "";
		if (blueScoreAmount == 268) {
			
			temp = JOptionPane.showInputDialog("success");
		}
		// ����ڰ� ��ҹ�ư�� �����ų� -1�� �Է��ϸ� â�� �ݴ´�.
		if (temp == null || temp.equals("-1")) {
			PM_frame.dispose();
		}

		// �Ѹ� �̵�
		int pacman_location_x = pacman.getX();// move�� x��ǥ��
		int pacman_location_y = pacman.getY();// move�� y��ǥ��

		// �浹�׽�Ʈ�� ���� �غ�
		Point pacman_location = pacman.getLocation();
		Rectangle pacman_area = new Rectangle();
	int speed = 10;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			pacman.setIcon(pacmanUp);
			pacman_location_y -= speed;// �ϴ� ��ġ�� �ű� �� �Ʒ� �浹�׽�Ʈ���� �浹�� �߻��ϸ� �ٽ� ���ڸ��� ���ƿ�
			pacman.setLocation(pacman_location_x, pacman_location_y);

			pacman_location = pacman.getLocation();// �Ѹ��� ��ǥ ����
			pacman_area = new Rectangle(pacman_location.x, pacman_location.y, 50, 50);// �Ѹ� ������ ����

			for (int i = 0; i < Wall_garo; i++) {
				for (int j = 0; j < Wall_sero; j++) {
					Point desired_location = wall[i][j].getLocation();// ��ǥ�� ����
					Rectangle desired_location_area = new Rectangle(desired_location.x, desired_location.y, 50, 50);// ������
																													// ����
					if (pacman_area.intersects(desired_location_area))// �ΰ��� ������ ��ġ�� �˻� ����
					{
						if ((wall[i][j].getIcon()).equals(Icon_wall)) {
							speed = -10;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
							break;// �ѹ��̶� �浹�� �߻��ϸ� ���̻� ������ �ݺ����� ����
						} else if ((wall[i][j].getIcon()).equals(Icon_heart)) {// �̵��� ��ġ�� ��Ʈ�� �ִٸ�,,
							speed = 0; // ���� ��ġ�� �����ϰ�
							wall[i][j].setIcon(Icon_road); // ������ �Ծ����Ƿ� ���� ��� ��� �����.
							++blueScoreAmount;
							blueScore.setText("" +blueScoreAmount );
							count_heart--; // ������ü ���� 1�� ����
						} else {
							speed = 0;
						}
					} // End �ΰ����� ��ġ�� �˻� ��
				} // for(j)��
				if (speed == -10)
					break;
			} // for(i)��
			pacman_location_y -= speed;

			break;
		case KeyEvent.VK_DOWN:
			pacman.setIcon(pacmanDown);
			pacman_location_y += speed;
			pacman.setLocation(pacman_location_x, pacman_location_y);

			pacman_location = pacman.getLocation();// �Ѹ��� ��ǥ ����
			pacman_area = new Rectangle(pacman_location.x, pacman_location.y, 50, 50);// �Ѹ� ������ ����
			for (int i = 0; i < Wall_garo; i++) {
				for (int j = 0; j < Wall_sero; j++) {
					Point desired_location = wall[i][j].getLocation();// Ÿ���� ��ǥ�� ����
					Rectangle desired_location_area = new Rectangle(desired_location.x, desired_location.y, 50, 50);// Ÿ��
																													// ������
																													// ����
					if (pacman_area.intersects(desired_location_area))// �ΰ��� ������ ��ġ�� �˻� ����
					{
						if ((wall[i][j].getIcon()).equals(Icon_wall)) {
							speed = -10;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
							break;
						} else if ((wall[i][j].getIcon()).equals(Icon_heart)) {
							speed = 0; // �浹 �߻����� ������ ���ڸ���
							wall[i][j].setIcon(Icon_road);
							++blueScoreAmount;
							blueScore.setText("" +blueScoreAmount );
							count_heart--;
						} else {
							speed = 0;
						}
					} // End �ΰ����� ��ġ�� �˻� ��
				} // for(j)��
				if (speed == -10)
					break;
			} // for(i)��
			pacman_location_y += speed;
			break;

		case KeyEvent.VK_LEFT:
			pacman.setIcon(pacmanLeft);
			pacman_location_x -= speed;
			pacman.setLocation(pacman_location_x, pacman_location_y);

			pacman_location = pacman.getLocation();// �Ѹ��� ��ǥ ����
			pacman_area = new Rectangle(pacman_location.x, pacman_location.y, 50, 50);// �Ѹ� ������ ����
			for (int i = 0; i < Wall_garo; i++) {
				for (int j = 0; j < Wall_sero; j++) {
					Point desired_location = wall[i][j].getLocation();// Ÿ���� ��ǥ�� ����
					Rectangle desired_location_area = new Rectangle(desired_location.x, desired_location.y, 50, 50);// Ÿ��
																													// ������
																													// ����

					if (pacman_area.intersects(desired_location_area))// �ΰ��� ������ ��ġ�� �˻� ����
					{
						if ((wall[i][j].getIcon()).equals(Icon_wall)) {
							speed = -10;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
							break;
						} else if ((wall[i][j].getIcon()).equals(Icon_heart)) {
							speed = 0; // �浹 �߻����� ������ ���ڸ���
							wall[i][j].setIcon(Icon_road);
							++blueScoreAmount;
							blueScore.setText("" +blueScoreAmount );
							count_heart--;
						} else {
							speed = 0;
						}
					} // End �ΰ����� ��ġ�� �˻� ��
				} // for(j)��
				if (speed == -10)
					break;
			} // for(i)��
			pacman_location_x -= speed;
			break;
		case KeyEvent.VK_RIGHT:
			pacman.setIcon(pacmanRight);
			pacman_location_x += speed;
			pacman.setLocation(pacman_location_x, pacman_location_y);

			pacman_location = pacman.getLocation();// �Ѹ��� ��ǥ ����
			pacman_area = new Rectangle(pacman_location.x, pacman_location.y, 50, 50);// �Ѹ� ������ ����
			for (int i = 0; i < Wall_garo; i++) {
				for (int j = 0; j < Wall_sero; j++) {
					Point desired_location = wall[i][j].getLocation();// ��ġ ��ǥ�� ����
					Rectangle desired_location_area = new Rectangle(desired_location.x, desired_location.y, 50, 50);// ��ġ
																													// ��ǥ��
																													// ������
																													// ����
					if (pacman_area.intersects(desired_location_area))// �ΰ��� ������ ��ġ�� �˻� ����
					{
						if ((wall[i][j].getIcon()).equals(Icon_wall)) {
							speed = -10;// �浹�߻��ϸ� �ٷ� ����ġ�� ���ƿ�
							break;
						} else if ((wall[i][j].getIcon()).equals(Icon_heart)) {
							speed = 0; // �浹 �߻����� ������ ���ڸ���
							wall[i][j].setIcon(Icon_road);
							++blueScoreAmount;
							blueScore.setText("" +blueScoreAmount );
							count_heart--;
						} else {
							speed = 0;
						}
					} // End �ΰ����� ��ġ�� �˻� ��
				} // for J �� ����
				if (speed == -10)
					break;
			} // for(i)��
			pacman_location_x += speed;
			break;

		}
	
		
		pacman.setLocation(pacman_location_x, pacman_location_y);// ����Ű ��� �� ������ġ

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void failMassage() {
		JOptionPane.showMessageDialog(PM_frame, "Fail ");
		PM_frame.dispose();
	}

}