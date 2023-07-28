ERDDAP fork, for the FAIR-EASE data provider, running in a docker container,

## Changes compared to the main ERDDAP repo :

`makeErddapWar.sh`: Install the Maven dependencies (if there's no `content/`), compile `.java` files into `.class`, create a `erddap.war`.

`docker/createDockerImage.sh` : Call `makeErddapWar.sh` to initialise the repo, compile the code and create `.war`. Build a `vliz/custom-erddap` docker image based on the `Dockerfile`. This creates a docker-image, from the repository

Start container with `docker run -p 8080:8080 vliz/custom-erddap:latest`

`docker/compile.sh` : Remove every non-running container, compile java code and does a `docker-compose up`. Start/Run a docker container based on the `docker-compose.yml`, this container is based on the `vliz/custom-erddap:latest` container, but mount `docker/data/[conf|data|dataset]`, `content/`, `WEB-INF/classes` and `/tmp` in the container (used to avoid re-creating a docker image for every change).


## Global informations :

copy `/docker/data/config.sh` to `/docker/data/conf/config.sh` and fill it with your infos (`/docker/data/conf/config.sh` is git-ignored)

Fill `content/erddap/setup.xml` (only created after the maven install) with your infos & replace the path in the `bigParentDirectory` with `/erddapData`

Running on localhost:8080/erddap (via `docker/createDockerImage.sh; docker run -p 8080:8080 vliz/custom-erddap:latest` or `docker/compile.sh`)

If you want to change the port, change it in the docker-compose or in the `docker run` command, **AND** in the `content/erddap/setup.xml`.


-----------------------------

Welcome to the ERDDAP repo. 

ERDDAP is a scientific data server that gives users a simple, consistent way to download subsets of 
gridded and tabular scientific datasets in common file formats and make graphs and maps.
ERDDAP is a Free and Open Source (Apache and Apache-like) Java Servlet from NOAA NMFS SWFSC Environmental Research Division (ERD).
* To see/use an ERDDAP installation: [https://coastwatch.pfeg.noaa.gov/erddap/index.html]
* To read installation instructions: [https://erddap.github.io/setup.html]
* To contribute code: [https://erddap.github.io/setup.html#programmersGuide]

Below you will find relevant links for asking questions and how to contribute.
* Review conversations and ask questions at https://groups.google.com/g/erddap or at https://github.com/erddap/erddap/discussions
* Review and submit issues to https://github.com/erddap/erddap/issues
* To propose feature requests, follow this guidance: https://github.com/erddap/erddap/discussions/93#discussion-4920427
