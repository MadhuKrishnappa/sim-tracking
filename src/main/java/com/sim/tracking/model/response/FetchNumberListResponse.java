package com.sim.tracking.model.response;

import com.sim.tracking.model.IResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class FetchNumberListResponse implements IResponse {

    private List<FetchNumberList> numberLists;
}
