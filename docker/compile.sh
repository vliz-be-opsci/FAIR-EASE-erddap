#!/bin/sh

docker container prune -f

cd ../WEB-INF
javac -cp "classes:../../../lib/servlet-api.jar:lib/*" classes/gov/noaa/pfel/coastwatch/TestAll.java;

[ $? -eq 0 ] || exit 1

cd ../docker

docker-compose up

exit 0

