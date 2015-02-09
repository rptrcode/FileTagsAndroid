package com.rptr87.filetags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rptr87.filetagger.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by puttaraju on 23-05-2015.
 */
public class FileItemView extends LinearLayout {
	File mFile;
	TextView textView;
	ImageView imageView;
	Context mContext;
	private List<FileItemView.OnFileClickListener> listenerList;
	private View.OnClickListener clickListener = new View.OnClickListener() {
		public void onClick(View v) {
			for (int i = 0; i < listenerList.size(); i++) {
				listenerList.get(i).onClick(FileItemView.this.mFile);
			}
		}
	};

	public FileItemView(final Context context) {
		super(context);
		mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.file_item, this, true);
		mFile = null;
		textView = (TextView) findViewById(R.id.textView);
		imageView = (ImageView) findViewById(R.id.imageView);

		listenerList = new LinkedList<>();
	}

	public void addListener(FileItemView.OnFileClickListener listener) {
		listenerList.add(listener);
	}

	public void removeListener(FileItemView.OnFileClickListener listener) {
		listenerList.remove(listener);
	}

	public void setFile(File file) {
		mFile = file;
		textView.setText(file.getName());
		this.setOnClickListener(clickListener);
	}

	public interface OnFileClickListener {

		void onClick(File file);
	}
}
