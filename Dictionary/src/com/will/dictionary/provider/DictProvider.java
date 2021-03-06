package com.will.dictionary.provider;

import com.will.dictionary.MyDatabaseHelper;
import com.will.dictionary.Words;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DictProvider extends ContentProvider {
	
	private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int WORDS = 1;
	private static final int WORD = 2;
	
	private MyDatabaseHelper dbHelper;
	
	static {
		matcher.addURI(Words.AUTHORITY, "words", WORDS);
		matcher.addURI(Words.AUTHORITY, "word", WORD);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int num = 0;
		
		switch (matcher.match(uri)) {
		case WORDS:
			num = db.delete("dict", selection, selectionArgs);
			break;
		case WORD:
			long id = ContentUris.parseId(uri);
			String whereClause = Words.Word._ID + "=" + id;
			if (selection != null && !selection.equals("")) {
				whereClause = whereClause + " and " + selection;
			}
			num = db.delete("dict", whereClause, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return num;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (matcher.match(uri)) {
		case WORDS:
			return "vnd.android.cursor.dir/com.will.dict";
		case WORD:
			return "vnd.android.cursor.item/com.will.dict";
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		switch (matcher.match(uri)) {
		case WORDS:
			long rowId = db.insert("dict", null, values);
			if (rowId > 0) {
				Uri wordUri = ContentUris.withAppendedId(uri, rowId);
				getContext().getContentResolver().notifyChange(wordUri, null);
				return wordUri;
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
		return null;
	}

	// It will be recall when DictProvider is called to create an instance at the first time.
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper = new MyDatabaseHelper(this.getContext(), "myDict.db3", 1);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		switch (matcher.match(uri)) {
		case WORDS:
			return db.query("dict", projection, selection, selectionArgs, null, null, sortOrder);
		case WORD:
			long id = ContentUris.parseId(uri);
			String whereClause = Words.Word._ID + "=" + id;
			if (selection != null && !selection.equals("")) {
				whereClause = whereClause + " and " + selection;
			}
			return db.query("dict", projection, whereClause, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		int num = 0;
		
		switch (matcher.match(uri)) {
		case WORDS:
			num = db.update("dict", values, selection, selectionArgs);
			break;
		case WORD:
			long id = ContentUris.parseId(uri);
			String whereClause = Words.Word._ID + "=" + id;
			if (selection != null && !selection.equals("")) {
				whereClause = whereClause + " and " + selection;
			}
			num = db.update("dict", values, whereClause, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return num;
	}

}
