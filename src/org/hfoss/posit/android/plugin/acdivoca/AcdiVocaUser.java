/*
 * File: AcdiVocaDbUser.java
 * 
 * Copyright (C) 2011 The Humanitarian FOSS Project (http://www.hfoss.org)
 * 
 * This file is part of the ACDI/VOCA plugin for POSIT, Portable Open Search 
 * and Identification Tool.
 *
 * This plugin is free software; you can redistribute it and/or modify
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

package org.hfoss.posit.android.plugin.acdivoca;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hfoss.posit.android.R;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * The User object for creating and persisting data for the user table in the database.
 */
public class AcdiVocaUser {
	public static final String TAG = "AcdiVocaUser";
	
	public enum UserType {SUPER, ADMIN, AGRI, USER};

	/**
	 * Default user accounts.
	 */
	public static final String USER_DEFAULT_NAME = "b";      // For testing purposes
	public static final String USER_DEFAULT_PASSWORD = "b";
	public static final String AGRI_DEFAULT_NAME = "a";      // For testing purposes
	public static final String AGRI_DEFAULT_PASSWORD = "a";
	public static final String USER_DEFAULT_NAME_2 = "auxil2";      // For testing purposes
	public static final String USER_DEFAULT_PASSWORD_2 = "acdivoca";	
	public static final String USER_DEFAULT_NAME_3 = "auxil3";      // For testing purposes
	public static final String USER_DEFAULT_PASSWORD_3 = "acdivoca";	
	public static final String USER_DEFAULT_NAME_4 = "auxil4";      // For testing purposes
	public static final String USER_DEFAULT_PASSWORD_4 = "acdivoca";		
	public static final String ADMIN_USER_NAME = "r";
	public static final String ADMIN_USER_PASSWORD = "a";
	public static final String SUPER_USER_NAME = "s";
	public static final String SUPER_USER_PASSWORD = "a";	
	public static final String USER_TYPE_STRING = "UserType";
	public static final String USER_TYPE_KEY= "UserLoginType";

	/**
	 * The fields annotated with @DatabaseField are persisted to the Db.
	 */
	// id is generated by the database and set on the object automagically
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField(uniqueIndex = true)
	String name;
	@DatabaseField
	String password;
	@DatabaseField
	int type;

	AcdiVocaUser() {
		// needed by ormlite
	}

	public AcdiVocaUser(String name, String password, int type) {
		this.name = name;
		this.password = password;
		this.type = type;
	}
	
