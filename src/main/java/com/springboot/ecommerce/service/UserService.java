package com.springboot.ecommerce.service;

import com.springboot.ecommerce.entity.User;

public interface UserService {
    void register(User user);
    User login(String name, String password);
    //以上两个方法为实例，其余方法如：查询，修改，删除，购买之后进行拓展
}
