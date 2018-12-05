package atm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import atm.StartScene;

import atm.LoginScene;
import atm.AccountScene;
import atm.CreateScene;
import atm.Account;


public class ATMUI extends Application{
	
	
	
	ArrayList<Account> accounts;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		accounts = new ArrayList<>();
		StartScene startscene = new StartScene(stage);
		LoginScene loginscene = new LoginScene(accounts);
		AccountScene accountscene = new AccountScene();
		CreateScene createscene = new CreateScene(accounts);
		
		ArrayList<Scene> allscene = new ArrayList<Scene>();
		allscene.add(0, startscene);//0
		allscene.add(1, loginscene);//1
		allscene.add(2, accountscene);//2
		allscene.add(3, createscene);//3
	
		initStage(stage);
		linkAllScene(allscene, stage);
		
		stage.setScene(startscene);
		stage.setOnShowing(showingevt->{
			System.out.println("init atm info...");
			this.readAccountRecord();
		});
		((StartScene)allscene.get(0)).getExitBtn().setOnAction(exitevt->{
			System.out.println("write file...");
			this.writeRankRecord();
			stage.close();
		});
		stage.setOnCloseRequest(closeevt->{
			System.out.println("write file...");
		});

		stage.show();

	}
	
	//设置舞台
	public void initStage(Stage stage) {
		String iconpath = "file:///D:/Study/JAVA/My%20projects/ecilpse/2018.9/basic%20java/resources/ATM.png";
		Image mainicon = new Image(iconpath);
		stage.getIcons().add(mainicon);
		stage.setTitle("crossword");
			
			
		stage.setMaxHeight(450);
		stage.setMaxWidth(320);
		stage.setMinHeight(380);
		stage.setMinWidth(290);		
		//stage.initStyle(StageStyle.TRANSPARENT);
			
			
	}
	
	public void linkAllScene(ArrayList<Scene> allscene, Stage stage) {
		//设置开始界面的登录按钮到登录面板的连接
		bindStartSceneLoginBtn(allscene.get(0), allscene.get(1), stage);
		
		//设置开始面板的开户按钮到开户面板的连接
		bindStartSceneCreateBtn(allscene.get(0), allscene.get(3), stage);
		
		//设置登录面板的取消按钮到开始面板的连接
		bindLoginSceneCancelBtn(allscene.get(1), allscene.get(0), stage) ;
		
		//设置登录面板的进入按钮到账户面板的连接
		bindLoginSceneEnterBtn(allscene.get(1), allscene.get(2), stage) ;
		
		//设置开户面板的取消按钮到开始面板的连接
		bindCreateSceneCancelBtn(allscene.get(3), allscene.get(0), stage) ;
				
		//设置开户面板的进入按钮到账户面板的连接
		bindCreateSceneEnterBtn(allscene.get(3), allscene.get(2), stage) ;
		
		//设置账户面板的退出登录按钮到开始面板的连接
		bindAccountSceneExitBtn(allscene.get(2), allscene.get(0), stage);
	}
	
	public void bindStartSceneLoginBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getLoginBtn().setOnAction(loginevt->{
			stage.setScene(dstscene);
			((LoginScene) dstscene).clearScene();
		});
		
	}
	
	public void bindStartSceneCreateBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((StartScene) srcscene).getCreateBtn().setOnAction(createevt->{
			stage.setScene(dstscene);
			((CreateScene)dstscene).clearScene();
		});
		
	}
	
	public void bindLoginSceneCancelBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((LoginScene) srcscene).getCancelBtn().setOnAction(cancelevt->{
			stage.setScene(dstscene);
		});
	}
	
	public void bindLoginSceneEnterBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((LoginScene) srcscene).getEnterBtn().setOnAction(enterevt->{
			Account tmp = ((LoginScene)srcscene).loginATM();
			if(tmp == null) {
				return;
			}
			stage.setScene(dstscene);
			((AccountScene)dstscene).clearScene();
			((AccountScene)dstscene).setAccount(tmp);
			((AccountScene)dstscene).setOwnerInfo();
		});
	}
	
	public void bindCreateSceneEnterBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((CreateScene) srcscene).getEnterBtn().setOnAction(enterevt->{
			Account tmp = ((CreateScene) srcscene).createAccount();
			//若创建用户失败则直接返回，不进行跳转
			if(tmp == null) {
				return;
			}
			this.accounts.add(tmp);
			//System.out.println("add successfully");
			System.out.println(this.accounts.toString());
			stage.setScene(dstscene);
			((AccountScene)dstscene).clearScene();
			((AccountScene)dstscene).setAccount(tmp);
			((AccountScene)dstscene).setOwnerInfo();
			
		});
	}
	
	public void bindCreateSceneCancelBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((CreateScene) srcscene).getCancelBtn().setOnAction(cancelevt->{
			stage.setScene(dstscene);
			
		});
	}
	
	
	public void bindAccountSceneExitBtn(Scene srcscene, Scene dstscene, Stage stage) {
		((AccountScene) srcscene).getExitBtn().setOnAction(cancelevt->{
			stage.setScene(dstscene);
			
		});
	}
	
	//从文件中读取账户信息
	public void readAccountRecord() {
		String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\2018.9\\basic java\\resources\\accountsinfo.dat";
		System.out.println("in readRankRecord!");
		FileInputStream infile;
		try {
			int count = 1;
			infile = new FileInputStream(file_path);
			ObjectInputStream objReader = new ObjectInputStream(infile);
			while(true) {
				try {
					Account record = (Account)objReader.readObject();
					//System.out.println("read:"+record.toString());
					this.accounts.add(record);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("readAccountRecord done");
		}

		
	}
	
	public void writeRankRecord() {
		//System.out.println("data.size:"+data.size());
		String file_path = "D:\\Study\\JAVA\\My projects\\ecilpse\\2018.9\\basic java\\resources\\accountsinfo.dat";
		try {
			FileOutputStream outfile = new FileOutputStream(file_path);
			ObjectOutputStream objWriter = new ObjectOutputStream(outfile);
			
			for(Account account: accounts) {
				//System.out.println("is writing 2 file:"+data.get(i).toString());
				objWriter.writeObject(account);
				//System.out.println("write:"+account.toString());
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Application.launch();
	}
	
}