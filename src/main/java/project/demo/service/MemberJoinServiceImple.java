package project.demo.service;

import java.util.Map;

public interface MemberJoinServiceImple {

    public boolean idOverlap(Map<String,String> id);
    public boolean nickNameOverlap(Map<String,String> nickName);
}
