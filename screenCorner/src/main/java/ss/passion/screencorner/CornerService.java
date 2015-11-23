package ss.passion.screencorner;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import ss.passion.screencorner.util.Variables;

public class CornerService extends Service {
	private WindowManager windowManager;
	private WindowManager.LayoutParams paramsTopLeft, paramsTopRight,
			paramsBottomLeft, paramsBottomRight;
	ImageView ivTopLeft;
	ImageView ivTopRight;
	ImageView ivBottomLeft;
	ImageView ivBottomRight;
	int size = 20;

	IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public CornerService getServerInstance() {
			return CornerService.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		adjustCorner();
		return START_NOT_STICKY;
	}

	void adjustCorner() {
		if (windowManager == null)
			windowManager = (WindowManager) getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
		SharedPreferences pre = getSharedPreferences(Variables.SETTING_PRE, 0);
		boolean enable = pre.getBoolean(Variables.ENABLE_CORNER, true);
		if (enable) {
			showCorner();
		}
		if (ivTopLeft == null && ivTopRight == null && ivBottomLeft == null
				&& ivBottomRight == null) {
			stopSelf();
		}
	}

	void showCorner() {
		SharedPreferences pre = getSharedPreferences(Variables.SETTING_PRE, 0);
		int corner_icon = pre.getInt(Variables.CORNER_ICON, 0);
		for (int i = 0; i < 4; i++) {
			removeCorner(i);
		}
		int flags;
		int type;
		if (pre.getBoolean(Variables.LOCK_SCREEN, false)) {
			type = LayoutParams.TYPE_SYSTEM_OVERLAY;
		} else {
			type = LayoutParams.TYPE_SYSTEM_ALERT;
		}
		if (pre.getBoolean(Variables.OVERLAP, false)) {
			flags = LayoutParams.FLAG_NOT_FOCUSABLE
					| LayoutParams.FLAG_LAYOUT_NO_LIMITS
					| LayoutParams.FLAG_NOT_TOUCHABLE
					| LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		} else {

			flags = LayoutParams.FLAG_NOT_FOCUSABLE
					| LayoutParams.FLAG_LAYOUT_NO_LIMITS
					| LayoutParams.FLAG_NOT_TOUCHABLE;

		}

		// set up top left corner
		if (pre.getBoolean(Variables.TOP_LEFT, true)) {
			removeCorner(0);
			ivTopLeft = new ImageView(this);
			ivTopLeft.setBackgroundResource(Variables.TL_ICONS[corner_icon]);
			paramsTopLeft = new WindowManager.LayoutParams(size, size, type,
					flags, PixelFormat.TRANSLUCENT);
			paramsTopLeft.gravity = Gravity.LEFT | Gravity.TOP;
			windowManager.addView(ivTopLeft, paramsTopLeft);
		} else {
			removeCorner(0);
		}

		// set up top right corner
		if (pre.getBoolean(Variables.TOP_RIGHT, true)) {
			removeCorner(1);
			ivTopRight = new ImageView(this);
			ivTopRight.setBackgroundResource(Variables.TR_ICONS[corner_icon]);
			paramsTopRight = new WindowManager.LayoutParams(size, size, type,
					flags, PixelFormat.TRANSLUCENT);
			paramsTopRight.gravity = Gravity.RIGHT | Gravity.TOP;
			windowManager.addView(ivTopRight, paramsTopRight);
		} else {
			removeCorner(1);
		}

		// set up bottom left corner
		if (pre.getBoolean(Variables.BOTTOM_LEFT, true)) {
			removeCorner(2);
			ivBottomLeft = new ImageView(this);
			ivBottomLeft.setBackgroundResource(Variables.BL_ICONS[corner_icon]);
			paramsBottomLeft = new WindowManager.LayoutParams(size, size, type,
					flags, PixelFormat.TRANSLUCENT);
			paramsBottomLeft.gravity = Gravity.LEFT | Gravity.BOTTOM;
			windowManager.addView(ivBottomLeft, paramsBottomLeft);
		} else {
			removeCorner(2);
		}

		// set up bottom right corner
		if (pre.getBoolean(Variables.BOTTOM_RIGHT, true)) {
			removeCorner(3);
			ivBottomRight = new ImageView(this);
			ivBottomRight
					.setBackgroundResource(Variables.BR_ICONS[corner_icon]);
			paramsBottomRight = new WindowManager.LayoutParams(size, size,
					type, flags, PixelFormat.TRANSLUCENT);
			paramsBottomRight.gravity = Gravity.RIGHT | Gravity.BOTTOM;
			windowManager.addView(ivBottomRight, paramsBottomRight);
		} else {
			removeCorner(3);
		}
		setCornerSize(pre.getInt(Variables.CORNER_SIZE, 20));
		notificationSetState("Screen Corner", getResources().getString(R.string.go_to_setting),
				R.drawable.ic_launcher);

	}

