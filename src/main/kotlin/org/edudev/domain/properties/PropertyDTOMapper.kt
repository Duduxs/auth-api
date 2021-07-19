package org.edudev.domain.properties

import org.edudev.arch.dtos.EntityDTOMapper
import javax.enterprise.context.Dependent
import javax.inject.Inject

@Dependent
class PropertyDTOMapper @Inject constructor(
    properties: Properties,
) : EntityDTOMapper<Property, PropertyDTO, PropertySummaryDTO>(
    dtoType = PropertyDTO::class,
    fullMapper = { PropertyDTO(it) },
    summaryMapper = { PropertySummaryDTO(it) },
    fullUnmapper = { dto -> dto.update( properties.findById(dto.id) ?: Property(dto.id) ) }
)