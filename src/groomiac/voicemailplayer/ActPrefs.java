package groomiac.voicemailplayer;


import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ActPrefs extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.act_prefs);

        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.LTGRAY);
        ll.setPadding(Base.dp(10, this), 4, Base.dp(10, this), 0);
        getListView().addFooterView(ll);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll3 = new LinearLayout(this);
        ll.addView(ll3);
        ll3.setGravity(Gravity.CENTER);
        ll3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(_PMegg.url()));
					startActivity(market);
				} catch (Exception e) {}
			}
		});
        
        ImageView iv3 = new ImageView(this);
        ll3.addView(iv3);
        iv3.setImageResource(R.drawable.eggcrusher_banner_640);
        iv3.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        iv3.setScaleType(ScaleType.FIT_CENTER);

        LinearLayout ll2 = new LinearLayout(this);
        ll2.setPadding(0, 4, 0, 0);
        ll.addView(ll2);
        ll2.setGravity(Gravity.CENTER);
        ll2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(_PMgeo.url()));
					startActivity(market);
				} catch (Exception e) {}
			}
		});
        
        ImageView iv = new ImageView(this);
        ll2.addView(iv);
        iv.setImageResource(R.drawable.google640realwhite);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        iv.setScaleType(ScaleType.FIT_CENTER);

	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		_P p = null;
		
		try {
			p = _P.valueOf(key);
		}
		catch (Exception e) {}
		
		if(p == null) return;
		
		switch (p) {
		case rateme:
			if(Base.getPBoolean(this, p)){
				GeneralUI.rating(this).show();
			}
			break;
		case videomime:
			if(Base.getPBoolean(this, p)){
				getPackageManager().setComponentEnabledSetting(new ComponentName(getApplicationContext(), "groomiac.voicemailplayer.ActMainVid"),
						PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
			}
			else{
				getPackageManager().setComponentEnabledSetting(new ComponentName(getApplicationContext(), "groomiac.voicemailplayer.ActMainVid"),
						PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				
			}
			break;
		case compall:
			if(Base.getPBoolean(this, p)){
				getPackageManager().setComponentEnabledSetting(new ComponentName(getApplicationContext(), "groomiac.voicemailplayer.ActMainAll"),
						PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
			}
			else{
				getPackageManager().setComponentEnabledSetting(new ComponentName(getApplicationContext(), "groomiac.voicemailplayer.ActMainAll"),
						PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				
			}
			break;
		case tweak:
			ActMain.switchCompare(Base.getPBoolean(this, p));
		}
		
	}
}
