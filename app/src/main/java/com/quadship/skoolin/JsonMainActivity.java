package com.quadship.skoolin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/4/16.
 */
public class JsonMainActivity extends AppCompatActivity {

    ArrayList<UserData> userDataArray;
RecyclerView snapListView;
    JsonDataAdapter dataAdapObj;
    final static String REGISTER_URL = "http://www.snapchat.findchatusername.com/gender-wise-data.php?gender=male&user_id=6908&page=3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity_main);
        userDataArray = new ArrayList<UserData>();
        snapListView = (RecyclerView) findViewById(R.id.card_recycler_view);
        snapListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        snapListView.setLayoutManager(layoutManager);
        snapAsync sdata = new snapAsync();
        sdata.execute(REGISTER_URL);

    }

    public InputStream httpMessageSend(String gtMessage) {
        InputStream in = null;
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
            System.out.println("inputStreamdata:" + in);
            //ack = readStream(in);
            //urlConnection.disconnect();
        } catch (Exception ex) {
            ack = "error";
            System.out.println("Exception" + ex.toString());
            //show pop-up for error

        }
        return in;
    }

    private List<UserData> parseJsonXml(InputStream inputStream2) {

            //how to parse json from inputstream
        if (userDataArray == null) {
            userDataArray = new ArrayList<UserData>();
        }
        UserData userDataObject = null;
        String text = null;
        try {
            //JSONParser jParser = new JSONParser();
            String responseString = readInputStream(inputStream2);
            JSONObject  jsonRootObject = new JSONObject(responseString);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("info");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userDataObject = new UserData();
                userDataArray.add(userDataObject);
               // int id = Integer.parseInt(jsonObject.optString("id").toString());
                String name = jsonObject.optString("name").toString();
                userDataObject.setUserName(name);
                String snapid = jsonObject.optString("snapchat_id").toString();
                userDataObject.setUserBbmPin(snapid);
                String snapimage = jsonObject.optString("user_image").toString();
                userDataObject.setUserImg(snapimage);


                System.out.println("Name " + name + " snapId " + snapid + " image " +snapimage);

            }

        }
        catch (JSONException e) {
            System.out.println("file read error :(");
            e.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return userDataArray;
    }
    public void onDataFetched(ArrayList<UserData> myData) {
        if(dataAdapObj==null&& myData!=null)
        {
            dataAdapObj = new JsonDataAdapter(getApplicationContext(),myData);
            snapListView.setAdapter(dataAdapObj);
            // dataAdapObj = new DataAdapter(getBaseContext(),myData);
            //pinListView.setAdapter(dataAdapObj);
        }
        else
        {
            dataAdapObj.notifyDataSetChanged();
        }
    }
    private String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream, "UTF-8"));
        String tmp;
        StringBuilder sb = new StringBuilder();
        while ((tmp = reader.readLine()) != null) {
            sb.append(tmp).append("\n");
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '\n') {
            sb.setLength(sb.length() - 1);
        }
        reader.close();
        return sb.toString();
    }
        class snapAsync extends AsyncTask<String, Void, Void> {
            ProgressDialog pdg = new ProgressDialog(JsonMainActivity.this);

            @Override
            protected Void doInBackground(String... params) {
                String ack = "";
                try {
                    parseJsonXml(httpMessageSend(params[0]));
                } catch (Exception ex) {
                    ack = "error";
                    System.out.println("Exception" + ex.toString());
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                pdg.setMessage("loading please wait ....");
                pdg.show();
                pdg.setCancelable(false);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void result) {
                pdg.dismiss();
                onDataFetched(userDataArray);
                super.onPostExecute(result);
            }
        }
    }

