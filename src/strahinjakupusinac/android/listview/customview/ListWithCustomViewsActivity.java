package strahinjakupusinac.android.listview.customview;

import strahinjakupusinac.android.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListWithCustomViewsActivity extends ListActivity {

	private CustomListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_layout);

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
				null, // Return all rows
				null, null);
		this.listAdapter = new CustomListAdapter(getApplicationContext(), cursor);

		setListAdapter(listAdapter);
		getListView().setItemsCanFocus(false);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

			}
		});
	}

}
