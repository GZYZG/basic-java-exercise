package MyPaint;

import java.io.Serializable;
import java.util.Date;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

interface Copy{
	public Shape deepCopy(double x, double y);
	public Shape deepCopy(double... points);
	public double getPosX();
	public double getPosY();
	public double getArea();
	public double getPerimeter();
	public double[] getPos();
	public String getID();
	public double getwidth();
	public double getheight();
	public double getradius();
	public String getLineColor();
	public String getFillColor();
	public double getLineWidth();
	public Shape reply();
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor);
	
}

class MyRandomLine extends Line implements Serializable, Copy{
	protected Date createDate;
	protected double[] pos;
	protected double lineWidth;
	protected String lineColor;
	protected String fillColor;
	
	public MyRandomLine(double lineWidth, Paint lineColor, Paint fillColor, double points) {
		
	}

	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double[] getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		
	}
	
	
}

class MyStraitLine extends Line implements Serializable, Copy{
	
	protected Date createDate;
	protected double[] pos;
	protected double lineWidth;
	protected String lineColor;
	protected String fillColor;
	
	public MyStraitLine(double startX, double startY, double endX, double endY, 
			double lineWidth, Paint lineColor, Paint fillColor) {
		super(startX, startY, endX, endY);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.pos = new double[] {startX, startY, endX, endY};
		this.createDate = new Date();
		this.lineWidth = lineWidth;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
	}
	
	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		double offsetX = x-this.pos[0];
		double offsetY = y-this.pos[1];
		MyStraitLine newCircle = new MyStraitLine(x,y,this.pos[2]+offsetX, this.pos[3]+offsetY,
				this.getStrokeWidth(), this.getStroke(), this.getFill());
		return newCircle;
	}

	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return this.deepCopy(points[0], points[1]);

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
	public double[] getPos() {
		// TODO Auto-generated method stub
		return this.pos;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MyStraitLine@"+this.createDate.getTime();
	}

	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}

	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}

	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}

	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		Paint lineColor = this.lineColor.equals("")?null:Color.valueOf(this.lineColor);
		MyStraitLine newShape = new MyStraitLine(this.getPosX(), this.getPosY(), this.pos[2], this.pos[3],
				this.lineWidth, lineColor, fill);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	public String toString() {
		return this.getID()+"[ pos=[startX="+(int)this.getPosX()+","
				+ " StartY="+(int)this.getPosY()+", endX="+(int)this.pos[2]+", endY="+(int)this.pos[3]+"] length="+(int)this.getPerimeter()+
				", strokeWidth="+(int)this.lineWidth+", stroke="+this.lineColor+", fill="+this.fillColor+"]";
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return Math.sqrt((this.pos[0]-this.pos[2])*(this.pos[0]-this.pos[2]) + (this.pos[1]-this.pos[3])*(this.pos[1]-this.pos[3]));
	}

	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
	}
	
	
}

class MyRectangle extends Rectangle implements Serializable, Copy{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8577986604588650981L;

	protected Date createDate;
	protected double[] pos;
	protected double width, height, lineWidth;
	protected String lineColor;
	protected String fillColor;
	
