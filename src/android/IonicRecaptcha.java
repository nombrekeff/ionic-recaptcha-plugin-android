package com.keff;

import android.support.annotation.*;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.concurrent.Executor;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.safetynet.*;
import com.google.android.gms.tasks.*;

import android.util.Log;


/**
 * Cordova Recaptcha plugin to verify Google Recaptcha on android.
 */
public class IonicRecaptcha extends CordovaPlugin {
    private static final String TAG = "IonicRecaptchaPlugin";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "Test log");
        if (action.equals("verify")) {
        	try {
            	String apiKey = args.getString(0);
            	this.verify(apiKey, callbackContext);
        	} catch(JSONException e){
				callbackContext.error("Verify called without providing a Site Key");
        	}
            return true;
        }
        return false;
    }

    private void verify(String apiKey, CallbackContext callbackContext) {
        // callbackContext.success("Is good");
        Log.d(TAG, "verify " + apiKey);
        if (apiKey.length() > 0) {
            SafetyNet.getClient(cordova.getActivity())
                .verifyWithRecaptcha(apiKey)
                .addOnSuccessListener(cordova.getActivity(),
                    new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                            String userResponseToken = response.getTokenResult();
                            if (!userResponseToken.isEmpty()) {
                                Log.e(TAG, "Response: " + userResponseToken);
                                callbackContext.success(userResponseToken);
                            } else {
                                Log.e(TAG, "Repsonse token was empty.");
                                callbackContext.error("Repsonse token was empty.");
                            }
                        }
                    })
                .addOnFailureListener(cordova.getActivity(),
                    new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof ApiException) {
                                // An error we know about occurred.
                                ApiException apiException = (ApiException) e;
                                int statusCode = apiException.getStatusCode();
                                String message = CommonStatusCodes.getStatusCodeString(statusCode);
                                Log.e(TAG, "error: " + message);
                                callbackContext.error(message);
                            } else {
                                Log.e(TAG, "error: " + e.getMessage());
                                // A different, unknown type of error occurred.
                                callbackContext.error(e.getMessage());
                            }
                        }
                    });

        } else {
            Log.e(TAG, "Verify called without providing a Site Key");
            callbackContext.error("Verify called without providing a Site Key");
        }
    }

}