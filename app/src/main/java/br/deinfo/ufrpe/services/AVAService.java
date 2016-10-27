package br.deinfo.ufrpe.services;

import org.json.JSONObject;

import java.util.List;

import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseAssignment;
import br.deinfo.ufrpe.models.CourseContent;
import br.deinfo.ufrpe.models.Forum;
import br.deinfo.ufrpe.models.SiteInfo;
import br.deinfo.ufrpe.models.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by paulo on 02/10/2016.
 */

public interface AVAService {
    @FormUrlEncoded
    @POST("/login/token.php")
    Call<Token> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("service") String service);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<SiteInfo> getSiteInfo(
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<List<Course>> getUsersCourses(
            @Field("userid") int userid,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<CourseAssignment> getAssigments(
            @Field("courseids[]") int[] courseids,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<List<CourseContent>> getCourseContent(
            @Field("courseid") int courseid,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<Forum> getForum(
            @Field("forumid") int forumid,
            @Field("sortby") String sortby,
            @Field("page") int page,
            @Field("perpage") int perpage,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);
}
