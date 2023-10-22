package cn.edu.whu;
import org.apache.ibatis.logging.Log;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
public class DBLogger implements Log{
	private SimpleLogger logger;
	public DBLogger(String clazz) throws Exception{
		logger = new SimpleLogger("app.log");
	}
	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public void error(String s, Throwable e) {
		logger.severe(s);
		logger.log(e.toString());
		//e.printStackTrace(System.err);
	}

	@Override
	public void error(String s) {
		logger.severe(s);
	}

	@Override
	public void debug(String s) {
		logger.log(s);
	}

	@Override
	public void trace(String s) {
		logger.log(s);
	}

	@Override
	public void warn(String s) {
		logger.warning(s);
	}
}
