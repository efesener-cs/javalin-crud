package space.efesener;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class Root {
    DB db = new DB();
    public Root(JavalinConfig config){
        config.routes.before("/*",this::before);
        config.routes.get("/{id}",this::get);
        config.routes.post("/", this::post);
        config.routes.put("/{id}", this::put);
        config.routes.patch("/{id}", this::patch);
        config.routes.delete("/{id}", this::delete);
        config.routes.after("/*", this::after);
    }
   
    void before(Context ctx){
        System.out.println("new request");
    }
    void get(Context ctx){
        ctx.result(db.get(Integer.parseInt(ctx.pathParam("id"))));
    }
    void post(Context ctx){
        db.insert(ctx.body());
    }
    void put(Context ctx){
        db.update(Integer.parseInt(ctx.pathParam("id")), ctx.body());
    }
    void patch(Context ctx){
        db.update(Integer.parseInt(ctx.pathParam("id")), ctx.body());
    }
    void delete(Context ctx){
        db.delete(Integer.parseInt(ctx.pathParam("id")));
    }
    void after(Context ctx){
        System.out.println(ctx);
    }
}
