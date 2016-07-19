package com.divyam.weatherapp;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by divyam on 6/7/16.
 */

public class Items{
    static ArrayList<Item> itemArrayList ;
    static ArrayList<String> linkArrayList;

    public static class Item {
        String imgUrl;
        String name;
        String country;
        String description;
        String tempMin;
        String tempMax;
        String humidity;
    }

    public static ArrayList<Items.Item> getItemArrayList() throws ExecutionException, InterruptedException {

        final ArrayList<String> urlList = getLinkArrayList();
        Log.d("size",urlList.size()+"");
        final ArrayList<Item> please = new ArrayList<>();

        for(int i = 0; i<urlList.size();i++){

            NetFetchTask nft = new NetFetchTask(new NetFetchTask.PostExecuteListener() {
                @Override
                public void PostExecuteDone(Item present) {
                            // please.add(present);
                    //Log.d("pleasesize",please.size()+"");

                }
            });
            Item present = nft.execute(urlList.get(i)).get();
            please.add(present);
        }

        Log.d("pleasesize",please.size()+"");
        return please;
    }


    public static ArrayList<String> getLinkArrayList(){


        linkArrayList = new ArrayList<>();

        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=2950159&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=1275339&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=5332748&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=1277333&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=1259229&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=5128638&APPID=2e22c7366dbeb9edae0589d551bf9197");
        linkArrayList.add("http://api.openweathermap.org/data/2.5/forecast/city?id=1275004&APPID=2e22c7366dbeb9edae0589d551bf9197");

        return linkArrayList;
    }


}