	public MyRectangle(double x, double y, double width, double height, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x,y,width,height);
		this.setFill(fillColor);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
		this.width = width;
		this.height = height;
		this.lineWidth = lineWidth;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
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
		return (int)this.getWidth() * (int)this.getHeight();
	}
	
	public double getPerimeter() {
		return ( (int)this.getWidth() + (int)this.getHeight() ) * 2;
	}
	
	public Date getDate() {
		return this.createDate;
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
	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return this.deepCopy(points[0], points[1]);
	}
	
	public Shape reply() {
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		Paint lineColor = this.lineColor.equals("")?null:Color.valueOf(this.lineColor);
		MyRectangle newShape = new MyRectangle(this.getPosX(), this.getPosY(), this.getwidth(), this.getheight(),
				this.lineWidth, lineColor, fill);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	public String toString() {
		return 
		this.getID()+"[ pos=[x="+(int)this.getPosX()+", y="+(int)this.getPosY()+"] width="+(int)this.width+", height="+(int)this.height+
		", area="+(int)this.getArea()+", perimeter="+(int)this.getPerimeter()+
		", strokeWidth="+(int)this.lineWidth+", stroke="+this.lineColor+", fill="+this.fillColor+"]";
	}
	
	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}
	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}
	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}
	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method not support");
	}



	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
	}
	
	public Rectangle resize() {
		Rectangle rect = new Rectangle(this.getPosX(), this.getPosY(), this.width, this.height);
		rect.setStrokeDashOffset(10);
		rect.setFill(null);
		rect.getStrokeDashArray().addAll(5d,5d,5d,5d);
		rect.setStroke(((Color)this.getStroke()).invert());
		
		return rect;
	}
	
}

class MySquare extends MyRectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3555599912704077113L;


	
	public MySquare(double x, double y, double edgeLen, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x,y,edgeLen, edgeLen, lineWidth, lineColor, fillColor);
	}
	
}

class MyCircle extends Circle implements Serializable, Copy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105246695834814882L;
	private double[] pos;
	private Date createDate;
	protected double radius, lineWidth;
	protected String lineColor;
	protected String fillColor;
	
	
	public MyCircle(double x, double y, double radius, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x, y, radius);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
		this.radius = radius;
		this.lineColor = ((Color)lineColor).toString();
		this.fillColor = fillColor==null?"":((Color)fillColor).toString();
		this.lineWidth = lineWidth;
		
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

	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		MyCircle newCircle = new MyCircle(x, y, this.getRadius(),
				this.getStrokeWidth(), this.getStroke(), this.getFill());
		return newCircle;
	}
	
	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return this.deepCopy(points[0], points[1]);
	}



	@Override
	public double[] getPos() {
		// TODO Auto-generated method stub
		return this.pos;
	}



	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}



	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}



	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}



	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}



	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}



	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		return this.radius;
	}



	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		MyCircle newShape = new MyCircle(this.getPosX(), this.getPosY(), this.radius, this.lineWidth,
				Color.valueOf(this.lineColor), fill);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	public String toString() {
		return this.getID()+"[ pos=[x="+(int)this.getPosX()+", y="+(int)this.getPosY()+"] radius="+(int)this.radius+
				", area="+this.getArea()+
				", strokeWidth="+this.lineWidth+
				", stroke="+this.lineColor+", fill="+this.fillColor+", createDate="+this.createDate+"]";
	}



	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return Math.PI*(int)this.radius*(int)this.radius;
	}



	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return 2*Math.PI*(int)this.radius;
	}



	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
	}
	
}

class MyText extends Text implements Serializable, Copy{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7436172851065445687L;
	private double[] pos;
	private Date createDate;
	protected double lineWidth, size;
	protected String lineColor, fillColor, fontFamily, text;
	
	
	public MyText(double x, double y, String text, Font font, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x, y, text);
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.setFont(font);
		this.pos = new double[] {x,y};
		this.createDate = new Date();
		this.lineWidth = lineWidth;
		this.lineColor = ( lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = ( fillColor==null?"":((Color)fillColor).toString() );
		this.fontFamily = font.getFamily();
		this.size = font.getSize();
		this.text = text;
		
		//System.out.println(Font.getFamilies());
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
	
	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		MyText newText = new MyText(x, y, this.getText(), this.getFont(), this.getStrokeWidth(), this.getStroke(), 
				this.getFill());
		return newText;
	}

	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return this.deepCopy(points[0], points[1]);
	}



	@Override
	public double[] getPos() {
		// TODO Auto-generated method stub
		return this.pos;
	}


	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}



	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}



	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}



	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}



	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}



	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}



	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		MyText newShape = new MyText(this.getPosX(), this.getPosY(), this.text, new Font(this.fontFamily, this.size),
				this.lineWidth, Color.valueOf(this.lineColor), fill);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	
	public String toString() {
		return this.getID()+"[ pos=[x="+(int)this.getPosX()+", y="+(int)this.getPosY()+"] "+" text="+this.text+" size="+this.size+", strokeWidth="+this.lineWidth+
				", stroke="+this.lineColor+", fill="+this.fillColor+", createDate="+this.createDate+"]";
	}



	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void changeTextAttr(double lineWidth, double size, String fontFamily, Paint lineColor, Paint fillColor) {
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.setFont( new Font(fontFamily, size) );
		this.lineWidth = lineWidth;
		this.size = size;
		this.fontFamily = fontFamily;
		this.lineColor = (lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = (fillColor==null?"":((Color)fillColor).toString() );
	}

	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		
	}
}

