# Space Invaders

[![Language](https://badgen.net/static/language/java/orange)](https://www.oracle.com/java/technologies/downloads/)
[![Java Swing](https://img.shields.io/badge/package-java%20swing-brightgreen.svg?style=flat)](https://docs.oracle.com/javase/8/docs/technotes/guides/swing/)

## About the Project

This project is an exploration of my skills and knowledge in Object-Oriented Programming (OOP) and graphical user interface (GUI) development using Java, specifically with the `java swing` package. My intention with this project is to test and refine my understanding of OOP concepts while creating a fun and engaging game.

## Features

- **OOP Principles:** The project is designed following fundamental OOP principles such as encapsulation, inheritance, polymorphism, and abstraction.
- **Java Swing GUI:** The graphical user interface is built using the `java swing` package.

## Run and verify

Requires JDK 17 or newer. Run these commands from the project root (macOS/Linux):

```sh
mkdir -p /tmp/space-invaders-build
javac --release 17 -d /tmp/space-invaders-build src/application/*.java src/main/*.java src/entity/*.java test/main/*.java
java -cp /tmp/space-invaders-build:res application.Main
```

Move with the arrow keys, shoot with Space, and open options with Escape.

To run without music or sound effects:

```sh
java -Dspaceinvaders.mute=true -cp /tmp/space-invaders-build:res application.Main
```

Run the headless regression checks and the asset construction benchmark:

```sh
java -Djava.awt.headless=true -cp /tmp/space-invaders-build:res main.GameRegressionTest
java -Djava.awt.headless=true -cp /tmp/space-invaders-build:res main.AssetBenchmark
```

Sprites are cached at their display sizes, and sound clips are preloaded and reused. Repeating a sound restarts its clip; different effects can overlap. Updates and painting run on Swing's event thread, with a fixed 60 Hz simulation and at most five catch-up updates after a stall. The benchmark measures warmed enemy/bullet construction, not overall FPS. The regression checks cover timing, collisions, projectile cleanup, scoring, drawing, retries, controls, and unavailable audio; they do not require a display or audio hardware.

<img src="/bin/doc/si-doc1.png" alt="Screenshot of the space invaders" width="500"/>
<img src="/bin/doc/si-doc2.png" alt="Screenshot of the space invaders" width="500"/>

## Game Assets
- Chris Courses: [youtube](https://www.youtube.com/watch?v=MCVU0w73uKI)
- RyiSnow: [youtube](https://www.youtube.com/watch?v=OF41XmRk2wo&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
- Fonts: [fontsgeek](https://fontsgeek.com/fonts/Gamer-Bold#google_vignette)
- Wallpaper: [wallhere](https://wallhere.com/en/wallpaper/2246866)
- Bullet: [Master484](https://opengameart.org/content/bullet-collection-1-m484)
- Spaceship: [millionthvector](https://millionthvector.blogspot.com/p/free-sprites.html)
- Monster: [millionthvector](https://millionthvector.blogspot.com/p/free-sprites.html)
- Blackhole : [millionthvector](https://millionthvector.blogspot.com/p/free-sprites.html)
- The images (Spaceship, Monster and Blackhole) that used in this project are licensed under the [Creative Commons Attribution 4.0 International License (CC BY 4.0)](https://creativecommons.org/licenses/by/4.0/)
