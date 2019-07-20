<h1 align="center"> StationMusicFM </h1>
<p align="center">
<a href="https://opensource.org/licenses/MIT"><img alt="Licence" src="https://img.shields.io/badge/license-MIT-green.svg" /></a>
</p>

# Introduction

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7924f3cf4f21463a9fd42fdd88884f9c)](https://app.codacy.com/app/pokk/StationMusicFM?utm_source=github.com&utm_medium=referral&utm_content=SmashKs/StationMusicFM&utm_campaign=Badge_Grade_Settings)

A showcase of music app which provides the functions that listening online musics, downloading a music from remote
server, checking top charts of artists, tracks, albums in this week. Also, with searching function for a track, artist,
album. Basically, it's a demo app but all the basic functions we provide.

Talking is wasting time XD Let's just see the Demo.

# Screenshots

1. Main View
2. Explore
3. My Music
4. Rank
5. Searching

# Data Source

Explore Function - All tracks, artists, photos data comes from [last.fm](https://www.last.fm/) . It lets me be able to
get so much information of them.

# Modules

We're using [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) for this
project.

- Presentation - This is the main Android application module.
- Domain - A pure kotlin library without Android SDK.
- Data - Android library with Firebase, Database for data fetching.
- BuildSrc - Kts build source, version, third-party libraries path.
- Ext
- Ktx
- Widget
- Player - A [ExoPlayer](https://github.com/google/ExoPlayer) wrapped library.

# Libraries Used


# TODO

- [ ] Playing View function for showing current playing music.
- [ ] Notification of playing a music.

# Licence

```
MIT License

Copyright (c) 2019 SmashKs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
