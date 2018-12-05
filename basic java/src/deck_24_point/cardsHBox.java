package deck_24_point;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

class cardsHBox extends HBox{
	ArrayList<Image> cardlist;
	
	public cardsHBox() {
		this.setPrefSize(300, 220);
		this.setPadding(new Insets(5,5,5,5));
		this.setStyle("-fx-background:transparent;-fx-background-color: rgba(255,255,255,0.85);" );
		this.cardlist = new ArrayList<Image>();
	}
	
	public boolean addCard(Image img) {
		if(cardlist.size() >= 4) {
			return false;
		}
		ImageView imgview = new ImageView(img);
		imgview.resize(60, 100);
		this.getChildren().add(imgview);
		this.cardlist.add(img);
		System.out.println("add card success");
		return true;
	}
	public void setCardDesk(int cardnum) {
		System.out.println("in setcarddesk");
		String cardpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/card/";
		Image card = new Image(cardpath+"b2fv.png");
		//this.getChildren()c
		this.cardlist.clear();
		for(int i = 0; i < cardnum; i++) {
			ImageView imgview = new ImageView(card);
			imgview.resize(60, 100);
			this.getChildren().add(imgview);
			this.cardlist.add(card);
		}
	}

	
	
	
	
}