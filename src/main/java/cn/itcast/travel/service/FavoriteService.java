package cn.itcast.travel.service;

public interface FavoriteService {

    /**
     * 根据rid和uid查询判断用户是否收藏
     *
     * @param rid
     * @param uid
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 根据rid和uid添加记录
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     *
     * @param rid
     * @param uid
     */
    void rem(String rid, int uid);
}
