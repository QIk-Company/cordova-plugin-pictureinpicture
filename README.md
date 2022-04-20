# PictureInPicture Mode (PIP) plugin for cordova android
Cordova plugin to enable PictureinPicture support for android > 8.0

Based on the plugin [cordova-plugin-pip-mode](https://github.com/efoxbr/cordova-plugin-pip-mode) present in the github repository. 

## Install
```
cordova plugin add https://github.com/Qik-Company/cordova-plugin-pictureinpicture.git
```

or if you see any warning/error regarding conflict when edit-config
```
cordova plugin add https://github.com/Qik-Company/cordova-plugin-pictureinpicture.git --force
```




## API

Methods:
* **enter**(width: int, height:int, success: function, error: function)
    * Call to enter pip mode. It receives the with and height in pixels of the desired pip window. Example: 
    ```javascript
        cordova.PIP.enter(200,400,
              function(){console.log("Entered Pip Mode")},
              function(error){console.log(error)});
    ```
    
* **isPip**(success: function, error: function)
    * Call to check if it is in pip mode. Returns **_true_** or **_false_** in the success function.
    Example: 
    ```javascript
        cordova.PIP.isPip(
          function(result){console.log(result)},
          function(error){console.log(error)});
    ```
    
* **isPipModeSupported**(success: function, error: function)
    * Call to check if pip mode is supported. Returns **_true_** or **_false_** in the success function.
    ```javascript
        cordova.PIP.isPipModeSupported(
          function(result){console.log(result)},
          function(error){console.log(error)});
    ```
    
* **onPipModeChanged**(success: function, error: function)
    * Call to register and handle pip-mode-changed events
    ```javascript
        cordova.PIP.onPipModeChanged(
          function(result){console.log(result)},
          function(error){console.log(error)});
    ```
