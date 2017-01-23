package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/25.
 */
public class UserSignupActivity extends Activity implements View.OnClickListener {

    Spinner majorSpinner, gradyrSpinner;
    Cursor majorCursor;
    String gradyr;
    EditText emailEdit, nameEdit, passEdit, passconEdit, phoneEdit, nationalityEdit;
    RadioGroup radioWorkExperience;
    RadioButton radioYes, radioNo;
    Button signupBtn;
    int ambID,amb_id,new_id;
    String new_idStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup_layout);

        majorSpinner = (Spinner)this.findViewById(R.id.user_signup_major_spinner);
        gradyrSpinner = (Spinner)this.findViewById(R.id.user_signup_gradyear_spinner);

        radioWorkExperience = (RadioGroup) findViewById(R.id.radioWorkExperience);
        radioYes = (RadioButton) findViewById(R.id.radioYes);
        radioNo = (RadioButton) findViewById(R.id.radioNo);

        nameEdit = (EditText) this.findViewById(R.id.user_signup_name_editText);
        nationalityEdit = (EditText) this.findViewById(R.id.user_signup_country_editText);
        phoneEdit = (EditText) this.findViewById(R.id.user_signup_phone_editText);
        emailEdit = (EditText) this.findViewById(R.id.user_signup_email_editText);
        passEdit = (EditText) this.findViewById(R.id.user_signup_password_editText);
        passconEdit = (EditText) this.findViewById(R.id.user_signup_confirm_password_editText);

        signupBtn = (Button) this.findViewById(R.id.user_signup_submit_button);
        signupBtn.setOnClickListener(this);


        //copy database file

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
        majorCursor = DBOperator.getInstance().execQuery(SQLCommand.USER_MAJOR);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.user_signup_major_list_item_layout,
                majorCursor,
                new String[]{"_id"},
                new int[] {R.id.user_signup_major},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
        );

        majorSpinner.setAdapter(adapter);

    }

    public void onClick(View v) {
        int id = v.getId();
        Cursor idCursor;
        String idnew = null;
        String name = nameEdit.getText().toString();
        String nationality = nationalityEdit.getText().toString();
        String major = null;
        String workexp = null;
        gradyr = gradyrSpinner.getSelectedItem().toString();
        String workhr = null;
        String phone = phoneEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String passcon = passconEdit.getText().toString();

        int pos = majorSpinner.getSelectedItemPosition();
        if (pos==Spinner.INVALID_POSITION){
            major = null;
        }else if (pos==0){
            major = "1";
        }else if (pos==1){
            major = "2";
        }else if (pos==2){
            major = "3";
        }else if (pos==3){
            major = "4";
        }

        int selectedWE = radioWorkExperience.getCheckedRadioButtonId();
        if(selectedWE == radioYes.getId()){
            workexp = "Y";
        }else if (selectedWE == radioNo.getId()){
            workexp = "N";
        }

        if (id == R.id.user_signup_submit_button) {

            idCursor = DBOperator.getInstance().execQuery(SQLCommand.USER_EMAIL);
            while (idCursor.moveToNext()){
                String cEmail = idCursor.getString(idCursor.getColumnIndex("Amb_Email"));
                if (email.equals(cEmail)){
                    Toast.makeText(getApplicationContext(), "You have already registered.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (email.matches("") || name.matches("") || pass.matches("") || passcon.matches("") || phone.matches("") || major.matches("") ||gradyr.matches("") || nationality.matches("") || workexp.matches("")) {
                Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }else if (!pass.equals(passcon)) {
                Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Cursor cursor1 = DBOperator.getInstance().execQuery("select count(*) from Ambassador");
                while (cursor1.moveToNext()){
                    amb_id = cursor1.getInt(cursor1.getColumnIndex("count(*)"));
                }
                new_id = amb_id + 1;
                new_idStr = Integer.toString(new_id);

                DBOperator.getInstance().execSQL(SQLCommand.USER_SIGNUP, this.getParameters(idnew,
                        name, nationality, major, gradyr, workhr, phone, email, workexp, pass,new_idStr));
                Toast.makeText(getApplicationContext(), "Signup Success!", Toast.LENGTH_SHORT).show();

                Cursor cursor = DBOperator.getInstance().execQuery("select count(*) from Ambassador");
                while(cursor.moveToNext()){
                    ambID = cursor.getInt(cursor.getColumnIndex("count(*)"));
                }

                Intent intent = new Intent(this,UserMainActivity.class);
                intent.putExtra("AmbID",ambID);
                this.startActivity(intent);
            }
        }
    }
    public String[] getParameters(String idnew, String name, String nationality,
                                  String major, String gradyr, String workhr,
                                  String phone, String email, String workexp,
                                  String pass,String amb_id){
        String args[] = new String[11];
        args[0] = idnew;
        args[1] = name;
        args[2] = nationality;
        args[3] = major;
        args[4] = gradyr;
        args[5] = workhr;
        args[6] = phone;
        args[7] = email;
        args[8] = workexp;
        args[9] = pass;
        args[10] = amb_id;
        return args;
    }

}
