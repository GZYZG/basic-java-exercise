package MyPaint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;



import MyPaint.ButtonStyle;;


/*
 * @author:GZY
 * @date:2018.12.16
 * description:创建工具栏，包含：
 * 		画笔选择下拉框；
 * 		形状选择下拉框；
 * 		线条选择下拉框；
 * 		颜色选择器；
 * 下拉框的实现方法：
 * 		创建标题窗格，将标题窗格加入到Accordion (手风琴控件，也许是因为这个控件可以拉伸吧)中
 *		创建流程：
 *			创建放置控件的面板A，将控件加入到该面板中
 *			创建标题面板B，设置标题面板的内容为A
 *			创建Accordion控件 C，获得C的面板列表，向其中添加标题面板
 */

class ToolsPane extends VBox{
	 //各个选项的图标的路径以及图标的名字
	
	private static String iconRootPath = "";//+"file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/";
	private static String[] brushIconPath = new String[] {"pencil_16px.png", "brush_16px.png"};
	private static String[] brushLabels = new String[] {"铅笔", "刷子"};
	private static String[] shapeIconPath = 
			new String[] {"randomline_16px.png", "straitline_16px.png", "rectangle_16px.png", "square_16px.png",
					"circle_16px.png" , "ellipse_16px.png", "triangle_16px.png", "text_16px.png"};
	private static String[] shapeLabels = new String[] {"任意线", "直线", "长方形", "正方形", "圆形", "椭圆", "三角形", "文本"};
	private static String[] lineTypeLabels = new String[] {"1px", "3px", "5px", "8px", "10px", "13px", "16px"};
	private static String[] lineTypeIconPath = new String[] {"line_1px.png", "line_3px.png", "line_5px.png", "line_8px.png",
					"line_10px.png", "line_13px.png", "line_16px.png"};
	
	protected static boolean hasStroke = false;
	protected static Boolean isFilled = false;
	private static String selectedBrush = "";
	private static String selectedLineType = "";
	private static String selectedShape = "长方形";
	private static Color selectedColor = null;
	private static String selectedFontFamily = "Microsoft YaHei";
	private static int selectedFontSize = 15;
	
	private Accordion brush;
	private Accordion shape;
	private Accordion lineType;
	private Accordion shapeAttrSelector;
	protected Button foreGroundColorSelector;
	protected Button backGroundColorSelector;
	protected Button foreBackColorSelector;
	private ColorPicker color;
	private ComboBox fontFamilySelector;
	private ComboBox fontSizeSelector;
	private boolean showColor = false;
	
	private MyCanvas canvas;
	
	public ToolsPane(Stage stage, MyCanvas canvas) {
		super(5);
		iconRootPath = "file:///"+(getClass().getResource("/").getPath().substring(0, getClass().getResource("/").getPath()
						.lastIndexOf("bin"))+"resources/").substring(1);
		HBox subPaneTop = new HBox();
		HBox subPaneMid = new HBox();
		this.setEffect(new DropShadow());
		this.setStyle("-fx-background-color:rgba(225,225,225)");
		this.prefWidthProperty().bind(stage.widthProperty());
		this.prefHeightProperty().bind(stage.heightProperty().divide(10));
		this.setMinHeight(40);
		this.setViewOrder(3);
		this.createTools();
		subPaneTop.getChildren().add(this.shape);
		subPaneTop.getChildren().add(this.lineType);
		subPaneTop.getChildren().add(this.foreGroundColorSelector);
		subPaneTop.getChildren().add(this.backGroundColorSelector);
		subPaneTop.getChildren().add(this.color);
		subPaneTop.getChildren().add(this.createTextBox("文本"));
		
		subPaneTop.getChildren().add(this.shapeAttrSelector);
		//subPaneMid.getChildren().add(this.fontSizeSelector);
		//this.getChildren().add(this.createTextBox("文本"));
		this.getChildren().add(subPaneTop);
		
		//添加是前景颜色标签
		
		//添加背景颜色标签
		
	}
	
