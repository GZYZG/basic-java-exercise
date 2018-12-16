package MyPaint;

import javafx.scene.control.Button;

final class winattr{
	static double winMinWidth = 800;
	static double winMinHeight = 600;
	static double mainPaneMinWidth = 400;
	static double mainPaneMinHeight = 400;
	
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