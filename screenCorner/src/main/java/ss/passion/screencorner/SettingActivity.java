package ss.passion.screencorner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.Slider;

import ss.passion.screencorner.CornerService.LocalBinder;
import ss.passion.screencorner.adapter.CornerAdapter;
import ss.passion.screencorner.util.Variables;

public class SettingActivity extends ActionBarActivity {
	SwitchCompat switchEnable;
	CheckBox cbStartOnBoot;
	CheckBox cbOverlap;
	CheckBox cbLockScreen;
	CheckBox cbShowIcon;
	Slider sbSize;
	LinearLayout itemChooseCorner;
	SharedPreferences pre;
	CheckBox cbTL, cbTR, cbBL, cbBR;
	CornerService cornerService;
	ImageView ivCorner;

	LinearLayout itemHelp;
	LinearLayout itemRate;
	LinearLayout itemTranslate;
	Boolean mBounded;

	Dialog dialog;
	int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar aBar = getSupportActionBar();
		aBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#004D40")));
		setContentView(R.layout.settings);

		pre = getSharedPreferences(Variables.SETTING_PRE, 0);
		getWidgets();
	}

	private void getWidgets() {
		switchEnable = (SwitchCompat) findViewById(R.id.switchEnable);
		cbStartOnBoot = (CheckBox) findViewById(R.id.cbStartOnBoot);
		cbOverlap = (CheckBox) findViewById(R.id.cbOverlap);
		cbLockScreen = (CheckBox) findViewById(R.id.cbLockScreen);
		cbShowIcon = (CheckBox) findViewById(R.id.cbShowIcon);
		sbSize = (Slider) findViewById(R.id.sliderSize);
		cbTL = (CheckBox) findViewById(R.id.cbTopLeft);
		cbTR = (CheckBox) findViewById(R.id.cbTopRight);
		cbBL = (CheckBox) findViewById(R.id.cbBottomLeft);
		cbBR = (CheckBox) findViewById(R.id.cbBottomRight);
		itemChooseCorner = (LinearLayout) findViewById(R.id.itemChooseCorner);
		ivCorner = (ImageView) findViewById(R.id.ivCorner);
		itemHelp = (LinearLayout) findViewById(R.id.itemHelp);
		itemRate = (LinearLayout) findViewById(R.id.itemRate);
		itemTranslate = (LinearLayout) findViewById(R.id.itemTranslate);

		loadingSettings();

		switchEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				setEnableCorner(isChecked);
			}
		});
		cbStartOnBoot.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setStartOnBoot(isChecked);
			}
		});
		cbOverlap.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setOverlapStatusBar(isChecked);
			}
		});
		cbLockScreen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setEnableLockScreen(isChecked);
			}
		});
		cbShowIcon.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				setShowIconStatusBar(isChecked);
			}
		});

		sbSize.setOnValueChangedListener(new Slider.OnValueChangedListener() {

			@Override
			public void onValueChanged(int value) {
				setCornerSize(value);
			}
		});

		cbTL.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				pre.edit().putBoolean(Variables.TOP_LEFT, isChecked).commit();
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		});

		cbTR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				pre.edit().putBoolean(Variables.TOP_RIGHT, isChecked).commit();
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		});

		cbBL.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				pre.edit().putBoolean(Variables.BOTTOM_LEFT, isChecked)
						.commit();
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		});

		cbBR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				pre.edit().putBoolean(Variables.BOTTOM_RIGHT, isChecked)
						.commit();
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		});
		itemChooseCorner.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						CornerChooser.class);
				// intent.putExtra(Variables., value)
				startActivityForResult(intent, 101);
				// onCreateDialog().show();
			}
		});
		itemHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						HelpActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		itemRate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("market://details?id=ss.passion.screencorner")));

				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("https://play.google.com/store/apps/details?id=ss.passion.screencorner")));
				}
			}
		});
		itemTranslate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://crowdin.com/project/screen-corner")));
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101) {
			if (resultCode == RESULT_OK) {
				int icon = data.getIntExtra(Variables.CORNER_ICON, 0);
				pre.edit().putInt(Variables.CORNER_ICON, icon).commit();
				ivCorner.setBackgroundResource(Variables.TL_ICONS[icon]);
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		}
	}

	private void loadingSettings() {
		switchEnable.setChecked(pre.getBoolean(Variables.ENABLE_CORNER, true));
		cbStartOnBoot.setChecked(pre.getBoolean(Variables.BOOT_START, false));
		cbOverlap.setChecked(pre.getBoolean(Variables.OVERLAP, false));
		cbLockScreen.setChecked(pre.getBoolean(Variables.LOCK_SCREEN, false));
		cbShowIcon.setChecked(pre.getBoolean(Variables.SHOW_ICON, false));

		sbSize.setValue(pre.getInt(Variables.CORNER_SIZE, 20));

		cbTL.setChecked(pre.getBoolean(Variables.TOP_LEFT, true));
		cbTR.setChecked(pre.getBoolean(Variables.TOP_RIGHT, true));
		cbBL.setChecked(pre.getBoolean(Variables.BOTTOM_LEFT, true));
		cbBR.setChecked(pre.getBoolean(Variables.BOTTOM_RIGHT, true));

		ivCorner.setBackgroundResource(Variables.TL_ICONS[pre.getInt(
				Variables.CORNER_ICON, 0)]);
		if (cornerService == null) {
			Intent i = new Intent(SettingActivity.this, CornerService.class);
			stopService(i);
			startService(i);
			bindService(i, mConnection, Context.BIND_AUTO_CREATE);
			if (cornerService != null) {
				cornerService.adjustCorner();
			}
		}
	}

	void setEnableCorner(boolean isChecked) {
		pre.edit().putBoolean(Variables.ENABLE_CORNER, isChecked).commit();
		if (isChecked) {
			if (cornerService == null) {
				Intent i = new Intent(SettingActivity.this, CornerService.class);
				startService(i);
				bindService(i, mConnection, Context.BIND_AUTO_CREATE);
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			} else {
				cornerService.adjustCorner();
			}
		} else {
			if (cornerService != null) {
				cornerService.stopCornerService();
			}
		}

	}

	void setStartOnBoot(boolean isChecked) {
		pre.edit().putBoolean(Variables.BOOT_START, isChecked).commit();
	}

	void setOverlapStatusBar(boolean isChecked) {
		pre.edit().putBoolean(Variables.OVERLAP, isChecked).commit();
		if (cornerService != null) {
			cornerService.adjustCorner();
		}
	}

	void setEnableLockScreen(boolean isChecked) {
		pre.edit().putBoolean(Variables.LOCK_SCREEN, isChecked).commit();
		if (cornerService != null) {
			cornerService.adjustCorner();
		}
	}

	void setShowIconStatusBar(boolean isChecked) {
		pre.edit().putBoolean(Variables.SHOW_ICON, isChecked).commit();
		if (cornerService != null) {
			cornerService.adjustCorner();
		}
	}

	void setCornerSize(int size) {
		pre.edit().putInt(Variables.CORNER_SIZE, size).commit();
		if (cornerService != null) {
			cornerService.setCornerSize(size);
		}
	}

	protected Dialog onCreateDialog() {

		AlertDialog.Builder builder;
		Context mContext = this;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.choose_corner,
				(ViewGroup) findViewById(R.id.icon_layout));

		GridView gvColor = (GridView) layout.findViewById(R.id.gvEmoticon);
		CornerAdapter colorAdapter = new CornerAdapter(this, Variables.TL_ICONS);
		gvColor.setAdapter(colorAdapter);

		gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				pre.edit().putInt(Variables.CORNER_ICON, position).commit();
				ivCorner.setBackgroundResource(Variables.TL_ICONS[position]);
				dialog.dismiss();
				if (cornerService != null) {
					cornerService.adjustCorner();
				}
			}
		});

		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		dialog = builder.create();

		return dialog;

	}

	ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName name) {

			mBounded = false;
			cornerService = null;
		}

		public void onServiceConnected(ComponentName name, IBinder service) {
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder) service;
			cornerService = mLocalBinder.getServerInstance();
		}
	};

	@Override
	public void onDestroy() {
		if (mBounded) {
			unbindService(mConnection);
		}
		if (pre.getBoolean(Variables.ENABLE_CORNER, false)) {
			Intent intent = new Intent(SettingActivity.this,
					CornerService.class);
			startService(intent);
		}
		super.onDestroy();
	}
}
