script_full_path=$(dirname "$0")

docker-compose -f $script_full_path/docker-compose.yaml -p micro-commerce up -d

healthy_condition="healthy"
kong_status=false

while [ $kong_status == false ]
do
    status=$(docker ps --filter name="microcommerce_kong" --format "{{.Status}}")
    if [[ $status =~ $healthy_condition ]]
    then
        kong_status=true
    fi
done

sh $script_full_path/configure_api_gateway.sh