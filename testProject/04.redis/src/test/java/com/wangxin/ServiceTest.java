package com.wangxin;


import com.wangxin.app.bean.Member;
import com.wangxin.app.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administra on 2016/9/4.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-mybatis.xml"})
public class ServiceTest{

    @Autowired
    private MemberService memberService;

    @Test
    public void test(){
        String  id = "11";
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(id);
        Member member = this.memberService.get(id);
        if(null!=member){
            map.put("message", "查询Id=" + id + "的用户名为:" + member.getNickname());
        }else{
            map.put("message", "没有查询到与Id=" + id + "相关的数据");
        }

        System.out.println(map.toString());
    }



    @Test
    public void add(){
        Member member = new Member();
        member.setId("11");
        member.setNickname("wangxin");
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(member);
        map.put("message", "成功添加数据到库," + member);
        this.memberService.add(member);
    }
}
