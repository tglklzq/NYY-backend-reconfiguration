package org.lzq.nyybackend.config;

import java.util.*;

public class MenuConfig {
    public static final Map<Integer, List<String>> ROLE_MENU_MAP = new HashMap<>();

    static {
        // 角色的菜单映射
        ROLE_MENU_MAP.put(1, List.of("管理员管理", "文章管理", "知识分享平台管理", "社区管理",
                "系统管理", "系统设置", "系统日志", "系统公告"));
//        ROLE_MENU_MAP.put(2, List.of("系统管理", "系统设置", "系统日志", "系统公告"));
//        ROLE_MENU_MAP.put(3, List.of("菜单1", "菜单2", "菜单3", "菜单4", "菜单5", "菜单6", "菜单7", "菜单8"));
//        ROLE_MENU_MAP.put(4, List.of("菜单1", "菜单2", "菜单3", "菜单4", "菜单5", "菜单6", "菜单7", "菜单8"));
    }

    public static final Map<String, List<Map<String, String>>> MENU_ITEMS = new LinkedHashMap<>(); // 使用LinkedHashMap

    static {
        List<Map<String, String>> put =
        MENU_ITEMS.put("管理员管理", List.of(
                  Map.of("title", "管理员信息页面", "component", "Customer/customer1", "name", "customer1", "path", "/customer1"),
                  Map.of("title", "管理员日志", "component", "Customer/customer2", "name", "customer2", "path", "/customer2")
               //，   Map.of("title", "页面3", "component", "Customer/customer3", "name", "customer3", "path", "/customer3"),
        ));
        MENU_ITEMS.put("文章管理", List.of(
                Map.of("title", "文章发布", "component", "Room/room1", "name", "room1", "path", "/room1"),
                Map.of("title", "文章审核", "component", "Room/room2", "name", "room2", "path", "/room2")
        ));
        MENU_ITEMS.put("知识分享平台管理", List.of(
                Map.of("title", "课题", "component", "Order/order1", "name", "order1", "path", "/order1"),
                Map.of("title", "页面2", "component", "Order/order2", "name", "order2", "path", "/order2")
        ));
        MENU_ITEMS.put("社区管理", List.of(
                Map.of("title", "社区帖子", "component", "Staff/staff1", "name", "staff1", "path", "/staff1"),
                Map.of("title", "活动", "component", "Staff/staff2", "name", "staff2", "path", "/staff2")

        ));
        MENU_ITEMS.put("系统管理", List.of(
                Map.of("title", "页面1", "component", "System/system1", "name", "system1", "path", "/system1"),
                Map.of("title", "页面2", "component", "System/system2", "name", "system2", "path", "/system2")
        ));
        MENU_ITEMS.put("系统设置", List.of(
                Map.of("title", "页面1", "component", "Setting/setting1", "name", "setting1", "path", "/setting1"),
                Map.of("title", "页面2", "component", "Setting/setting2", "name", "setting2", "path", "/setting2")
        ));
        MENU_ITEMS.put("系统日志", List.of(
                Map.of("title", "页面1", "component", "Log/log1", "name", "log1", "path", "/log1"),
                Map.of("title", "页面2", "component", "Log/log2", "name", "log2", "path", "/log2")
        ));
        MENU_ITEMS.put("系统公告", List.of(
                Map.of("title", "页面1", "component", "Announcement/announcement1", "name", "announcement1", "path", "/announcement1"),
                Map.of("title", "页面2", "component", "Announcement/announcement2", "name", "announcement2", "path", "/announcement2")
        ));
    }

    // 新增方法：根据角色ID获取菜单数据并格式化为前端需要的结构
    public static List<Map<String, Object>> getMenuForRole(int roleId) {
        List<Map<String, Object>> menuList = new ArrayList<>();
        List<String> menus = ROLE_MENU_MAP.get(roleId);

        if (menus != null) {
            for (String menu : menus) {
                Map<String, Object> menuMap = new HashMap<>();
                menuMap.put("title", menu);

                List<Map<String, String>> subMenus = MENU_ITEMS.get(menu);
                if (subMenus != null) {
                    List<Map<String, String>> children = new ArrayList<>();
                    for (Map<String, String> subMenu : subMenus) {
                        Map<String, String> childMap = new HashMap<>(subMenu);
                        children.add(childMap);
                    }
                    menuMap.put("children", children);
                } else {
                    menuMap.put("children", new ArrayList<>()); // 确保有空子菜单列表
                }
                menuList.add(menuMap);
            }
        }

        return menuList;
    }
}
