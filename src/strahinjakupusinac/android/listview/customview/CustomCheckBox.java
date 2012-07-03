package strahinjakupusinac.android.listview.customview;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.CheckBox;

public class CustomCheckBox extends CheckBox {

	public CustomCheckBox(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

}
