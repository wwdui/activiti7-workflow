package com.imooc.activitiweb.mapper;



import com.imooc.activitiweb.pojo.Act_ru_task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface ActivitiMapper {

    @Select("select NAME_,TASK_DEF_KEY_ from act_ru_task")
    List<Act_ru_task> selectName();
}
