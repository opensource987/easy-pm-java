package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.annotation.DataSource;
import com.mdframework.common.constant.Constants;
import com.mdframework.common.constant.UserConstants;
import com.mdframework.common.core.redis.RedisCache;
import com.mdframework.common.core.text.Convert;
import com.mdframework.common.enums.DataSourceType;
import com.mdframework.common.exception.CustomException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysConfigService;
import com.mdframework.module.system.domain.SysConfig;
import com.mdframework.module.system.domain.vo.SysConfigVo;
import com.mdframework.module.system.mapper.SysConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 参数配置 服务层实现
 * 
 * @author mdframework
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService
{
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init()
    {
        QueryWrapper<SysConfig> lqw = new QueryWrapper<SysConfig>();
        SysConfig sysConfig = new SysConfig();
        lqw.like(StringUtils.isNotBlank(sysConfig.getConfigName()),"config_name",sysConfig.getConfigName())
                .eq(StringUtils.isNotBlank(sysConfig.getConfigType()),"config_name",sysConfig.getConfigType())
                .like(StringUtils.isNotBlank(sysConfig.getConfigKey()),"config_key",sysConfig.getConfigKey())
                .ge(sysConfig.getBeginTime()!=null,"create_time",sysConfig.getBeginTime())
                .le(sysConfig.getEndTime()!=null,"create_time",sysConfig.getEndTime());

        //List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        List<SysConfig> configsList = configMapper.selectMPConfigList(lqw);
        for (SysConfig config : configsList)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 查询参数配置信息
     * 
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    @DataSource(DataSourceType.MASTER)
    public SysConfig selectConfigById(Long configId)
    {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey)
    {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue))
        {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig))
        {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    //用MP形式selectMPConfigList代替selectConfigList
    @Override
    public List<SysConfig> selectConfigList(SysConfig config)
    {
        return configMapper.selectConfigList(config);
    }

    @Override
    public List<SysConfig> selectMPConfigList(QueryWrapper<SysConfig> lqw) {
        SysConfig sysConfig = new SysConfig();
        lqw.like(StringUtils.isNotBlank(sysConfig.getConfigName()),"config_name",sysConfig.getConfigName())
            .eq(StringUtils.isNotBlank(sysConfig.getConfigType()),"config_name",sysConfig.getConfigType())
            .like(StringUtils.isNotBlank(sysConfig.getConfigKey()),"config_key",sysConfig.getConfigKey())
            .ge(sysConfig.getBeginTime()!=null,"create_time",sysConfig.getBeginTime())
            .le(sysConfig.getBeginTime()!=null,"create_time",sysConfig.getBeginTime());
        return configMapper.selectMPConfigList(lqw);
    }

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config)
    {
        //Mybatisplus的insert插入失败，用代码实现
        config.setCreateTime(new Date());
        int row = baseMapper.insert(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config)
    {
        UpdateWrapper<SysConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtils.isNotBlank(config.getConfigName()),"config_name",config.getConfigName())
                .set(StringUtils.isNotBlank(config.getConfigKey()),"config_key",config.getConfigKey())
                .set(StringUtils.isNotBlank(config.getConfigValue()),"config_value",config.getConfigValue())
                .set(StringUtils.isNotBlank(config.getConfigType()),"config_type",config.getConfigType())
                .set(StringUtils.isNotBlank(config.getUpdateBy()),"update_by",config.getUpdateBy())
                .set(StringUtils.isNotBlank(config.getRemark()),"remark",config.getRemark())
                .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .eq("config_id",config.getConfigId());
        int row = baseMapper.update(null,updateWrapper);
        //int row = configMapper.updateConfig(config);
        if (row > 0)
        {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(Long[] configIds)
    {
        for (Long configId : configIds)
        {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
            {
                throw new CustomException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
        }
        int count = configMapper.deleteConfigByIds(configIds);
        if (count > 0)
        {
            Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
            redisCache.deleteObject(keys);
        }
        return count;
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache()
    {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 校验参数键名是否唯一
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config)
    {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }

    @Override
    public List<SysConfigVo> listVo(QueryWrapper<SysConfig> lqw) {
        return this.baseMapper.listVo(lqw);
    }
}
