package MyPaint;




import java.util.ArrayList;

import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;


final class MyCanvas extends Pane {
	private ToolsPane tool;
	private Integer lineType;
	private static boolean startPaint = false;
	private static boolean isChosedShape = false;
	private static int clickedTimes = 0;
	private double x;
	private double y;
	private TextArea input = null;
	private static Shape shape = null;
	private static Shape dragged = null;
	private static Shape chosedShape = null;
	private static winattr attr = new winattr();
	private static ArrayList<Shape> allChosedShapes;
	private static TextArea canvasInfo = null;
	private static VBox operatePane;
	private static Stage stage;
	
	public MyCanvas(Stage stage, ToolsPane tool) {
		super();
		this.stage = stage;
		this.tool = tool;
		this.lineType = tool.lineTypeProperty();
		this.prefWidthProperty().bind(stage.widthProperty().divide(1.1));
		this.prefHeightProperty().bind(stage.heightProperty().divide(1.5));
		this.setStyle("-fx-background-color: rgba(255,255,255);");
		this.setEffect(new InnerShadow());
		this.setViewOrder(5);
		this.setCursor(Cursor.cursor("CROSSHAIR"));
		this.bindEvents4Canvas();
		this.allChosedShapes = new ArrayList<>();
		
		this.createCanvasInfoLabel(stage);
		
		this.setOnKeyPressed(e->{
			System.out.println("deling");
		});
	}
	
