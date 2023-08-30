package gov.noaa.pfel.erddap;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class RDFVocab {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final String DCAT_URI = "https://www.w3.org/ns/dcat#";
    public static final String RDF_URI = "https://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String DCT_URI = "http://purl.org/dc/terms/";
    public static final String SH_URI = "https://www.w3.org/ns/shacl#";
    public static final String FOAF_URI = "http://xmlns.com/foaf/0.1/";
    public static final String XSD_URI = "http://www.w3.org/2001/XMLSchema#";
    public static final String QUDT_URI = "http://qudt.org/schema/qudt/";
    public static final String CSVW_URI = "http://www.w3.org/ns/csvw#";
    public static final String HYDRA_URI = "http://www.w3.org/ns/hydra/core#";
    public static final String RDFS_URI = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String SCHEMA_URI = "http://schema.org/";
    public static final String GSP_URI = "http://www.opengis.net/ont/geosparql#";
    public static final String FAIREASE_URI = "https://fairease.eu#";

    // =======================================================================
    // Prefixes :
    // =======================================================================
    public static final String[] prefixesName = {"dcat", "rdf", "dct", "sh", "foaf", "xsd",
            "qudt", "csvw", "hydra", "rdfs", "schema", "gsp", "fairease"};
    public static final String[] prefixesURI = {
            "https://www.w3.org/ns/dcat#",
            "https://www.w3.org/1999/02/22-rdf-syntax-ns#",
            "http://purl.org/dc/terms/",
            "https://www.w3.org/ns/shacl#",
            "http://xmlns.com/foaf/0.1/",
            "http://www.w3.org/2001/XMLSchema#",
            "http://qudt.org/schema/qudt/",
            "http://www.w3.org/ns/csvw#",
            "http://www.w3.org/ns/hydra/core#",
            "http://www.w3.org/2000/01/rdf-schema#",
            "http://schema.org/",
            "http://www.opengis.net/ont/geosparql#",
            "https://fairease.eu#"
    };

    // =======================================================================
    // DCAT related
    // =======================================================================
    public static final Resource DCAT_Dataset;
    public static final Resource DCAT_Distribution;
    public static final Resource DCAT_Dataservice;
    public static final Resource DCAT_Catalog;
    public static final Property DCAT_landingPage;
    public static final Property DCAT_keyword;
    public static final Property DCAT_spatialResolutionInMeters;
    public static final Property DCAT_temporalResolution;
    public static final Property DCAT_startDate;
    public static final Property DCAT_endDate;
    public static final Property DCAT_bbox;
    public static final Property DCAT_contactPoint;
    public static final Property DCAT_distribution;
    public static final Property DCAT_downloadURL;
    public static final Property DCAT_servesDataset;
    public static final Property DCAT_dataset;
    public static final Property DCAT_endPointDescription;

    // =======================================================================
    // RDF related
    // =======================================================================
    public static final Property RDF_type;

    // =======================================================================
    // DCT
    // =======================================================================
    public static final Resource DCT_PeriodOfTime;
    public static final Resource DCT_Location;
    public static final Property DCT_type;
    public static final Property DCT_spatial;
    public static final Property DCT_temporal;
    public static final Property DCT_conformsTo;
    public static final Property DCT_description;
    public static final Property DCT_identifier;
    public static final Property DCT_issued;
    public static final Property DCT_license;
    public static final Property DCT_modified;
    public static final Property DCT_title;
    public static final Property DCT_creator;
    public static final Property DCT_publisher;
    public static final Property DCT_mediaType;
    public static final Property DCT_format;

    // =======================================================================
    // Shacl
    // =======================================================================
    public static final Resource SH_NodeShape;
    public static final Property SH_property;
    public static final Property SH_dataType;
    public static final Property SH_path;
    public static final Property SH_minCount;
    public static final Property SH_minInclusive;
    public static final Property SH_maxCount;
    public static final Property SH_maxInclusive;

    // =======================================================================
    // Friend-Of-A-Friend
    // =======================================================================
    public static final Resource FOAF_person;
    public static final Resource FOAF_organization;
    public static final Resource FOAF_group;
    public static final Property FOAF_mbox;
    public static final Property FOAF_name;
    public static final Property FOAF_workplaceHomepage;
    public static final Property FOAF_member;

    // =======================================================================
    // XSD
    // =======================================================================
    public static final Resource XSD_decimal;
    public static final Resource XSD_double;
    public static final Resource XSD_float;
    public static final Resource XSD_gDay;
    public static final Resource XSD_gMonth;
    public static final Resource XSD_gMonthDay;
    public static final Resource XSD_gYear;
    public static final Resource XSD_gYearMonth;
    public static final Resource XSD_hexBinary;
    public static final Resource XSD_int;
    public static final Resource XSD_integer;
    public static final Resource XSD_language;
    public static final Resource XSD_long;
    public static final Resource XSD_Name;
    public static final Resource XSD_NCName;
    public static final Resource XSD_NMTOKEN;
    public static final Resource XSD_negativeInteger;
    public static final Resource XSD_nonNegativeInteger;
    public static final Resource XSD_nonPositiveInteger;
    public static final Resource XSD_normalizedString;
    public static final Resource XSD_positiveInteger;
    public static final Resource XSD_short;
    public static final Resource XSD_string;
    public static final Resource XSD_time;
    public static final Resource XSD_token;
    public static final Resource XSD_unsignedByte;
    public static final Resource XSD_unsignedInt;
    public static final Resource XSD_unsignedLong;
    public static final Resource XSD_unsignedShort;
    public static final Resource XSD_anyURI;
    public static final Resource XSD_base64Binary;
    public static final Resource XSD_boolean;
    public static final Resource XSD_byte;
    public static final Resource XSD_date;
    public static final Resource XSD_dateTime;

    // =======================================================================
    // File Format
    // =======================================================================
    public static final Resource FF_N3;
    public static final Resource FF_CSV;
    public static final Resource FF_HTML;
    public static final Resource FF_TURTLE;
    public static final Resource FF_XML;
    public static final Resource FF_JSON;
    public static final Resource FF_TRIG;
    public static final Resource FF_JSONLD;
    public static final Resource FF_NQ;
    public static final Resource FF_RDFXML;
    public static final Resource FF_NT;
    public static final Resource FF_XHTML;
    public static final Resource FF_DODS;
    public static final Resource FF_TSV;

    // =======================================================================
    // QUDT
    // =======================================================================
    public static final Property QUDT_lowerBound;
    public static final Property QUDT_upperBound;

    // =======================================================================
    // CSVW
    // =======================================================================
    public static final Resource CSVW_Column;
    public static final Resource CSVW_Row;
    public static final Resource CSVW_TableSchema;
    public static final Resource CSVW_Table;
    public static final Property CSVW_column;
    public static final Property CSVW_row;
    public static final Property CSVW_rownum;
    public static final Property CSVW_titles;
    public static final Property CSVW_name;

    // =======================================================================
    // RDFS
    // =======================================================================
    public static final Property RDFS_label;
    public static final Property RDFS_range;

    // =======================================================================
    // SCHEMA
    // =======================================================================
    public static final Property SCHEMA_defaultvalue;

    // =======================================================================
    // HYDRA
    // =======================================================================
    public static final Resource HYDRA_IriTemplateMapping;
    public static final Resource HYDRA_Operation;
    public static final Resource HYDRA_ApiDocumentation;
    public static final Property HYDRA_variable;
    public static final Property HYDRA_mapping;
    public static final Property HYDRA_required;
    public static final Property HYDRA_supportedOperation;
    public static final Property HYDRA_entrypoint;
    public static final Property HYDRA_property;
    public static final Property HYDRA_method;
    public static final Property HYDRA_template;
    public static final Property HYDRA_title;
    public static final Property HYDRA_description;
    // =======================================================================
    // GeoSparql
    // =======================================================================
    public static final Resource GSP_WktLiteral;

    // =======================================================================
    // GeoSparql
    // =======================================================================
    public static final Resource FAIREASE_TemplateTrick;
    public static final Property FAIREASE_valueTemplate;
    public static final Property FAIREASE_valueType;
    public static final Property FAIREASE_targetProperty;
    public static final Property FAIREASE_generatedValue;


    public RDFVocab(){}

    static {
        // =======================================================================
        // DCAT related
        // =======================================================================
        DCAT_Dataset = m.createResource(DCAT_URI + "Dataset");
        DCAT_Distribution = m.createResource(DCAT_URI + "Distribution");
        DCAT_Dataservice = m.createResource(DCAT_URI + "Dataservice");
        DCAT_Catalog = m.createResource(DCAT_URI + "Catalog");
        DCAT_keyword = m.createProperty(DCAT_URI, "keyword");
        DCAT_landingPage = m.createProperty(DCAT_URI, "landingPage");
        DCAT_spatialResolutionInMeters = m.createProperty(DCAT_URI, "spatialResolutionInMeters");
        DCAT_temporalResolution = m.createProperty(DCAT_URI, "temporalResolution");
        DCAT_startDate = m.createProperty(DCAT_URI, "startDate");
        DCAT_endDate = m.createProperty(DCAT_URI, "endDate");
        DCAT_bbox = m.createProperty(DCAT_URI, "bbox");
        DCAT_contactPoint = m.createProperty(DCAT_URI, "contactPoint");
        DCAT_distribution = m.createProperty(DCAT_URI, "distribution");
        DCAT_downloadURL = m.createProperty(DCAT_URI, "downloadURL");
        DCAT_servesDataset = m.createProperty(DCAT_URI + "servesDataset");
        DCAT_dataset = m.createProperty(DCAT_URI + "dataset");
        DCAT_endPointDescription = m.createProperty(DCAT_URI + "endPointDescription");

        // =======================================================================
        // RDF related
        // =======================================================================
        RDF_type = m.createProperty(RDF_URI,"type");

        // =======================================================================
        // DCT relation
        // =======================================================================
        DCT_PeriodOfTime = m.createResource(DCT_URI + "PeriodOfTime");
        DCT_Location = m.createResource(DCT_URI + "Location");

        DCT_type = m.createProperty(DCT_URI, "type");
        DCT_spatial = m.createProperty(DCT_URI + "spatial");
        DCT_temporal = m.createProperty(DCT_URI, "temporal");
        DCT_conformsTo = m.createProperty(DCT_URI, "conformsTo");
        DCT_description = m.createProperty(DCT_URI, "description");
        DCT_identifier = m.createProperty(DCT_URI, "identifier");
        DCT_issued = m.createProperty(DCT_URI, "issued");
        DCT_license = m.createProperty(DCT_URI, "license");
        DCT_modified = m.createProperty(DCT_URI, "modified");
        DCT_title = m.createProperty(DCT_URI, "title");
        DCT_creator = m.createProperty(DCT_URI, "creator");
        DCT_publisher = m.createProperty(DCT_URI, "publisher");
        DCT_mediaType = m.createProperty(DCT_URI, "mediatype");
        DCT_format = m.createProperty(DCT_URI, "format");

        // =======================================================================
        // Shacl
        // =======================================================================
        SH_NodeShape = m.createResource(SH_URI + "NodeShape");
        SH_property = m.createProperty(SH_URI,"property");
        SH_dataType = m.createProperty(SH_URI,"dataType");
        SH_path = m.createProperty(SH_URI,"path");
        SH_minCount = m.createProperty(SH_URI,"minCount");
        SH_minInclusive = m.createProperty(SH_URI,"minInclusive");
        SH_maxCount = m.createProperty(SH_URI,"maxCount");
        SH_maxInclusive = m.createProperty(SH_URI,"maxInclusive");

        // =======================================================================
        // Friend-Of-A-Friend
        // =======================================================================
        FOAF_person = m.createResource(FOAF_URI + "person");
        FOAF_organization = m.createResource(FOAF_URI + "organization");
        FOAF_group = m.createResource(FOAF_URI + "group");
        FOAF_mbox = m.createProperty(FOAF_URI,"mbox");
        FOAF_name = m.createProperty(FOAF_URI,"name");
        FOAF_workplaceHomepage = m.createProperty(FOAF_URI,"workplaceHomepage");
        FOAF_member = m.createProperty(FOAF_URI,"member");

        // =======================================================================
        // XSD:types
        // =======================================================================
        XSD_decimal = m.createResource(XSD_URI + "decimal");
        XSD_double = m.createResource(XSD_URI + "double");
        XSD_float = m.createResource(XSD_URI + "float");
        XSD_gDay = m.createResource(XSD_URI + "gDay");
        XSD_gMonth = m.createResource(XSD_URI + "gMonth");
        XSD_gMonthDay = m.createResource(XSD_URI + "gMonthDay");
        XSD_gYear = m.createResource(XSD_URI + "gYear");
        XSD_gYearMonth = m.createResource(XSD_URI + "gYearMonth");
        XSD_hexBinary = m.createResource(XSD_URI + "hexBinary");
        XSD_int = m.createResource(XSD_URI + "int");
        XSD_integer = m.createResource(XSD_URI + "integer");
        XSD_language = m.createResource(XSD_URI + "language");
        XSD_long = m.createResource(XSD_URI + "long");
        XSD_Name = m.createResource(XSD_URI + "Name");
        XSD_NCName = m.createResource(XSD_URI + "NCName");
        XSD_NMTOKEN = m.createResource(XSD_URI + "NMTOKEN");
        XSD_negativeInteger = m.createResource(XSD_URI + "negativeInteger");
        XSD_nonNegativeInteger = m.createResource(XSD_URI + "nonNegativeInteger");
        XSD_nonPositiveInteger = m.createResource(XSD_URI + "nonPositiveInteger");
        XSD_normalizedString = m.createResource(XSD_URI + "normalizedString");
        XSD_positiveInteger = m.createResource(XSD_URI + "positiveInteger");
        XSD_short = m.createResource(XSD_URI + "short");
        XSD_string = m.createResource(XSD_URI + "string");
        XSD_time = m.createResource(XSD_URI + "time");
        XSD_token = m.createResource(XSD_URI + "token");
        XSD_unsignedByte = m.createResource(XSD_URI + "unsignedByte");
        XSD_unsignedInt = m.createResource(XSD_URI + "unsignedInt");
        XSD_unsignedLong = m.createResource(XSD_URI + "unsignedLong");
        XSD_unsignedShort = m.createResource(XSD_URI + "unsignedShort");
        XSD_anyURI = m.createResource(XSD_URI + "anyURI");
        XSD_base64Binary = m.createResource(XSD_URI + "base64Binary");
        XSD_boolean = m.createResource(XSD_URI + "boolean");
        XSD_byte = m.createResource(XSD_URI + "byte");
        XSD_date = m.createResource(XSD_URI + "date");
        XSD_dateTime = m.createResource(XSD_URI + "dateTime");

        // =======================================================================
        // File Format
        // =======================================================================
        FF_N3 = m.createResource("https://www.iana.org/assignments/media-types/text/n3");
        FF_CSV = m.createResource("https://www.iana.org/assignments/media-types/text/csv");
        FF_HTML = m.createResource("https://www.iana.org/assignments/media-types/text/html");
        FF_TURTLE = m.createResource("https://www.iana.org/assignments/media-types/text/turtle");
        FF_XML = m.createResource("https://www.iana.org/assignments/media-types/application/xml");
        FF_JSON = m.createResource("https://www.iana.org/assignments/media-types/application/json");
        FF_TRIG = m.createResource("https://www.iana.org/assignments/media-types/application/trig");
        FF_JSONLD = m.createResource("https://www.iana.org/assignments/media-types/application/ld+json");
        FF_NQ = m.createResource("https://www.iana.org/assignments/media-types/application/n-quads");
        FF_RDFXML = m.createResource("https://www.iana.org/assignments/media-types/application/rdf+xml");
        FF_NT = m.createResource("https://www.iana.org/assignments/media-types/application/n-triples");
        FF_XHTML = m.createResource("https://www.iana.org/assignments/media-types/application/xhtml+xml");
        FF_DODS = m.createResource("https://www.iana.org/assignments/media-types/application/octet-stream");
        FF_TSV = m.createResource("https://www.iana.org/assignments/media-types/text/tab-separated-values");

        // =======================================================================
        // QUDT
        // =======================================================================
        QUDT_lowerBound = m.createProperty(QUDT_URI, "lowerBound");
        QUDT_upperBound = m.createProperty(QUDT_URI, "upperBound");

        // =======================================================================
        // QUDT
        // =======================================================================
        CSVW_Column = m.createResource(CSVW_URI + "Column");
        CSVW_Row = m.createResource(CSVW_URI + "Row");
        CSVW_TableSchema = m.createResource(CSVW_URI + "CSVW_TableSchema");
        CSVW_Table = m.createResource(CSVW_URI + "Table");
        CSVW_column = m.createProperty(CSVW_URI, "column");
        CSVW_row = m.createProperty(CSVW_URI, "row");
        CSVW_rownum = m.createProperty(CSVW_URI, "rownum");
        CSVW_titles = m.createProperty(CSVW_URI, "titles");
        CSVW_name = m.createProperty(CSVW_URI, "name");

        // =======================================================================
        // RDFS
        // =======================================================================
        RDFS_label = m.createProperty(RDFS_URI, "label");
        RDFS_range = m.createProperty(RDFS_URI, "range");

        // =======================================================================
        // SCHEMA
        // =======================================================================
        SCHEMA_defaultvalue = m.createProperty(SCHEMA_URI, "defaultvalue");

        // =======================================================================
        // HYDRA
        // =======================================================================
        HYDRA_IriTemplateMapping = m.createResource(HYDRA_URI +  "IriTemplateMapping");
        HYDRA_Operation = m.createResource(HYDRA_URI +  "Operation");
        HYDRA_variable = m.createProperty(HYDRA_URI, "variable");
        HYDRA_ApiDocumentation = m.createProperty(HYDRA_URI, "ApiDocumentation");
        HYDRA_mapping = m.createProperty(HYDRA_URI, "mapping");
        HYDRA_required = m.createProperty(HYDRA_URI, "required");
        HYDRA_supportedOperation = m.createProperty(HYDRA_URI, "supportedOperation");
        HYDRA_entrypoint = m.createProperty(HYDRA_URI, "entrypoint");
        HYDRA_property = m.createProperty(HYDRA_URI, "property");
        HYDRA_method = m.createProperty(HYDRA_URI, "method");
        HYDRA_template = m.createProperty(HYDRA_URI, "template");
        HYDRA_title = m.createProperty(HYDRA_URI, "title");
        HYDRA_description = m.createProperty(HYDRA_URI, "description");

        // =======================================================================
        // GeoSparql
        // =======================================================================
        GSP_WktLiteral = m.createResource(GSP_URI + "wktLiteral");

        // =======================================================================
        // FairEase
        // =======================================================================
        FAIREASE_TemplateTrick = m.createResource(FAIREASE_URI + "TemplateTrick");
        FAIREASE_valueTemplate = m.createProperty(FAIREASE_URI, "valueTemplate");
        FAIREASE_valueType = m.createProperty(FAIREASE_URI, "valueType");
        FAIREASE_targetProperty = m.createProperty(FAIREASE_URI, "targetProperty");
        FAIREASE_generatedValue = m.createProperty(FAIREASE_URI, "generateValue");
    }

}