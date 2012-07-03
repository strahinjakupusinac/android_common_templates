package strahinjakupusinac.android.gallery;

import strahinjakupusinac.android.R;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageGalleryActivity extends Activity {

	private Gallery gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_galery_screen);

		this.gallery = (Gallery) findViewById(R.id.image_gallery_gallery);
		gallery.setAdapter(new ImageAdapter());
	}

	private class ImageAdapter extends BaseAdapter {

		private ImageView[] views;
		private Bitmap[] bitmaps;
		private int[] viewMapping;

		private Cursor cursor;
		private int columnIndex;

		// private BitmapPrefatcherThread prefatcherThread;

		public ImageAdapter() {
			super();

			views = new ImageView[3];
			viewMapping = new int[3];
			bitmaps = new Bitmap[3];
			viewMapping[0] = -1;
			viewMapping[1] = 0;
			viewMapping[2] = 1;

			// Get the data location of the image
			String[] projection = { MediaStore.Images.Media.DATA };
			cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
					null, // Return all rows
					null, null);
			columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			views[0] = new ImageView(getApplicationContext());

			views[1] = new ImageView(getApplicationContext());
			cursor.moveToPosition(0);
			String imagePath = cursor.getString(columnIndex);
			bitmaps[1] = BitmapFactory.decodeFile(imagePath);
			float scale = (float) bitmaps[1].getWidth() / (float) bitmaps[1].getHeight();
			int height = (int) (scale * 100);
			views[1].setImageBitmap(Bitmap.createScaledBitmap(bitmaps[1], 100, height, false));

			views[2] = new ImageView(getApplicationContext());
			cursor.moveToPosition(0);
			imagePath = cursor.getString(columnIndex);
			bitmaps[2] = BitmapFactory.decodeFile(imagePath);
			scale = (float) bitmaps[2].getWidth() / (float) bitmaps[2].getHeight();
			height = (int) (scale * 100);
			views[2].setImageBitmap(Bitmap.createScaledBitmap(bitmaps[2], 100, height, false));

			// prefatcherThread = new BitmapPrefatcherThread();
		}

		public int getCount() {
			return cursor.getCount();
		}

		public Object getItem(int position) {
			Log.e("", "get item for position " + position);
			return null;
		}

		public long getItemId(int position) {
			Log.e("", "get item id for position " + position);
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Log.e("", "get item view for position " + position);

			if (viewMapping[0] == position) {
				// scroll left
				views[2] = views[1];
				views[1] = views[0];

				bitmaps[2] = bitmaps[1];
				bitmaps[1] = bitmaps[0];

				viewMapping[0]--;
				viewMapping[1]--;
				viewMapping[2]--;

				if (position - 1 >= 0) {
					new BitmapPrefatcherThread().prefatchView(2, position - 1);
				}
			} else if (viewMapping[2] == position) {
				// scroll right
				views[0] = views[1];
				views[1] = views[2];

				bitmaps[1] = bitmaps[1];
				bitmaps[1] = bitmaps[2];

				viewMapping[0]++;
				viewMapping[1]++;
				viewMapping[2]++;

				if (position + 1 <= getCount()) {
					new BitmapPrefatcherThread().prefatchView(0, position + 1);
				}
			}
			views[1].setImageBitmap(bitmaps[1]);
			return views[1];

		}

		private class BitmapPrefatcherThread extends Thread {

			private boolean running = false;

			private int relativePosition;
			private int absolutePosition;

			public void prefatchView(int relativePosition, int absolutePosition) {
				if (!running) {
					running = true;
					this.relativePosition = relativePosition;
					this.absolutePosition = absolutePosition;
					start();
				}
			}

			@Override
			public void run() {
				super.run();
				cursor.moveToPosition(absolutePosition);
				String imagePath = cursor.getString(columnIndex);
				bitmaps[relativePosition] = null;
				bitmaps[relativePosition] = BitmapFactory.decodeFile(imagePath);
				float scale = (float) bitmaps[relativePosition].getWidth() / (float) bitmaps[relativePosition].getHeight();
				int height = (int) (scale * 100);
				bitmaps[relativePosition] = Bitmap.createScaledBitmap(bitmaps[relativePosition], 100, height, false);
			}

		}

	}

}
