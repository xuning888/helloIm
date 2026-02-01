package com.github.xuning888.helloim.api.convert;


import com.github.xuning888.helloim.api.dto.ImUserDto;
import com.github.xuning888.helloim.api.protobuf.common.v1.ImUser;
import com.github.xuning888.helloim.api.utils.ProtobufUtils;

/**
 * @author xuning
 * @date 2026/2/1 12:00
 */
public class ImUserConvert {

    public static ImUserDto bpConvertDto(ImUser imUser) {
        ImUserDto imUserDto = new ImUserDto();
        imUserDto.setUserId(imUser.getUserId());
        imUserDto.setUserType(imUser.getUserType());
        imUserDto.setUserName(imUser.getUserName());
        imUserDto.setIcon(imUser.getIcon());
        imUserDto.setMobile(imUser.getMobile());
        imUserDto.setDeviceId(imUser.getDeviceId());
        imUserDto.setExtra(imUser.getExtra());
        imUserDto.setUserStatus(imUser.getUserStatus());
        imUserDto.setCreatedAt(ProtobufUtils.convertDate(imUser.getCreatedAt()));
        imUserDto.setUpdatedAt(ProtobufUtils.convertDate(imUser.getUpdatedAt()));
        return imUserDto;
    }
}
