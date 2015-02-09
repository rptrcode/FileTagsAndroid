package com.rptr87.filetags;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rptr87.filetagger.R;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileviewFragment extends Fragment {
	public static LinearLayout filesLayout;
	public FileDetailviewFragment mFileDetailviewFragment = new FileDetailviewFragment();
	private FileItemView.OnFileClickListener fileClickListener = new FileItemView.OnFileClickListener() {
		public void onClick(File file) {
			if (file.isDirectory())
				listFiles(file);
			else
				displayFileDetailview(file);
		}
	};

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.folderview_layout, container, false);
		filesLayout = (LinearLayout) rootView.findViewById(R.id.layoutFiles);
		File folder = Environment.getExternalStorageDirectory();
		listFiles(folder);
		return rootView;
	}

	private void listFiles(File folder) {

		FileviewFragment.filesLayout.removeAllViews();
		File[] fileList = folder.listFiles();
		List<FileItemView> fileItemViewList = new LinkedList<>();

		for (File aFileList : fileList) {
			FileItemView item = new FileItemView(FileviewFragment.filesLayout.getContext());
			item.setFile(aFileList);
			fileItemViewList.add(item);
		}

		for (int i = 0; i < fileList.length; i++) {
			fileItemViewList.get(i).addListener(fileClickListener);
			FileviewFragment.filesLayout.addView(fileItemViewList.get(i));
		}
	}

	private void displayFileDetailview(File file) {

		Bundle args = new Bundle();
		args.putString(FileDetailviewFragment.ARG_FILE_PATH, file.getAbsolutePath());
		mFileDetailviewFragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, mFileDetailviewFragment).commit();
	}

}
