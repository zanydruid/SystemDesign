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
 * Created by xyd on 16/4/25.
 */
public class AdminSignupActivity extends Activity implements View.OnClickListener {

    EditText usernameEdit, passEdit, passconEdit, positionEdit, codeEdit;
    Cursor codeCursor, usernameCursor;
    Button signupBtn;
    int adminID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_signup_layout);

        usernameEdit = (EditText) this.findViewById(R.id.admin_signup_username_editText);
        passEdit = (EditText) this.findViewById(R.id.admin_signup_password_editText);
        passconEdit = (EditText) this.findViewById(R.id.admin_signup_confirm_password_editText);
        positionEdit = (EditText) this.findViewById(R.id.admin_signup_position_editText);
        codeEdit = (EditText) this.findViewById(R.id.admin_signup_code_editText);

        signupBtn = (Button) this.findViewById(R.id.admin_signup_submit_button);
        signupBtn.setOnClickListener(this);

    }

    public void onClick(View v) {
        int id = v.getId();
        String username = usernameEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String passcon = passconEdit.getText().toString();
        String position = positionEdit.getText().toString();
        String code = codeEdit.getText().toString();
        Boolean codecheck = false;
        String usernamecheck = "1";
        Boolean flagallfields = false;

        if (id == R.id.admin_signup_submit_button) {

            if (username.matches("") || pass.matches("") || passcon.matches("") || position.matches("") || code.matches("")) {

                Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                return;

            } else {

                try {
                    DBOperator.copyDB(getBaseContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                codeCursor = DBOperator.getInstance().execQuery(SQLCommand.ADMIN_CODE);
                while (codeCursor.moveToNext()) {
                    String sqlcode = codeCursor.getString(codeCursor.getColumnIndex("Admin_ID"));

                    if (code.equals(sqlcode)) {
                        codecheck = true;
                        break;
                    }
                }

                usernameCursor = DBOperator.getInstance().execQuery("select Admin_Name from Administrator where Admin_ID = " + code);
                while (usernameCursor.moveToNext()) {
                    usernamecheck = usernameCursor.getString(usernameCursor.getColumnIndex("Admin_Name"));
                }

                if (codecheck == true && usernamecheck == null) {

                    if (!pass.equals(passcon)) {
                        Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String update = "update Administrator set Admin_Name = '" + username + "', Admin_Password = '" + pass + "', Admin_Position = '" + position + "' where Administrator.Admin_ID = " + code;
                        DBOperator.getInstance().execSQL(update);
                        Toast.makeText(getApplicationContext(), "Signup Success!", Toast.LENGTH_SHORT).show();

                        Cursor cursor = DBOperator.getInstance().execQuery("select max(Admin_ID) from Administrator");
                        while (cursor.moveToNext()){
                            adminID = cursor.getInt(cursor.getColumnIndex("max(Admin_ID)"));
                        }

                        Intent intent = new Intent(this,AdminMainActivity.class);
                        intent.putExtra("AdminID",adminID);
                        this.startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Admin ID.", Toast.LENGTH_SHORT).show();
                }
            }



        }
    }

}

