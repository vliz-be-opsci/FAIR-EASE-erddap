#!/bin/sh

cd ..

./makeErddapWar.sh
[ $? -eq 0 ] || exit 1

docker build -t vliz/custom-erddap .
