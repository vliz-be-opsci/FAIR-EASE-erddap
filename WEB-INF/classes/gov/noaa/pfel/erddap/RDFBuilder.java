package gov.noaa.pfel.erddap;

import com.cohort.array.Attributes;
import com.cohort.array.PrimitiveArray;
import com.cohort.array.StringArray;
import com.cohort.util.Calendar2;

import gov.noaa.pfel.coastwatch.griddata.OpendapHelper;
import gov.noaa.pfel.coastwatch.pointdata.Table;
import gov.noaa.pfel.erddap.dataset.EDD;
import gov.noaa.pfel.erddap.dataset.EDDGrid;
import gov.noaa.pfel.erddap.dataset.EDDTable;
import gov.noaa.pfel.erddap.util.EDStatic;
import gov.noaa.pfel.erddap.variable.EDV;
import gov.noaa.pfel.erddap.variable.EDVGridAxis;

import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.GraphMemFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFWriter;

import java.io.OutputStream;
import java.util.SortedMap;
import java.util.TreeMap;

import static gov.noaa.pfel.erddap.util.EDStatic.*;


public class RDFBuilder {

    // ====================================
    // General values :
    // ====================================
    private EDD edd = null;
    private Model model = null;
    private Resource mainNode = null;
    private String globalURI = null;

    // ====================================
    // Resource :
    // ====================================
    // ====================================
    private Resource blankNodeConformsTo = null;
    private Resource hydraPropertySubsetting = null;
    private Resource hydraSupportedOperationSubsetting = null;

    // ====================================
    // Specific values :
    // ====================================
    private String latestUpdate = null;
    private String[] latLongVarName = {"", ""}; // [{latitude}, {longitude] Spatial Position related Column/Axis names //todo add Z/Depth/Height ?
    private String timeVarName = ""; // Time related Column/Axis name


    /**
     * The constructor.
     *
     * <p> Used to generate a RDF representation of a dataset, or of a set of dataset (catalog).
     * Using Apache Jena Library. Initialize the Jena Model to build the representation.
     *
     */
    public RDFBuilder() {
        model = new ModelCom(GraphMemFactory.createGraphMem());
    }

    /**
     * The constructor.
     *
     * <p> Used to generate a RDF representation of a dataset, or of a set of dataset (catalog).
     * Using Apache Jena Library. Initialize the Jena Model to build the representation and set the EDD from params.
     *
     * @param pEdd EDD of the dataset (this EDD is used to get every information about the dataset)
     *
     */
    public RDFBuilder(EDD pEdd) {
        if(pEdd != null) edd = pEdd;
        model = new ModelCom(GraphMemFactory.createGraphMem());
    }

    /**
     * The constructor.
     *
     * <p> Used to generate a RDF representation of a dataset, or of a set of dataset (catalog).
     * Using Apache Jena Library. Initialize the Jena Model to build the representation and set the EDD from params.
     *
     * @param datasetID ID of the dataset (used to retrieve the EDD dataset, and then get information from it)
     *
     */
    public RDFBuilder(String datasetID) {
        edd = gridDatasetHashMap.get(datasetID);
        if (edd == null) edd = tableDatasetHashMap.get(datasetID);
        model = new ModelCom(GraphMemFactory.createGraphMem());
    }

    /**
     * Initialize the model, set the globalURI, prefixes and create the mainNode.
     */
    public void initModel() {
        if (model != null)
            model.removeAll();

        globalURI = EDStatic.baseUrl + "/" + warName + "/" +
                (edd instanceof EDDTable ? "tabledap/" : "griddap/") + edd.datasetID();
        model = new ModelCom(GraphMemFactory.createGraphMem());
        setNsPrefixes();
        mainNode = model.createResource(globalURI);
    }

    /**
     * Initialize the Hydra subsettings RDF representation part.
     *
     * @throws Exception if trouble
     */
    public void initHydraSubsettings() throws Exception {
        if(model == null) throw new Exception("in initHydraSubsettings, model have not been initialized");
        if(mainNode == null) throw new Exception("in initHydraSubsettings, mainNode have not been initialized");

        Resource dcatDistributionSubsetting = model.createResource(globalURI + "#.subsettingOpenDap");
        hydraSupportedOperationSubsetting = model.createResource();
        Resource hydraEndpointDescriptionSubsetting = model.createResource();
        hydraPropertySubsetting = model.createResource();
        mainNode.addProperty(RDFVocab.DCAT_distribution, dcatDistributionSubsetting);

        dcatDistributionSubsetting
                .addProperty(RDFVocab.RDF_type, RDFVocab.DCAT_Dataservice)
                .addProperty(RDFVocab.DCAT_servesDataset, mainNode)
                .addProperty(RDFVocab.HYDRA_entrypoint, globalURI, XSDDatatype.XSDanyURI)
                .addProperty(RDFVocab.DCAT_endPointDescription, hydraEndpointDescriptionSubsetting)
                .addProperty(RDFVocab.HYDRA_supportedOperation, hydraSupportedOperationSubsetting)
//                .addProperty(RDFVocab.SCHEMA_identifier, ..., XSDDatatype.XSD..)
//                .addProperty(RDFVocab.SCHEMA_description, ..., )
//                .addProperty(RDFVocab.SCHEMA_datePublished, ..., XSDDatatype.XSDdateTime)
//                .addProperty(RDFVocab.SCHEMA_dateModified, ..., XSDDatatype.XSDdateTime)
        ;

        hydraEndpointDescriptionSubsetting
                .addProperty(RDFVocab.RDF_type, RDFVocab.HYDRA_ApiDocumentation)
                .addProperty(RDFVocab.HYDRA_entrypoint, globalURI, XSDDatatype.XSDanyURI)
                .addProperty(RDFVocab.HYDRA_title, "ERDDAP Api")
                .addProperty(RDFVocab.HYDRA_description, "ERDDAP Api to get " + edd.datasetID() + ", or subsettings of the "+ edd.datasetID() + " dataset")
        ;

        hydraSupportedOperationSubsetting
                .addProperty(RDFVocab.HYDRA_property, hydraPropertySubsetting)
                .addProperty(RDFVocab.RDF_type, RDFVocab.HYDRA_Operation)
                .addProperty(RDFVocab.HYDRA_method, "GET", XSDDatatype.XSDstring);
    }

