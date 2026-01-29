package com.nijiahao.llm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nijiahao.common.core.domain.ResultCode;
import com.nijiahao.common.core.exception.ServiceException;
import com.nijiahao.llm.api.dto.po.AiDatasetPo;
import com.nijiahao.llm.api.dto.req.AiDatasetAddDto;
import com.nijiahao.llm.api.dto.req.AiDatasetUpdateDto;
import com.nijiahao.llm.api.dto.res.AiDatasetVo;
import com.nijiahao.llm.mapper.AiDatasetMapper;
import com.nijiahao.llm.service.AiKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nijiahao.common.core.utils.CRUDUTILS.updateIfChanged;

@Service
public class AiKnowledgeImpl extends ServiceImpl<AiDatasetMapper, AiDatasetPo> implements AiKnowledgeService {

    @Autowired
    private AiDatasetMapper aiDatasetMapper;

    @Override
    public AiDatasetVo addDataset(AiDatasetAddDto aiDatasetAddDto) {

        if (aiDatasetAddDto == null || aiDatasetAddDto.getName() == null ) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "新增知识库时，入参和name必须有值");
        }

        AiDatasetPo aiDatasetPo = AiDatasetPo.builder()
                .name(aiDatasetAddDto.getName())
                .description(aiDatasetAddDto.getDescription())
                .avatar(aiDatasetAddDto.getAvatar())
                .isPublic(aiDatasetAddDto.getIsPublic())
                .docCount(0)
                .ownerId(StpUtil.getLoginIdAsLong())
                .build();

        aiDatasetMapper.insert(aiDatasetPo);

        return aiDatasetPo2Vo(aiDatasetPo);
    }

    @Override
    public AiDatasetVo updateDataset(AiDatasetUpdateDto aiDatasetUpdateDto) {
        if (aiDatasetUpdateDto == null || aiDatasetUpdateDto.getId() == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "修改知识库时，入参和id必须有值");
        }

        //先查出旧数据
        AiDatasetPo oldPo = aiDatasetMapper.selectById(aiDatasetUpdateDto.getId());
        boolean ischange = false;

        if (oldPo == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "该数据不存在");
        }else {
            ischange |= updateIfChanged(aiDatasetUpdateDto.getName() , oldPo.getName() , oldPo::setName);
            ischange |= updateIfChanged(aiDatasetUpdateDto.getAvatar() , oldPo.getAvatar() , oldPo::setAvatar);
            ischange |= updateIfChanged(aiDatasetUpdateDto.getDescription() , oldPo.getDescription() , oldPo::setAvatar);
            ischange |= updateIfChanged(aiDatasetUpdateDto.getIsPublic() , oldPo.getIsPublic() , oldPo::setIsPublic);
        }
        if (ischange) {
            if (!oldPo.getRevision().equals(aiDatasetUpdateDto.getRevision())) {
                throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR, "这条数据已经被别人修改了");
            }

            int row = aiDatasetMapper.updateById(oldPo);

            if (row == 0) {
                throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR, "这条数据已经被别人修改了");

            }
        }




        return aiDatasetPo2Vo(oldPo);
    }

    @Override
    public AiDatasetVo getDatasetById(Integer id) {

        if (id == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "【getDatasetById】传入id为空值");
        }
        AiDatasetPo aiDatasetPo = aiDatasetMapper.selectById(id);

        if (aiDatasetPo == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "【getDatasetById】没有这条数据");
        }

        return aiDatasetPo2Vo(aiDatasetPo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiDatasetVo deleteKnowledge(Integer id) {
        if (id == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "【deleteKnowledge】传入id为空值");
        }

        //查出旧数据
        AiDatasetPo oldPo = aiDatasetMapper.selectById(id);
        if (oldPo == null) {
            throw new ServiceException(ResultCode.KNOWLEDGE_MANAGE_ERROR , "【deleteKnowledge】没有这一条数据");
        }
        if (oldPo.getDocCount() > 0){
            // todo 删除对应知识库下的文档
        }

        aiDatasetMapper.deleteById(oldPo);

        return aiDatasetPo2Vo(oldPo);
    }

    private AiDatasetVo aiDatasetPo2Vo(AiDatasetPo aiDatasetPo) {
        return AiDatasetVo.builder()
                .id(aiDatasetPo.getId())
                .name(aiDatasetPo.getName())
                .description(aiDatasetPo.getDescription())
                .avatar(aiDatasetPo.getAvatar())
                .isPublic(aiDatasetPo.getIsPublic())
                .revision(aiDatasetPo.getRevision())
                .createTime(aiDatasetPo.getCreateTime())
                .updateTime(aiDatasetPo.getUpdateTime())
                .docCount(aiDatasetPo.getDocCount())
                .build();
    }


}
