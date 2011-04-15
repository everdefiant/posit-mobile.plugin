/*
 * File: PhotoFind.java
 * 
 * Copyright (C) 2009 The Humanitarian FOSS Project (http://www.hfoss.org)
 * 
 * This file is part of POSIT, Portable Open Search and Identification Tool.
 *
 * POSIT is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) as published 
 * by the Free Software Foundation; either version 3.0 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU LGPL along with this program; 
 * if not visit http://www.gnu.org/licenses/lgpl.html.
 * 
 */

package org.hfoss.posit.android.photofindsorted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hfoss.posit.android.api.Find;
import org.hfoss.posit.android.provider.PositDbHelper;
import org.hfoss.posit.android.utilities.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Represents a specific find for a project, with a unique identifier
 * 
 */
public class PhotoFind extends Find{
	private static final String TAG = "Find";
	private long mId = -1;  	         // The Find's rowID (should be changed to DB ID)
	private String mGuid = "";       // BARCODE -- globally unique ID

	/**
	 * This constructor is used for a new Find
	 * @param context is the Activity
	 */
	public PhotoFind (Context context) {
		mId = -1;
	}
	
	/**
	 * This constructor is used for an existing Find.
	 * @param context is the Activity
	 * @param id is the Find's _id in the Sqlite DB
	 */
	public PhotoFind (Context context, long id) {
		this(context);
		mId = id;
		mGuid = "";
	}
	
	/**
	 * This constructor is used for an existing Find.
	 * @param context is the Activity
	 * @param guid is a globally unique identifier, used by the server
	 *   and other devices
	 */
	public PhotoFind (Context context, String guid) {
		this(context);
		mId = -1;
		mGuid = guid;
	}

	public void setGuid(String guid) {
		mGuid = guid;
	}
	/**
	 * 	getContent() returns the Find's <attr:value> pairs in a ContentValues array. 
	 *  This method assumes the Find object has been instantiated with a context
	 *  and an id.
	 *  @return A ContentValues with <key, value> pairs
	 */
	public ContentValues getContent() {
		ContentValues values = PositDbHelper.getInstance().fetchFindDataById(mId, null);
//		getMediaUrisFromDb(values);
		return values;
	}
	
	public Uri getFindDataUriByPosition(long findId, int position) {
		return PositDbHelper.getInstance().getFindDataUriByPosition(findId, position);
	}
	
	
	/**
	 * Deprecated
	 * Returns a hashmap of string key and value
	 * this is used primarily for sending and receiving over Http, which is why it 
	 * somewhat works as everything is sent as string eventually. 
	 * @return
	 */
	public HashMap<String,String> getContentMap(){
		Log.i(TAG, "getContentMap " + mId);
		return PositDbHelper.getInstance().fetchFindMapById(mId);
	}
	
	/**
	 * Returns a hashmap of key/value pairs represented as Strings.
	 * This is used primarily for sending and receiving over Http.
	 * @return
	 */
	public HashMap<String,String> getContentMapGuid(){
		Log.i(TAG, "getContentMapGuid " + mGuid);
		return PositDbHelper.getInstance().fetchFindMapByGuid(mGuid);
	}

	/** 
	 * Creates an entry for a Find in the DB. 
	 * Assumes that the context has been passed through a constructor.
	 * @param content contains the Find's attributes and values.  
	 * @param images list of contentvalues containing the image references to add
	 * @return whether the DB operation succeeds
	 */
	
	/**
	 * Inserts images for this find
	 * @param images
	 * @return
	 */
	public boolean insertFindDataEntriesToDB(List<ContentValues> images) {
		if (Utils.debug) Log.i(TAG, "insertImagesToDB, mId=" + mId + " guId=" + mGuid);
		if (images == null || images.size() == 0)
			return true; // Nothing to do
		if (mId != -1 && !mGuid.equals(""))
			return PositDbHelper.getInstance().addFindData(mId, mGuid, images);
		else 
			return false;
	}
	