    /**
     * Set the prefixes of the RDF Representation.
     */
    public void setNsPrefixes(){
        SortedMap<String, String> prefix = new TreeMap<>();
        int lPrefixes = RDFVocab.prefixesName.length;

        for(int i=0; i < lPrefixes; i++)
            prefix.put(RDFVocab.prefixesName[i], RDFVocab.prefixesURI[i]);

        model.setNsPrefixes(prefix);
    }

    /**
     * Append the Global Attributes from the dataset, to the RDF representation.
     *
     * @throws Exception if trouble
     */
    public void setRDFGlobalAttributes() throws Exception {
        Property gaRelation;
        String sValue,
               tId = edd.datasetID();

        boolean isGrid = true;
        EDDGrid eddGrid = gridDatasetHashMap.get(tId);
        EDDTable eddTable;
        EDD edd = gridDatasetHashMap.get(tId);
        Table table = null;

        mainNode.addProperty(RDFVocab.RDF_type,
                model.createProperty(RDFVocab.DCAT_URI, "Dataset")
        );

        Attributes globalAttributes;
        EDVGridAxis[] axisVariables = null;
        int nVar;

        if (edd == null) {
            try {
                isGrid = false;
                eddTable = tableDatasetHashMap.get(tId);
                edd = tableDatasetHashMap.get(tId);
                table = eddTable.makeEmptyDestinationTable(0, "s", "", true); //as if userDapQuery was for everything
                globalAttributes = table.globalAttributes();
                nVar = table.nColumns();
            } catch (Throwable t) {throw new Exception("in RDFBuilder.setRDFGlobalAttributes : " + t);}
        } else {
            globalAttributes = eddGrid.combinedGlobalAttributes();
            axisVariables = eddGrid.axisVariables();
            nVar = axisVariables.length;
        }

        if (edd == null || globalAttributes == null) //perhaps just deleted or no GlobalAttributes Available
            return;

        StringArray gaNames = new StringArray(globalAttributes.getNames());
        int gaSize = gaNames.size();
        String[] timeCoverage      = {"", ""};
        String[] latitudeCoverage  = {"", ""};
        String[] longitudeCoverage = {"", ""};
        XSDDatatype gaDataType;

        SortedMap<Integer, Resource> nodeContributor = new TreeMap<>();
        SortedMap<Integer, Resource> nodeCreator = new TreeMap<>();
        SortedMap<Integer, Resource> nodePublisher = new TreeMap<>();
        String[] typeNode = {"person", "person", "person"};

        for (int nGa = 0; nGa < gaSize; nGa++) {
            gaRelation = null;
            gaDataType = null;

            String gaName = gaNames.get(nGa).trim().toLowerCase();

            if (globalAttributes.get(gaName) == null) continue;

            sValue = globalAttributes.get(gaName).getString(0);

            if (gaName.equals("title")) gaRelation = RDFVocab.DCT_title;
            else if (gaName.equals("license")) {
                gaRelation = RDFVocab.DCT_license;
                if(sValue.startsWith("http")){
                    mainNode.addProperty(gaRelation, model.createResource(sValue));
                    continue;
                }
            }
            else if (gaName.equals("summary")) gaRelation = RDFVocab.DCT_description;
            else if (gaName.equals("time_coverage_start")) timeCoverage[0] = sValue;
            else if (gaName.equals("time_coverage_end"))   timeCoverage[1] = sValue;
            else if (gaName.equals("geospatial_lat_min"))  latitudeCoverage[0]  = sValue;
            else if (gaName.equals("geospatial_lat_max"))  latitudeCoverage[1]  = sValue;
            else if (gaName.equals("geospatial_lon_min"))  longitudeCoverage[0] = sValue;
            else if (gaName.equals("geospatial_lon_max"))  longitudeCoverage[1] = sValue;
            else if (gaName.equals("date_created")) {
                gaRelation = RDFVocab.DCT_issued;
                gaDataType = XSDDatatype.XSDdateTime;
            } else if (gaName.equals("keywords")) {
                String[] saKeyword = sValue.split(",", 0);
                for (String keyword : saKeyword) {
                    if (!keyword.isEmpty())
                        mainNode.addProperty(RDFVocab.DCAT_keyword, keyword.trim(), XSDDatatype.XSDstring);
                }
            } else if (gaName.startsWith("contributor") || gaName.startsWith("creator") || gaName.startsWith("publisher")) {
                String[] saValues = sValue.split(",|;", -1);
                Property lightRelation;

                int nPersonRole = 2;
                SortedMap<Integer, Resource> nodeCurrent = nodePublisher;

                if (gaName.startsWith("contributor")) {  nPersonRole = 0;  nodeCurrent = nodeContributor; }
                else if (gaName.startsWith("creator")) { nPersonRole = 1;  nodeCurrent = nodeCreator;     }

                for (int i = 0; i < saValues.length; i++) {

                    if (gaName.endsWith("_type") && !sValue.equalsIgnoreCase("person"))
                        typeNode[nPersonRole] = sValue.toLowerCase().trim();
                    if (gaName.endsWith("_institution")) typeNode[nPersonRole] = "institution";

                    if (gaName.equals("contributor") || gaName.equals("creator") || gaName.equals("publisher"))
                        lightRelation = RDFVocab.FOAF_name;
                    else if (gaName.endsWith("_name")) lightRelation = RDFVocab.FOAF_name;
                    else if (gaName.endsWith("_email")) lightRelation = RDFVocab.FOAF_mbox;
                    else if (gaName.endsWith("_url")) lightRelation = RDFVocab.FOAF_workplaceHomepage;
                    else continue;

                    if (nodeCurrent.size()-1 < i)
                        nodeCurrent.put(i, model.createResource());
                    if (saValues[i] != null && !saValues[i].isEmpty() && !saValues[i].equalsIgnoreCase("none"))
                        nodeCurrent.get(i).addProperty(lightRelation, saValues[i]);
                }
            }

            if (gaRelation != null && !sValue.isEmpty()) {
                if (gaDataType == null) {
                    mainNode.addProperty(gaRelation, sValue);
                    continue;
                }
                mainNode.addProperty(gaRelation, sValue, gaDataType);
            }
        }

        Attributes attr;
        StringArray saAttr;
        String columnName, sAttr, datasetValueIRI = "";
        int datasetPriorityIRI = 99;

        for (int iVar = 0; iVar < nVar; iVar++) {

            if (isGrid) {
                attr = axisVariables[iVar].combinedAttributes();
                saAttr = new StringArray(attr.getNames());
                columnName = axisVariables[iVar].destinationName();
            } else {
                attr = table.columnAttributes(iVar);
                saAttr = new StringArray(attr.getNames());
                columnName = table.getColumnName(iVar);
            }

            if (columnName.equals("ScientificName")) {
                int nSaAttr = saAttr.size();
                for (int iAttr = 0; iAttr < nSaAttr; iAttr++) {
                    sAttr = saAttr.get(iAttr);
                    if (sAttr.equals("sdn_P02_uri") && datasetPriorityIRI > 1) datasetPriorityIRI = 1;
                    else if (sAttr.equals("sdn_P02_urn") && datasetPriorityIRI > 2) datasetPriorityIRI = 2;
                    else if (sAttr.equals("sdn_P02_name") && datasetPriorityIRI > 3) datasetPriorityIRI = 3;
                    else continue;
                    datasetValueIRI = attr.get(sAttr).getRawString(0);
                }
            }
        }

        mainNode.addProperty(RDFVocab.DCT_identifier, tId)
                .addProperty(RDFVocab.DCAT_landingPage, model.createResource(globalURI));

        if (!datasetValueIRI.isEmpty())
            mainNode.addProperty(RDFVocab.DCT_type, model.createResource(datasetValueIRI));

        if (!timeCoverage[0].isEmpty() && !timeCoverage[1].isEmpty()){
            mainNode.addProperty(RDFVocab.DCT_temporal,
                    model.createResource()
                            .addProperty(RDFVocab.RDF_type, RDFVocab.DCT_PeriodOfTime)
                            .addProperty(RDFVocab.DCAT_startDate, timeCoverage[0], XSDDatatype.XSDdateTime)
                            .addProperty(RDFVocab.DCAT_endDate, timeCoverage[1], XSDDatatype.XSDdateTime)
            );

            if(!timeVarName.isEmpty()) {
                Resource temporal = model.createResource();
                hydraSupportedOperationSubsetting.addProperty(
                        RDFVocab.DCT_temporal,
                        temporal.addProperty(RDFVocab.RDF_type, RDFVocab.DCT_Location)
                );
                addFairEaseGenerator(temporal, "{" + timeVarName + ".max}", XSDDatatype.XSDdateTime, RDFVocab.DCAT_endDate.getURI(), RDFVocab.XSD_date.getURI());
                addFairEaseGenerator(temporal, "{" + timeVarName + ".min}", XSDDatatype.XSDdateTime, RDFVocab.DCAT_endDate.getURI(), RDFVocab.XSD_date.getURI());
            }
        }

        if (latestUpdate != null && !latestUpdate.isEmpty())
            mainNode.addProperty(RDFVocab.DCT_modified, latestUpdate);

        if ( (!latitudeCoverage[0].isEmpty() || !latitudeCoverage[1].isEmpty()) &&
                (!longitudeCoverage[0].isEmpty() || !longitudeCoverage[1].isEmpty()) ) {
            RDFDatatype wktLiteral = new BaseDatatype(RDFVocab.GSP_URI + "wktLiteral");
            TypeMapper.getInstance().registerDatatype(wktLiteral);

            mainNode.addProperty(RDFVocab.DCT_spatial,
                    model.createResource()
                            .addProperty(RDFVocab.RDF_type, RDFVocab.DCT_Location)
                            .addProperty(
                                    RDFVocab.DCAT_bbox,
                                    getWktLiteralString(latitudeCoverage, longitudeCoverage),
                                    wktLiteral
                            )
            );

            if(!latLongVarName[0].isEmpty() && !latLongVarName[1].isEmpty()) {
                Resource spatial = model.createResource();

                hydraSupportedOperationSubsetting.addProperty(
                        RDFVocab.DCT_spatial,
                        spatial.addProperty(RDFVocab.RDF_type, RDFVocab.DCT_PeriodOfTime)
                );

                String[] latitudeVar =  {"{"+latLongVarName[0]+".min}", "{"+latLongVarName[0]+".max}"};
                String[] longitudeVar = {"{"+latLongVarName[1]+".min}", "{"+latLongVarName[1]+".max}"};

                if(latitudeCoverage[0].equals(latitudeCoverage[1]))
                    latitudeVar = new String[]{"{"+latLongVarName[0]+".max}", "{"+latLongVarName[0]+".max}"};
                if(longitudeCoverage[0].equals(longitudeCoverage[1]))
                    longitudeVar = new String[]{"{"+latLongVarName[1]+".max}", "{"+latLongVarName[1]+".max}"};

                addFairEaseGenerator(spatial,
                        getWktLiteralString(latitudeVar, longitudeVar),
                        null, RDFVocab.DCAT_bbox.getURI(), RDFVocab.GSP_WktLiteral.getURI());
            }
        }

        SortedMap<Integer, Resource> nodeList = null;
        Property link = null;
        for(int i=0; i < 3; i++) {
            switch (i) {
                case 0 -> {nodeList = nodeContributor; link = RDFVocab.DCAT_contactPoint; }
                case 1 -> {nodeList = nodeCreator;     link = RDFVocab.DCT_creator;       }
                case 2 -> {nodeList = nodePublisher;   link = RDFVocab.DCT_publisher;     }
            }

            int lNode = nodeList.size();
            if (lNode == 0 || link == null) continue;
            for (int j = 0; j < lNode; j++) {
                nodeList.get(j).addProperty(RDFVocab.RDF_type, RDFVocab.FOAF_person);

                if (typeNode[i].equals("institution") || typeNode[i].equals("organization")) {
                    mainNode.addProperty(link, model.createResource()
                                    .addProperty(
                                            RDFVocab.RDF_type, (typeNode[i].equals("organization") ?
                                                            RDFVocab.FOAF_organization : RDFVocab.FOAF_group))
                                    .addProperty(RDFVocab.FOAF_member, nodeList.get(j))
                    );
                } else if (typeNode[i].equals("person")) mainNode.addProperty(link, nodeList.get(j));
            }
        }
    }

