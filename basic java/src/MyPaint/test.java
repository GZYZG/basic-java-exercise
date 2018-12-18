package MyPaint;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import MyPaint.ToolsPane;
import MyPaint.winattr;
import MyPaint.WRObject2File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import MyPaint.MyCanvas;

public class test extends Application{
	static MyCanvas canvas;
	static ArrayList<Shape> allShapeChosed;
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		initStage(stage);
		
		VBox mainPane = new VBox();
		Scene mainScene = new Scene(mainPane, stage.getWidth(), stage.getHeight());
		stage.setScene(mainScene);
		
		initMainPane(mainPane, stage);
		
		MenuBar menuBar = this.createMenuBar(stage);
		mainPane.getChildren().add(menuBar);
		mainPane.setMargin(menuBar, new Insets(5,5,5,5));
		
		ToolsPane toolsBar = this.createToolsBar(stage);
		mainPane.getChildren().add(toolsBar);
		mainPane.setMargin(toolsBar, new Insets(5,5,5,5));
		
		canvas = this.createMyCanvas(stage, toolsBar);

		mainPane.getChildren().add(canvas);
		mainPane.setMargin(canvas, new Insets(5,5,5,5));
		
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}
	
	//创建菜单栏
	public MenuBar createMenuBar(Stage stage) {
		MenuBar menubar = new MenuBar();
		//将菜单栏的宽和舞台绑定
		menubar.prefWidthProperty().bind(stage.widthProperty());
		
		//在菜单栏中添加菜单:文件菜单 ==> 创建文件、保存文件、退出
		Menu fileMenu = new Menu("文件");
		String iconpath;
		//MenuItem newFileItem = new MenuItem("新建");
		//String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/addfile_16px.png";
		//newFileItem.setGraphic(new ImageView(iconpath));
		
		MenuItem openFileItem = new MenuItem("打开");
		iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/openfile_16px.png";
		openFileItem.setGraphic(new ImageView(iconpath));
		openFileItem.setOnAction(openEvt->{
			this.readShape(stage);
		});
		
		MenuItem saveFileItem = new MenuItem("保存");
		iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/savefile_16px.png";
		saveFileItem.setGraphic(new ImageView(iconpath));
		saveFileItem.setOnAction(save->{
			System.out.println(canvas.getAllChosedShape());
			this.allShapeChosed = this.canvas.getAllChosedShape();
			this.saveShape(stage);
		});
		
		MenuItem exitItem = new MenuItem("退出");
		iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/exit_16px.png";
		exitItem.setGraphic(new ImageView(iconpath));
		exitItem.setOnAction(e->{
			stage.close();
		});
		fileMenu.getItems().addAll(/*newFileItem,*/ openFileItem,
				saveFileItem, exitItem);
		iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/file_24px.png";
		fileMenu.setGraphic(new ImageView(iconpath));
		
		menubar.setEffect(new Blend());
		
		menubar.getMenus().add(fileMenu);
		return menubar;
	}

	
	//创建工具栏，工具栏功能包括：
	//画笔的选择、形状的选择、线条粗细的选择、颜色的选择
	//以上功能的实现：
	//
	public ToolsPane createToolsBar(Stage stage) {
		ToolsPane tools = new ToolsPane(stage);
		return tools;
	}
	
	public MyCanvas createMyCanvas(Stage stage, ToolsPane tool) {
		MyCanvas canvas = new MyCanvas(stage, tool);
		
		return canvas;
	}
	
	//设置舞台
	public void initStage(Stage stage) {
		//
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/paint.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("paint");
		
		stage.setMinHeight(winattr.winMinHeight);
		stage.setMinWidth(winattr.winMinWidth);	
		stage.initStyle(StageStyle.DECORATED);
	}
		
	//设置主面板
	public void initMainPane(Pane main_pane, Stage stage) {	
		main_pane.setMinSize(winattr.winMinWidth, winattr.winMinHeight);
		main_pane.prefHeightProperty().bind(stage.heightProperty());
		main_pane.prefWidthProperty().bind(stage.widthProperty());
		main_pane.setStyle("-fx-background-color: rgba(230,230,230);" );
	}
	
	//点击形状时，弹出文件选择器，并进行文件保存
	@SuppressWarnings("deprecation")
	public void saveShape(Stage stage) {
		FileChooser filePicker = new FileChooser();
		filePicker.setTitle("保存形状");
		filePicker.getExtensionFilters().add(new ExtensionFilter("Shape", "*.s","*.shape","*.SHAPE","*.Shape"));
		
		File f = null; 
		
		f = filePicker.showSaveDialog(stage);
		//filePicker.showOpenDialog(stage);
		if ( f == null) {
			//默认保存在项目的根目录下
			Date date = new Date();
			f = new File("Shape_"+date.getYear()+"_"+date.getMonth()+"_"+date.getDay()+
					"_"+date.getHours()+"_"+date.getMinutes()+"_"+date.getSeconds()+".shape");
		}
		WRObject2File.writeObject2File(this.allShapeChosed, f.getPath());
		System.out.println("write path:"+f.getPath());
	}
	
	//读取已经保存的形状对象
	public void readShape(Stage stage) {
		FileChooser filePicker = new FileChooser();
		filePicker.setTitle("读取形状");
		filePicker.getExtensionFilters().add(new ExtensionFilter("Shape", "*.s","*.shape","*.SHAPE","*.Shape"));
		
		File f = null; 
		
		//f = filePicker.showSaveDialog(stage);
		f = filePicker.showOpenDialog(stage);
		if ( f == null) {
			//默认保存在项目的根目录下
			return;
		}
		ArrayList<Shape> shapes = WRObject2File.readFromFile(f.getPath());
		for (Shape s:shapes) {
			this.canvas.getChildren().add(s);
			System.out.println(s+"");
		}

		System.out.println("read path:"+f.getPath());
	}
	
	
	public static void main(String[] args) {
		Application.launch();
	}
	
}