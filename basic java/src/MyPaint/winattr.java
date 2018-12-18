package MyPaint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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

final class WRObject2File{
	
	public static ArrayList<Shape> readFromFile(String filePath) {
		System.out.println("in readShape!");
		FileInputStream infile;
		ArrayList<Shape> objList = new ArrayList<>();
		try {
			int count = 1;
			infile = new FileInputStream(filePath);
			ObjectInputStream objReader = new ObjectInputStream(infile);
			Shape obj;
			while(true) {
				try {
					obj = (Shape) objReader.readObject();
					count++;
					objList.add(obj);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("readShape done...");
		}

		return objList;
	}
	
	public static void writeObject2File(ArrayList<Shape> objList, String filePath) {
		System.out.println("objList.size:"+objList.size());
		try {
			FileOutputStream outfile = new FileOutputStream(filePath);
			ObjectOutputStream objWriter = new ObjectOutputStream(outfile);
			
			for(int i = 0; i < objList.size(); i++) {
				System.out.println("is writing 2 file:"+objList.get(i));
				objWriter.writeObject(objList.get(i));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("write shape done...");
		
	}
	
}
