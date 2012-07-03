package strahinjakupusinac.android.listview.customview;

import java.text.SimpleDateFormat;
import java.util.Date;

import strahinjakupusinac.android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

public class CustomListItem extends View implements Checkable {

	// height will always be 80dp
	private static final int HEIGHT = 80;

	private final float displayDensity;

	private boolean isChecked;

	private Drawable checkBoxIconUnchecked;
	private Drawable checkBoxIconChecked;
	private Bitmap icon;
	private String fileName;
	private String updated;
	private Bitmap tagBitmap;

	private Paint textPaint;

	public CustomListItem(Context context, Bitmap icon, String fileName, long updated) {
		super(context);

		this.isChecked = false;

		this.displayDensity = getResources().getDisplayMetrics().density;

		Rect checkboxBounds = new Rect(dpToPixels(5), dpToPixels(25), dpToPixels(35), dpToPixels(55));
		this.checkBoxIconUnchecked = getResources().getDrawable(R.drawable.checkbox_unchecked);
		this.checkBoxIconUnchecked.setBounds(checkboxBounds);
		this.checkBoxIconChecked = getResources().getDrawable(R.drawable.checkbox_checked);
		this.checkBoxIconChecked.setBounds(checkboxBounds);
		this.icon = icon;
		this.fileName = fileName;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		this.updated = "Updated " + dateFormat.format(new Date(updated));
		this.tagBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tag_icon);
		// tagDrawable.get
		this.textPaint = new Paint();
		this.textPaint.setAntiAlias(true);
		// Must manually scale the desired text size to match screen density
		this.textPaint.setTextSize(16 * displayDensity);
		this.textPaint.setColor(0xffffffff);

		setFocusable(false);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(parentWidth, new Float(HEIGHT * getResources().getDisplayMetrics().density).intValue());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isChecked) {
			checkBoxIconChecked.draw(canvas);
		} else {
			checkBoxIconUnchecked.draw(canvas);
		}

		if (icon != null) {
			canvas.drawBitmap(icon, dpToPixels(70) - icon.getWidth() / 2, dpToPixels(40) - icon.getHeight() / 2, null);

			int x = getWidth() - dpToPixels(40) - tagBitmap.getWidth() / 2;
			int y = dpToPixels(40) - tagBitmap.getHeight() / 2;
			canvas.drawBitmap(tagBitmap, x, y, null);
		}
		canvas.drawText(fileName, dpToPixels(110), dpToPixels(26), textPaint);
		canvas.drawText(fileName, dpToPixels(110), dpToPixels(75), textPaint);

	}

	public void setIconBitmap(Bitmap bitmap) {
		this.icon = bitmap;
		requestLayout();
		invalidate();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		requestLayout();
		invalidate();
	}

	private int dpToPixels(float dp) {
		return new Float(dp * displayDensity).intValue();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();

			// checkBoxIcon.getBounds().exactCenterX()
			if (checkBoxIconChecked.getBounds().contains((int) x, (int) y)) {
				toggle();
				return true;
			}
		}
		return false;
	}

	public void setChecked(boolean checked) {
		this.isChecked = checked;
		requestLayout();
		invalidate();
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void toggle() {
		this.isChecked = !isChecked;
		requestLayout();
		invalidate();
	}

}
