package yidan.ambassador;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;



/**
 * Created by xyd on 16/4/13.
 */
public class NewEventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    Button addNewBtn;
    public ListView evtSwpListView;
    public static String sql;
    int eventNum,event_id;
    Cursor cursor1;
    SQLiteDatabase db;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_event_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);


        evtSwpListView = (ListView) this.findViewById(R.id.event_swipe_listView);
        evtSwpListView.setOnItemClickListener(new ItemClickListener());

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.FUTURE_EVENT_LIST);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    getApplicationContext(),
                    R.layout.event_delete_list_item_layout,
                    cursor,
                    new String[]{"Event_Date", "Event_Topic"},
                    new int[]{R.id.event_date,R.id.event_topic},
                    SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
            );
        evtSwpListView.setAdapter(adapter);

        addNewBtn = (Button)this.findViewById(R.id.admin_event_addnew_button);
        addNewBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AdminMainActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.admin_nav_ambassador) {
            Intent intent = new Intent(this,AmbassadorActivity.class);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.admin_nav_event) {
            Toast.makeText(getApplicationContext(), "You are in 'Event' page!", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.admin_nav_rate) {
            Intent intent = new Intent(this, ReviewActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.admin_nav_logout) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) evtSwpListView.getItemAtPosition(position);
            eventNum = (int)id;
            eventId = cursor.getInt(0);
            String eventDate = cursor.getString(1);
            String eventTime = cursor.getString(2);
            int eventDuration = cursor.getInt(3);
            String eventTopic = cursor.getString(4);
            String eventDesc = cursor.getString(5);
            String eventFormat = cursor.getString(6);
            int eventAmbAmount = cursor.getInt(7);
            event_id = cursor.getInt(8);
            if (eventId != 0){
                Intent intent = new Intent(getApplicationContext(),EventDetailActivity.class);
                intent.putExtra("Event_Num",eventId);
                intent.putExtra("Event_Date",eventDate);
                intent.putExtra("Event_Time",eventTime);
                intent.putExtra("Event_Duration",eventDuration);
                intent.putExtra("Event_Topic",eventTopic);
                intent.putExtra("Event_Desc",eventDesc);
                intent.putExtra("Event_Format",eventFormat);
                intent.putExtra("Event_AmbAmount", eventAmbAmount);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Please choose an event!",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.admin_event_addnew_button){
            Intent intent = new Intent(this,AdminEventAddnewActivity.class);
            this.startActivity(intent);
        }
    }
}
