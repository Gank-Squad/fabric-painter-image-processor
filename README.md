# fabric-painter-image-processor
This application is meant to be used alongside the [fabric painter mod](https://github.com/Gank-Squad/fabric-painter).

This meant to be used for the [ArtMap](https://gitlab.com/BlockStack/ArtMap) plugin
## Dependencies
This application **requires** ImageMagick to work at all.

On Windows, make sure its added to path, if it isn't, this program will not work.

On Linux, make sure its in your /bin folder.

I will likely get rid of this dependency in the future but for the time being its still necessary.

## Features
 - resize image as you change the desired in-game size
 - automatically convert images to the appropriate color-palette
 - dithering (FloydSteinberg, Riemersma, or you can go with no dithering)
 - option to stretch or crop the image
 - preview the image in a larger window
 - generate instructions to be used with the [fabric painter mod](https://github.com/Gank-Squad/fabric-painter)

## How to Use
click "select image" and choose an image from your file system. From there, just mess with the settings until you get something you like. 
Do note though that `x panels` and `y panels` correspond to the number of maps in game, so a value of 2 for both would correspond to a 2x2 in game.

Also, for the time being, avoid resizing the main window, as it doesn't scale properly yet.

## Setup
Import the codebase as a Maven project in your editor of choice, Base.java has the main method so make sure you run from there
