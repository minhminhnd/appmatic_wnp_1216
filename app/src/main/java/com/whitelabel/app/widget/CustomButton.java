package com.whitelabel.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.whitelabel.app.R;
import com.whitelabel.app.utils.JFontUtils;


public class CustomButton extends Button {

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
//		initTypeface(context, attrs);
	}

	private void initTypeface(Context context, AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		
		TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.CustomTextStyle);
		if (types == null) {
			return;
		}

		int typefaceIndex = types.getInt(R.styleable.CustomTextStyle_textFont, -1);
		
		types.recycle();
		
		if (typefaceIndex != -1) {
			Typeface typeFace = JFontUtils.getTypeface(context, typefaceIndex);
			if (typeFace != null) {
				this.setTypeface(typeFace);
			}
		}
	}
	
	@Override
	public void setBackground(Drawable background) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				super.setBackground(background);
			} else {
				super.setBackgroundDrawable(background);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
