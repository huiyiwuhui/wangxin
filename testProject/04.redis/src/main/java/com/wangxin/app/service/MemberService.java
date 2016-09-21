package com.wangxin.app.service;

import com.wangxin.app.bean.Member;
import com.wangxin.app.dao.MemberDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberService {
      
     @Resource(name="memberDao")
     private MemberDao memberDao;
      
     public void setMemberDao(MemberDao memberDao)
     {
        this.memberDao = memberDao;
     }
       
     public void add(Member member){
         memberDao.add(member);
     }
      
     public void delete(String id){
         memberDao.delete(id);
     }
      
     public Member get(String id)
     {
         return memberDao.get(id);
     }
}