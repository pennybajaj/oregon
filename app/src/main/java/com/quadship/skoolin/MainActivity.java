package com.quadship.skoolin;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ArrayList<UserData> userDataArray;
    final static String REGISTER_URL = "http://www.etimesweb.com/bbmpin/BBMPIN.aspx?postdata=MF&allp=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
DisplayContent(0);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


//    public void onBackPressed() {
//      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("skoolinid " + id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        System.out.println("skooliniddd " + id);
        DisplayContent(id);

        return true;
    }

  public void  DisplayContent(int id)
    {
        Fragment fragment = null;
        Class fragmentClass;
        drawer.openDrawer(GravityCompat.START);
        fragmentClass = FirstFragment.class;
        if (id == R.id.nav_home) {
            System.out.println("skoolinidddhome "+id);
            drawer.openDrawer(GravityCompat.START);
            fragmentClass = FirstFragment.class;

            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            System.out.println("skoolinidddprofile " + id);
            drawer.openDrawer(GravityCompat.START);
            Intent intentjson = new Intent(MainActivity.this , JsonMainActivity.class);
            startActivity(intentjson);
        } else if (id == R.id.nav_skool) {
            //fragmentClass = ThirdFragment.class;
            Intent intent = new Intent(MainActivity.this , DisplayListView.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intentRecycler = new Intent(MainActivity.this , RecyclerActivityMain.class);
            startActivity(intentRecycler);
        } else if (id == R.id.nav_share) {
            fragmentClass = SecondFragment.class;
        } else if (id == R.id.nav_send) {
            fragmentClass = SecondFragment.class;
        }
        else {
            fragmentClass = FirstFragment.class;
        }
        try {
            fragment = (Fragment)fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();



        drawer.closeDrawer(GravityCompat.START);
    }

    /********************************************************************
     * Method : parsing Xml Data
     * @param
     * //Description : parsing xml data
     ********************************************************************/
    private List<UserData> xmlParseData(InputStream inputStream2){

        if(userDataArray==null)
        {
            userDataArray  = new ArrayList<UserData>();
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
                        }else if(tagname.equalsIgnoreCase("BBMPIN")){
                            userDataObject.setUserBbmPin(text);
                        }else if (tagname.equalsIgnoreCase("Name")) {
                            userDataObject.setUserName(text);
                        }else if(tagname.equalsIgnoreCase("Sex")){
                            userDataObject.setUserGender(text);
                        }
                        else if(tagname.equalsIgnoreCase("country")){
                            userDataObject.setUserCountry(text);
                        }else if(tagname.equalsIgnoreCase("city")){
                            userDataObject.setUserCity(text);
                        }else if(tagname.equalsIgnoreCase("age")){
                            userDataObject.setUserAge(text);
                        }
                        else if(tagname.equalsIgnoreCase("State"))
                        {
                            userDataObject.setUserState(text);
                        }else if(tagname.equalsIgnoreCase("Img")){
                            userDataObject.setUserImg(text);
                        }else if(tagname.equalsIgnoreCase("Imgstatus")){
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
}
