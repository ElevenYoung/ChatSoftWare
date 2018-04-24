package Main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//�������촰��
/**
 * 
 * @author Young
 *
 */
public class ChatWindow extends JFrame{
	private JButton sendButton;//���Ͱ�ť
	private JButton cancelButton;//ȡ����ť
	private JButton receiveButton;//���հ�ť
	private JTextArea receiveText;//������
	private JTextField sendText;//������
	private JLabel localPort;//��ǰ �û��Ķ˿ں�
	private JTextField remoteAddress;// Զ��IP��ַ��
	private JTextField remoteport;//Զ�̶˿ں�
	private SendThread sendThread;
	private ReceiveThread receiveThread;
	
	private void GUIini() {//GUI����
		// TODO Auto-generated method stub
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
        setSize(400,500);
//         ��һ��Panel
        JPanel panel1 = new JPanel(new GridLayout(4,2));
        panel1.setSize(400, 75);
        panel1.add(new JLabel("��ǰ������IP��ַ�ǣ�"));
        try {
			panel1.add(new JLabel(InetAddress.getLocalHost().getHostAddress()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			panel1.add(new JLabel("��ǰ������IP��ַ�ǣ�"+"Unknown"));
		}
        panel1.add(new JLabel("��ǰ�Ի��Ķ˿��ǣ�"));
        panel1.add(localPort = new JLabel(" "+0));
        panel1.add(new JLabel("Զ��������IP��ַ�ǣ�"));
        remoteAddress = new JTextField();
        remoteAddress.setColumns(0);
        panel1.add(remoteAddress);
        panel1.add(new JLabel("Զ�������Ķ˿ں��ǣ�"));
        remoteport = new JTextField();
        remoteport.setColumns(0);
        panel1.add(remoteport);
        c.add(panel1,BorderLayout.NORTH);
        
//        �ڶ���panel
        JPanel panel2 = new JPanel();
        panel2.setSize(400, 200);
        panel2.add(new JLabel("�յ�������"));
        receiveText = new JTextArea(15, 30);
        receiveText.setEditable(false);
        receiveText.setAutoscrolls(true);
        JScrollPane jsp = new JScrollPane(receiveText);
        panel2.add(jsp);
        
//        ������panel
        JPanel panel3 = new JPanel();
        panel2.add(new JLabel("�����뷢�͵�����:"));
        sendText = new JTextField(30);
        sendText.setAutoscrolls(true);
        panel2.add(sendText);
        c.add(panel2,BorderLayout.CENTER);
        c.add(panel3);
        
//        ���ĸ�panel
        JPanel panel4 = new JPanel(new GridLayout(1,0));
        panel4.setSize(400, 200);
        receiveButton = new JButton("��ʼ��������");
        sendButton = new JButton("����");
        cancelButton = new JButton("ȡ��");
        panel4.add(receiveButton);
        panel4.add(cancelButton);
        panel4.add(sendButton);
        c.add(panel4, BorderLayout.SOUTH);
//        �����ĸ�����������
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void ActionIni(){
//		���̶���
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
					//�����ı�
					sendThread.sendMessage(remoteAddress.getText(), Integer.parseInt(remoteport.getText()), sendText.getText());
					receiveText.setText(receiveText.getText()+"\n"+"���ͣ�"+sendText.getText());
					
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//		ȡ����ť�Ķ���
		cancelButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				sendText.setText("");
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		sendButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				sendThread.sendMessage(remoteAddress.getText(),Integer.parseInt(remoteport.getText()), sendText.getText());
				receiveText.setText(receiveText.getText() + "���ͣ�" + sendText.getText() + '\n');
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void ThreadIni() {
		// TODO Auto-generated method stub
		sendThread = new SendThread(0, this);
		receiveThread = new ReceiveThread(this);
	}
	public ChatWindow(){ 
		GUIini();
		ActionIni();
		ThreadIni();
	}
    public void printError(String err) {
		// TODO Auto-generated method stub
    	System.out.println("Error occur:"+err);
	}

//    �ص��������������ݴ��߳��з��ص�����
    public void setReceive(String receive){ 
    	receiveText.setText(receiveText.getText()+"�յ���"+receive+'\n');
    }
    
//    ���������ݵ��߳̿�ʼ����֮�󣬵��øûص����������õ�ǰ����ʹ�õĶ˿������ĸ�
    public void setLocalPort(int localPortText){ 
    	localPort.setText(" "+localPortText);
    }
    
//    ������������
    public static void main(String[] args) {
		new ChatWindow();
	}
}