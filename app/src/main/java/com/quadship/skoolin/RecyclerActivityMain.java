package com.quadship.skoolin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerActivityMain extends AppCompatActivity {

//    private final String android_version_names[] = {
//            "Donut",
//            "Eclair",
//            "Froyo",
//            "Gingerbread",
//            "Honeycomb",
//            "Ice Cream Sandwich",
//            "Jelly Bean",
//            "KitKat",
//            "Lollipop",
//            "Marshmallow"
//    };

//    private final String android_image_urls[] = {
//            "http://api.learn2crack.com/android/images/donut.png",
//            "http://api.learn2crack.com/android/images/eclair.png",
//            "http://api.learn2crack.com/android/images/froyo.png",
//            "http://api.learn2crack.com/android/images/ginger.png",
//            "http://api.learn2crack.com/android/images/honey.png",
//            "http://api.learn2crack.com/android/images/icecream.png",
//            "http://api.learn2crack.com/android/images/jellybean.png",
//            "http://api.learn2crack.com/android/images/kitkat.png",
//            "http://api.learn2crack.com/android/images/lollipop.png",
//            "http://api.learn2crack.com/android/images/marshmallow.png"
//    };
    ArrayList<UserData> userDataArray;
    final static String REGISTER_URL = "http://www.etimesweb.com/bbmpin/BBMPIN.aspx?postdata=MF&allp=1";
    SharedPreferences prefs;
    //ListView pinListView;
    Button btnLoadMore;
    RecyclerDataAdapter dataAdapObj;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity_main);

        initViews();
    }

    private void initViews(){
         recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

       // ArrayList androidVersions = prepareData();
        //RecyclerDataAdapter adapter = new RecyclerDataAdapter(getApplicationContext(),androidVersions);
        //recyclerView.setAdapter(adapter);

        userDataArray = new ArrayList<UserData>();
        //		myResponseObj = BbmListActivity.this;
        registerForContextMenu(recyclerView);
        prefs = getSharedPreferences("BBMPIN_FINDER", 0);
        // Creating a button - Load More
        btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");

        prefs.edit().putString("SERVERERROR", "NO").commit();

        // Adding button to listview at footer
       // recyclerView.
                fetchData fdata = new fetchData();
        fdata.execute(REGISTER_URL);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData fdata = new fetchData();
                fdata.execute(REGISTER_URL);
            }
        });

    }
//    private ArrayList prepareData(){
//
//        ArrayList android_version = new ArrayList<>();
//        for(int i=0;i<android_version_names.length;i++){
//            AndroidVersion androidVersion = new AndroidVersion();
//            androidVersion.setAndroid_version_name(android_version_names[i]);
//            androidVersion.setAndroid_image_url(android_image_urls[i]);
//            android_version.add(androidVersion);
//            android_version.add(androidVersion);
//        }
//        return android_version;
//    }

    private List<UserData> xmlParseData(InputStream inputStream2) {

        if (userDataArray == null) {
            userDataArray = new ArrayList<UserData>();
        }
        UserData userDataObject = null;
        String text = null;

        XmlPullParserFactory factory = null;
        XmlPullParser xmlparser = null;
        int eventType;
        String tagname;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xmlparser = factory.newPullParser();

            xmlparser.setInput(new InputStreamReader(inputStream2));
            eventType = xmlparser.getEventType();
            System.out.println("xml parsing going to start:)");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                tagname = xmlparser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Table")) {
                            // create a new instance of employee
                            userDataObject = new UserData();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlparser.getText().trim();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("Table")) {
                            // add employee object to list
                            userDataArray.add(userDataObject);
                        } else if (tagname.equalsIgnoreCase("BBMPIN")) {
                            userDataObject.setUserBbmPin(text);
                        } else if (tagname.equalsIgnoreCase("Name")) {
                            userDataObject.setUserName(text);
                        } else if (tagname.equalsIgnoreCase("Sex")) {
                            userDataObject.setUserGender(text);
                        } else if (tagname.equalsIgnoreCase("country")) {
                            userDataObject.setUserCountry(text);
                        } else if (tagname.equalsIgnoreCase("city")) {
                            userDataObject.setUserCity(text);
                        } else if (tagname.equalsIgnoreCase("age")) {
                            userDataObject.setUserAge(text);
                        } else if (tagname.equalsIgnoreCase("State")) {
                            userDataObject.setUserState(text);
                        } else if (tagname.equalsIgnoreCase("Img")) {
                            userDataObject.setUserImg(text);
                        } else if (tagname.equalsIgnoreCase("Imgstatus")) {
                            userDataObject.setUserImgState(text);
                        }
                        break;
                }
                eventType = xmlparser.next();
            }

        } catch (XmlPullParserException e) {
            System.out.println("xml parsing error :(");
            e.printStackTrace();
            //prefs.edit().putString("SERVERERROR", "YES").commit();
        } catch (IOException e) {
            System.out.println("file read error :(");
            e.printStackTrace();
        }
        System.out.println("xml parsing done :)");

        userDataArray.size();
        return userDataArray;
    }
    public InputStream httpMessageSend(String gtMessage) {
        InputStream in =null;
        String ack = "";
        try {
            URL url = new URL(gtMessage);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // Allow Inputs & Outputs
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(true);
            in = new BufferedInputStream(
                    urlConnection.getInputStream());
            System.out.println("inputStreamdata:"+in);
            //ack = readStream(in);
            //urlConnection.disconnect();
        } catch (Exception ex) {
            ack = "error";
            System.out.println("Exception" + ex.toString());
            //show pop-up for error
         //
         //   prefs.edit().putString("SERVERERROR","YES").commit();
        }
        return in;
    }

    public void onDataFetched(ArrayList<UserData> myData) {
        if(dataAdapObj==null&& myData!=null)
        {
            RecyclerDataAdapter dataAdapObj = new RecyclerDataAdapter(getApplicationContext(),myData);
            recyclerView.setAdapter(dataAdapObj);
           // dataAdapObj = new DataAdapter(getBaseContext(),myData);
            //pinListView.setAdapter(dataAdapObj);
        }
        else
        {
            dataAdapObj.notifyDataSetChanged();
        }
    }

    class fetchData extends AsyncTask<String, Void, Void> {
        ProgressDialog pdg = new ProgressDialog(RecyclerActivityMain.this);

        @Override
        protected void onPreExecute() {

            pdg.setMessage("loading please wait ....");
            pdg.show();
            pdg.setCancelable(false);
            super.onPreExecute();
           // prefs.edit().putString("SERVERERROR", "NO").commit();
        }

        @Override
        protected Void doInBackground(String... url_link) {

            String ack = "";
            try {
                xmlParseData(httpMessageSend(url_link[0]));

            } catch (Exception ex) {
                ack = "error";
                System.out.println("Exception" + ex.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pdg.dismiss();
            if (prefs.getString("SERVERERROR", "").equalsIgnoreCase("YES")) {
                AlertDialog.Builder bld = new AlertDialog.Builder(RecyclerActivityMain.this);
                bld.setTitle("Error");
                bld.setMessage("Server Not Reachable !!!");
                bld.setCancelable(false);
                bld.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        fetchData fdata = new fetchData();

                        fdata.execute(REGISTER_URL);

                    }
                });

                bld.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //finish();
                        dialog.cancel();
                    }
                });

                // dialog = bld.create();
                //dialog.show();
                prefs.edit().putString("SERVERERROR", "").commit();
            }
            onDataFetched(userDataArray);

            super.onPostExecute(result);
        }
}
}


