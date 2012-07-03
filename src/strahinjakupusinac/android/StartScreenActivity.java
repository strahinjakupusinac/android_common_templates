package strahinjakupusinac.android;

import strahinjakupusinac.android.gallery.ImageGalleryActivity;
import strahinjakupusinac.android.listview.compoundview.ListWithCompoundViewsActivity;
import strahinjakupusinac.android.listview.customview.ListWithCustomViewsActivity;
import strahinjakupusinac.android.viewpager.ViewPagerGalleryActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartScreenActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_templates_start_screen_layout);

		btn1 = (Button) findViewById(R.id.startscreen_viewpager_button);
		btn2 = (Button) findViewById(R.id.startscreen_gallery_button);
		btn3 = (Button) findViewById(R.id.startscreen_custom_list_button);
		btn4 = (Button) findViewById(R.id.startscreen_compound_list_button);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btn1) {
			startActivity(new Intent(getApplicationContext(), ViewPagerGalleryActivity.class));
		} else if (v == btn2) {
			startActivity(new Intent(getApplicationContext(), ImageGalleryActivity.class));
		} else if (v == btn3) {
			startActivity(new Intent(getApplicationContext(), ListWithCustomViewsActivity.class));
		} else if (v == btn4) {
			startActivity(new Intent(getApplicationContext(), ListWithCompoundViewsActivity.class));
		}

	}

}
