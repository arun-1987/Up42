package com.up42.apireusable;

public class TestData {

    public static String getWorkFlowTaskData(){
        return "[\n" +
                "{\n" +
                "\"name\": \"nasa-modis:1\",\n" +
                "\"parentName\": null,\n" +
                "\"blockId\": \"ef6faaf5-8182-4986-bce4-4f811d2745e5\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"sharpening:1\",\n" +
                "\"parentName\": \"nasa-modis:1\",\n" +
                "\"blockId\": \"e374ea64-dc3b-4500-bb4b-974260fb203e\"\n" +
                "}\n" +
                "]";
    }


    public static String getWorkFlowData(){
        return "{\n" +
                "\"name\": \"QA coding challenge workflow\",\n" +
                "\"description\": \"Workflow description\"\n" +
                "}";
    }

    public static String getWorkFlowDataWithEmptyName(){
        return "{\n" +
                "\"name\": \"\",\n" +
                "\"description\": \"Workflow description\"\n" +
                "}";
    }

    public static String getWorkFlowDataWithExpiredToken(){
        return "{\n" +
                "\"name\": \"Expired\",\n" +
                "\"description\": \"Workflow description\"\n" +
                "}";
    }

    public static String getWorkFlowDataWithIncorrectContentType(){
        return "{\n" +
                "\"name\": \"IncorrectContent\",\n" +
                "\"description\": \"Workflow description\"\n" +
                "}";
    }


    public static String getWorkFlowConfigData(){
        return"{\n" +
                "\"nasa-modis:1\": {\n" +
                "\"time\": \"2018-12-01T00:00:00+00:00/2020-12-31T23:59:59+00:00\",\n" +
                "\"limit\": 1,\n" +
                "\"zoom_level\": 9,\n" +
                "\"imagery_layers\": [\n" +
                "\"MODIS_Terra_CorrectedReflectance_TrueColor\"\n" +
                "],\n" +
                "\"bbox\": [\n" +
                "13.365373,\n" +
                "52.49582,\n" +
                "13.385796,\n" +
                "52.510455\n" +
                "]\n" +
                "},\n" +
                "\"sharpening:1\": {\n" +
                "\"strength\": \"medium\"\n" +
                "}\n" +
                "}";
    }
}
