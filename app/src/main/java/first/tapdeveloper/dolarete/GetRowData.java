package first.tapdeveloper.dolarete;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

class GetRowData extends AsyncTask<String,Void, ResultadoDeJsoup> {

    private static final String TAG = "GetRowData";

    private DownloadStatus mDownloadStatus;
    private String moneda = null;
    private final OnDownloadComplete mCallBack;

    //Para estar seguros que la actividad posterior a esta tenga su metodo OnDownloadComplete
    interface OnDownloadComplete {
        void onDownloadComplete (ResultadoDeJsoup resultadoDeJsoup, DownloadStatus status, String moneda);
    }


    public GetRowData(OnDownloadComplete mCallBack) {
       this.mDownloadStatus = DownloadStatus.IDLE;
       this.mCallBack = mCallBack;
    }


    @Override
    protected ResultadoDeJsoup doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: moneda antes del link es " + strings[0]);
        this.mDownloadStatus = DownloadStatus.PROCESSING;

            try{
                org.jsoup.nodes.Document docBancoNacion = Jsoup.connect("http://www.bna.com.ar/Personas").get();
                org.jsoup.nodes.Document docInfoDolar = Jsoup.connect("https://www.infodolar.com/").get();

                if (strings[0].equals("dolar")){
                    moneda = "dolar";

                } else if (strings[0].equals("euro")){
                    moneda = "euro";
                }

                ResultadoDeJsoup resultadoDeJsoup = new ResultadoDeJsoup(docBancoNacion,docInfoDolar);

                Log.d(TAG, "docBancoNacion: " +docBancoNacion);
                Log.d(TAG, "docInfoDolar: " + docInfoDolar);

                this.mDownloadStatus = DownloadStatus.OK;

                return resultadoDeJsoup;

            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error");
                this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                e.printStackTrace();
            }

        return null;
    }

    void runInSameThread(String s){
        Log.d(TAG, "moneda es " + s);
        Log.d(TAG, "runInSameThread: start");

//        onPostExecute(doInBackground(s));
        mCallBack.onDownloadComplete(doInBackground(s), mDownloadStatus, s);


        Log.d(TAG, "runInSameThread: Ends");

    }

    @Override
    protected void onPostExecute(ResultadoDeJsoup resultadoDeJsoup) {

        if (mCallBack != null){
            mCallBack.onDownloadComplete(resultadoDeJsoup, mDownloadStatus, moneda);
        }


    }



}
