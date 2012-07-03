package strahinjakupusinac.android.listview.compoundview;

import strahinjakupusinac.android.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListWithCompoundViewsActivity extends ListActivity {

	private CompoundListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_layout);

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
				null, // Return all rows
				null, null);
		this.listAdapter = new CompoundListAdapter(getApplicationContext(), cursor);

		setListAdapter(listAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});
	}
}
