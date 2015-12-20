package com.henu.smp.proxy;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liyngu on 12/15/15.
 */
public class ServerProxy {
    private static final String LOG_TAG = ServerProxy.class.getSimpleName();
    private static final String HOST = "http://10.0.2.2:8080/EtymaDictionaryApi/webservice/";
    //  private static final String url = "http://172.27.35.1:8080/EtymaDictionaryApi/webservice/test?wsdl";

//    static {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//            StrictMode.ThreadPolicy.Builder policyBuilder = new StrictMode.ThreadPolicy.Builder();
//            StrictMode.ThreadPolicy threadPolicy = policyBuilder.detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build();
//            StrictMode.setThreadPolicy(threadPolicy);
//
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
//                    .penaltyLog().penaltyDeath().build());
//
//        }
//    }

    /**
     * soap
     *
     * @return
     */
//    public String invoke() {
//        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
//        httpTransportSE.debug = true;
//
//        SoapObject rpc = new SoapObject(namespace, methodName);
//        //rpc.addProperty("testname", "hhhhhhhh");
//        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        soapEnvelope.setOutputSoapObject(rpc);
//        soapEnvelope.dotNet = false;
//
//        String result = "";
//        try {
//            httpTransportSE.call(null, soapEnvelope);
//            if (soapEnvelope.getResponse() != null) {
//                SoapObject soapObject = (SoapObject) soapEnvelope.bodyIn;
//                result = soapObject.getProperty("return").toString();
//            }
//        } catch (Exception e) {
//            Log.e(LOG_TAG, e.toString());
//        }
//        return result;
//    }
    public String doGetRequest() {
        String result = "{}";
        try {
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpGet request = new HttpGet(url);
//            //request.addHeader("Accept","text/plain");
//            HttpResponse response = httpClient.execute(request);
//            HttpEntity entity=response.getEntity();
//            result =EntityUtils.toString(entity);
            URL url = new URL("http://localhost:8080/SAOMusicPlayer_Api/user/login");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = br.readLine();
                Log.i(LOG_TAG, str);
            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
        return result;
    }
}
