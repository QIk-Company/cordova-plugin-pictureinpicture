cordova.addConstructor(function () {
  var exec = cordova.exec;

  window.cordova.PIP = {
    enter: function (width, height, success, error) {
      exec(success, error, 'PIP', 'enter', [width, height]);
    },

    isPip: function (success, error) {
      exec(success, error, 'PIP', 'isPip', []);
    },

    onPipModeChanged: function (success, error) {
      exec(success, error, 'PIP', 'onPipModeChanged', []);
    },

    // User close the PIP mode by touching close button or drag to dismiss
    onUserDismissed: function (success) {
      exec(success, null, 'PIP', 'onUserDismissed', []);
    },

    isPipModeSupported: function (success, error) {
      exec(success, error, 'PIP', 'isPipModeSupported', []);
    },

    reset: function () {
      exec(null, null, 'PIP', 'reset', []);
    },
  };
});
