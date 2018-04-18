package baseballRecruitment.jd;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import android.app.ProgressDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import android.widget.Toast;

import android.support.annotation.NonNull;

import baseballRecruitment.jd.DataLayer.Account.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@EActivity(R.layout.activity_registration)
public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    boolean registerBool = false;

    @ViewById
    TextView email_response;

    @ViewById
    TextView username_response;

    @ViewById
    TextView password_response;

    @ViewById
    TextView confirm_password;

    @ViewById
    TextView org_response;

    @ViewById
    TextView name_response;

    @AfterViews
    protected void init() {
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void register(View v) {
        // TODO Encapsulate later
        final String email = email_response.getText().toString();
        final String username = username_response.getText().toString();
        final String password = password_response.getText().toString();
        final String organization = org_response.getText().toString();
        final String name = name_response.getText().toString();

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        //creates the user and directs to correct homepage
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            registerBool = true;
                            String userID = mAuth.getCurrentUser().getUid();
                            Account newUser = new Account(name, email, username, password, organization);
                            DatabaseReference currentUserDB = mDatabase.child(userID);
                            currentUserDB.setValue(newUser);
                            progressDialog.dismiss();
                            HomePage_.intent(Registration.this).start();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "Could not register... Please try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onNothingSelected(AdapterView<?> parent) {
        String role = "User";
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String role = parent.getItemAtPosition(position).toString();
    }
}
