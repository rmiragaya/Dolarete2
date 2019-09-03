package first.tapdeveloper.dolarete;

import android.util.Log;

import java.util.ArrayList;

class JsoupSelectorIndices implements GetRowDataIndices.OnDownloadCompleteIndices {

    private static final String TAG = "JsoupSelectorIndices";

    private ArrayList<Bancos> mArrayListaBancos = null;
    //    private String moneda;
    private Boolean runingOnSameThread = false;

    private final JsoupSelectorIndices.OnDataAvailable mCallBack;

    interface OnDataAvailable {
        void onDataAvailable (ArrayList<Bancos> mArrayListaBancos, DownloadStatuusIndices status);
    }

    public JsoupSelectorIndices(OnDataAvailable mCallBack) {
        this.mCallBack = mCallBack;
    }


    public void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread: Starts");
        runingOnSameThread = true;
        GetRowDataIndices getRowData = new GetRowDataIndices(this);
        getRowData.execute();
        Log.d(TAG, "executeOnSameThread: finish");
    }

    @Override
    public void onDownloadCompleteIndices(ResultadoDeJsoup resultadoDeJsoup, DownloadStatuusIndices status) {
            this.mArrayListaBancos = new ArrayList<>();

            //acatodo el parseo de indices

            Bancos riesgoPais = new Bancos("riesgoPais");
            Bancos merval = new Bancos("merval");
            Bancos mervalArgentina = new Bancos("mervalArgentina");
            Bancos oro = new Bancos("Oro");
            Bancos bitcoin = new Bancos("Bitcoin");

            status = DownloadStatuusIndices.OK;

            try {
                riesgoPais.setCompraDolar(resultadoDeJsoup.getDocumentoRiesgoPais().select("strong").get(0).text());
                riesgoPais.setVariacionDolar(resultadoDeJsoup.getDocumentoRiesgoPais().select("strong").get(1).text());

                merval.setCompraDolar(resultadoDeJsoup.getDocumentoDolarMerval().select("big").get(1).text());
                merval.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarMerval().select("big").get(0).text());
                merval.setCierre(resultadoDeJsoup.getDocumentoDolarMerval().select("big").get(6).text());

                mervalArgentina.setCompraDolar(resultadoDeJsoup.getDocumentoDolarMervalArtengina().select("big").get(1).text());
                mervalArgentina.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarMervalArtengina().select("big").get(0).text());
                mervalArgentina.setCierre(resultadoDeJsoup.getDocumentoDolarMervalArtengina().select("big").get(6).text());

                oro.setCompraDolar(resultadoDeJsoup.getDocumentoOro().select("big").get(1).text());
                oro.setVariacionDolar(resultadoDeJsoup.getDocumentoOro().select("big").get(0).text());

                bitcoin.setCompraDolar(resultadoDeJsoup.getDocumentoBitcoin().select("span.inlineblock").get(2).text());
                bitcoin.setVariacionDolar(resultadoDeJsoup.getDocumentoBitcoin().select("span.arial_20.parentheses").get(0).text());

                this.mArrayListaBancos.add(riesgoPais);
                this.mArrayListaBancos.add(merval);
                this. mArrayListaBancos.add(mervalArgentina);
                this. mArrayListaBancos.add(oro);
                this.mArrayListaBancos.add(bitcoin);

            } catch (Exception e) {
                Log.d(TAG, "doInBackground: Error");
                status = DownloadStatuusIndices.FAILED_OR_EMPTY;
                e.printStackTrace();
            }

        if (mCallBack !=null){
            //informamos que el proceso de seleccón está hecho
            //o quizas error
            mCallBack.onDataAvailable(this.mArrayListaBancos, status);
        }



        }
}

