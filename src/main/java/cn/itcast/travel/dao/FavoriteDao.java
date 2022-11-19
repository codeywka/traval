package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     *
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid查询收藏次数
     *
     * @param rid
     */
    int findCountByRid(int rid);

    /**
     * 根据rid和uid添加记录
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     *
     * @param rid
     * @param uid
     */
    void rem(int rid, int uid);
}
