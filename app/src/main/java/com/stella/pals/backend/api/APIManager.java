package com.stella.pals.backend.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.stella.pals.R;
import com.stella.pals.backend.model.Cookie;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.interfaces.APIInterface;
import com.stella.pals.utils.DeviceUtil;
import com.stella.pals.utils.ToastUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by DJ on 13/8/15.
 */
public abstract class APIManager extends AsyncTask<Integer, Integer, Integer> implements APIInterface {

    private static final String TAG = APIManager.class.getSimpleName();
    private static final int NO_CONNECTION = -999;

    private final WeakReference<Context> context;
    private boolean showDialog;
    private String url;
    private final Map<String, String> data;
    protected Document documentSoup;

    public APIManager(Context context, String url, Map<String, String> data) {
        this(context, url, data, false);
    }

    public APIManager(Context context, String url, Map<String, String> data, boolean showDialog) {
        this.context = new WeakReference<Context>(context);
        this.url = "http://www.interpals.net/".concat(url);
        this.data = data;
        this.showDialog = showDialog;
    }

    private void showDialog() {
        EventBus.getDefault().post(true);
    }

    private void dismissDialog() {
        EventBus.getDefault().post(false);
    }

    private void saveCookies(Connection.Response responseSoup) {
        Map<String, String> cookies = responseSoup.cookies();
        Global.setCookies(cookies);
    }

    @Override
    protected void onPreExecute() {
        if (showDialog) {
            showDialog();
        }
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        int responseCode = HttpURLConnection.HTTP_OK;

        if (DeviceUtil.hasInternetConnection(context.get())) {
            try {
                Connection connectionSoup = Jsoup.connect(url).cookies(cookiesStringToMap()).data(data);
                Connection.Response responseSoup = connectionSoup.execute();
                saveCookies(responseSoup);
                documentSoup = responseSoup.parse();
                responseCode = responseSoup.statusCode();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        } else {
            responseCode = NO_CONNECTION;
        }

        return responseCode;
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        Context context = this.context.get();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            if (documentSoup != null) {
                if (documentSoup.getElementById("topLogin") == null) {
                    onPostTask();
                } else {
                    if (!url.endsWith(APIConstants.LOGIN)) {
                        onPostFailTask();
                    } else {
                        onPostTask();
                    }
                }
            } else {
                ToastUtil.makeShortToast(context, "Not doing anything");
            }
//            if (documentSoup != null && !documentSoup.location().contains(APIConstants.LOGIN)) {

//            } else {
//
//            }
//            } else if (!url.equals(APIConstants.LOGIN)) {
//                Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                BaseApplication.getInstance().startActivity(intent);
//            }
        } else if (responseCode != NO_CONNECTION) {
            onPostFailTask();
            ToastUtil.makeShortToast(context, String.valueOf(responseCode));
        } else {
            ToastUtil.makeShortToast(context, context.getString(R.string.em_no_internet_connection));
        }
    }

    @Override
    public void onPostTask() {
        if (showDialog) {
            dismissDialog();
        }
    }

    @Override
    public void onPostFailTask() {
        if (showDialog) {
            dismissDialog();
        }
    }

    private Map<String, String> cookiesStringToMap() {
        HashMap<String, String> cookiesMap = new HashMap<String, String>();

        List<Cookie> cookiesList = SQLite.select().from(Cookie.class).queryList();

        for (int i = 0; i < cookiesList.size(); i++) {
            Cookie cookie = cookiesList.get(i);
            cookiesMap.put(cookie.getKey(), cookie.getValue());
        }

        return cookiesMap;
    }
}
