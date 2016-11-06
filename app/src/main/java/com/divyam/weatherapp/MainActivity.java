package com.divyam.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ArrayList<Items.Item> itemArrayList;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            itemArrayList = Items.getItemArrayList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.weatherList);
        WeatherAdapter myAdapter = new WeatherAdapter(itemArrayList);

        //if(isConnectedtoNet()==true){

            listView.setAdapter(myAdapter);
        Toast.makeText(MainActivity.this, "My Adapter", Toast.LENGTH_SHORT).show();



        /*else{
            Toast.makeText(MainActivity.this, "Please Connect To internet", Toast.LENGTH_SHORT).show();
        }*/
    }
    public boolean isConnectedtoNet(){
        ConnectivityManager cMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cMgr.getActiveNetworkInfo();

        if(netInfo!=null && netInfo.isConnected()==true){
            return true;
        }

        return false;
    }

    class WeatherAdapter extends BaseAdapter{
        ArrayList<Items.Item> itemArrayList;

        class ItemHolder{
            ImageView image;
            TextView name;
            TextView country;
            TextView description;
            TextView minTemp;
            TextView maxTemp;
            TextView humidity;

        }

        public WeatherAdapter(ArrayList<Items.Item> itemArrayList) {
            this.itemArrayList = itemArrayList;
            Log.d("Divyam",itemArrayList.size()+"");
        }

        @Override
        public int getCount() {
            return itemArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Items.Item item = itemArrayList.get(position);

            LayoutInflater li = getLayoutInflater();
            ItemHolder itemHolder = new ItemHolder();

            if(convertView == null){
                convertView = li.inflate(R.layout.listitem,null);
                itemHolder = new ItemHolder();
                itemHolder.image = (ImageView) convertView.findViewById(R.id.img_1);
                itemHolder.country = (TextView) convertView.findViewById(R.id.city);
                itemHolder.description= (TextView) convertView.findViewById(R.id.desc);
                itemHolder.humidity = (TextView) convertView.findViewById(R.id.humidity);
                itemHolder.minTemp = (TextView) convertView.findViewById(R.id.min);
                itemHolder.maxTemp = (TextView) convertView.findViewById(R.id.max);
                convertView.setTag(itemHolder);
            }

            else{

                itemHolder = (ItemHolder) convertView.getTag();
            }


          /*  convertView = li.inflate(R.layout.listitem,null);
            itemHolder = new ItemHolder();
            itemHolder.image = (ImageView) convertView.findViewById(R.id.img_1);
            itemHolder.country = (TextView) convertView.findViewById(R.id.city);
            itemHolder.description= (TextView) convertView.findViewById(R.id.desc);
            itemHolder.humidity = (TextView) convertView.findViewById(R.id.humidity);
            itemHolder.minTemp = (TextView) convertView.findViewById(R.id.min);
            itemHolder.maxTemp = (TextView) convertView.findViewById(R.id.max);
            convertView.setTag(itemHolder);

*/

                        if (item == null) Log.e("Divyam","PostExecuteDone:itemHolder = null");

                        itemHolder.country.setText(item.country);
                        itemHolder.maxTemp.setText(item.tempMax);
                        itemHolder.minTemp.setText(item.tempMin);
                        itemHolder.description.setText(item.description);
                        itemHolder.humidity.setText(item.humidity);

            DownloadImageTask dit = new DownloadImageTask(itemHolder.image);
            dit.execute(item.imgUrl);

            return convertView;
        }
    }

    public class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
        public DownloadImageTask(ImageView imgView) {
            this.imgView = imgView;
        }

        ImageView imgView;

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlOfImage = params[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
               // e.printStackTrace();
            } catch (IOException e) {
                //e.printStackTrace();
            }

            return logo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgView.setImageBitmap(bitmap);
        }
    }


}
