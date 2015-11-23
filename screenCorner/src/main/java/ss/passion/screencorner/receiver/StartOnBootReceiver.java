package ss.passion.screencorner.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import ss.passion.screencorner.CornerService;
import ss.passion.screencorner.util.Variables;

public class StartOnBootReceiver extends BroadcastReceiver {
	public StartOnBootReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences pre= context.getSharedPreferences(Variables.SETTING_PRE, 0);
		if (pre.getBoolean(Variables.BOOT_START, false)) {
			context.startService(new Intent(context, CornerService.class));
		}
	}
}
