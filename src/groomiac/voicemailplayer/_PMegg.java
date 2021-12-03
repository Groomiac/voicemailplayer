package groomiac.voicemailplayer;

public class _PMegg{
	private static final String name = "egg-crusher";
	
	public static final String url(){
		switch (Base.markt) {
		case Google_Play:
			return "market://details?id=groomiac." + name.replace("-", "");
		case SlideME:
			return "http://slideme.org/application/" + name;
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
}
