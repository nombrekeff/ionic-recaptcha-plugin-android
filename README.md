# Ionic 3 Recaptcha Plugin (Android)
This is a cordova plugin to perform Google reCAPTCHA verify on Android using the android SafetyNet package.
> Based on [cordova-plugin-recaptcha](https://bitbucket.org/packt-internal/cordova-plugin-recaptcha) by [@pack-internal](https://bitbucket.org/packt-internal)


## Links
* Safetynet Recaptcha Docs: https://developer.android.com/training/safetynet/recaptcha

## Installation
```
cordova plugin add git@bitbucket.org:manoloedge/ionic-recaptcha-android.git
```

<!-- __note:__ The plan is to publish this to the @packt npm org, however cordova-cli doesn't currently 
support scoped plugins, [see Issue CB-12774](https://issues.apache.org/jira/browse/CB-12774). -->

## Usage
```ts
  ionViewDidLoad() {
    this.platform.ready().then(() => {
      (<any>window).IonicRecaptcha.verify('ANDROID_KEY', this.successCallback, this.errorCallback);
    });
  }
  
  successCallback(data) {
    console.log("Good, verified", data);
  }

  errorCallback(err) {
    console.log("Bad, not verified", err);
  }
```