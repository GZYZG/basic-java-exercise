package MyPaint;




import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

final class MyCanvas extends Pane {
	private ToolsPane tool;
	private Integer lineType;
	private static boolean startPaint = false;
	private double x;
	private double y;
	public MyCanvas(Stage stage, ToolsPane tool) {
		super();
		this.tool = tool;
		this.lineType = tool.lineTypeProperty();
		this.prefWidthProperty().bind(stage.widthProperty().divide(1.1));
		this.prefHeightProperty().bind(stage.heightProperty().divide(1.5));
		this.setStyle("-fx-background-color: rgba(255,255,255);");
		this.setEffect(new InnerShadow());
		this.setViewOrder(5);
		
		this.setOnMousePressed(pe->{
			this.startPaint = true;
			System.out.println("start");
			this.x = pe.getX();
			this.y = pe.getY();
		});
		
		this.setOnMouseDragged(mouseDraggedEvt->{
			if( this.startPaint ) {
				this.getChildren().clear();
				System.out.println("dragged x:"+mouseDraggedEvt.getX()+"  y:"+mouseDraggedEvt.getY());
				Rectangle rect = new Rectangle(this.x, this.y, mouseDraggedEvt.getX()-this.x, mouseDraggedEvt.getY()-this.y);
				this.getChildren().add(rect);
			}
			
			
		});
		
		this.setOnMouseReleased(re->{
			this.startPaint = false;
			System.out.println("released");
			
		});
		
	}
	public MyCanvas(Stage stage, double width, double height) {
		super();
		this.setWidth(width);
		this.setHeight(height);
		this.setViewOrder(5);
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
	
	public int[] getMousePos() {
		int[] pos = new int[2];
		
		
		return pos;
	}
	
	
	/*
	 * 画图的流程：
	 * 		先第一次按下鼠标后，记录下最开始的点；
	 * 		然后等到鼠标释放；
	 * 		绘制相应的图形。
	 */
	private void drawShapes() {
		/*
		 * 根据this.tool中的选定的形状来绘制相应的形状
		 */
		String shape = this.tool.getSelectedShape();
		if (shape.equals("")) {
			return;
		}
		
		int lineWidth = this.tool.getSelectedLineType();
		Color lineColor = this.tool.getSelectedColor();
		String brush = this.tool.getSelectedBrush();
		
		if ( shape.equals("长方形") ) {
			//画长方形
		}
		if ( shape.equals("正方形")) {
			
		}
		if ( shape.equals("圆形")) {
					
		}
		if ( shape.equals("三角形")) {
			
		}
		if ( shape.equals("文本")) {
			
		}
		
		
		
	}
	
}