package cn.dsscm.service;

import cn.dsscm.pojo.News;
import java.util.List;

/**
 * 新闻业务逻辑接口
 */
public interface NewsService {

    /**
     * 分页查询新闻
     */
    List<News> getNewsList(String title, int pageIndex, int pageSize);

    /**
     * 统计新闻数量
     */
    int getNewsCount(String title);

    /**
     * 添加新闻
     */
    boolean addNews(News news);

    /**
     * 修改新闻
     */
    boolean modifyNews(News news);

    /**
     * 删除新闻
     */
    boolean deleteNews(Long id);

    /**
     * 根据ID获取新闻
     */
    News getNewsById(Long id);

    /**
     * 提交审批（设置 approvalStatus = 1）
     */
    boolean submitForApproval(Long id);
}