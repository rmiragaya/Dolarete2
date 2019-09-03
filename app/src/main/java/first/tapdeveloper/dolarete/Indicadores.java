package first.tapdeveloper.dolarete;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Indicadores extends AppCompatActivity implements JsoupSelectorIndices.OnDataAvailable {
    private static final String TAG = "Indicadores";

    //cartel de Loading
    // ProgressDialog cartelProgres;
    private ProgressBar mProgressBar;
    private TextView cargandoCotizaciones;


    //Toolbar
    private Toolbar toolbar;

    //RiesgoPais
    private  TextView riesgoPais;
    private TextView variacionRiesgo;
//    private RiesgoPais riesgoPaisClass;
    private CardView cardRiesgoPais;


    //Merval
    private  TextView montoMerval;
    private  TextView variacionMerval;
    private  TextView cierreUltimoMerval;
    private CardView cardMerval;

    //MervalArgentina
    private  TextView montoMervalArgentina;
    private  TextView variacionMervalArgentina;
    private  TextView cierreUltimoMervalArgentina;
    private  CardView cardMervalArgentina;

    //Oro
    private TextView montoOro;
    private  TextView variacionOro;
    private  CardView cardOro;

    //Bitcoin
    private TextView montoBitcoin;
    private  TextView variacionBitCoin;
    private CardView cardBitCoin;

    JsoupSelectorIndices jsoupSelectorIndices = new JsoupSelectorIndices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicadores);


        // seteando NUESTRA toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Iniciar Mobile Ads
        MobileAds.initialize(this, "cdsfsdfs83");
        AdView adView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        //catgando/descargando
        mProgressBar = findViewById(R.id.indeterminateBar);
        cargandoCotizaciones = findViewById(R.id.cargando);
        loadingAnimation();

        // RiesgoPais
        riesgoPais = findViewById(R.id.riesgopaisnumero);
        variacionRiesgo = findViewById(R.id.variacionRiesgoPais);
        cardRiesgoPais = findViewById(R.id.riesgoPais);

        //Merval
        montoMerval = findViewById(R.id.mervalNumero);
        variacionMerval = findViewById(R.id.mervalVariacion);
        cierreUltimoMerval = findViewById(R.id.cierreAnteriorId);
        cardMerval = findViewById(R.id.sypMerval);

        //Merval Argentina
        montoMervalArgentina = findViewById(R.id.montoMervalArgentinaid);
        variacionMervalArgentina = findViewById(R.id.variacionMervalArgentinaId);
        cierreUltimoMervalArgentina = findViewById(R.id.cierreMervalArgentinaId);
        cardMervalArgentina = findViewById(R.id.mervalArgentinaCardId);

        //Oro
        montoOro = findViewById(R.id.oroMonto);
        variacionOro = findViewById(R.id.oroVariacion);
        cardOro = findViewById(R.id.oro);

        //BitCoin
        montoBitcoin = findViewById(R.id.montoBitCoinId);
        variacionBitCoin = findViewById(R.id.variacionBitCoinId);
        cardBitCoin = findViewById(R.id.bitCoinId);


        // seteando NUESTRA toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cardRiesgoPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab("http://data.ambito.com/economia/mercados/riesgo-pais/");
            }
        });

        cardMerval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab("http://data.ambito.com/economia/mercados/indices/merval/");
            }
        });

        cardOro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab("http://data.ambito.com/economia/mercados/metales/oro/");
            }
        });

        cardMervalArgentina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab("http://data.ambito.com/economia/mercados/indices/info/?ric=.MAR");
            }
        });

        cardBitCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab("https://es.investing.com/crypto/bitcoin");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerIndices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item2:
                Intent cotizacionesIntent = new Intent(this, MainActivity.class);
                startActivity(cotizacionesIntent);
                finish();
                break;
            case R.id.item3:
                openSobreEstaAppDialog();
                break;
            case R.id.item4:
                try {
                    //aca debiera ir MI pacage name
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e ){
                    launchChromeTabtoRateApp();
                }
                break;
            case R.id.item5:
                launchChromeTab("https://sites.google.com/view/tapdeveloperprivacypolicy/p%C3%A1gina-principal");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataAvailable(ArrayList<Bancos> mArrayListaBancos, DownloadStatuusIndices status) {
        if (status == DownloadStatuusIndices.OK){
            Log.d(TAG, "Indicadores Lista De Indices " + mArrayListaBancos);

            Log.d(TAG, "Data de indcadores listo, ahora armar los set.text ") ;

            riesgoPais.setText(mArrayListaBancos.get(0).getCompraDolar());


            //color de variacion Riesgo Pais
            if (mArrayListaBancos.get(0).getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
                variacionRiesgo.setTextColor(Color.parseColor("#4ccc3f"));
                variacionRiesgo.setText("▼" + mArrayListaBancos.get(0).getVariacionDolar());


            } else {
                variacionRiesgo.setTextColor(Color.parseColor("#c51f32"));
                variacionRiesgo.setText("▲" + mArrayListaBancos.get(0).getVariacionDolar());

            }


            //Carga Merval
            montoMerval.setText(mArrayListaBancos.get(1).getCompraDolar());
            cierreUltimoMerval.setText(mArrayListaBancos.get(1).getCierre());

            //Color de variacion MeRval
            if (mArrayListaBancos.get(1).getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
                variacionMerval.setTextColor(Color.parseColor("#c51f32"));
                variacionMerval.setText("▼" + mArrayListaBancos.get(1).getVariacionDolar());


            } else {
                variacionMerval.setTextColor(Color.parseColor("#4ccc3f"));
                variacionMerval.setText("▲" + mArrayListaBancos.get(1).getVariacionDolar());


            }

            //Merval Argentina

            montoMervalArgentina.setText(mArrayListaBancos.get(2).getCompraDolar());
            cierreUltimoMervalArgentina.setText(mArrayListaBancos.get(2).getCierre());

            //Color de variacion MeRval
            if (mArrayListaBancos.get(2).getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
                variacionMervalArgentina.setTextColor(Color.parseColor("#c51f32"));
                variacionMervalArgentina.setText("▼" + mArrayListaBancos.get(2).getVariacionDolar());


            } else {
                variacionMervalArgentina.setTextColor(Color.parseColor("#4ccc3f"));
                variacionMervalArgentina.setText("▲" + mArrayListaBancos.get(2).getVariacionDolar());
            }


            //Oro
            montoOro.setText(mArrayListaBancos.get(3).getCompraDolar());

            //Color de variacion Oro
            if (mArrayListaBancos.get(3).getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
                variacionOro.setTextColor(Color.parseColor("#c51f32"));
                variacionOro.setText("▼" + mArrayListaBancos.get(3).getVariacionDolar());


            } else {
                variacionOro.setTextColor(Color.parseColor("#4ccc3f"));
                variacionOro.setText("▲" + mArrayListaBancos.get(3).getVariacionDolar());
            }

            //BitCoin

            montoBitcoin.setText(mArrayListaBancos.get(4).getCompraDolar());

            //Color de variacion Oro
            if (mArrayListaBancos.get(4).getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
                variacionBitCoin.setTextColor(Color.parseColor("#c51f32"));
                variacionBitCoin.setText("▼" + mArrayListaBancos.get(4).getVariacionDolar());


            } else {
                variacionBitCoin.setTextColor(Color.parseColor("#4ccc3f"));
                variacionBitCoin.setText("▲" + mArrayListaBancos.get(4).getVariacionDolar());
            }

        } else {
            openDialog();
            Log.d(TAG, "Indicadores Jsoup no valido " + mArrayListaBancos);
        }

        loadingAnimationOff();
    }


    public void openSobreEstaAppDialog(){
        SobreEstaAppDialog exampleDialog = new SobreEstaAppDialog();
        exampleDialog.show(getSupportFragmentManager(), "Sin coneccion");
    }

    public void launchChromeTabtoRateApp(){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#002040"));
        CustomTabsIntent customTabsIntent = builder.build();
        //aca debiera ir MI pacage name
        customTabsIntent.launchUrl(this, Uri.parse("http://play.google.com/store.apps/details?id=" + getPackageName()));
    }

    public void launchChromeTab(String url) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#002040"));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    public void loadingAnimation(){
        mProgressBar.setVisibility(View.VISIBLE);
        cargandoCotizaciones.setVisibility(View.VISIBLE);
    }

    public void loadingAnimationOff(){
        mProgressBar.setVisibility(View.INVISIBLE);
        cargandoCotizaciones.setVisibility(View.INVISIBLE);
    }

    public void obtenerIndices(){
        jsoupSelectorIndices.executeOnSameThread();
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Sin coneccion");
    }
}
