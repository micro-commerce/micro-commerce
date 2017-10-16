# micro-commerce
Eventually Consistent Event Sourced With CQRS E-Commerce Application In MicroService Architecture And DDD

Run this command to setup the cluster

    sh docker/run-micro-commerce.sh

Wait for the command to fully set up cluster

API

* Get Products  

    
    curl -X GET http://localhost:8000/product

* Insert Product    


    curl -X POST http://localhost:8000/product \
      -H 'content-type: application/json' \
      -d '{ 
        "id": "f489b2c1-6312-4768-ab7d-8fead222c319", 
        "name": "product-name-1", 
        "description":"product-description-1", 
        "price": 3
    }'
    
* Insert Invalid Product



    curl -X POST \
      http://localhost:8000/product \
      -H 'content-type: application/json' \
      -d '{ 
    	"id": "40586077-14c6-4074-a21c-6a9017580a31", 
    	"name": "product-name-3", 
    	"description":"product-description-3", 
    	"price": -1
    }'