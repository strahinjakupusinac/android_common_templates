package strahinjakupusinac.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * Provides various utils for Bitmap manipulation.
 * 
 * @author Strahinja Kupusinac
 * 
 */
public class BitmapUtils {

	/**
	 * Scales bitmap while keeping its aspect ratio. Bigger dimension will allways be <= 'maxSize'.
	 * 
	 * @param bitmap
	 *            bitmap to scale
	 * @param maxSize
	 *            maximum dimension
	 * @return scaled bitmap
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, int maxSize) {
		final float scale = (float) bitmap.getWidth() / (float) bitmap.getHeight();
		int width;
		int height;
		if (scale < 1) {
			width = Double.valueOf((Math.floor(scale * maxSize))).intValue();
			height = maxSize;
		} else if (scale > 1) {
			width = maxSize;
			height = Double.valueOf((Math.floor(maxSize / scale))).intValue();
		} else {
			width = maxSize;
			height = maxSize;
		}

		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * Scales bitmap from file while keeping its aspect ratio. Bigger dimension will allways be <= 'maxSize'.
	 * 
	 * @param bitmap
	 *            bitmap to scale
	 * @param maxSize
	 *            maximum dimension
	 * @return scaled bitmap
	 */
	public static Bitmap scaleBitmapFromFile(String fileName, int maxSize) {
		Bitmap bitmap = decodeSampledBitmapFromFile(fileName, maxSize, maxSize);
		return scaleBitmap(bitmap, maxSize);
	}

	/**
	 * Decodes bitmap from given file path keeping it within required with and height.
	 * 
	 * @param fileName
	 *            location of bitmap
	 * @param reqWidth
	 *            required width
	 * @param reqHeight
	 *            required height
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String fileName, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fileName, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(fileName, options);
	}

	/**
	 * Calculates sample size used for bitmap scaling.
	 * 
	 * @param options
	 *            {@link Options} for bitmap decoding
	 * @param reqWidth
	 *            requested width
	 * @param reqHeight
	 *            requested height
	 * @return
	 */
	public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			} else {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
		}
		return inSampleSize;
	}

}
