package crossword;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class crossword extends Application{
	String currentPlayer = "";
	int score = -1;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		
		StartScene startscene = new StartScene(stage);
		GameScene gamescene = new GameScene(stage);
		ResultScene resultscene = new ResultScene(stage);
		RankScene rankscene = new RankScene(stage);
		ArrayList<Scene> allscene = new ArrayList<Scene>();
		allscene.add(startscene);//0
		allscene.add(gamescene);//1
		allscene.add(resultscene);//2
		allscene.add(rankscene);//3
		
		
		
		
		initStage(stage);
		linkAllScene(allscene, stage);
		
		stage.setScene(startscene);
		//stage.setScene(gamescene);
		//stage.setScene(resultscene);
		//stage.setScene(rankscene);
		stage.setOnShowing(showingevt->{
			System.out.println("read from file 4 ranktable");
			initRankTable(rankscene);
		});
		
		
		
		
		stage.show();
		
		
		
	}
	
	
	//设置舞台
	public void initStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/git/basic%20java/resources/Game_Controller_32px.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("crossword");
		
		
		stage.setMaxHeight(450);
		stage.setMaxWidth(320);
		stage.setMinHeight(380);
		stage.setMinWidth(290);		
		stage.initStyle(StageStyle.TRANSPARENT);
		
		
	}
	
	//显示添加玩家的对话框
	public void addPlayer() {
		TextInputDialog addPlayer = new TextInputDialog();
		addPlayer.setTitle("创建玩家");
		addPlayer.setHeaderText("");
		addPlayer.setContentText("请输入玩家姓名:");
		
		addPlayer.showAndWait();
		currentPlayer = addPlayer.getResult();
		//System.out.println( addPlayer.getResult());
	}
	
	public void initRankTable(Scene scene) {
		((RankScene)scene).readRankRecord();
	}
	
	public void saveRankTable(Scene scene) {
		((RankScene)scene).writeRankRecord();
	}
	
	public void linkAllScene(ArrayList<Scene> allscene, Stage stage) {
		//设置排行榜的返回按钮到游戏开始界面的连接
		bindRankSceneBackBtn(allscene.get(3), allscene.get(0), stage);
		//设置开始界面的开始游戏按钮到游戏界面的连接
		bindStartSceneStartBtn(allscene.get(0), allscene.get(1), stage);
		//设置开始界面的排行榜按钮到排行榜的连接
		bindStartSceneRankBtn(allscene.get(0), allscene.get(3), stage);
		//为主界面的退出按钮设置保存排行榜的事件
		bindStartSceneExitBtn(allscene.get(0), allscene.get(3), stage);
		//设置游戏界面重新开始按钮到开始界面的连接
		bindGameSceneReplayBtn(allscene.get(1), allscene.get(0), stage);
		//设置当答错题数大于等于3时则认为游戏结束，转到游戏结果的界面
		bindGameSceneNextBtn(allscene.get(1), allscene.get(2), stage);
		//设置游戏结果界面按下主界面按钮后到主界面
		bindResultSceneBackBtn(allscene.get(2), allscene.get(0), allscene.get(3), stage);
	}
	
	//为排行榜的返回按钮绑定事件
	public void bindRankSceneBackBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((RankScene)srcscene).back.setOnAction(backevt->{
			stage.setScene(dstscene);
		});
	}
	
	//为开始场景的开始游戏按钮绑定事件
	public void bindStartSceneStartBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getPlayBtn().setOnAction(playevt->{
			if(currentPlayer.equals("")) {
				addPlayer();
			}
			((GameScene)dstscene).initFlags();
			((GameScene)dstscene).setProblem();
			
			stage.setScene(dstscene);
			
		});
	}
	
	//为开始场景的排行榜按钮绑定转换场景的事件
	public void bindStartSceneRankBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getRankBtn().setOnAction(playevt->{
			((RankScene)dstscene).sortByScore();
			stage.setScene(dstscene);
		});
	}
	
	//为主界面的退出按钮绑定事件
	public void bindStartSceneExitBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene)srcscene).getExitBtn().setOnAction(exitevt->{
			((RankScene)dstscene).sortByScore();
			((RankScene)dstscene).writeRankRecord();
			stage.close();
		});
	}
	
	//为游戏界面的重新开始按钮绑定事件
	public void bindGameSceneReplayBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((GameScene)srcscene).getReplayBtn().setOnAction(replayevt->{
			((GameScene)srcscene).resetTfs();
			stage.setScene(dstscene);
		});
	}
	
	//为游戏界面的下一题按钮绑定事件
	public void bindGameSceneNextBtn(Scene srcscene, Scene dstscene, Stage stage) {
		GameScene newscene = (GameScene)srcscene;
		newscene.getNextBtn().setOnAction(judgeevt->{
			if(!newscene.hasEdit) {
				return;
			}
			if(newscene.wrongNum >= 3) {
				((ResultScene)dstscene).addStars(newscene.rightNum);
				((ResultScene)dstscene).setRightNum(newscene.rightNum);
				((ResultScene)dstscene).setWrongNum(newscene.wrongNum);
				score = newscene.rightNum;
				//System.out.println(" after in bindnextbtn, score:"+score);
				stage.setScene(dstscene);
			}else {
				((GameScene)srcscene).setProblem();
			}
			((GameScene)srcscene).resetTfs();
			newscene.hasEdit = false;
		});
	}
	
	//为游戏结果界面绑定返回主界面按钮的事件
	public void bindResultSceneBackBtn(Scene srcscene, Scene dstscene, Scene rankscene, Stage stage) {
		((ResultScene)srcscene).getBackBtn().setOnAction(backevt->{
			ObservableList<RankRecord> data = ((RankScene)rankscene).getData();
			//System.out.println("in bindbackbtn,score:"+score);
			RankRecord record = new RankRecord( data.size()+1, currentPlayer, score );
			data.add( record );
			stage.setScene(dstscene);	
		});
	}
	
	
	

	public static void main(String[] args) {
		Application.launch();
	}

	
}