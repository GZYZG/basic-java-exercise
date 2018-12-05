package atm;



import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

class AccountScene extends Scene{
	public static VBox mainpane = new VBox();
	
	private Label ownerInfo;
	private TextArea accountInfo;
	Button[] allBtns;
	Account currentaccount;
	
	public AccountScene() {
		super(mainpane);
		
		allBtns = new Button[4];
		ownerInfo = new Label();
		this.setLabelStyle(this.ownerInfo);
		//this.ownerInfo.
		accountInfo = new TextArea();
		
		this.accountInfo.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		this.accountInfo.setMinSize(250, 25);
		
		//this.accountInfo.setAlignment(Pos.CENTER);
		this.accountInfo.setEditable(false);
		this.accountInfo.setStyle("-fx-background-color:rgb(200,200,200)");
		this.accountInfo.setWrapText(true);
		this.setAccount(null);
		//this.setLabelStyle(this.accountInfo);
		this.accountInfo.setMinSize(250, 150);
		this.accountInfo.setMaxSize(250, 150);
		
		//this.accountInfo.setWrapText(true);
		this.initBtns();
		this.initMainPane(mainpane);
	}
	
	public void setAccount(Account account) {
		this.currentaccount = account;
	}
	
	public void initMainPane(Pane main_pane) {
		main_pane.setPadding(new Insets(10, 10, 10, 10));
		main_pane.setPrefSize(300, 350);
		main_pane.setMinSize(260, 300);
		main_pane.setStyle("-fx-background-color: rgba(255,255,255,0.85);" );
		
		GridPane subpane = new GridPane();
		
		
		
		((VBox)main_pane).getChildren().add(this.ownerInfo);
		((VBox)main_pane).setMargin(this.ownerInfo, new Insets(0,1,5,5));
		((VBox)main_pane).getChildren().add(this.accountInfo);
		((VBox)main_pane).setMargin(this.accountInfo, new Insets(0,1,5,5));
		for(int i = 0; i < 4; i++) {
			subpane.add(this.allBtns[i], i%2, i/2);
			subpane.setMargin(this.allBtns[i], new Insets(15,1,5,5));
		}
		((VBox)main_pane).getChildren().add(subpane);
		
		
	}
	
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPrefSize(70, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	public void initBtns() {
		String[] btnName = new String[] {"存款", "取款", "打印清单","退出登录"};
		for(int i = 0; i < this.allBtns.length; i++) {
			allBtns[i] = new Button(btnName[i]);
			this.setBtnStyle(allBtns[i]);
			if(allBtns[i].getText().equals("存款")) {
				allBtns[i].setOnAction(depositevt->{
					this.deposit();
				});
			}
			if(allBtns[i].getText().equals("取款")) {
				allBtns[i].setOnAction(depositevt->{
					this.withdraw();
				});
			}
			if(allBtns[i].getText().equals("打印清单")) {
				allBtns[i].setOnAction(depositevt->{
					this.accountInfo.setText(this.currentaccount.toString());
				});
			}

		}
	}
	
	public void setLabelStyle(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(250, 25);
		label.setAlignment(Pos.CENTER);
	
		label.setStyle("-fx-background-color:rgb(200,200,200)");
	}
	
	public void clearScene() {
		this.accountInfo.setText("");
	}
	
	public Button getExitBtn() {
		return this.allBtns[3];
	}
	
	public void setOwnerInfo() {
		if(this.currentaccount != null) {
			this.ownerInfo.setText("姓名:"+this.currentaccount.name+"\t\t账号:"+this.currentaccount.getId());
		}else {
			this.ownerInfo.setText("");
		}
		
	}
	
	public double inputAmount(String header) {
		TextInputDialog input = new TextInputDialog();
		input.setTitle("输入金额");
		input.setHeaderText(header);
		input.setContentText("请输入金额:");
		
		input.showAndWait();
		if(input.getResult() == null) {
			return -1;
		}
		try {
			return Double.valueOf(input.getResult());
		}catch (NumberFormatException e) {
			return -11;
		}
		//System.out.println( addPlayer.getResult());
	}
	
	
	public void withdraw() {
		double amount = this.inputAmount("取款");
		
		
		if(amount < 0) {
			if(amount == -11) {
				this.accountInfo.setText(this.accountInfo.getText()+
						"取款失败！取款金额应该为数字！目前账户余额："+this.currentaccount.getBalance()+'\n');
				return;
			}
			this.accountInfo.setText(this.accountInfo.getText()+
					"取款 "+amount+" 失败！取款金额不能小于0！目前账户余额："+this.currentaccount.getBalance()+'\n');
			return;
		}
		if(amount > this.currentaccount.getBalance()) {
			this.accountInfo.setText(this.accountInfo.getText()+
					"取款 "+amount+" 失败！取款金额不能大于余额！目前账户余额："+this.currentaccount.getBalance()+'\n');
			return;
		}
		this.currentaccount.withDraw(amount);
		this.accountInfo.setText(this.accountInfo.getText()+
				"取款 "+amount+" 成功！目前账户余额："+this.currentaccount.getBalance()+'\n');
	}
	
	public void deposit() {
		double amount = this.inputAmount("存款");
		
		if(amount < 0) {
			if(amount == -11) {
				this.accountInfo.setText(this.accountInfo.getText()+
						"存款失败！存款金额应该为数字！目前账户余额："+this.currentaccount.getBalance()+'\n');
				return;
			}
			this.accountInfo.setText(this.accountInfo.getText()+
					"存款 "+amount+" 失败！存款金额不能小于0！目前账户余额："+this.currentaccount.getBalance()+'\n');
			return;
		}
	
		this.currentaccount.deposit(amount);
		this.accountInfo.setText(this.accountInfo.getText()+
				"存款 "+amount+" 成功！目前账户余额："+this.currentaccount.getBalance()+'\n');
	}
	
	
	
}