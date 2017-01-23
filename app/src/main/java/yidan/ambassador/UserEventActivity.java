package yidan.ambassador;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/23.
 */
public class UserEventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int ambId;
    ListView evtListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_event_main_layout);

        Intent intent = getIntent();
        ambId = intent.getIntExtra("AmbID",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_event_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        evtListView = (ListView) this.findViewById(R.id.user_event_listView);
        evtListView.setOnItemClickListener(new ItemClickListener());

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.FUTURE_EVENT_LIST);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.event_delete_list_item_layout,
                cursor,
                new String[]{"Event_Date", "Event_Topic"},
                new int[]{R.id.event_date, R.id.event_topic},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
        );
        evtListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, UserMainActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.user_nav_event) {
            Toast.makeText(getApplicationContext(),"You are in the 'Event' page!",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.user_nav_register) {
            Intent intent = new Intent(this,UserRegisteredActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.user_nav_person) {
            Intent intent = new Intent(this,UserProfileActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.user_nav_logout) {
            Intent intent = new Intent(this, UserLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) evtListView.getItemAtPosition(position);
            int evtNum = cursor.getInt(0);
            String evtDate = cursor.getString(1);
            String evtTime = cursor.getString(2);
            int evtDur = cursor.getInt(3);
            String evtTopic = cursor.getString(4);
            String evtDesc = cursor.getString(5);
            String evtFormat = cursor.getString(6);
            int evtAmount = cursor.getInt(7);
            int evt_id = cursor.getInt(8);

            if (evtNum !=0){
                Intent intent = new Intent(getApplicationContext(),UserEventDetailActivity.class);
                intent.putExtra("EvtNum",evtNum);
                intent.putExtra("EvtDate",evtDate);
                intent.putExtra("EvtTime",evtTime);
                intent.putExtra("EvtDur",evtDur);
                intent.putExtra("EvtTopic",evtTopic);
                intent.putExtra("EvtDesc",evtDesc);
                intent.putExtra("EvtFormat",evtFormat);
                intent.putExtra("EvtAmount",evtAmount);
                intent.putExtra("AmbID",ambId);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Please choose a event!",Toast.LENGTH_SHORT).show();
            }



        }
    }
}