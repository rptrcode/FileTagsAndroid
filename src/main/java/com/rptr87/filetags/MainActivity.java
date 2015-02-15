package com.rptr87.filetags;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rptr87.filetagger.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {
	static Context appContext;
	List<String> mDrawerListValues = new ArrayList<>();
	FileviewFragment mFileviewFragment = new FileviewFragment();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position == 0) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, mFileviewFragment).commit();
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				Fragment tagviewFragment = new TagviewFragment();
				Bundle args = new Bundle();
				String tag = (String) mDrawerList.getItemAtPosition(position);
				args.putString(TagviewFragment.ARG_TAG_NAME, tag);
				tagviewFragment.setArguments(args);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, tagviewFragment).commit();
				mDrawerList.setItemChecked(position, true);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	};
	private ArrayAdapter<String> mAdapter;
	private BidirectionalMap mBidirectionalMap = new BidirectionalMap();
	FileDetailviewFragment.TagsUpdatedListener tagsUpdatedListener = new FileDetailviewFragment.TagsUpdatedListener() {
		@Override
		public void notifyTagsUpdated(MainActivity.TAG_UPDATE update, String filename, String tag) {
			switch (update) {
				case ADD_TAG:
					mBidirectionalMap.put(filename, tag);
					if (!mDrawerListValues.contains(tag)) {
						mDrawerListValues.add(tag);
						mAdapter.notifyDataSetChanged();
					}

					break;
				case REMOVE_TAG:
					mBidirectionalMap.remove(filename, tag);
					if (mDrawerListValues.contains(tag)) {
						mDrawerListValues.remove(tag);
						mAdapter.notifyDataSetChanged();
					}
					break;
			}
		}
	};

	public static void alert(String string) {
		Toast.makeText(appContext, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerListValues);
		mDrawerList.setAdapter(mAdapter);
		mDrawerListValues.add("Folder View");
		mAdapter.notifyDataSetChanged();

		mDrawerLayout.openDrawer(Gravity.START);
		appContext = getApplicationContext();

		mDrawerList.setOnItemClickListener(itemClickListener);





		mFileviewFragment.mFileDetailviewFragment.addListener(tagsUpdatedListener);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, mFileviewFragment).commit();

	}

	@Override
	protected void onPause() {
		super.onPause();

		SaveRestoreMaps saveRestoreMaps = new SaveRestoreMaps();
		saveRestoreMaps.save(this, mBidirectionalMap);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mBidirectionalMap.clear();
		SaveRestoreMaps saveRestoreMaps = new SaveRestoreMaps();
		mBidirectionalMap = saveRestoreMaps.restore(this);
		HashMap<String, List<String>> filenameMap = mBidirectionalMap.getFilenameMap();

		for (HashMap.Entry<String, List<String>> entry : filenameMap.entrySet()) {
			List<String> tagList = entry.getValue();
			if (tagList != null) {
				mDrawerListValues.clear();
				mDrawerListValues.add("Folder View");
				mDrawerListValues.addAll(tagList);
				mAdapter.notifyDataSetChanged();
			}
		}
	}

	public List<String> getTagList(String filename) {
		return mBidirectionalMap.get(filename);
	}

	public List<String> getFilenameList(String tag) {
		return mBidirectionalMap.get(tag);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
					mDrawerLayout.closeDrawer(Gravity.START);
				} else {
					mDrawerLayout.openDrawer(Gravity.START);
				}
				return true;

			case KeyEvent.KEYCODE_BACK:


		}
		return super.onKeyDown(keyCode, e);
	}

	public enum TAG_UPDATE {
		ADD_TAG, REMOVE_TAG
	}

}
