package first.tapdeveloper.dolarete;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

class JsoupSelector extends AsyncTask<String, Void, ArrayList<Bancos>> implements GetRowData.OnDownloadComplete {
    private static final String TAG = "JsoupSelector";

    private ArrayList<Bancos> mArrayListaBancos = null;
//    private String moneda;
    private Boolean runingOnSameThread = false;

    private final OnDataAvailable mCallBack;

    interface OnDataAvailable {
        void onDataAvailable (ArrayList<Bancos> mArrayListaBancos, DownloadStatus status);
    }


    public JsoupSelector(OnDataAvailable mCallBack) {
        Log.d(TAG, "JsoupSelector: Call");
//        this.moneda = moneda;
        this.mCallBack = mCallBack;
    }


    public void executeOnSameThread(String moneda) {
        Log.d(TAG, "executeOnSameThread: Starts");
        runingOnSameThread = true;
        GetRowData getRowData = new GetRowData(this);
        getRowData.execute(moneda);
        Log.d(TAG, "executeOnSameThread: finish");
    }

    @Override
    protected void onPostExecute(ArrayList<Bancos> bancos) {
        Log.d(TAG, "onPostExecute: Starts");

        if (mCallBack != null){
            mCallBack.onDataAvailable(this.mArrayListaBancos, DownloadStatus.OK);
        }

        Log.d(TAG, "onPostExecute: Finish");
    }



    @Override
    protected ArrayList<Bancos> doInBackground(String... moneda) {
        Log.d(TAG, "doInBackground: Starts");
        Log.d(TAG, "doInBackground: moneda es" + moneda[0]);
        GetRowData getRowData = new GetRowData(this);
        getRowData.runInSameThread(moneda[0]);

        Log.d(TAG, "doInBackground: Termina");
        return this.mArrayListaBancos;
    }



