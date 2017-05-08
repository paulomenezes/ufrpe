package br.ufrpe.conectada.services;

import java.util.List;

import br.ufrpe.conectada.models.AVAUser;
import br.ufrpe.conectada.models.Calendar;
import br.ufrpe.conectada.models.Course;
import br.ufrpe.conectada.models.CourseAssignment;
import br.ufrpe.conectada.models.CourseContent;
import br.ufrpe.conectada.models.Forum;
import br.ufrpe.conectada.models.ForumPosts;
import br.ufrpe.conectada.models.Grades;
import br.ufrpe.conectada.models.Messages;
import br.ufrpe.conectada.models.SendMessage;
import br.ufrpe.conectada.models.SiteInfo;
import br.ufrpe.conectada.models.Token;
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

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<List<AVAUser>> getParticipants(
            @Field("courseid") int courseid,
            @Field("options[0][name]") String opname1,
            @Field("options[0][value]") int opvalue1,
            @Field("options[1][name]") String opname2,
            @Field("options[1][value]") int opvalue2,
            @Field("options[2][name]") String opname3,
            @Field("options[2][value]") String opvalue3,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);

    @FormUrlEncoded
    @POST("/webservice/rest/server.php?moodlewsrestformat=json")
    Call<Grades> getGrades(
            @Field("courseid") int courseid,
            @Field("userid") int userid,
            @Field("wsfunction") String wsfunction,
            @Field("wstoken") String wstoken);
}
