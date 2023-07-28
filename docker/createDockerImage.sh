#!/bin/sh

cd ..

./makeErddapWar.sh

docker build -t vliz/custom-erddap .
