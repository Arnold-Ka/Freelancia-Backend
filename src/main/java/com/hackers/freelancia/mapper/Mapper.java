package com.hackers.freelancia.mapper;

import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface Mapper {

}
