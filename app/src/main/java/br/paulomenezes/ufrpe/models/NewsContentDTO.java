package br.paulomenezes.ufrpe.models;

@org.parceler.Parcel
public class NewsContentDTO
{
    private String body;

    private String authorImageUrl;

    private String state;

    private String commentsCount;

    private String recommended;

    private String stationId;

    private String snippet;

    private String date;

    private String authorId;

    private String publisher;

    private String bookmarked;

    private String id;

    private String title;

    private String largeImageUrl;

    private String mediumImageUrl;

    private String stationName;

    private String recommendsCount;

    private String externalVideoUrl;

    private String[] tags;

    private String bookmarkCount;

    private String readTime;

    private String authorEmail;

    private String smallImageUrl;

    private String videoHash;

    private String sponsor;

    private String postId;

    private String esid;

    private String authorUsername;

    private String authorName;

    private String allowComments;

    private String audioHash;

    private NewsCategoriesDTO[] categories;

    private String slug;

    private String imageCredits;

    private String sponsored;

    public String getBody ()
    {
        return body;
    }

    public void setBody (String body)
    {
        this.body = body;
    }

    public String getAuthorImageUrl ()
    {
        return authorImageUrl;
    }

    public void setAuthorImageUrl (String authorImageUrl)
    {
        this.authorImageUrl = authorImageUrl;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getCommentsCount ()
    {
        return commentsCount;
    }

    public void setCommentsCount (String commentsCount)
    {
        this.commentsCount = commentsCount;
    }

    public String getRecommended ()
    {
        return recommended;
    }

    public void setRecommended (String recommended)
    {
        this.recommended = recommended;
    }

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    public String getSnippet ()
    {
        return snippet;
    }

    public void setSnippet (String snippet)
    {
        this.snippet = snippet;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getAuthorId ()
    {
        return authorId;
    }

    public void setAuthorId (String authorId)
    {
        this.authorId = authorId;
    }

    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }

    public String getBookmarked ()
    {
        return bookmarked;
    }

    public void setBookmarked (String bookmarked)
    {
        this.bookmarked = bookmarked;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getLargeImageUrl ()
    {
        return largeImageUrl;
    }

    public void setLargeImageUrl (String largeImageUrl)
    {
        this.largeImageUrl = largeImageUrl;
    }

    public String getMediumImageUrl ()
    {
        return mediumImageUrl;
    }

    public void setMediumImageUrl (String mediumImageUrl)
    {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getStationName ()
    {
        return stationName;
    }

    public void setStationName (String stationName)
    {
        this.stationName = stationName;
    }

    public String getRecommendsCount ()
    {
        return recommendsCount;
    }

    public void setRecommendsCount (String recommendsCount)
    {
        this.recommendsCount = recommendsCount;
    }

    public String getExternalVideoUrl () {
        return externalVideoUrl;
    }

    public void setExternalVideoUrl (String externalVideoUrl)
    {
        this.externalVideoUrl = externalVideoUrl;
    }

    public String[] getTags ()
    {
        return tags;
    }

    public void setTags (String[] tags)
    {
        this.tags = tags;
    }

    public String getBookmarkCount ()
    {
        return bookmarkCount;
    }

    public void setBookmarkCount (String bookmarkCount)
    {
        this.bookmarkCount = bookmarkCount;
    }

    public String getReadTime ()
    {
        return readTime;
    }

    public void setReadTime (String readTime)
    {
        this.readTime = readTime;
    }

    public String getAuthorEmail ()
    {
        return authorEmail;
    }

    public void setAuthorEmail (String authorEmail)
    {
        this.authorEmail = authorEmail;
    }

    public String getSmallImageUrl ()
    {
        return smallImageUrl;
    }

    public void setSmallImageUrl (String smallImageUrl)
    {
        this.smallImageUrl = smallImageUrl;
    }

    public String getVideoHash ()  {
        return videoHash;
    }

    public void setVideoHash (String videoHash)
    {
        this.videoHash = videoHash;
    }

    public String getSponsor ()
    {
        return sponsor;
    }

    public void setSponsor (String sponsor)
    {
        this.sponsor = sponsor;
    }

    public String getPostId ()
    {
        return postId;
    }

    public void setPostId (String postId)
    {
        this.postId = postId;
    }

    public String getEsid ()
    {
        return esid;
    }

    public void setEsid (String esid)
    {
        this.esid = esid;
    }

    public String getAuthorUsername ()
    {
        return authorUsername;
    }

    public void setAuthorUsername (String authorUsername)
    {
        this.authorUsername = authorUsername;
    }

    public String getAuthorName ()
    {
        return authorName;
    }

    public void setAuthorName (String authorName)
    {
        this.authorName = authorName;
    }

    public String getAllowComments ()
    {
        return allowComments;
    }

    public void setAllowComments (String allowComments)
    {
        this.allowComments = allowComments;
    }

    public String getAudioHash ()
{
    return audioHash;
}

    public void setAudioHash (String audioHash)
    {
        this.audioHash = audioHash;
    }

    public NewsCategoriesDTO[] getCategories ()
    {
        return categories;
    }

    public void setCategories (NewsCategoriesDTO[] categories)
    {
        this.categories = categories;
    }

    public String getSlug ()
    {
        return slug;
    }

    public void setSlug (String slug)
    {
        this.slug = slug;
    }

    public String getImageCredits ()
    {
        return imageCredits;
    }

    public void setImageCredits (String imageCredits)
    {
        this.imageCredits = imageCredits;
    }

    public String getSponsored ()
    {
        return sponsored;
    }

    public void setSponsored (String sponsored)
    {
        this.sponsored = sponsored;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [body = "+body+", authorImageUrl = "+authorImageUrl+", state = "+state+", commentsCount = "+commentsCount+", recommended = "+recommended+", stationId = "+stationId+", snippet = "+snippet+", date = "+date+", authorId = "+authorId+", publisher = "+publisher+", bookmarked = "+bookmarked+", id = "+id+", title = "+title+", largeImageUrl = "+largeImageUrl+", mediumImageUrl = "+mediumImageUrl+", stationName = "+stationName+", recommendsCount = "+recommendsCount+", externalVideoUrl = "+externalVideoUrl+", tags = "+tags+", bookmarkCount = "+bookmarkCount+", readTime = "+readTime+", authorEmail = "+authorEmail+", smallImageUrl = "+smallImageUrl+", videoHash = "+videoHash+", sponsor = "+sponsor+", postId = "+postId+", esid = "+esid+", authorUsername = "+authorUsername+", authorName = "+authorName+", allowComments = "+allowComments+", audioHash = "+audioHash+", categories = "+categories+", slug = "+slug+", imageCredits = "+imageCredits+", sponsored = "+sponsored+"]";
    }
}
