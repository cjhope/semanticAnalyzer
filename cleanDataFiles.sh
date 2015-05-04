#!/bin/bash

perl -pi -e 's/\n/ /g' $1
perl -pi -e 's/"::::"/::::/g' $1
