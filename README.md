# CustomTitleScreen

### Features

- Custom background image
- Add custom button texture
- Modify button locations
- Add your own buttons that open URLs

### Configuration

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