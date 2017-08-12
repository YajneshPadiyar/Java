package hellow;

public final class LogDetails {

	
	//Logger log = new Logger.getLogger();
	
	public static void LogDetailGeneral(String LogValue) {

		System.out.println(LogValue);
	}

	public static void LogDetailGeneral(String LogValue, String EnableLog) {

		if (EnableLog.equalsIgnoreCase("Y"))
			System.out.println(LogValue);
	}

	public static void LogDetailSystem(String LogValue) {
		System.out.println(LogValue);
	}
	

	public static void LogDetailApplication(String LogValue) {
		System.out.println(LogValue);
	}
	
	public static void LogDetailApplication(String LogValue, String EnableLog) {
		if (EnableLog.equalsIgnoreCase("Y"))
		System.out.println(LogValue);
	}
	
	//Logger Log = Logger.getLogger("hellow");
	
}
