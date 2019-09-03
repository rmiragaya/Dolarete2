package first.tapdeveloper.dolarete;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

class DolareteRecyclerViewAdapter extends RecyclerView.Adapter<DolareteRecyclerViewAdapter.DolareteImageViewHolder> {
    private static final String TAG = "DolareteRecyclerViewAda";
    private ArrayList<Bancos> listaDeBancos;
    private ArrayList<Integer> mImagesNames;
    private Context context;

    public DolareteRecyclerViewAdapter(ArrayList<Bancos> listaDeBancos, Context context, ArrayList<Integer> mImagesNames) {
        this.listaDeBancos = listaDeBancos;
        this.context = context;
        this.mImagesNames = mImagesNames;
    }

    @NonNull
    @Override
    public DolareteImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardsdebancos, viewGroup, false);
        return new DolareteImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DolareteImageViewHolder dolareteImageViewHolder, int i) {

        final Bancos bancoItem =listaDeBancos.get(i);
        Log.d(TAG, "DESPUES DES ESTO ESTA EL PROBLEMA" + bancoItem.getVariacionDolar());

        Glide.with(context)
                .asBitmap()
                .load(mImagesNames.get(i))
                .into(dolareteImageViewHolder.mView);
        if (bancoItem.getCompraDolar() == "1") {
            bancoItem.setCompraDolar("$ ------");
        }

        dolareteImageViewHolder.mCompra.setText(bancoItem.getCompraDolar());


        if (bancoItem.getVentaDolar() == "1") {
            bancoItem.setVentaDolar("$ ------");
        }

        dolareteImageViewHolder.mVenta.setText(bancoItem.getVentaDolar());

        dolareteImageViewHolder.nombreBanco.setText(bancoItem.getNombreBanco());

        if (bancoItem.getVariacionDolar() == "1") {
            bancoItem.setVariacionDolar(" = --------%");
        }

        if (bancoItem.getVariacionDolar().substring(0,1).equalsIgnoreCase("-")){
            dolareteImageViewHolder.variacion.setTextColor(Color.parseColor("#c51f32"));
            dolareteImageViewHolder.variacion.setText("▼" + bancoItem.getVariacionDolar());
            //pintar de Rojo y agregar flecha

        } else if (bancoItem.getVariacionDolar().substring(0,1).equalsIgnoreCase("=") || bancoItem.getVariacionDolar().substring(0,1).equalsIgnoreCase(" ")){
            dolareteImageViewHolder.variacion.setTextColor(Color.parseColor("#808080"));
            dolareteImageViewHolder.variacion.setText(" " + bancoItem.getVariacionDolar());

        } else {
            dolareteImageViewHolder.variacion.setTextColor(Color.parseColor("#37a631"));
            dolareteImageViewHolder.variacion.setText("▲" + bancoItem.getVariacionDolar());
            //pintar de Verde y agregar flecha
        }

        dolareteImageViewHolder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChromeTab(bancoItem.getNombreBanco());
                Toast.makeText(context , bancoItem.getNombreBanco() , Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: tiene de tamano " + listaDeBancos.size());

        return listaDeBancos.size();

    }

    void loadNewData(ArrayList<Bancos> nuevaListadeBancos){
        listaDeBancos = nuevaListadeBancos;
        notifyDataSetChanged();
    }

    static class DolareteImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "DolareteImageViewHolder";

        ImageView mView = null;
        TextView mCompra = null;
        TextView mVenta = null;
        TextView variacion = null;
        TextView nombreBanco = null;
        ConstraintLayout mConstraintLayout;


        public DolareteImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "DolareteImageViewHolder: called");
            this.mView = itemView.findViewById(R.id.imageView11);
            this.mCompra = itemView.findViewById(R.id.textView);
            this.mVenta = itemView.findViewById(R.id.textView2);
            this.variacion = itemView.findViewById(R.id.variacionID);
            this.nombreBanco = itemView.findViewById(R.id.nombreBancos);
            this.mConstraintLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public void launchChromeTab(String nombreBanco) {

        String url = null;

        if (nombreBanco == "Nación"){
            url = "http://www.bna.com.ar/Personas";
        }
        if (nombreBanco == "Frances"){
            url = "https://www.bbvafrances.com.ar/";
        }
        if (nombreBanco == "Santander Río"){
            url = "https://www.santanderrio.com.ar/";
        }
        if (nombreBanco == "Supervielle"){
            url = "https://www.supervielle.com.ar/";
        }
        if (nombreBanco == "ICBC"){
            url = "https://www.icbc.com.ar/personas";
        }
        if (nombreBanco == "Hipotecario"){
            url = "https://www.hipotecario.com.ar/";
        }
        if (nombreBanco == "Ciudad"){
            url = "https://bancociudad.com.ar/";
        }
        if (nombreBanco == "Provincia"){
            url = "https://www.bancoprovincia.com.ar/";
        }
        if (nombreBanco == "Credicoop"){
            url = "https://www.bancocredicoop.coop/";
        }
        if (nombreBanco == "Patagania"){
            url = "http://www.bancopatagonia.com/personas/index.php";
        }
        if (nombreBanco == "Comafi") {
            url = "https://www.comafi.com.ar/";
        }

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#002040"));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
