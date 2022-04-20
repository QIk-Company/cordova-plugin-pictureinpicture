cordova.addConstructor(function (){
	var exec = cordova.exec;
	window.cordova.PIP = {
		enter: function (width, height, success, error) {
			exec(success, error, "PIP", "enter", [width, height]);
		},
		initializePip: function (success, error) {
			exec(success, error, "PIP", "initializePip", []);
		},
		isPip: function (success, error) {
			exec(success, error, "PIP", "isPip", []);
		},
		onPipModeChanged: function (success, error) {
			exec(success, error, "PIP", "onPipModeChanged", []);
		},
		isPipModeSupported: function (success, error) {
			exec(success, error, "PIP", "isPipModeSupported", []);
		}
	}
})