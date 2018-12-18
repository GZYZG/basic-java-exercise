package MyPaint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private static String iconRootPath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/";
	private static String[] brushIconPath = new String[] {iconRootPath+"pencil_16px.png", iconRootPath+"brush_16px.png"};
	private static String[] brushLabels = new String[] {"铅笔", "刷子"};
	private static String[] shapeIconPath = 
			new String[] {iconRootPath + "rectangle_16px.png", iconRootPath + "square_16px.png",
					iconRootPath + "circle_16px.png", iconRootPath + "triangle_16px.png", 
					iconRootPath + "text_16px.png"};
	private static String[] shapeLabels = new String[] {"长方形", "正方形", "圆形", "三角形", "文本"};
	private static String[] lineTypeLabels = new String[] {"1px", "3px", "5px", "8px", "10px", "13px", "16px"};
	private static String[] lineTypeIconPath = new String[] {iconRootPath + "line_1px.png", iconRootPath + "line_3px.png",
					iconRootPath + "line_5px.png", iconRootPath + "line_8px.png",
					iconRootPath + "line_10px.png", iconRootPath + "line_13px.png", iconRootPath + "line_16px.png"};
	
	private static String selectedBrush = "";
	private static String selectedLineType = "";
	private static String selectedShape = "长方形";
	private static Color selectedColor = null;
	private static String selectedFontFamily = "Microsoft YaHei";
	private static int selectedFontSize = 15;
	
	private Accordion brush;
	private Accordion shape;
	private Accordion lineType;
	private ColorPicker color;
	private ComboBox fontFamilySelector;
	private ComboBox fontSizeSelector;
	private boolean showColor = false;
	
	public ToolsPane(Stage stage) {
		super(5);
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
		subPaneTop.getChildren().add(this.color);
		subPaneTop.getChildren().add(this.createTextBox("文本"));
		//subPaneMid.getChildren().add(this.fontFamilySelector);
		//subPaneMid.getChildren().add(this.fontSizeSelector);
		//this.getChildren().add(this.createTextBox("文本"));
		this.getChildren().add(subPaneTop);
		/*
		//添加画笔选择框
		this.getChildren().add(this.brush);
		//添加形状选择框
		this.getChildren().add(this.shape);
		//添加线条粗细选择框
		this.getChildren().add(this.lineType);
		//添加颜色选择器
		this.getChildren().add(this.color);
		//添加字体选择器
		this.getChildren().add(this.fontFamilySelector);
		//添加文字大小选择器
		this.getChildren().add(this.fontSizeSelector);*/
		//添加是前景颜色标签
		
		//添加背景颜色标签
		
	}
	
	
	public void createTools() {
		this.brush = this.createBox(ToolsPane.brushIconPath, ToolsPane.brushLabels, "画笔", 3);
		this.shape = this.createBox(ToolsPane.shapeIconPath, ToolsPane.shapeLabels, "形状", 3);
		this.lineType = this.createBox(ToolsPane.lineTypeIconPath, ToolsPane.lineTypeLabels, "线条粗细", 1);
		this.color = new ColorPicker(Color.BLACK);
		this.color.prefWidthProperty().bind(this.widthProperty().divide(6));
		this.color.setTooltip(new Tooltip("颜色"));
		this.color.valueProperty().addListener(e->{
			this.selectedColor = this.color.getValue();
			System.out.println("chosed color "+this.selectedColor);
		});
		
		this.fontFamilySelector = this.createFontFamilySelector();
		this.fontSizeSelector = this.createFontSizeSelector();
	}
	
	public ComboBox createFontFamilySelector() {
		ComboBox cb = new ComboBox();
		ObservableList<String> fontFamilies = FXCollections.observableList(Font.getFamilies());
		cb.setItems(fontFamilies);
		cb.setOnAction(e->{
			this.selectedFontFamily = (String) cb.getSelectionModel().getSelectedItem();
			System.out.println("font:"+this.selectedFontFamily);
		});
		cb.prefWidthProperty().bind(this.widthProperty().divide(10));
		cb.setTooltip(new Tooltip("字体"));
		cb.setValue(this.selectedFontFamily);
		return cb;
	}
	
	public ComboBox createFontSizeSelector() {
		ComboBox cb = new ComboBox();
		ArrayList<Integer> fontSizeList = new ArrayList<Integer>();
		for (int i = 8; i < 72; i += 4) {
			fontSizeList.add(i);
		}
		ObservableList<Integer> fontSize = FXCollections.observableList(fontSizeList);
		cb.setItems(fontSize);
		cb.setOnAction(e->{
			this.selectedFontSize = (Integer) cb.getSelectionModel().getSelectedItem();
			System.out.println("font:"+this.selectedFontSize);
		});
		cb.prefWidthProperty().bind(this.widthProperty().divide(10));
		cb.setTooltip(new Tooltip("字体大小"));
		cb.setValue(this.selectedFontSize);
		return cb;
	}
	
	public Accordion createTextBox(String tip) {
		GridPane grid = new GridPane();
		grid.add(this.fontFamilySelector, 0, 0);
		grid.add(this.fontSizeSelector, 1, 0);
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		
		//设置各个控件的属性
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(4.5));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		//this.bindEvents(btns, tp, iconPath);
		
		return ac;
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
		ImageView[] icons = this.createIcons(iconPath);
		GridPane grid = new GridPane();
		Button[] btns = this.addNodes2GridPane(grid, icons, labels, col_num);
		
		TitledPane tp = new TitledPane();
		tp.setContent(grid);
		
		Accordion ac = new Accordion();
		ac.getPanes().addAll(tp);
		
		
		//设置各个控件的属性
		tp.setCollapsible(true);
		tp.prefWidthProperty().bind(this.widthProperty().divide(4.5));
		tp.prefHeightProperty().bind(grid.widthProperty());
		tp.setText(tip);
		tp.setTooltip(new Tooltip(tip));
		
		this.bindEvents(btns, tp, iconPath);
		
		return ac;
	}
	
	
	//设置各个工具选项被选中的事件
	public void bindEvents(Node[] nodes, TitledPane tp, String[] iconPath) {
		for(int i = 0; i < nodes.length; i++) {
			Button tmp = (Button)nodes[i];
			
			//必须重新创建一个imageview对象，否则要是使用按钮所使用的imageview来设置的话，则原来按钮的graphics则失效了
			ImageView iv = new ImageView(iconPath[i]);
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
		
		tp.setOnMouseExited(e->{
			tp.setExpanded(false);
		});
		
		
	}
	
	//根据图片地址创建图标
	public ImageView[] createIcons(String[] iconPath) {
		ImageView[] icons = new ImageView[iconPath.length];
		for( int i = 0; i < icons.length; i++) {
			icons[i] = new ImageView(iconPath[i]);
		}
		
		return icons;
	}
	
	//在网格面板中添加图标
	@SuppressWarnings("static-access")
	public Button[] addNodes2GridPane(GridPane grid, Node[] nodes, String[] labels, int col_num) {
		//System.out.print(""+nodes.length);
		Button[] btns = new Button[nodes.length];
		Button tmp;
		for(int i = 0; i < nodes.length; i++) {
			
			if( !(nodes[i] instanceof Button)) {
				tmp = new Button(labels[i], nodes[i]);
				
			}else {
				tmp = (Button)nodes[i];
				tmp.setText(labels[i]);
			}
			btns[i] = tmp;
			tmp.prefWidthProperty().bind(grid.widthProperty().divide(col_num+0.2));
			tmp.prefHeightProperty().bind(grid.heightProperty().divide(nodes.length/col_num+0.4));
			ButtonStyle.setButtonStyle(tmp);
			tmp.setTooltip(new Tooltip(labels[i]));
			grid.add(tmp, i % col_num, i / col_num);
			grid.setMargin(tmp, new Insets(2,3,2,2));
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
		if (ToolsPane.selectedLineType.equals("")) {
			//默认线宽为3px
			return 3;
		}
		return Integer.valueOf(ToolsPane.selectedLineType.substring(0, ToolsPane.selectedLineType.indexOf("px")));
	}

	public Color getSelectedColor() {
		return this.color.getValue();
	}
	
	public String getSelectedFontFamily() {
		return this.selectedFontFamily;
	}
	
	public int getSelectedFontSize() {
		return this.selectedFontSize;
	}
	
	@SuppressWarnings("deprecation")
	public Integer lineTypeProperty() {
		return new Integer(this.getSelectedLineType());
	}
}