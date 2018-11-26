package com.packtpub;

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

/**
 * Cordova Recaptcha plugin to verify Google Recaptcha on android.
 */
public class Recaptcha extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
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
        if (apiKey.length() > 0) {
            SafetyNet.getClient(cordova.getActivity())
                .verifyWithRecaptcha(apiKey)
                .addOnSuccessListener(cordova.getActivity(),
                    new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                            String userResponseToken = response.getTokenResult();
                            if (!userResponseToken.isEmpty()) {
                                callbackContext.success(userResponseToken);
                            } else {
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
                                callbackContext.error(message);
                            } else {
                                // A different, unknown type of error occurred.
                                callbackContext.error(e.getMessage());
                            }
                        }
                    });

        } else {
            callbackContext.error("Verify called without providing a Site Key");
        }
    }

}