class MyEllipse extends Ellipse implements Serializable, Copy{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[] pos;
	private Date createDate;
	protected double lineWidth, radiusX, radiusY;
	protected String lineColor, fillColor;
	
	public MyEllipse(double x, double y, double radiusX, double radiusY, double lineWidth, Paint lineColor, Paint fillColor) {
		super(x, y, radiusX, radiusY);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.setStrokeWidth(lineWidth);
		this.radiusX = radiusX;
		this.radiusY = radiusY;
		this.createDate = new Date();
		this.pos = new double[] {x,y};
		this.lineWidth = lineWidth;
		this.lineColor = ( lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = ( fillColor==null?"":((Color)lineColor).toString() );
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
	public double[] getPos() {
		// TODO Auto-generated method stub
		return this.pos;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return "MyEllipse@"+this.createDate.getTime();
	}

	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		return this.radiusX;
	}

	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		return this.radiusY;
	}

	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsuported");
	}

	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}

	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}

	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}

	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		Paint lineColor = this.lineColor.equals("")?null:Color.valueOf(this.lineColor);
		MyEllipse newShape = new MyEllipse(this.getPosX(), this.getPosY(), this.getwidth(), this.getheight(),
				this.lineWidth, lineColor, fill);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		MyEllipse newShape = new MyEllipse(x, y, this.radiusX, this.radiusY,
				this.lineWidth, this.getStroke(), this.getFill());
		newShape.createDate = this.createDate;
		System.out.println("new Ellipse:"+newShape.toString());
		return newShape;
	}

	@Override
	public Shape deepCopy(double... points) {
		// TODO Auto-generated method stub
		return this.deepCopy(points[0], points[1]);
	}
	
	public String toString() {
		return this.getID()+"[ pos=[x="+(int)this.getPosX()+", y="+(int)this.getPosY()+"] "+" Xradius="+(int)this.radiusX+
			" Yradius="+(int)this.radiusY+", area="+(int)this.getArea()+", perimeter="+(int)this.getPerimeter()+
			", strokeWidth="+this.lineWidth+", stroke="+this.lineColor+", fill="+this.fillColor+", createDate="+this.createDate+"]";
	}



	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		return (int)this.radiusX*(int)this.radiusY*Math.PI;
	}



	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return 4*((int)this.radiusX+(int)this.radiusY - (4-Math.PI)*((int)this.radiusX*(int)this.radiusY)/((int)this.radiusX+(int)this.radiusY));
	}



	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = ( lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = ( fillColor==null?"":((Color)fillColor).toString() );
	}

	
	
}

