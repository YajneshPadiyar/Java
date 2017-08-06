package hellow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class LoadApplicationSettings {
	public Map<String,String> GetConfigSettings(){
		
		
		LogDetails.LogDetailSystem("Working Directory = " +
	              System.getProperty("user.dir"));
		
		Map<String,String> ConfigSetting = new HashMap<String,String>();
		String SPHost = null;
		String SPServiceName = null;
		String SPPort = null;
		String SPUserName = null;
		String SPPassword = null;
		String TCHost = null;
		String TCDBName = null;
		String TCPort = null;
		String TCUserName = null;
		String TCPassword = null;
		String EnableLog_1 = null;
		String EnableLog_2 = null;
		String EnableLog_3 = null;
		String EnableLog_4 = null;
		boolean ConfigLoaded = true;
		File configFile = new File("config.properties");	
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			EnableLog_1 = props.getProperty(SystemConstants.cEnableLog_1);
			EnableLog_2 = props.getProperty(SystemConstants.cEnableLog_2);
			EnableLog_3 = props.getProperty(SystemConstants.cEnableLog_3);
			EnableLog_4 = props.getProperty(SystemConstants.cEnableLog_4);
			SPHost = props.getProperty(SystemConstants.cHost);
			SPServiceName = props.getProperty(SystemConstants.cServiceName);
			SPPort = props.getProperty(SystemConstants.cPort);
			SPUserName = props.getProperty(SystemConstants.cUserName);
			SPPassword = props.getProperty(SystemConstants.cPassword);
			TCHost = props.getProperty(SystemConstants.cTCHost);
			TCDBName = props.getProperty(SystemConstants.cTCDBName);
			TCPort = props.getProperty(SystemConstants.cTCPort);
			TCUserName = props.getProperty(SystemConstants.cTCUserName);
			TCPassword = props.getProperty(SystemConstants.cTCPassword);
			if(EnableLog_4.equalsIgnoreCase("Y")){
				LogDetails.LogDetailGeneral("SP Host name is: " + SPHost);
				LogDetails.LogDetailGeneral("SP ServiceName is: " + SPServiceName);
				LogDetails.LogDetailGeneral("SP Port  is: " + SPPort);
				LogDetails.LogDetailGeneral("SP UserName is: " + SPUserName);
				LogDetails.LogDetailGeneral("SP Password is: " + SPPassword);
				LogDetails.LogDetailGeneral("TC Host name is: " + TCHost);
				LogDetails.LogDetailGeneral("TC DB Name is: " + TCDBName);
				LogDetails.LogDetailGeneral("TC Port is: " + TCPort);
				LogDetails.LogDetailGeneral("TC UserName is: " + TCUserName);
				LogDetails.LogDetailGeneral("TC Password is: " + TCPassword);
				LogDetails.LogDetailGeneral("Loaded Config Details");
			}

			reader.close();
		} catch (FileNotFoundException ex) {
			ConfigLoaded = false;
			// file does not exist
			ex.printStackTrace();
		} catch (IOException ex) {
			// I/O error
			ConfigLoaded = false;
			ex.printStackTrace();
		}
		ConfigSetting.put(SystemConstants.cConfigLoaded, ConfigLoaded?"Y":"N");
		if(ConfigLoaded){			
			ConfigSetting.put(SystemConstants.cEnableLog_1,EnableLog_1);
			ConfigSetting.put(SystemConstants.cEnableLog_2,EnableLog_2);
			ConfigSetting.put(SystemConstants.cEnableLog_3,EnableLog_3);
			ConfigSetting.put(SystemConstants.cEnableLog_4,EnableLog_4);
			
			ConfigSetting.put(SystemConstants.cHost,SPHost);
			ConfigSetting.put(SystemConstants.cServiceName,SPServiceName);
			ConfigSetting.put(SystemConstants.cPort,SPPort);
			ConfigSetting.put(SystemConstants.cUserName,SPUserName);
			ConfigSetting.put(SystemConstants.cPassword,SPPassword);
			
			ConfigSetting.put(SystemConstants.cTCUserName,TCUserName);
			ConfigSetting.put(SystemConstants.cTCPassword,TCPassword);
			ConfigSetting.put(SystemConstants.cTCPort,TCPort);
			ConfigSetting.put(SystemConstants.cTCDBName,TCDBName);
			ConfigSetting.put(SystemConstants.cTCHost,TCHost);			
		}
		
		return ConfigSetting;
				
				
	}
}
