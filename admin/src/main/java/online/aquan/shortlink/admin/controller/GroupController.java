package online.aquan.shortlink.admin.controller;


import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.dto.req.GroupSortDto;
import online.aquan.shortlink.admin.dto.req.GroupUpdateDto;
import online.aquan.shortlink.admin.dto.resp.GroupRepsDto;
import online.aquan.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     * @param requestParam 分组名
     */
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<Void> savaGroup(@RequestBody GroupSaveDto requestParam) {
        groupService.saveGroup(requestParam);
        return Results.success();
    }


    /**
     * 获取分组集合
     * @return 分组集合
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<GroupRepsDto>> getGroupList(){
        return Results.success(groupService.getGroupList());
    }

    /**
     * 修改短链接分组名称
     * @param requestParam gid,name
     */
    @PutMapping("/api/short-link/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody GroupUpdateDto requestParam){
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接
     * @param gid gid
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public Result<Void> deleteGroup(@RequestParam String gid){
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 修改短链接的排序规则
     * @param requestParam gid和sortOrder的集合
     */
    @PostMapping("/api/short-link/admin/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<GroupSortDto> requestParam){
        groupService.sortGroup(requestParam);
        return Results.success();
    }

}
