package br.deinfo.ufrpe.services;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import br.deinfo.ufrpe.models.AVAUser;
import br.deinfo.ufrpe.models.Calendar;
import br.deinfo.ufrpe.models.Course;
import br.deinfo.ufrpe.models.CourseAssignment;
import br.deinfo.ufrpe.models.CourseContent;
import br.deinfo.ufrpe.models.Forum;
import br.deinfo.ufrpe.models.ForumPosts;
import br.deinfo.ufrpe.models.Messages;
import br.deinfo.ufrpe.models.SendMessage;
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
    Call<List<AVAUser>> getUserById(
            @Field("userids[]") int[] userids,
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
    Call<Forum> getDiscussion(
            @Field("forumid") int forumid,
            @Field("sortby") String sortby,
            @Field("page") int page,
            @Field("perpage") int perpage,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<ForumPosts> getDiscussionPosts(
            @Field("discussionid") int discussionid,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<Calendar> getCalendarEvents(
            @Field("options[userevents]") int userevents,
            @Field("options[siteevents]") int siteevents,
            @Field("options[timestart]") long timestart,
            @Field("options[timeend]") long timeend,
            @Field("events[courseids][]") int[] events,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<Messages> getMessages(
            @Field("useridto") int useridto,
            @Field("useridfrom") int useridfrom,
            @Field("limitfrom") int limitfrom,
            @Field("limitnum") int limitnum,
            @Field("read") int read,
            @Field("type") String type,
            @Field("newestfirst") int newestfirst,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<List<SendMessage>> sendMessage(
            @Field("messages[0][touserid]") int touserid,
            @Field("messages[0][text]") String text,
            @Field("messages[0][textformat]") int textformat,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);
}