    @Override
    public void onDownloadComplete(ResultadoDeJsoup resultadoDeJsoup, DownloadStatus status, String moneda) {
        Log.d(TAG, "onDownloadComplete: ARRANCA");
        Log.d(TAG, "JsopSeleccionador: Stars moneda es " + moneda);

        if (status == DownloadStatus.OK){





                this.mArrayListaBancos = new ArrayList<>();


                Bancos nacion = new Bancos("Nación");
                Bancos frances = new Bancos("Frances");
                Bancos sandanter = new Bancos("Santander Río");
                Bancos supervielle = new Bancos("Supervielle");
                Bancos icbc = new Bancos("Icbc");
                Bancos hipotecario = new Bancos("Hipotecario");
                Bancos ciudad = new Bancos("Ciudad");
                Bancos privincia = new Bancos("Provincia");
                Bancos credicoop = new Bancos("Credicoop");
                Bancos patagonia = new Bancos("Patagonia");
                Bancos comafi = new Bancos("Comafi");
                Bancos galicia = new Bancos("Galicia");

                if (moneda.equalsIgnoreCase("dolar")){

                    try {


                        nacion.setCompraDolar("$ " + resultadoDeJsoup.getDocumentoBna().select("td:nth-of-type(2)").get(0).text().substring(0, 5));
                        nacion.setVentaDolar("$ " + resultadoDeJsoup.getDocumentoBna().select("td:nth-of-type(3)").get(0).text().substring(0, 5));

                        for (int i = 2; i <41 ; i++ ){

                            if ((resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Nación")) {
                                nacion.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, nacion.toString());
                            }



                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("BBVA Banco Francés")){
                                frances.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                frances.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                frances.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, frances.toString());
                            }



                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Santander Río")){
                                sandanter.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                sandanter.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                sandanter.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, sandanter.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Supervielle")){
                                supervielle.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                supervielle.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                supervielle.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, supervielle.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("ICBC")){
                                icbc.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                icbc.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                icbc.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, icbc.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Hipotecario")){
                                hipotecario.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                hipotecario.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                hipotecario.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, hipotecario.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Ciudad")){
                                ciudad.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                ciudad.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                ciudad.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, ciudad.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Provincia")){
                                privincia.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                privincia.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                privincia.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, privincia.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Credicoop")){
                                credicoop.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                credicoop.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                credicoop.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, credicoop.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Patagonia")){
                                patagonia.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                patagonia.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                patagonia.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, patagonia.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Comafi")){
                                comafi.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                comafi.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                comafi.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, comafi.toString());
                            }

//                        if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Galicia")){
//                            galicia.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
//                            galicia.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
//                            galicia.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
////                            Log.d(TAG, galicia.toString());
//                        }



                        }


                    } catch (Exception e) {
                        Log.d(TAG, "doInBackground: Error");
                        status = DownloadStatus.FAILED_OR_EMPTY;
                        e.printStackTrace();
                    }

                }
                if (moneda.equalsIgnoreCase("euro")){

                    try {


                        nacion.setCompraDolar("$ " + resultadoDeJsoup.getDocumentoBna().select("td:nth-of-type(2)").get(1).text().substring(0, 5));
                        nacion.setVentaDolar("$ " + resultadoDeJsoup.getDocumentoBna().select("td:nth-of-type(3)").get(1).text().substring(0, 5));

                        for (int i = 42; i < 73 ; i++ ){

                            if ((resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Nación")) {
                                nacion.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, nacion.toString());
                            }



                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("BBVA Banco Francés")){
                                frances.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                frances.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                frances.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, frances.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Santander Río")){
                                sandanter.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                sandanter.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                sandanter.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, sandanter.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Supervielle")){
                                supervielle.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                supervielle.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                supervielle.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, supervielle.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("ICBC")){
                                icbc.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                icbc.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                icbc.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, icbc.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Hipotecario")){
                                hipotecario.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                hipotecario.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                hipotecario.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, hipotecario.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Ciudad")){
                                ciudad.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                ciudad.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                ciudad.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, ciudad.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Provincia")){
                                privincia.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                privincia.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                privincia.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, privincia.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Credicoop")){
                                credicoop.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                credicoop.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                credicoop.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, credicoop.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Patagonia")){
                                patagonia.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                patagonia.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                patagonia.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, patagonia.toString());
                            }


                            if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Comafi")){
                                comafi.setCompraDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
                                comafi.setVentaDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
                                comafi.setVariacionDolar(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
//                            Log.d(TAG, comafi.toString());
                            }


//                        if ( (resultadoDeJsoup.getDocumentoDolarInfo().select("td.colnombre").get(i).text()).equalsIgnoreCase("Banco Galicia")){
//                            galicia.setCompraEuro(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(2)").get(i).text().substring(0,7));
//                            galicia.setVentaEuro(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colCompraVenta:nth-of-type(3)").get(i).text().substring(0,7));
//                            galicia.setVariacionDEuro(resultadoDeJsoup.getDocumentoDolarInfo().select("td.colvariacion").get(i).text());
////                            Log.d(TAG, galicia.toString());
//                        }


                        }


                    } catch (Exception e) {
                        Log.d(TAG, "doInBackground: Error");
                        status = DownloadStatus.FAILED_OR_EMPTY;
                        e.printStackTrace();
                    }



                }

                this.mArrayListaBancos.add(nacion);
                this.mArrayListaBancos.add(frances);
                this. mArrayListaBancos.add(sandanter);
                this. mArrayListaBancos.add(supervielle);
                this.mArrayListaBancos.add(icbc);
                this.mArrayListaBancos.add(hipotecario);
                this.mArrayListaBancos.add(ciudad);
                this. mArrayListaBancos.add(privincia);
                this.mArrayListaBancos.add(credicoop);
                this. mArrayListaBancos.add(patagonia);
                this.mArrayListaBancos.add(comafi);
//            this.mArrayListaBancos.add(galicia);






        }

        if (runingOnSameThread && mCallBack !=null){
            //informamos que el proceso de seleccón está hecho
            //o quizas error
            mCallBack.onDataAvailable(this.mArrayListaBancos, status);
        }

        Log.d(TAG, "onDownloadComplete: Ends");
    }
}
