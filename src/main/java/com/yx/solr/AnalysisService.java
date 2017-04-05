package com.yx.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.FieldAnalysisRequest;
import org.apache.solr.client.solrj.response.AnalysisResponseBase;
import org.apache.solr.client.solrj.response.FieldAnalysisResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分词api使用
 */
public class AnalysisService {

    private static HttpSolrServer server;
    private static final String DEFAULT_URL = "http://123.56.26.24:8939/solr";

    static {
        server = new HttpSolrServer(DEFAULT_URL);
    }

    /**
     * 给指定的语句分词，取结果下标从2开始，注意判断list的size是否大于4
     */
    public static List<String> getAnalysis(String keyword) throws IOException, SolrServerException {
        FieldAnalysisRequest request = new FieldAnalysisRequest("/analysis/field");
        request.addFieldName("BULLETINNAME"); //字段名，随便指定一个支持中文分词的字段
        request.setFieldValue("");            //字段值，可以为空字符串，但是需要显式指定此参数
        request.setQuery("BULLETINNAME:" + keyword);

        FieldAnalysisResponse response = request.process(server);
        List<String> results = new ArrayList<String>();
        Iterator<AnalysisResponseBase.AnalysisPhase> it = response.getFieldNameAnalysis("BULLETINNAME").getQueryPhases().iterator();
        while (it.hasNext()) {
            AnalysisResponseBase.AnalysisPhase phase = it.next();
            List<AnalysisResponseBase.TokenInfo> list = phase.getTokens();
            for (AnalysisResponseBase.TokenInfo info : list) {
                if (!"sentence".equals(info.getType())) {
                    results.add(info.getText());
                }
            }
        }
        return results.subList(2, results.size());
    }

    public static void main(String[] args) throws IOException, SolrServerException {
        List<String> list = getAnalysis("");
        System.out.println(list);
    }
}