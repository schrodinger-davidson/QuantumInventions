package com.example.andy.myapplication.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



// Helper class for checking network connections

public  class NetworkConnection {

	private Context mContext;
	private ConnectivityManager cm;
	private AppCompatActivity mActivity;

	public NetworkConnection(Context context, AppCompatActivity mActivity) {
		// TODO Auto-generated constructor stub
		mContext = context;
		this.mActivity = mActivity;
		cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public boolean isNetworkConnected() {
		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public boolean isNetworkConnectingOrConnected() {

		NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}


	// Check Internet Connectivity and show toast
	public boolean isActive(){

		if (isNetworkConnected()){
			return true;
		} else if (isNetworkConnectingOrConnected()) {
			//showNoInternetDialog("Connecting...try again shortly");
			Toast.makeText(
					mContext,
					"Connecting...try again shortly",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext,
					"No internet connection", Toast.LENGTH_LONG)
					.show();
			//showNoInternetDialog("No internet connection!");
		}
		return false;
	}



}
