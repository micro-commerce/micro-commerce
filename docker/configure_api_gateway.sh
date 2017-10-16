curl -X POST \
  http://localhost:8001/apis/ \
  -F name=product-query \
  -F upstream_url=http://product-query:8080 \
  -F uris=/product \
  -F methods=GET \
  -F strip_uri=false

curl -X POST \
  http://localhost:8001/apis/ \
  -F name=product-command \
  -F upstream_url=http://product-command:8080 \
  -F uris=/product \
  -F 'methods=POST,PUT' \
  -F strip_uri=false