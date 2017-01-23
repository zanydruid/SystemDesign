package yidan.ambassador;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by xyd on 16/4/8.
 */
public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    int ambId;
    ImageButton evtBtn,regBtn,profBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_layout);

        Intent intent = getIntent();
        ambId = intent.getIntExtra("AmbID",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        evtBtn = (ImageButton)this.findViewById(R.id.user_event_button);
        evtBtn.setOnClickListener(this);
        regBtn = (ImageButton)this.findViewById(R.id.user_profile_button);
        regBtn.setOnClickListener(this);
        profBtn = (ImageButton)this.findViewById(R.id.user_registered_button);
        profBtn.setOnClickListener(this);


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
            Intent intent = new Intent(this,UserLoginActivity.class);
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
            // Handle the camera action
            Intent intent = new Intent(this,UserEventActivity.class);
            intent.putExtra("AmbID", ambId);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.user_nav_register){
            Intent intent = new Intent(this,UserRegisteredActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.user_nav_person) {
            Intent intent = new Intent(this,UserProfileActivity.class);
            intent.putExtra("AmbID", ambId);
            this.startActivity(intent);
            return true;
        } else if (id == R.id.user_nav_logout) {
            Intent intent = new Intent(this,UserLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.user_event_button){
            Intent intent = new Intent(this,UserEventActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);

        }else if(id == R.id.user_profile_button){
            Intent intent = new Intent(this,UserProfileActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);

        }else if(id == R.id.user_registered_button){
            Intent intent = new Intent(this,UserRegisteredActivity.class);
            intent.putExtra("AmbID",ambId);
            this.startActivity(intent);

        }
    }
}
