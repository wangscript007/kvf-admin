package com.kalvin.kvf.service.sys;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kalvin.kvf.comm.constant.Constants;
import com.kalvin.kvf.dto.ZTreeDTO;
import com.kalvin.kvf.entity.sys.Menu;
import com.kalvin.kvf.mapper.sys.MenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<String> getPermission(Long userId) {
        return baseMapper.selectPermission(userId);
    }

    @Override
    public Page<Menu> listMenuPage(Menu menu) {
        Page<Menu> page = new Page<>(menu.getCurrent(), menu.getSize());
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(menu.getName())) {
            menu.setParentId(menu.getId() == null ? 0L : menu.getId()); // treegrid默认传的id作为parentId
        }
        List<Menu> menus = baseMapper.selectMenuList(menu, page);
        return page.setRecords(menus);
    }

    @Override
    public List<Menu> listMenuTree(Menu menu) {
        // 当名称为空，即不是查询操作；设置parentId，否则不需要区分parentId
        if (StrUtil.isBlank(menu.getName())) {
            menu.setParentId(menu.getId() == null ? 0L : menu.getId()); // treegrid默认传的id作为parentId
        }
        return baseMapper.selectMenuList(menu);
    }

    @Override
    public List<Menu> listMenuByParentId(Long parentId) {
        return list(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getParentId, parentId == null ? 0L : parentId)
                .eq(Menu::getStatus, Constants.STATUS_0)
                .orderByAsc(Menu::getSort));
    }

    @Override
    public List<ZTreeDTO> listRoleMenu(Long roleId) {
        return baseMapper.selectRoleMenu(roleId);
    }

    @Override
    public List<Menu> listUserPermissionMenu(Long parentId, Long userId) {
        return baseMapper.selectUserPermissionMenuList(parentId, userId);
    }

    @Override
    public List<Menu> listUserPermissionMenuWithSubByUserId(Long userId) {
        List<Menu> menus = this.listUserPermissionMenu(0L, userId);
        menus.forEach(menu -> {
            List<Menu> submenus = this.listUserPermissionMenu(menu.getId(), userId);
            menu.setSubMenus(submenus);
        });
        return menus;
    }

    @Override
    public List<Menu> listUserPermissionNavMenuByUserId(Long userId) {
        return baseMapper.selectUserPermissionNavMenuList(userId);
    }

}