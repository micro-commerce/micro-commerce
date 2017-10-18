package org.micro.commerce.product.infrastructure.repository;

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
import java.util.UUID;

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

    public Product findById(UUID productId){
        Select findByIdQuery = QueryBuilder
                .select()
                .from(session.getLoggedKeyspace(), table)
                .where(QueryBuilder.eq("id", productId))
                .limit(1);

        ResultSet resultSet = execute(findByIdQuery);
        return productMapper
                .map(resultSet)
                .one();
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
