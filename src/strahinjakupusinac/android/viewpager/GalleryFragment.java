package strahinjakupusinac.android.viewpager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import strahinjakupusinac.android.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GalleryFragment extends Fragment {

	private static final int IMAGE_MAX_SIZE = 800;
	private int mPosition;
	private String mImagePath;

    static GalleryFragment newInstance(int position, String imagePath) {
    	GalleryFragment galleryFragment = new GalleryFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("image_path", imagePath);
        galleryFragment.setArguments(args);

        return galleryFragment;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPosition = getArguments() != null ? getArguments().getInt("position") : -1;
        this.mImagePath = getArguments() != null ? getArguments().getString("image_path") : "";
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View page = inflater.inflate(R.layout.view_pager_gallery_page_layout, null);
		ImageView image = (ImageView) page.findViewById(R.id.view_pager_gallery_page_image_view);
		image.setImageBitmap(decodeFile(new File(mImagePath)));
		return page;
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

	private Bitmap decodeFile(File f) {
		Bitmap b = null;
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();

			int scale = 1;
			if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
				scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		} catch (IOException e) {
		}
		return b;
	}
	
}
