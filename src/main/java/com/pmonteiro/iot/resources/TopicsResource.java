package com.pmonteiro.iot.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.pmonteiro.iot.core.TopicMessage;
import com.pmonteiro.iot.db.TopicMessageDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

@Api("/")
@Path("topics")
@Produces(APPLICATION_JSON)
public class TopicsResource {

    private TopicMessageDAO dao;

    @Inject
    public TopicsResource(TopicMessageDAO taskDAO) {
        this.dao = taskDAO;
    }

    @GET
    @Timed
    @ApiOperation(
            value = "Get all streamed messages",
            notes = "Returns all the streamed messages saved in the database")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "", responseContainer = "List", response = TopicMessage.class)})
    public Response getTasks() {
        return ok().entity(ImmutableMap.of("messages", dao.find(TopicMessage.class))).build();
    }

//    @GET
//    @Path("/{topic}")
//    @Timed
//    @ApiOperation(
//            value = "Get streamed messages by topic",
//            notes = "Returns streamed messages by topic. If it does not exist it will return a HTTP 404")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "", response = TopicMessage.class),
//        @ApiResponse(code = 404, message = "Not Found")})
//    public Response getTask(@ApiParam(value = "topic", example = "9781337a-a4f6-4ee9-b7b2-2c001d8d457d") @PathParam("topic") String topic) {
//        return dao.findByTopic(topic)
//                .map(task -> ok().entity(task).build())
//                .orElse(status(NOT_FOUND.getStatusCode()).build());
//    }
}
