package zmoriga.com.acticle.bean;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Comments {

    /**
     * success : true
     * msg : 操作成功
     * status : 200
     * data : {"commentList":[],"totalPage":1,"pageSize":50,"page":1,"totalCount":30,"commentContentArr":{},"desc":true}
     */

    private boolean success;
    private String msg;
    private int status;
    /**
     * commentList : []
     * totalPage : 1
     * pageSize : 50
     * page : 1
     * totalCount : 30
     * commentContentArr : {}
     * desc : true
     */

    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private int totalPage;
        private int pageSize;
        private int page;
        private int totalCount;
        private JsonObject commentContentArr;
        private boolean desc;
        private ArrayList<Integer> commentList;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public JsonObject getCommentContentArr() {
            return commentContentArr;
        }

        public void setCommentContentArr(JsonObject commentContentArr) {
            this.commentContentArr = commentContentArr;
        }

        public boolean isDesc() {
            return desc;
        }

        public void setDesc(boolean desc) {
            this.desc = desc;
        }

        public List<Integer> getCommentList() {
            return commentList;
        }

        public void setCommentList(ArrayList<Integer> commentList) {
            this.commentList = commentList;
        }


    }
}
