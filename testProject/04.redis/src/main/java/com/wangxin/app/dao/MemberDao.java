package com.wangxin.app.dao;

import com.wangxin.app.bean.Member;

import java.util.List;

public interface MemberDao {
    boolean add(Member member);
 
    abstract boolean add(List<Member> list);
 
    void delete(String key);
 
    Member get(String keyId);
     
}