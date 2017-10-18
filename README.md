# micro-commerce
Eventually Consistent Event Sourced With CQRS E-Commerce Application In MicroService Architecture And DDD

Run this command to setup the cluster

    sh docker/run-micro-commerce.sh

Wait for the command to fully set up cluster

API

* Get Products  

    
        curl -X GET http://localhost:8080/products

* Insert Product    


        curl -X POST \
          http://localhost:8080/products \
          -H 'content-type: application/json' \
          -d '{ 
            "name": "product-name-1", 
            "description":"product-description-1", 
            "price": 3
        }'
    
* Insert Invalid Product



        curl -X POST \
          http://localhost:8080/products \
          -H 'content-type: application/json' \
          -d '{ 
            "name": "product-name-3", 
            "description":"product-description-3", 
            "price": -1
        }'