package ch.bbc.fit4ipa.bbcbank.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogUtil {
	
	private static LogUtil instance;
	
	private Logger logger;
	
	private LogUtil() {
		
		logger = Logger.getLogger("logger");
		FileHandler fh;
		
		String path = System.getProperty("user.home");
		System.out.println(path);
		File directory = new File(path + File.separator + "bbcbankLog");
		if(!directory.exists()) {
            directory.mkdirs();
		}
		
		try {

	        // Configure File Handler
	        fh = new FileHandler(path + File.separator + "bbcbankLog" + File.separator + "bbcbankLog.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter); 

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
		
	}
	
	public static LogUtil getInstance() {
		if (instance == null) {
			instance = new LogUtil();
		}
		return instance;
	}
	
	public void log(String message, Level level) {
		logger.log(level, message);
	}
	
}
