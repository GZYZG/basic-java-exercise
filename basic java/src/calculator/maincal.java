package calculator;

import calculator.calexpress;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;

public class maincal extends Application{	
	/***
	 * @author GZY
	 * <h2>注意：-404040404 是错误码！若表达式结果为-404040404 计算器会误认为是计算错误！且直接的负数运算未实现！</h2>
	 */
	//计算器的主面板为一个VBox，从上往下为输入输出面板iopane
	static VBox mainpane = new VBox();
	//用于放置输入输出文本的面板
	static HBox iopane = new HBox(20);
	//用于放置按钮的面板，按钮以每行6个的方式排列
	static GridPane keyspane = new GridPane();
	//输入的数字，运算符的显示位置
	static TextField iofield = new TextField();
	//用于放置输入历史的面板
	static VBox hisVB = new VBox();
	//用于放置历史面板的场景
	static Scene hisScene;
	//用于放置历史场景的舞台
	static Stage hisStage = new Stage();
	//用于记录输入的次数
	static int count = 0;
	//用于放置历史输入的TextArea
	static TextArea hisIO = new TextArea("-------------HISTORY INPUT&OUTPUT---------------\r\n" + 
			"-------------@copyright gzy 2018.9-------------------\r\n");
	//历史输入输出信息
	static String hisinfo = "-------------HISTORY INPUT&OUTPUT---------------\r\n" + 
			"-------------Copyright©gzy 2018.9-------------------\r\n\r\n";
	//用于历史信息的控制按钮
	Button exitbtn = new Button("esc");
	Button dragbtn = new Button("drag");
	//用于放置按钮的VBox
	HBox hisCtrBtnsHB = new HBox(30);
	
	
	//定义键盘上的字符，顺序为每一排字符的排列顺序
	final static String[] keynames = {"7","8","9","/","(",")",
					     "4","5","6","x","^","!",
					     "1","2","3","+","C","//",
					     "M","0",".","-","D","=",
					     "size","esc","drag"};
	//定义所有的按钮
	Button[] allbtns = new Button[keynames.length];
	
	//用于0~9、.、+、-、*、/、^、(、)的事件处理器
	operatorHandler ophandler = new operatorHandler();
	//用于D,C的事件处理器
	dcHandler dchandler = new dcHandler();
	//用于=的事件处理器
	equalHandler equalhandler = new equalHandler();
	//用于M的事件处理器
	memHandler memhandler = new memHandler();
	
	//kbinputHandler kbhandler = new kbinputHandler();  //禁用从键盘输入，有bug
	//当一次的表达式计算完成按下=后，显示运算结果，再次按按钮的时候需要把iofield清空，利用标志flag标记是否清空
	boolean flag = false;
	//获取屏幕大小
	Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize(); 
	//音效
	MediaPlayer music = new MediaPlayer(new Media("file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/press_auto_pencil.mp3"));
	//
	MediaPlayer dragmusic = new MediaPlayer(new Media("file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/sousou_wind.mp3"));
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		//初始化历时输入输出
		initHistory(hisStage);
		//初始化按钮面板
		initBtnPane();
		//初始化输入输出面板
		initIOPane();	
		//对按钮进行设置，绑定事件处理器
		initButtons(stage);	
		//初始化主面板
		initMainPane(stage);