    /**
     * Return the wktLiteral formated string, based on the given parameters.
     * Does not generate MULTILINE or MULTIPOLYGON or any MULTI.. .
     *
     * @param latitude    contain latitude values [{latitude.min}, {latitude.max}]
     * @param longitude   contain longitude values [{longitude.min}, {longitude.max}]
     * @return            wktLiteral formated string
     */
    public String getWktLiteralString(String[] latitude, String[] longitude){
        int lLatitude = latitude.length,
            lLongitude = longitude.length,
            nLocation = lLatitude * lLongitude,
            varFor = 0;

        if(latitude[0].equals(latitude[1])) lLatitude = 1;
        if(longitude[0].equals(longitude[1])){
            lLongitude = 1;
            if(lLatitude == 2) varFor=1;
        }

        String sLatLong = (lLatitude == 1 && lLongitude == 1) ? "POINT (" :
                          (lLatitude == 1 || lLongitude == 1) ? "LineString (("  :
                                "POLYGON ((";

        if(lLatitude == 2) nLocation++;

        for(; varFor < nLocation; varFor++)
            sLatLong += latitude[(varFor>>1)%2] + " " + longitude[((varFor+1)>>1)%2] + (varFor == nLocation-1 ? "" : ",");
        sLatLong += nLocation > 1 ? "))" : ")";

        return sLatLong;
    }

