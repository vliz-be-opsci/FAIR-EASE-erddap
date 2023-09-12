package gov.noaa.pfel.erddap;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class RDFVocab {
    private static final Model m = ModelFactory.createDefaultModel();
    public static final String CSVW_URI     = "http://www.w3.org/ns/csvw#";
    public static final String DCAT_URI     = "http://www.w3.org/ns/dcat#";
    public static final String DCT_URI      = "http://purl.org/dc/terms/";
    public static final String FAIREASE_URI = "http://fairease.eu#";
    public static final String FOAF_URI     = "http://xmlns.com/foaf/0.1/";
    public static final String GSP_URI      = "http://www.opengis.net/ont/geosparql#";
    public static final String HYDRA_URI    = "http://www.w3.org/ns/hydra/core#";
    public static final String QUDT_URI     = "http://qudt.org/schema/qudt/";
    public static final String RDF_URI      = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final String RDFS_URI     = "http://www.w3.org/2000/01/rdf-schema#";
    public static final String SCHEMA_URI   = "http://schema.org/";
    public static final String SH_URI       = "http://www.w3.org/ns/shacl#";
    public static final String XSD_URI      = "http://www.w3.org/2001/XMLSchema#";
    public static final String IANA_URI     = "https://www.iana.org/assignments/media-types/";

    // =======================================================================
    // Prefixes :
    // =======================================================================
    public static final String[] prefixesName = {
            "csvw", "dcat", "dct",  "fairease", "foaf", "gsp", "hydra",
            "qudt", "rdf",  "rdfs", "schema",   "sh",   "xsd"
    };

    static String[] prefixesURI = {
            CSVW_URI, DCAT_URI, DCT_URI,  FAIREASE_URI, FOAF_URI, GSP_URI, HYDRA_URI,
            QUDT_URI, RDF_URI,  RDFS_URI, SCHEMA_URI,   SH_URI,   XSD_URI
    };

    // =======================================================================
    // CSVW
    // =======================================================================
    public static final Resource CSVW_Column;
    public static final Resource CSVW_Row;
    public static final Resource CSVW_Table;
    public static final Resource CSVW_TableSchema;

    public static final Property CSVW_column;
    public static final Property CSVW_name;
    public static final Property CSVW_row;
    public static final Property CSVW_rownum;
    public static final Property CSVW_titles;

    // =======================================================================
    // DCAT related
    // =======================================================================
    public static final Resource DCAT_Catalog;
    public static final Resource DCAT_Dataset;
    public static final Resource DCAT_Dataservice;
    public static final Resource DCAT_Distribution;

    public static final Property DCAT_bbox;
    public static final Property DCAT_contactPoint;
    public static final Property DCAT_dataset;
    public static final Property DCAT_distribution;
    public static final Property DCAT_downloadURL;
    public static final Property DCAT_endDate;
    public static final Property DCAT_endPointDescription;
    public static final Property DCAT_keyword;
    public static final Property DCAT_landingPage;
    public static final Property DCAT_mediaType;
    public static final Property DCAT_servesDataset;
    public static final Property DCAT_spatialResolutionInMeters;
    public static final Property DCAT_startDate;
    public static final Property DCAT_temporalResolution;

    // =======================================================================
    // DCT
    // =======================================================================
    public static final Resource DCT_PeriodOfTime;
    public static final Resource DCT_Location;

    public static final Property DCT_conformsTo;
    public static final Property DCT_creator;
    public static final Property DCT_description;
    public static final Property DCT_format;
    public static final Property DCT_identifier;
    public static final Property DCT_issued;
    public static final Property DCT_license;
    public static final Property DCT_modified;
    public static final Property DCT_publisher;
    public static final Property DCT_spatial;
    public static final Property DCT_temporal;
    public static final Property DCT_title;
    public static final Property DCT_type;


    // =======================================================================
    // FAIREASE
    // =======================================================================
    public static final Resource FAIREASE_Template;

    public static final Property FAIREASE_generatedValue;
    public static final Property FAIREASE_targetProperty;
    public static final Property FAIREASE_valueType;
    public static final Property FAIREASE_valueTemplate;

    // =======================================================================
    // Friend-Of-A-Friend
    // =======================================================================
    public static final Resource FOAF_Group;
    public static final Resource FOAF_Organization;
    public static final Resource FOAF_Person;

    public static final Property FOAF_name;
    public static final Property FOAF_mbox;
    public static final Property FOAF_member;
    public static final Property FOAF_workplaceHomepage;

    // =======================================================================
    // GeoSparql
    // =======================================================================
    public static final Resource GSP_WktLiteral;

    // =======================================================================
    // HYDRA
    // =======================================================================
    public static final Resource HYDRA_ApiDocumentation;
    public static final Resource HYDRA_Operation;
    public static final Resource HYDRA_IriTemplate;
    public static final Resource HYDRA_IriTemplateMapping;

    public static final Property HYDRA_description;
    public static final Property HYDRA_entrypoint;
    public static final Property HYDRA_mapping;
    public static final Property HYDRA_method;
    public static final Property HYDRA_property;
    public static final Property HYDRA_required;
    public static final Property HYDRA_supportedOperation;
    public static final Property HYDRA_template;
    public static final Property HYDRA_title;
    public static final Property HYDRA_variable;

    // =======================================================================
    // QUDT
    // =======================================================================
    public static final Property QUDT_defaultValue;
    public static final Property QUDT_lowerBound;
    public static final Property QUDT_upperBound;

    // =======================================================================
    // RDF
    // =======================================================================
    public static final Property RDF_type;

    // =======================================================================
    // RDFS
    // =======================================================================
    public static final Property RDFS_comment;
    public static final Property RDFS_label;
    public static final Property RDFS_range;

    // =======================================================================
    // SCHEMA
    // =======================================================================
    public static final Resource SCHEMA_CreativeWork;

    public static final Property SCHEMA_defaultValue;
    public static final Property SCHEMA_hasPart;

    // =======================================================================
    // Shacl
    // =======================================================================
    public static final Resource SH_NodeShape;

    public static final Property SH_dataType;
    public static final Property SH_maxCount;
    public static final Property SH_maxInclusive;
    public static final Property SH_minCount;
    public static final Property SH_minInclusive;
    public static final Property SH_path;
    public static final Property SH_property;

    // =======================================================================
    // XSD
    // =======================================================================
    public static final Resource XSD_AnyURI;
    public static final Resource XSD_Base64Binary;
    public static final Resource XSD_Boolean;
    public static final Resource XSD_Byte;
    public static final Resource XSD_Date;
    public static final Resource XSD_DateTime;
    public static final Resource XSD_Decimal;
    public static final Resource XSD_Double;
    public static final Resource XSD_Float;
    public static final Resource XSD_GDay;
    public static final Resource XSD_GMonth;
    public static final Resource XSD_GMonthDay;
    public static final Resource XSD_GYear;
    public static final Resource XSD_GYearMonth;
    public static final Resource XSD_HexBinary;
    public static final Resource XSD_Int;
    public static final Resource XSD_Integer;
    public static final Resource XSD_Language;
    public static final Resource XSD_Long;
    public static final Resource XSD_Name;
    public static final Resource XSD_NCName;
    public static final Resource XSD_NegativeInteger;
    public static final Resource XSD_NMTOKEN;
    public static final Resource XSD_NonNegativeInteger;
    public static final Resource XSD_NonPositiveInteger;
    public static final Resource XSD_NormalizedString;
    public static final Resource XSD_PositiveInteger;
    public static final Resource XSD_Short;
    public static final Resource XSD_String;
    public static final Resource XSD_Time;
    public static final Resource XSD_Token;
    public static final Resource XSD_UnsignedByte;
    public static final Resource XSD_UnsignedInt;
    public static final Resource XSD_UnsignedLong;
    public static final Resource XSD_UnsignedShort;

    // =======================================================================
    // IANA File Format
    // =======================================================================
    public static final Resource IANA_CSV;
    public static final Resource IANA_DODS;
    public static final Resource IANA_HTML;
    public static final Resource IANA_JSON;
    public static final Resource IANA_JSONLD;
    public static final Resource IANA_N3;
    public static final Resource IANA_NQ;
    public static final Resource IANA_NT;
    public static final Resource IANA_RDFXML;
    public static final Resource IANA_TRIG;
    public static final Resource IANA_TSV;
    public static final Resource IANA_TURTLE;
    public static final Resource IANA_XHTML;
    public static final Resource IANA_XML;


    public RDFVocab(){}

    static {
        // =======================================================================
        // CSVW
        // =======================================================================
        CSVW_Column =      m.createResource(CSVW_URI + "Column");
        CSVW_Row =         m.createResource(CSVW_URI + "Row");
        CSVW_TableSchema = m.createResource(CSVW_URI + "TableSchema");
        CSVW_Table =       m.createResource(CSVW_URI + "Table");

        CSVW_column = m.createProperty(CSVW_URI, "column");
        CSVW_name =   m.createProperty(CSVW_URI, "name");
        CSVW_row =    m.createProperty(CSVW_URI, "row");
        CSVW_rownum = m.createProperty(CSVW_URI, "rownum");
        CSVW_titles = m.createProperty(CSVW_URI, "titles");

        // =======================================================================
        // DCAT related
        // =======================================================================
        DCAT_Catalog =      m.createResource(DCAT_URI + "Catalog");
        DCAT_Dataset =      m.createResource(DCAT_URI + "Dataset");
        DCAT_Dataservice =  m.createResource(DCAT_URI + "Dataservice");
        DCAT_Distribution = m.createResource(DCAT_URI + "Distribution");

        DCAT_bbox =                      m.createProperty(DCAT_URI, "bbox");
        DCAT_contactPoint =              m.createProperty(DCAT_URI, "contactPoint");
        DCAT_dataset =                   m.createProperty(DCAT_URI, "dataset");
        DCAT_distribution =              m.createProperty(DCAT_URI, "distribution");
        DCAT_downloadURL =               m.createProperty(DCAT_URI, "downloadURL");
        DCAT_endDate =                   m.createProperty(DCAT_URI, "endDate");
        DCAT_endPointDescription =       m.createProperty(DCAT_URI, "endPointDescription");
        DCAT_keyword =                   m.createProperty(DCAT_URI, "keyword");
        DCAT_landingPage =               m.createProperty(DCAT_URI, "landingPage");
        DCAT_mediaType =                 m.createProperty(DCAT_URI, "mediaType");
        DCAT_servesDataset =             m.createProperty(DCAT_URI, "servesDataset");
        DCAT_spatialResolutionInMeters = m.createProperty(DCAT_URI, "spatialResolutionInMeters");
        DCAT_startDate =                 m.createProperty(DCAT_URI, "startDate");
        DCAT_temporalResolution =        m.createProperty(DCAT_URI, "temporalResolution");

        // =======================================================================
        // DCT relation
        // =======================================================================
        DCT_PeriodOfTime = m.createResource(DCT_URI + "PeriodOfTime");
        DCT_Location =     m.createResource(DCT_URI + "Location");

        DCT_conformsTo =  m.createProperty(DCT_URI, "conformsTo");
        DCT_creator =     m.createProperty(DCT_URI, "creator");
        DCT_description = m.createProperty(DCT_URI, "description");
        DCT_format =      m.createProperty(DCT_URI, "format");
        DCT_identifier =  m.createProperty(DCT_URI, "identifier");
        DCT_issued =      m.createProperty(DCT_URI, "issued");
        DCT_license =     m.createProperty(DCT_URI, "license");
        DCT_mediaType =   m.createProperty(DCT_URI, "mediatype");
        DCT_modified =    m.createProperty(DCT_URI, "modified");
        DCT_publisher =   m.createProperty(DCT_URI, "publisher");
        DCT_spatial =     m.createProperty(DCT_URI, "spatial");
        DCT_temporal =    m.createProperty(DCT_URI, "temporal");
        DCT_title =       m.createProperty(DCT_URI, "title");
        DCT_type =        m.createProperty(DCT_URI, "type");

        // =======================================================================
        // FairEase
        // =======================================================================
        FAIREASE_Template = m.createResource(FAIREASE_URI + "Template");

        FAIREASE_generatedValue = m.createProperty(FAIREASE_URI, "generateValue");
        FAIREASE_targetProperty = m.createProperty(FAIREASE_URI, "targetProperty");
        FAIREASE_valueTemplate =  m.createProperty(FAIREASE_URI, "valueTemplate");
        FAIREASE_valueType =      m.createProperty(FAIREASE_URI, "valueType");

        // =======================================================================
        // Friend-Of-A-Friend
        // =======================================================================
        FOAF_Group =             m.createResource(FOAF_URI + "Group");
        FOAF_Organization =      m.createResource(FOAF_URI + "Organization");
        FOAF_Person =            m.createResource(FOAF_URI + "Person");

        FOAF_name =              m.createProperty(FOAF_URI,"name");
        FOAF_mbox =              m.createProperty(FOAF_URI,"mbox");
        FOAF_member =            m.createProperty(FOAF_URI,"member");
        FOAF_workplaceHomepage = m.createProperty(FOAF_URI,"workplaceHomepage");

        // =======================================================================
        // GeoSparql
        // =======================================================================
        GSP_WktLiteral = m.createResource(GSP_URI + "wktLiteral");

        // =======================================================================
        // HYDRA
        // =======================================================================
        HYDRA_ApiDocumentation =   m.createResource(HYDRA_URI + "ApiDocumentation");
        HYDRA_Operation =          m.createResource(HYDRA_URI +  "Operation");
        HYDRA_IriTemplate =        m.createResource(HYDRA_URI +  "IriTemplate");
        HYDRA_IriTemplateMapping = m.createResource(HYDRA_URI +  "IriTemplateMapping");

        HYDRA_description =        m.createProperty(HYDRA_URI, "description");
        HYDRA_entrypoint =         m.createProperty(HYDRA_URI, "entrypoint");
        HYDRA_mapping =            m.createProperty(HYDRA_URI, "mapping");
        HYDRA_method =             m.createProperty(HYDRA_URI, "method");
        HYDRA_property =           m.createProperty(HYDRA_URI, "property");
        HYDRA_required =           m.createProperty(HYDRA_URI, "required");
        HYDRA_supportedOperation = m.createProperty(HYDRA_URI, "supportedOperation");
        HYDRA_template =           m.createProperty(HYDRA_URI, "template");
        HYDRA_title =              m.createProperty(HYDRA_URI, "title");
        HYDRA_variable =           m.createProperty(HYDRA_URI, "variable");

        // =======================================================================
        // QUDT
        // =======================================================================
        QUDT_defaultValue = m.createProperty(QUDT_URI, "defaultValue");
        QUDT_lowerBound   = m.createProperty(QUDT_URI, "lowerBound");
        QUDT_upperBound   = m.createProperty(QUDT_URI, "upperBound");

        // =======================================================================
        // RDF related
        // =======================================================================
        RDF_type = m.createProperty(RDF_URI,"type");

        // =======================================================================
        // RDFS
        // =======================================================================
        RDFS_comment = m.createProperty(RDFS_URI, "comment");
        RDFS_label =   m.createProperty(RDFS_URI, "label");
        RDFS_range =   m.createProperty(RDFS_URI, "range");

        // =======================================================================
        // SCHEMA
        // =======================================================================
        SCHEMA_CreativeWork = m.createResource(SCHEMA_URI + "CreativeWork");

        SCHEMA_defaultValue = m.createProperty(SCHEMA_URI, "defaultValue");
        SCHEMA_hasPart = m.createProperty(SCHEMA_URI, "hasPart");


        // =======================================================================
        // Shacl
        // =======================================================================
        SH_NodeShape =    m.createResource(SH_URI + "NodeShape");

        SH_dataType =     m.createProperty(SH_URI,"dataType");
        SH_maxCount =     m.createProperty(SH_URI,"maxCount");
        SH_maxInclusive = m.createProperty(SH_URI,"maxInclusive");
        SH_minCount =     m.createProperty(SH_URI,"minCount");
        SH_minInclusive = m.createProperty(SH_URI,"minInclusive");
        SH_path =         m.createProperty(SH_URI,"path");
        SH_property =     m.createProperty(SH_URI,"property");

        // =======================================================================
        // XSD:types
        // =======================================================================
        XSD_AnyURI =             m.createResource(XSD_URI + "anyURI");
        XSD_Base64Binary =       m.createResource(XSD_URI + "base64Binary");
        XSD_Boolean =            m.createResource(XSD_URI + "boolean");
        XSD_Byte =               m.createResource(XSD_URI + "byte");
        XSD_Date =               m.createResource(XSD_URI + "date");
        XSD_DateTime =           m.createResource(XSD_URI + "dateTime");
        XSD_Decimal =            m.createResource(XSD_URI + "decimal");
        XSD_Double =             m.createResource(XSD_URI + "double");
        XSD_Float =              m.createResource(XSD_URI + "float");
        XSD_GDay =               m.createResource(XSD_URI + "gDay");
        XSD_GMonth =             m.createResource(XSD_URI + "gMonth");
        XSD_GMonthDay =          m.createResource(XSD_URI + "gMonthDay");
        XSD_GYear =              m.createResource(XSD_URI + "gYear");
        XSD_GYearMonth =         m.createResource(XSD_URI + "gYearMonth");
        XSD_HexBinary =          m.createResource(XSD_URI + "hexBinary");
        XSD_Int =                m.createResource(XSD_URI + "int");
        XSD_Integer =            m.createResource(XSD_URI + "integer");
        XSD_Language =           m.createResource(XSD_URI + "language");
        XSD_Long =               m.createResource(XSD_URI + "long");
        XSD_Name =               m.createResource(XSD_URI + "Name");
        XSD_NCName =             m.createResource(XSD_URI + "NCName");
        XSD_NegativeInteger =    m.createResource(XSD_URI + "negativeInteger");
        XSD_NMTOKEN =            m.createResource(XSD_URI + "NMTOKEN");
        XSD_NonNegativeInteger = m.createResource(XSD_URI + "nonNegativeInteger");
        XSD_NonPositiveInteger = m.createResource(XSD_URI + "nonPositiveInteger");
        XSD_NormalizedString =   m.createResource(XSD_URI + "normalizedString");
        XSD_PositiveInteger =    m.createResource(XSD_URI + "positiveInteger");
        XSD_Short =              m.createResource(XSD_URI + "short");
        XSD_String =             m.createResource(XSD_URI + "string");
        XSD_Time =               m.createResource(XSD_URI + "time");
        XSD_Token =              m.createResource(XSD_URI + "token");
        XSD_UnsignedByte =       m.createResource(XSD_URI + "unsignedByte");
        XSD_UnsignedInt =        m.createResource(XSD_URI + "unsignedInt");
        XSD_UnsignedLong =       m.createResource(XSD_URI + "unsignedLong");
        XSD_UnsignedShort =      m.createResource(XSD_URI + "unsignedShort");

        // =======================================================================
        // IANA
        // =======================================================================
        IANA_CSV =    m.createResource(IANA_URI + "text/csv");
        IANA_DODS =   m.createResource(IANA_URI + "application/octet-stream");
        IANA_HTML =   m.createResource(IANA_URI + "text/html");
        IANA_JSON =   m.createResource(IANA_URI + "application/json");
        IANA_JSONLD = m.createResource(IANA_URI + "application/ld+json");
        IANA_N3 =     m.createResource(IANA_URI + "text/n3");
        IANA_NQ =     m.createResource(IANA_URI + "application/n-quads");
        IANA_NT =     m.createResource(IANA_URI + "application/n-triples");
        IANA_RDFXML = m.createResource(IANA_URI + "application/rdf+xml");
        IANA_TRIG =   m.createResource(IANA_URI + "application/trig");
        IANA_TSV =    m.createResource(IANA_URI + "text/tab-separated-values");
        IANA_TURTLE = m.createResource(IANA_URI + "text/turtle");
        IANA_XHTML =  m.createResource(IANA_URI + "application/xhtml+xml");
        IANA_XML =    m.createResource(IANA_URI + "application/xml");
    }

}