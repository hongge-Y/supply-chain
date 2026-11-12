package cn.dsscm.tools;

/**
 * 分页工具类
 * 用途：封装分页所需的核心参数（当前页、每页条数、总记录数、总页数），
 *       并提供计算起始行的便捷方法。
 */
public class PageUtil {

    /** 当前页码（从 1 开始） */
    private int pageIndex;

    /** 每页显示记录数 */
    private int pageSize;

    /** 总记录数 */
    private int totalCount;

    /** 总页数（由总记录数和每页条数计算得出） */
    private int totalPages;

    /**
     * 构造分页对象，自动计算总页数
     * @param pageIndex 当前页码
     * @param pageSize  每页记录数
     * @param totalCount 总记录数
     */
    public PageUtil(int pageIndex, int pageSize, int totalCount) {
        // 参数兜底：页码从 1 开始，每页至少 1 条，总数不为负
        this.pageIndex = pageIndex < 1 ? 1 : pageIndex;
        this.pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
        this.totalCount = totalCount < 0 ? 0 : totalCount;
        this.totalPages = calcTotalPages(this.totalCount, this.pageSize);
    }

    /** 默认每页条数 */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 计算总页数：向上取整，避免浮点数
     */
    private static int calcTotalPages(int totalCount, int pageSize) {
        return pageSize < 1 ? 0 : (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 获取当前页码
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置当前页码（如需手动调整）
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
        this.totalPages = calcTotalPages(this.totalCount, this.pageSize);
    }

    /**
     * 获取总记录数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数（修改后会重新计算总页数）
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount < 0 ? 0 : totalCount;
        this.totalPages = calcTotalPages(this.totalCount, this.pageSize);
    }

    /**
     * 获取总页数
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 计算数据库查询的起始行索引（从 0 开始）
     * 通常用于 MyBatis 的 LIMIT #{start}, #{size}
     * @return 起始行索引（0 表示第一行）
     */
    public int getStart() {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 是否还有上一页 / 下一页
     */
    public boolean isHasPrev() {
        return pageIndex > 1;
    }

    public boolean isHasNext() {
        return pageIndex < totalPages;
    }
}