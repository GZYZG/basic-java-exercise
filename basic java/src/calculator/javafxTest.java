package calculator;

import java.lang.String;
import java.awt.Insets;
import java.lang.Exception;
import javafx.application.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextArea;

public class javafxTest extends Application{
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		VBox pane = new VBox();
		pane.spacingProperty().bind(primaryStage.heightProperty().divide(20));

		
		//可用textarea来显示之前的输入历史
		TextArea text = new TextArea();
		text.setMaxHeight(20);
		text.setPrefColumnCount(30);
		text.setPrefRowCount(10);
		pane.getChildren().add(getIOField(pane));
		pane.getChildren().add(getGridPane(pane));
		
		
		
		Scene scene = new Scene(pane,350,300);

		
		primaryStage.setScene(scene);
		primaryStage.setTitle("javafxTestDemo");
		
		primaryStage.setMaxHeight(500);
		primaryStage.setMaxWidth(500);
		primaryStage.setMinHeight(250);
		primaryStage.setMinWidth(350);
		//primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	private HBox getIOField(Pane pane) {
		HBox hb = new HBox(20);
		TextField iofield = new TextField();
		iofield.setFont(Font.font("Console",FontWeight.LIGHT,FontPosture.ITALIC,15));
		iofield.setPrefHeight(40);
		iofield.setPrefColumnCount(60);
		hb.getChildren().add(iofield);
		return hb;
	}
	
	private HBox getKeyRow(Pane pane) {
		HBox hb = new HBox();
		int i = 20;
		hb.spacingProperty().bind(pane.widthProperty().divide(10));
		Button addbtn = new Button("+");
		Button decbtn = new Button("-");
		Button mulbtn = new Button("x");
		addbtn.setScaleX(1.5);
		addbtn.setScaleY(1.5);
		decbtn.setScaleX(1.5);
		decbtn.setScaleY(1.5);
		mulbtn.setScaleX(1.5);
		mulbtn.setScaleY(1.5);
		hb.getChildren().addAll(addbtn,decbtn,mulbtn);
		
		return hb;
	}
	
	private GridPane getGridPane(Pane pane) {
		GridPane gp = new GridPane();
		gp.hgapProperty().bind(pane.widthProperty().divide(10));
		gp.vgapProperty().bind(pane.heightProperty().divide(10));
		gp.setAlignment(Pos.CENTER);
		
		String[] keynames = {"7","8","9","/","(",")",
							 "4","5","6","*","^","!",
							 "1","2","3","+","C","!",
							 "M","0",".","—","D","="};
		Button[] allbtns = new Button[keynames.length];
		for(int i = 0;i < keynames.length;i++) {
			allbtns[i] = new Button(keynames[i]);
			allbtns[i].setScaleX(1.5);		//将按钮在X,Y方向上放大1.5倍
			allbtns[i].setScaleY(1.5);
			
			//设置每个按钮的样式
			allbtns[i].setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
			allbtns[i].setStyle("-fx-background-color:#A5DE37;"
					+ "-fx-background-radius: 10;-fx-border-radius: 0");
		
			gp.add(allbtns[i],i%6,i/6);
			if(keynames[i] == "!") {
				allbtns[i].setVisible(false);
			}
		}
		
		return gp;
	}
	
	public static void main(String[] arg) throws Exception {
		Application.launch(arg);
		
	}
}