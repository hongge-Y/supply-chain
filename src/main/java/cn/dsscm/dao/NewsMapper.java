package cn.dsscm.dao;

import cn.dsscm.pojo.News;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 新闻表数据访问接口
 */
public interface NewsMapper {

    /**
     * 分页查询新闻（可按标题模糊查询）
     */
    List<News> selectByPage(@Param("title") String title,
                            @Param("start") int start,
                            @Param("size") int size);

    /**
     * 统计符合条件的新闻数量
     *
     * @param title 标题（模糊查询，可选）
     * @return 总记录数
     */
    int selectCount(@Param("title") String title);

    /**
     * 根据ID查询新闻
     *
     * @param id 新闻ID
     * @return 新闻对象
     */
    News selectById(@Param("id") Long id);

    /**
     * 新增新闻
     *
     * @param news 新闻对象
     * @return 受影响行数
     */
    int insertNews(News news);

    /**
     * 更新新闻
     *
     * @param news 新闻对象
     * @return 受影响行数
     */
    int updateNews(News news);

    /**
     * 删除新闻
     *
     * @param id 新闻ID
     * @return 受影响行数
     */
    int deleteNews(@Param("id") Long id);
}