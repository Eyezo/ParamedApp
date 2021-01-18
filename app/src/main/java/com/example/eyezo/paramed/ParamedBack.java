package com.example.eyezo.paramed;

import android.app.Application;

import com.backendless.Backendless;

public class ParamedBack extends Application {

    private static final String APPLICATION_ID = "F0932BEA-D077-BA5B-FF5D-B5ECDDB92500";
    private static final String API_KEY = "07CF6EB3-18DF-4121-862D-030B28FA6EC7";
    private static final String SERVER_URL = "https://api.backendless.com";


    @Override
    public void onCreate(){
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);
    }
}
