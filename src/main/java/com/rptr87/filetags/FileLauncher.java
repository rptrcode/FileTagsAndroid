package com.rptr87.filetags;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class FileLauncher {
	private Intent mViewIntent;

	public FileLauncher(String filePath) {
		mViewIntent = new Intent();
		mViewIntent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(filePath);
		mViewIntent.setDataAndType(Uri.fromFile(file), getMimeType(filePath.toLowerCase()));
		mViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	public void launch() {
		try {
			MainActivity.appContext.startActivity(mViewIntent);
		} catch (ActivityNotFoundException e) {
			MainActivity.alert("No handler for this type ");
		}
	}

	private String getMimeType(String path) {
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
			type = "audio/*";
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


}
