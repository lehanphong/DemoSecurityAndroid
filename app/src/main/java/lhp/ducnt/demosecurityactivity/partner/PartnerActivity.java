package lhp.ducnt.demosecurityactivity.partner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import lhp.ducnt.demosecurityactivity.BuildConfig;
import lhp.ducnt.demosecurityactivity.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ducnt on 4/11/17.
 */

public class PartnerActivity extends AppCompatActivity{
    private static PkgCertWhitelists sWhitelists = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);
        // *** POINT 4 *** Verify the requesting application's certificate through a predefined whitelist.
        if (!checkPartner(this, getCallingActivity().getPackageName())) {
            Toast.makeText(this,"Requesting application is not a partner application.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // *** POINT 5 *** Handle the received intent carefully and securely,
        // even though the intent was sent from a partner application.
        Toast.makeText(this, "Accessed by Partner App", Toast.LENGTH_LONG).show();
    }


    private static boolean checkPartner(Context context, String pkgname) {
        if (sWhitelists == null) buildWhitelists(context);
        return sWhitelists.test(context, pkgname);
    }

    private static void buildWhitelists(Context context) {
        boolean isdebug = BuildConfig.DEBUG;
        final String PACKAGE_WHITE = "lhp.ducnt.demousingsecurityactivity";
        sWhitelists = new PkgCertWhitelists();
        // Register certificate hash value
        sWhitelists.add(PACKAGE_WHITE, isdebug ?
                // Certificate hash value of "androiddebugkey" in the debug.keystore.
                "7A:C4:8D:D8:4C:99:9F:20:A1:97:85:ED:9C:80:BF:B0:4B:C7:60:28:B5:1E:91:81:68:C1:4C:2C:5A:5D:49:4F" :
                // Certificate hash value of "Activity call to" in the keystore.
                "4A:BD:53:31:D5:2D:B6:3E:34:86:D7:5A:B0:49:47:B6:FE:87:B5:17:F4:09:4A:37:BD:CE:C1:77:B5:60:CF:49");
    }

    public void onReturnResultClick(View view) {
        // *** POINT 6 *** Only return Information that is granted to be disclosed to a partner application.
        Intent intent = new Intent();
        intent.putExtra("RESULT", "Information for partner applications");
        setResult(RESULT_OK, intent);
        finish();
    }
}
