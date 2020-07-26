package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 通过username查询User
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**保存
     *
     * @param user
     */
    void save(User user);

    /**
     * 通过code查询User
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更改激活状态
     * @param user
     */
    void updateStatus(User user);


    /**
     * 通过账号密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username,String password);
}
