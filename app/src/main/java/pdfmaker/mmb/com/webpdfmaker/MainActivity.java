package pdfmaker.mmb.com.webpdfmaker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.adad.client.AdListener;
import ir.adad.client.AdView;
import ir.adad.client.Adad;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText url;
    Button btnPdf;
    Button btnGo;
    WebView webView;
    String url2;

    @Override
    public void onBackPressed() {
        webView.goBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        BindViews();

//        url2="http://hamyardeveloper.ir/";
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url.getText().toString().equals("")) {
                    url2 = url.getText().toString();
                    ProgressDialog.show(MainActivity.this, "",
                            "Loading. Please wait...", true).setCancelable(true);
                    webView.loadUrl(url2);

                } else {
                    Toast.makeText(MainActivity.this, "Address Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                    createWebPrintJob(webView);
                } else {
                    Toast.makeText(MainActivity.this, "Your android  version not support", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void BindViews() {
        url = findViewById(R.id.url);
        btnPdf = findViewById(R.id.btnPdf);
        btnGo = findViewById(R.id.btnGo);
        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient());
        webView.setDrawingCacheEnabled(true);
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter = null;

        printAdapter = webView.createPrintDocumentAdapter();


        String jobName = getString(R.string.app_name) + " Print Test";

        if (printManager != null) {
            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
        }
    }

}
