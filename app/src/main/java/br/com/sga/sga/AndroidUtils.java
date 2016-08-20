package br.com.sga.sga;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Mailson on 10/07/2016.
 */
public class AndroidUtils {

        private Context context;

        AndroidUtils(Context context){
            this.context = context;
        }

        /**
         * Retorna se o app está conectado na internet
         * @return
         */
        public boolean isConnectingToInternet(){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            return isConnected;
        }

    }