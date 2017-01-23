package yidan.ambassador;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/13.
 */
public class AdminMainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    int adminID;
    ImageButton eventBtn,ambBtn,reviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Intent intent = getIntent();
        adminID = intent.getIntExtra("AdminID",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        eventBtn = (ImageButton)this.findViewById(R.id.admin_main_event);
        eventBtn.setOnClickListener(this);
        ambBtn = (ImageButton)this.findViewById(R.id.admin_main_amb);
        ambBtn.setOnClickListener(this);
        reviewBtn = (ImageButton)this.findViewById(R.id.admin_main_review);
        reviewBtn.setOnClickListener(this);

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
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
            Intent intent = new Intent(this,AdminMainActivity.class);
            this.startActivity(intent);
            Toast.makeText(getApplicationContext(),"You are in the 'Home' page!",Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this,AmbassadorActivity.class);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.admin_nav_event) {
            Intent intent = new Intent(this,NewEventActivity.class);
            this.startActivity(intent);
            return true;

        }  else if (id == R.id.admin_nav_rate){
            Intent intent = new Intent(this,ReviewActivity.class);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.admin_nav_logout) {
            Intent intent = new Intent(this,AdminLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(),"Logout Successful!",Toast.LENGTH_LONG).show();
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.admin_main_event){
            Intent intent = new Intent(this, NewEventActivity.class);
            this.startActivity(intent);

        }else if (id == R.id.admin_main_amb){
            Intent intent = new Intent(this, AmbassadorActivity.class);
            this.startActivity(intent);

        }else if (id == R.id.admin_main_review){
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("Admin_ID",adminID);
            this.startActivity(intent);

        }
    }
}
