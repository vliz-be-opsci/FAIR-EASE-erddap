ERDDAP fork, for the FAIR-EASE data provider, running in a docker container,

## Changes compared to the main ERDDAP repo :

## /!\ Things are still under development, they might change over the time (optimization, security (exception & visibility), typo, etc..) /!\


### Known Issues
Dependency netcdfAll-5.5.3.jar unavailable/private ?   
https://artifacts.unidata.ucar.edu/service/rest/repository/browse/unidata-all/edu/ucar/netcdfAll/
https://artifacts.unidata.ucar.edu/#browse/browse:unidata-all:edu%2Fucar%2FnetcdfAll%2F5.5.2  
but available at : https://downloads.unidata.ucar.edu/netcdf-java/5.5.3/netcdfAll-5.5.3.jar  

Error : 
```
[ERROR] Failed to execute goal on project ERDDAP: Could not resolve dependencies for project gov.noaa.pfel.erddap:ERDDAP:war:2.23-SNAPSHOT: The following artifacts could not be resolved: edu.ucar:netcdfAll:jar:5.5.3 (absent): Could not find artifact edu.ucar:netcdfAll:jar:5.5.3 in unidata-all (https://artifacts.unidata.ucar.edu/repository/unidata-all/) -> [Help 1]
```
Running with the 5.5.2 version. (will see later if it's fixable)

--------------------------------------------------------------------------------
### Files :
`makeErddapWar.sh`: Install the Maven dependencies (if there's no `content/`), compile `.java` files into `.class`, create a `erddap.war`.

`docker/createDockerImage.sh` : Call `makeErddapWar.sh` to initialise the repo, compile the code and create `.war`. Build a `vliz/custom-erddap` docker image based on the `Dockerfile`. This creates a docker-image, from the repository

Start container with `docker run -p 8080:8080 vliz/custom-erddap:latest`

`docker/compile.sh` : Remove every non-running container, compile java code and does a `docker-compose up`. Start/Run a docker container based on the `docker-compose.yml`, this container is based on the `vliz/custom-erddap:latest` container, but mount `docker/data/[conf|data|dataset]`, `content/`, `WEB-INF/classes` and `/tmp` in the container (used to avoid re-creating a docker image for every change).

--------------------------------------------------------------------------------

### History :
Add the persistency (if the history file is persistent) of the dataset.xml modifications.
In the EDD class, created : 
- Public attribute String `HISTORY_FILEPATH` : the history file location (default : `<BigParents>/history.json`) organized like that : 
```
{
  "dataset_c5ed_89e8": [
    {
      "date": "2023-07-27T07:52:09",
      "description": "A combinedGlobalAttribute changed:",
      "old": "  old line #12="    infoUrl=???",",
      "new": "  new line #12="    infoUrl=https://vliz.be"."
    },
    {
      "date": "2023-07-27T07:51:29",
      ...
    },
    ...
  ],
  "...": [
   ...
  ]
}
```

- public method `readEDDHistory` : the content of the HISTORY_FILEPATH, and returns the latest modification information (at least date + description). If no historical information can be retrieved for the dataset, call `writeDatasetEDDHistory`, and create a record for the dataset with a default description `Initial init <datasetID>` 
- public method `writeDatasetEDDHistory` : Write the input in the `HISTORY_FILEPATH`.
Slightly modified the `UpdateRSS` method to insert the changes in the `HISTORY_FILEPATH` and to read it when ERDDAP start

In the ERDDAP class, created :
2 Concurrent Hashmap public attributes :
- `updateDateHashMap` : link a datasetID to the latest update date.
- `updateChangeHashMap` : link a datasetID to the latest update change.
I'll be useful for the catalog to get them so we don't have to parse with regex the `rssHashMap` to retrieve date and changes.

--------------------------------------------------------------------------------

### RDF Representation :  

Create the RDF Representation of a Dataset (based on the global variable, column/axis/variable attributes and latestUpdate).  

#### Create RDFBuilder :
- [Create RDF representation like so](https://app.diagrams.net/#G1P0V9ZJogupb4mZvdCiLTySSyM2gQ6ASm)
- Based on [Apache Jena](https://jena.apache.org/tutorials/rdf_api.html)
- Can be serialized in [multiple language](https://www.javadoc.io/doc/org.apache.jena/jena-arq/3.4.0/org/apache/jena/riot/Lang.html)


#### Create RDFVocab :
- Create and RDF vocabulary used by `RDFBuilder`

#### Add access to the new file type (`.jsonld`/`.n3`/`.nt`/`.nq`/`.rdfxml`/`.trig`/`.ttl`) :
- `EDDTable` & `EDDGrid`: add every new file type.
- `EDStatic` & `messages-{lang}.xml`: add new file description for every language (not translated in every different language yet).
- `EDDGrid.respondToDapQuery` & `EDDTable.respondToDapQuery`: authorize access to these new formats & return the corresponding values.
- `OuputStreamFromHttpResponse`: add new fileExtension MIME type.
  
#### Create Catalog :
- Add ConcurrentHashMap `rdfHashMap`: link a datasetID to this associated RDF representation.
- In the `erddap.goGet`: add link to the `erddap.doCatalog` on url `{erddap}/info/catalog.{format}`
- `erddap.doCatalog`: get Latest updated datasetID (via `erddap.sortByUpdateDate`), limit the number of results via the URL query.
Create a RDF representation, containing all of the node in the node list. Then return the result in the given format.
- `erddap.sortByUpdateDate`: list every dataset, and return list of datasetID, sort by the latestUpdate date.
- Had to modify the `table.leftToRightSortIgnoreCase` so we can choose between ascending and descending sort. 
Had to slightly modify each call to the `table.leftToRightSortIgnoreCase` method.

-----------------------------
## Global information :

copy `/docker/data/config.sh` to `/docker/data/conf/config.sh` and fill it with your infos (`/docker/data/conf/config.sh` is git-ignored)

For further information or modification, `content/erddap/setup.xml` (only created after the maven install)

Running on localhost:8080/erddap (via `docker/createDockerImage.sh; docker run -p 8080:8080 vliz/custom-erddap:latest` or `docker/compile.sh`)

If you want to change the port, change it in the docker-compose or in the `docker run..` command, **AND** in the `docker/data/conf/config.sh`

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
