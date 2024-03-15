package com.example.school.controller;

import com.example.school.combineEntity.Result;
import com.example.school.entity.NormalUser;
import com.example.school.mapper.NormalUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*（前后端链接）简历管理*/
@RestController
@RequestMapping("/NormalUser")
public class NormalUserController {
    @Autowired
    private NormalUserMapper normalUserMapper;

    @PostMapping("/update")/*简历的修改（填写）*/
    public Result update(@RequestBody NormalUser normalUser){
        if(normalUser.getName()==null||normalUser.getName().isEmpty()){
            return Result.error("姓名不可为空");
        }
        int result=normalUserMapper.update(normalUser);
        if(result>0) {
            return Result.ok("简历填写成功");
        }
        return Result.error("简历填写出错");
    }
    @PostMapping("/findOne")/*通过用户id查询详细简历信息*/
    public Result findOne(@RequestBody NormalUser normalUser){
        return Result.ok().put("data",normalUserMapper.findOne(normalUser.getUserId()));
    }

//    @PostMapping("/delete")/*简历的删除*/
//    public Integer delete(@RequestBody NormalUser normalUser){
//        return normalUserMapper.delete(normalUser.getUserId());
//    }
//

//    @GetMapping
//    public List<NormalUser> find(){
//        return normalUserMapper.findAll();
//    }

//        @PostMapping("/insert")/*普通用户简历填写*/
//        public Integer insert(@RequestBody NormalUser normalUser){
//            if(normalUserMapper.findOne(normalUser.getUserId()).isEmpty()) {
//                return normalUserMapper.insert(normalUser);/*如果查询为空就返回添加个数*/
//            }
//            else {
//                return 0;/*否则为0表示无法添加*/
//            }
//        }
}
