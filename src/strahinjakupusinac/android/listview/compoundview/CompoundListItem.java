package strahinjakupusinac.android.listview.compoundview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompoundListItem extends LinearLayout {

	private CheckBox mCheckBox;
	private ImageView mThumbnail;
	private TextView mPrimaryText;
	private TextView mSecondaryText;
	private ImageView mImage;

	public CompoundListItem(Context context, int id, String primaryText, String secondaryText) {
		super(context);
		this.setOrientation(HORIZONTAL);

		LayoutParams checkBoxLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mCheckBox = new CheckBox(context);
		addView(mCheckBox, checkBoxLayoutParams);

		mThumbnail = new ImageView(context);
		addView(mThumbnail);

		LinearLayout textLayout = new LinearLayout(context);
		textLayout.setOrientation(VERTICAL);
		LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
		layoutParams.weight = 1;
		textLayout.setLayoutParams(layoutParams);

		mPrimaryText = new TextView(context);
		mPrimaryText.setLines(2);
		mPrimaryText.setText(primaryText);
		mSecondaryText = new TextView(context);
		mSecondaryText.setGravity(Gravity.BOTTOM);
		mSecondaryText.setText(secondaryText);

		textLayout.addView(mPrimaryText);
		textLayout.addView(mSecondaryText);
		addView(textLayout);

		mImage = new ImageView(context);
		addView(mImage);
	}

	public void setPrimaryText(String primaryText) {
		this.mPrimaryText.setText(primaryText);
	}

	public void setSecondaryText(String secondaryText) {
		this.mSecondaryText.setText(secondaryText);
	}

	public void setThumbnailBitmap(Bitmap bitmap) {
		this.mThumbnail.setImageBitmap(bitmap);
		this.mImage.setImageBitmap(bitmap);
	}

}
