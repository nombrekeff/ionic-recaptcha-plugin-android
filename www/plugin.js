
const cordova = require('cordova');
const PLUGIN_NAME = 'IonicRecaptcha';

const IonicRecaptcha = {
  verify: function (key, successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, PLUGIN_NAME, "verify", [key]);
  }
};

module.exports = IonicRecaptcha;
