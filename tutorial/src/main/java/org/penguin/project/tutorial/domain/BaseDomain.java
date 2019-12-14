package org.penguin.project.tutorial.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: t3
 * @description:
 * @author: 程英华
 * @CreateDate: 2019-11-21 17:03
 * @UpdateUser:
 * @UpdateDate: 2019/11/21 17:03
 * @UpdateRemark:
 *
 */

@Data
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 6459686528761795310L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