    /**
     * Create a fairEaseGenerator blank-node node with given params, and append it to the {node}.
     *
     * @param node                  the node to which the fairEaseGenerator blank node will be added to.
     * @param valueTemplate         template string, to which
     * @param valueTemplateDatatype the dateType of the valueTemplate (can be null, or of XSDDatatype class)
     * @param targetProperty        the target property of the valueTemplate (will be set as a Resource)
     * @param valueType             the datatype of the valueTemplate (will be set as a Resource)
     *
     */
    public void addFairEaseGenerator(Resource node, String valueTemplate, XSDDatatype valueTemplateDatatype, String targetProperty, String valueType){

        Resource FAIREASE_generatedValue = model.createResource();

        if(valueTemplateDatatype == null)
            FAIREASE_generatedValue.addProperty(RDFVocab.FAIREASE_valueTemplate, valueTemplate);
        else
            FAIREASE_generatedValue.addProperty(RDFVocab.FAIREASE_valueTemplate, valueTemplate, valueTemplateDatatype);

        FAIREASE_generatedValue
                .addProperty(RDFVocab.RDF_type,                RDFVocab.FAIREASE_TemplateTrick)
                .addProperty(RDFVocab.FAIREASE_valueType,      model.createResource(valueType))
                .addProperty(RDFVocab.FAIREASE_targetProperty, model.createResource(targetProperty));

        node.addProperty(RDFVocab.FAIREASE_generatedValue, FAIREASE_generatedValue);
    }

