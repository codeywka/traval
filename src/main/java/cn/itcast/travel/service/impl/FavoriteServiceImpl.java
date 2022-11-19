package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.nativejdbc.Jdbc4NativeJdbcExtractor;

/**
 * @BelongsProject: project03
 * @BelongsPackage: cn.itcast.travel.service.impl
 * @Author: yinwenkang
 * @CreateTime: 2022-10-17  16:00
 * @Description: TODO
 * @Version: 1.0
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        //
        Favorite favorite = dao.findByRidAndUid(Integer.parseInt(rid), uid);
        //
        /*if (favorite == null){
            return false;
        } else {
            return true;
        }*/
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid) {
        //
        dao.add(Integer.parseInt(rid), uid);
    }

    @Override
    public void rem(String rid, int uid) {
        //
        dao.rem(Integer.parseInt(rid), uid);
    }
}
