package util;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		AbandonedConnectionCleanupThread.checkedShutdown();

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 其他初始化程式碼
	}
}
