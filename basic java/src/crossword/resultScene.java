package crossword;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class ResultScene extends Scene{
	private Label[] allLabels = new Label[2];
	private Button[] allBtns = new Button[2];
	private static HBox subpane = new HBox();
	private static VBox mainpane = new VBox();
	private static FlowPane starfp = new FlowPane(Orientation.HORIZONTAL, 5, 5);
	//private ArrayList<Image> starts = new ArrayList<Image>();
	int starnum = 0;
	Stage stage;
	
	public ResultScene(Stage stage) {
		super(mainpane);
		this.initHBox(subpane);
		this.initMainPane(mainpane);
		this.initBtns();
		this.initLabels();
		this.installWidgetsOnMainPane();
		this.initFlowPane(starfp);
		this.mainpane.getChildren().add(starfp);
		this.mainpane.setMargin(starfp, new Insets(5,5,5,5));
		this.installWidgetsOnHBox(subpane);
		
		this.mainpane.getChildren().add(subpane);
		this.mainpane.setMargin(this.subpane, new Insets(5,5,5,5));
		this.setFill(null);
		this.stage = stage;
		
	}
	
	public void initLabels() {
		String[] labelName = new String[] {"答对:", "答错:"};
		for(int i = 0; i < allLabels.length; i++) {
			allLabels[i] = new Label(labelName[i]);
			setLabelStyle(allLabels[i]);
		}
	}
	
	public void setRightNum(int num) {
		this.allLabels[0].setText("答对: " + num );
	}
	
	public void setWrongNum(int num) {
		this.allLabels[1].setText("答错: " + num );
	}
	
	public void initBtns() {
		String[] btnName = new String[] {"主界面", "退出"};
		for(int i = 0; i < 2; i++) {
			allBtns[i] = new Button(btnName[i]);
			setBtnStyle(allBtns[i]);
			if(btnName[i].equals("退出")) {
				allBtns[i].setOnAction(exitevt->{
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.stage.close();
				});
			}
		}
	}
	
	public Button getBackBtn() {
		return this.allBtns[0];
	}
	
	//设置TextField样式
	public void setTextFieldStyle(TextField tf,String prom) {
		tf.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		tf.setMinWidth(80);
		tf.setPromptText(prom);
	}
	
	//设置标签的样式
	public void setLabelStyle(Label label) {
		label.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		label.setMinSize(50, 25);
	}
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		btn.setPrefSize(70, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,10));
		btn.setTextAlignment(TextAlignment.CENTER);

	}

	public void initFlowPane(FlowPane fp) {
		fp.setMinSize(280, 50);
		fp.setStyle("-fx-background:transparent;-fx-background-color: rgba(100,100,100,0.85);");
		//this.addStars(10);
	}
	
	public void initHBox(HBox pane) {
		pane.setMinSize(240, 40);
		pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(100,100,100,0.85);" );

	}
	
	//设置主面板
	public void initMainPane(Pane main_pane) {
		main_pane.setPadding(new Insets(20, 20, 20, 20));
		main_pane.setPrefSize(280, 300);
		main_pane.setMinSize(280, 300);
		main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(20,20,20,0.85);" );
	}
	
	public void installWidgetsOnMainPane() {
		for(Label label:allLabels) {
			this.mainpane.getChildren().add(label);
			this.mainpane.setMargin(label, new Insets(5,5,5,5));
		}
	}

	private void installWidgetsOnHBox(Pane pane) {
		for(int i = 0; i < allBtns.length; i++) {
			pane.getChildren().add(allBtns[i]);
			((HBox) pane).setMargin(allBtns[i], new Insets(10,10,10,10));
		}
	}
	
	public void addStars(int num) {
		//System.out.println("in addstars,starnum:"+this.starnum);
		for(int i = 0; i < this.starnum; i++) {
			this.starfp.getChildren().remove(0);
		}
		this.starnum = num;
		String starpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/star_24px.png";
		for(int i = 0; i < num; i++) {
			Image star = new Image(starpath);
			ImageView starview = new ImageView(star);
			this.starfp.getChildren().add(starview);
		}
		
		
	}
	
	
}