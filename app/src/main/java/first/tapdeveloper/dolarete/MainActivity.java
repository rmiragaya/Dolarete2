package first.tapdeveloper.dolarete;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity implements JsoupSelector.OnDataAvailable {
    private static final String TAG = "MainActivity";

    //cartel de Loading
    // ProgressDialog cartelProgres;
    private ProgressBar mProgressBar;
    private TextView cargandoCotizaciones;

    //Toolbar
    private  Toolbar toolbar;

    //Titulo
    private  TextView titulo;

    //vars imagenes
    private ArrayList<Integer> mNamesimages = new ArrayList<>();

    //RecyclerAdapter
    private DolareteRecyclerViewAdapter dolareteRecyclerViewAdapter;

    //BotonCircular
    private CircularProgressButton boton;

    //Es Dolar?
    private  Boolean esDolar = true;


    JsoupSelector jsoupSelector = new JsoupSelector(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // Iniciar Mobile Ads
        MobileAds.initialize(this, "5165165");
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cargadoDeImagenes();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dolareteRecyclerViewAdapter = new DolareteRecyclerViewAdapter(new ArrayList<Bancos>(), this, mNamesimages);
        recyclerView.setAdapter(dolareteRecyclerViewAdapter);


        //Titulo
        titulo = findViewById(R.id.titulo);


        //        showLoadingAnimation();
        mProgressBar = findViewById(R.id.indeterminateBar);
        cargandoCotizaciones = findViewById(R.id.cargando);
        loadingAnimation();

        //BotonCircular
        boton = findViewById(R.id.button);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton.startAnimation();
                loadingAnimation();
                actualizarDolarOEuro();

            }
        });

////        PARA PROBAR SI FUNCIONABA, AHORA NO SIRVE
//        GetRowData getRowData = new GetRowData(this);
//        getRowData.execute();


        Log.d(TAG, "onCreate: Ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: Starts");
        super.onResume();
        actualizarDolarOEuro();

//        jsoupSelector.execute("dolar");
        Log.d(TAG, "onResume: Ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                titulo = findViewById(R.id.titulo);
                if (esDolar){
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.example_menu1);
                    titulo.setText("COTIZACIÓN EURO");
                    esDolar = false;
                    actualizarDolarOEuro();
                    boton.startAnimation();
                    loadingAnimation();

                } else {
                    toolbar.getMenu().clear();
                    toolbar.inflateMenu(R.menu.menu1);
                    titulo.setText("COTIZACIÓN DOLAR");
                    esDolar = true;
                    actualizarDolarOEuro();
                    boton.startAnimation();
                    loadingAnimation();
                }
                break;
            case R.id.item2:
                Intent indicadoresIntent = new Intent(this, Indicadores.class);
                startActivity(indicadoresIntent);
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
    public void onDataAvailable (ArrayList<Bancos> mArrayListaBancos, DownloadStatus status){
        if (status == DownloadStatus.OK){
            dolareteRecyclerViewAdapter.loadNewData(mArrayListaBancos);
            outroAnimacionBotonOk();
            Log.d(TAG, "onDataAvailable Jsoup valido " + mArrayListaBancos);

        } else if (status == DownloadStatus.FAILED_OR_EMPTY){
            outroAnimacionBotonError();
            openDialog();
            Log.d(TAG, "onDataAvailable Jsoup no valido");
        } else if (status == DownloadStatus.IDLE){

        }

        loadingAnimationOff();
        boton.setVisibility(View.VISIBLE);
    }

    private void cargadoDeImagenes(){
        mNamesimages.add(R.drawable.banconacion2);
        mNamesimages.add(R.drawable.bancofrances2);
        mNamesimages.add(R.drawable.bancosantanderrio2);
        mNamesimages.add(R.drawable.bancosupervielle2);
        mNamesimages.add(R.drawable.icbc2);
        mNamesimages.add(R.drawable.hipotecario2);
        mNamesimages.add(R.drawable.ciudad);
        mNamesimages.add(R.drawable.provincia);
        mNamesimages.add(R.drawable.credicoop);
        mNamesimages.add(R.drawable.patagonia);
        mNamesimages.add(R.drawable.comafi);
//        mNamesimages.add(R.drawable.bancogalicia2);
    }


    public void loadingAnimation(){
        mProgressBar.setVisibility(View.VISIBLE);
        cargandoCotizaciones.setVisibility(View.VISIBLE);
    }

    public void loadingAnimationOff(){
        mProgressBar.setVisibility(View.INVISIBLE);
        cargandoCotizaciones.setVisibility(View.INVISIBLE);
    }

    public void outroAnimacionBotonOk(){
        //ANIMACION DE CIERRE Y REVERSE

        boton.doneLoadingAnimation(Color.parseColor("#FF31C114"),BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boton.revertAnimation();
            }
        },1000);

    }

    public void outroAnimacionBotonError(){
        //ANIMACION DE CIERRE Y REVERSE
            boton.doneLoadingAnimation(Color.parseColor("#FF130C"), BitmapFactory.decodeResource(getResources(),R.drawable.ic_done_white_48dp));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boton.revertAnimation();
            }
        },1000);

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

    public void actualizarDolarOEuro(){
        if (esDolar){
            jsoupSelector.executeOnSameThread("dolar");

        } else {
            jsoupSelector.executeOnSameThread("euro");

        }
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Sin coneccion");
    }























}
