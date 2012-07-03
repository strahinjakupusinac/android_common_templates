package strahinjakupusinac.android.viewpager;

import strahinjakupusinac.android.R;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class ViewPagerGalleryActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private PageAdapter mPageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_gallery_layout);

		this.mViewPager = (ViewPager) findViewById(R.id.view_pager_gallery_view_pager);
		this.mPageAdapter = new PageAdapter(getSupportFragmentManager());
		this.mViewPager.setAdapter(mPageAdapter);
		this.mViewPager.setPageMargin(100);
	}

	private class PageAdapter extends FragmentStatePagerAdapter {

		private Cursor cursor;
		private int columnIndex;

		public PageAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);

			// Get the data location of the image
			String[] projection = { MediaStore.Images.Media.DATA };
			cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, // Which columns to return
					null, // Return all rows
					null, null);
			columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		}

		@Override
		public Fragment getItem(int position) {
			cursor.moveToPosition(position);
			String imagePath = cursor.getString(columnIndex);
			return GalleryFragment.newInstance(position, imagePath);
		}

		@Override
		public int getCount() {
			return cursor.getCount();
		}

		// @Override
		// public Object instantiateItem(View collection, int position) {
		//
		// ((ViewPager) collection).addView(page,position);
		//
		// return page;
		// }

		// @Override
		// public void destroyItem(View collection, int position, Object view) {
		// ((ViewPager) collection).removeView((TextView) view);
		// }
	}

}
