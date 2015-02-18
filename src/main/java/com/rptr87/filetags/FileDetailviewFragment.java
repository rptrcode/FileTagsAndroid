package com.rptr87.filetags;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
	private static String filePath;
	private View mRootView;
	private List<String> mListValues = new ArrayList<>();
	private ArrayAdapter<String> mAdpater;

	public interface TagsUpdatedListener {
		void notifyTagsUpdated(MainActivity.TAG_UPDATE update, String filename, String tag);
	}

	private View.OnClickListener openFileBtnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			FileLauncher fileLauncher = new FileLauncher(filePath);
			fileLauncher.launch();
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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.file_detail_layout, container, false);
		TextView textView = (TextView) mRootView.findViewById(R.id.textView);
		filePath = getArguments().getString(ARG_FILE_PATH);
		textView.setText(filePath);
		(mRootView.findViewById(R.id.addTagBtn)).setOnClickListener(addTagBtnClickListener);
		(mRootView.findViewById(R.id.openFileBtn)).setOnClickListener(openFileBtnClickListener);
		ListView tagListView = (ListView) mRootView.findViewById(R.id.listView);
		mAdpater = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, mListValues);
		tagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView textView = (TextView) view;
				String str = textView.getText().toString();
				Fragment tagviewFragment = new TagviewFragment();
				Bundle args = new Bundle();
				args.putString(TagviewFragment.ARG_TAG_NAME, str);
				tagviewFragment.setArguments(args);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, tagviewFragment).commit();
			}
		});
		tagListView.setAdapter(mAdpater);

		MainActivity mainActivity = (MainActivity) getActivity();
		List<String> tagList = mainActivity.getTagList(filePath);
		if (tagList != null) {
			mListValues.clear();
			mListValues.addAll(tagList);
			mAdpater.notifyDataSetChanged();
		} else {
			mListValues.clear();
			mAdpater.notifyDataSetChanged();
		}

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



}