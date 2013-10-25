package com.cskwhite.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.database.Cursor;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DBAdapter db = new DBAdapter(this);
		TextView txt = (TextView) findViewById(R.id.MainText);

		db.open();
		db.insertRow("Column1", "Column2", "Column3", "Column4");
		db.close();
		db.open();
		String row = "";
		Cursor c = db.getRow(3);
		c.moveToFirst();
		row = row + c.getInt(0) + ", " + c.getString(1) + ", " + c.getString(2)
				+ ", " + c.getString(3) + ", " + c.getString(4) + "\n";
		txt.setText(row);
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