    /**
     * Create distribution Resources for each dataFileType available (except graph/picture ones).
     *
     * @throws Exception if trouble
     */
    public void setDistribution() throws Exception {

        int nDFTN;
//        int nIFTN;

//        String[][] allFileTypeOptionsAr;
//        String[] allFileTypeNames;

        String[] dataFileTypeNames;
//        String[][] dataFileTypeDescriptionsAr;

//        String imageFileTypeNames[];
//        String imageFileTypeDescriptionsAr[][];
//                publicGraphFileTypeNames

        if (edd instanceof EDDGrid) {
            nDFTN = EDDGrid.dataFileTypeNames.length;
//            nIFTN = EDDGrid.imageFileTypeNames.length;
            dataFileTypeNames = EDDGrid.dataFileTypeNames;
//            dataFileTypeDescriptionsAr = EDDGrid.dataFileTypeDescriptionsAr;
//            imageFileTypeNames = EDDGrid.imageFileTypeNames;
//            imageFileTypeDescriptionsAr = EDDGrid.imageFileTypeDescriptionsAr;
        } else if (edd instanceof EDDTable) {
            nDFTN = EDDTable.dataFileTypeNames.length;
//            nIFTN = EDDTable.imageFileTypeNames.length;
            dataFileTypeNames = EDDTable.dataFileTypeNames;
//            dataFileTypeDescriptionsAr = EDDTable.dataFileTypeDescriptionsAr;
// //            imageFileTypeNames = EDDTable.imageFileTypeNames;
// //            imageFileTypeDescriptionsAr = EDDTable.imageFileTypeDescriptionsAr;
        } else throw new Exception("in RDFBuilder.setDistribution, given edd not instance of EDDTable or EDDGrid");

//        allFileTypeOptionsAr = new String[EDStatic.nLanguages][nDFTN + nIFTN];
//        allFileTypeNames = new String[nDFTN];


//        for (int i = 0; i < nDFTN; i++) {
//            for (int tl = 0; tl < EDStatic.nLanguages; tl++)
//                allFileTypeOptionsAr[tl][i] = dataFileTypeNames[i] + " - " + dataFileTypeDescriptionsAr[tl][i];
//            allFileTypeNames[i] = dataFileTypeNames[i];
//        }

// //        String[] publicGraphFileTypeNames = new String[nIFTN];
// //        for (int i = 0; i < nIFTN; i++) {
// //            for (int tl = 0; tl < EDStatic.nLanguages; tl++)
// //                allFileTypeOptionsAr[tl][nDFTN + i] = imageFileTypeNames[i] + " - " + imageFileTypeDescriptionsAr[tl][i];
// //            publicGraphFileTypeNames[i] = imageFileTypeNames[i];
// //        }

        Resource res;

        String typeName;
        for(int i=0; i < nDFTN; i++){
//            typeName = allFileTypeNames[i];
            typeName = dataFileTypeNames[i];

            res = switch(typeName.toLowerCase()){
                case ".csv", ".csvp", ".csv0", ".nccsv", ".nccsvmetadata"
                               -> RDFVocab.FF_CSV;
                case ".graph", ".help", ".html", ".htmltable"
                               -> RDFVocab.FF_HTML;
                case ".fgdc", ".iso19115", ".ncml"
                               -> RDFVocab.FF_XML;
                case ".tsv", ".tsvp", ".tsv0"
                               -> RDFVocab.FF_TSV;
                case ".json", ".ncojson"
                               -> RDFVocab.FF_JSON;
                case ".n3"     -> RDFVocab.FF_N3;
                case ".ttl"    -> RDFVocab.FF_TURTLE;
                case ".trig"   -> RDFVocab.FF_TRIG;
                case ".jsonld" -> RDFVocab.FF_JSONLD;
                case ".nq"     -> RDFVocab.FF_NQ;
                case ".rdfxml" -> RDFVocab.FF_RDFXML;
                case ".nt"     -> RDFVocab.FF_NT;
                case ".xhtml"  -> RDFVocab.FF_XHTML;
                case ".dods"   -> RDFVocab.FF_DODS;
                default        -> null;
//              default :
//                ".jsonlCSV1" ".jsonlCSV" ".jsonlKVP" => "application/x-jsonlines"
//                ".nc" => "application/x-netcdf"
//                ".wav" => "audio/wav" | "audio/x-wav"
//                ".itx" ".mat" => "application/x-download"
//                ".asc" ".das" ".dds" ".esriAscii" ".ncHeader" ".odvTxt" ".timeGaps" => "text/plain"
            };

            Resource localDistrib = model.createResource(globalURI + "#" + typeName)
                    .addProperty(RDFVocab.RDF_type, RDFVocab.DCAT_Distribution)
                    .addProperty(RDFVocab.DCAT_downloadURL, globalURI + typeName);

            if (res == null) localDistrib.addProperty(RDFVocab.DCT_format, typeName);
            else localDistrib.addProperty(RDFVocab.DCT_mediaType, res);

            mainNode.addProperty(RDFVocab.DCAT_distribution, localDistrib);
        }
    }

