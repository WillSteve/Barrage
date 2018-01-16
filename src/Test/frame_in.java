package Test;


import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.sql.*;

import org.sqlite.JDBC;

public  class frame_in extends JFrame{
	
	 private JTextPane test=new JTextPane();
	 private JPanel p=new JPanel();
	 public static int id=1;//id���
	 
	 Font font=new Font("����",Font.PLAIN,40);

 public frame_in()
 {
	 
	 //����frame͸��	 
	 this.setUndecorated(true);	
	 this.setBackground(new Color(0, 0, 0, 0));
	 
	 //����JTextPane͸��
	test.setOpaque(false);
	test.setEditable(false);
	//���������С����ɫ��
	test.setFont(font);
	test.setForeground(Color.RED);
	
	//������壬���ֻ���һ��test��JTextPane���ɣ�test����������ʾ
    p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
	p.add(test);
	p.setOpaque(false);
	
	//����JFrame�����p���
	this.add(p);
	this.setExtendedState(Frame.MAXIMIZED_BOTH);
	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 this.setAlwaysOnTop(true);
	 this.setVisible(true);
	
	 Timer timer=new Timer();
	 //ÿ2000��������TextTimer���򣬼�����ݿ������Ƿ���£����¼����
	 timer.schedule(new TextTimer()	 , 2000);

 }
 
public class TextTimer extends TimerTask{
	public void run()
	 {
		 try
			{
			    //����SQLite���ݿ�
				Class.forName("org.sqlite.JDBC");
				Connection conn=DriverManager.getConnection("jdbc:sqlite:test.sqlite3");
				Statement stat=conn.createStatement();
				//��Ѱcontent���
				ResultSet rs=stat.executeQuery("select * from content;");
				
				//JTextPane��ʾ������,��ʽΪString
				String page="";
				
				//�����id���£��ͽ�text��������ӽ���page���Ӷ���JTextPane����ʾ����
				while(rs.next())
				{
					String x=rs.getString("id");
					int temp=Integer.parseInt(x);
					
					if(temp==id)
					{
						page+=rs.getString("text");
						page+="\n";
						test.setText(page);
						id++;
					}
					//�ڴ�ͣ��1500����
					Thread.sleep(1500);
				}
				//���������ʾ���Ժ󣬽����������Ϊ��һ����׼��
				test.setText(" ");
				rs.close();
				conn.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	 }
}
 
}
