package atm;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

class CreateScene extends Scene{
	
	ArrayList<Label> allLabels;
	ArrayList<TextField> allTFs;
	ArrayList<Account> accounts;
	Button cancel;
	Button enter;
	
	//PasswordField inputPasswd;
	public static GridPane mainpane = new GridPane();
	
	public CreateScene(ArrayList<Account> accounts) {
		super(mainpane);
		
		this.accounts = accounts;
		this.allLabels = new ArrayList<>(4);
		this.allTFs = new ArrayList<>(4);
		cancel = new Button("取消");
		enter = new Button("确定");
		this.initLabels();
		this.initTextFields();
		this.initMainPane(mainpane);
	}
	
	
	//设置标签的样式
	public void setLabelStyle(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(45, 25);
		label.setAlignment(Pos.CENTER);
		label.setStyle("-fx-background-color:rgb(200,200,200)");
	}
	
	public void initLabels() {
		String[] labelName = new String[] {"姓名:", " 初始余额:", " 年利率:", " 密码:"};
		for(int i = 0; i < 4; i++) {
			this.allLabels.add(i, new Label(labelName[i]));
			this.setLabelStyle(this.allLabels.get(i));
		}
	}
	
	
	public void setTextFieldStyle(TextField tf,String prom) {
		tf.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		tf.setMinWidth(80);
		tf.setPromptText(prom);
	}
	
	public void initTextFields() {
		for(int i = 0; i < 4; i++) {
			if(i != 3) {
				this.allTFs.add(i, new TextField());
			}
			else
				this.allTFs.add(i, new PasswordField());
				
			this.setTextFieldStyle(this.allTFs.get(i), "");
		}
	}
	
	
	//设置主面板
	@SuppressWarnings("static-access")
	public void initMainPane(Pane main_pane) {
		
		main_pane.setPadding(new Insets(50, 80, 20, 80));
		main_pane.setPrefSize(300, 350);
		main_pane.setMinSize(260, 300);
		main_pane.setStyle("-fx-background-color: rgba(255,255,255,0.85);" );
		/**/
		
		for(int i = 0; i < 4; i++) {
			((GridPane)main_pane).add(this.allLabels.get(i), 0, i);
			((GridPane)main_pane).setMargin(this.allLabels.get(i), new Insets(15,1,5,5));
			((GridPane)main_pane).add(this.allTFs.get(i), 1, i);
			((GridPane)main_pane).setMargin(this.allTFs.get(i), new Insets(15,1,5,5));
		}
		((GridPane)main_pane).add(cancel, 0, 4);
		((GridPane)main_pane).setMargin(cancel, new Insets(15,5,5,5));
		((GridPane)main_pane).add(enter, 1, 4);
		((GridPane)main_pane).setMargin(enter, new Insets(15,5,5,5));
	}
	
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPrefSize(70, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	

	public void clearScene() {
		for(int i = 0; i < this.allTFs.size(); i++) {
			this.allTFs.get(i).setText("");
		}
	}
	
	public Button getCancelBtn() {
		return this.cancel;
	}
	
	public Button getEnterBtn() {
		return this.enter;
	}
	
	//创建一个账户
	public Account createAccount() {
		String name = this.allTFs.get(0).getText();
		if(name.equals(""))
			return null;
		double money = 0;
		if(!this.allTFs.get(1).getText().equals(""))
			money = Double.valueOf(this.allTFs.get(1).getText());
		if(money < 0)
			return null;
		double annualInterstRate = 0.0;
		if(!this.allTFs.get(2).getText().equals(""))
			annualInterstRate = Double.valueOf(this.allTFs.get(2).getText());
		if(annualInterstRate < 0)
			return null;
		String passwd = this.allTFs.get(3).getText();
		//System.out.println("ur passwd is:"+passwd);
		if(passwd.equals(""))
			return null;
		Account account = new Account(name,this.accounts.size(), passwd,  money, annualInterstRate);
		
		return account;
	}

}