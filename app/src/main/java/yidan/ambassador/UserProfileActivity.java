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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/23.
 */
public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText phoneEdit,workEdit;
    TextView nameTxtView,cityTxtView,emailTxtView,gradTxtView,majorTxtView,expTxtView,avgTxtView;

    CheckBox vmChkBox,epChkBox,gdChkBox,mgChkBox,pmChkBox;
    Button editBtn,saveBtn;

    int ambID;
    String ambIDStr;
    String name,city,major,phone,exp,email;
    int grad,work;
    String vmSkill,epSkill,pmSkill,gdSkill,mgSkill;
    String AMB_SKILL_INSERT,AMB_SKILL_DELETE,AMB_UPDATE;

//    Boolean vm = false, ep = false, gd = false, mg = false, pm = false;
//    Cursor getskillCursor;
    int count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        nameTxtView = (TextView)this.findViewById(R.id.user_amb_name_txt);
        cityTxtView = (TextView)this.findViewById(R.id.user_amb_city_txt);

        gradTxtView = (TextView)this.findViewById(R.id.user_gradYr_txt);
        majorTxtView = (TextView)this.findViewById(R.id.user_major_txt);

        phoneEdit = (EditText)this.findViewById(R.id.user_amb_phone_EditTxt);
        workEdit = (EditText)this.findViewById(R.id.user_amb_workHr_editTxt);

        expTxtView = (TextView)this.findViewById(R.id.user_workExp_txt);
        emailTxtView = (TextView)this.findViewById(R.id.user_amb_email_txt);

        vmChkBox = (CheckBox)this.findViewById(R.id.user_vm_chkBox);
        epChkBox = (CheckBox)this.findViewById(R.id.user_ep_chkBox);
        gdChkBox = (CheckBox)this.findViewById(R.id.user_gd_chkBox);
        mgChkBox = (CheckBox)this.findViewById(R.id.user_mo_chkBox);
        pmChkBox = (CheckBox)this.findViewById(R.id.user_pm_chkBox);
        vmChkBox.setChecked(false);
        epChkBox.setChecked(false);
        gdChkBox.setChecked(false);
        mgChkBox.setChecked(false);
        pmChkBox.setChecked(false);
        vmChkBox.setEnabled(false);
        epChkBox.setEnabled(false);
        gdChkBox.setEnabled(false);
        mgChkBox.setEnabled(false);
        pmChkBox.setEnabled(false);

        vmChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vmSkill = "1";
                }
            }
        });
        epChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    epSkill = "2";
                }
            }
        });
        gdChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gdSkill = "3";
                }
            }
        });
        mgChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mgSkill = "4";
                }
            }
        });
        pmChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pmSkill = "5";
                }
            }
        });
        avgTxtView = (TextView)this.findViewById(R.id.user_amb_avg_txt);

        editBtn = (Button)this.findViewById(R.id.user_edit_button);
        editBtn.setOnClickListener(this);
        saveBtn = (Button)this.findViewById(R.id.user_save_button);
        saveBtn.setOnClickListener(this);
        saveBtn.setEnabled(false);

        phoneEdit.setEnabled(false);
        workEdit.setEnabled(false);


        Intent intent = getIntent();
        ambID = intent.getIntExtra("AmbID", 0);
        ambIDStr = Integer.toString(ambID);


        Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.PROFILE_AMB,this.getParameter(ambIDStr));
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex("Amb_Name"));
            city = cursor.getString(cursor.getColumnIndex("Amb_Citizenship"));
            grad = cursor.getInt(cursor.getColumnIndex("Amb_GradYr"));
            major = cursor.getString(cursor.getColumnIndex("Major_Name"));
            phone = cursor.getString(cursor.getColumnIndex("Amb_Phone"));
            work = cursor.getInt(cursor.getColumnIndex("Amb_WorkHr"));
            exp = cursor.getString(cursor.getColumnIndex("Amb_WorkExp"));
            email = cursor.getString(cursor.getColumnIndex("Amb_Email"));
        }

        nameTxtView.setText(name);
        cityTxtView.setText(city);
        gradTxtView.setText(Integer.toString(grad));
        majorTxtView.setText(major);
        phoneEdit.setText(phone);
        workEdit.setText(Integer.toString(work));
        expTxtView.setText(exp);
        emailTxtView.setText(email);

        Cursor cursor1 = DBOperator.getInstance().execQuery(SQLCommand.AMB_PROFILE_AVG,this.getParameter(ambIDStr));
        int count = 0;
        int sum = 0;
        double avg = 0.0;
        avgTxtView.setText("");
        while (cursor1.moveToNext()){
            int ambPerfId = cursor1.getInt(cursor1.getColumnIndex("Amb_ID"));
            int score = cursor1.getInt(cursor1.getColumnIndex("Perform_Score"));
            if (ambID == ambPerfId){
                count++;
                sum = sum + score;
                avg = (sum / count);
            }
            avgTxtView.setText(Double.toString(avg));
        }