	@SuppressWarnings("static-access")
	public void bindEvents4Canvas() {
		this.setOnMouseMoved(moveEvt->{
			this.canvasInfo.setText("x:"+(int)moveEvt.getX()+", y:"+(int)moveEvt.getY());
			for ( Shape s:this.allChosedShapes) {
				this.canvasInfo.setText(this.canvasInfo.getText()+"\n"+s.toString());
			}
		});
		
		this.setOnMousePressed(pe->{
			if ( this.getChildren().contains(this.operatePane)) {
				this.getChildren().remove(this.operatePane);
			}
			if (pe.isSecondaryButtonDown()) {
				System.out.println("primary down");
				this.operatePane = this.createOperatePanel(pe.getX(), pe.getY());
				this.getChildren().add(this.operatePane);
				return ;
			}
			this.startPaint = true;
			//System.out.println("start");
			this.x = pe.getX();
			this.y = pe.getY();
			if (this.startPaint /*&& !this.isChosedShape*/ && this.tool.getSelectedShape().equals("文本") && this.clickedTimes == 0) {
				if (this.input != null) {
					
					Shape shapeObj = new MyText(input.getTranslateX(), input.getTranslateY()+input.getHeight(), 
							input.getText(), new Font(this.tool.getSelectedFontFamily(), this.tool.getSelectedFontSize()), 
							this.tool.getSelectedLineType(), this.tool.getSelectedForeColor(), this.tool.getSelectedBackColor());
					this.getChildren().remove(this.input);
					this.getChildren().add(shapeObj);
					this.bindEvents4Shape(shapeObj);
					this.input = null;
				}else {
					this.input = new TextArea();
					this.input.setPrefHeight(this.tool.getSelectedFontSize());
					this.input.setPrefWidth(3);
					this.input.setStyle("-fx-background-color:Transparent");
					
					this.input.setOnKeyPressed(e->{
						this.input.setFont(new Font(this.tool.getSelectedFontFamily(), this.tool.getSelectedFontSize()));
						if ( e.getCode() == KeyCode.ENTER) {
							this.input.setPrefHeight(this.input.getHeight()+this.tool.getSelectedFontSize());
						}else {
							this.input.setPrefWidth(this.input.getWidth()+8);
						}
						
						//
						System.out.println("aaa");
					});
					this.input.setTranslateX(this.x);
					this.input.setTranslateY(this.y);
					this.getChildren().add(this.input);
				}

			}
			
		});
		
		this.setOnMouseDragged(mouseDraggedEvt->{
			if( this.startPaint && !this.isChosedShape) {
				if( this.getChildren().size() >= 1 && this.shape != null)
					this.getChildren().remove(this.shape);

				this.createShapes();
				
				String shapeType = this.tool.getSelectedShape();
				if (shapeType.equals("")) {
					return;
				}
				
				
				double lineWidth = this.tool.getSelectedLineType();
				Color lineColor = this.tool.getSelectedForeColor();
				String brush = this.tool.getSelectedBrush();
				Shape shapeObj = null;
				
				double startX = Math.min(this.x, mouseDraggedEvt.getX() );
				double startY = Math.min(this.y, mouseDraggedEvt.getY() );
				if ( shapeType.equals("长方形") ) {
					//画长方形
					
					shapeObj = new MyRectangle(startX, startY,
							Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y),
							lineWidth, lineColor, this.tool.getSelectedBackColor());
				}
				if ( shapeType.equals("正方形")) {
					//画正方形
					
					shapeObj = new MySquare(startX, startY,
							Math.max(Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y)),
							lineWidth, lineColor, this.tool.getSelectedBackColor());
				}
				if ( shapeType.equals("圆形")) {
					//画圆形
					shapeObj = new MyCircle(this.x, this.y, 
							Math.max(Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y)),
							lineWidth, lineColor, this.tool.getSelectedBackColor());	
					
				}
				if ( shapeType.equals("三角形")) {
					//画三角形
					shapeObj = new MyPolygon(lineWidth, lineColor, this.tool.getSelectedBackColor(), 
							this.x, this.y, 
							mouseDraggedEvt.getX(), mouseDraggedEvt.getY(),
							2*this.x-mouseDraggedEvt.getX(), mouseDraggedEvt.getY());
				}
				if ( shapeType.equals("椭圆")) {
					//画椭圆
					shapeObj = new MyEllipse(this.x, this.y, 
							Math.abs(mouseDraggedEvt.getX()-this.x), Math.abs(mouseDraggedEvt.getY()-this.y),
							lineWidth, lineColor, this.tool.getSelectedBackColor());	
					
				}
				if ( shapeType.equals("直线")) {
					//画椭圆
					shapeObj = new MyStraitLine(this.x, this.y,mouseDraggedEvt.getX(), mouseDraggedEvt.getY(),
							lineWidth, lineColor, this.tool.getSelectedBackColor());	
					
				}
				
				if ( shapeObj != null) {
					this.getChildren().add(shapeObj);
					this.shape = shapeObj;
				}
				
				
					
				
			}
		
		});
		
		
		this.setOnMouseReleased(re->{
			this.startPaint = false;
			if (this.shape == null) {
				return;
			}
			if ( this.shape != null && !this.getChildren().contains(this.shape)) {
				this.getChildren().add(this.shape);
				this.shape.setViewOrder(6);
				System.out.println("add successfully");
			}
				
			Node tmp = this.getChildren().get(this.getChildren().size()-1);
			this.bindEvents4Shape(tmp);
		
			//System.out.println("released");
			this.shape = null;
			
		});
	}

	public void bindEvents4Shape(Node shape) {
		
		shape.setOnMousePressed(clickEvt->{
			
			if (this.chosedShape != shape) {
				this.clickedTimes = 0;
			}
			
			MyCanvas.clickedTimes ++;
			if ( MyCanvas.clickedTimes == 1) {
				this.x = clickEvt.getX();
				this.y = clickEvt.getY();
				shape.setEffect(attr.ds);
				if ( !this.allChosedShapes.contains(shape) ) {
					this.allChosedShapes.add((Shape) shape);
				
					for ( int i = 0; i < 4; i++) {
						MyRectangle c = new MyRectangle(0, 0, ((Copy)shape).getLineWidth(), ((Copy)shape).getLineWidth(), 1, Color.RED, Color.RED);
						if (i == 0) {
							c.setTranslateX(((Copy)shape).getPosX());
							c.setTranslateY(((Copy)shape).getPosY());
						}else if (i==1) {
							c.setTranslateX(((Copy)shape).getPosX() + ((Copy)shape).getwidth());
							c.setTranslateY(((Copy)shape).getPosY());
						}else if (i==2) {
							c.setTranslateX(((Copy)shape).getPosX());
							c.setTranslateY(((Copy)shape).getPosY() + ((Copy)shape).getheight());
						}else if (i==3) {
							c.setTranslateX(((Copy)shape).getPosX() + ((Copy)shape).getwidth());
							c.setTranslateY(((Copy)shape).getPosY() + ((Copy)shape).getheight());
						}
						c.setOnMouseDragged(e->{
							System.out.println("aaa");
							shape.setTranslateX(((Copy)shape).getPosX()+e.getX()-c.getPosX());
							shape.setTranslateY(((Copy)shape).getPosY()+e.getY()-c.getPosY());
						});
						this.getChildren().add(c);
						
					}
					
					
				} 
				this.chosedShape = (Shape)shape;
				return;
			}
			
			if ( MyCanvas.clickedTimes == 2) {
				MyCanvas.clickedTimes = 0;
				MyCanvas.isChosedShape = true;
				
				
				this.chosedShape = (Shape)shape;
				return;
			}	
			if ( this.input != null) {
				this.getChildren().remove(this.input);
			}
		});
		
		
		shape.setOnMouseDragged(dragEvt->{
			if ( MyCanvas.isChosedShape) {
				
				if ( this.dragged != null && this.getChildren().contains(this.dragged) ) {
					this.getChildren().remove( this.dragged );
				}
				double offsetX = dragEvt.getX() - this.x;
				double offsetY = dragEvt.getY() - this.y;
				
				double[] pos = ((Copy)shape).getPos();
				double[] points = new double[pos.length];
				for (int i = 0; i < points.length; i += 2) {
					points[i] = pos[i]+offsetX;
					points[i+1] = pos[i+1]+offsetY;
				}
	
				Shape newShape = ((Copy) shape).deepCopy( points );
				
				this.dragged = newShape;
				this.dragged.setEffect(attr.ds);
				this.getChildren().add(newShape);
				shape.setVisible(false);
				this.shape = newShape;
			}
		});

		shape.setOnMouseReleased(releaseEvt->{
			if ( !MyCanvas.isChosedShape ) {
				return;
			}
		
			if ( this.dragged != null) {
				this.dragged.setEffect(null);
				this.getChildren().remove(this.chosedShape);
				this.allChosedShapes.remove(this.chosedShape);
			}else {
				this.chosedShape.setEffect(null);			
			}
			
			if ( this.allChosedShapes.contains(this.chosedShape) && this.chosedShape.getEffect() == null) {
				this.allChosedShapes.remove(this.chosedShape);
			}
			
			if (this.getChildren().contains(this.chosedShape))
				System.out.println("Yes");
			MyCanvas.clickedTimes = 0;
			MyCanvas.isChosedShape = false;
			this.dragged = null;
		});		
	}
	
	/*
	* setWidth和setHeight方法会导致死循环所以导致栈溢出问题
	* 因为时重写了Pane的方法，所以会导致子类自己调用自己，导致无限循环调用自己
	*可以改成调用父类的setWidth和setHeight方法*/
	public void setWidth(double width) {
		super.setWidth(width);
	}
	
	public void setHeight(double height) {
		super.setHeight(height);
	} 
	
	public void bindWidthProperty(Node node) {
		this.prefWidthProperty().bind(((Pane)node).widthProperty());
	}
	
	public void bindHeightProperty(Node node) {
		this.prefHeightProperty().bind(((Pane)node).heightProperty());
	}

	/*
	 * 画图的流程：
	 * 		先第一次按下鼠标后，记录下最开始的点；
	 * 		然后等到鼠标释放；
	 * 		绘制相应的图形。
	 */
	private Shape createShapes() {
		/*
		 * 根据this.tool中的选定的形状来绘制相应的形状
		 */
		
		String shape = this.tool.getSelectedShape();
		if (shape.equals("")) {
			return null;
		}
		
		Shape shapeObj = null;
		int lineWidth = this.tool.getSelectedLineType();
		Color lineColor = this.tool.getSelectedForeColor();
		String brush = this.tool.getSelectedBrush();
		
		if ( shape.equals("长方形") ) {
			//画长方形
			//shapeObj = new MyRectangle();
		}
		if ( shape.equals("正方形")) {
			
		}
		if ( shape.equals("圆形")) {
					
		}
		if ( shape.equals("三角形")) {
			
		}
		if ( shape.equals("文本")) {
			
		}
		
		return shapeObj;
		
	}
	
	public ArrayList<Shape> getAllChosedShape(){
		return this.allChosedShapes;
	}
	
	public void createCanvasInfoLabel(Stage stage) {
		this.canvasInfo = new TextArea();
		this.canvasInfo.setText("info");
		this.canvasInfo.prefWidthProperty().bind(stage.widthProperty());
		this.canvasInfo.prefHeightProperty().bind(stage.heightProperty().divide(10));
		this.canvasInfo.setStyle("-fx-background:rgb(100,100,180)");
		
		this.canvasInfo.setWrapText(true);
		this.canvasInfo.setEditable(false);
		
	}
	
	public TextArea getCanvasInfoLabel() {
		return this.canvasInfo;
	}
	
	public void addShapes(ArrayList<Shape> shapes) {
		
		for ( Shape s:shapes) {
			
			this.getChildren().add(s);
			this.bindEvents4Shape(s);
		}
	}
	
	public void deleteChosedShapes() {
		System.out.println("del");
		for ( Shape s:this.allChosedShapes) {
			System.out.println("deling");
			this.getChildren().remove(s);
			
		}
		this.allChosedShapes.clear();
	}

	
	public void changeChosedShapes() {
		if ( this.allChosedShapes == null)
			return;
		for ( Shape s: this.allChosedShapes) {
			if ( s instanceof MyText) {
				((MyText)s).changeTextAttr(this.tool.getSelectedLineType(), this.tool.getSelectedFontSize()
						, this.tool.getSelectedFontFamily(), tool.getSelectedForeColor(), this.tool.getSelectedBackColor() );
				continue;
			}
			((Copy)s).changeAttr(this.tool.getSelectedLineType(), this.tool.getSelectedForeColor(), this.tool.getSelectedBackColor());
		}
		
	}

	public VBox createOperatePanel(double x, double y) {
		VBox p = new VBox();
		Button deleteBtn = new Button("删除");
		deleteBtn.setOnAction(e->{
			this.deleteChosedShapes();
			//p.getChildren().remove(deleteBtn);
			this.getChildren().remove(this.operatePane);
			System.out.println("del done");
		});
		Button saveBtn = new Button("保存");
		saveBtn.setOnAction(e->{
			test t = new test();
			t.saveShape(this.stage, this.allChosedShapes);
			this.getChildren().remove(this.operatePane);
		});
		Button readBtn = new Button("读取");
		readBtn.setOnAction(e->{
			test t = new test();
			t.readShape(stage);
			this.getChildren().remove(this.operatePane);
		});
		Button clearBtn = new Button("清屏");
		clearBtn.setOnAction(e->{
			this.getChildren().clear();
			
		});
		ButtonStyle.setAllButtonsStyle(new Node[] {deleteBtn, saveBtn, readBtn, clearBtn}, "-fx-background-color:#ffffff;-fx-background-radius:0,0,0,0");
		p.getChildren().add(deleteBtn);
		p.setMargin(deleteBtn, new Insets(2,2,0,2));
		p.getChildren().add(saveBtn);
		p.setMargin(saveBtn, new Insets(2,2,2,2));
		p.getChildren().add(readBtn);
		p.setMargin(readBtn, new Insets(0,2,2,2));
		p.getChildren().add(clearBtn);
		p.setMargin(clearBtn, new Insets(0,2,2,2));
		p.setTranslateX(x);
		p.setTranslateY(y);
		p.setStyle("-fx-background-color:#999999");
		
		return p;
	}
}