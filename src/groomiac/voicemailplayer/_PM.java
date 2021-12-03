package groomiac.voicemailplayer;

public enum _PM {
	SlideME,
	Google_Play,
	AndroidPIT,
	Amazon,
	GetJar,
	Samsung_Apps,
	
	;
	
	private static final String name = "voice-mail-player";
	
	public String url(){
		switch (this) {
		case Google_Play:
			return "market://details?id=groomiac." + name.replace("-", "");
		case SlideME:
			return "sam://details?id=groomiac" +  name.replace("-", "");
		case AndroidPIT:
			return "appcenter://package/groomiac." + name.replace("-", "");
		case Amazon:
			return "http://www.amazon.com/gp/mas/dl/android?p=groomiac." + name.replace("-", "");
		case GetJar:
			return "http://www.getjar.com/" + name.replace("-", "");
		case Samsung_Apps:
			return "samsungapps://ProductDetail/groomiac." + name.replace("-", "");
		}
		return "market://details?id=groomiac." + name.replace("-", "");
	}
	
	public String weburl(){
		switch (this) {
		case Google_Play:
			return "https://play.google.com/store/apps/details?id=groomiac." + name.replace("-", "");
		case SlideME:
			return "http://slideme.org/application/" + name;
		case AndroidPIT:
			return "http://androidpit.de/de/android/market/apps/app/groomiac." + name.replace("-", "");
		case Amazon:
			return "http://www.amazon.com/gp/mas/dl/android?p=groomiac." + name.replace("-", "");
		case GetJar:
			return "http://www.getjar.com/" + name.replace("-", "");
		/*
		case Samsung_Apps:
			return "http://www.samsungapps.com/earth/topApps/topAppsDetail.as?productId=???";
		*/
		}
		return "https://play.google.com/store/apps/details?id=groomiac." + name.replace("-", "");
	}
	
	public String toString() {
		String tmp = name().replace("_", " ");
		return tmp;
	};
}
