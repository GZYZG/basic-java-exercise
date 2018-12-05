package deck_24_point;

import java.util.ArrayList;
import java.util.Collection;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import deck_24_point.cardsHBox;

class playerInfoHBox{
	Label playerName;
	Label cardNum;
	Label pointNum;
	Button one_more;
	Button submit;
	HBox playerHBox = new HBox();
	cardsHBox cardDesk = new cardsHBox();
	int playerId = -1;
	
	public playerInfoHBox() {
		
		this.playerHBox.setPrefSize(300, 50);
		this.playerHBox.setPadding(new Insets(5,5,5,5));
		this.playerHBox.setStyle("-fx-background:transparent;-fx-background-color: rgba(10,10,10,0.85);" );
		this.playerId = -1;
		
		System.out.println("in playerInfoHBox()");
		//this.playerName = new Label();
		this.playerName = new Label("player: -1");
		this.cardNum = new Label("牌数: ");
		this.pointNum = new Label("点数: ");
		this.one_more = new Button("one more");
		this.submit = new Button("submit");
		this.cardDesk = new cardsHBox();
		
		ArrayList<Label> labellist = new ArrayList<Label>();
		labellist.add(this.playerName);
		labellist.add(this.cardNum);
		labellist.add(this.pointNum);
		this.initLabels(labellist);
		ArrayList<Button> btnlist = new ArrayList<Button>();
		btnlist.add(this.one_more);
		btnlist.add(this.submit);
		this.initButtons(btnlist);
		
		this.installWidgets();
	}
	
	//设置按钮的样式
	public void initButtons(ArrayList<Button> btnlist) {
		for(Button btn:btnlist) {
			DropShadow shadow = new DropShadow();
			btn.setPadding(new Insets(5,15,5,15));
			btn.setEffect(shadow);
			btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
			btn.setTextAlignment(TextAlignment.CENTER);
			this.playerHBox.setMargin(btn, new Insets(5,5,5,5));
		}
	}
	
	
	//设置标签的样式
	public void initLabels(ArrayList<Label> labellist) {
		for(Label label:labellist) {
			label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
			//label.setMaxWidth(44);
			//label.setMinWidth(44);
			label.setMinSize(80, 33);
			label.setPadding(new Insets(2,2,2,2));
		}
	}
	
	//将所有的控件安装在HBox上
	public void installWidgets() {
		this.playerHBox.getChildren().addAll(this.playerName, this.cardNum, this.pointNum,
				this.one_more, this.submit);
	}
	
	public void setPlayerName(String name) {
		this.playerName.setText("player: " + name);
	}
	
	public void setCardNum(int num) {
		this.cardNum.setText("牌数: " + String.valueOf(num));
	}
	
	public void setPointNum(int num) {
		this.pointNum.setText("点数: " + String.valueOf(num));
	}
	
	
}