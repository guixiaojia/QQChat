package Frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatTread;
import UserSocket.Client;
import _Util.CommandTranser;

	//改进参考 https://blog.csdn.net/qq_33531400/article/details/52839439
	//JLabel 		标签:主要用于展示 文本 或 图片，也可以 同时显示文本和图片
	//JButton 		按钮：点击事件
	//JTextArea 	单行文本框:编辑单行的文本框
	//JPasswordArea 一个只能输入数字的密码框 把输入的内容用其他字符回显
	//JTabledPlan 	选项卡面板。它允许用户通过点击给定标题或图标的选项卡，在一组组件之间进行切换显示
	//JCheckBox 	复选框，是否被选中
	//JPlanel JPanel面板:然后在面板中使用布局管理器（管理按钮的布局）一个窗口只能有一个JFrame，能有多个JPlanel
	//Imagin.SCALE_DEFAULT 自适应JLabel的大小
	//getScaledInstance 创建此图像的缩放版本。返回一个新的 image 对象，默认情况下，该对象按指定的 width 和 height 呈现图像。
	//SetIcon图标将会被自动地放到按钮的上面,缺省时居中放置
	//setborderpainted 如果进度条应该绘制边框，则为 true；否则为 false
 	//可以改进登录窗口弹出后 一段时间后自动关闭 见https://blog.csdn.net/qq_24448899/article/details/75731529
 
/**主窗口页面
* 	1：如果点击了登录按钮 首先判断帐号或者密码是否为空 然后封装为CommandTranser对象 向服务器发送数据 服务器通过与数据库的比对
* 	来验证帐号密码，
* 	2：如果点击了注册账号就弹出注册页面, 信息填写完整后连接服务器
* 	3：如果点击了忘记密码弹出找回密码页面
*/
public class MainFrame extends JFrame implements ActionListener, FocusListener {
	
	private static final long serialVersionUID = 1L;
	private static final String _txt_account = "手机/邮箱";
	private static final String _txt_pwd = "密码";
	private static final String _txt_title = "QQ登录";
	private static final String _txt_registe = "注册";
	private static final String _txt_forget = "忘记密码";
	private static final String _txt_blank = "";
//	private JLable txt_account, txt_pwd;
	private JTextField account;
	private JPasswordField pwd;
	private JLabel upper_frame;
	private JPanel lower_frame, center_frame;
	private JButton login, register, forget;

	
	
	
	
	
	
