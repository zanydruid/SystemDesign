package yidan.ambassador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import yidan.ambassador.constant.SQLCommand;
import yidan.ambassador.util.DBOperator;

/**
 * Created by xyd on 16/4/13.
 */
public class UserLoginActivity extends Activity implements View.OnClickListener {

    EditText emailEdit, passEdit;
    ImageButton loginBtn;
    ImageButton signupBtn;
    String email,pass;
    Boolean userCheck = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_layout);

        emailEdit = (EditText) this.findViewById(R.id.user_login_username_editText);
        passEdit = (EditText) this.findViewById(R.id.user_login_password_editText);

        loginBtn = (ImageButton) this.findViewById(R.id.user_login_entry_button);
        signupBtn = (ImageButton) this.findViewById(R.id.user_login_signup_button);
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        //copy database file

        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onClick(View v) {
        int id = v.getId();
        Cursor cursor3, cursor4;
        email = emailEdit.getText().toString();
        pass = passEdit.getText().toString();

        if (id == R.id.user_login_entry_button) {

            cursor3 = DBOperator.getInstance().execQuery(SQLCommand.USER_EMAIL);
            cursor4 = DBOperator.getInstance().execQuery(SQLCommand.USER_PASS);

            while (cursor3.moveToNext() && cursor4.moveToNext()) {

                String cEmail = cursor3.getString(cursor3.getColumnIndex("Amb_Email"));

                String cPass = cursor4.getString(cursor4.getColumnIndex("Amb_Password"));

                if (email.equals(cEmail) && pass.equals(cPass)) {
                    int cId = cursor3.getInt(cursor3.getColumnIndex("Amb_ID"));
                    userCheck = true;
                    Toast.makeText(getBaseContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, UserMainActivity.class);
                    intent.putExtra("AmbID",cId);
                    this.startActivity(intent);
                    break;
                }
            }
                if(userCheck == false) {
                    Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }
           }else if (id == R.id.user_login_signup_button){
            Intent intent = new Intent(this,UserSignupActivity.class);
            this.startActivity(intent);
        }
    }
}
