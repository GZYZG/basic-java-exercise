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

class LoginScene extends Scene{
	
	Label account;
	Label passwd;
	TextField inputAccount;
	//TextField inputPasswd;
	PasswordField inputPasswd;
	Button cancel;
	Button enter;
	ArrayList<Account> accounts;
	public static GridPane mainpane = new GridPane();
	
	public LoginScene(ArrayList<Account> accounts) {
		super(mainpane);
		
		this.accounts = accounts;
		account = new Label("账号:");
		passwd = new Label("密码:");
		inputAccount = new TextField();
		inputPasswd = new PasswordField();
		cancel = new Button("取消");
		enter = new Button("确定");
		this.setWidgetsStyle();
		
		this.initMainPane(mainpane);
	}
	
	public void setWidgetsStyle() {
		this.setLabelStyle(account);
		this.setLabelStyle(passwd);
		this.setBtnStyle(cancel);
		this.setBtnStyle(enter);
		this.setTextFieldStyle(inputAccount, "");
		this.setTextFieldStyle(inputPasswd, "");
		
	}
	
	//设置主面板
	@SuppressWarnings("static-access")
	public void initMainPane(Pane main_pane) {
		
		main_pane.setPadding(new Insets(50, 80, 20, 80));
		main_pane.setPrefSize(300, 350);
		main_pane.setMinSize(260, 300);
		main_pane.setStyle("-fx-background-color: rgba(255,255,255,0.85);" );
		/**/
		((GridPane)main_pane).add(account, 0, 0);
		((GridPane)main_pane).setMargin(account, new Insets(15,1,5,5));
		((GridPane)main_pane).add(inputAccount, 1, 0);
		((GridPane)main_pane).setMargin(inputAccount, new Insets(15,5,5,1));
		((GridPane)main_pane).add(passwd, 0, 1);
		((GridPane)main_pane).setMargin(passwd, new Insets(15,1,5,5));
		((GridPane)main_pane).add(inputPasswd, 1, 1);
		((GridPane)main_pane).setMargin(inputPasswd, new Insets(15,5,5,1));
		((GridPane)main_pane).add(cancel, 0, 2);
		((GridPane)main_pane).setMargin(cancel, new Insets(15,5,5,5));
		((GridPane)main_pane).add(enter, 1, 2);
		((GridPane)main_pane).setMargin(enter, new Insets(15,5,5,5));
	}
	
	
	//设置标签的样式
	public void setLabelStyle(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(40, 25);
		label.setAlignment(Pos.CENTER);
		label.setStyle("-fx-background-color:rgb(200,200,200)");
	}
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPrefSize(70, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	public void setTextFieldStyle(TextField tf,String prom) {
		tf.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		tf.setMinWidth(80);
		tf.setPromptText(prom);
	}
		
	
	public void clearScene() {
		this.inputAccount.setText("");
		this.inputPasswd.setText("");
	}

	public Button getCancelBtn() {
		return this.cancel;
	}
	
	public Button getEnterBtn() {
		return this.enter;
	}

	public Account loginATM() {
		String name = this.inputAccount.getText();
		
		String passwd = this.inputPasswd.getText();
		//System.out.println("in loginAtm:"+this.accounts.size());
		for(int i = 0; i < this.accounts.size(); i++) {
			if(this.accounts.get(i).name.equals(name) && this.accounts.get(i).getPasswd().equals(passwd)) {
				return this.accounts.get(i);
			}
		}
		
		return null;
		
	}
	
	
}