package org.micro.commerce.product.infrastructure.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.micro.commerce.product.domain.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CassandraConfiguration {

    private static final String CREATE_PRODUCT_QUERY_KEYSPACE = "CREATE KEYSPACE IF NOT EXISTS %s WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";
    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE IF NOT EXISTS %s (\n" +
            "   id uuid,\n" +
            "   name varchar,\n" +
            "   description varchar,\n" +
            "   price decimal,\n" +
            "   PRIMARY KEY (id)\n" +
            ");";

    @Value("${cassandra.contact-point}")
    private String contactPoint;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.table}")
    private String table;

    @PostConstruct
    public void init(){
        Session session = Cluster
                .builder()
                .addContactPoint(contactPoint)
                .build()
                .connect();
        session.execute(String.format(CREATE_PRODUCT_QUERY_KEYSPACE, keyspace));
        session.execute(String.format(CREATE_PRODUCT_TABLE, keyspace + "." + table));
        session.close();
    }

    @Bean
    public Session cassandraSession(){
        return Cluster
                .builder()
                .addContactPoint(contactPoint)
                .build()
                .connect(keyspace);
    }

    @Bean
    public MappingManager cassandraMappingManager(Session session){
        return new MappingManager(session);
    }

    @Bean
    public Mapper<Product> productMapper(MappingManager mappingManager){
        return mappingManager.mapper(Product.class);
    }

}
