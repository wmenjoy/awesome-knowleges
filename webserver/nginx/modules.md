# ngx_http_upstream_hc_module
The ngx_http_upstream_hc_module module allows enabling periodic health checks of the servers in a group referenced in the surrounding location. The server group must reside in the shared memory.

If a health check fails, the server will be considered unhealthy. If several health checks are defined for the same group of servers, a single failure of any check will make the corresponding server be considered unhealthy. Client requests are not passed to unhealthy servers and servers in the “checking” state.

## Directives
* health_check Enables periodic health checks of the servers in a group referenced in the surrounding location.
* match Defines the named test set used to verify responses to health check requests.

``` nginx
http {
    server {
    ...
        location / {
            proxy_pass http://backend;
            health_check match=welcome;
        }
    }

    match welcome {
        status 200;
        header Content-Type = text/html;
        body ~ "Welcome to nginx!";
    }
}
```

