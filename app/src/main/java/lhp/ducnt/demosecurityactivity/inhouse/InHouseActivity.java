package lhp.ducnt.demosecurityactivity.inhouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import lhp.ducnt.demosecurityactivity.BuildConfig;
import lhp.ducnt.demosecurityactivity.R;

/**
 * Created by ducnt on 4/11/17.
 */

public class InHouseActivity extends AppCompatActivity {
    // In-house Signature Permission
    private static final String DUCNT_PERMISSION = "lhp.ducnt.DUCNT_PERMISSION";
    // In-house certificate hash value
    private static String sMyCertHash = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inhouse);
        // *** POINT 6 *** Verify that the in-house signature permission is defined by an in-house application.
        if (!SigPerm.test(this, DUCNT_PERMISSION, myCertHash(this))) {
            Toast.makeText(this, "The in-house signature permission is not declared by in-house application.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // *** POINT 7 *** Handle the received intent carefully and securely, even though the intent was sent from an in-house application.
        // Omitted, since this is a sample. Please refer to "3.2 Handling Input Data Carefully and Securely."
        String param = getIntent().getStringExtra("PARAM");
        Toast.makeText(this, String.format("Received param: %s", param), Toast.LENGTH_LONG).show();
    }

    private static String myCertHash(Context context) {
        if (sMyCertHash == null) {
            if (BuildConfig.DEBUG) {
                // Certificate hash value of "androiddebugkey" in the debug.keystore.
                sMyCertHash = "7A:C4:8D:D8:4C:99:9F:20:A1:97:85:ED:9C:80:BF:B0:4B:C7:60:28:B5:1E:91:81:68:C1:4C:2C:5A:5D:49:4F";
            } else {
                // Certificate hash value of "my company key" in the keystore.
                sMyCertHash = "AF:52:BA:4E:71:E2:B3:CF:69:7C:1A:59:99:6A:D6:59:90:78:73:5E:15:E6:23:FC:26:81:91:4D:C2:85:AA:34";
            }
        }
        return sMyCertHash;
    }

    public void onReturnResultClick(View view) {
        // *** POINT 8 *** Sensitive information can be returned since the requesting application is in-house.
        Intent intent = new Intent();
        intent.putExtra("RESULT", "Sensitive Info");
        setResult(RESULT_OK, intent);
        finish();
    }
}
