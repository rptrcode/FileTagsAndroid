package com.rptr87.filetags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BidirectionalMap {
	private HashMap<String, List<String>> filenameMap = new HashMap<>();
	private HashMap<String, List<String>> tagnameMap = new HashMap<>();

	BidirectionalMap() {
	}

	public void put(String filename, String tagname) {

		List<String> tagList = filenameMap.get(filename);
		if (tagList == null) {
			tagList = new ArrayList<>();
		}
		tagList.add(tagname);
		filenameMap.put(filename, tagList);

		List<String> filenameList = tagnameMap.get(tagname);
		if (filenameList == null) {
			filenameList = new ArrayList<>();
		}
		filenameList.add(filename);
		tagnameMap.put(tagname, filenameList);

	}

	public void remove(String filename, String tagname) {
		List<String> tagList = filenameMap.get(filename);
		if (tagList != null) {
			tagList.remove(tagname);
		}
		List<String> filenameList = tagnameMap.get(tagname);
		if (filenameList != null) {
			filenameList.remove(filename);
		}
	}

	public List<String> get(String str) {
		List<String> list = filenameMap.get(str);
		if (list != null) {
			return list;
		} else {
			list = tagnameMap.get(str);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	HashMap<String, List<String>> getFilenameMap() {
		return filenameMap;
	}

	void setFilenameMap(HashMap<String, List<String>> map) {
		filenameMap.putAll(map);
	}

	HashMap<String, List<String>> getTagnameMap() {
		return tagnameMap;
	}

	void setTagnameMap(HashMap<String, List<String>> map) {
		tagnameMap.putAll(map);
	}

	void clear() {
		filenameMap.clear();
		tagnameMap.clear();
	}
}
