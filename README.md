# mYOUsic RUN
mYOUsic RUN revolutionizes the concept of playlist: instead of having a predefined set of music in which the app shuffled depending on the tempo, the genre and so on, mYOUsic RUN creates the music on the go, while you run, in order to help you achieve the best experience possible, based on your physiology! Depending on your objective, it may detect if you need a boost or on the contrary of you need to slow down in order to keep your pace.

Here is the link to the [Devpost submission](https://devpost.com/software/myousic) for the hackathon Lauzack 2018.

# Getting started

you will need to install `android-studio`, and then create a virtual device using AWD with the following specificties: emulate an Nexus 5X, with android 8.0, API 26

# Implementation details 

folder structure:

    .
      ├── mobile                    app for the mobile phone
          ├── src
              ├── main
                  ├── java          contain the java files necessary 
                  |                      to actually do the computations, modulate the music
                  └── res           contain the files for the layout 
          └── build
      ├── wear                      in relation with the smartwatch
          ├── src
          └── build
      └── README.md

    
   In mobile/src/java, in the mainActivity.java are the detalied implementation of our sound modulating algorithms.
   
# Sound modulation

We propose here an algorithm to "create" an appropriate music to each type of run and tailored for the usere, withouth user input apart from its biological data from the smart watch (see next section about this part). 

We split a song into three tracks, using bypass filter: `bass` are between 0 and 60 Hz, `medium` are between 60 Hz and 4K Hz and finally `high` contains all the frequencies above.

We then combine them with modified amplitute between the tracks (`bass`,`medium`,`high`). While keeping the speed of playback constant between the tracks, we use it at our advantage.

we suppose the following effect of the music on the runner (motivated by our own experience):
   1. The stronger the `bass`, the more motivated he will be to do an effort
   2. The synchronisation between the tempo of the music and his "running tempo" helps him keep his cadence, and he will sligthly modify his own cadence if necessary to match the tempo of the music (if the desynchronisation is small enough)
   3. More melody might encourage some "heroic" effect and helps him go faster.
   
   

# Simulation of the data
Due to technical issues, 
