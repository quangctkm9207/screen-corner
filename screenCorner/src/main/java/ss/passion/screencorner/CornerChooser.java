package ss.passion.screencorner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import ss.passion.screencorner.adapter.CornerAdapter;
import ss.passion.screencorner.util.Variables;

public class CornerChooser extends ActionBarActivity {
	int cornerIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_corner);
		getSupportActionBar().hide();
		getWidgets();

	}

	void getWidgets() {
		GridView gvColor = (GridView) findViewById(R.id.gvEmoticon);
		CornerAdapter colorAdapter = new CornerAdapter(this, Variables.TL_ICONS);
		gvColor.setAdapter(colorAdapter);
		cornerIcon = getIntent().getIntExtra(Variables.CORNER_ICON, 0);

		gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent data = new Intent();
				data.putExtra(Variables.CORNER_ICON, position);
				setResult(RESULT_OK, data);
				finish();
			}
		});

	}

}
