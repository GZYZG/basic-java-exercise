package MyPaint;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

final class winattr{
	static double winMinWidth = 800;
	static double winMinHeight = 600;
	static double mainPaneMinWidth = 400;
	static double mainPaneMinHeight = 400;
	static DropShadow ds = new DropShadow();
	
	public winattr() {
		this.ds.setColor(Color.web("#0e6008"));
		this.ds.setHeight(25);
		this.ds.setWidth(25);
		this.ds.setSpread(0.35);
	}
			
}

final class ButtonStyle{
	private static String defaultStyle = "-fx-background:transparent;-fx-background-radius:35;-fx-border-radius:55"
			+ ";-fx-font-size:10px";
	public static void setButtonStyle(Button btn) {
		ButtonStyle.setButtonStyle(btn, ButtonStyle.defaultStyle);
	}
	
	public static void setButtonStyle(Button btn, String style) {
		btn.setStyle(style);
	}

}
