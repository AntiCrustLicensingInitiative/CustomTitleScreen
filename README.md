# CustomTitleScreen

An open-source fork of https://dilaton.martmists.com/Martmists/CustomTitleScreen

## Why a Fork?

Martmists changed all of the licenses of his mods from MIT to a very restrictive license. This is a continuation of the mod from when it was open source, in an effort to preserve user and developer freedom.


## Features

- Custom background image
- Add custom button texture
- Modify button locations
- Add your own buttons that open URLs


## Configuration

For a custom wallpaper, simply put an image in config/cts/background.png    
For custom buttons, put an image in config/cts/buttons.png    
> Note: The buttons.png needs to be based on widgets.png, which can be extracted from minecraft.

Main config:
```json5
{
    // Add a custom Edition line
    "customEditionEnabled": true,
    // Your custom edition line
    "customEdition": "This is my custom edition!",
    
    // Add your own splashes (overrides default splashes)
    "customSplashEnabled": true,
    
    // Remove splashes entirely
    "removeSplash": false,
    
    // List of custom splash text
    "customSplash": [
        "Custom Splash 1",
        "Custom Splash 2",
        "Custom Splash 3"
    ],
    
    // Remove the minecraft logo from the main menu
    "removeMinecraftLogo": false
}
```
Custom buttons:
```json5
{
    "buttons": {
        // Key format: "modname.buttonname"
        // modifyable keys:
        // minecraft.singleplayer
        //           multiplayer
        //           options
        //           realms
        //           quit
        //           accessibility
        //           language
        // modmenu.modlist -> only works if 'Fabric Mod List' is installed
        // Any other key can be used for non-modify keys, e.g. 'modpack.discord'
        "<key>": {
            // X offset from center
            "x": 104,
            // Y offset from just below top
            "y": 2,
            // Button width
            "width": 100,
            // Button height
            "height": 20,
            // Text on the button, '<default>' to not override.
            "text": "<default>",
            // type of button. Valid options: "modify", "url", "modifyTextured"
            // modify: changes parameters on a button
            // modifyTextured: turns a textured button into a text button
            // url: custom new button that opens a website when clicked
            "type": "modify",
            // Only used for `url` type, the url to open when clicked.
            "parameter": "",
            // Not currently implemented
            "textureName": ""
        }
    }
}
```

## Licensing

Licensed under the LGPLv3


## Credit

*The original code has the following copyright notice:*

Copyright 2019 Martmists

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

