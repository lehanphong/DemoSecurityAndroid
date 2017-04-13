package lhp.ducnt.demosecurityactivity.inhouse;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.util.Log;


/**
 * Created by ducnt on 4/11/17.
 */

public class SigPerm {
    public static boolean test(Context ctx, String sigPermName, String correctHash) {
        if (correctHash == null) return false;
        correctHash = correctHash.replaceAll(" ", "").replace(":","");
        Log.d("NTDLHP","SigPerm correctHash: "+correctHash);
        return correctHash.equals(hash(ctx, sigPermName));
    }

    public static String hash(Context ctx, String sigPermName) {
        if (sigPermName == null) return null;
        try {
            // Get the package name of the application which declares a permission named sigPermName.
            PackageManager pm = ctx.getPackageManager();
            PermissionInfo pi;
            pi = pm.getPermissionInfo(sigPermName, PackageManager.GET_META_DATA);
            String pkgname = pi.packageName;
            Log.d("NTDLHP","SigPerm packageName: "+pkgname);
            // Fail if the permission named sigPermName is not a Signature Permission
            if (pi.protectionLevel != PermissionInfo.PROTECTION_SIGNATURE) return null;
            // Return the certificate hash value of the application which declares a permission named sigPermName.
            String hash = PkgCert.hash(ctx, pkgname);
            Log.d("NTDLHP","SigPerm hash: "+hash);
            return hash;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }


}