	MainFrame() {
		//部分的形成
		init();
		//整体形成
		
//		add(upper_frame, BorderLayout.NORTH);
		add(center_frame, BorderLayout.CENTER);
		add(lower_frame, BorderLayout.SOUTH);
		
		setBounds(500, 230, 430, 360); //设定大小及位置
		setResizable(false); //登录框大小固定，不允许通过拖、拉改变大小
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //设置窗口右上角的叉号，点击叉号窗口关闭 注意不能EXIT_ON_CLOSE做参数的，用它时候使用的是System.exit方法退出应用程序。故会关闭所有窗口。
		setTitle(_txt_title);
		setVisible(true); //说明数据模型已经构造好了,允许JVM可以根据数据模型执行paint方法开始画图并显示到屏幕上，一般放在最后一句
	}
	public void init() {
		//账号输入块
		account = new JTextField(_txt_account);
		account.setName("account");
		account.setForeground(Color.gray);
		//account.setLocation(110, 165); //确定大小及位置
		//account.setSize(210, 30);
		//setContentPane(account);
		account.addFocusListener(this); //产生事件响应用户行为

		//密码输入块
		pwd = new JPasswordField(_txt_pwd);
		pwd.setName("pwd");
		pwd.setForeground(Color.gray);
		pwd.setEchoChar('\0'); //启动后密码框内一定为 “密码”
		//pwd.setLocation(110, 200);
		//pwd.setSize(210, 30);
		pwd.addFocusListener(this);

		//注册模块
		register = new JButton(_txt_registe);
		register.setBorderPainted(false);
		register.setBorder(BorderFactory.createRaisedBevelBorder()); //斜面边框（凸）
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		register.addActionListener(this);

		//忘记密码模块
		forget = new JButton(_txt_forget);
		forget.setBorderPainted(false);
		forget.setBorder(BorderFactory.createRaisedBevelBorder());
		forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		forget.addActionListener(this);
				
		//login按钮模块
		login = new JButton();
		ImageIcon login_image = new ImageIcon("image/login_image.png");
		login_image.setImage(login_image.getImage().getScaledInstance(430, 35, Image.SCALE_DEFAULT));
		login.setIcon(login_image);
		login.setBackground(Color.white);
		login.setBorderPainted(false); //如果进度条应该绘制边框，则为 true；否则为 false
		login.setBorder(null); //设置此组件的边框 无
		login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //将光标设为 “小手”形状
		login.addActionListener(this);
						
		//qq登录框架上半部分
//		ImageIcon upper_image = new ImageIcon("image/upper_image.png");
//		upper_image.setImage(upper_image.getImage().getScaledInstance(430, 160, Image.SCALE_DEFAULT));
//		upper_frame = new JLabel(upper_image);
				
		//qq登录中间部分 (账号、密码、 注册、forget)
		center_frame = new JPanel();
		center_frame.setName("center_frame");
		center_frame.setLayout(null);
		center_frame.setLayout(new GridLayout(3, 3, 3, 15)); //这里用到3行3列的空间, 用空格填充
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(account);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(pwd);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(register);
		center_frame.add(new JLabel(_txt_blank, JLabel.CENTER));
		center_frame.add(forget);
		center_frame.setBackground(Color.white);
				
		//qq登录框架的下半部分login
		lower_frame = new JPanel();
		lower_frame.setName("lower_frame");
		lower_frame.setLayout(null);
		//lower_frame.setLayout(new GridLayout(1, 3, 3, 15));
		lower_frame.setLayout(new GridLayout(0, 1)); 
		lower_frame.add(login);
	}
	public static void main(String[] args) {
		MainFrame mainframe = new MainFrame();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == login){											//处理登录(login)页面
			String user_name = account.getText().trim();
			String user_pwd = new String(pwd.getPassword()).trim();			//去掉空格
			if("".equals(user_name) || user_name == null || _txt_account.equals(user_name)) {
				JOptionPane.showMessageDialog(null, "请输入帐号！！");
				return;
			}if("".equals(user_pwd) || user_pwd == null || _txt_pwd.equals(user_pwd)) {
				JOptionPane.showMessageDialog(null, "请输入密码！！");
				return;
			}
			User user = new User(user_name, user_pwd);
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("login");
			cmd.setData(user);
			cmd.setReceiver(user_name);
			cmd.setSender(user_name);
			
			//实例化客户端 连接服务器 发送数据 密码去验证是否正确?
			//创建唯一的客户端（用于接受服务器发来的消息， socket接口），
			
			Client client = new Client(); 
			client.sendData(cmd); 			//发送数据
			cmd = client.getData(); 		//接受反馈的消息
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					this.dispose(); 		//关闭MainFrame页面
					
					JOptionPane.showMessageDialog(null,  "登陆成功");
					user = (User)cmd.getData(); 
					FriendsUI friendsUI = new FriendsUI(user, client); //将user的全部信息传到FriendsUI中，并将唯一与服务器交流的接口传到FriendUI中 这里传client仅为了发送消息
					ChatTread thread = new ChatTread(client, user, friendsUI); //这里传client为了收消息， 整个客户端用一个 ChatTread，一个client 
					thread.start();
				}else {
					JOptionPane.showMessageDialog(this, cmd.getResult());
				}
			}		
		}

		if(e.getSource() == register){								//调出注册(register)页面
			RegisterUI registerUI = new RegisterUI(this);	
		}
		if(e.getSource() == forget){								//处理找回密码(forget)页面
			ForgetUI forgetUI = new ForgetUI(this);	
		}
			
	}
	@Override
	public void focusGained(FocusEvent e) {						//鼠标的点击或移动之类的用focuslistener，可以定位焦点
		
    	if(e.getSource() == account){							//处理账号输入框
			if(_txt_account.equals(account.getText())){
				account.setText("");
				account.setForeground(Color.BLACK);
			}
		}
    	
		if(e.getSource() == pwd){								//处理密码输入框
			if(_txt_pwd.equals(pwd.getText())){
				pwd.setText("");
				pwd.setEchoChar('*');
				pwd.setForeground(Color.BLACK);
			}
		}
		
	}
	@Override
	public void focusLost(FocusEvent e) {						//监听鼠标焦点
		
		if(e.getSource() == account){							//处理账号输入框
			if("".equals(account.getText())){
				account.setForeground(Color.gray);
				account.setText(_txt_account);
			}
		}
		if(e.getSource() == pwd){								//处理密码输入框
			if("".equals(pwd.getText())){
				pwd.setForeground(Color.gray);
				pwd.setText(_txt_pwd);
				pwd.setEchoChar('\0');
			}
		}

	}
	
	

}
