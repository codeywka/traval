package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * @BelongsProject: project03
 * @BelongsPackage: cn.itcast.travel.dao.impl
 * @Author: yinwenkang
 * @CreateTime: 2022-10-17  16:13
 * @Description: TODO
 * @Version: 1.0
 */
public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        //
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            //
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.println("没有人收藏过哦");
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";

        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        //
        String sql = "insert into tab_favorite values(?,?,?)";
        //
        template.update(sql, rid, new Date(), uid);
    }

    @Override
    public void rem(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ?";

        template.update(sql,rid,uid);
    }
}
