package groomiac.voicemailplayer;

public class _PMgeo {
	public final static String url(){
		switch (Base.markt) {
		case Google_Play:
			return "market://details?id=groomiac.geocascade";
		case SlideME:
			return "http://slideme.org/application/geo-cascade";
		case AndroidPIT:
			return "appcenter://package/groomiac.geocascade";
		case Amazon:
			return "http://www.amazon.com/gp/mas/dl/android?p=groomiac.geocascade";
		case GetJar:
			return "http://www.getjar.com/geocascade";
		case Samsung_Apps:
			return "samsungapps://ProductDetail/groomiac.geocascade";
		}
		return "market://details?id=groomiac.geocascade";
	}
}
