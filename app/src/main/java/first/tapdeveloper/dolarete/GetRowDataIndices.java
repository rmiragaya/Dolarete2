package first.tapdeveloper.dolarete;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

enum DownloadStatuusIndices { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

class GetRowDataIndices extends AsyncTask<Void, Void, ResultadoDeJsoup> {
    private static final String TAG = "GetRowDataIndices";
    private DownloadStatuusIndices mDownloadStatus;
    private String moneda = null;
    private final OnDownloadCompleteIndices mCallback;


    interface OnDownloadCompleteIndices {
        void onDownloadCompleteIndices (ResultadoDeJsoup resultadoDeJsoup, DownloadStatuusIndices status);
    }

    public GetRowDataIndices(OnDownloadCompleteIndices mCallback) {
        this.mDownloadStatus = DownloadStatuusIndices.IDLE;
        this.mCallback = mCallback;
    }

    @Override
    protected void onPostExecute(ResultadoDeJsoup resultadoDeJsoup) {
        if (mCallback != null){
            mCallback.onDownloadCompleteIndices(resultadoDeJsoup, mDownloadStatus);
        }
    }

    @Override
    protected ResultadoDeJsoup doInBackground(Void... voids) {

        try {

            org.jsoup.nodes.Document documentoRiesgoPais = Jsoup.connect("http://data.ambito.com/economia/mercados/riesgo-pais/").get();
            org.jsoup.nodes.Document documentoDolarMerval = Jsoup.connect("http://data.ambito.com/economia/mercados/indices/info/?ric=.MERV").get();
            org.jsoup.nodes.Document documentoDolarMervalArtengina = Jsoup.connect("http://data.ambito.com/economia/mercados/indices/info/?ric=.MAR").get();
            org.jsoup.nodes.Document documentoOro = Jsoup.connect("http://data.ambito.com/economia/mercados/metales/oro/").get();
            org.jsoup.nodes.Document documentoBitcoin = Jsoup.connect("https://es.investing.com/crypto/bitcoin").get();

            ResultadoDeJsoup resultadoDeJsoupIndices = new ResultadoDeJsoup(documentoRiesgoPais, documentoDolarMerval, documentoDolarMervalArtengina,documentoOro, documentoBitcoin  );

            this.mDownloadStatus = DownloadStatuusIndices.OK;

            return resultadoDeJsoupIndices;



        } catch (Exception e) {
            Log.d(TAG, "doInBackground: Error");
            this.mDownloadStatus = DownloadStatuusIndices.FAILED_OR_EMPTY;
            e.printStackTrace();
        }

        return null;
    }
}
