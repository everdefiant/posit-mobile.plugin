package org.hfoss.posit.android.experimental.functionplugins;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CameraActivity extends Activity {
	
	//TODO:What do I do with the base64 string??
	//TODO:How do I sync the images to and from the server??

	public static final String PREFERENCES_IMAGE = "Image";
	static final int TAKE_CAMERA_REQUEST = 1000;
	private String img_str = null; //stores base64 string of the image
	
	private LinearLayout linear;
	private TextView text; 
	private Button btnPic;
	private Button btnCont;
	private ImageView photo;
	
//    private String display_name = "test123"; //TODO: replace this value with the find's GUID. Pass it using intents when starting this activity.
		
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    linear = new LinearLayout(this);
	    linear.setOrientation(LinearLayout.VERTICAL);
	    
	    photo = new ImageView(this);
	    photo.setVisibility(View.INVISIBLE); //this is just to change the button text
	    linear.addView(photo);
/*
	    //retrieve the photo, display it, and also encode to base64 string
	    Cursor managedCursor = managedQuery(Images.Media.EXTERNAL_CONTENT_URI, null, "_DISPLAY_NAME ="+"'"+display_name+"'", null, null);
	    if(managedCursor.moveToFirst()){
	    	Uri photoUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, managedCursor.getInt(managedCursor.getColumnIndex("_ID")));
	    	try {
	    		Bitmap cameraPic = Media.getBitmap(getContentResolver(), photoUri);
				photo.setImageBitmap(cameraPic);
			    photo.setVisibility(View.VISIBLE);
			    
				//encode to base64 string
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				cameraPic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] b = baos.toByteArray();
				img_str = Base64.encodeToString(b, Base64.DEFAULT); //TODO: it's encoded, now what?
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 	
	    }
*/	    
	    btnPic = new Button(this);
	    if(photo.getVisibility() == View.INVISIBLE){
		    btnPic.setText("Take Picture");
	    }
	    else{
		    btnPic.setText("Retake Picture");
	    }
	    linear.addView(btnPic);
	    
	    btnPic.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		//launch the camera
	    		Intent pictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	    		//get the full picture
	    		pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
	    		//how to handle the picture taken
	    		startActivityForResult(pictureIntent, TAKE_CAMERA_REQUEST);
	    	}
	    });
	    
	    btnCont = new Button(this);
	    btnCont.setText("Continue");
	    linear.addView(btnCont);
	    
	    btnCont.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		//pass base64 string to calling function
				Intent intent=new Intent();  
				intent.putExtra("Photo", img_str);
				setResult(RESULT_OK, intent);
	    		finish();
	    	}
	    });
	    
	    setContentView(linear);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case TAKE_CAMERA_REQUEST:
			if(resultCode == Activity.RESULT_CANCELED){
			}
			else if(resultCode == Activity.RESULT_OK){
				//handle photo taken
				Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
				
				//encode to base64 string
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				cameraPic.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] b = baos.toByteArray();
				img_str = Base64.encodeToString(b, Base64.DEFAULT); //TODO: it's encoded, now what?
/*				
				//test decoding it back into an image
				byte[] c = Base64.decode(img_str, Base64.DEFAULT);
			    Bitmap bmp = BitmapFactory.decodeByteArray(c, 0, c.length);
*/			    
			    photo.setImageBitmap(cameraPic);//display the retrieved image
			    photo.setVisibility(View.VISIBLE);
			    btnPic.setText("Retake Picture");//we now have an image
/*			    
			    //in order to replace an image, I delete an existing row and insert a new row.
			    //delete previous photo if it exists
			    Cursor managedCursor = managedQuery(Images.Media.EXTERNAL_CONTENT_URI, null, "_DISPLAY_NAME ="+"'"+display_name+"'", null, null);
			    if(managedCursor.moveToFirst()){
			    	Uri photoUri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, managedCursor.getInt(managedCursor.getColumnIndex("_ID")));
			    	getContentResolver().delete(photoUri, null, null);
			    }
			    
			    //insert a new one
			    ContentValues values = new ContentValues(2);
			    values.put(Media.DISPLAY_NAME, display_name);
			    values.put(Media.MIME_TYPE, "image/jpeg");

			    Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
			    
			    try {
			        OutputStream outStream = getContentResolver().openOutputStream(uri);
			        bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			        outStream.close();
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
*/			    
			}
			break;
		}
	}
}