# protozoa

A de Jong attractor explorer, built with quil.

The `tubes` and `bezier-wanderer` branches contain alternate sketches that grew
out of development smoketests of the bezier and animation namespaces.

## Usage

Clone this repo, then in a REPL `(use 'protozoa.core)`.

The sketch starts in 'paused' mode, where the parameters of the mapping do not
change. This produces a higher-quality image of a particular point in parameter
space over many frames. To switch to 'play', press the `p` key on your keybord.
In this mode, the parameters change over time, so only the last few frames are
displayed, and the image is correspondingly lower-quality, but the behaviour of
the mapping as the parameters animate (along a random bezier walk) is
interesting.

An fps meter can be toggled by pressing your `f` key.

## License

Copyright (C) 2012 Dan Lidral-Porter

Distributed under the GPLv3 (see LICENSE.txt).
