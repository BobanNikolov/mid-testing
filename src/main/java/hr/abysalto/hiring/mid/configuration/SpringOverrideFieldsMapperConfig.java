package hr.abysalto.hiring.mid.configuration;

import org.mapstruct.*;

/**
 * Reusable mapper configuration overriding existing values in target when source is null.
 * Unmapped target properties are ignored.
 * Has semantic like PUT method.
 * <pre><code class='java'>
 * &#64;Mapper(config = SpringOverrideFieldsMapperConfig.class)
 * public interface MyEntityUpdateRequestMerger {...}
 * <code></pre>
 */
@MapperConfig(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
    collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.ERROR)
public class SpringOverrideFieldsMapperConfig {

}
