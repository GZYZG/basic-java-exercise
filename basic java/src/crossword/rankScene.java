package crossword;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Integer;
import java.lang.String;

class RankScene extends Scene{

	private static VBox mainpane = new VBox();
	private static TableView<RankRecord> ranktable = new TableView<RankRecord>();
	private ObservableList<RankRecord> data = 
			FXCollections.observableArrayList();
	String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\git\\basic java\\resources\\rankinfo.dat";
	Stage stage;
	Button back;
	
	public RankScene(Stage stage) {
		super(mainpane);
		
		back = new Button("返回");
		setBtnStyle(back);
		this.initTable();
		//this.readRankRecord();
		this.mainpane.getChildren().add(this.ranktable);
		this.mainpane.setMargin(this.ranktable, new Insets(10,10,10,10));
		this.mainpane.getChildren().add(back);
		this.mainpane.setMargin(back, new Insets(10,60,10,60));
		this.setFill(null);
		this.stage = stage;
		
		
	}
	
	
	public ObservableList<RankRecord> getData(){
		return this.data;
	}
	
	public void setBtnStyle(Button btn) {
		DropShadow shadow = new DropShadow();
		//btn.setPadding(new Insets(5,15,5,15));
		btn.setPrefSize(80, 30);
		btn.setEffect(shadow);
		btn.setFont(Font.font("Lucida Grande",FontWeight.BOLD,FontPosture.ITALIC,12));
		btn.setTextAlignment(TextAlignment.CENTER);

	}
	
	public void setColumnStyle(TableColumn<RankRecord,?> col,int minwidth, int maxwidth,String field, String style) {
		col.setMinWidth(minwidth);
		col.setMaxWidth(maxwidth);
		col.setStyle(style);
		col.setCellValueFactory(new PropertyValueFactory<>(field) );
	}
	
	public void initTable() {
		TableColumn<RankRecord,Integer> rankNumberCol = new TableColumn<RankRecord,Integer>("排名");
		setColumnStyle(rankNumberCol, 60, 60, "rankNumber", "-fx-background-color: rgba(50,50,50);-fx-text-fill:white;-fx-font-size:14px");

		TableColumn<RankRecord,String> playerNameCol = new TableColumn<>("玩家");
		setColumnStyle(playerNameCol, 80, 80, "playerName", "-fx-background-color: rgba(70,70,70);-fx-text-fill:white;-fx-font-size:15px");

		TableColumn<RankRecord,Integer> scoreCol = new TableColumn<RankRecord,Integer>("得分");
		setColumnStyle(scoreCol, 75, 75, "score", "-fx-background-color: rgba(90,90,90);-fx-text-fill:white;-fx-font-size:13px");

		this.ranktable.setMaxWidth(220);
		this.ranktable.setMinWidth(180);
		this.ranktable.setMaxHeight(350);
		this.ranktable.setMaxHeight(300);
		this.ranktable.setStyle("-fx-background:transparent;-fx-background-color: rgba(150,150,150);");
		this.ranktable.getColumns().addAll(rankNumberCol, playerNameCol, scoreCol);
		this.ranktable.setItems(data);
	
	}
	
	public void sortByScore() {
		RankRecord temp;
		boolean ischanged = false;
		for(int i = 0; i < this.data.size(); i++) {
			for(int j = 0; j < this.data.size() - i - 1; j++) {
				if( this.data.get(j).getScore() < this.data.get(j+1).getScore() ) {
					ischanged = true;
					temp = this.data.get(j);
					this.data.set(j, this.data.get(j+1));
					this.data.set(j+1, temp);
					this.data.get(j).setRankNumber(j+1);
					this.data.get(j+1).setRankNumber(j+2);
					
				}
			}
			if(!ischanged) {
				break;
			}
		}
		this.ranktable.setItems(data);
	}
	
	//设置主面板
	public void initMainPane(Pane main_pane) {
		main_pane.setPadding(new Insets(20, 40, 20, 40));
		main_pane.setPrefSize(280, 300);
		main_pane.setMinSize(280, 300);
		main_pane.setStyle("-fx-background:transparent;-fx-background-color: rgba(30,30,30);" );
	}
	
	
	public void readRankRecord() {
		System.out.println("in readRankRecord!");
		FileInputStream infile;
		try {
			int count = 1;
			infile = new FileInputStream(file_path);
			ObjectInputStream objReader = new ObjectInputStream(infile);
			while(true) {
				try {
					record record = (record)objReader.readObject();
					record.setRankNum(count);
					count++;
					data.add(record.toRankRecord());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("readRankRecord done");
		}

		
	}
	
	public void writeRankRecord() {
		System.out.println("data.size:"+data.size());
		try {
			FileOutputStream outfile = new FileOutputStream(file_path);
			ObjectOutputStream objWriter = new ObjectOutputStream(outfile);
			
			for(int i = 0; i < data.size(); i++) {
				//System.out.println("is writing 2 file:"+data.get(i).toString());
				objWriter.writeObject(data.get(i).toRecord());
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}