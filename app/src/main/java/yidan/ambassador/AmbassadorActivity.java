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
 * Created by xyd on 16/4/15.
 */
public class AmbassadorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView ambSwpListView;
    int amb_id;

    SQLiteDatabase db1;
    Cursor cursor2, cursor3,cursor4,cursor5,cursor6;
    Intent intent1;
    int regId;
    String sql,sql1,sql2;
    int ambid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_amb_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_amb_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.admin_amb_drawer_layout);

        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(
                this,
                drawer1,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer1.setDrawerListener(toggle1);
        toggle1.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        ambSwpListView = (ListView) this.findViewById(R.id.admin_amb_listView);
        ambSwpListView.setOnItemClickListener(new ItemClickListener());

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.AMB_ORDER_LIST);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.amb_delete_list_item_layout,
                cursor,
                new String[]{"Major_Name","Amb_Name", },
                new int[]{R.id.amb_major_txv,R.id.amb_name_txv},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
        );
        ambSwpListView.setAdapter(adapter);


            }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_amb_drawer_layout);
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
            Intent intent = new Intent(this, AdminMainActivity.class);
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

        if (id == R.id.admin_nav_ambassador) {
            Toast.makeText(getApplicationContext(), "You are in 'Ambassador' page!", Toast.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.admin_nav_event) {
            Intent intent = new Intent(this, NewEventActivity.class);
            this.startActivity(intent);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_amb_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) ambSwpListView.getItemAtPosition(position);
            amb_id = cursor.getInt(11);

            ambid = cursor.getInt(0);
            String name = cursor.getString(1);
            String city = cursor.getString(2);
            int major = cursor.getInt(3);
            int grad = cursor.getInt(4);
            int work = cursor.getInt(5);
            String phone = cursor.getString(6);
            String email = cursor.getString(7);
            String exp = cursor.getString(8);

            if(ambid != 0){
                Intent intent= new Intent(getApplicationContext(),AmbasadorDetailActivity.class);
                intent.putExtra("AmbID",ambid);
                intent.putExtra("AmbName",name);
                intent.putExtra("AmbCity",city);
                intent.putExtra("AmbMajor",major);
                intent.putExtra("AmbGrad",grad);
                intent.putExtra("AmbWork",work);
                intent.putExtra("AmbPhone",phone);
                intent.putExtra("AmbEmail",email);
                intent.putExtra("AmbExp",exp);
                intent.putExtra("_id",amb_id);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Please choose an item!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}