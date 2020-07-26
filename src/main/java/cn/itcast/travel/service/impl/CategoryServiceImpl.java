package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {


    @Override
    public List<Category> findAll() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        //1.使用redis缓存，获取redis里的category
        Jedis jedis = JedisUtil.getJedis();
        Set<String> cs = jedis.zrange("category", 0, -1);
        //2.判断是否存在
        //3.若不存在，从数据库中获取,存入redis
        List<Category> categories = null;
        if(cs == null || cs.size() == 0 ){
            System.out.println("从数据库取值。。");
           categories =  categoryDao.findAll();
            for (Category c : categories) {
                 jedis.zadd("category",c.getCid(),c.getCname());
            }

        }else {
            System.out.println("从redis取值。。");
            //需要将Set转换为List
            categories = new ArrayList<Category>();
            for (String c : cs) {
                Category category = new Category();
                category.setCname(c);
                categories.add(category);
            }
        }

        //4.若存在，直接返回
        return categories;
    }
}
