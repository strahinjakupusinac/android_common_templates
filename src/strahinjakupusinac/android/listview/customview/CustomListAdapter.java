package strahinjakupusinac.android.listview.customview;

import java.util.ArrayList;
import java.util.Date;
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

public class CustomListAdapter extends BaseAdapter {

	private Context context;

	private Cursor cursor;
	private int columnIndex;

	private List<Bitmap> bitmaps;

	private ImageLoaderAsyncTask asyncImageLoader;

	public CustomListAdapter(Context context, Cursor cursor) {
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
		View view = convertView;

		try {
			bitmaps.get(position);
		} catch (IndexOutOfBoundsException e) {
			bitmaps.add(position, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
			if (position == 0) {
				asyncImageLoader.execute(0);
			}
		}

		if (convertView == null) {
			view = new CustomListItem(context, bitmaps.get(position), "Some file.ext", new Date().getTime());
		} else {
			((CustomListItem) view).setIconBitmap(bitmaps.get(position));
		}
		return view;
	}

	private class ImageLoaderAsyncTask extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int counter = 0;
			try {
				while (cursor.moveToNext()) {
					String imagePath = cursor.getString(columnIndex);
					bitmaps.add(counter,
							BitmapUtils.scaleBitmapFromFile(imagePath, new Float(context.getResources().getDisplayMetrics().density * 60).intValue()));
					counter++;
					publishProgress(counter);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			notifyDataSetChanged();
		}

	}

}
