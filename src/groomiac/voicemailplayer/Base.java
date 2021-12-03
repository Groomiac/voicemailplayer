package groomiac.voicemailplayer;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Base extends Activity {
	final static _PM markt = _PM.Google_Play;      //!!!

	//-------------------------------------
	static String _m(){
		return markt.toString();
	}
	
	final static String app = "Voice Mail Player";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PreferenceManager.setDefaultValues(this, R.layout.act_prefs, true);
		
		if(!getPBoolean(_P.rateme)){
			int rctr = getPInt(_P.ratectr);
			rctr++;
			setP(_P.ratectr, rctr);
			GeneralUI.rate(this);
		}
		
	}
	
	//PREFS
	
	final boolean getPBoolean(String p){
		return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(p, false);
	}

	final boolean getPBoolean(_P p){
		return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(p.name(), false);
	}

	final String getPString(_P p){
		return PreferenceManager.getDefaultSharedPreferences(this).getString(p.name(), null);
	}
	
	final int getPInt(_P p){
		return PreferenceManager.getDefaultSharedPreferences(this).getInt(p.name(), -1);
	}
	
	final long getPLong(_P p){
		return PreferenceManager.getDefaultSharedPreferences(this).getLong(p.name(), -1);
	}
	
	final void setP(_P p, String val){
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
		edit.putString(p.name(), val);
		edit.commit();
	}
	
	final void setP(_P p, int val){
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
		edit.putInt(p.name(), val);
		edit.commit();
	}
	
	final void setP(_P p, long val){
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
		edit.putLong(p.name(), val);
		edit.commit();
	}
	
	final void setP(_P p, boolean val){
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
		edit.putBoolean(p.name(), val);
		edit.commit();
	}
	
	final void setP(String p, boolean val){
		Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
		edit.putBoolean(p, val);
		edit.commit();
	}
	
	//---
	
	final static boolean getPBoolean(Context c, _P p){
		return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(p.name(), false);
	}
	
	final static int getPInt(Context c, _P p){
		return PreferenceManager.getDefaultSharedPreferences(c).getInt(p.name(), -1);
	}
	
	final int dp(int dp){
		return (int)(dp * getResources().getDisplayMetrics().density);
	}

	static final int dp(int dp, Context con){
		return (int)(dp * con.getResources().getDisplayMetrics().density);
	}

    //UI HELPERS
    
	final static RelativeLayout.LayoutParams getRLP(View v){
		return (RelativeLayout.LayoutParams) v.getLayoutParams();
	}
	
	final static LinearLayout.LayoutParams getLLP(View v){
		return (LinearLayout.LayoutParams) v.getLayoutParams();
	}
	
	final static void add(ViewGroup v, View... vs){
		for(View vx: vs){
			if(vx != null) v.addView(vx);
		}
	}
	
	final static void addListener(View.OnClickListener von, View... v){
		for(View vx: v){
			if(vx != null) vx.setOnClickListener(von);
		}
	}

}
