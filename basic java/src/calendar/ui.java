package calendar;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import calendar.createCalendar;

public class ui extends Application{

	Label year_label = new Label("year");
	Label month_label = new Label("month");
	VBox main_pane = new VBox();
	HBox title = new HBox();
	//GridPane calendar = new GridPane();
	//Button[] date = new Button[42];
	//ComboBox year = new ComboBox();
	ComboBox month = new ComboBox();
	TextField year = new TextField();
	
	createCalendar show = new createCalendar();
	String result = "";
	Label showlabel = new Label("welcome");
	StackPane sp = new StackPane();

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
	
		showlabel.setWrapText(true);
		showlabel.setMinSize(280, 220);
		showlabel.setTextFill(Color.WHITE);
		showlabel.setPadding(new Insets(5,5,5,5));
		showlabel.setStyle("-fx-background-color:rgb(150,150,150)");
		showlabel.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,14));

		sp.getChildren().add(showlabel);
		sp.setPadding(new Insets(5,5,5,5));
		//initLabel(biglabel,"calendar");
		
		
		initComboBox(month, 2);
		initLabel(year_label,"year:");
		initLabel(month_label,"month:");
		initInput(year);
		
		initTitleHBox(title);
		title.getChildren().add(year_label);
		title.getChildren().add(year);
		title.getChildren().add(month_label);
		title.getChildren().add(month);
		
		/*使用按钮来显示日历
		initBtns(date);
		initGridPane(calendar);
		addBtn2GridPane(calendar, date);*/
		//main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);" );
		main_pane.setMaxWidth(300);
		main_pane.getChildren().add(title);
		//main_pane.getChildren().add(calendar);
		main_pane.getChildren().add(sp);

		Scene scene = new Scene(main_pane, 300, 320);
		//scene.setFill(null);
		initMainStage(stage);
		stage.setScene(scene);
		//stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		
	}
	
	//显示一个提示框
	public void showAlert(String message,Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setWidth(200);
		alert.setHeight(80);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
	
	//初始化顶部的标题
	public void initTitleHBox(HBox hbox) {
		hbox.setPadding(new Insets(5,5,5,5));
		hbox.setAlignment(Pos.CENTER);
	}
	
	//初始化标题的标签
	public void initLabel(Label label,String name) {
		label.setStyle("-fx-background-color:rgb(200,200,200)");
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		label.setMaxWidth(60);
		label.setMinWidth(60);
		label.setPadding(new Insets(3,3,3,10));
		label.setVisible(true);
		label.setText(name);
		label.setTextFill(Color.BLACK);
		
	}
	
	public boolean checkInput(String text) {
		if(text.equals("")) {
			showAlert("请输入正确的年份！",Alert.AlertType.ERROR);
			return false;
		}
		for(char c : text.toCharArray()) {
			if( c < '0' || c > '9') {
				showAlert("请输入正确的年份！",Alert.AlertType.ERROR);
				return false;
			}
		}
		if(Integer.valueOf(text) < 0 ) {
			showAlert("请输入正确的年份！",Alert.AlertType.ERROR);
			return false;
		}
		
		return true;
	}
	
	//初始化输入的文本框
	public void initInput(TextField input) {
		input.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		input.setPromptText("enter year");
		input.setPrefColumnCount(40);
		input.setMaxWidth(80);
		input.setMinWidth(60);
		input.setPadding(new Insets(5,5,5,5));
		input.setOnAction(e->{
			String year_text = input.getText();
			//若包含非法字符则弹出错误提示框
			if( ! checkInput(year_text) )
				return;
			
			int month_idx = month.getSelectionModel().getSelectedIndex();
			if(month_idx == -1) {
				return;
			}
			
			result = "welcome";
			show.printMonth(Integer.valueOf(year_text ), month_idx+1);
			result = show.show;
			showlabel.textProperty().setValue("hh");;
			showlabel.setText(result);

			System.out.println(month_idx);
		});

		
	}
	
	public void initComboBox(ComboBox comb,int type) {
		//comb.setPrefHeight(30);
		comb.setMinHeight(20);
		if(type == 1) {
			ArrayList<String> years = new ArrayList<String>();

		}
		if(type == 2) {
			String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
			ObservableList<String> options = FXCollections.observableArrayList(months);
			comb.getItems().addAll(months);
			comb.setOnAction(e->{
				int month_idx = month.getSelectionModel().getSelectedIndex();
				if(month_idx == -1) {
					return;
				}
				if( !checkInput(year.getText()) ) {
					return;
				}

				result = " ";
				show.printMonth(Integer.valueOf(year.getText() ), month_idx+1);
				result = show.show ;
				
				showlabel.setText(result);
			});
		}
		
	}
	
	public void initMainStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/calendar_32px.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("calendar");
		
		stage.setMaxHeight(300);
		stage.setMaxWidth(380);
		stage.setMinHeight(300);
		stage.setMinWidth(350);
		//stage.initStyle(StageStyle.TRANSPARENT);
	}
	
	
	//设置按钮的样式
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPadding(new Insets(1,10,10,5));
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.NORMAL,FontPosture.ITALIC,13));
		btn.setTextAlignment(TextAlignment.CENTER);
	}
	
	//初始化日期按钮
	public void initBtns(Button[] date) {
		String[] keynames = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat",
				 			 "   ","   ","   ","   ","   ","   ","   ",
				 			 "   ","   ","   ","   ","   ","   ","   ",
				 			 "   ","   ","   ","   ","   ","   ","   ",
				 			 "   ","   ","   ","   ","   ","   ","   ",
				 			 "   ","   ","   ","   ","   ","   ","   ", };
		
		for(int i = 0;i < keynames.length;i++) {
			date[i] = new Button("hah"/*keynames[i]*/);
			setBtnStyle(date[i]);
			
		}
	}
	

	//初始化按键的网格面板
	public void  initGridPane(GridPane pane) {
		pane.setPadding(new Insets(15,5,15,5));
		pane.setHgap(0);
		pane.setVgap(1);
		pane.setAlignment(Pos.CENTER);

	}
	
	//为面板添加按钮
	public void addBtn2GridPane(GridPane gp, Button[] btns) {
		for(int i = 0; i < btns.length; i++) {
			gp.add(btns[i],i % 7, (int)(i / 7) );
		}
	}
	
	//初始化标签
	public void initAVStageLabel(Label label) {
		label.setMinWidth(65);
		label.setTextFill(Color.BLACK);
	}
	
	
	public static void main(String[] arg) throws Exception {
		Application.launch(arg);
		
	}	
	
}