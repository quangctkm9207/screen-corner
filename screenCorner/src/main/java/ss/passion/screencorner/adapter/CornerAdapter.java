package ss.passion.screencorner.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import ss.passion.screencorner.R;

public class CornerAdapter extends BaseAdapter {

	private Context mContext;
	private int[] corners;

	public CornerAdapter(Context c, int[] arrIds) {
		mContext = c;
		corners = arrIds;
	}

	public int getCount() {
		return corners.length;
	}

	public Object getItem(int arg0) {
		return corners[arg0];
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.corner, arg2, false);
		}

		ImageView imgView = (ImageView) convertView.findViewById(R.id.ivCorner);
		imgView.setBackgroundResource(corners[position]);
		return convertView;
	}

}
