package groomiac.voicemailplayer;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GeneralUI extends DialogPreference{
	
	public GeneralUI(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GeneralUI(Context context, AttributeSet attrs, int i) {
		super(context, attrs, i);
	}
	
	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		String tmp = null;
		try {
			if(getKey() != null){
				tmp = getKey().toLowerCase();
				
				if(tmp.equals(_P.rateintro.name())){
					rating(getContext(), builder);
				}
				else if(tmp.equals(_P.showinit.name())){
					quickstart(getContext(), builder);
				}
				/*
				else if(tmp.equals(_P.reset.name())){
					reset(getContext(), builder);
				}
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static AlertDialog quickstart(Context act){
        Builder bui = new AlertDialog.Builder(act);
        quickstart(act, bui);
        return bui.create();
	}

	
	public static void quickstart(Context act, Builder bui){
		ScrollView sv = new ScrollView(act);
		sv.setBackgroundColor(Color.WHITE);
		
        LinearLayout dia = new LinearLayout(act);
        dia.setPadding(10, 10, 10, 10);
        dia.setOrientation(LinearLayout.VERTICAL);
        
        sv.addView(dia);
        
        TextView tvd = new TextView(act);
        tvd.setTextColor(Color.BLACK);
        dia.addView(tvd);
        tvd.setText(
        		
        		"Voice Mail Player automatically plays audio and video files (audio part only) via your Android phone's ear-piece - like as you would take a phone call.\n\n" +

        		"Voice Mail Player does not(!) include a file picker. It can be started like Android's default media player from any application that deals with media files. It's in the shared apps list. It can be used via gallery, e-mail clients, file pickers, download folder etc.\n\n" +

        		"This app will not work on devices without ear-pieces (obviously). If you think, your device should be supported but is not, please drop us an e-mail!\n\n" +

        		"Take a look at the settings to configure Voice Mail Player's behavior for your needs. Of course, you can use your phone's volume control keys as usual.\n\n" +
        		
        		"In the settings you can also disable this message.\n\n"
        		
        		);
        tvd.setPadding(0, 10, 0, 10);

        
        
        bui.setView(sv);
        bui.setTitle("Quick Start");
        //bui.setMessage("AF");
        bui.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
        
        bui.setNegativeButton(null, null);

	}
	
	public static AlertDialog noModein(Context act){
        Builder bui = new AlertDialog.Builder(act);
        noModein(act, bui);
        return bui.create();
	}

	
	public static void noModein(Context act, Builder bui){
		ScrollView sv = new ScrollView(act);
		sv.setBackgroundColor(Color.WHITE);
		
        LinearLayout dia = new LinearLayout(act);
        dia.setPadding(10, 10, 10, 10);
        dia.setOrientation(LinearLayout.VERTICAL);
        
        sv.addView(dia);
        
        TextView tvd = new TextView(act);
        tvd.setTextColor(Color.BLACK);
        dia.addView(tvd);
        tvd.setText("Your phone does not allow to use its ear piece for media playback. Sorry!");
        tvd.setPadding(0, 10, 0, 10);

        
        
        bui.setView(sv);
        bui.setTitle("Error using ear piece");
        //bui.setMessage("AF");
        bui.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
        
        bui.setNegativeButton(null, null);

	}
	
	
	/*
	public static AlertDialog reset(Context act){
        Builder bui = new AlertDialog.Builder(act);
        reset(act, bui);
        return bui.create();
	}

	public static void reset(final Context act, Builder bui){
		ScrollView sv = new ScrollView(act);
		sv.setBackgroundColor(Color.WHITE);
		
        LinearLayout dia = new LinearLayout(act);
        dia.setPadding(10, 10, 10, 10);
        dia.setOrientation(LinearLayout.VERTICAL);
        
        sv.addView(dia);
        
        TextView tvd = new TextView(act);
        tvd.setTextColor(Color.BLACK);
        dia.addView(tvd);
        tvd.setText(
        		"Your audio settings are back to default!"
        		);
        tvd.setPadding(0, 10, 0, 10);

        bui.setView(sv);
        bui.setTitle("Reset Audio Settings");
        
        String tmp2 = "OK";
        
        	bui.setPositiveButton(tmp2, new OnClickListener() {
    			
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    			}
    		});
        	
        	bui.setNegativeButton(null, null);
        	bui.setNeutralButton(null, null);
	}
	*/

	public static AlertDialog rating(Context act){
        Builder bui = new AlertDialog.Builder(act);
        rating(act, bui);
        return bui.create();
	}

	public static void rating(final Context act, Builder bui){
		ScrollView sv = new ScrollView(act);
		sv.setBackgroundColor(Color.WHITE);
		
        LinearLayout dia = new LinearLayout(act);
        dia.setPadding(10, 10, 10, 10);
        dia.setOrientation(LinearLayout.VERTICAL);
        
        sv.addView(dia);
        
        TextView tvd = new TextView(act);
        tvd.setTextColor(Color.BLACK);
        dia.addView(tvd);
        tvd.setText(
        		"Thank you for using " + Base.app + "! We would like you to rate " + Base.app + " on " + Base._m() + "." +
        		"\n\nMore ratings give us the motivation to maintain this free app. Thank you in advance!"
        		);
        tvd.setPadding(0, 10, 0, 10);

        bui.setView(sv);
        bui.setTitle("Rate");
        //bui.setMessage("AF");
        
        String tmp2 = "OK";
        
        	bui.setPositiveButton(tmp2, new OnClickListener() {
    			
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    				
					try {
						Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(Base.markt.url()));
						act.startActivity(market);
					} catch (Exception e) {
						try {
							Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(Base.markt.weburl()));
							act.startActivity(market);
						} catch (Exception e2) {}
					}
    				
    			}
    		});
        
        	String tmp = "Cancel";
        	
        bui.setNegativeButton(tmp, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}

	final static boolean rate(Base act){
		if(!act.getPBoolean(_P.rateme)){
			int rctr = act.getPInt(_P.ratectr);
			
			if(rctr >= 25){
				act.setP(_P.ratectr, 0);
				GeneralUI.rating(act).show();
				return true;
			}
			
			return false;
		}
		return false;
	}

}
