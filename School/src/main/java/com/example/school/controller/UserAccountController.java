package com.example.school.controller;

import com.example.school.combineEntity.AccountAndUser;
import com.example.school.combineEntity.Result;
import com.example.school.entity.UserAccount;
import com.example.school.mapper.ApplicationInfoMapper;
import com.example.school.mapper.InformationMapper;
import com.example.school.mapper.NormalUserMapper;
import com.example.school.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/*（前后端链接）账号管理*/
@RestController
@RequestMapping("/UserAccount")
public class UserAccountController {
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private NormalUserMapper normalUserMapper;
    @Autowired
    private ApplicationInfoMapper applicationInfoMapper;
    @Autowired
    private InformationMapper informationMapper;

    @PostMapping("/signup")/*普通用户账号注册ok*/
    public Result signup(@RequestBody UserAccount userAccount){
        /*字符处理（空）*/
        if(userAccount.getUserId()<=0){
            return Result.error("账号格式不正确");
        }else if(userAccount.getPassword()==null|| userAccount.getPassword().isEmpty()){
            return Result.error("密码不可为空");
        }

        if(userAccountMapper.findOne(userAccount.getUserId()).isEmpty()) {
            userAccountMapper.insert(userAccount);/*账号添加*/
            normalUserMapper.insert(userAccount.getUserId());/*简历的添加*/
            return Result.ok("账号注册成功");
        }
        else {
            return Result.error("账号用户名重复");
        }
    }

    @PostMapping("/login")/*登录功能ok*/
    public Result login(@RequestBody UserAccount userAccount){
        /*字符处理（空）*/
        if(userAccount.getUserId()==0){
            return Result.error("账号不可为空");
        }else if(userAccount.getPassword()==null|| userAccount.getPassword().isEmpty()){
            return Result.error("密码不可为空");
        }

        List<UserAccount> userAccountList=userAccountMapper.findOne(userAccount.getUserId());
        if(!userAccountList.isEmpty()){
            UserAccount userAccount1=userAccountList.get(0);
            if(Objects.equals(userAccount.getPassword(), userAccount1.getPassword())){
                return Result.ok("登陆成功").put("data",userAccountList);
            }else {
                return Result.error("密码错误");
            }
        }
        return Result.error("用户名不存在，请先注册账号");
    }

    @PostMapping("/delete")/*普通用户账号删除（该功能设计多个表请在使用中备份）ok*/
    public Result delete(@RequestBody UserAccount userAccount){
        try{
            normalUserMapper.delete(userAccount.getUserId());/*简历的删除*/
            applicationInfoMapper.deleteAllUser(userAccount.getUserId());/*应聘信息删除*/
            informationMapper.delete(userAccount.getUserId());/*删除用户的应聘消息*/
            int result = userAccountMapper.delete(userAccount.getUserId());/*账号的删除*/
            if(result>0){
                return Result.ok("账号删除成功");
            }
            return Result.error("账号删除失败");
        }catch (Exception e){
            System.out.println("账号删除出错，请检查代码");
        }
        return null;
    }

    @PostMapping("/update")/*普通用户密码更新ok*/
    public Result update(@RequestBody UserAccount userAccount){
        /*字符处理（空）*/
        if(userAccount.getPassword()==null|| userAccount.getPassword().isEmpty()){
            return Result.error("密码不可为空");
        }

        int result=userAccountMapper.update(userAccount);/*密码更新*/
        if(result>0){
            return Result.ok("密码更新成功");
        }
        return Result.error("密码更新失败");
    }

    @PostMapping("/find")/*按照姓名查找并展示人员基本信息ok*/
    public Result find(@RequestBody AccountAndUser accountAndUser){
        return Result.ok().put("data",userAccountMapper.find(accountAndUser));
    }

//    @PostMapping("/findAll")/*查找并展示所有账号和人员基本信息ok*/
//    public Result findAll(){
//        return Result.ok().put("data",userAccountMapper.findAll());
//    }

//    @GetMapping/*测试用可以不用管*/
//    public List<UserAccount> find(){
//        return userAccountMapper.findAll();
//    }

}
