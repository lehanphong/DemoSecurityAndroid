package lhp.ducnt.demosecurityactivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ducnt on 4/10/17.
 */

public class PrivateActivity extends AppCompatActivity {
    @BindView(R.id.ivShowPara)
    ImageView ivShowPara;
    private String urlStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        ButterKnife.bind(this);
        urlStr = getPara();
        if(urlStr!=null && urlStr.length()>0) {
            Log.d("NTDLHP","PARA: "+ urlStr);
            Picasso.with(this)
                    .load(urlStr)
                    .resize(500, 400)
                    .centerCrop()
                    .into(ivShowPara);
        }
    }

    private String getPara() {
        // *** POINT 4 *** Handle the received Intent carefully and securely, even though the Intent was sent from the same application.
        try {
            String para = getIntent().getStringExtra(ConstApp.PARA_URL);
            URL urlPara = new URL(para);
            String protocol = urlPara.getProtocol();
            if (!"http".equals(protocol) && !"https".equals(protocol)) {
                throw new MalformedURLException("invalid protocol");
            }
            return para;
        } catch (MalformedURLException e) {
            Toast.makeText(this, "MalformedURLException", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_LONG).show();
        }
        return "";
    }

    @OnClick(R.id.ivShowPara)
    public void clickImageVIew() {
        if(urlStr!=null && urlStr.length()>0) {
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.btResult)
    public void onReturnResultClick(View view) {
        // *** POINT 5 *** Sensitive information can be sent since it is sending and receiving all within the same app lication.
        Intent intent = new Intent();
        intent.putExtra("RESULT", "Sensitive Info");
        setResult(RESULT_OK, intent);
        finish();
    }
}