//        getskillCursor = DBOperator.getInstance().execQuery("select AmbassadorSkill.Skill_Num as _id from AmbassadorSkill where AmbassadorSkill.Amb_ID = "+ambIDStr);

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
            Intent intent = new Intent(this,UserMainActivity.class);
            intent.putExtra("AmbID",ambID);
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
            intent.putExtra("AmbID",ambID);
            this.startActivity(intent);
            return true;

        } else if (id == R.id.user_nav_person) {
            Toast.makeText(getBaseContext(), "You are in 'My Profile' page!", Toast.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.user_nav_logout) {
            Intent intent = new Intent(this,UserLoginActivity.class);
            this.startActivity(intent);
            Toast.makeText(getBaseContext(), "Logout Successful!", Toast.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.user_nav_register){
            Intent intent = new Intent(this,UserRegisteredActivity.class);
            intent.putExtra("AmbID", ambID);
            this.startActivity(intent);
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.user_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String[] getParameter(String aId){
        String[] args = new String[1];
        args[0] = aId;
        return args;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.user_edit_button){
            phoneEdit.setEnabled(true);
            workEdit.setEnabled(true);
            vmChkBox.setEnabled(true);
            epChkBox.setEnabled(true);
            gdChkBox.setEnabled(true);
            mgChkBox.setEnabled(true);
            pmChkBox.setEnabled(true);
            vmChkBox.setChecked(false);
            epChkBox.setChecked(false);
            gdChkBox.setChecked(false);
            mgChkBox.setChecked(false);
            pmChkBox.setChecked(false);

            saveBtn.setEnabled(true);

        }else if( id == R.id.user_save_button){
            String newphone = phoneEdit.getText().toString();
            String newworkhour = workEdit.getText().toString();


            AMB_SKILL_INSERT = "insert into AmbassadorSkill(Amb_ID,Skill_Num,_id) values (?,?,?)";
            AMB_SKILL_DELETE = "delete from AmbassadorSkill where Amb_ID = ? ";
            AMB_UPDATE = "update Ambassador set Amb_Phone = '"+newphone+"', Amb_WorkHr = '" +newworkhour+"' where Ambassador.Amb_ID = "+ambIDStr;

            DBOperator.getInstance().execSQL(AMB_UPDATE);

            String sql = "select count(*) from AmbassadorSkill where Amb_ID = ?";

            Cursor cursor = DBOperator.getInstance().execQuery(sql,this.getParameter(ambIDStr));
            while (cursor.moveToNext()){
                count = cursor.getInt(cursor.getColumnIndex("count(*)"));
            }
            if(count == 0){
                if(vmChkBox.isChecked()){

                    DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,vmSkill));
                    if(epChkBox.isChecked()){

                        DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,epSkill));
                        if(gdChkBox.isChecked()){

                            DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,gdSkill));
                            if(mgChkBox.isChecked()){

                                DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,mgSkill));
                                if(pmChkBox.isChecked()){

                                    DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,pmSkill));
                                }
                            }
                        }
                    }
                }
            } else if (count > 0){
                DBOperator.getInstance().execSQL(AMB_SKILL_DELETE,this.getParameter(ambIDStr));

                if(vmChkBox.isChecked()){

                    DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,vmSkill));
                    if(epChkBox.isChecked()){

                        DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,epSkill));
                        if(gdChkBox.isChecked()){

                            DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,gdSkill));
                            if(mgChkBox.isChecked()){

                                DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,mgSkill));
                                if(pmChkBox.isChecked()){

                                    DBOperator.getInstance().execSQL(AMB_SKILL_INSERT,this.getParameterSkill(ambIDStr,pmSkill));
                                }
                            }
                        }
                    }
                }

            }

            Toast.makeText(getApplicationContext(),"Save Success!",Toast.LENGTH_SHORT).show();
            vmChkBox.setEnabled(false);
            epChkBox.setEnabled(false);
            gdChkBox.setEnabled(false);
            mgChkBox.setEnabled(false);
            pmChkBox.setEnabled(false);

            phoneEdit.setEnabled(false);
            workEdit.setEnabled(false);

//            Intent intent = new Intent(this,UserProfileActivity.class);
//            intent.putExtra("AmbID",ambID);
//            this.startActivity(intent);

        }
    }

    public String[] getParameterSkill(String idStr,String skillNum){
        String args[] = new String[2];
        args[0] = idStr;
        args[1] = skillNum;
        return args;
    }
}
