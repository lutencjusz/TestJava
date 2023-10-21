package pageobjects;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import model.Comment;
import model.Post;
import java.util.List;

public class ApiPageObjects {

    public List<Comment> getApiResponseComments(String extend) {
        return RestAssured
                .get("https://jsonplaceholder.typicode.com" + extend)
                .as(new TypeRef<>() {
                });
    }

    public List<Post> getApiResponsePosts(String extend) {
        return RestAssured
                .get("https://jsonplaceholder.typicode.com" + extend)
                .as(new TypeRef<>() {
                });
    }

    public void getIdComments(int rowId) {
        List<Comment> comment = getApiResponseComments("/comments?id="+rowId);
        comment.forEach(row -> System.out.println("id: " + row.getId()));
        System.out.println("Get name: " + comment.get(1).getName());
    }

    public void getIdComments() {
        List<Comment> comment = getApiResponseComments("/comments");
        comment.forEach(row -> System.out.println("id: " + row.getId()));
        System.out.println("Get name: " + comment.get(1).getName());
    }


    public void getIdPosts() {
        List<Post> posts = getApiResponsePosts("/posts");
        posts.forEach(row -> System.out.println("Posts id: " + row.getId()));
    }

}
