package org.inchain.wallet.utils;

import org.inchain.wallet.Context;
import org.inchain.wallet.controllers.DailogController;
import org.inchain.wallet.entity.Point;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * 弹出框工具类
 * @author ln
 *
 */
public class DailogUtil {
	
	public final static long DEFAULT_HIDE_TIME = 1000l;

	/**
	 * 提示消息
	 * @param message
	 */
	public static void showTip(String message) {
    	showTip(message, DEFAULT_HIDE_TIME);
	}
	
	/**
	 * 提示消息
	 * @param message
	 */
	public static void showTip(String message, Stage stage) {
		showTip(message, stage, DEFAULT_HIDE_TIME);
	}
	
	/**
	 * 提示消息，延时自动消失
	 * @param message
	 * @param hideTime
	 */
	public static void showTip(String message, long hideTime) {
    	showTip(message, Context.getMainStage(), hideTime);
	}
	
	/**
	 * 提示消息，延时自动消失
	 * @param message
	 * @param hideTime
	 */
	public static void showTip(String message, Stage stage, long hideTime) {
		Point point = getDailogPoint(100, 30);
		showTip(message, stage, hideTime, point.getX(), point.getY());
	}
	
	/**
	 * 在制定位置提示消息
	 * @param message
	 * @param x
	 * @param y
	 */
	public static void showTip(String message, double x, double y) {
		showTip(message, Context.getMainStage(), DEFAULT_HIDE_TIME, x, y);
	}
	
	/**
	 * 自定义的提示消息
	 * @param message
	 * @param hideTime
	 * @param x
	 * @param y
	 */
	public static void showTip(String message, Stage stage, long hideTime, double x, double y) {
		Tooltip tip = new Tooltip(message);
		tip.setFont(Font.font("宋体", 14));
		tip.show(stage, x, y);
		
		hideDailog(tip, hideTime);
	}

	/**
	 * 隐藏提示消息框
	 * @param tip
	 * @param hideTime
	 */
	public static void hideDailog(final Tooltip tip, final long hideTime) {
		new Thread(){
    		public void run() {
    			try {
					Thread.sleep(hideTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    			close();
    		}
			private void close() {
				//延时自动消失
    			Platform.runLater(new Runnable() {
    			    @Override
    			    public void run() {
						tip.hide();
    			    }
    			});
			};
    	}.start();
	}
	
	/**
	 * 获取弹出层的剧中位置
	 * @param dailogWidth
	 * @param dailogHeight
	 * @return Point
	 */
	public static Point getDailogPoint(double dailogWidth, double dailogHeight) {
		Stage mainStage = Context.getMainStage();
		double x = mainStage.getX() + (mainStage.getWidth() - dailogWidth) / 2;
    	double y = mainStage.getY() + (mainStage.getHeight() - dailogHeight) / 2;
    	return new Point(x, y);
	}
	
	/**
	 * 显示弹出层
	 * @param ui
	 * @param title
	 */
	public static void showDailog(FXMLLoader loader, String title) {
		showDailog(loader, title, null);
	}
	
	/**
	 * 显示弹出层
	 * @param loader
	 * @param title
	 * @param callback 关闭时的回调
	 */
	public static void showDailog(FXMLLoader loader, String title, final Runnable callback) {
		try {
			Pane ui = loader.load();
			Stage window = new Stage(StageStyle.UTILITY);
			window.setTitle(title);
			window.initModality(Modality.APPLICATION_MODAL);
			
			Point point = getDailogPoint(ui.getPrefWidth(), ui.getPrefHeight());
			window.setX(point.getX());
			window.setY(point.getY());
	
			Scene scene = new Scene(ui);
			window.setScene(scene);
			
			if(ui.getId() != null) {
				Context.addStage(ui.getId(), window);
			}
			window.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if(ui.getId() != null) {
						Context.deleteStage(ui.getId());
					}
					if(callback != null) {
						callback.run();
					}
				}
			});
			DailogController controller = loader.getController();
			controller.setCallback(callback);
			controller.setPageId(ui.getId());
			
			window.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
