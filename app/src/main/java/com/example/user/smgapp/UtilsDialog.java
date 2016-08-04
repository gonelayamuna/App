package com.example.user.smgapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class UtilsDialog {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String convertStreamToString(InputStream inputStream)
			throws IOException {
		if (inputStream != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inputStream.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public void dialog(Context c, String text) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);

		alertDialogBuilder.setTitle("Alert!");

		alertDialogBuilder.setMessage(text);

		// set positive button: Yes message

		alertDialogBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						// go to a new activity of the app

						dialog.dismiss();
					/*	Intent intent = new Intent(getApplicationContext(), Login.class);
						startActivity(intent);
*/

					}

				});

		AlertDialog alertDialog = alertDialogBuilder.create();

		// show alert

		alertDialog.show();
	}

}