package com.divyam.weatherapp;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by divyam on 6/7/16.
 */

public class NetFetchTask extends AsyncTask<String,Void,Items.Item>{

    private PostExecuteListener mListener;

    public interface PostExecuteListener{

        public void PostExecuteDone(Items.Item item);

    }

    public NetFetchTask(PostExecuteListener mListener) {
        super();
        this.mListener = mListener;
    }

    @Override
    protected Items.Item doInBackground(String... params) {
        Items.Item item=null;
        int responseCode;
        try{
            URL url = new URL(params[0]);
            HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();

            responseCode = myConnection.getResponseCode();

            String resp = getStringResponseFromInputStream(myConnection.getInputStream()) ;

            item = getItemFromStringResponse(resp);


        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } catch (JSONException e) {
            //e.printStackTrace();
        }


        return item;

    }



    @Override
    protected void onPostExecute(Items.Item item) {
        super.onPostExecute(item);
        mListener.PostExecuteDone(item);

    }
    private Items.Item getItemFromStringResponse(String str) throws JSONException {
        Items.Item item = new Items.Item();

        JSONObject root = new JSONObject(str);
        item.country = root.getJSONObject("city").getString("name");

        JSONArray jsonArray = root.getJSONArray("list");
        JSONObject obj0 = jsonArray.getJSONObject(0);
       // jsonArray = jsonArray.getJSONArray(1);
        String string = null;

       // item.tempMin = obj0.getJSONObject("main").getString("temp_min").substring(0,2)+" *C" ;
        item.tempMin = "Temp";

        item.tempMax = obj0.getJSONObject("main").getString("temp_max").substring(0,2)+" "+ (char)0x00B0+"C";

        item.humidity = obj0.getJSONObject("main").getString("humidity")+"%" ;

        JSONArray myArray = obj0.getJSONArray("weather");
        //
        item.description = myArray.getJSONObject(0).getString("description");

        String icon = myArray.getJSONObject(0).getString("icon");

        item.imgUrl = "http://openweathermap.org/img/w/"+icon+".png";

        return item;


    }
    private String getStringResponseFromInputStream(InputStream inputStream) throws IOException {
        String resp = null;
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = r.readLine();
        while(line!=null && !line.isEmpty()){
            sb.append(line);
            line = r.readLine();
        }
        return sb.toString();
    }
}