	public void setCornerSize(int size) {
		// TODO Auto-generated method stub
		size = pxFromDp(size);
		if (ivTopLeft != null) {
			paramsTopLeft.width = size;
			paramsTopLeft.height = size;
			try {
				windowManager.updateViewLayout(ivTopLeft, paramsTopLeft);
			} catch (Exception e) {
			}
		}
		if (ivTopRight != null) {
			paramsTopRight.width = size;
			paramsTopRight.height = size;
			try {
				windowManager.updateViewLayout(ivTopRight, paramsTopRight);
			} catch (Exception e) {
			}
		}
		if (ivBottomLeft != null) {
			paramsBottomLeft.width = size;
			paramsBottomLeft.height = size;
			try {
				windowManager.updateViewLayout(ivBottomLeft, paramsBottomLeft);
			} catch (Exception e) {
			}
		}
		if (ivBottomRight != null) {
			paramsBottomRight.width = size;
			paramsBottomRight.height = size;
			try {
				windowManager
						.updateViewLayout(ivBottomRight, paramsBottomRight);
			} catch (Exception e) {
			}
		}

	}

	public boolean stopCornerService() {
		stopSelf();
		for (int i = 0; i < 4; i++) {
			removeCorner(i);
		}
		return false;
	}

	public void removeCorner(int corner) {
		switch (corner) {
		case 0:
			if (ivTopLeft != null) {
				try {
					windowManager.removeView(ivTopLeft);
					ivTopLeft = null;
				} catch (Exception e) {
				}

			}
			break;
		case 1:
			if (ivTopRight != null) {
				try {
					windowManager.removeView(ivTopRight);
					ivTopRight = null;
				} catch (Exception e) {
				}

			}
			break;
		case 2:
			if (ivBottomLeft != null) {
				try {
					windowManager.removeView(ivBottomLeft);
					ivBottomLeft = null;
				} catch (Exception e) {
				}

			}
			break;
		case 3:
			if (ivBottomRight != null) {
				try {
					windowManager.removeView(ivBottomRight);
					ivBottomRight = null;
				} catch (Exception e) {
				}

			}
			break;

		default:
			break;
		}
	}

	private void notificationSetState(String title, String content,
			int resourceId) {
		// Notification for set state of application
		Intent intent = new Intent(this, SettingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getApplicationContext());
		builder.setContentIntent(pendingIntent);
		builder.setContentText(content);

		// check if users want to show icon and notification
		SharedPreferences pre = getSharedPreferences(Variables.SETTING_PRE, 0);
		if (pre.getBoolean(Variables.SHOW_ICON, false)) {
			builder.setSmallIcon(resourceId);
			builder.setContentTitle(title);
		} else {
			
			if(android.os.Build.VERSION.SDK_INT>=16){
				builder.setSmallIcon(resourceId);
				builder.setContentTitle("Screen Corner");
				builder.setPriority(Notification.PRIORITY_MIN);
			}else{
				
			}
			
		}

		Notification noti = builder.build();
		startForeground(1337, noti);
	}

	private int pxFromDp(double dp) {
		return (int) (dp * getResources().getDisplayMetrics().density);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopCornerService();
		stopForeground(true);
	}

}
