#!/bin/bash

# Bob Simons uses this to create erddap.war.
# The specific files in erddap.war do change occasionally.
# The directories are specific to Bob's computer. Other people will need to change them.
# The starting dir is important.
# For everyone but Bob, the cwexperimental directory will instead be called 'erddap'.

# Name of war file will be name of webapp directory.
# Delete files in temp directories

TEMP_DIR=WEB-INF/temp/

rm -f public/*
rm -f $TEMP_DIR*
rm -f WEB-INF/classes/gov/noaa/pfel/coastwatch/log.*
rm WEB-INF/NetCheck.xml.log

cp ./WEB-INF/erddap.web.xml ./WEB-INF/web.xml

if [ ! -d "content/" ];
then
   echo "content/ not found, need maven install"
   mvn install
   [ $? -eq 0 ] || exit 1
   rm -r download_cache 
   rm -r target 
   mv content/erddap/images/erddapStart2.css content/erddap/images/erddap2.css
fi

if [ ! -d "$TEMP_DIR" ];
then
   echo "no $TEMP_DIR found, create one"
   mkdir -p $TEMP_DIR
fi

cd ./WEB-INF/
javac -cp "classes:../../../lib/servlet-api.jar:lib/*" classes/gov/noaa/pfel/coastwatch/TestAll.java;
[ $? -eq 0 ] || exit 1

cd ..
jar -cf erddap.war download/ images/orcid_24x24.png images/data.gif images/envelope.gif images/external.png images/favicon.ico images/fileIcons images/nlogo.gif images/noaa_simple.gif images/noaab.png images/noaa20.gif images/openid.png images/QuestionMark.png images/resize.gif images/x.gif images/arrow2R.gif images/arrow2L.gif images/arrow2U.gif images/arrow2D.gif images/arrowR.gif images/arrowRR.gif images/arrowL.gif images/arrowLL.gif images/arrowU.gif images/arrowUU.gif images/arrowD.gif images/arrowDD.gif images/erddap.css images/erddap2.css images/leaflet.css images/leaflet.js images/loading.png images/plus.gif images/plusplus.gif images/plus10.gif images/plus100.gif images/plus1000.gif images/minus.gif images/minusminus.gif images/minus10.gif images/minus100.gif images/minus1000.gif images/rss.gif images/sliderBg.gif images/sliderCenter.gif images/sliderLeft.gif images/sliderRight.gif images/spacer.gif images/world540.png images/world0360.png images/worldPM180.png images/world540Big.png images/world0360Big.png images/worldPM180Big.png images/wz_dragdrop.js images/TranslatedByGoogle.png images/wz_tooltip.js images/youtube.png public/ WEB-INF/ArchiveADataset.sh WEB-INF/ArchiveADataset.bat WEB-INF/DasDds.sh WEB-INF/DasDds.bat WEB-INF/ConvertTable.sh WEB-INF/ConvertTable.bat WEB-INF/GenerateDatasetsXml.sh WEB-INF/GenerateDatasetsXml.bat WEB-INF/HashDigest.sh WEB-INF/HashDigest.bat WEB-INF/web.xml WEB-INF/classes/ WEB-INF/cptfiles/ WEB-INF/lib/ WEB-INF/ref/ WEB-INF/temp/ 

