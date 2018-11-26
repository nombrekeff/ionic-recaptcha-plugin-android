
const exec = require('cordova/exec');
const PLUGIN_NAME = 'IonicRecaptcha';

const IonicRecaptcha = {
  verify: function (key, successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "verify", [key]);
  }
};

module.exports = IonicRecaptcha;
