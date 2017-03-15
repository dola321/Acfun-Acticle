package zmoriga.com.acticle.bean;

import java.util.List;

/**
 *
 */

public class Title {
    private int channelId;
    private int comments;
    private String contentId;
    private String cover;
    private String description;
    private int isArticle;
    private int isRecommend;
    private long releaseDate;
    private int stows;
    private String title;
    private int toplevel;
    /**
     * userImg :
     * userId : 579011
     * username : 会计
     */

    private User user;
    private int viewOnly;
    private int views;
    private List<String> tags;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsArticle() {
        return isArticle;
    }

    public void setIsArticle(int isArticle) {
        this.isArticle = isArticle;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getStows() {
        return stows;
    }

    public void setStows(int stows) {
        this.stows = stows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getToplevel() {
        return toplevel;
    }

    public void setToplevel(int toplevel) {
        this.toplevel = toplevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getViewOnly() {
        return viewOnly;
    }

    public void setViewOnly(int viewOnly) {
        this.viewOnly = viewOnly;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public static class User {
        private String userImg;
        private int userId;
        private String username;

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
