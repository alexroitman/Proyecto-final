package com.aexample.a41758511.myapplication;
import android.app.VoiceInteractor;
import android.util.Log;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class JsonParser {

    static InputStream is = null;
    static JSONArray jarray = null;
    static String json = "";

    public JsonParser() {
    }

  /*  public JSONArray getJSONFromUrl(String url) {



    }*/

}
