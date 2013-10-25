package com.cskwhite.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	static final String KEY_ROWID = "_id";
	static final String KEY_COLUMN1 = "column1";
	static final String KEY_COLUMN2 = "column2";
	static final String KEY_COLUMN3 = "column3";
	static final String KEY_COLUMN4 = "column4";
	static final String TAG = "DBAdapter";

	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "MyTable";
	static final int DATABASE_VERSION = 1;

	static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE
			+ " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_COLUMN1 + " TEXT, " + KEY_COLUMN2 + " TEXT, " + KEY_COLUMN3
			+ " TEXT, " + KEY_COLUMN4 + " TEXT);";

	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long insertRow(String cm1, String cm2, String cm3, String cm4) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_COLUMN1, cm1);
		initialValues.put(KEY_COLUMN2, cm2);
		initialValues.put(KEY_COLUMN3, cm3);
		initialValues.put(KEY_COLUMN4, cm4);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteRow(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	public Cursor getAllRows() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_COLUMN1,
				KEY_COLUMN2, KEY_COLUMN3, KEY_COLUMN4 }, null, null, null,
				null, null);
	}

	public Cursor getRow(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE,
				new String[] { KEY_ROWID, KEY_COLUMN1, KEY_COLUMN2,
						KEY_COLUMN3, KEY_COLUMN4 }, KEY_ROWID + "=" + rowId,
						null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public boolean updateRow(long rowId, String cm1, String cm2, String cm3,
			String cm4) {
		ContentValues args = new ContentValues();
		args.put(KEY_COLUMN1, cm1);
		args.put(KEY_COLUMN2, cm2);
		args.put(KEY_COLUMN3, cm3);
		args.put(KEY_COLUMN4, cm4);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
