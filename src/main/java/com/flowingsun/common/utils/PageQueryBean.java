package com.flowingsun.common.utils;
import java.util.List;

public class PageQueryBean {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private Integer total;     //总记录数
    public Integer pageNum;   //当前页数
    public Integer pageSize;  //每页显示条数
    private Integer pageCount; //总页数
    private Integer startRow;  //查询起始条数
    private List<?> dataList;  //存放List类型数据


    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public Integer getStartRow() {
        if(startRow==null){
            if(pageNum==null){
                startRow = 0;
            }else{
                startRow = (pageNum-1) * getPageSize();
            }
        }
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
        Integer pageCount = (total%pageSize==0) ? total/pageSize : total/pageSize+1;
        setPageCount(pageCount);
    }

    public Integer getPageNum() {
        if(pageNum==null){
            return DEFAULT_PAGE_NUM;
        }else{
            if(pageNum<1){
                pageNum = 0;
            }else{
                if(total!=null){
                    if(pageNum>total){
                        pageNum = total-1;
                    }else if(pageNum>pageCount){
                        pageNum = pageCount;
                    }
                }
            }
            return pageNum;
        }
    }

    public void setPageNum(Integer pageNum) {
        if(pageNum<1){
            this.pageNum = DEFAULT_PAGE_NUM;
        }else{
            this.pageNum = pageNum;
        }
    }

    public Integer getPageSize() {
        if (pageSize==null){
            return DEFAULT_PAGE_SIZE;
        }else{
            return pageSize;
        }
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize<1){
            this.pageSize = DEFAULT_PAGE_SIZE;
        }else{
            this.pageSize = pageSize;
        }
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "PageQueryBean{" +
                "total=" + total +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pageCount=" + pageCount +
                ", startRow=" + startRow +
                ", dataList=" + dataList +
                '}';
    }
}