	/**
	 * Creates the table associated with this object.  And creates the default
	 * users. The table's name is 'acdivocauser', same as the class name. 
	 * @param connectionSource
	 * @param avUserDao
	 */
	public static void init(ConnectionSource connectionSource, Dao<AcdiVocaUser, Integer> avUserDao) {
		try {
			TableUtils.createTable(connectionSource, AcdiVocaUser.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create some Users
		if (!insertUser(avUserDao, SUPER_USER_NAME, SUPER_USER_PASSWORD, UserType.SUPER))
			Log.e(TAG, "Error adding user = " + SUPER_USER_NAME);
		if (!insertUser(avUserDao, ADMIN_USER_NAME, ADMIN_USER_PASSWORD, UserType.ADMIN))
			Log.e(TAG, "Error adding user = " + ADMIN_USER_NAME);
		if (!insertUser(avUserDao, USER_DEFAULT_NAME, USER_DEFAULT_PASSWORD, UserType.USER))
			Log.e(TAG, "Error adding user = " + USER_DEFAULT_NAME);
		if (!insertUser(avUserDao, USER_DEFAULT_NAME_2, USER_DEFAULT_PASSWORD_2, UserType.USER))
			Log.e(TAG, "Error adding user = " + USER_DEFAULT_NAME_2);
		if (!insertUser(avUserDao, USER_DEFAULT_NAME_3, USER_DEFAULT_PASSWORD_3, UserType.USER))
			Log.e(TAG, "Error adding user = " + USER_DEFAULT_NAME_3);
		if (!insertUser(avUserDao, USER_DEFAULT_NAME_4, USER_DEFAULT_PASSWORD_4, UserType.USER))
			Log.e(TAG, "Error adding user = " + USER_DEFAULT_NAME_4);
		if (!insertUser(avUserDao, AGRI_DEFAULT_NAME, AGRI_DEFAULT_PASSWORD, UserType.AGRI))
			Log.e(TAG, "Error adding user = " + USER_DEFAULT_NAME);
		
		
		Log.i(TAG, "Created User Accounts");
//		displayUsers(avUserDao);
	}
	
	/**
	 * Inserts new users into the database given the username, password, and type.
	 * Uses ORMlite's DAO. 
	 * @param username
	 * @param password
	 * @param usertype  one of SUPER, ADMIN, USER
	 * @return
	 */
	private static boolean insertUser(Dao<AcdiVocaUser, Integer> avUserDao, String username, String password, UserType usertype) {
		Log.i(TAG, "insertUser " + username + " of type = " + usertype);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", username);
		
		// Query for the username in the user table
		List<AcdiVocaUser> list = null;
		try {
			list = avUserDao.queryForFieldValues(map);
		} catch (SQLException e) {
			Log.e(TAG, "SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
		
		// If the user doesn't already exist, insert it.
		if (list.size() == 0) {
			try {
				avUserDao.create(new AcdiVocaUser(username, password, usertype.ordinal()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return true;
		}
		return false;
	}
	
	/**
	 * Utility method to display the list of users in the acdivocauser table.
	 */
	private static void displayUsers(Dao<AcdiVocaUser, Integer> avUserDao) {
		Log.i(TAG, "Displaying user table");
		
		List<AcdiVocaUser> list = null;
		try {
			list = avUserDao.queryForAll();
		} catch (SQLException e) {
			Log.e(TAG, "SQL Exception " + e.getMessage());
			e.printStackTrace();
		}

		for (AcdiVocaUser item : list) {
			Log.i(TAG, item.toString());
		}
	}
	
	/**
	 * Returns true iff a row containing username and password is found
	 * @param username
	 * @param password
	 * @param userType is an enum that defines whether this is a regular or super user.
	 * @return Returns the user's type or -1 if authentication fails.
	 */
	public static int authenicateUser(Context context, String username, String password, UserType userType) {
		Log.i(TAG, "Authenticating user = " + username + " Access type = " + userType);		
		
		AcdiVocaDbHelper db = new AcdiVocaDbHelper(context);
		
		Dao<AcdiVocaUser, Integer> avUserDao = null;
		try {
			avUserDao = db.getAvUserDao();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		int result = 0;
		if (userType.equals(UserType.ADMIN)) {
			if (! ((username.equals(ADMIN_USER_NAME) &&  password.equals(ADMIN_USER_PASSWORD)) 
				|| (username.equals(SUPER_USER_NAME) && password.equals(SUPER_USER_PASSWORD)) )) {
				Log.i(TAG, "Sorry you must be ADMIN USER to do this.");
				Toast.makeText(context, context.getString(R.string.toast_adminuser), Toast.LENGTH_SHORT);
				result = -1;
			}
		} else if (userType.equals(UserType.SUPER)) {
			if (!username.equals(SUPER_USER_NAME) ||  !password.equals(SUPER_USER_PASSWORD)) {
				Log.i(TAG, "Sorry you must be SUPER USER to do this.");
				Toast.makeText(context, context.getString(R.string.toast_superuser), Toast.LENGTH_SHORT);
				result = -1;
			}
		} 
		if (result != -1) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", username);
			map.put("password", password);
			
			List<AcdiVocaUser> list = null;
			try {
				list = avUserDao.queryForFieldValues(map);
			} catch (SQLException e) {
				Log.e(TAG, "SQL Exception " + e.getMessage());
				e.printStackTrace();
			}
			Log.i(TAG, "List size = " + list.size());
			if (list.size() != 1) 
				result =  -1;
			else {
				AcdiVocaUser user = list.get(0);
				result = user.type;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append(", ").append("name=").append(name);
		sb.append(", ").append("password=").append(password);
		sb.append(", ").append("type=").append(type);
		return sb.toString();
	}
}
