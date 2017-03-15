package zmoriga.com.acticle.bean;

import zmoriga.com.acticle.db.CommentHelper;

/**
 *
 */
public class Comment {

    /**
     * cid : 58643816
     * quoteId : 58643391
     * content : 自含根！![emot=ac,16/]
     * postDate : 2016-05-05 21:40:26
     * userID : 2289003
     * userName : 千锤百炼终成渣
     * userImg : http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201510/21151915awicrosa.jpg
     * count : 19
     * deep : 14
     * refCount : 1
     * ups : 0
     * downs : 0
     * nameRed : 0
     * avatarFrame : 0
     */

    private int cid;
    private int quoteId;
    private String content;
    private String postDate;
    private int userID;
    private String userName;
    private String userImg;
    private int count;
    private int deep;
    private int refCount;
    private int ups;
    private int downs;
    private int nameRed;
    private int avatarFrame;

    private CommentHelper helper;
    public CommentHelper getHelper() {
        return helper;
    }
    public void setHelper(CommentHelper commentHelper) {
        this.helper = commentHelper;
    }
    private int repeat;
    public int getRepeat() {
        return repeat;
    }
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public int getRefCount() {
        return refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getNameRed() {
        return nameRed;
    }

    public void setNameRed(int nameRed) {
        this.nameRed = nameRed;
    }

    public int getAvatarFrame() {
        return avatarFrame;
    }

    public void setAvatarFrame(int avatarFrame) {
        this.avatarFrame = avatarFrame;
    }
}
