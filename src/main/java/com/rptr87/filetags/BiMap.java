package com.rptr87.filetags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by puttaraju on 28-05-2015.
 */
public class BiMap {
	Map<String, List<String>> filenameMap = new HashMap<>();
	Map<String, List<String>> tagnameMap = new HashMap<>();

	BiMap() {
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

}