	/**
	 * deletes the Find object form the DB, not including its photos
	 * Call deleteFindPhotos() to delete its photos.
	 * @return whether the DB operation was successful
	 */
	public boolean delete() {
		Log.i(TAG,"deleteing find #"+mId);
		return PositDbHelper.getInstance().deleteFind(mId);
	}
	
	/**
	 * deletes the photos associated with this find.
	 * @return
	 */
	public boolean deleteFindDataEntry() {
		Log.i(TAG,"deleteing find #"+mId);
		return PositDbHelper.getInstance().deleteFindDataEntriesById(mId);
	}

	/**
	 * @return the mId
	 */
	public long getId() {
		if (mId != -1) 
			return mId;
		else {
			mId = PositDbHelper.getInstance().getRowIdFromGuId(mGuid);
			return mId;
		}
	}
	
	/**
	 * @return the guId
	 */
	public String getguId() {
		return mGuid;
	}
	
	
	/**
	 * NOTE: This may cause a leak because the Cursor is not closed
	 * Get all images attached to this find
	 * @return the cursor that points to the images
	 */
	public Cursor getFindDataEntriesCursor() {
		Log.i(TAG,"GetImages find id = "+mId);
		return PositDbHelper.getInstance().getFindDataEntriesCursor(mId);
	}
	
	public ArrayList<ContentValues> getFindDataEntriesList() {
		return PositDbHelper.getInstance().getFindDataEntriesList(mId);
	}

	
	/**
	 * @return whether or not there are images attached to this find
	 */
	public boolean hasDataEntries(){
		return getFindDataEntriesCursor().getCount() > 0;
	}

	public boolean deleteFindDataEntriesByPosition(int position) {
		return false;
		//return mDbHelper.deletePhotoByPosition(mId, position);
	}
	

	/**
	 * Directly sets the Find as either Synced or not.
	 * @param status
	 */
	public void setSyncStatus(boolean status){
		ContentValues content = new ContentValues();
		content.put(PositDbHelper.FINDS_SYNCED, status);
		if (mId != -1)
			//mDbHelper.updateFind(mId, content);
			PositDbHelper.getInstance().updateFind(mId, content);
		else 
			//mDbHelper.updateFind(mGuid, content,null); //photos=null
			PositDbHelper.getInstance().updateFind(mGuid, content, null);
//		updateToDB(content);
	}
	
	// TODO: Test that this method works with GUIDs
	public int getRevision() {
		ContentValues value = PositDbHelper.getInstance().fetchFindDataById(mId, new String[] {PositDbHelper.FINDS_REVISION});
		return value.getAsInteger(PositDbHelper.FINDS_REVISION);
	}
	
	/**
	 * Used for adhoc finds.  Tests whether find already exists
	 * @param guid
	 * @return
	 */
	public boolean exists(String guid) {
		return PositDbHelper.getInstance().containsFind(guid);
	}
	
	/**
	 * Tests whether the Find is synced.  This method should work with
	 * either GUIDs (Find from a server) or row iDs (Finds from the phone).
	 * If neither is set, it returns false.
	 * @return
	 */
	public boolean isSynced() {
		ContentValues value=null;
		Log.i(TAG, "isSynced mId = " + mId + " guId = " + mGuid);
		if (mId != -1) {
			value = PositDbHelper.getInstance().fetchFindDataById(mId, new String[] { PositDbHelper.FINDS_SYNCED});
			return value.getAsInteger(PositDbHelper.FINDS_SYNCED)==PositDbHelper.FIND_IS_SYNCED;
		} else if (!mGuid.equals("")){
			value = PositDbHelper.getInstance().fetchFindDataByGuId(mGuid, new String[] { PositDbHelper.FINDS_SYNCED});
			return value.getAsInteger(PositDbHelper.FINDS_SYNCED)==PositDbHelper.FIND_IS_SYNCED;
		} else 
			return false;
	}	
}