package com.sim.tracking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class GenericErrorBO implements Serializable {/**
 *
 */
private static final long serialVersionUID = -8871342319074337967L;

    private String errorMessage;
}
