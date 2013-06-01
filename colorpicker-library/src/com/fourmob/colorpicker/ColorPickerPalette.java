package com.fourmob.colorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ColorPickerPalette extends TableLayout {
	private String mDescription;
	private String mDescriptionSelected;
	private int mMarginSize;
	private int mNumColumns;
	public ColorPickerSwatch.OnColorSelectedListener mOnColorSelectedListener;
	private int mSwatchLength;

	public ColorPickerPalette(Context context) {
		super(context);
	}

	public ColorPickerPalette(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	private void addSwatchToRow(TableRow tableRow, View view, int line) {
		if (line % 2 == 0) {
			tableRow.addView(view);
			return;
		}
		tableRow.addView(view, 0);
	}

	private ImageView createBlankSpace() {
		ImageView imageView = new ImageView(getContext());
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(this.mSwatchLength, this.mSwatchLength);
		layoutParams.setMargins(this.mMarginSize, this.mMarginSize, this.mMarginSize, this.mMarginSize);
		imageView.setLayoutParams(layoutParams);
		return imageView;
	}

	private ColorPickerSwatch createColorSwatch(int color, int selectedColor) {
		ColorPickerSwatch colorPickerSwatch = new ColorPickerSwatch(getContext(), color, color == selectedColor, this.mOnColorSelectedListener);
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(this.mSwatchLength, this.mSwatchLength);
		layoutParams.setMargins(this.mMarginSize, this.mMarginSize, this.mMarginSize, this.mMarginSize);
		colorPickerSwatch.setLayoutParams(layoutParams);
		return colorPickerSwatch;
	}

	private TableRow createTableRow() {
		TableRow localTableRow = new TableRow(getContext());
		localTableRow.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
		return localTableRow;
	}

	private void setSwatchDescription(int colorIndex, boolean checked, View view) {
		String contentDescription;
		if (!checked) {
			contentDescription = String.format(mDescription, colorIndex);
		} else {
			contentDescription = String.format(mDescriptionSelected, colorIndex);
		}
		view.setContentDescription(contentDescription);
	}

	public void drawPalette(int[] colors, int selectedColor) {
		if (colors == null) {
			return;
		}
		removeAllViews();

		int numColors = colors.length;
		int numCreatedColor = 0;
		TableRow tableRow = createTableRow();
		int numColorsInRow = 0;
		int line;
		do {
			line = 0;
			int color = colors[numCreatedColor];
			ColorPickerSwatch colorPickerSwatch = createColorSwatch(color, selectedColor);
			setSwatchDescription(numCreatedColor + 1, selectedColor == color, colorPickerSwatch);
			addSwatchToRow(tableRow, colorPickerSwatch, line);

			numColorsInRow++;
			if (numColorsInRow == this.mNumColumns) {
				addView(tableRow);
				tableRow = createTableRow();
				numColorsInRow = 0;
				line++;
			}

			numCreatedColor++;
		} while (numCreatedColor < numColors);

		while (numColorsInRow != this.mNumColumns) {
			addSwatchToRow(tableRow, createBlankSpace(), line);
			numColorsInRow++;
		}
		addView(tableRow);
	}

	public void init(int size, int numColumns, ColorPickerSwatch.OnColorSelectedListener onColorSelectedListener) {
		this.mNumColumns = numColumns;
		Resources resources = getResources();
		if (size == 1) {
			this.mSwatchLength = resources.getDimensionPixelSize(R.dimen.color_swatch_large);
			this.mMarginSize = resources.getDimensionPixelSize(R.dimen.color_swatch_margins_large);
		} else {
			this.mSwatchLength = resources.getDimensionPixelSize(R.dimen.color_swatch_small);
			this.mMarginSize = resources.getDimensionPixelSize(R.dimen.color_swatch_margins_small);
		}
		this.mOnColorSelectedListener = onColorSelectedListener;
		this.mDescription = resources.getString(R.string.color_swatch_description);
		this.mDescriptionSelected = resources.getString(R.string.color_swatch_description_selected);
	}
}