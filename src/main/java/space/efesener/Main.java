package space.efesener;

import io.javalin.Javalin;

public class Main {

    void main() {
        Javalin.create(config -> {
            new Root(config);
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    String host = "hostname"; 
                    rule.allowHost(host);
                });
            });
        }).start();
    }
}
