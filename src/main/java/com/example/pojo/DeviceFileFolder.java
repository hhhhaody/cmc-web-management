package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceFileFolder {
        private Long folderId;
        private String folderName;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date createdAt;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date updatedAt;
}
