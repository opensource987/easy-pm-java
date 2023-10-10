package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.module.system.domain.entity.SysDictData;
import com.mdframework.utils.DictUtils;
import com.mdframework.module.system.service.ISysDictDataService;
import com.mdframework.module.system.domain.vo.SysDictDataVo;
import com.mdframework.module.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 字典 业务层处理
 * 
 * @author mdframework
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(Long[] dictCodes)
    {
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData)
    {
        dictData.setCreateTime(new Date());
        int row = (this.save(dictData))?1:0;
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        /*UpdateWrapper<SysDictData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(dictData.getDictType()!=null,"dict_sort",dictData.getDictSort())
            .set(dictData.getDictLabel()!=null,"dict_label",dictData.getDictLabel())
            .set(dictData.getDictValue()!=null,"dict_value",dictData.getDictValue())
            .set(dictData.getDictType()!=null,"dict_type",dictData.getDictType())
            .set(dictData.getCssClass()!=null,"css_class",dictData.getCssClass())
            .set(StringUtils.isNotBlank(dictData.getListClass()),"list_class",dictData.getListClass())
            .set(StringUtils.isNotBlank(dictData.getIsDefault()),"is_default",dictData.getIsDefault())
            .set(StringUtils.isNotBlank(dictData.getStatus()),"status",dictData.getStatus())
            .set(StringUtils.isNotBlank(dictData.getRemark()),"remark",dictData.getRemark())
            .set(StringUtils.isNotBlank(dictData.getUpdateBy()),"update_by",dictData.getUpdateBy())
            .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .eq("dict_code",dictData.getDictCode());
        int row = baseMapper.update(null,updateWrapper);*/


        int row = 0;
        if (this.updateById(dictData))
        {
            row = 1;
            DictUtils.clearDictCache();
        }
        return row;
    }

    @Override
    public List<SysDictDataVo> listVo(QueryWrapper<SysDictData> lqw) {
        return this.baseMapper.listVo(lqw);
    }
}