		//将主面板添加进场景中
		Scene scene = new Scene(mainpane,400,300);
		
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/icon1.png";
		Image mainicon = new Image(iconpath);
		//将当前场景添加进舞台
		stage.setScene(scene);
		stage.setTitle("Calculator");
		stage.setMaxHeight(400);
		stage.setMaxWidth(550);
		stage.setMinHeight(300);
		stage.setMinWidth(380);
		stage.getIcons().add(mainicon);
		//stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED/*TRANSPARENT*/);
		stage.show();
		
	}
	
	//初始化按钮面板
	public void initBtnPane() {
		//设置按钮面板内节点之间的水平，垂直间隔
		keyspane.hgapProperty().bind(mainpane.widthProperty().divide(10));
		keyspane.vgapProperty().bind(mainpane.heightProperty().divide(10));
		keyspane.setAlignment(Pos.CENTER);
	}
	
	//初始化输入输出面板
	public void initIOPane() {
		//设置输入输出文本框的属性
		iofield.setFont(Font.font("Console",FontWeight.LIGHT,FontPosture.ITALIC,15));
		iofield.setPrefHeight(40);
		iofield.setPrefColumnCount(60);
		//iofield.setOnKeyPressed(kbhandler);
		//将iofield加入到iopane中
		iopane.getChildren().add(iofield);
	}
	
	//初始化主面板
	public void initMainPane(Stage stage) {
		//设置主面板中结点的间隔
		mainpane.spacingProperty().bind(stage.heightProperty().divide(20));
		//将iopane,keyspane添加进主面板
		mainpane.getChildren().add(iopane);
		mainpane.getChildren().add(keyspane);//-fx-background-color:#535886;
		mainpane.setStyle("-fx-background-image:url(file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/mix.png)");
				
	}
	
	//初始化关于历史输入的结点
	private void initHistory(Stage stage) {
		setBtnCss(exitbtn);
		setBtnCss(dragbtn);
		exitbtn.setOnAction(e->closeWindow(stage));
		dragWindow dragHandler = new dragWindow(stage);
		dragbtn.setOnMouseDragged(dragHandler);
		
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/icon2.png";
		Image icon = new Image(iconpath);
		
		hisIO.setMaxHeight(600);
		hisIO.setMaxWidth(400);
		hisIO.setWrapText(true);
		hisIO.setFont(Font.font("Console",FontWeight.LIGHT,FontPosture.ITALIC,14));
		hisIO.setStyle("-fx-background-color:gray");
		hisIO.setStyle("-fx-background-image:url(file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/Christmas_tree_Child.png)");
		
		hisCtrBtnsHB.getChildren().addAll(exitbtn,dragbtn);
		//hisCtrBtnsHB.seth
		hisCtrBtnsHB.setAlignment(Pos.CENTER);
		
		hisVB.setAlignment(Pos.CENTER);
		hisVB.getChildren().add(hisIO);
		hisVB.getChildren().add(hisCtrBtnsHB);
		hisScene = new Scene(hisVB,700,800);
		
		hisStage.setScene(hisScene);
		hisStage.setTitle("HISTORY INPUT");
		hisStage.setHeight(300);
		hisStage.setWidth(415);
		hisStage.getIcons().add(icon);
		hisStage.initStyle(StageStyle.TRANSPARENT/*UNDECORATED*/);  //设置窗体无边框
		
		hisStage.setResizable(false);
		
		
		
	}
	
	//设置单个按钮的样式
	public void setBtnCss(Button btn) {
		btn.setScaleX(1.5);		
		btn.setScaleY(1.5);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setStyle("-fx-background-radius:10;-fx-border-radius:0;-fx-background-color:white;"
				+"-fx-background-color:#66CCCC;"+"-fx-padding-top:10");
	}
	
	//初始化按钮
	private void initButtons(Stage stage) {
		//对按钮进行设置，绑定事件处理器
		for(int i = 0;i < keynames.length;i++) {
			allbtns[i] = new Button(keynames[i]);
			//将按钮在X,Y方向上放大1.5倍
			allbtns[i].setScaleX(1.5);		
			allbtns[i].setScaleY(1.5);
			
			//设置每个按钮的动作,根据按钮的意义事件处理
			if(keynames[i] == "esc" ) {
				allbtns[i].setOnAction(e->closeWindow(stage));
				//do nothing 
			}else if(keynames[i] == "上") {
				allbtns[i].setOnAction(e->moveUpWindowSize(stage));
			}else if(keynames[i] == "下") {
				allbtns[i].setOnAction(e->moveDownWindowSize(stage));
			}else if(keynames[i] == "drag") {
				//用于拖动窗口的鼠标事件处理器
				dragWindow dragHandler = new dragWindow(stage);
				allbtns[i].setOnMouseDragged(dragHandler);
			}else if(keynames[i] == "size") {
				allbtns[i].setOnAction(e->chageWindowSize(stage));
			}else if(keynames[i] == "M" ) {
				allbtns[i].setOnAction(memhandler);
			}else if(keynames[i] == "=" ) {
				allbtns[i].setOnAction(equalhandler);
			}else if(keynames[i] == "C" || keynames[i] == "D") {
				allbtns[i].setOnAction(dchandler);
			}
			else {
				allbtns[i].setOnAction(ophandler);
				
			}
			
			//设置每个按钮的样式  -fx-background-color:#A5DE37;
			allbtns[i].setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
			allbtns[i].setStyle("-fx-background-radius:10;-fx-border-radius:0;-fx-background-color:white;"
					+"-fx-background-color:#66CCCC;");
			
			//每一行放置6个按钮
			keyspane.add(allbtns[i],i%6,i/6);
			//按钮文字为“！”的是占位按钮，将他们设置为不可见
			if(keynames[i] == "//") {
				allbtns[i].setVisible(false);
			}
		}
	}
	
	//改变窗口的大小
	public void chageWindowSize(Stage stage){
		double maxH = stage.maxHeightProperty().get();
		double maxW = stage.maxWidthProperty().get();
		double minH = stage.minHeightProperty().get();
		double minW = stage.minWidthProperty().get();
		double curH = stage.getHeight();
		double curW = stage.getWidth();
		if(curH < maxH || curW < maxW) {
			stage.setHeight( curH+(maxH - minH) / 3  );
			stage.setWidth( curW+(maxW - minW) / 3  );
		}else {
			stage.setHeight( minH );
			stage.setWidth( minH );
		}
	}
	
	//向上移动窗口
	public void moveUpWindowSize(Stage stage) {
		if(stage.getY() <= 0) {
			return;
		}
		stage.setY(stage.getY()-10);
	}
	
	//向下移动窗口
	public void moveDownWindowSize(Stage stage) {
		System.out.println("curY: "+stage.getY()+" screenH: "+screenSize.getHeight());
		if(stage.getY() + stage.getHeight() > screenSize.getHeight()) {
			return;
		}
		stage.setY(stage.getY()+10);
	}
	
	//关闭窗口
	public void closeWindow(Stage stage) {
		stage.close();
	}
	
	//移动窗口
	class dragWindow implements EventHandler<MouseEvent>{
		Stage stage;
		public dragWindow(Stage stage) {
			this.stage = stage;
		}
		@Override
		public void handle(MouseEvent e) {
			
			double x = e.getScreenX(); //- ((Button)e.getSource()).getLayoutX();
			double y = e.getScreenY();// - ((Button)e.getSource()).getLayoutY();
			if(y + stage.getHeight() > screenSize.height) {
				y = screenSize.height - stage.getHeight();
			}
			if(x + stage.getWidth() > screenSize.width) {
				x = screenSize.width - stage.getWidth();
			}
			//dragmusic.seek(Duration.millis(140));
			//dragmusic.seek(Duration.ZERO);
			//dragmusic.play();
			
			stage.setX(x);
			stage.setY(y);
			//dragmusic.stop();
		}
		
	}
	
	//定义0~9、.、+、-、*、/、^、!、(、)事件处理类
	class operatorHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Button srcbtn = (Button) e.getSource();
			music.seek(Duration.millis(160));
			music.play();
			music.seek(Duration.ZERO);
			//如果上一次的表达式计算完且输出了结果在iofield上，则再次按按钮的时候先要将iofield清空,之后按了按钮就要把flag置为false
			if(flag) {
				iofield.setText("");
				flag = false;
				
			}
			iofield.setText(iofield.getText()+srcbtn.getText());
			System.out.println(srcbtn.getText()+" is pressed!");
		}
		
	}
	
	//定义D,C的事件处理器
	class dcHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Button srcbtn = (Button) e.getSource();
			if(srcbtn.getText() == "D") {
				//如果是D，则只删除一个字符,当iofield已经没有字符时要额外处理
				int len = iofield.getText().length();
				if(len == 0) {
					len = 0;
				}else {
					len = len - 1;
				}
				iofield.setText(iofield.getText(0 , len));
			}else if(srcbtn.getText() == "C") {
				//如果是C，则删除输入输出文本框内的所有字符
				iofield.setText("");
			}
			
		}
		
	}
	
	//定义=的事件处理器
	class equalHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			music.seek(Duration.millis(250));
			music.play();
			music.seek(Duration.ZERO);
			if(flag) {
				iofield.setText("");
				return;
			}
			flag = true;
			Button srcbtn = (Button) e.getSource();
			//计算表达式的值，并将结果显示在iofield上,显示后需要将flag置为true以便再次按按钮时能够将iofield清空
			float result = calexpress.calculate(iofield.getText());
			if(result == -404040404) {
				count++;
				hisinfo += "-NO." + count + "    "+ iofield.getText() + " = " + " 运算错误\n";
				hisIO.setText(hisinfo);
				iofield.setText("error");
				return;
			}
			count++;
			hisinfo += "-NO." + count + "    "+ iofield.getText() + " = " + result+"\n";
			iofield.setText(" = "+result);
			hisIO.setText(hisinfo);
			System.out.println(srcbtn.getText()+" is pressed!");		
		}
			
	}
	
	//定义M的事件处理器
	class memHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {
			Button srcbtn = (Button) e.getSource();
			hisStage.show();
			
			System.out.println(srcbtn.getText()+" is pressed!");	
					
		}
				
	}
	
	public static void main(String[] arg) throws Exception {
		Application.launch(arg);		
	}	
}