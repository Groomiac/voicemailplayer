package groomiac.voicemailplayer;

import java.io.IOException;
import java.net.URLDecoder;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActMain extends Base {
	MediaPlayer mPlayer;
	ProgressBar pb ;
	TextView tv ;
	LinearLayout rl ;
	RelativeLayout main, black;
	SensorManager mySensorManager;
	Sensor myProximitySensor;
	
	final float defval = -97.93f;
	float maxval = defval;
		

	final static int UNSET = -232457099;
	final static int TWEAKMODE = AudioManager.MODE_INVALID;
	static int BASEMODE = UNSET;
	
	static int COMPAREMODE = BASEMODE;
	static boolean CHECKCOMPARE = true;
	
	static void switchCompare(boolean nocompare){
		CHECKCOMPARE = !nocompare;
		
		if(CHECKCOMPARE){
			COMPAREMODE = BASEMODE;
		}
		else{
			COMPAREMODE = TWEAKMODE;
		}
	}
	
	final static void setMode(int mode){
		BASEMODE = mode;
		COMPAREMODE = BASEMODE;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	if(getPBoolean(_P.keepon)) getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	
    	if(BASEMODE == UNSET){
    		int trymode = AudioManager.MODE_IN_CALL;
        	if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
    			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    			
    			audioManager.setMode(trymode);
    			audioManager.setSpeakerphoneOn(false);
    			
    			if(audioManager.getMode() != trymode){
    				soundnormal();

    				trymode = AudioManager.STREAM_VOICE_CALL;
        			audioManager.setMode(trymode);
        			audioManager.setSpeakerphoneOn(false);
        			
        			if(audioManager.getMode() != trymode){
        				trymode = AudioManager.MODE_IN_CALL;
        			}
    			}
    			soundnormal();
    			
        	}

			setMode(trymode);
    	}
    	switchCompare(getPBoolean(_P.tweak));
    	
        LayoutParams lp;
        
		rl = new LinearLayout(this);
		rl.setOrientation(LinearLayout.VERTICAL);
		
		BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.back));
        bd.setTileModeXY(TileMode.MIRROR, TileMode.REPEAT);
        rl.setBackgroundDrawable(bd);

        pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.text));
        rl.addView(pb);
        
        lp = (LayoutParams) pb.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = dp(35);
        pb.setLayoutParams(lp);
        
        pb.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if(mPlayer == null /*|| !mPlayer.isPlaying()*/) return false;
					
					int max = mPlayer.getDuration();
					float pos = event.getX() / pb.getWidth();
					max = (int)(pos * max);
					
					mPlayer.seekTo(max);
					pb.setProgress(max);
					
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		});
        
        
        View vdummy = new View(this);
        rl.addView(vdummy);
        vdummy.setBackgroundColor(Color.TRANSPARENT);
        lp = (LayoutParams) vdummy.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = dp(25);
        vdummy.setLayoutParams(lp);

        LinearLayout ll = new LinearLayout(this);
        rl.addView(ll);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        
        ImageView play = new ImageView(this);
        ImageView pause = new ImageView(this);
        ImageView stop = new ImageView(this);
        ll.addView(play);
        ll.addView(pause);
        ll.addView(stop);

        int dp = dp(40) + dp(30);
        if (dp > 150) dp = 150;
        
        lp = (LayoutParams) play.getLayoutParams();
        lp.width = dp;
        lp.height = dp;
        play.setLayoutParams(lp);
        
        lp = (LayoutParams) pause.getLayoutParams();
        lp.width = dp;
        lp.height = dp;
        pause.setLayoutParams(lp);
        
        lp = (LayoutParams) stop.getLayoutParams();
        lp.width = dp;
        lp.height = dp;
        stop.setLayoutParams(lp);
        
        play.setImageResource(R.drawable.selector_play);
        pause.setImageResource(R.drawable.selector_pause);
        stop.setImageResource(R.drawable.selector_stop);
        
        int ab = 15;
        pause.setPadding(dp(ab), 0, dp(ab), 0);
        play.setPadding(dp(ab), 0, dp(ab), 0);
        stop.setPadding(dp(ab), 0, dp(ab), 0);
        
        pause.setClickable(true);
        play.setClickable(true);
        stop.setClickable(true);
        
        ll.setBackgroundResource(R.drawable.tbutton);
        
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        
        pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if(mPlayer != null && mPlayer.isPlaying()){
						mPlayer.pause();
						soundnormal(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
				if(mPlayer != null){
					if(mPlayer != null && mPlayer.isPlaying()){
						mPlayer.pause();
						soundnormal(true);
					}
					mPlayer.seekTo(0);
					pb.setProgress(0);
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
				if(mPlayer != null && !mPlayer.isPlaying()){
					if(mPlayer.getCurrentPosition() == mPlayer.getDuration()) mPlayer.seekTo(0);
					if(soundoffchk())
						mPlayer.start();
					else
						GeneralUI.noModein(ActMain.this).show();
				}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        
        
        rl.setGravity(Gravity.CENTER_HORIZONTAL);
        rl.setPadding(dp(10), dp(10), dp(10), dp(10));

		tv = new TextView(this);
		rl.addView(tv);
		
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextColor(Color.BLACK);
		
        ImageButton b = new ImageButton(this);
        b.setImageResource(R.drawable.settings);

        LinearLayout llb = new LinearLayout(this);
        llb.setGravity(Gravity.RIGHT);
        llb.addView(b);

        lp = (LayoutParams) b.getLayoutParams();
        lp.width = dp(50);
        lp.height = dp(50);
        b.setLayoutParams(lp);
        b.setScaleType(ScaleType.CENTER_CROP);
        
		rl.addView(llb);

		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if(mPlayer != null && mPlayer.isPlaying()){
						mPlayer.pause();
						soundnormal();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				startActivity(new Intent(getApplicationContext(), ActPrefs.class));
			}
		});

		main = new RelativeLayout(this);
		black = new RelativeLayout(this);

		RelativeLayout.LayoutParams lpr ;
		main.addView(rl);
		lpr = (RelativeLayout.LayoutParams) rl.getLayoutParams();
		lpr.width = LayoutParams.MATCH_PARENT;
		lpr.height = LayoutParams.MATCH_PARENT;
		rl.setLayoutParams(lpr);

		main.addView(black);
		lpr = (RelativeLayout.LayoutParams) black.getLayoutParams();
		lpr.width = LayoutParams.MATCH_PARENT;
		lpr.height = LayoutParams.MATCH_PARENT;
		black.setLayoutParams(lpr);

		black.setVisibility(View.GONE);
		black.setBackgroundColor(Color.BLACK);

		
		setContentView(main);
		
		onNewIntent(getIntent());
		
		
	}
	
	Runnable runner = null;
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
		
		Uri uri = null;
		
		if(getIntent() != null){
			String action = getIntent().getAction();
			if(action != null){
				if(Intent.ACTION_VIEW.equalsIgnoreCase(action) || Intent.ACTION_SEND.equalsIgnoreCase(action)){
					try {
						uri = (Uri) getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
					} catch (Exception e) {
					}
					
					try {
						if(uri == null){
							uri = (Uri) getIntent().getData();
						}
					} catch (Exception e) {}
				}
			}
		}
		
		if(uri != null){
			if(runner != null) rl.removeCallbacks(runner);
			if(mPlayer != null){
				try {
					try {
						mPlayer.setOnCompletionListener(null);
					} catch (Exception e) {}

					try {
						if(mPlayer.isPlaying()){
							mPlayer.pause(); 
							mPlayer.stop();
						}
					} catch (Exception e) {}

					mPlayer.release();
					pb.setProgress(0);
				} catch (Exception e) {}
			}
			mPlayer = new MediaPlayer();
	        try {
	        	
	        	String tmp = URLDecoder.decode(uri.toString(), "utf-8");
	        	
	        	if(!tmp.toLowerCase().startsWith("http") && !tmp.toLowerCase().startsWith("file")) tmp = "From Gallery/Library";
	        	else tmp = tmp.substring(tmp.lastIndexOf("/")+1);
	        	tv.setText("Playing: " + tmp);
	        	
	            mPlayer.setDataSource(getApplicationContext(), uri);
	            mPlayer.prepare();
	            pb.setMax(mPlayer.getDuration());

				if(soundoffchk())
					mPlayer.start();
				else
					GeneralUI.noModein(this).show();

				mPlayer.setOnCompletionListener(mycomplete);
	            
				if(runner == null){
		            runner = 
		    	            (new Runnable() {
		    	    			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		    	            	
		    					@Override
		    					public void run() {
		    						try {
		    							if(mPlayer !=null && mPlayer.isPlaying()){
		    								if(CHECKCOMPARE && audioManager.getMode() != COMPAREMODE){
		    									try {
		    										mPlayer.pause();
												} catch (Exception e) {}
		    								}
		    								
		    								pb.setProgress(mPlayer.getCurrentPosition());
		    							}
		    						} catch (Exception e) {
		    							pb.setProgress(0);
		    							return;
		    						}
		    						if(mPlayer !=null) rl.postDelayed(this, 200);
		    					}
		    				});
				}
	            
	            rl.postDelayed(runner, 100);

	            if(getPBoolean(_P.prox) && (mySensorManager == null || myProximitySensor == null)){
		    		try {
			    		mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			    		myProximitySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

			    		if (mySensorManager == null || myProximitySensor == null) {
			    			Toast.makeText(getApplicationContext(), "No proximity sensor available", Toast.LENGTH_SHORT).show();
			    		} else {
			    			maxval = myProximitySensor.getMaximumRange() * 0.75f;
			    			mySensorManager.registerListener(proximitySensorEventListener, myProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
			    		}
					} catch (Exception e) {
		    			Toast.makeText(getApplicationContext(), "Error using proximity sensor", Toast.LENGTH_SHORT).show();
					}
	            }
	        } catch (IOException e) {
	        	e.printStackTrace();
				tv.setText("Media not compatible");
	        }
		}
		else{
			if(mPlayer == null){
				tv.setText("No media read");
				if(getPBoolean(_P.showguide)) GeneralUI.quickstart(this).show();
			}
		}
	}
	
	private boolean soundoffchk(){
		try {
			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(COMPAREMODE);
			audioManager.setSpeakerphoneOn(false);
			
			if(CHECKCOMPARE && audioManager.getMode() != COMPAREMODE) return false;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void soundnormal(){
		try {
			AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_NORMAL);
			audioManager.setSpeakerphoneOn(true);
		} catch (Exception e) {
		}
	}
	
	private void soundnormal(boolean wait){
		if(wait) SystemClock.sleep(150);
		soundnormal();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		try {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.layout.menu, menu);
		    return true;
		} catch (Exception e) {}
	    return false;
	}

	/*
	public boolean onPrepareOptionsMenu(Menu menu) {
		try {
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.layout.menu, menu);
		    return true;
		} catch (Exception e) {}
		return false;
	}
	*/
	
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			startActivity(new Intent(getApplicationContext(), ActPrefs.class));
		} catch (Exception e) {}
		return true;
	}
	
	private OnCompletionListener mycomplete = new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			soundnormal();
			pb.setProgress(pb.getMax());
			if(getPBoolean(_P.autoclose)) finish();
		}
	};
	
	private void clearup(){
		try {
			if(mPlayer != null ){
				if(mPlayer.isPlaying()){
					mPlayer.pause(); 
					mPlayer.stop();
				}
			}
		} catch (Exception e) {}
		try {
			mPlayer.setOnCompletionListener(null);
		} catch (Exception e) {}
		try {
			mPlayer.release();
		} catch (Exception e) {}
		
		mPlayer = null;
		soundnormal(true);
		
		try {
	        if(mySensorManager !=null && proximitySensorEventListener != null) mySensorManager.unregisterListener(proximitySensorEventListener, myProximitySensor);
		} catch (Exception e) {}
	}
	
	@Override
	protected void onPause() {
		try {
			if(mPlayer != null && mPlayer.isPlaying()){
				mPlayer.pause();
				soundnormal(true);
			}
		} catch (Exception e) {}
		
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		clearup();
		
		super.onDestroy();
	};

	
	/////////////
	
	SensorEventListener proximitySensorEventListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		boolean sema = false;
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(sema) return;
			sema = true;
			
			try {
				if (main == null || black == null || maxval == defval)
					return;

				if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
					float reading = event.values[0];
					if (reading < maxval) {
						main.post(new Runnable() {

							@Override
							public void run() {
								black.setVisibility(View.VISIBLE);
								try {
									getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
									getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
								} catch (Exception e) {
								}
								main.requestLayout();

							}
						});
					}
					else {
						main.post(new Runnable() {

							@Override
							public void run() {
								black.setVisibility(View.GONE);
								try {
									getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
									getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
								} catch (Exception e) {}
								main.requestLayout();
							}
						});
					}
				}
			} catch (Exception e) {}
			finally{
				sema = false;
			}
		}
	};

}