    /**
     * Generate the RDF representation of the column properties, for each column.
     *
     * @throws Exception if trouble
     */
    public void addVariablesAttributes() throws Exception {

        String tId = edd.datasetID();

        EDDGrid eddGrid = gridDatasetHashMap.get(tId);
        EDDTable eddTable = null;
        EDD lEdd = eddGrid;
        Table table = null;
        EDVGridAxis[] axisVariables = null;
        EDV[] dataVariable = null;
        int nVar = 0;
        boolean isGrid = true;

        blankNodeConformsTo = model.createResource();
        blankNodeConformsTo.addProperty(RDFVocab.RDF_type, RDFVocab.CSVW_TableSchema);
        mainNode.addProperty(RDFVocab.DCT_conformsTo, blankNodeConformsTo);

        if (lEdd != null) {
            axisVariables = eddGrid.axisVariables();
            dataVariable = eddGrid.dataVariables();
            nVar = axisVariables.length;
        } else {
            try {
                eddTable = tableDatasetHashMap.get(tId);
                lEdd = eddTable;
                table = eddTable.makeEmptyDestinationTable(0, "s", "", true); //as if userDapQuery was for everything
                nVar = table.nColumns();
                isGrid = false;
            } catch(Throwable t){System.out.println("In RDFBuilder, Cannot get DestinationTable for : " + tId);}
        }

        if(lEdd == null || nVar == 0) return;

        Attributes attr;
        StringArray saAttr;
        String columnName;
        String columnType;
        String queryPattern = "";
        String accessPattern = "";

        for (int iVar=0; iVar<nVar; iVar++){
            attr =  null;
            columnName = "";
            columnType = "";

            if(isGrid){
                attr = axisVariables[iVar].combinedAttributes();
                saAttr = new StringArray(attr.getNames());
                columnName = axisVariables[iVar].destinationName();
                columnType = OpendapHelper.getAtomicType(axisVariables[iVar].destinationDataPAType());
                accessPattern += "[({"+ columnName + ".min}):{" + columnName + ".str}:({" + columnName + ".max})]";
            } else {
                attr = table.columnAttributes(iVar);
                saAttr = new StringArray(attr.getNames());
                columnName = table.getColumnName(iVar);
                columnType = table.getColumn(iVar).elementTypeString();
            }

            if(attr.size() > 0 && saAttr.size() > 0 && !columnName.isEmpty() && !columnType.isEmpty()){
                addHydraMappingForVariable(isGrid, columnType, columnName, saAttr, attr);
                addRDFVariableAttributes(columnType, columnName, saAttr, attr);
            }
        }

        if (isGrid){
            nVar = dataVariable.length;

            for (int iVar=0; iVar<nVar; iVar++){
                attr = dataVariable[iVar].combinedAttributes();
                saAttr = new StringArray(attr.getNames());
                columnName = dataVariable[iVar].destinationName();
                columnType = OpendapHelper.getAtomicType(dataVariable[iVar].destinationDataPAType());
                queryPattern += (iVar > 0 ? ",": "") + columnName + accessPattern;

                if(attr.size() > 0 && saAttr.size() > 0 && !columnName.isEmpty() && !columnType.isEmpty()){
                    addHydraMappingForVariable(false, columnType, columnName, saAttr, attr);
                    addRDFVariableAttributes(columnType, columnName, saAttr, attr);
                }
            }
        }
        else {
            String sConstrain = "";
            String sVariable = "";
            int lGetColumn = table.getColumnNames().length;

            for(int i=0; i<lGetColumn; i++) {
                String variable = table.getColumnNames()[i];
                sVariable += (i > 0 ? "," : "") + variable;
                sConstrain += "&" + variable + ">={" + variable + ".min}&"+ variable + "<={" + variable + ".max}";
            }
            queryPattern = sVariable + sConstrain;
        }

        hydraPropertySubsetting.addProperty(RDFVocab.HYDRA_template, globalURI + ".{format}?" + queryPattern);
    }

    /**
     * Generate the RDF representation for the given column.
     *
     * @param columnType the dataType of the column
     * @param columnName the name of the column
     * @param saAttr     the properties of the given column
     * @param attr       contains value of the properties of the given column
     * @throws Exception if trouble
     */
    public void addRDFVariableAttributes(String columnType, String columnName, StringArray saAttr, Attributes attr) throws Exception{

        Property relation;

        String attrValue,
                sAttr,
                units = "",
                columnValueIRI = "";

        int nAttr = saAttr.size(),
            columnProrityIRI = 99;

        Resource rColumn = model.createResource(globalURI + "#" + columnName);
        blankNodeConformsTo.addProperty(RDFVocab.CSVW_column, rColumn);

        rColumn.addProperty(RDFVocab.RDF_type, RDFVocab.CSVW_Column)
                .addProperty(RDFVocab.CSVW_name, columnName);


        for(int iAttr=0; iAttr<nAttr; iAttr++){
            sAttr = saAttr.get(iAttr);
            relation = null;
            attrValue = attr.get(sAttr).getRawString(0);

            if(sAttr.equals("actual_range")) {
                XSDDatatype dataType = getXSDDatatypeXsdType(columnType);
                if (dataType == null) continue;
                if(timeVarName.equals(columnName)){

                    PrimitiveArray pa = attr.get(sAttr);
                    String tp = attr.getString(EDV.TIME_PRECISION);
                    String beg = Calendar2.epochSecondsToLimitedIsoStringT(tp, pa.getDouble(0), "");
                    String end = Calendar2.epochSecondsToLimitedIsoStringT(tp, pa.getDouble(1), "");
                    rColumn.addProperty(RDFVocab.QUDT_lowerBound, beg, XSDDatatype.XSDdateTime);
                    rColumn.addProperty(RDFVocab.QUDT_upperBound, end, XSDDatatype.XSDdateTime);
                } else {
                    rColumn.addProperty(RDFVocab.QUDT_lowerBound, attrValue, dataType);
                    rColumn.addProperty(RDFVocab.QUDT_upperBound, attr.get(sAttr).getRawString(1), dataType);
                }
            }
            else if(sAttr.equals("long_name")) relation = RDFVocab.CSVW_titles;
            else if(sAttr.equals("units_uri")) units = attrValue;
            else if(sAttr.equals("sdn_parameter_uri")  && columnProrityIRI > 1){columnProrityIRI=1; columnValueIRI = attrValue;}
            else if(sAttr.equals("sdn_parameter_urn")  && columnProrityIRI > 2){columnProrityIRI=2; columnValueIRI = attrValue;}
            else if(sAttr.equals("sdn_parameter_name") && columnProrityIRI > 3){columnProrityIRI=3; columnValueIRI = attrValue;}
            else if(sAttr.equals("_CoordinateAxisType")){
                switch(attrValue.toLowerCase()) {
                    case "lat"  -> latLongVarName[0] = columnName;
                    case "lon"  -> latLongVarName[1] = columnName;
                    case "time" -> timeVarName       = columnName;
                }
            }

            if (relation != null && !(attrValue.isEmpty())) rColumn.addProperty(relation, attrValue);
        }

        if(units.isEmpty()){
            Resource dataTypeResource = getResourceXsdType(columnType);
            if (dataTypeResource != null) rColumn.addProperty(RDFVocab.SH_dataType, dataTypeResource);
        } else rColumn.addProperty(RDFVocab.SH_dataType, model.createResource(units));

        if(!columnValueIRI.isEmpty()) rColumn.addProperty(RDFVocab.SH_path, model.createResource(columnValueIRI));
    }

