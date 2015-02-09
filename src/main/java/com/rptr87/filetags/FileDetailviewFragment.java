package com.rptr87.filetags;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.rptr87.filetagger.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileDetailviewFragment extends Fragment {
	public static final String ARG_FILE_PATH = "file path";
	public static String filePath;
	View mRootView;
	List<String> mListValues = new ArrayList<>();
	ArrayAdapter<String> mAdpater;
	View.OnClickListener openFileBtnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
			String mimeType = mimeTypeMap.getMimeTypeFromExtension(getFileExtension(filePath));
			Intent viewIntent = new Intent(Intent.ACTION_VIEW);
			viewIntent.setDataAndType(Uri.parse(filePath), mimeType);
//			viewIntent.setDataAndType(Uri.parse(filePath), getMimeType(filePath.toLowerCase()));
			viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
				MainActivity.appContext.startActivity(viewIntent);
			} catch (ActivityNotFoundException e) {
				MainActivity.alert("No handler for this type " + getMimeType(filePath));
			}
		}
	};
	private List<FileDetailviewFragment.TagsUpdatedListener> mListeners = new LinkedList<>();
	View.OnClickListener addTagBtnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			EditText editText = (EditText) mRootView.findViewById(R.id.editText);
			String tag = editText.getText().toString();
			if (!mListValues.contains(tag)) {
				mListValues.add(tag);
				mAdpater.notifyDataSetChanged();
				notifyListeners(MainActivity.TAG_UPDATE.ADD_TAG, filePath, tag);
			}
		}
	};

	static String getFileExtension(String path) {
		String ext = "";
		int dotHere = path.lastIndexOf(".");
		if (dotHere != -1) {
			ext = path.substring(dotHere + 1);
		}
		MainActivity.alert("extension: " + ext);
		return ext.toLowerCase();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.file_detail_layout, container, false);
		TextView textView = (TextView) mRootView.findViewById(R.id.textView);
		filePath = getArguments().getString(ARG_FILE_PATH);
		textView.setText(filePath);

		(mRootView.findViewById(R.id.addTagBtn)).setOnClickListener(addTagBtnClickListener);
		(mRootView.findViewById(R.id.openFileBtn)).setOnClickListener(openFileBtnClickListener);
		ListView listView = (ListView) mRootView.findViewById(R.id.listView);
		mAdpater = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, mListValues);
		MainActivity mainActivity = (MainActivity) getActivity();
		List<String> list = mainActivity.getTagList(filePath);
		if (list != null)
			mListValues.addAll(list);
		listView.setAdapter(mAdpater);

		return mRootView;
	}

	public void addListener(FileDetailviewFragment.TagsUpdatedListener listener) {
		mListeners.add(listener);
	}

	public void removeListener(FileDetailviewFragment.TagsUpdatedListener listener) {
		mListeners.remove(listener);
	}

	private void notifyListeners(MainActivity.TAG_UPDATE update, String filename, String tag) {
		for (int i = 0; i < mListeners.size(); i++) {
			mListeners.get(i).notifyTagsUpdated(update, filename, tag);
		}
	}

	String getMimeType(String path) {
		String type = "";
		if (path.contains(".doc") || path.contains(".docx")) {
			type = "application/msword";
		} else if (path.contains(".pdf")) {
			type = "application/pdf";
		} else if (path.contains(".ppt") || path.contains(".pptx")) {
			type = "application/vnd.ms-powerpoint";
		} else if (path.contains(".xls") || path.contains(".xlsx")) {
			type = "application/vnd.ms-excel";
		} else if (path.contains(".zip") || path.contains(".rar")) {
			type = "application/x-wav";
		} else if (path.contains(".rtf")) {
			type = "application/rtf";
		} else if (path.contains(".wav") || path.contains(".mp3")) {
			type = "audio/x-wav";
		} else if (path.contains(".gif")) {
			type = "image/gif";
		} else if (path.contains(".jpg") || path.contains(".jpeg") || path.contains(".png")) {
			type = "image/jpeg";
		} else if (path.contains(".txt")) {
			type = "text/plain";
		} else if (path.contains(".3gp") || path.contains(".mpg") || path.contains(".mpeg") || path.contains(".mpe") || path.contains(".mp4") || path.contains(".avi")) {
			type = "video/*";
		}
		return type;
	}

	public interface TagsUpdatedListener {
		void notifyTagsUpdated(MainActivity.TAG_UPDATE update, String filename, String tag);
	}

}