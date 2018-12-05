package crossword;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class StartScene extends Scene{

	private Button[] allBtns = new Button[3];
	private static VBox mainpane = new VBox();
	Stage stage;
	
	public StartScene(Stage stage) {
		super(mainpane);
		initMainPane(mainpane);
		initBtns();
		installWidgets();
		this.setFill(null);
		this.stage = stage;
	}
	
	public void initBtns() {
		String[] btnName = new String[] {"开始游戏", "排行榜", "退出游戏"};
		for(int i = 0; i < 3; i++) {
			allBtns[i] = new Button(btnName[i]);
			setBtnStyle(allBtns[i]);
			if(btnName[i].equals("退出游戏")) {
				allBtns[i].setOnAction(exitevt->{
					this.stage.close();
				});
			}
		}
	}
	
	public Button getExitBtn() {
		return this.allBtns[2];
	}
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		//btn.setPadding(new Insets(5,15,5,15));
		btn.setPrefSize(80, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	//设置主面板
	public void initMainPane(Pane main_pane) {
		main_pane.setPadding(new Insets(50, 80, 20, 80));
		main_pane.setPrefSize(300, 350);
		main_pane.setMinSize(260, 300);
		main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(0,0,0,0.85);" );
	}
	
	private void installWidgets() {
		for(int i = 0; i < allBtns.length; i++) {
			this.mainpane.getChildren().add(allBtns[i]);
			this.mainpane.setMargin(allBtns[i], new Insets(10,10,10,10));
		}
	}
	
	public Button getPlayBtn() {
		return this.allBtns[0];
	}
	
	public Button getRankBtn() {
		return this.allBtns[1];
	}
	
}