    /**
     * Generate the Hydra Mapping part, of the given columnName.
     *
     * @param isRequired boolean, set the "required" properties to "yes" or "no"
     * @param columnType the dataType of the column
     * @param columnName the name of the column
     * @param saAttr     the properties of the given column
     * @param attr       contains value of the properties of the given column
     * @throws Exception if trouble
     */
    public void addHydraMappingForVariable(boolean isRequired, String columnType, String columnName, StringArray saAttr, Attributes attr) throws Exception {

        XSDDatatype columnDatatype = getXSDDatatypeXsdType(columnType);

        String   add = "",
                description = "",
                sAttr = "";
        String[] minMax = {"", ""};
        int nAttr = saAttr.size();

        for(int iAttr=0; iAttr<nAttr; iAttr++){
            sAttr = saAttr.get(iAttr);

            switch (sAttr.toLowerCase()) {
                case "_coordinateaxistype" -> {
                    if (attr.get(sAttr).getRawString(0).equalsIgnoreCase("time"))
                        columnDatatype = XSDDatatype.XSDdateTime;
                }
                case "actual_range" -> {
                    minMax[0] = attr.get(sAttr).getRawString(0);
                    minMax[1] = attr.get(sAttr).getRawString(1);
                }
                case "comment" -> description = attr.get(sAttr).getRawString(0);
            }
        }

        if (columnDatatype.equals(XSDDatatype.XSDdateTime)){
            String tp = attr.getString(EDV.TIME_PRECISION);
            minMax[0] = Calendar2.epochSecondsToLimitedIsoStringT(tp, Double.parseDouble(minMax[0]), "");
            minMax[1] = Calendar2.epochSecondsToLimitedIsoStringT(tp, Double.parseDouble(minMax[1]), "");
        }

        int nbParams = (edd instanceof EDDGrid ? 4 : 3);

        for(int i=0; i<nbParams; i++){
            Resource localMappingNode = model.createResource();
            if(i==1) add = "min";
            if(i==2) add = "max";
            if(i==3) add = "str";

            localMappingNode
                    .addProperty(RDFVocab.RDF_type, RDFVocab.HYDRA_IriTemplateMapping)
                    .addProperty(RDFVocab.HYDRA_variable, (add.isEmpty() ? columnName : columnName + "." + add),
                            XSDDatatype.XSDstring)
                    .addProperty(RDFVocab.HYDRA_required, (isRequired ? "true" : "false"), XSDDatatype.XSDboolean);

            if (i==0){
                localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, columnName, XSDDatatype.XSDstring);
            } else if (i==1 && !minMax[0].isEmpty()){
                if(columnDatatype == null) localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, minMax[0]);
                else localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, minMax[0], columnDatatype);
            } else if (i==2 && !minMax[1].isEmpty()){
                if(columnDatatype == null) localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, minMax[1]);
                else localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, minMax[1], columnDatatype);
            } else if (i==3) localMappingNode.addProperty(RDFVocab.SCHEMA_defaultvalue, "1");

            if(!description.isEmpty())
                localMappingNode.addProperty(RDFVocab.RDFS_label, add + " - " + description);

            if(columnDatatype != null)
                localMappingNode.addProperty(RDFVocab.RDFS_range,
                        (i == 0 ? XSDDatatype.XSDstring.getURI() : columnDatatype.getURI())
                );

            hydraPropertySubsetting.addProperty(RDFVocab.HYDRA_mapping, localMappingNode);
        }

        hydraPropertySubsetting.addProperty(RDFVocab.HYDRA_mapping, model.createResource()
                .addProperty(RDFVocab.RDF_type, RDFVocab.HYDRA_IriTemplateMapping)
                .addProperty(RDFVocab.HYDRA_variable, "format")
                .addProperty(RDFVocab.RDFS_label, "File extension Format")
                .addProperty(RDFVocab.HYDRA_required, "true")
                .addProperty(RDFVocab.SCHEMA_defaultvalue, "htmlTable")
        );
    }

    /**
     * Find the XSD resource associated to a variable type.
     *
     * @param  type String the variable type
     * @return      corresponding Resource
     */
    public Resource getResourceXsdType(String type){
        return switch (type.toLowerCase()) {
            case "float32", "float"  -> RDFVocab.XSD_float;
            case "float64", "double" -> RDFVocab.XSD_double;
            case "int64", "long"     -> RDFVocab.XSD_long;
            case "uint64", "ulong"   -> RDFVocab.XSD_unsignedLong;
            case "int32", "int"      -> RDFVocab.XSD_int;
            case "uint32", "uint"    -> RDFVocab.XSD_unsignedInt;
            case "int16", "short"    -> RDFVocab.XSD_short;
            case "uint16", "ushort"  -> RDFVocab.XSD_unsignedShort;
            case "byte"              -> RDFVocab.XSD_byte;
            case "ubyte"             -> RDFVocab.XSD_unsignedByte;
            case "char", "string"    -> RDFVocab.XSD_string;
            default                  -> null;
        };
    }

    /**
     * Find the XSDDatatype associated to a variable type.
     *
     * @param  type String the variable type
     * @return      corresponding XSDDatatype
     */
    public XSDDatatype getXSDDatatypeXsdType(String type){
        return switch (type.toLowerCase()) {
            case "float32", "float"  -> XSDDatatype.XSDfloat;
            case "float64", "double" -> XSDDatatype.XSDdouble;
            case "int64", "long"     -> XSDDatatype.XSDlong;
            case "uint64", "ulong"   -> XSDDatatype.XSDunsignedLong;
            case "int32", "int"      -> XSDDatatype.XSDint;
            case "uint32", "uint"    -> XSDDatatype.XSDunsignedInt;
            case "int16", "short"    -> XSDDatatype.XSDshort;
            case "uint16", "ushort"  -> XSDDatatype.XSDunsignedShort;
            case "byte"              -> XSDDatatype.XSDbyte;
            case "ubyte"             -> XSDDatatype.XSDunsignedByte;
            case "char", "string"    -> XSDDatatype.XSDstring;
            default                  -> null;
        };
    }

    /**
     * Set the latestUpdate Global Variable.
     * Need to do it like that, because not all instance have access to this information.
     *
     * @param date the Date (POSIX format ex : "1970-01-01T00:00:00Z")
     */
    public void setLatestUpdate(String date){latestUpdate = date;}

    /**
     * Return the model.
     * @return Model the model of this RDFBuilder instance
     */
    public Model getCurrentModel(){return model;}

    /**
     * Return the first Node / main Node.
     * @return Resource the mainNode of this RDFBuilder instance
     */
    public Resource getFirstNode(){return mainNode;}

    /**
     * Update the rdf representation of the dataset (delete everything, and re-create it).
     * @throws Exception if trouble
     */
    public void updateFullDatasetRDF() throws Exception {
        if (model != null)
            model.removeAll();
        initModel();
        buildFullDatasetRDF();
    }

    /**
     * Generate the complet RDF represantation of the current dataset.
     * @throws Exception if trouble
     */
    public void buildFullDatasetRDF() throws Exception {
        if(edd == null) throw new Exception("Cannot Generate RDF representation of an empty EDD");
        initModel();
        initHydraSubsettings();
        addVariablesAttributes();
        setRDFGlobalAttributes();
        setDistribution();
    }

    /**
     * Build the Catalog, and add the RDF representation of every given dataset in params.
     *
     * @param pRdfBuilder array of every RdfBuilder to add to the catalog
     * @throws Exception  if trouble
     */
    public void buildCatalog(RDFBuilder[] pRdfBuilder) throws Exception {
        globalURI = EDStatic.baseUrl + "/" + warName + "/";
        mainNode = model.createResource(globalURI);
        mainNode.addProperty(RDFVocab.RDF_type, RDFVocab.DCAT_Catalog);

        for (RDFBuilder node : pRdfBuilder) {
            model.add(node.getCurrentModel());
            mainNode.addProperty(RDFVocab.DCAT_dataset, node.getFirstNode());
        }
    }

    /**
     * Serialize the RDF Representation to RDF-XML and write it on the outputStreamSource.
     *
     * @param outputStream the outputStream where the serialization will be print on
     */
    public void writeRDFSerialized(OutputStream outputStream){
        writeRDFSerialized(Lang.RDFXML, outputStream);}

    /**
     * Serialize the RDF Representation to the given Language and write it on the outputStreamSource.
     *
     * @param sLang        the serialization Language
     * @param outputStream the outputStream where the serialization will be print on
     */
    public void writeRDFSerialized(String sLang, OutputStream outputStream){
        Lang lLang = switch (sLang) {
            case ".turtle" -> Lang.TURTLE;       // => turtle
            case ".ttl"       -> Lang.TTL;       // => ttl
            case ".n3"        -> Lang.N3;        // => n3 (looks like turtle)
            case ".trig"      -> Lang.TRIG;      // => trig (look like turtle)
            case ".ntriples"  -> Lang.NTRIPLES;  // => n-triples
            case ".nt"        -> Lang.NT;        // => n-triples
            case ".nquads"    -> Lang.NQUADS;    // => nquads
            case ".nq"        -> Lang.NQ;        // => nquads
            case ".jsonld"    -> Lang.JSONLD;    // => json-ld
            case ".jsonld10"  -> Lang.JSONLD10;  // => json-ld 10
            case ".jsonld11"  -> Lang.JSONLD11;  // => json-ld 11
            case ".rdfjson"   -> Lang.RDFJSON;   // => RDF-JSON
            case ".rdfproto"  -> Lang.RDFPROTO;  // doesn't fully work => strange glyphs
            case ".rdfthrift" -> Lang.RDFTHRIFT; // doesn't fully work => strange glyphs
            case ".shaclc"    -> Lang.SHACLC;    // doesn't fully work => just show prefixes
            case ".csv"       -> Lang.CSV;       // doesn't work       => nothing
            case ".tsv"       -> Lang.TSV;       // doesn't work       => nothing
            default           -> Lang.RDFXML;    // RDF-XML
        };
        writeRDFSerialized(lLang, outputStream);
    }

    /**
     * Serialize the RDF Representation to the given Language and write it on the outputStreamSource.
     *
     * @param lang         the Lang to serialize the RDF Representation
     * @param outputStream the outputStream where the serialization will be print on
     */

    public void writeRDFSerialized(Lang lang, OutputStream outputStream) {
        RDFWriter.create().source(model).lang(lang).base(globalURI).output(outputStream);
    }
}