	public void setCanvas(MyCanvas canvas) {
		this.canvas = canvas;
	}
	
	
	public void createTools() {
		//this.brush = this.createBox(ToolsPane.brushIconPath, ToolsPane.brushLabels, "画笔", 3);
		this.shape = this.createBox(ToolsPane.shapeIconPath, ToolsPane.shapeLabels, "形状", 3);
		this.lineType = this.createBox(ToolsPane.lineTypeIconPath, ToolsPane.lineTypeLabels, "线条粗细", 1);
		this.color = new ColorPicker(Color.BLACK);
		this.color.prefWidthProperty().bind(this.widthProperty().divide(6));
		this.color.setTooltip(new Tooltip("颜色"));
		this.color.valueProperty().addListener(e->{
			this.selectedColor = this.color.getValue();
			System.out.println("chosed color "+this.selectedColor);
			if ( this.foreBackColorSelector != null) {
				this.foreBackColorSelector.setStyle("-fx-background-color:#"+this.selectedColor.toString().substring(2) );
				this.foreBackColorSelector.setTooltip(
						new Tooltip(this.foreBackColorSelector.getText()+":#"+
								this.selectedColor.toString().substring(2)) );
			}else {
				this.foreGroundColorSelector.setStyle("-fx-background-color:#"+this.selectedColor.toString().substring(2) );
				this.foreGroundColorSelector.setTooltip(
						new Tooltip(this.foreGroundColorSelector.getText()+":#"+
								this.selectedColor.toString().substring(2)) );
			}
		});
		
		
		
		this.foreGroundColorSelector = new Button("前景色");
		this.backGroundColorSelector = new Button("背景色");
		this.foreGroundColorSelector.setStyle("-fx-background-color:#000000");
		this.backGroundColorSelector.setStyle("-fx-background-color:#000000");
		this.foreGroundColorSelector.setTooltip(new Tooltip("前景色"+
				this.foreGroundColorSelector.getStyle().substring("-fx-background-color:".length()-1) ) );
		this.backGroundColorSelector.setTooltip(new Tooltip("背景色" + 
				this.backGroundColorSelector.getStyle().substring("-fx-background-color:".length()-1) ) );
		
		this.foreGroundColorSelector.prefWidthProperty().bind(this.widthProperty().divide(15));
		this.backGroundColorSelector.prefWidthProperty().bind(this.widthProperty().divide(15));
		
		this.foreGroundColorSelector.setOnAction(e->{
			this.foreBackColorSelector = this.foreGroundColorSelector;
			this.canvas.changeChosedShapes();
		});

		this.backGroundColorSelector.setOnAction(e->{
			this.foreBackColorSelector = this.backGroundColorSelector;
			this.canvas.changeChosedShapes();
		});
		
		//this.shapeAttrSelector = this.createShapeAttrSelector("形状属性", 3);
		this.shapeAttrSelector = this.createBox(null, new String[] {"边框", "填充"}, "形状属性", 3);
		
		
	}
	
