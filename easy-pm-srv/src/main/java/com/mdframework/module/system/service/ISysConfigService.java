package com.mdframework.module.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mdframework.module.system.domain.SysConfig;
import com.mdframework.module.system.domain.vo.SysConfigVo;
import org.apache.ibatis.annotations.Param;
import oshi.util.platform.mac.SysctlUtil;

import java.util.List;

/**
 * 参数配置 服务层
 * 
 * @author mdframework
 */
public interface ISysConfigService extends IService<SysConfig>
{
    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    //用MP形式selectMPConfigList代替selectConfigList
    public List<SysConfig> selectConfigList(SysConfig config);


    List<SysConfig> selectMPConfigList(QueryWrapper<SysConfig> lqw);    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(SysConfig config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(SysConfig config);

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public int deleteConfigByIds(Long[] configIds);

    /**
     * 清空缓存数据
     */
    public void clearCache();

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数信息
     * @return 结果
     */
    public String checkConfigKeyUnique(SysConfig config);

    List<SysConfigVo> listVo(QueryWrapper<SysConfig> lqw);
}
