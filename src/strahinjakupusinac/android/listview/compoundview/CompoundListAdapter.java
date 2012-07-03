package strahinjakupusinac.android.listview.compoundview;

import java.util.ArrayList;
import java.util.List;

import strahinjakupusinac.android.R;
import strahinjakupusinac.android.util.BitmapUtils;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CompoundListAdapter extends BaseAdapter {

	private Context context;

	private Cursor cursor;
	private int columnIndex;

	private List<Bitmap> bitmaps;

	private ImageLoaderAsyncTask asyncImageLoader;

	public CompoundListAdapter(Context context, Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
		this.columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		this.bitmaps = new ArrayList<Bitmap>();
		this.asyncImageLoader = new ImageLoaderAsyncTask();
	}

	public int getCount() {
		return cursor.getCount();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		try {
			bitmaps.get(position);
		} catch (IndexOutOfBoundsException e) {
			bitmaps.add(position, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
			if (position == 0) {
				asyncImageLoader.execute();
			}
		}

		if (convertView == null) {
			convertView = new CompoundListItem(context, position, "czxczxcasdfsdf", "dsdsdefwwef");
		} else {
			((CompoundListItem) convertView).setThumbnailBitmap(bitmaps.get(position));
		}
		return convertView;
	}

	private class ImageLoaderAsyncTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			int counter = 0;

			try {
				while (cursor.moveToNext()) {
					String imagePath = cursor.getString(columnIndex);
					bitmaps.add(counter,
							BitmapUtils.scaleBitmapFromFile(imagePath, new Float(context.getResources().getDisplayMetrics().density * 60).intValue()));
					counter++;
					publishProgress(counter);
				}
			} catch (IllegalStateException e) {
				// silently ignore
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			notifyDataSetChanged();
		}

	}

}
