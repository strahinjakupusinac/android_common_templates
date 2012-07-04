package strahinjakupusinac.android.grid;

import strahinjakupusinac.android.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view_layout);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
				null, // Return all rows
				null, null);
		gridview.setAdapter(new GridViewAdapter(this, cursor));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(GridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

}
