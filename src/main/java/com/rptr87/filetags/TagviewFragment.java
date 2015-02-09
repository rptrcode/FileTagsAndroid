package com.rptr87.filetags;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rptr87.filetagger.R;

import java.util.ArrayList;
import java.util.List;

public class TagviewFragment extends Fragment {
	public static final String ARG_TAG_NAME = "tag name";
	String mTagname;
	ListView mTagList;
	ArrayAdapter<String> mAdapter;
	List<String> mTagListValues = new ArrayList<>();

	public TagviewFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tagview_layout, container, false);
		mTagname = getArguments().getString(ARG_TAG_NAME);

		TextView textView = (TextView) rootView.findViewById(R.id.tagnameView);
		textView.setText(mTagname);
		mTagList = (ListView) rootView.findViewById(R.id.taglistView);
		MainActivity mainActivity = (MainActivity) getActivity();
		List<String> list = mainActivity.getFilenameList(mTagname);
		if (list != null)
			mTagListValues.addAll(list);
		mAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, mTagListValues);
		mTagList.setAdapter(mAdapter);

		return rootView;
	}
}
