# TinySDK
this project wraps ExoPlayer libraries with the very basic video playback functions to be exposed.
it accepts video url and render video and control buttons onto StyledPlayerView from application.

Currently, it's under development with the following plan:

1.  Enable video playback workable based on original ExoPlayer libraries    - done
2.  summarize the necessary APIs and wrap them to hide logic in a wrap class  - done
3.  seperate stream selection and playback into two different activity - done
4.  load streams from json file, rather than hard code -done
5.  enable track selection during playback
6.  improve application for lifecycle management with savedInstanceState
