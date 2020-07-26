package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        //通过UserDao获取User
        User u = userDao.findByUsername(user.getUsername());
        //判断用户名是否存在
        if (u != null){
            return false;
        }
        //保存用户信息

        //保存激活码和激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);
        //发送验证邮件
        String content="<a href='http://localhost:9090/travel/user/active?code="+user.getCode()+"'>点击激活【HAHA旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;


    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public User login(User user) {
        return userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
