package com.rptr87.filetags;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

public class SaveRestoreMaps {
	private static final String FILE_NAME_MAP = "filenameMap.ser";
	private static final String TAG_NAME_MAP = "tagnameMap.ser";

	public void save(MainActivity activity, BidirectionalMap bimap) {
		FileOutputStream outputStream;
		try {
			outputStream = activity.openFileOutput(FILE_NAME_MAP, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(bimap.getFilenameMap());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			outputStream = activity.openFileOutput(TAG_NAME_MAP, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(bimap.getTagnameMap());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public BidirectionalMap restore(MainActivity activity) {
		BidirectionalMap bimap = new BidirectionalMap();

		try {
			FileInputStream streamIn = activity.openFileInput(FILE_NAME_MAP);
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			HashMap<String, List<String>> filenameMap = (HashMap<String, List<String>>) objectinputstream.readObject();
			bimap.setFilenameMap(filenameMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FileInputStream streamIn = activity.openFileInput(TAG_NAME_MAP);
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			HashMap<String, List<String>> tagnameMap = (HashMap<String, List<String>>) objectinputstream.readObject();
			bimap.setTagnameMap(tagnameMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bimap;
	}
}
