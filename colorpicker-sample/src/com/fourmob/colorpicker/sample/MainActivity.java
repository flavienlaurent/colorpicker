package com.fourmob.colorpicker.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.fourmob.colorpicker.ColorPickerDialog;
import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
		colorPickerDialog.initialize(R.string.dialog_title, new int[] { Color.CYAN, Color.LTGRAY, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color.YELLOW }, Color.YELLOW, 3, 2);
		colorPickerDialog.setOnColorSelectedListener(new OnColorSelectedListener() {

			@Override
			public void onColorSelected(int color) {
				Toast.makeText(MainActivity.this, "selectedColor : " + color, Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				colorPickerDialog.show(getSupportFragmentManager(), "colorpicker");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
