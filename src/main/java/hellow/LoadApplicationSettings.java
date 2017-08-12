package hellow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class LoadApplicationSettings {
	private static  Map<String,String> ConfigSetting = new HashMap<String,String>();
	
	public LoadApplicationSettings(){
		setConfigSetting(GetConfigSettings());
	}
	static Logger log = Logger.getLogger(ApplicationStart.class.getName());
	public  Map<String,String> GetConfigSettings(){
		
		ObjectMapper mapper = new ObjectMapper();
		LogDetails.LogDetailSystem("Working Directory = " +
	              System.getProperty("user.dir"));
		
		Map<String,String> lConfigSetting = new HashMap<String,String>();
		boolean ConfigLoaded = true;
		File configFile = new File("config.properties");	
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			
			Enumeration<?> PropsNames =  props.propertyNames();	
			log.info("Log level set to : "+props.getProperty(SystemConstants.cEnableLogLevel));
			log.setLevel(SystemConstants.getLogLevel(props.getProperty(SystemConstants.cEnableLogLevel)));
			//(mapper.writeValueAsString());
			
			while(PropsNames.hasMoreElements()){
				String key = (String) PropsNames.nextElement();
				String value = props.getProperty(key);
				if(key.equalsIgnoreCase(SystemConstants.cPassword) || key.equalsIgnoreCase(SystemConstants.cTCPassword)){
					log.debug(key+" = **********");
				}else{
				log.trace(key+" = "+value);
				}
				lConfigSetting.put(key, value);
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
		lConfigSetting.put(SystemConstants.cConfigLoaded, ConfigLoaded?"Y":"N");
		setConfigSetting(lConfigSetting);
		return lConfigSetting;
				
				
	}

	public static final Map<String,String> getConfigSetting() {
		return ConfigSetting;
	}

	public static void setConfigSetting(Map<String,String> configSetting) {
		ConfigSetting = configSetting;
	}
}
