package com.imooc.activitiweb.mapper;



import com.imooc.activitiweb.pojo.Act_ru_task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


@Mapper
@Component
public interface ActivitiMapper {

    //读取表单
    @Select("SELECT Control_ID_,Control_VALUE_ from formdata where PROC_INST_ID_ = #{PROC_INST_ID}")
    List<HashMap<String, Object>> selectFormData(@Param("PROC_INST_ID") String PROC_INST_ID);


    //写入表单
    @Insert("<script> insert into formdata (PROC_DEF_ID_,PROC_INST_ID_,FORM_KEY_,Control_ID_,Control_VALUE_)" +
            "    values" +
            "    <foreach collection=\"maps\" item=\"formData\" index=\"index\" separator=\",\">" +
            "      (#{formData.PROC_DEF_ID_,jdbcType=VARCHAR},#{formData.PROC_INST_ID_,jdbcType=VARCHAR}," +
            "      #{formData.FORM_KEY_,jdbcType=VARCHAR}, #{formData.Control_ID_,jdbcType=VARCHAR},#{formData.Control_VALUE_,jdbcType=VARCHAR})" +
            "    </foreach>  </script>")
    int insertFormData(@Param("maps") List<HashMap<String, Object>> maps);

    //删除表单
    @Delete("DELETE FROM formdata WHERE PROC_DEF_ID_ = #{PROC_DEF_ID}")
    int DeleteFormData(@Param("PROC_DEF_ID") String PROC_DEF_ID);

    //获取用户名
    @Select("SELECT name,username from user")
    List<HashMap<String, Object>> selectUser();

    //测试
    @Select("select NAME_,TASK_DEF_KEY_ from act_ru_task")
    List<Act_ru_task> selectName();

    //流程定义数
    //SELECT COUNT(ID_) from ACT_RE_PROCDEF

    //进行中的流程实例
    //SELECT COUNT(DISTINCT PROC_INST_ID_) from act_ru_execution

    //查询流程定义产生的流程实例数
/*    SELECT p.NAME_,COUNT(DISTINCT e.PROC_INST_ID_) as PiNUM from act_ru_execution AS e
    RIGHT JOIN ACT_RE_PROCDEF AS p on e.PROC_DEF_ID_ = p.ID_
    WHERE p.NAME_ IS NOT NULL GROUP BY p.NAME_*/


}
