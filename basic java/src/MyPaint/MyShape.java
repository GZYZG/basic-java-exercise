package MyPaint;

import java.io.Serializable;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

interface Copy{
	public Shape deepCopy(double x, double y);
	public double getPosX();
	public double getPosY();
	public String getID();
}

class MyRectangle extends Rectangle implements Serializable, Copy{
	protected Date createDate;
	
	protected double[] pos;
	
	public MyRectangle() {
		super();
		this.pos = new double[] {0.0, 0.0};
		this.createDate = new Date();
		this.setFill(null);
		this.setStroke(Color.BLACK);
	}
	public MyRectangle(double x, double y, double width, double height) {
		super(x,y,width,height);
		this.setFill(null);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
	}
	
	public MyRectangle(double x, double y, double width, double height, double lineWidth, Paint lineColor) {
		this(x,y,width,height);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(null);
	}
	
	public MyRectangle(double x, double y, double width, double height, double lineWidth, Paint lineColor, Paint fillColor) {
		this(x,y,width,height,lineWidth,lineColor);
		this.setFill(fillColor);
	}
	
	
	
	public double[] getPos() {
		return this.pos;
	}
	
	public double getPosX() {
		return this.pos[0];
	}
	
	public double getPosY() {
		return this.pos[1];
	}
	
	public double getArea() {
		return this.getWidth() * this.getHeight();
	}
	
	public double getPerimeter() {
		return ( this.getWidth() + this.getHeight() ) * 2;
	}
	
	
	
	//使用创建时的秒数来唯一标记形状
	public String getID() {
		return "MyRectangle@"+this.createDate.getTime();
	}
	
	public MyRectangle deepCopy(double x, double y) {
		MyRectangle newRect = new MyRectangle(x, y, this.getWidth(), this.getHeight(),
				this.getStrokeWidth(), this.getStroke(), this.getFill());

		return newRect;
	}

}

class MySquare extends MyRectangle {
	public MySquare() {
		
	}
	public MySquare(double x, double y, double edgeLen) {
		super(x, y, edgeLen, edgeLen);
		this.setStrokeWidth(3);
		this.setStroke(Color.BLACK);
		this.setFill(null);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
	}
	
	public MySquare(double x, double y, double edgeLen, double lineWidth, Paint lineColor, Paint fillColor) {
		this(x,y,edgeLen);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(null);
	}
	
}

class MyCircle extends Circle implements Serializable, Copy{
	
	private double[] pos;
	private Date createDate;
	
	public MyCircle(double x, double y, double radius, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x, y, radius);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
		
	}

	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		MyCircle newCircle = new MyCircle(x, y, this.getRadius(),
				this.getStrokeWidth(), this.getStroke(), this.getFill());
		return newCircle;
	}

	@Override
	public double getPosX() {
		// TODO Auto-generated method stub
		return this.pos[0];
	}

	@Override
	public double getPosY() {
		// TODO Auto-generated method stub
		return this.pos[1];
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MyCircle@"+this.createDate.getTime();
	}
	
}

class MyText extends Text implements Serializable, Copy{
	private double[] pos;
	private Date createDate;
	
	public MyText(double x, double y, String text, Font font, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x, y, text);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
		this.setFont(font);
	}

	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		MyText newText = new MyText(x, y, this.getText(), this.getFont(), this.getStrokeWidth(), this.getStroke(), 
				this.getFill());
		return newText;
	}

	@Override
	public double getPosX() {
		// TODO Auto-generated method stub
		return this.pos[0];
	}

	@Override
	public double getPosY() {
		// TODO Auto-generated method stub
		return this.pos[1];
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MyText@"+this.createDate.getTime();
	}
	
}