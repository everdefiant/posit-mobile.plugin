package org.hfoss.posit.android.plugin.csv;

import org.hfoss.posit.android.R;
import org.hfoss.posit.android.api.Find;
import org.hfoss.posit.android.api.fragment.FindFragment;

import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CsvFindFragment extends FindFragment {

	private static final String TAG = "CsvFindFragment";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivtyCreated()");
		super.onActivityCreated(savedInstanceState);
		
		setHasOptionsMenu(false);
		
		if (getAction().equals(CsvListFindsFragment.ACTION_CSV_FINDS)) {
			int id = getArguments().getInt(CsvListFindsFragment.ACTION_CSV_FINDS, 0);
			displayContentInView(CsvListFindsFragment.getFind(id));
		}
	}
	
	@Override
	protected void displayContentInView(Find find) {
		super.displayContentInView(find);	
		
		
		View v = getView().findViewById(R.id.csvAddLinearLayout);
		
		TextView newTV = new TextView(getActivity());
		newTV.setText((((CsvFind)find).getClosing()));
		newTV.setPadding(4, 8, 0, 0);
//		newTV.setId(5);
		newTV.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.WRAP_CONTENT));
		((LinearLayout)v).addView(newTV);
		
		newTV = new TextView(getActivity());
		newTV.setText((((CsvFind)find).getRates()));
		newTV.setPadding(4, 8, 0, 0);
//		newTV.setId(5);
		newTV.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.WRAP_CONTENT));
		((LinearLayout)v).addView(newTV);
		
		newTV = new TextView(getActivity());
		String specials = (((CsvFind)find).getSpecials()).trim();
		if (specials.length() == 0)
			newTV.setText("");
		else 
			newTV.setText("Specials: " + specials);

		newTV.setPadding(4, 8, 0, 0);
//		newTV.setId(5);
		newTV.setLayoutParams(new LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.WRAP_CONTENT));
		((LinearLayout)v).addView(newTV);
		
		TextView tv = (TextView)getView().findViewById(R.id.nameValueText);
		tv.setText(((CsvFind)find).getName());
		
		tv = (TextView)getView().findViewById(R.id.descriptionValueText);
		tv.setText(((CsvFind)find).getFullAddress());
        Linkify.addLinks( tv, Linkify.MAP_ADDRESSES);	
		
		tv = (TextView)getView().findViewById(R.id.latitudeValueTextView);
		tv.setText(""+ ((CsvFind)find).getLatitude());	

		tv = (TextView)getView().findViewById(R.id.longitudeValueTextView);
		tv.setText(""+ ((CsvFind)find).getLongitude());
		
		tv = (TextView)getView().findViewById(R.id.guidValueTextView);
		tv.setText(""+ ((CsvFind)find).getGuid());
		
		tv = (TextView)getView().findViewById( R.id.urlValueTextView );
        // make sure that setText call comes BEFORE Linkify.addLinks call
        tv.setText(((CsvFind)find).getUrl());
        Linkify.addLinks( tv, Linkify.WEB_URLS );
		
		tv = (TextView)getView().findViewById( R.id.phoneValueTextView );
        tv.setText(((CsvFind)find).getPhone());
        Linkify.addLinks( tv, Linkify.PHONE_NUMBERS );	
	}
}
