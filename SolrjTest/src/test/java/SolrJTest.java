import com.cainiao.pojo.Products;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SolrJTest {
//    1.定义SolrService对象连接solr服务器
    private SolrServer solrServer=new HttpSolrServer("http://localhost:8090/solr/collection1");
//    2.添加或修改索引
    @Test
    public void addOrUpdateIndext() throws IOException, SolrServerException {
        Products products=new Products();
        products.setPid("8000");
        products.setName("iphone8");
        products.setCatalogName("手机挺好的");
        products.setPrice(80000d);
        products.setDescription("垃圾苹果手机");
        products.setPicture("1.jpg");

//        添加或修改索引库中的数据（如果该数据已存在就是修改，）
        solrServer.addBean(products);

//         提交事务
        solrServer.commit();

    }

//    3.根据Id删除索引
    @Test
    public void deleteById() throws IOException, SolrServerException {
        solrServer.deleteById("8000");
//        提交事务
        solrServer.commit();
    }
//    4.根据条件删除索引
    @Test
    public void deleteByQuery() throws IOException, SolrServerException {
//        删除条件：name:手机
//        solrServer.deleteByQuery("name:手机");
//        删除全部
        solrServer.deleteByQuery("*:*");

//        提交事务
        solrServer.commit();
    }

//    5.查询索引
    @Test
    public void find() throws SolrServerException {
//        创建封装条件
        SolrQuery query=new SolrQuery("pid:8000");
//        设置开始分页的数
        query.setStart(0);
//        设置每页的记录数
        query.setRows(5);
//        执行搜索
        QueryResponse queryResponse = solrServer.query(query);
        System.out.println("总记录数："+queryResponse.getResults().getNumFound());
        System.out.println("开始分页的数的："+queryResponse.getResults().getStart());


//        获取查询的结果，封装在实体类集合中
        List<Products> productsList = queryResponse.getBeans(Products.class);
//        遍历打印
        for (Products products : productsList) {
            System.out.println("=======分割线=========");
            System.out.println(products.getPid()+"\t"+products.getName()+"\t"+products.getCatalogName());
        }

    }
}
