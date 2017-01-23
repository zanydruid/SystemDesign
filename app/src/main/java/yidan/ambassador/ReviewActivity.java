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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/17.
 */
public class ReviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button reviewBtn,submitBtn;
    Spinner evtSpinner, ambSpinner;
    Cursor cursor1,cursor3;
    Cursor cursorCrt1,cursorCrt2,cursorCrt3;
    TextView nameTxt;
    int ambSelctedID;
    String ambSelectName;
    String sql;
    EditText c1EdtTxt,c2EdtTxt,c3EdtTxt;
    String c1,c2,c3;
    int adminId,eventId;
    int maxNum,countNum,maxNum1,maxNum2;
    String evtStr,admStr,ambStr,maxStr,maxStr1,maxStr2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main_layout);

        Intent intent = getIntent();
        adminId = intent.getIntExtra("Admin_ID",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_review_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.review_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        evtSpinner = (Spinner) this.findViewById(R.id.review_event_spinner);
        evtSpinner.setOnItemSelectedListener(eventSelectListner);
        nameTxt = (TextView) this.findViewById(R.id.review_ambName_txt);

        reviewBtn = (Button) this.findViewById(R.id.admin_review_button);
        reviewBtn.setOnClickListener(this);
        submitBtn = (Button)this.findViewById(R.id.review_submit_button);
        submitBtn.setOnClickListener(this);

        c1EdtTxt = (EditText)this.findViewById(R.id.critera1_ediTxt);
        c2EdtTxt = (EditText)this.findViewById(R.id.critera2_editTxt);
        c3EdtTxt = (EditText)this.findViewById(R.id.critera3_editTxt);

        cursor1 = DBOperator.getInstance().execQuery(SQLCommand.EVENT_LIST);
        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.activity_review_event_drop_down_list,
                cursor1,
                new String[]{"Event_Topic"},
                new int[]{R.id.review_event},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
        evtSpinner.setAdapter(adapter1);


        ambSpinner = (Spinner) this.findViewById(R.id.review_amb_spinner);
        ambSpinner.setOnItemSelectedListener(ambSelectListner);
    }


    private AdapterView.OnItemSelectedListener eventSelectListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor)evtSpinner.getItemAtPosition(position);
                eventId = cursor.getInt(0);

                sql = "select Ambassador.Amb_ID,Amb_Name, Ambassador._id, Register._id from Ambassador, Register " +
                        "where Ambassador.Amb_ID = Register.Amb_ID and Register.Event_Num = " + Integer.toString(eventId);
                cursor3 = DBOperator.getInstance().execQuery(sql);

                SimpleCursorAdapter adapter3 = new SimpleCursorAdapter(
                        getApplicationContext(),
                        R.layout.activity_review_amb_drop_down_list,
                        cursor3,
                        new String[]{"Amb_Name"},
                        new int[]{R.id.review_amb},
                        SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

                ambSpinner.setAdapter(adapter3);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener ambSelectListner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Cursor cursor = (Cursor)ambSpinner.getItemAtPosition(position);
            ambSelctedID = cursor.getInt(0);
            ambSelectName = cursor.getString(1);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.review_drawer_layout);
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
            Intent intent = new Intent(this, AmbassadorActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.admin_nav_event) {
            Intent intent = new Intent(this, NewEventActivity.class);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.admin_nav_rate) {
            Toast.makeText(getApplicationContext(), "You are in 'Review' page!", Toast.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.admin_nav_logout) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.review_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        nameTxt.setText("");
        if (id == R.id.admin_review_button) {
            nameTxt.setText(ambSelectName);
        }

        if(id == R.id.review_submit_button) {
            c1 = c1EdtTxt.getText().toString();
            c2 = c2EdtTxt.getText().toString();
            c3 = c3EdtTxt.getText().toString();

            if (c1.matches("") || c2.matches("") || c3.matches("")) {
                Toast.makeText(this, "Please enter the scores.", Toast.LENGTH_SHORT).show();
            } else {
                ambStr = Integer.toString(ambSelctedID);
                evtStr = Integer.toString(eventId);
                admStr = Integer.toString(adminId);


                Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.PERFORMANCE_MAX);
                while (cursor.moveToNext()) {
                    maxNum = cursor.getInt(cursor.getColumnIndex("max(_id)"));
                }
                maxNum = maxNum + 1;
                maxStr = Integer.toString(maxNum);

//            cursorCrt1 = DBOperator.getInstance().execQuery(SQLCommand.INSERT_CRITERIA1, this.getParameters1(
//                    ambStr, evtStr, admStr, Integer.toString(1), c1, maxStr));
                DBOperator.getInstance().execSQL(SQLCommand.INSERT_CRITERIA1, this.getParameters1(
                        ambStr, evtStr, admStr, Integer.toString(1), c1, maxStr));

                maxNum1 = maxNum + 1;
                maxStr1 = Integer.toString(maxNum1);
//            cursorCrt2 = DBOperator.getInstance().execQuery(SQLCommand.INSERT_CRITERIA1, this.getParameters1(
//                    ambStr, evtStr, admStr, Integer.toString(2), c2, maxStr1));
                DBOperator.getInstance().execSQL(SQLCommand.INSERT_CRITERIA1, this.getParameters1(
                        ambStr, evtStr, admStr, Integer.toString(2), c2, maxStr1));

                maxNum2 = maxNum1 + 1;
                maxStr2 = Integer.toString(maxNum2);
                DBOperator.getInstance().execSQL(SQLCommand.INSERT_CRITERIA1, this.getParameters1(
                        ambStr, evtStr, admStr, Integer.toString(3), c3, maxStr2));

                Cursor cursor2 = DBOperator.getInstance().execQuery(SQLCommand.PERFORMANCE_MAX);

                while (cursor2.moveToNext()) {
                    countNum = cursor2.getInt(cursor2.getColumnIndex("max(_id)"));
                }

                Toast.makeText(getBaseContext(), "Review Success!", Toast.LENGTH_LONG).show();

            }
        }
    }

    public String[] getParameters1(String ambID,String evtNum,String admID, String crit,
                                   String score,String max){
        String args[] = new String[6];
        args[0] = ambID;
        args[1] = evtNum;
        args[2] = admID;
        args[3] = crit;
        args[4] = score;
        args[5] = max;
        return args;
    }
}
