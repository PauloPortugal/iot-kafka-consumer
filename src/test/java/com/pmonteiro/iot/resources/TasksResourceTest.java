//package com.pmonteiro.iot.resources;
//
//import com.pmonteiro.iot.IOTKafkaConsumerApplication;
//import com.pmonteiro.iot.IOTKafkaConsumerConfiguration;
//import io.dropwizard.testing.junit.DropwizardAppRule;
//import io.restassured.response.Response;
//import org.junit.ClassRule;
//import org.junit.Test;
//
//import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
//import static io.restassured.RestAssured.given;
//import static io.restassured.RestAssured.when;
//import static io.restassured.http.ContentType.JSON;
//import static javax.ws.rs.core.Response.Status.*;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.hamcrest.core.Is.is;
//
//public class TasksResourceTest {
//
//    @ClassRule
//    public static DropwizardAppRule<IOTKafkaConsumerConfiguration>
//            rule = new DropwizardAppRule<>(IOTKafkaConsumerApplication.class,  resourceFilePath("test-config.yml"));
//
//    @Test
//    public void getTasks_whenTasksExist_ShouldReturnTasks() throws Exception {
//        Task task = insertTask(new TaskApi("some-description"));
//        when()
//                .get("/tasks")
//        .then()
//                .statusCode(OK.getStatusCode())
//                    .body("tasks[0].description", is(task.getDescription()))
//                    .body("tasks[0].id", is(notNullValue()));
//    }
//
//    @Test
//    public void getTask_whenTaskDoesNotExist_ShouldReturn404() throws Exception {
//        when()
//                .get("/task/1")
//        .then()
//                .statusCode(NOT_FOUND.getStatusCode());
//    }
//
//    @Test
//    public void getTask_whenIdExists_ShouldReturnTask() throws Exception {
//        Task task = insertTask(new TaskApi("description-2"));
//        when()
//                .get("/tasks/" + task.getId())
//        .then()
//                .statusCode(OK.getStatusCode())
//                .body("description", is(task.getDescription()))
//                .body("id", is(notNullValue()));
//    }
//
//    @Test
//    public void create() throws Exception {
//        TaskApi task = new TaskApi("description");
//        given()
//                .accept(JSON)
//                .contentType(JSON)
//                .body(task)
//        .when()
//                .post("/tasks")
//        .then()
//                .statusCode(CREATED.getStatusCode())
//                .body("description", is(task.getDescription()))
//                .body("id", is(notNullValue()));
//    }
//
//    @Test
//    public void update_whenTaskDoesNotExist_ShouldReturn404() throws Exception {
//        Task task = new Task("description");
//        given()
//                .accept(JSON)
//                .contentType(JSON)
//                .body(task)
//        .when()
//                .put("/tasks/1")
//        .then()
//                .statusCode(NOT_FOUND.getStatusCode());
//    }
//
//    @Test
//    public void update_whenTaskExists_ShouldUpdateTask() throws Exception {
//        Task task = insertTask(new TaskApi("description"));
//        TaskApi taskApi = new TaskApi(task.getDescription());
//        given()
//                .accept(JSON)
//                .contentType(JSON)
//                .body(taskApi)
//        .when()
//                .put("/tasks/" + task.getId())
//        .then()
//                .statusCode(OK.getStatusCode());
//    }
//
//    @Test
//    public void delete_whenTaskExists_ShouldDeleteAndReturn204() throws Exception {
//        Task task = insertTask(new TaskApi("description"));
//        given()
//                .accept(JSON)
//                .contentType(JSON)
//        .when()
//                .delete("/tasks/" + task.getId())
//        .then()
//                .statusCode(NO_CONTENT.getStatusCode());
//    }
//
//    @Test
//    public void delete_whenTaskDoesNotExist_ShouldReturn404() throws Exception {
//        given()
//                .accept(JSON)
//                .contentType(JSON)
//        .when()
//                .delete("/tasks/1")
//        .then()
//                .statusCode(NOT_FOUND.getStatusCode());
//    }
//
//    private Task insertTask(TaskApi task) {
//        Response response =
//            given()
//                    .accept(JSON)
//                    .contentType(JSON)
//                    .body(task)
//            .when()
//                    .post("/tasks");
//
//        return response.as(Task.class);
//    }
//}