	public Accordion createTextBox(String tip) {
		GridPane grid = new GridPane();
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		this.fontFamilySelector = this.createFontFamilySelector(grid, tp);
		this.fontSizeSelector = this.createFontSizeSelector(grid, tp);
		grid.add(this.fontFamilySelector, 0, 0);
		grid.setMargin(this.fontFamilySelector, new Insets(2,2,2,2));
		grid.add(this.fontSizeSelector, 1, 0);
		grid.setMargin(this.fontSizeSelector, new Insets(2,2,2,2));
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		//设置各个控件的属性
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(6.5));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		return ac;
	}
	
	public ComboBox createFontFamilySelector(Pane pane, TitledPane tp) {
		ComboBox cb = new ComboBox();
		cb.setStyle("-fx-background-color:#e2e2e2;-fx-font-size:10px");
		
		ObservableList<String> fontFamilies = FXCollections.observableList(Font.getFamilies());
		cb.setItems(fontFamilies);
		cb.setTooltip(new Tooltip("字体:"+this.getSelectedFontFamily()));
		cb.setOnAction(e->{
			this.selectedFontFamily = (String) cb.getSelectionModel().getSelectedItem();
			cb.getTooltip().setText("字体:"+this.selectedFontFamily);
			this.canvas.changeChosedShapes();
			tp.setExpanded(false);
		});
		cb.prefWidthProperty().bind(pane.widthProperty().divide(1.5));
		
		cb.setValue(this.selectedFontFamily);
		return cb;
	}
	
	public ComboBox createFontSizeSelector(Pane pane, TitledPane tp) {
		ComboBox cb = new ComboBox();
		cb.setStyle("-fx-background-color:#e2e2e2;-fx-font-size:12px");
		ArrayList<Integer> fontSizeList = new ArrayList<Integer>();
		for (int i = 8; i < 72; i += 4) {
			fontSizeList.add(i);
		}
		ObservableList<Integer> fontSize = FXCollections.observableList(fontSizeList);
		cb.setItems(fontSize);
		cb.setTooltip(new Tooltip("字体大小:"+this.selectedFontSize));
		cb.setOnAction(e->{
			this.selectedFontSize = (Integer) cb.getSelectionModel().getSelectedItem();
			cb.getTooltip().setText("字体大小:"+this.selectedFontSize);
			this.canvas.changeChosedShapes();
			tp.setExpanded(false);
			
		});
		cb.prefWidthProperty().bind(pane.widthProperty().divide(3));
		
		cb.setValue(this.selectedFontSize);
		return cb;
	}
	
	
	
	//设置各个功能的下拉框
	/*实现方法：
	创建标题窗格，将标题窗格加入到Accordion (手风琴控件，也许是因为这个控件可以拉伸吧)中
	创建流程：
	创建放置控件的面板A，将控件加入到该面板中
	创建标题面板B，设置标题面板的内容为A
	创建Accordion控件 C，获得C的面板列表，向其中添加标题面板
	设置各个控件的属性*/
	public Accordion createBox(String[] iconPath, String[] labels, String tip, int col_num) {
		ImageView[] icons = null;
		if ( iconPath != null) {
			icons = this.createIcons(iconPath);
		}
			
		GridPane grid = new GridPane();
		Node[] btns =  this.addNodes2GridPane(grid, icons, labels, col_num);
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		
		//设置各个控件的属性
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(6));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		this.bindEvents(btns, tp, iconPath);
		
		return ac;
	}

	//设置各个工具选项被选中的事件
	public void bindEvents(Node[] nodes, TitledPane tp, String[] iconPath) {
		//ImageView iv = null;
		for(int i = 0; i < nodes.length; i++) {
			if (nodes[i] instanceof Button) {
				Button tmp = (Button)nodes[i];
			//必须重新创建一个imageview对象，否则要是使用按钮所使用的imageview来设置的话，则原来按钮的graphics则失效了
			
				 ImageView iv = new ImageView(this.iconRootPath+iconPath[i]);
				((Button)nodes[i]).setOnAction(evt->{
					tp.setText(tmp.getText());
					//选择后就将相应的面板折叠起来
					tp.setExpanded(false);
					
					tp.setGraphic(iv);
					System.out.println("chosed  " + tmp.getText());
					
					if(tp.getTooltip().getText().equals("画笔")) {
						this.selectedBrush = tmp.getText();
						
					}
					if(tp.getTooltip().getText().equals("形状")) {
						this.selectedShape = tmp.getText();
						
					}
					if(tp.getTooltip().getText().equals("线条粗细")) {
						this.selectedLineType = tmp.getText();
						
					}
					
				});
			}
			if ( nodes[i] instanceof CheckBox) {
				CheckBox tmp = (CheckBox)nodes[i];
				tmp.setOnAction(e->{
					if ( tmp.getText().equals("边框")) {
						this.hasStroke = (this.hasStroke?false:true);
						this.canvas.changeChosedShapes();
					}
					if ( tmp.getText().equals("填充")) {
						this.isFilled = (this.isFilled?false:true);
						this.canvas.changeChosedShapes();
					}
				});
				
			}
			
		}
		
		tp.setOnMouseExited(e->{
			tp.setExpanded(false);
		});
		
		
	}
	
	//根据图片地址创建图标
	public ImageView[] createIcons(String[] iconPath) {
		ImageView[] icons = new ImageView[iconPath.length];
		for( int i = 0; i < icons.length; i++) {
			icons[i] = new ImageView(this.iconRootPath+iconPath[i]);
		}
		
		return icons;
	}
	
	//在网格面板中添加图标
	@SuppressWarnings("static-access")
	public Node[] addNodes2GridPane(GridPane grid, Node[] nodes, String[] labels, int col_num) {
		Node[] btns = null;
		if (nodes != null && nodes[0] instanceof ImageView)
			btns = new Button[nodes.length];
		if (nodes == null || nodes[0] instanceof CheckBox)
			btns = new CheckBox[labels.length];
		
		for(int i = 0; i < labels.length; i++) {
			if ( btns instanceof CheckBox[]) {
				CheckBox tmp = new CheckBox(labels[i]);
				tmp.setStyle("-fx-background-color:transparent;-fx-font-size:10px");
				tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
				tmp.prefHeightProperty().bind(grid.heightProperty().divide(labels.length/col_num+0.4));
				tmp.setTooltip(new Tooltip(labels[i]));
				btns[i] = tmp;
				
			}
			else if( nodes[i] instanceof ImageView ) {
				Button tmp = new Button(labels[i], nodes[i]);
				ButtonStyle.setButtonStyle(tmp);
				tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
				tmp.prefHeightProperty().bind(grid.heightProperty().divide(nodes.length/col_num+0.4));
				tmp.setTooltip(new Tooltip(labels[i]));
				btns[i] = tmp;
			}
			

			grid.add(btns[i], i % col_num, i / col_num);
			grid.setMargin(btns[i], new Insets(2,3,2,2));
		}
		
		return btns;
	}
	
	public String getSelectedBrush() {
		return ToolsPane.selectedBrush;
	}
	
	public String getSelectedShape() {
		return ToolsPane.selectedShape;
	}
	
	public int getSelectedLineType() {
		if ( this.hasStroke) {
			//默认线宽为3px
			if (ToolsPane.selectedLineType.equals(""))
				return 3;
			return Integer.valueOf(ToolsPane.selectedLineType.substring(0, ToolsPane.selectedLineType.indexOf("px")));
		}
		else
			return 0;

		
	}

	public Color getSelectedForeColor() {
		if ( this.color.getValue() == null)
			return Color.BLACK;
		return Color.valueOf("0x"+this.foreGroundColorSelector.getTooltip().getText().substring(5));
	}
	
	public Color getSelectedBackColor() {
		//System.out.println("0x"+this.backGroundColorSelector.getTooltip().getText().substring(5));
		if ( !this.isFilled )
			return null;
		return Color.valueOf("0x"+this.backGroundColorSelector.getTooltip().getText().substring(5));
		
	}
	
	public String getSelectedFontFamily() {
		return this.selectedFontFamily;
	}
	
	public Integer lineTypeProperty() {
		return this.getSelectedLineType();
	}

	public int getSelectedFontSize() {
		return this.selectedFontSize;
	}
	
	
}