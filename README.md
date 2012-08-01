# protozoa

A de Jong attractor explorer, built with quil.

The `tubes` and `bezier-wanderer` branches contain alternate sketches that grew
out of development smoketests of the bezier and animation namespaces.

## Usage

Clone this repo, then in a REPL `(use 'protozoa.core)`.

The sketch starts in 'play' mode, where the parameters of the mapping are
changing, and correspondingly only the last few frames of particle history are
shown. This produces a lower-quality image, but the behaviour of the mapping as
the parameters animate (along a random bezier walk) is interesting.   To switch
switch to 'paused' mode, where the parameters of the mapping do not change,
producing a higher-quality image of a particular point in parameter space over
many frames, press the `p` key on your keybord.

An fps meter can be toggled by pressing your `m` key.

## License

Copyright (C) 2012 Dan Lidral-Porter

Distributed under the GPLv3 (see LICENSE.txt).
