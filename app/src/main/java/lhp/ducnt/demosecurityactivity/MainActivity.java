package lhp.ducnt.demosecurityactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //using private activity

    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.tvNoti)
    TextView tvNoti;

    private final int REQUEST_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btLogin)
    public void Login() {
        String password = edtPassword.getText().toString();
        String username = edtUserName.getText().toString();
        if (password != null && password.length() > 0 && username != null && username.length() > 0) {
            if ("123456".equals(password) && "ducnt".equals(username)) {
                // *** POINT 6 *** Do not set the FLAG_ACTIVITY_NEW_TASK flag for intents to start an activity.
                // *** POINT 7 *** Use the explicit Intents with the class specified to call an activity in the same application.
                Intent intent = new Intent(this, PrivateActivity.class);
                // *** POINT 8 *** Sensitive information can be sent only by putExtra() since the destination activity is in the same application
                intent.putExtra(ConstApp.PARA_URL, "https://vnpay.vn/Uploads/images/2017_%20VCB-MobileBanking%20CTKM%20ONLINE_Man%20hinh%20ATM%20(800x600)(2).jpg");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_CODE);
//                finish();
            } else {
                Toast.makeText(this, "Wrong password or username", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Empty password or username", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.tvNoti)
    public void showActivityNoti() {
        startActivity(new Intent(this, VisibilityPrivateNotificationActivity.class));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CODE:
                String result = data.getStringExtra(ConstApp.PARA_RESULT);
                // *** POINT 9 *** Handle the received data carefully and securely,
                // even though the data comes from an activity within the same application.
                Toast.makeText(this, String.format("Received result: %s", result), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