class MyPolygon extends Polygon implements Serializable ,Copy{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3713518895459604096L;
	private static Date createDate;
	private static double[] pos;
	protected double lineWidth;
	protected String lineColor, fillColor;
	
	
	public MyPolygon(double lineWidth, Paint lineColor, Paint fillColor, double...points) {
		super(points);
		this.pos = new double[this.getPoints().size()];
		for(int i = 0; i < points.length; i++) {
			this.pos[i] = this.getPoints().get(i);
		}
		this.createDate = new Date();
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = ( lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = ( fillColor==null?"":((Color)fillColor).toString() );
		
	}
	
	@Override
	public double[] getPos() {
		double[] points = new double[this.getPoints().size()];
		for(int i = 0; i < points.length; i++) {
			points[i] = this.getPoints().get(i);
		}
		return points;
	}
	
	@Override
	public Shape deepCopy(double x, double y) {
		// TODO Auto-generated method stub
		//MyPolygon newPolygon = new MyPolygon
		throw new UnsupportedOperationException("method unsupport");
	}
	
	@Override
	public Shape deepCopy(double ...points) {
		// TODO Auto-generated method stub
		MyPolygon newPolygon = new MyPolygon( this.getStrokeWidth(), this.getStroke(), this.getFill(), points);
		return newPolygon;
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
		return "MyPolygon@"+this.createDate.getTime();
	}

	@Override
	public String getLineColor() {
		// TODO Auto-generated method stub
		return this.lineColor;
	}

	@Override
	public String getFillColor() {
		// TODO Auto-generated method stub
		return this.fillColor;
	}

	@Override
	public double getLineWidth() {
		// TODO Auto-generated method stub
		return this.lineWidth;
	}

	@Override
	public double getwidth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}

	@Override
	public double getheight() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}

	@Override
	public double getradius() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("method unsupport");
	}

	@Override
	public Shape reply() {
		// TODO Auto-generated method stub
		Paint fill = this.fillColor.equals("")?null:Color.valueOf(this.fillColor);
		MyPolygon newShape = new MyPolygon(this.lineWidth, Color.valueOf(this.lineColor), fill, this.pos);
		newShape.createDate = this.createDate;
		return newShape;
	}
	
	public String toString() {
		String pos = "";
		for ( double p:this.pos) {
			pos += (int)p+" ";
		}
		return this.getID()+"[ pos=["+pos+"] area="+(int)this.getArea()+", perimeter="+(int)this.getPerimeter()+
				", strokeWidth="+this.lineWidth+", stroke="+this.lineColor+", fill="+this.fillColor+", createDate="+this.createDate+"]";
	}

	@Override
	public double getArea() {
		// TODO Auto-generated method stub
		double p = this.getPerimeter();
		return Math.sqrt(p*
				(p-Math.sqrt((this.pos[0]-this.pos[2])*(this.pos[0]-this.pos[2])+(this.pos[1]-this.pos[2])*(this.pos[0]-this.pos[3])))*
				(p-Math.sqrt((this.pos[2]-this.pos[4])*(this.pos[2]-this.pos[4])+(this.pos[3]-this.pos[5])*(this.pos[3]-this.pos[5])))*
				(p-Math.sqrt((this.pos[4]-this.pos[0])*(this.pos[4]-this.pos[0])+(this.pos[5]-this.pos[1])*(this.pos[5]-this.pos[1]))));
	}

	@Override
	public double getPerimeter() {
		// TODO Auto-generated method stub
		double p = 0;
		for (int i = 0; i < this.pos.length; i+=2) {
			p += Math.sqrt((this.pos[i]-this.pos[(i+2)%this.pos.length])*(this.pos[i]-this.pos[(i+2)%this.pos.length]) + 
					(this.pos[i+1]-this.pos[(i+3)%this.pos.length])*(this.pos[i+1]-this.pos[(i+3)%this.pos.length]));
		}
		return p;
	}

	@Override
	public void changeAttr(double lineWidth, Paint lineColor, Paint fillColor) {
		// TODO Auto-generated method stub
		this.setStrokeWidth(lineWidth);
		this.setStroke(lineColor);
		this.setFill(fillColor);
		this.lineWidth = lineWidth;
		this.lineColor = ( lineColor==null?"":((Color)lineColor).toString() );
		this.fillColor = ( fillColor==null?"":((Color)fillColor).toString() );
	}
}