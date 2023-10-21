import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("https://jsonplaceholder.typicode.com")
public class QuarkusDemo {
    @GET
    @Path("/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response test() {
        return Response.ok().build();
    }

    public static void main(String[] args) {
        Response rs = test();
        System.out.println(rs.getStatus());
        System.out.println(rs.getStatusInfo());
    }

}
