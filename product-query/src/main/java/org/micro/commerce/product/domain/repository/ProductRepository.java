package org.micro.commerce.product.domain.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.Mapper;
import org.micro.commerce.product.domain.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Value("${cassandra.table}")
    private String table;

    private Session session;

    private Mapper<Product> productMapper;

    public ProductRepository(Session session, Mapper<Product> productMapper) {
        this.session = session;
        this.productMapper = productMapper;
    }

    public List<Product> findAll(){
        Select findAllQuery = QueryBuilder
                .select()
                .all()
                .from(session.getLoggedKeyspace(), table);
        ResultSet resultSet = execute(findAllQuery);
        return productMapper
                .map(resultSet)
                .all();
    }

    public void save(Product product) {
        Insert saveQuery = QueryBuilder
                .insertInto(session.getLoggedKeyspace(), table)
                .value("id", product.getId())
                .value("name", product.getName())
                .value("description", product.getDescription())
                .value("price", product.getPrice())
                .ifNotExists();
        execute(saveQuery);
    }

    private ResultSet execute(Statement statement){
        return session.execute(statement);
    }

}
