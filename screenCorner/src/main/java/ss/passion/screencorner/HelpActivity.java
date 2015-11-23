package ss.passion.screencorner;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gc.materialdesign.views.LayoutRipple;

@SuppressLint("NewApi")
public class HelpActivity extends ActionBarActivity implements
		OnClickListener {
	LayoutRipple itemShare,itemFeedBack,itemAbout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ActionBar aBar=getSupportActionBar();
		aBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#004D40")));
		aBar.setHomeButtonEnabled(true);
		aBar.setDisplayHomeAsUpEnabled(true);
		
		itemShare = (LayoutRipple) findViewById(R.id.itemRate);
		itemFeedBack = (LayoutRipple) findViewById(R.id.itemFeedback);
		itemAbout = (LayoutRipple) findViewById(R.id.itemAbout);
		
		itemShare.setOnClickListener(this);
		itemFeedBack.setOnClickListener(this);
		itemAbout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.itemShare:
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Screen Corner App");
			sharingIntent
					.putExtra(
							Intent.EXTRA_TEXT,
							"Hey, check cool a app to customize android screen cornes at: https://play.google.com/store/apps/details?id=ss.passion.screencorner");
			try {
				startActivity(Intent.createChooser(sharingIntent, "Share"));
				// db.close();

			} catch (android.content.ActivityNotFoundException ex) {

			}
			break;

		case R.id.itemFeedback:
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("message/rfc822");
			i.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "quang.bme.hust.55@gmail.com" });
			i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from Screen Corner app");
			i.putExtra(Intent.EXTRA_TEXT, "");
			try {
				startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "There are no email clients installed.",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.itemAbout:
			final Dialog di = new Dialog(this);
			di.requestWindowFeature(Window.FEATURE_NO_TITLE);
			di.setContentView(R.layout.about);
			di.getWindow().setLayout(android.view.WindowManager.LayoutParams.FILL_PARENT,
					android.view.WindowManager.LayoutParams.WRAP_CONTENT);
			di.show();
			break;
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
