package online.aquan.shortlink.admin.controller;


import lombok.RequiredArgsConstructor;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.common.convention.result.Results;
import online.aquan.shortlink.admin.dto.req.GroupSaveDto;
import online.aquan.shortlink.admin.dto.resp.GroupRepsDto;
import online.aquan.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService; 
    
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<Void> savaGroup(@RequestBody GroupSaveDto requestParam) {
        groupService.saveGroup(requestParam);
        return Results.success();
    }
    
    
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<GroupRepsDto>> getGroupList(){
        return Results.success(groupService.getGroupList());
    }

}
