package com.yx.solr;

import com.yx.bean.Animal;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: NMY
 * Date: 16-8-30
 */
public class Server642Test {


    private SolrServer server;
    //gettingstarted_shard1_replica2是core的名称
//    private static final String DEFAULT_URL = "http://192.168.2.113:8983/solr/gettingstarted_shard1_replica2";
    private static final String DEFAULT_URL = "http://192.168.2.113:8983/solr/techproducts";


    @Before
    public void init() {
        server = new HttpSolrServer(DEFAULT_URL);
    }

    /**
     * 往索引库添加文档
     *
     * @throws org.apache.solr.client.solrj.SolrServerException
     *
     * @throws java.io.IOException
     */
    @Test
    public void addDoc() throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
        document.addField("id", "number001");
        document.addField("name", "中国牛逼啊");

        /*document.setField("id", "number001");
        document.setField("name", "中国牛逼啊");如果存在就先删除再添加（所谓的修改），不存在直接添加，*/
        server.add(document);
        server.optimize();//不要频繁的调用..尽量在无人使用时调用.
        server.commit();
    }

    /**
     * 根据id删除文档
     */
    @Test
    public void deleteDocumentById() throws Exception {
        server.deleteById("num001");
        server.commit();
    }

    //根据查询删除文档
    @Test
    public void deleteDocumentByQuery() throws Exception {
        server.deleteByQuery("name:jack");
        server.commit();
    }

    /**
     * 对索引库中的文档进行更新
     *
     * @throws org.apache.solr.client.solrj.SolrServerException
     *
     */
    @Test
    public void query() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery("GB18030");
        //给query设置一个主查询条件
        //query.set("q", "*:*");//查询所有

//        query.set("q", "solr");

        //给query增加过滤查询条件
//        query.addFilterQuery("product_price:[0 TO 200]");

        //给query增加布尔过滤条件
        //query.addFilterQuery("-product_name:台灯");

        //给query设置默认搜索域
//        query.set("df", "product_keywords");

        //设置返回结果的排序规则
//        query.setSort("product_price", ORDER.desc);

        //设置分页参数
        query.setStart(0);
        query.setRows(20);

        //设置高亮
        query.setHighlight(true);
        //设置高亮的字段
        query.addHighlightField("name");
        //设置高亮的样式
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        QueryResponse response = server.query(query);
        SolrDocumentList solrDocumentList = response.getResults();

        System.out.println("查询结果的总数量：" + solrDocumentList.getNumFound());

        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("name"));
        }
    }

    @Test
    public void queryBeans() {
        try {
            SolrQuery params = new SolrQuery("name_*");//通配符
            params.addSort("name", SolrQuery.ORDER.desc);
            QueryResponse response = server.query(params);
            List<Animal> animals = response.getBeans(Animal.class);
            for (Animal animal : animals) {
                System.out.println(animal);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBean() {
        try {
            Animal bean = new Animal("333", "中国");
            server.addBean(bean);
            UpdateResponse response = server.commit();
            System.out.println(response.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBeans() {
        try {
            List<Animal> beanList = new ArrayList<Animal>();
            for (int i = 0; i < 10; i++) {
                beanList.add(new Animal(String.valueOf(i), "name_" + i));
            }
            server.addBeans(beanList);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}