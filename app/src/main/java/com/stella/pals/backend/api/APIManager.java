package com.stella.pals.backend.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stella.pals.R;
import com.stella.pals.frontend.base.BaseActivity;
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
import java.util.Map;

/**
 * Created by DJ on 13/8/15.
 */
public abstract class APIManager extends AsyncTask<Integer, Integer, Integer> implements APIInterface {

    private static final String TAG = APIManager.class.getSimpleName();
    private static final int NO_CONNECTION = -999;
    private static final String COOKIE_HEADER = "Set-Cookie";

    private final WeakReference<Context> mContext;
    private boolean mShowOverlay;
    private String mUrl;
    private final Map<String, String> mData;
    protected Document mDocumentSoup;

    public APIManager(Context context, String url, Map<String, String> data) {
        mContext = new WeakReference<Context>(context);
        mUrl = "http://www.interpals.net/".concat(url);
        mData = data;
    }

    public APIManager setShowOverlay(boolean showOverlay) {
        this.mShowOverlay = showOverlay;
        return this;
    }

    private void addLoadingOverlay(WeakReference<Context> weakContext) {
        Context context = weakContext.get();

        // If null, context has been gc'd
        if (context != null) {
            if (context instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) context;
                LayoutInflater inflater = baseActivity.getLayoutInflater();
                View parent = baseActivity.findViewById(R.id.parent);
                if (parent instanceof ViewGroup) {
                    inflater.inflate(R.layout.loading, (ViewGroup) parent);
                }
            }
        }
    }

    private void removeLoadingOverlay(WeakReference<Context> weakContext) {
        Context context = weakContext.get();

        // If null, context has been gc'd
        if (context != null) {
            if (context instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) context;
                View view = baseActivity.findViewById(R.id.loading_overlay);
                if (view != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
            }
        }
    }

    private void getCookies(Connection.Response responseSoup) {
        Global.setCookies(responseSoup.cookies());
        Global.initCookiesFromPreferences();
    }

    @Override
    protected void onPreExecute() {
        if (mShowOverlay) {
            addLoadingOverlay(mContext);
        }
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        int responseCode = HttpURLConnection.HTTP_OK;

        if (DeviceUtil.hasInternetConnection(mContext.get())) {
            try {
                Connection connectionSoup = Jsoup.connect(mUrl).cookies(Global.COOKIES).data(mData);
                Connection.Response responseSoup = connectionSoup.execute();
                getCookies(responseSoup);
                mDocumentSoup = responseSoup.parse();
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
        Context context = mContext.get();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            onPostTask();
        } else if (responseCode != NO_CONNECTION) {
            onPostFailTask();
            ToastUtil.makeShortToast(context, String.valueOf(responseCode));
        } else {
            ToastUtil.makeShortToast(context, context.getString(R.string.em_no_internet_connection));
        }

        if (mShowOverlay) {
            removeLoadingOverlay(mContext);
        }
    }

    @Override
    public abstract void onPostTask();

    @Override
    public abstract void onPostFailTask();
}
