/**
 * Copyright 2011 Mark Wyszomierski
 */
package com.foursquare.android.oauth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * https://developer.foursquare.com/docs/oauth.html
 * https://foursquare.com/oauth/
 * 
 * @date May 17, 2011
 * @author Mark Wyszomierski (markww@gmail.com)
 */
public class ActivityWebView2 extends Activity 
{
    private static final String TAG = "ActivityWebView2";
	
    /**
     * Get these values after registering your oauth app at: https://foursquare.com/oauth/
     */
    public static final String CALLBACK_URL = "https://www.p1.com/callback1";
//    public static final String CLIENT_ID = "WA3WC32ZFC0ZNUFEIZLYQBJSVKTVRBCS10SAR1A4GHQ3BNI5";
    public static final String CLIENT_ID = "K4FFGWINCKUXMSDRT1DFGUP02NKOTZAHIHCKCH100QSOUBYL";
    public static final String CLIENT_SECRET = "VG1STWGNPZSS0ZAHPCOOSAYMOLWQ3UDARR0BFPFOKFW3NU1Q";
    public static final String NEAR = "Piscataway,NJ";
	public static final Integer RADIUS = 800;
	public static final String QUERY = "donuts";
	

	
	public static JSONObject getJSONfromURL(String url){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try{
	        	jArray = new JSONObject(result);
	        	
	        	
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}

		return jArray;
	} 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        
        String url = 
        		"https://api.foursquare.com/v2/venues/explore" +
        		"?near=" + NEAR +
        		"&query=" + QUERY +
        		"&oauth_token=" + ActivityWebView.getAccessToken();
        
        // If authentication works, we'll get redirected to a url with a pattern like:  
        //
        //    http://YOUR_REGISTERED_REDIRECT_URI/#access_token=ACCESS_TOKEN
        //
        // We can override onPageStarted() in the web client and grab the token out.
        WebView webview = (WebView)findViewById(R.id.webview2);
        webview.getSettings().setJavaScriptEnabled(true);
        
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
/*            	
                String fragment = "response";
                int start = url.indexOf(fragment);
                if (start > -1) {
                    // You can use the accessToken for api calls now.
                    String venueResult = url.substring(start + fragment.length(), url.length());
        			
                    Log.v(TAG, "Venue result: [" + venueResult + "].");

                    
                    Toast.makeText(ActivityWebView2.this, "Venue: " + venueResult, Toast.LENGTH_SHORT).show();
               }
*/
            	
            Toast.makeText(ActivityWebView2.this, "Venue: " + url, Toast.LENGTH_SHORT).show();
                            	
            }
        });
        

        /*
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

      //Get the data (see above)
      JSONObject json = getJSONfromURL(url);

             try{
      	//Get the element that holds the earthquakes ( JSONArray )
      	JSONArray  venues = json.getJSONArray("venue");

            	       	//Loop the Array
              for(int i=0;i < venues.length();i++){						
                  
              	HashMap<String, String> map = new HashMap<String, String>();
              	JSONObject e = venues.getJSONObject(i);

              	map.put("id",  String.valueOf(i));
              	map.put("name", "Venue name:" + e.getString("name"));
              	map.put("address", "Address: " +  e.getString("address"));
              	mylist.add(map);
      	}
             }catch(JSONException e)        {
             	 Log.e("log_tag", "Error parsing data "+e.toString());
             }
*/
        
        webview.loadUrl(url);

/*
        for (int i = 0; i < mylist.size(); i++) {
        	HashMap<String, String> map = mylist.get(i);
        	
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
               String key = iterator.next().toString();
               String value = map.get(key).toString();
         
               System.out.println(key + " " + value);
            }
        }

        
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.activity_webview2,
                new String[] { "name", "address" },
                new int[] { R.id.item1, R.id.item2 });
        
        setListAdapter(adapter);


        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		@SuppressWarnings("unchecked")

        		Toast.makeText(Main.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_SHORT).show(); 

			}
		});
*/
        
    }
}