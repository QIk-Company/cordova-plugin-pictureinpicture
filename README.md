# PictureInPicture Mode (PIP) plugin for cordova android

Cordova plugin to enable PictureinPicture support for android > 8.0

Based on the plugin [cordova-plugin-pip-mode](https://github.com/efoxbr/cordova-plugin-pip-mode) present in the github repository.

## Install

```
cordova plugin add https://github.com/Qik-Company/cordova-plugin-pictureinpicture.git
```

## Setup

Adding on config.xml to prevent conflict when many plugins try to edit same manifest's attribute

> Failed to install 'qik.cordova.pip': Error: There was a conflict trying to modify attributes with <edit-config> in plugin qik.cordova.pip. The conflicting plugin, undefined, already modified the same attributes. The conflict must be resolved before qik.cordova.pip can be added. You may use --force to add the plugin and overwrite the conflicting attributes.

```
<custom-preference name="android-manifest/application/activity/@android:name" value="PIPActivity" />
<custom-preference name="android-manifest/application/activity/@android:supportsPictureInPicture" value="true" />
<custom-preference name="android-manifest/application/activity/@android:launchMode" value="singleTask" />
<custom-preference name="android-manifest/application/activity/@android:resizeableActivity" value="true" />
```

## API

Methods:

- **enter**(width: int, height:int, success: function, error: function)
  - Call to enter pip mode. It receives the with and height in pixels of the desired pip window. Example:
  ```javascript
  cordova.PIP.enter(
    200,
    400,
    function () {
      console.log('Entered Pip Mode');
    },
    function (error) {
      console.log(error);
    }
  );
  ```
- **isPip**(success: function, error: function)
  - Call to check if it is in pip mode. Returns **_true_** or **_false_** in the success function.
    Example:
  ```javascript
  cordova.PIP.isPip(
    function (result) {
      console.log(result);
    },
    function (error) {
      console.log(error);
    }
  );
  ```
- **isPipModeSupported**(success: function, error: function)
  - Call to check if pip mode is supported. Returns **_true_** or **_false_** in the success function.
  ```javascript
  cordova.PIP.isPipModeSupported(
    function (result) {
      console.log(result);
    },
    function (error) {
      console.log(error);
    }
  );
  ```
- **onPipModeChanged**(success: function, error: function)

  - Call to register and handle pip-mode-changed events

  ```javascript
  cordova.PIP.onPipModeChanged(
    function (result) {
      console.log(result);
    },
    function (error) {
      console.log(error);
    }
  );
  ```

- **onUserDismissed**(callback: function)

  - Call to register and handle pip-mode-dismissed event

  ```javascript
  cordova.PIP.onUserDismissed(function () {
    console.log('User dismissed PIP mode');
  });
  ```

- **reset**()
  - Remove all listeners (onPipModeChanged, onUserDismissed) and reset the plugin
  ```javascript
  cordova.PIP.reset();
  ```
