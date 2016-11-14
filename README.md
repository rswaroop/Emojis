External libraries
==================
- [slf4j](http://www.slf4j.org/) for logging

- [OkHttp](http://square.github.io/okhttp/) for making API requests to GitHub.

- [Picasso](http://square.github.io/picasso/) for emoji image downloading and caching.

Notes
=====

- When a fullscreen view is shown on clicking an emoji, a stretched image is presented instead of resizing the image.

- I've used [getSupportActionBar().setDisplayHomeAsUpEnabled(...)](https://developer.android.com/reference/android/support/v7/app/ActionBar.html#setDisplayHomeAsUpEnabled(boolean)) to provide a way for the user to go back to previous activity.

- Launcher icon is created on AndroidAssetStudio.

![EmojiView](/device-2016-11-13-161152.png?raw=true "EmojiView")