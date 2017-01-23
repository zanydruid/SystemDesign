package yidan.ambassador;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/17.
 */
public class RegisterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ListView evtListView, ambListView;
    Button readBtn, regrstBtn;
    Cursor cursorPerf, cursorReg,cursorTot;
    String sql1, sql2,sql3;
    TextView regName, regMajor, regTot;
    public int regAmbId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_register_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.register_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        evtListView = (ListView) this.findViewById(R.id.register_event_listView);
        evtListView.setOnItemClickListener(new ItemClickListener());

        ambListView = (ListView) this.findViewById(R.id.register_amb_listView);
        ambListView.setOnItemClickListener(new ItemClickListener());

        readBtn = (Button) this.findViewById(R.id.register_readmore_button);
        readBtn.setOnClickListener(this);
        readBtn.setEnabled(false);

        regrstBtn = (Button) this.findViewById(R.id.register_reset_button);
        regrstBtn.setOnClickListener(this);

        regName = (TextView) this.findViewById(R.id.register_name_content);
        regMajor = (TextView) this.findViewById(R.id.register_major_content);
        regTot = (TextView) this.findViewById(R.id.register_total_num);

        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.EVENT_LIST);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.register_event_list_item_layout,
                cursor,
                new String[]{"Event_Num", "Event_Date", "Event_Time", "Event_Topic"},
                new int[]{R.id.reg_event_id, R.id.reg_event_date, R.id.reg_event_time, R.id.reg_event_topic},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
        );
        evtListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.register_drawer_layout);
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
            Intent intent = new Intent(this, MainActivity.class);
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
            Intent intent = new Intent(this, AmbassadorActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.admin_nav_event) {
            Intent intent = new Intent(this, NewEventActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.admin_nav_rate) {

        } else if (id == R.id.admin_nav_logout) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.register_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            regName.setText("");
            regMajor.setText("");
            readBtn.setEnabled(true);

            Cursor cursorEvt = (Cursor) evtListView.getItemAtPosition(position);
            int eventId = cursorEvt.getInt(0);

            sql3="select count(Amb_ID),_id from Register where Event_Num = " + Integer.toString(eventId);
            cursorTot = DBOperator.getInstance().execQuery(sql3);
            while (cursorTot.moveToNext()) {
                int count = cursorTot.getInt(cursorTot.getColumnIndex("count(Amb_ID)"));
                regTot.setText(Integer.toString(count));
            }


            sql1 = "select Amb_ID, Event_Num, Register_Date, _id from Register where " +
                    "Event_Num = " + Integer.toString(eventId);
            cursorPerf = DBOperator.getInstance().execQuery(sql1);


            while (cursorPerf.moveToNext()) {
                regAmbId = cursorPerf.getInt(cursorPerf.getColumnIndex("Amb_ID"));

//                sql2 = "select Amb_ID, Amb_Name, Amb_Citizenship," +
//                        "Major_Num, Amb_GradYr, Amb_WorkHr, Amb_Phone, Amb_Email, Amb_WorkExp, _id " +
//                        "from Ambassador where Amb_ID = " + Integer.toString(regAmbId);
                sql2 = "select Amb_ID, Amb_Name, Amb_Citizenship," +
                        "Major_Num, Amb_GradYr, Amb_WorkHr, Amb_Phone, Amb_Email, Amb_WorkExp, _id " +
                        "from Ambassador where Amb_ID in( " + Integer.toString(regAmbId) +")";

                cursorReg = DBOperator.getInstance().execQuery(sql2);

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        getApplicationContext(),
                        R.layout.register_amb_list_item_layout,
                        cursorReg,
                        new String[]{"Amb_ID", "Amb_Name", "Amb_WorkHr", "Amb_WorkExp"},
                        new int[]{R.id.reg_amb_id_txv, R.id.reg_amb_name_txv, R.id.reg_amb_workHr_txv, R.id.reg_amb_workExp_txv},
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
                );
                ambListView.setAdapter(adapter);//only show out the last one

            }
            if (cursorReg != null) {
                while (cursorReg.moveToNext()) {

                    String name = cursorReg.getString(1);
                    int major = cursorReg.getInt(3);
                    if (regName != null) {
                        regName.setText(name);
                        switch (major) {
                            case 1:
                                regMajor.setText("MSIT");
                                break;
                            case 2:
                                regMajor.setText("MSOAM");
                                break;
                            case 3:
                                regMajor.setText("MSMTI");
                                break;
                            case 4:
                                regMajor.setText("MBA");
                                break;
                            case 5:
                                regMajor.setText("MSDM");
                                break;
                        }
                    }
                }
            } else {
                regName.setText("");
                regMajor.setText("");
                Toast.makeText(getBaseContext(), "No one register for this event!", Toast.LENGTH_LONG).show();

            }
    }

}

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.register_readmore_button){

            Intent intent = new Intent(getApplicationContext(),AmbassadorActivity.class);
            intent.putExtra("AmbReg",regAmbId);
            this.startActivity(intent);


        }else if (id == R.id.register_reset_button){
            regName.setText("");
            regMajor.setText("");
            readBtn.setEnabled(false);
        }
    }
}