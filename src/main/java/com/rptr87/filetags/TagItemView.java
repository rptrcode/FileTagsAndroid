package com.rptr87.filetags;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rptr87.filetagger.R;

public class TagItemView extends LinearLayout {
	TextView textView;

	public TagItemView(Context context) {
		super(context);

		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.tag_item, this, true);
		textView = (TextView) findViewById(R.id.textView);
	}

	void setLabel(String str) {
		(this.textView).setText(str);
	}
}
