package org.penguin.project.tutorial.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * @program: t3
 * @description:
 * @author: 程英华
 * @CreateDate: 2019-11-25 22:13
 * @UpdateUser:
 * @UpdateDate: 2019/11/25 22:13
 * @UpdateRemark:
 * @Version:
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false,of = {"id"})
public class Role extends BaseDomain {

    private String id;

    private String name;

    private String description;

//    private Instant createdAt;
//
//    private Instant updatedAt;
}
