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
 * Created by xyd on 16/4/7.
 */
public class AdminLoginActivity extends Activity implements View.OnClickListener {
    int adminID;
    ImageButton loginBtn;
    ImageButton signupBtn;
    EditText usernameEdit,passwordEdit;
    String username,password;
    Boolean adminCheck = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_layout);

        usernameEdit = (EditText)this.findViewById(R.id.admin_login_username_editText);
        passwordEdit = (EditText)this.findViewById(R.id.admin_login_password_editText);


        loginBtn = (ImageButton) this.findViewById(R.id.admin_login_entry_button);
        loginBtn.setOnClickListener(this);
        signupBtn = (ImageButton) this.findViewById(R.id.admin_login_signup_button);
        signupBtn.setOnClickListener(this);

        usernameEdit.setText("");
        passwordEdit.setText("");

        //copy database file

    }

    public void onClick(View v){
        int id = v.getId();

        username = this.usernameEdit.getText().toString();
        password = this.passwordEdit.getText().toString();

        Cursor cursor1,cursor2;

        if(id == R.id.admin_login_entry_button){
            cursor1 = DBOperator.getInstance().execQuery(SQLCommand.ADMIN_USERID);
            cursor2 = DBOperator.getInstance().execQuery(SQLCommand.ADMIN_PASS);


        while(cursor1.moveToNext() && cursor2.moveToNext()){
            String sqlName = cursor1.getString(cursor1.getColumnIndex("Admin_ID"));
            String sqlPass = cursor2.getString(cursor2.getColumnIndex("Admin_Password"));

            if(username.equals(sqlName) && password.equals(sqlPass)){
                adminCheck = true;
                Toast.makeText(getBaseContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                adminID = Integer.parseInt(sqlName);
                Intent intent = new Intent(this,AdminMainActivity.class);
                intent.putExtra("AdminID",adminID);
                this.startActivity(intent);
                break;
            }
        }
            if(adminCheck == false) {
                Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            }

        }else if(id == R.id.admin_login_signup_button){
            Intent intent = new Intent(this,AdminSignupActivity.class);
            this.startActivity(intent);
        }

    }
}
