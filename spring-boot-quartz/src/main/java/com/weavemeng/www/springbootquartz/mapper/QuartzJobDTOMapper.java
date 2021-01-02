package com.weavemeng.www.springbootquartz.mapper;

import com.weavemeng.www.springbootquartz.dto.QuartzJobDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author gaoji
 */
@Mapper
public interface QuartzJobDTOMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(QuartzJobDTO record);

    int insertSelective(QuartzJobDTO record);

    QuartzJobDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuartzJobDTO record);

    int updateByPrimaryKey(QuartzJobDTO record);

    List<QuartzJobDTO> selectJob();

    List<QuartzJobDTO> selectJobByGroup(@Param("group") String group);

    QuartzJobDTO selectByJobName(QuartzJobDTO job);
}